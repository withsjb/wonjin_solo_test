import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper


def map = [:]
pipeline {
    agent {
        label "master"
    }
    // ! Jenkins Web에서 지정한 tools
    tools {
        maven "jenkins-maven"
    }
    environment {
        // ! Jenkins Web에서 설정한 값
        JIRA_CLOUD_CREDENTIALS = credentials('jira-api-token')
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
                    def test_env = map.issue.data.fields[map.const.test_env].value[0]
                    println "Test environment (slave) --->" + test_env

                    // ! init method에서 지정해놓은 agents_ref 중 현재 설정된 Tablet info 필드 값과 일치하는 값이 있는지 확인 후 path, slave 설정 
                     map.agents_ref.each { key, value ->
                        if (test_env == key) {
                            println "map.current_node1" + map.current_node
                            if (test_env.contains("SM-")) {
                                map.current_node = key.substring(3)
                                println "map.current_node2" + map.current_node
                            } else {
                                map.current_node = key
                            }
                            map.current_path = value
                        }
                    }

                    println "current node: " + map.current_node
                    println "current node's source path: " + map.current_path

                    // ! 위에서 확인한 Tablet info 값이 지정한 환경 중 무엇과도 일치하지 않으면 에러
                    if (map.current_node == null || map.current_path == null) {
                        jenkinsException(map, "JIRA 'Tablet info' field value is invalid. These are the available values: ${map.agents_ref}")
                    }

                    // ! 가져온 Test Plan/Run issue의 Test 대상 필드에 적용된 JQL을 사용하여 get test issues.
                    def jql = map.issue.data.fields[map.const.plan_tests]
                    if (jql.length() <= 0) {
                        jenkinsException(map, "This 'Test Plan/Run' issues has empty value of 'Test 대상' field")
                    }
                    // ! JIRA REST API (JQL로 이슈들 가져오기)
                    def result = getIssuesByJql(map.jira.base_url, map.jira.auth, jql.toString())

                    // ! 가져온 issue가 없으면 에러처리
                    if (result.issues.size() == 0 || result.issues == null) {
                        jenkinsException(map, "This 'Test Plan/Run' issues has no tests")
                    }

                    // ! 이슈들 하나하나의 issueKey:scenario를 map에 저장
                    for (def issue in result.issues) {
                        map.testcases.put(issue.key, issue.fields[map.jira.scenario_field].content[0].content[0].text)
                    }
                }
            }
        }

        // stage("Checkout slave's local branch") {
        //     agent {
        //         label "${map.current_node}"
        //     }
        //     steps {
        //         dir("${map.current_path}") {
        //         script {
        //             println "✅✅✅✅ Checkout slave's local branch ✅✅✅✅"
        //             try {
        //                 // ! slave의 테스트 환경에서 cicd에 사용되는 branch로 checkout (이미 해당 브랜치겠지만 예외상항을 배제)
        //                 git branch: map.git.branch, url: map.git.url
        //             } catch(error) {
        //                 jenkinsException(map, error)
        //             }
        //         }
        //     }
        //     }
        // }

        stage("Download testcases on slave") {
            // ! agent는 지정한 slave node의 label
            agent {
                label "${map.current_node}"
            }
            steps {
                echo "스탭 진입"
                // ! dir로 특정 path를 지정하면 지정한 slave의 지정한 path에서 작업을 한다는 의미
                dir("${map.current_path}") {
                    script {
                        println "✅✅✅✅ Download testcases on slave ✅✅✅✅"
                        println "testcases count --> : ${map.testcases.size()}"
                        println "map.current_path --> : ${map.current_path}/src/main/resources/app.properties"
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
                        map.testcases.each { key, value ->

                            def feature = (map.cucumber.feature_name != null) ? "Feature: ${map.cucumber.feature_name}\n\n\n" : "Feature: Default\n\n\n"
                            sh "mkdir -p '${map.cucumber.feature_path}/${key}'"
                            sleep 1
                            def addedDescription = null
                            if (value.contains("\r\n")) {
                                addedDescription = value.replaceFirst("\r\n", ("\r\n" + key + "\n\n"))
                                 println "addedDescription1 ---> : " + addedDescription
                                feature += addedDescription
                                feature += "\n\n"
                            } else {
                                addedDescription = value.replaceFirst("\n", ("\n" + key + "\n\n"))
                                println "addedDescription2 ---> : " + addedDescription
                                feature += addedDescription
                                feature += "\n\n"
                            }

                                println "key ---> : " + key
                                println "value ---> : " + feature
                                 println "${map.current_path}/a_features/auto.feature"
                                 println "./a_features/${key}/${key}.feature"
                        
                            writeFile(file: "./a_features/${key}/${key}.feature", text: feature, encoding: 'UTF-8')
                        
                        }
                        // println "key ---> : " + key
                        // println "value ---> : " + feature
                    
                        // ! slave의 directory에서 auto.feature라는 파일을 만들고 그 파일에 jira에서 가져온 모든 시나리오를 집어넣음
                      
                    }
                }
            }
        }

        stage("Reboot devices") {
            agent {
                label "${map.current_node}"
            }
            steps {
                 
                    script {
                        println "🔄🔄🔄 Rebooting the device before tests 🔄🔄🔄"
                        
                        def appPropertiesPath = "${map.current_path}/src/main/resources/app.properties"  
                        echo "Using app.properties from: ${appPropertiesPath}"
                        // app.properties 파일 읽기
                        def propsContent = readFile(appPropertiesPath)  
                        def props = [:]

                        propsContent.split('\n').each { line ->  
                        if (line && !line.startsWith('#')) { // 주석 제거  
                            def splitLine = line.split('=')  
                            if (splitLine.size() == 2) {  
                                props[splitLine[0].trim()] = splitLine[1].trim()  
                            }  
                        }  
                    }

                    // deviceName과 udid 가져오기  
                    def deviceName = props['deviceName']  
                    def udid = props['udid']

                    echo "Device Name: ${deviceName}, UDID: ${udid}"

                        if (udid) {
                            println "Rebooting device with udid: ${udid}, device name: ${deviceName}"

                            // ADB 연결 상태 확인
                            def deviceCheck = sh(script: "adb devices | grep ${udid} || echo 'notfound'", returnStdout: true).trim()
                            
                            if (deviceCheck.contains('notfound')) {
                                error "❌ Device with UDID ${udid} not found!"
                            }

                            // ADB 명령어 실행
                            sh "adb -s ${udid} reboot"
                            sleep 10  // 재부팅 후 안정적인 실행을 위한 대기 시간
                            
                            // 기기 재연결 대기
                            sh "adb -s ${udid} wait-for-device"
                            println "✅ Device ${udid} is ready."
                        } else {
                            error "❌ UDID not found in app.properties"
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
                        try {
                            // ! Run cucumber test command line
                            sh("mvn exec:java -D file.encoding=UTF-8 -D project.build.sourceEncoding=UTF-8 -D project.reporting.outputEncoding=UTF-8 -D exec.mainClass=io.cucumber.core.cli.Main -D exec.args=\"${map.cucumber.feature_path} --glue ${map.cucumber.glue} --plugin json:${map.cucumber.report_json} --plugin progress:${map.cucumber.running_progress} --publish --plugin pretty --plugin html:${map.cucumber.cucumber_html}\"")
                        } catch(error) {
                            println "automation test error ---> : ${error.getMessage()}"
                        }
                    }
                }

                dir("${map.current_path}") {
                    script {
                        try {
                            // ! 테스트가 끝난 후 appium server kill (원래는 이 stage에서만 실행되는 스크립트이기 때문에 이 stage가 끝나면 저절로 appium server가 꺼지긴 한다만, 불예측성 에러를 방지하기 위해 process 직접 종료)
                            // ! lsof -t -i :PORT 가 의미하는건 해당 포트로 할당된 process를 가져오는것
                            OUTPUT = sh script: "kill \$(lsof -t -i :${APPIUM_PORT})", returnStdout: true
                            echo OUTPUT

                            // ! 테스트가 모두 끝나고 생성되는 cucumber.json 파일을 읽어서 map에 저장
                            map.cucumber.result_text = readFile file: map.cucumber.report_json
                        } catch (NoSuchFileException) {
                            throwableException(map, NoSuchFileException)
                        } catch (Exception) {
                            throwableException(map, Exception)
                        }

                        // ! Test가 정상 수행되어서 report 파일이 생성되었다면 적어도 사이즈가 0은 될 수 없음
                        if (map.cucumber.result_text == null || map.cucumber.result_text.isEmpty()) {
                            jenkinsException(map, "Reports file was created, but file is empty")
                        }
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
                            // ! 테스트 후 생성된 cucumber.json 파일을 가져온다.
                            map.cucumber.result_json = readFile file: map.cucumber.report_json
                            // ! 가져온 cucumber.json 파일을 parsing
                            def results = new JsonSlurper().parseText(map.cucumber.result_json as String)

                            def clearResult = results[0].elements
                            def isPassed = true
                            def currentIssue = null
                            def scenarioName = null

                            for (def result in clearResult) {
                                // ! description에는 반드시 해당 jira issue key값이 들어있어야 한다. 상위 stage에서 이 부분을 처리해줬음
                                if (result.description == "") {
                                    jenkinsException(map, "Scenario description (Issue key) required.")
                                }
                                currentIssue = result.description.trim()

                                // ! 테스트 Scenario의 name
                                scenarioName = result.name.trim().replaceAll(" ", "_")
                                println "defect screenshot name --> ${scenarioName}"

                                // ! 테스트 Scenario의 before, after step의 result
                                def before = result.before[0].result
                                def after = result.after[0].result

                                if (!before.status.contains("passed")) {
                                    map.cucumber.error_message = before.error_message
                                    println "befor map.cucumber.error_message --> ${map.cucumber.error_message}"
                                    isPassed = false

                                    
                                    String errorreason = errordescrit(before.error_message)
                                    
                                    // ! create defect issue 
                                    def res = createIssue(map.jira.base_url, map.jira.auth, createBugPayload(map.jira.project_key,
                                        "Defect of test '${currentIssue}'",
                                        errorreason,
                                        map.cucumber.error_message,
                                        map.jira.defect_issuetype)
                                        )
                                    
                                    // ! 추후 stage에서 screenshot을 attach할 때 필요한 정보들 
                                    map.cucumber.defect_info.put(res.key, scenarioName)

                                    // ! Plan/Run linked with Bug
                                    linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(JIRA_ISSUE_KEY, res.key, map.jira.defect_link))
                                    // ! Bug linked with Test case
                                    linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(res.key, currentIssue, map.jira.tests_link))
                                    // ! continue 처리를 하는 이유는 passed가 아닌 이후부터는 모든 step이 skipped 상태이기 때문에 문제가 발생한 스텝에서의 에러를 defect로 생성하고 다음 scenario로 넘어가면 됨
                                    continue
                                }

                                if (!after.status.contains("passed")) {
                                    map.cucumber.error_message = after.error_message
                                    println "after map.cucumber.error_message --> ${map.cucumber.error_message}"
                                    isPassed = false

                                    String errorreason = errordescrit(after.error_message)
                                    
                                    def res = createIssue(map.jira.base_url, map.jira.auth, createBugPayload(map.jira.project_key,
                                        "Defect of test '${currentIssue}'",
                                        errorreason,
                                        map.cucumber.error_message,
                                        map.jira.defect_issuetype)
                                        )
                                    // ! Plan/Run linked with Bug
                                    linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(JIRA_ISSUE_KEY, res.key, map.jira.defect_link))
                                    // ! Bug linked with Test case
                                    linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(res.key, currentIssue, map.jira.tests_link))
                                    continue
                                }
                                
                                for (def step in result.steps) {
                                    def eachStep = step.result
                                    if (!eachStep.status.contains("passed")) {
                                        map.cucumber.error_message = eachStep.error_message
                                        
                                        
                                        if (map.cucumber.error_message == null || map.cucumber.error_message == "") {
                                            // ! undefined은 error_message가 없어서 직접 처리해줘야 함. undefined은 해당 step이 implement되지 않았을 때 발생함
                                            if (eachStep.status.contains("undefined")) {
                                                isPassed = false
                                                String errorreason = errordescrit(eachStep.error_message)
                                                def res = createIssue(map.jira.base_url, map.jira.auth, createBugPayload(map.jira.project_key,
                                                "Defect of test '${currentIssue}'",
                                                errorreason,
                                                "step '${step.name}'의 step definition이 정의되지 않았습니다.", map.jira.defect_issuetype)
                                                )

                                                // ! Plan/Run linked with Bug
                                                linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(JIRA_ISSUE_KEY, res.key, map.jira.defect_link))
                                                // ! Bug linked with Test case
                                                linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(res.key, currentIssue, map.jira.tests_link))
                                                // ! 역시 마찬가지로 passed가 아닌 무언가 (undefined, failed) 생긴 이후 step은 다 skipped임 그래서 이 for문을 빠져나가면 됨
                                                break
                                            } else {
                                                // ! error_message가 없고 undefined가 아니면 skipped인 경우밖에 없음 근데 skipped인 경우가 loop에서 나오면 안됨 (skipped가 나오기전에 빠져나오는 로직을 실행하기 때문에)
                                                jenkinsException(map, "error message is empty")
                                            }
                                        }
                                        isPassed = false
                                        println currentIssue
                                        println map.cucumber.error_message
                                        println map.jira.defect_issuetype
                                        
                                        def res = createIssue(map.jira.base_url, map.jira.auth, createBugPayload(map.jira.project_key,
                                        "Defect of test '${currentIssue}'",
                                        "",
                                        map.cucumber.error_message,
                                        map.jira.defect_issuetype)
                                        )
                                        
                                        // ! 추후 stage에서 screenshot을 attach할 때 필요한 정보들 
                                        map.cucumber.defect_info.put(res.key, scenarioName)

                                        // ! Plan/Run linked with Bug
                                        linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(JIRA_ISSUE_KEY, res.key, map.jira.defect_link))
                                        // ! Bug linked with Test case
                                        linkIssue(map.jira.base_url, map.jira.auth, createLinkPayload(res.key, currentIssue, map.jira.tests_link))
                                        break
                                    }
                                }
                            }
                            


                            // if (isPassed) {
                            //     // ! 이 statement가 실행되는 경우는 모든 step이 다 passed 될 경우임 그래서 test plan/run issue를 finish 상태로 변경
                            //     transitionIssue(map.jira.base_url, map.jira.auth, transitionPayload(map.jira.success_transition), JIRA_ISSUE_KEY)
                            // } else {
                            //     // ! 이 statement가 실행되는 경우는 모든 시나리오 중 하나라도 passed가 이루어지지 않은 시나리오가 있다면 실행됨 test plan/run issue를 test fail 상태로 변경
                            //     transitionIssue(map.jira.base_url, map.jira.auth, transitionPayload(map.jira.fail_transition), JIRA_ISSUE_KEY)
                            // }
                        } catch (error) {
                            throwableException(map, error)
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
                            // ! cURL로 각 defect issue에 맞는 defect screenshot을 업로드한다.
                            map.cucumber.defect_info.each { key, value ->
                            sh "echo 'Current directory: ${map.current_path}'"
                        sh "echo 'Defect screenshot file path: ${map.current_path}/defect_screenshots/${value}.png'"

                        // 파일 존재 여부 확인
                        sh "ls -l '${map.current_path}/defect_screenshots/${value}.png'"

                        println "✅✅✅✅ ✅✅✅✅✅✅✅✅ ✅✅✅✅"


                            // sh """
                            //     curl --insecure -D- \
                            //     -u '${JIRA_CLOUD_CREDENTIALS_USR}:${JIRA_CLOUD_CREDENTIALS_PSW}' \
                            //     -X POST \
                            //     -H 'X-Atlassian-Token: no-check' \
                            //     -F 'file=@${map.current_path}/defect_screenshots/${value}.png;filename=errorscreenshot.png' \
                            //     '${map.jira.base_url}/rest/api/3/issue/${key}/attachments'
                            //     """
                                sh "echo 'jira.auth_user: ${map.jira.auth_user}'"
                                sh "echo 'defect_screenshot_path: ${map.cucumber.defect_screenshot_path}/${value}.png'"
                                sh "echo 'jira_api: ${map.jira.base_url}/rest/api/3/issue/${key}/attachments'"
                                
                                sh script: "curl -D- -u ${map.jira.auth_user} -X POST -H 'X-Atlassian-Token: no-check' -F 'file=@${map.cucumber.defect_screenshot_path}/${value}.png' ${map.jira.base_url}/rest/api/3/issue/${key}/attachments", returnStdout: false
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
                            fileIncludePattern: '**/*.json',
                            trendsLimit: 10,
                            classifications: [
                                [
                                    'key': 'Browser',
                                    'value': 'Chrome'
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
                        sh("curl -D- -u ${map.jira.auth_user} -X POST -H 'X-Atlassian-Token: no-check' -F 'file=@${map.cucumber.cucumber_html}' ${map.jira.base_url}/rest/api/3/issue/${JIRA_ISSUE_KEY}/attachments")
                    }
                }
            } 
        }

        stage("Zip file transfer") {
            steps {
                script {
                    // ! 아래 jenkins_server, jenkins_server_port는 Jenkins Web에서 Global variables로 작성할 수 있음. 
                    // ! remote map은 SSH Steps 이라는 Jenkins plugin을 사용하는 방식임 아래처럼 작성해줘야함
                    // def remote = [:]
                    // remote.name = "${jenkins_server}"
                    // remote.host = "${jenkins_server}"
                    // remote.port = jenkins_server_port as int
                    // remote.user = TBELL_BACKUP_AUTH_USR
                    // remote.password = TBELL_BACKUP_AUTH_PSW
                    // remote.allowAnyHosts = true

                    // ! Jenkins Server 내 Plugin에 접근하여 빌드 ID에 따라 생성되는 cucumber report를 JIRA Issue (Tets Plan/Run)에 올려야 함. 
                    sh("cd /Users/sonjinbin/.jenkins/jobs/${JOB_NAME}/builds/${BUILD_ID}; zip -r report_included_css_file.zip cucumber-html-reports_*; curl -D- -u ${map.jira.auth_user} -X POST -H 'X-Atlassian-Token: no-check' -F 'file=@report_included_css_file.zip' ${map.jira.base_url}/rest/api/3/issue/${JIRA_ISSUE_KEY}/attachments; rm -rf report_included_css_file.zip")
                    // sshCommand remote: remote, command: "cd /var/lib/jenkins/jobs/${JOB_NAME}/builds/${BUILD_ID}; zip -r report_included_css_file.zip cucumber-html-reports_*; curl -D- -u $TBELL_JIRA_CWCHOI_USR:$TBELL_JIRA_CWCHOI_PSW -X POST -H 'X-Atlassian-Token: no-check' -F 'file=@report_included_css_file.zip' ${map.jira.base_url}/rest/api/3/issue/${JIRA_ISSUE_KEY}/attachments; rm -rf report_included_css_file.zip"
                }
            }
        }
    }

    post {
        always {
            script {
                // ! 아래는 jenkins build가 어떻게 끝나든 무조건 test plan/run issue의 'Job Run No' field의 값에 jenkins build id를 넣어줌
                def payload = [
                    "fields": [
                        "${map.jira.job_link}": "#${BUILD_ID}"
                    ]
                ]
                payload = JsonOutput.toJson(payload)
                updateIssue(map.jira.base_url, map.jira.auth, payload, JIRA_ISSUE_KEY)
            }
        }

        failure {
            script {
                try {
                    // ! pipeline을 실행하면서 에러가 나서 fail로 떨어지면 무조건 jira plan/run issue의 status를 fail로 처리
                    transitionIssue(map.jira.base_url, map.jira.auth, transitionPayload(map.jira.fail_transition), JIRA_ISSUE_KEY)

                    // ! try catch로 감싼 이유는 이미 status가 fail일 수 있기 때문에 이미 fail인 상태면 처리
                } catch (RuntimeException) {
                    println "젠장 오류가 났잖아"
                }
            }
        }
    }
}

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
    // String error = errre.replace("\n", "\\n").replace("\"", "\\\"")
    String error = "11"
    
    // 실패 원인 텍스트
    String errordetail = "[테스트 실패 원인] " + errre

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
                                "text": "${errre}"
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


def errordescrit(String errorMessage){

                if (errorMessage) {
                        try {
                            throw new Exception(errorMessage)
                        } catch (Exception t) {
                            def logBuffer = new StringBuilder()
                            for (StackTraceElement element : t.getStackTrace()) {
                                logBuffer.append(element.toString()).append("\n")
                            }
                            map.cucumber.error_stack_trace = logBuffer.toString()
                            println map.cucumber.error_stack_trace
                            int time = 10;
                            String ui = "예제 UI"; // 실제 값으로 교체
                                String anotherUi = "다른 UI"; // 실제 값으로 교체
                            // errorreason에 값을 저장
                            String errorreason = geterrorReason(t, ui, anotherUi, time)

                            // errorreason을 map에 저장 (필요시 사용)
                            return errorreason
                        }
                    }
                    return null // errorMessage가 없으면 null 반환
}


def geterrorReason(Throwable t, String ui, String anotherUi, int time) {
    String errorReason

    switch (t.getClass().getSimpleName()) {
            case "NoSuchElementException":
                errorReason = String.format("UI 요소 %s를 찾지 못했습니다.", ui)
                break
            case "ElementNotVisibleException":
                errorReason = String.format("UI 요소 %s가 화면에 보이지 않습니다.", ui)
                break
            case "StaleElementReferenceException":
                errorReason = String.format("UI 요소 %s가 새로 로드되거나 화면에서 사라졌습니다.", ui)
                break
            case "TimeoutException":
                errorReason = String.format("UI 요소 %s가 %d초 동안 화면에 나타나지 않았습니다.", ui, time)
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
                errorReason = String.format("클릭하려는 요소 %s가 다른 요소 %s에 의해 가려져 있습니다.", ui, anotherUi)
                break
            case "HttpRequestException":
                errorReason = "네트워크 요청이 실패했습니다. 인터넷 및 와이파이를 확인하세요!"
                break
            case "SocketTimeoutException":
                errorReason = "네트워크가 지정된 시간 동안 응답을 받지 못했습니다. 인터넷 및 와이파이를 확인하세요!"
                break
            case "RuntimeException":
                errorReason = "테스트 진행 시간동안 요소가 나타나지 않았습니다."
                break
            default:
                errorReason = "알 수 없는 오류가 발생했습니다. 오류 메시지: " + t.getMessage()
                break
        }

        return errorReason

}