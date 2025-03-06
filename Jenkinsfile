import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper


def map = [:]
pipeline {
    agent any   // 'any'는 이 파이프라인이 사용 가능한 어떤 Jenkins 에이전트에서도 실행될 수 있음을 의미
    // ! Jenkins Web에서 지정한 tools
    tools {
        maven "jenkins-maven"
    }
    environment {
        // ! Jenkins Web에서 설정한 값
        JIRA_CLOUD_CREDENTIALS = credentials('jiraAPI_token')
        // ! Jira trigger를 통해 자동으로 받는 값
        JIRA_ISSUE_KEY = "${JIRA_TEST_PLAN_KEY}"

        APPIUM_ADDR = "0.0.0.0"
        // APPIUM_PORT = "4723"
        // ! 환경변수 설정 해주면 굳이 써주지 않아도 됨
        // ANDROID_HOME = "C:\\Users\\TB-NTB-223\\AppData\\Local\\Android\\Sdk"
        // ! Jenkins Build URL (Jenkins가 자동으로 만들어주는 변수)
        BUILD_URL = "${BUILD_URL}"
        // ! Jenkins Build ID (Jenkins가 자동으로 만들어주는 변수)
        BUILD_ID = "${BUILD_ID}"
    }
    stages {
        stage('Workspace Cleanup') {
            steps {
                cleanWs()
                // 또는 특정 디렉토리만 정리하려면
                // sh "rm -rf ${env.WORKSPACE}/*"
            }
        }
        stage("Init") {
            steps {
                script {
                    init(map)
                    println "✅✅✅✅ Init Pipeline ✅✅✅✅"
                    println "Plan issue key: ${JIRA_ISSUE_KEY}"
                    // ! Jenkins Credential을 Username/Password로 지정하면 _USR, _PSW가 변수로 자동 등록
                    // ! 그리고 한가지 유의할 점은 위 JIRA_ISSUE_KEY 와 달리 아래는 작은 따옴표로 {} 없이 사용하고 있는데, 이 이유는 Groovy interpolation으로 민감한 정보는 전달하면 안됨
                    // ! 전달 할 때 host OS에서 arguments value를 그대로 받기 때문에 보안에 취약함 -> Jenkins 공식 문서
                    map.jira.auth_user = '$JIRA_CLOUD_CREDENTIALS_USR:$JIRA_CLOUD_CREDENTIALS_PSW'
                    map.jira.auth = "Basic " + "${JIRA_CLOUD_CREDENTIALS_USR}:${JIRA_CLOUD_CREDENTIALS_PSW}".bytes.encodeBase64()
                }
            }
        }

        stage("Get test plan") {
            steps {
                script {
                    println "✅✅✅✅ Get test plan ✅✅✅✅"
                    // ! Jira Pipeline steps 라는 plugin 기능 중 jiraGetIssue라는 API를 사용 
                    map.issue = jiraGetIssue idOrKey: JIRA_ISSUE_KEY, site: map.jira.site_name

                    if (map.issue.data.fields.components.name && map.issue.data.fields.components.name[0]) {
                        map.cucumber.feature_name = map.issue.data.fields.components.name[0]
                    }
                    // ! trigger 시킨 이슈타입이 Test Plan 아니라면 에러
                    if (map.issue.data.fields.issuetype.name != map.const.test_plan_issuetype) {
                        jenkinsException(map, "This issue does not matched 'Test Plan/Run' issue type.")
                    }
                }
            }
        } 
        
        stage("Set environments / Get testcases") {
            steps {
                script {
                    println "✅✅✅✅ Set environments / Get testcases ✅✅✅✅"
                    // ! Jira의 custom field 중 Tablet info 라는 select field의 현재 설정된 값을 가져온다.
                    // def test_env = map.issue.data.fields[map.const.test_env].value[0]
                    def test_env = map.issue.data.fields[map.const.test_env].value
                    // println "Jira field value: ${map.issue.data.fields[map.const.test_env]}"
                    // println "Available agents: ${map.agents_ref}"


                    // println "Test environment (devices) --->" + test_env

                    // ! init method에서 지정해놓은 agents_ref 중 현재 설정된 Tablet info 필드 값과 일치하는 값이 있는지 확인 후 path, slave 설정 
                     map.agents_ref.each { key, value ->
                        if (test_env == key) {
                            println "map.current_node" + map.current_node
                            
                            map.current_node = key
                            map.current_path = value
                        }
                    }

                    println "current node: " + map.current_node
                    println "current node's source path: " + map.current_path

                    // Tablet info 값 검증
                    if (map.current_node == null || map.current_path == null) {
                        jenkinsException(map, "JIRA 'Tablet info' field value is invalid. These are the available values: ${map.agents_ref}")
                    }

                    // JQL 쿼리 검증
                    def jql = map.issue.data.fields[map.const.plan_tests]
                    if (jql == null || jql.toString().trim().isEmpty()) {
                        jenkinsException(map, "This 'Test Plan/Run' issues has empty value of 'Test 대상' field")
                    }

                    // println "Executing JQL query: ${jql}"

                    try {
                        def result = getIssuesByJql(map.jira.base_url, map.jira.auth, jql.toString())
                        // println "Result: ${result}"

                        if (result.issues == null || result.issues.size() == 0) {
                            jenkinsException(map, "This 'Test Plan/Run' issues has no tests")
                        }

                        // 이슈들의 issueKey:scenario를 map에 저장
                        for (def issue in result.issues) {
                            map.testcases.put(issue.key, issue.fields[map.jira.scenario_field].content[0].content[0].text)
                        }
                    } catch (Exception e) {
                        jenkinsException(map, "Failed to execute JQL query: ${e.message}")
                    }  
                }
            }
        }
        stage("Download testcases on slave") {
            // ! agent는 지정한 slave node의 label
            agent {
                label "${map.current_node}"
            }
            steps {
                // ! dir로 특정 path를 지정하면 지정한 slave의 지정한 path에서 작업을 한다는 의미
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Download testcases on slave ✅✅✅✅"
                        println "testcases count --> : ${map.testcases.size()}"
                        // println "map.current_path --> : ${map.current_path}"
                        if (!fileExists("${map.current_path}/src/main/resources/app.properties")) {
                            println "no app.properites"
                            map.skipByAppProperties = true
                        }
                        // fileExists는 Jenkins Pipeline에서 제공하는 method로 동일하게 사용 가능
                        if (fileExists("${map.cucumber.feature_path}")) {
                            // 해당 파일/폴더가 있다면 지움
                            sh script: """ rm -rf "${map.cucumber.feature_path}" """, returnStdout: false
                        }
                        // 해당 폴더가 없으면 만듦
                        sh script: """ mkdir "${map.cucumber.feature_path}" """, returnStdout: false


                        // ! map.testcases에 담긴 각 시나리오를 하나의 feature 파일로 변환 하는 과정에서 
                        // ! 첫 줄의 Feature Name을 지정
                        // ! JIRA에 올라가 있는 scenario를 가져와서 description으로 해당 JIRA issue key를 붙여준다.
                        // ! issue key를 붙여주는 이유는 해당 시나리오가 JIRA에 어떤 issue와 매핑되는지 알기 위함
                        map.testcases.each { testKey, testValue ->
                        def feature = (map.cucumber.feature_name != null) ? "Feature: ${map.cucumber.feature_name}\n\n\n" : "Feature: Default\n\n\n"
                        
                        // macOS 환경에 맞게 sh 명령어 사용
                        sh script: """ mkdir -p "${map.cucumber.feature_path}/${testKey}" """, returnStdout: false
                        sleep 1
                        
                        def addedDescription = null
                        if (testValue.contains("\r\n")) {
                            addedDescription = testValue.replaceFirst("\r\n", ("\r\n" + testKey + "\n\n"))
                            feature += addedDescription
                            feature += "\n\n"
                        } else {
                            addedDescription = testValue.replaceFirst("\n", ("\n" + testKey + "\n\n"))
                            feature += addedDescription
                            feature += "\n\n"
                        }
                        
                        println "key ---> : " + testKey
                        println "value ---> : " + feature
                        
                        // .feature 확장자 추가 및 변수명 수정
                        writeFile(file: "${map.cucumber.feature_path}/${testKey}/${testKey}.feature", text: feature, encoding: 'UTF-8')
                    }
                    }
                }
            }
        }

        stage("Build") {
            when { expression {!map.skipByAppProperties} }
            agent {
                label "${map.current_node}"
            }
            steps {
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Build ✅✅✅✅"
                        try {
                            // ! maven build project
                            sh('mvn clean compile -D file.encoding=UTF-8 -D project.build.sourceEncoding=UTF-8 -D project.reporting.outputEncoding=UTF-8')
                        } catch (error) {
                            throwableException(map, error)
                        }
                    }
                }
            }
        }


        stage("Run automation testing") {
            when { expression {!map.skipByAppProperties} }
            // ! agent는 등록한 slave에서만 해당 stage를 실행시킬 수 있다는 의미
            agent{
                label "${map.current_node}"
            }
            steps {
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Run automation testing ✅✅✅✅"
                        println "source location ==> ${map.current_path}"
                        println "source location ==> ${APPIUM_PORT}"
                        sh("echo $APPIUM_PORT")

                        try {
                            // ! Start appium server
                            // ! Real device로 테스트하기 때문에 0.0.0.0 으로 실행시켜야 한다.
                            // ! background로 실행하기 위해 뒤에 &
                            // ! 실행 후 10초정도 대기
                            sh script: "appium --address ${APPIUM_ADDR} --port ${APPIUM_PORT} &"
                            sleep 2

                            println "current node: " + map.current_node

                        } catch(error) {
                            throwableException(map, error)
                        }

                        // ! 기존 defect_screenshot 폴더와 파일을 삭제
                        if (fileExists("${map.cucumber.defect_screenshot_path}")) {
                            sh("rm -rf ${map.cucumber.defect_screenshot_path}")
                        }

                        // ! 기존 report json 파일을 삭제
                        if (fileExists("${map.cucumber.report_json}")) {
                            sh("rm -rf ${map.cucumber.report_json}")
                        }

                        // ! 기존 log_path 파일과 폴더를 삭제
                        if (fileExists("${map.cucumber.log_path}")) {
                            sh("rm -rf ${map.cucumber.log_path}")
                        }

                         // *실패한 시나리오를 저장할 맵 선언
                        def failedScenarios = [:]

                        // * 각 테스트 케이스 실행 함수
                        map.testcases.each { testKey, testValue ->
                            try {
                                // ! Run cucumber test command line for each test case
                                sh """
                                    mvn exec:java -D file.encoding=UTF-8 \
                                    -D project.build.sourceEncoding=UTF-8 \
                                    -D project.reporting.outputEncoding=UTF-8 \
                                    -D exec.mainClass=io.cucumber.core.cli.Main \
                                    -D exec.args="${map.cucumber.feature_path}/${testKey} \
                                    --glue ${map.cucumber.glue} \
                                    --plugin json:./a_features/${testKey}/${map.cucumber.report_json} \
                                    --plugin progress:./a_features/${testKey}/${map.cucumber.running_progress} \
                                    --publish \
                                    --plugin pretty \
                                    --plugin html:./a_features/${testKey}/${map.cucumber.cucumber_html}"
                                """

                            } catch (error) {
                                println "자동화 테스트 오류 (${testKey}): ${error.getMessage()}"

                                // *2. 재실실행에서 실패한 시나리오 저장
                                failedScenarios[testKey] = testValue
                            }
                        }
                        // *실패한 시나리오 수를 map에 저장
                        map.cucumber.failedScenarios = failedScenarios.size()

                        // *실패한 시나리오 수 출력
                        println "Number of Retry failed scenarios: ${map.cucumber.failedScenarios}"

                    }
                }
            }
        }

        stage("Analysis test result") {  
            when { expression {!map.skipByAppProperties} }  
            agent { label "${map.current_node}" }  
            steps {  
                dir("${map.current_path}") {  
                    script {  
                        println "✅✅✅✅ Analysis test result ✅✅✅✅"  

                        try {
                            map.testcases.each { testKey, testValue ->
                                // 테스트 후 생성된 cucumber.json 파일을 가져온다.  
                                def jsonFile = "./a_features/${testKey}/${map.cucumber.report_json}"
                                if (fileExists(jsonFile)) {
                                    map.cucumber.result_json = readFile(file: jsonFile)
                                    def folder = "${testKey}"
                                    println "key_________________>${testKey}"
                                    println "value_________________>${testValue}"
                                    println "folder___________>${folder}"

                                    // 가져온 cucumber.json 파일을 parsing
                                    def results = new JsonSlurper().parseText(map.cucumber.result_json)
                                    if (results && results.size() > 0) {
                                    def clearResult = results[0].elements
                                    def isPassed = true
                                    def currentIssue = null
                                    def scenarioName = null

                                    clearResult.each { result ->
                                    // description에 반드시 해당 jira issue key값이 들어있어야 한다.
                                    if (result.description == null || result.description.trim() == "") {
                                    jenkinsException(map, "Scenario description (Issue key) required.")
                                    }
                                    currentIssue = result.description.trim()

                                    // 테스트 Scenario의 name
                                    scenarioName = result.name ? result.name.trim().replaceAll(" ", "_") : "Unknown_Scenario"
                                    println "defect screenshot name --> ${scenarioName}"

                                    // 테스트 Scenario의 before, after step의 result
                                    def before = result.before ? result.before[0]?.result : null
                                    def after = result.after ? result.after[0]?.result : null

                                    if (before && !before.status?.contains("passed")) {
                                    handleFailure(map, currentIssue, before.error_message, scenarioName)
                                    isPassed = false
                                    return // continue to next iteration
                                    }

                                    if (after && !after.status?.contains("passed")) {
                                    handleFailure(map, currentIssue, after.error_message, scenarioName)
                                    isPassed = false
                                    return // continue to next iteration
                                    }

                                    result.steps?.each { step ->
                                        def eachStep = step.result
                                        if (eachStep && !eachStep.status?.contains("passed")) {
                                        handleFailure(map, currentIssue, eachStep.error_message, scenarioName, step.name)
                                        isPassed = false
                                        return true // break the inner loop
                                        }
                                    }
                                }
                                } else {
                                    println "Warning: No results found in JSON for ${testKey}"
                                }
                            } else {
                                println "Warning: JSON file not found for ${testKey}"
                            }
                        }
                        } catch (Exception e) {
                            println "Error in Analysis test result stage: ${e.message}"
                            e.printStackTrace()
                            throwableException(map, e)
                        } 
                    }  
                }  
            }  
        }   
        stage("Attached defect screenshots") {
            when { expression {!map.skipByAppProperties} }
            agent { label "${map.current_node}" }
            steps {
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Attached defect screenshots or bypass ✅✅✅✅"

                        if (map.cucumber.defect_info.size() > 0) {
                            // 스크린샷 디렉토리 존재 여부 확인 및 생성
                            dir("${map.current_path}/defect_screenshots") {
                                map.cucumber.defect_info.each { testKey, testValue ->
                                    // 파일 존재 여부 확인
                                    def screenshotPath = "${map.current_path}/defect_screenshots/${testValue}.png"
                                    if (fileExists(screenshotPath)) {
                                        try {
                                            sh """
                                                curl --insecure -D- \
                                                -u '${JIRA_CLOUD_CREDENTIALS_USR}:${JIRA_CLOUD_CREDENTIALS_PSW}' \
                                                -X POST \
                                                -H 'X-Atlassian-Token: no-check' \
                                                -F "file=@${screenshotPath};filename=errorscreenshot.png" \
                                                '${map.jira.base_url}/rest/api/3/issue/${testKey}/attachments'
                                            """
                                        } catch (Exception e) {
                                            println "Warning: Failed to attach screenshot for issue ${testKey}: ${e.message}"
                                        }
                                    } else {
                                        println "Warning: Screenshot file not found: ${screenshotPath}"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        stage("Generate cucumber html reports") {
            when { expression {!map.skipByAppProperties} }
            agent { label "${map.current_node}" }
            steps {
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Generate cucumber html reports ✅✅✅✅"
                        // ! Jenkins plugin -> Cucumber reports를 설치한 상태에서 실행해야 한다.
                        // ! buildStatus가 UNSTABLE이 의미하는 건 테스트 실행 후 하나라도 fail이 생기면 build의 status가 unstable 상태로 끝남, 만약 모두 패스면 success로 끝남
                        // ! 이렇게 하는 이유는 테스트가 fail이 나는 scenario가 있다고해서 build가 fail이라고 볼 순 없고 그렇다고 success라고 보기도 애매하기 때문에 상황에 맞춰서 build status를 표시하게 하기 위함
                        cucumber buildStatus: 'UNSTABLE',
                            reportTitle: 'My report',
                            fileIncludePattern: '**/a_features/**/cucumber.json',
                            fileExcludePattern: '**/.vscode/**/*.json',
                            trendsLimit: 10,
                            classifications: [
                                [
                                    'key': 'Browser',
                                    'value': 'Chrome'
                                ],
                                [
                                    'key': 'OS', 
                                    'value': 'MacOS'
                                ]
                            ]
                        
                        def payload = [
                            "fields": [
                                "${map.jira.test_result_link}": "${BUILD_URL}${map.cucumber.report_link}"
                            ]
                        ]
                        payload = JsonOutput.toJson(payload)
                        
                        // ! test plan/run 이슈의 Test Result Link 필드에 cucumber report url을 fetch
                        updateIssue(map.jira.base_url, map.jira.auth, payload, JIRA_ISSUE_KEY)
                        
                        // ! test plan/run 이슈의 attachment로 cucumber report를 올림 이 파일은 jenkins의 plugin인 cucumber-report가 아니고 cucumber runner를 실행할 때 실행 후 만들어지는 html 파일임
                        // ! 이렇게 하는 이유는 Jenkins 권한을 가지지 않은 사람이 있는 경우 위에 URL에 접근이 불가능하므로
                        // sh("curl -D- -u ${map.jira.auth_user} -X POST -H 'X-Atlassian-Token: no-check' -F 'file=@${map.cucumber.cucumber_html}' ${map.jira.base_url}/rest/api/3/issue/${JIRA_ISSUE_KEY}/attachments")

                    }
                }
            } 
        }
         // * 시나리오 TC건수(모든 EXAMPLES) 포함 하는 Result
        stage("Update Jira with Test Results") {
            when { expression {!map.skipByAppProperties} }
            agent { label "${map.current_node}" }
            steps {
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Update Jira with Test Results ✅✅✅✅"

                        def totalScenarios = 0 // 전체 시나리오 수
                        def failedScenarios = 0 // 실패한 시나리오 수
                        def currentDate = new Date().format("yyyy-MM-dd HH:mm:ss") // 현재 날짜와 시간 추가
                        def newTableRows = "" // 새로 생성된 테이블 행을 저장할 변수
                        def allTestsPassed = true // 모든 테스트가 성공했는지 여부 확인

                        // 중복 시나리오 이름을 추적하기 위한 맵
                        def scenarioNameCount = [:]

                        // 각 cucumber.json 파일을 읽어와 시나리오 결과를 분석
                        map.testcases.each { testKey, testValue ->
                            def jsonFile = "./a_features/${testKey}/${map.cucumber.report_json}"
                            if (fileExists(jsonFile)) {
                                // JsonSlurper를 사용하여 JSON 파일 읽기
                                def jsonContent = readFile(file: jsonFile)
                                def jsonParser = new JsonSlurper()
                                def results = jsonParser.parseText(jsonContent)

                                results[0].elements.each { scenario ->
                                    totalScenarios++ // 모든 시나리오를 카운트
                                    def name = scenario.name

                                    // 중복된 시나리오 이름에 번호 추가
                                    if (scenarioNameCount.containsKey(name)) {
                                        scenarioNameCount[name] += 1
                                        name += " Example - (${scenarioNameCount[name]})"
                                    } else {
                                        scenarioNameCount[name] = 1
                                        name += " Example - (1)"
                                    }

                                    // 실패 여부 확인 및 테이블 행 추가
                                    if (!scenario.steps.every { it.result.status == 'passed' }) {
                                        failedScenarios++ // 실패한 시나리오만 카운트
                                        allTestsPassed = false // 모든 테스트가 성공하지 않음
                                        newTableRows += "|${currentDate}|${name}|fail|\n"
                                    }
                                }
                            }
                        }

                        // Total 시나리오 TC 테이블 생성 (항상 최상단에 위치)
                        def totalScenarioTable = """
                        |Total 시나리오 TC|${totalScenarios}|
                        """

                        // 실패한 시나리오가 10개 이상인 경우 요약 테이블 생성
                        def failureSummaryTable = ""
                        if (failedScenarios >= 10) {
                            failureSummaryTable = """
                            ||일자||시나리오명||결과 (실패 시나리오 TC 개수: ${failedScenarios})||
                            |${currentDate}|10개 이상의 시나리오가 실패하였습니다.|${failedScenarios}|
                            """
                        }

                        // 모든 테스트가 성공한 경우 간략한 메시지 생성
                        def newTable = ""
                        if (allTestsPassed) {
                            newTable = """
                            ||일자||시나리오명||결과 (실패 시나리오 TC 개수: ${failedScenarios})||
                            |${currentDate}|모든 시나리오가 테스트 성공하였습니다.|성공|
                            """
                        } else if (failedScenarios < 10) {
                            newTable = """
                            ||일자||시나리오명||결과 (실패 시나리오 TC 개수: ${failedScenarios})||
                            ${newTableRows}
                            """
                        }

                        // 기존 Jira 설명 가져오기 및 "Total 시나리오 TC" 제거
                        def currentDescription = jiraGetIssue(idOrKey: JIRA_ISSUE_KEY, site: map.jira.site_name).data.fields.description ?: ""
                        currentDescription = currentDescription.replaceAll(/\|Total 시나리오 TC\|.*?\|\n/, "").trim()

                        // 새로운 설명 생성 (Total 시나리오 TC 테이블을 최상단에 배치)
                        def newDescription = """
                        ${totalScenarioTable}

                        ${failureSummaryTable}

                        ${newTable}

                        ${currentDescription}
                        """

                        // Payload 생성
                        def payload = [
                            fields: [
                                description: newDescription
                            ]
                        ]

                        // 디버깅용 로그 출력
                        println "Payload being sent to Jira:"
                        println JsonOutput.prettyPrint(JsonOutput.toJson(payload))

                        // Jira 이슈 업데이트 호출
                        updateIssue(map.jira.base_url, map.jira.auth, JsonOutput.toJson(payload), JIRA_ISSUE_KEY)

                        println "Jira issue updated with detailed results table."
                    }
                }
            }
        }

        // * 중복시나리오 제거 하여, 시나리오 수만 카운트
        // stage("Update Jira with Test Results") {
        //     when { expression {!map.skipByAppProperties} }
        //     agent { label "${map.current_node}" }
        //     steps {
        //         dir("${map.current_path}") {
        //             script {
        //                 println "✅✅✅✅ Update Jira with Test Results ✅✅✅✅"

        //                 def totalScenarios = 0
        //                 def failedScenarios = 0
        //                 def currentDate = new Date().format("yyyy-MM-dd HH:mm:ss") // 현재 날짜와 시간 추가
        //                 def tableContent = "||시나리오||결과 (${currentDate})||\n" // 테이블 헤더 생성

        //                 // 중복 제거를 위한 Map 사용 (시나리오 이름 -> 실패 여부)
        //                 def scenarioResults = [:]

        //                 // 각 cucumber.json 파일을 읽어와 시나리오 결과를 분석
        //                 map.testcases.each { testKey, testValue ->
        //                     def jsonFile = "./a_features/${testKey}/${map.cucumber.report_json}"
        //                     if (fileExists(jsonFile)) {
        //                         def jsonContent = readFile(file: jsonFile)
        //                         def results = readJSON text: jsonContent    // readJSON 사용

        //                         results[0].elements.each { scenario ->
        //                             def name = scenario.name
        //                             if (!scenarioResults.containsKey(name)) {
        //                                 scenarioResults[name] = true // 초기값은 성공으로 설정
        //                                 totalScenarios++
        //                             }
        //                             if (!scenario.steps.every { it.result.status == 'passed' }) {
        //                                 scenarioResults[name] = false // 실패가 발생하면 실패로 설정
        //                             }
        //                         }
        //                     }
        //                 }

        //                 failedScenarios = scenarioResults.values().count { !it } // 실패한 시나리오의 개수 계산

        //                 // 실패한 시나리오가 10개 이상인 경우 요약 정보만 추가
        //                 if (failedScenarios >= 10) {
        //                     tableContent += "|❌ Fail 시나리오|${failedScenarios}|\n"
        //                     tableContent += "|✅ Total 시나리오|${totalScenarios}|\n"
        //                 } else {
        //                     // 실패한 시나리오 테이블 생성
        //                     scenarioResults.each { name, isPassed ->
        //                         if (!isPassed) {
        //                             tableContent += "|${name}|실패|\n"
        //                         }
        //                     }

        //                     // 요약 정보 추가
        //                     tableContent += "|❌ Fail 시나리오|${failedScenarios}|\n"
        //                     tableContent += "|✅ Total 시나리오|${totalScenarios}|\n"
        //                 }

        //                 // 기존 Jira 설명 가져오기
        //                 def currentDescription = jiraGetIssue(idOrKey: JIRA_ISSUE_KEY, site: map.jira.site_name).data.fields.description ?: ""

        //                 // 새로운 설명 생성
        //                 def newDescription = """
        //                 ${tableContent}

        //                 ${currentDescription}
        //                 """

        //                 // Payload 생성
        //                 def payload = [
        //                     fields: [
        //                         description: newDescription
        //                     ]
        //                 ]

        //                 // 디버깅용 로그 출력
        //                 println "Payload being sent to Jira:"
        //                 println JsonOutput.prettyPrint(JsonOutput.toJson(payload))

        //                 // Jira 이슈 업데이트 호출
        //                 updateIssue(map.jira.base_url, map.jira.auth, JsonOutput.toJson(payload), JIRA_ISSUE_KEY)

        //                 println "Jira issue updated with detailed results table."
        //             }
        //         }
        //     }
        // }
    

        // stage("Update Test Plan Description") {  
        //     when { expression {!map.skipByAppProperties} }  
        //     agent { label "${map.current_node}" }  
        //     steps {  
        //         script {  
        //             println "✅✅✅✅ Update Test Plan Description ✅✅✅✅"  
                    
        //             try {  
        //                 // 현재 날짜와 시간
        //                 def currentDate = new Date().format("yyyy-MM-dd HH:mm:ss")  
        //                     def failedCount = map.cucumber.failedScenarios  // 실패한 시나리오의 수
        //                     println "실패한 시나리오의 수 --> ${failedCount}"  
        //                     def totalCount = map.testcases.size()  // 총 시나리오의 수
        //                     println "총 시나리오의 수 --> ${totalCount}"  
                        
        //                 // 새로운 설명 생성  
        //                 def newDescription = """Test Run Result (${currentDate})  
        //                 Failed Scenarios: ${failedCount}  
        //                 Total Scenarios: ${totalCount}
        //                 ----------------------------------------  

        //                 """  
        //                 // 기존 설명을 가져와서 새로운 설명 뒤에 추가  
        //                 def existingDescription = map.issue.data.fields.description ?: ""  
        //                 def payload = [  
        //                     fields: [  
        //                         description: newDescription + existingDescription  
        //                     ]  
        //                 ]  
        //                 // Jira 이슈를 업데이트
        //                 updateIssue(map.jira.base_url, map.jira.auth, JsonOutput.toJson(payload), JIRA_ISSUE_KEY)  
        //             } catch (error) {  
        //                 println "Warning: Failed to update Test Plan description: ${error.message}"  
        //             }  
        //         }  
        //     }  
        // }
    }
    

    //------------------------ 여기까지 stages ------------------------   

// * methods * //

// ? Custom exception method
def jenkinsException(java.util.Map map, String error) {
    map.exceptionMsg = error
    throw new RuntimeException("❌ " + error + " ❌")
}


// ? Catching exception 
def throwableException(java.util.Map map, Exception e) {
    map.exceptionMsg = e.toString()
    throw e as java.lang.Throwable
}

// ? init map for variables and environments...
def init(def map) {

    map.issue = null
    map.current_node = null
    map.testcases = [:]
    map.current_path = null
    map.agents_ref = [
        "T583": "/Users/sonjinbin/jenkins/T583/workspace/wongjin_solo_test@2",
        "T500": "/Users/sonjinbin/jenkins/T500/workspace"
    ]


    map.git = [:]
    map.git.branch = "master"
    map.git.url = "https://github.com/withsjb/wonjin_solo_test.git"

    map.jira = [:]
    // !jenkins Jira Steps Plugin을 위해 Configure System에서 설정한 값
    map.jira.site_name = "JIRA_CLOUD_JINBIN"
    map.jira.base_url = "https://withsjb1.atlassian.net"
    map.jira.project_key = "WON"
    map.jira.defect_issuetype = "Defect"
    // ! scenario field on tests issue
    map.jira.scenario_field = "customfield_10058"
    // ! transition id for test start -> test fail
    map.jira.fail_transition = "21"
    // ! transition id for test start -> finish 
    map.jira.success_transition = "31"
    // ! plan/run - bug link name
    map.jira.defect_link = "Defect"
    // ! bug - test link name 
    map.jira.tests_link = "Tests"
    // ! Job Run No. field
    map.jira.job_link = "customfield_10062"
    // ! Test Result Link field
    map.jira.test_result_link = "customfield_10063"
    

    map.const = [:]
    // ! Test plan/Run의 'Test 대상' field 
    map.const.plan_tests = "customfield_10059"
    // ! Test Plan/Run의 'Tablet Info' field (Multi Select field -> array)
    map.const.test_env = "customfield_10061"
    map.const.test_plan_issuetype = "Test Plan/Run"

    map.cucumber = [:]
    // ! Test Plan/Run issue의 컴포넌트에서 값이 있다면 이 feature_name에 지정할 것
    map.cucumber.feature_name = null
    // ! feature파일의 생성 경로
    map.cucumber.feature_path = "/Users/sonjinbin/jenkins/T583/workspace/wongjin_solo_test@2/a_features"
    map.cucumber.defect_screenshot_path = "defect_screenshots"
    // ! defect가 생길경우 해당 defect issue key와 해당 defect를 생성시킨 testcase의 scenario명을 저장하기 위함
    map.cucumber.defect_info = [:]
    map.cucumber.scenario_name = null
    map.cucumber.glue = "stepdefinitions"
    map.cucumber.report_json = "cucumber.json"
    map.cucumber.running_progress = "cucumber_progress.html"
    map.cucumber.cucumber_html = "cucumber_report.html"
    map.cucumber.log_path = "features_log"
    map.cucumber.log_suffix = ".feature.log"
    map.cucumber.result_json = null
    map.cucumber.error_message = null
    // ! Cucumber reports 라는 Jenkins plugin을 설치하면 cucumber test를 build 시킬 때 아래와 같은 html 파일을 job number 별로 나눠서 떨어뜨려줌
    map.cucumber.report_link = "cucumber-html-reports_fb242bb7-17b2-346f-b0a4-d7a3b25b65b4/overview-features.html"
}

def createBugPayload(String projectKey, String summary, String errre, String logdetail, String issuetype) {
    // 로그 및 에러 메시지 처리
    String log = logdetail
    
    // 실패 원인 텍스트
    String errordetail =  errre

    // 로그 내용
    String description =  log 
    
    // 새로운 description 구조로 변환
    def payload = [
        "fields": [
            "project": ["key": "${projectKey}"],
            "summary": "${summary}",
            "description": [
                "type": "doc",
                "version": 1,
                "content": [
                    [
                        "type": "paragraph",
                        "content": [
                            [
                                "type": "text",
                                "text": "[테스트 실패 원인]",
                                "marks": [
                                    [
                                        "type": "strong"
                                    ],
                                    [
                                        "type": "textColor",
                                        "attrs": [
                                            "color": "#FF0000"
                                        ]
                                    ]
                                ]
                            ],
                            [
                                "type": "hardBreak"
                            ],
                            [
                                "type": "text",
                                "text": "${errordetail}"
                            ],
                            [
                                "type": "hardBreak"
                            ],
                            [
                                "type": "text",
                                "text": "[테스트 실패 로그]",
                                "marks": [
                                    [
                                        "type": "strong"
                                    ],
                                    [
                                        "type": "textColor",
                                        "attrs": [
                                            "color": "#FF0000"
                                        ]
                                    ]
                                ]
                            ]
                        ]
                    ],
                    [
                        "type": "codeBlock",
                        "attrs": [
                            "language": "java"
                        ],
                        "content": [
                            [
                                "type": "text",
                                "text": "${description}"
                            ]
                        ]
                    ]
                ]
            ],
            "assignee": [
                // ! Jira 로그인별 ID 값 변경 필요
                "id": "712020:274498f4-ced6-44f6-ae56-7d1ef20acba1"
            ],
            "issuetype": ["name": "${issuetype}"],
            "priority": ["name": "Medium"]
        ]
    ]
    
    // JSON 변환
    return JsonOutput.toJson(payload)
}

// JIRA API 
def createIssue(String baseUrl, String auth, String payload) {
    def url = "${baseUrl}/rest/api/3/issue"
    def conn = new URL(url).openConnection()
    conn.setRequestMethod("POST")
    conn.setDoOutput(true)
    conn.setRequestProperty("Content-Type", "application/json")
    conn.addRequestProperty("Authorization", auth)
    if (payload) {
        conn.getOutputStream().write(payload.getBytes("UTF-8"))
    }

    def responseCode = conn.getResponseCode()
    println "createIssue response --> : ${responseCode}"
    if (responseCode != 200 && responseCode != 201 && responseCode != 204) {
        def errorResponse = conn.getErrorStream().getText()  // 오류 응답 본문을 읽기
        println "에러 Error response body: ${errorResponse}"  // 응답 본문 출력
        throw new RuntimeException("에러2 Error - httpCode : ${responseCode}, Response body: ${errorResponse}")
    }

    def response = conn.getInputStream().getText()
    def result = new JsonSlurper().parseText(response)
    println "result --> : ${result}"
    return result
}

def createLinkPayload(String planIssueKey, String createdDefectKey, String linkType) {
    def payload = [
        "type": ["name": "${linkType}"],
        "inwardIssue": ["key": "${planIssueKey}"],
        "outwardIssue": ["key": "${createdDefectKey}"]
    ]
    return JsonOutput.toJson(payload)
}

// JIRA API
def linkIssue(String baseUrl, String auth, String payload) {
    def url = "${baseUrl}/rest/api/3/issueLink"
    def conn = new URL(url).openConnection()
    conn.setRequestMethod("POST")
    conn.setDoOutput(true)
    conn.setRequestProperty("Content-Type", "application/json")
    conn.addRequestProperty("Authorization", auth)
    if (payload) {
        conn.getOutputStream().write(payload.getBytes("UTF-8"))
    }

    def responseCode = conn.getResponseCode()
    println "linkIssue response --> : ${responseCode}"
    if (responseCode != 201) {
        throw new RuntimeException("Error - httpCode : ${responseCode}")
    }
}

def getIssuesByJql(String baseUrl, String auth, String jql) {
    def encodedJql = java.net.URLEncoder.encode(jql, "UTF-8")
    println "replace jql --> : ${encodedJql}"

    def url = "${baseUrl}/rest/api/3/search?jql=${encodedJql}"
    println "url ---> ${url}"


    def conn = new URL(url).openConnection()
    conn.setRequestMethod("GET")
    conn.setDoOutput(true)
    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8")
    conn.addRequestProperty("Authorization", auth)
    
    def responseCode = conn.getResponseCode()
    def response = conn.getInputStream().getText()
    def result = new JsonSlurper().parseText(response)

    if (responseCode != 200) {
        throw new RuntimeException("Error - httpCode : ${responseCode}")
    }

    return result
}   

def transitionPayload(String transition) {
    def payload = [
        "transition": [
            "id": "${transition}"
        ]
    ]    
    return JsonOutput.toJson(payload)
}



def transitionIssue(String baseUrl, String auth, String payload, String issueKey) {
    def url = "${baseUrl}/rest/api/2/issue/${issueKey}/transitions"
    def conn = new URL(url).openConnection()
    conn.setRequestMethod("POST")
    conn.setDoOutput(true)
    conn.setRequestProperty("Content-Type", "application/json")
    conn.addRequestProperty("Authorization", auth)
    conn.getOutputStream().write(payload.getBytes("UTF-8"))

    def responseCode = conn.getResponseCode()
    println "transitionIssue response --> : ${responseCode}"
    if (responseCode != 204) {
        throw new RuntimeException("Error - httpCode : ${responseCode}")
    }
}

def updateIssue(String baseUrl, String auth, String payload, String issueKey) {
    def url = "${baseUrl}/rest/api/2/issue/${issueKey}"
    def conn = new URL(url).openConnection()
    conn.setRequestMethod("PUT")
    conn.setDoOutput(true)
    conn.setRequestProperty("Content-Type", "application/json")
    conn.addRequestProperty("Authorization", auth)
    conn.getOutputStream().write(payload.getBytes("UTF-8"))

    def responseCode = conn.getResponseCode()
    println "updateIssue response ---> ${responseCode}"
    if (responseCode != 204) {
        throw new RuntimeException("Error - httpCode : ${responseCode}")
    }
}


def errordescrit(String errorMessage, String uiElement){
    if (errorMessage != null && !errorMessage.trim().isEmpty()) {
        try {
            throw new RuntimeException(errorMessage)
        } catch (RuntimeException t) {
            def logBuffer = new StringBuilder()
            for (StackTraceElement element : t.getStackTrace()) {
                logBuffer.append(element.toString()).append("\n")
            }
            
            def errorStackTrace = logBuffer.toString()
            println "Stack trace logged: \n${errorStackTrace}"
            println "오류난 ui: \n${uiElement}"

            int time = 30;
            
            String anotherUi = "팝업 창"

            String errorreason = geterrorReason(t, uiElement, anotherUi, time)
            println "Generated error reason: ${errorreason}"

            return errorreason
        }
    }
    return "오류 메시지가 제공되지 않았습니다."
}


def geterrorReason(Throwable t, String ui, String anotherUi, int time) {
    String errorReason

    switch (t.getClass().getSimpleName()) {
            case "NoSuchElementException":
                errorReason = String.format("UI 요소 ${ui}를 찾지 못했습니다.")
                break
            case "ElementNotVisibleException":
                errorReason = String.format("UI 요소 ${ui}가 화면에 보이지 않습니다.")
                break
            case "StaleElementReferenceException":
                errorReason = String.format("UI 요소 ${ui}가 새로 로드되거나 화면에서 사라졌습니다.")
                break
            case "TimeoutException":
                errorReason = String.format("UI 요소 ${ui}가 ${time}초 동안 화면에 나타나지 않았습니다.")
                break
            case "NoSuchWindowException":
                errorReason = "작업 중인 창을 찾을 수 없습니다."
                break
            case "WebDriverException":
                errorReason = "WebDriver가 제대로 연결되지 않았거나 실행되지 않았습니다. 경로를 확인하세요."
                break
            case "SessionNotCreatedException":
                errorReason = "WebDriver 세션을 시작할 수 없습니다. 드라이버 버전과 브라우저 버전을 확인하세요."
                break
            case "ElementClickInterceptedException":
                errorReason = String.format("클릭하려는 요소 ${ui}가 다른 요소 ${anotherUi}에 의해 가려져 있습니다.")
                break
            case "HttpRequestException":
                errorReason = "네트워크 요청이 실패했습니다. 인터넷 및 와이파이를 확인하세요!"
                break
            case "SocketTimeoutException":
                errorReason = "네트워크가 지정된 시간 동안 응답을 받지 못했습니다. 인터넷 및 와이파이를 확인하세요!"
                break
            case "RuntimeException":
                errorReason = "테스트 진행 시간동안 ${ui}가 나타나지 않았습니다."
                break
            default:
                errorReason = "알 수 없는 오류가 발생했습니다. 오류 메시지: " + t.getMessage()
                break
        }

        return errorReason

}


def extractUiElement(String errorMessage) {
    def idPattern = ~/By\.id:\s*([\w:.]+)/    // By.id 추출 정규식
    def xpathPattern = ~/By\.xpath:\s*([^\s]+)/ // By.xpath 추출 정규식

    def matcher = errorMessage =~ idPattern
    if (matcher.find()) {
        return matcher.group(1)  // 추출된 ID 값 반환
    }

    matcher = errorMessage =~ xpathPattern
    if (matcher.find()) {
        return matcher.group(1)  // 추출된 XPath 값 반환
    }

    return "알 수 없는 UI 요소"
}