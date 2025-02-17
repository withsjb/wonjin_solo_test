package utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestReportUploader {

    static String dateOnly = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    static String Xray_API_URL;
    static String XRAY_API_TOKEN;

    static String USER_DIR;

    static String JIRA_URL;

    static String USERNAME;
    static String JIRA_TOKEN;

    static String oneonetestPlen;

    static String logpath;
    static String errorcaturepath;

    static String logandimgzip;

    static String mp4path;

    private static ZonedDateTime koreantime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String formatdate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(koreantime);


    public static String Linkissuekey;


    public static void setLinkissuekey(String key){
        Linkissuekey = key;
    }

    public static String getLinkissuekey(){
        return Linkissuekey;
    }

    //app.properties 에서 불러오기
    public static void loadProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
            USER_DIR = properties.getProperty("user.dir");
            Xray_API_URL = properties.getProperty("xray.api.cucumberurl");
            XRAY_API_TOKEN = properties.getProperty("xray.api.token");
            JIRA_URL = properties.getProperty("jira.base.url");
            JIRA_TOKEN = properties.getProperty("jira.api.token");
            USERNAME = properties.getProperty("username");
            oneonetestPlen = properties.getProperty("11st.plen");
            logpath = properties.getProperty("log.path");
            errorcaturepath = properties.getProperty("errorcature.path");
            logandimgzip = properties.getProperty("log.img.zip");
            mp4path = properties.getProperty("mp4.path");
        }
    }


    public static String extractIssueKeyFromResponse(String response) {
        // 응답 JSON에서 "key" 값을 추출하는 로직
        // 예: {"id":"10001", "key":"SCRUM-123", "self":"https://..."}

        // 예시로 간단하게 key 추출 (실제 응답은 더 복잡할 수 있음)
        String key = null;
        int keyStart = response.indexOf("\"key\":\"") + 7;
        int keyEnd = response.indexOf("\"", keyStart);
        if (keyStart != -1 && keyEnd != -1) {
            key = response.substring(keyStart, keyEnd);
        }
        return key;

    }

    //  jira 최근 이슈 jql 로 찾기
    public static String findrecentissue(String projectKey, String jiraApiToken, String username)throws Exception{

        String jql = "project = " + projectKey  + " AND issuetype = 'Test Plen' ORDER BY created DESC";
        String searchUrl = JIRA_URL + "/rest/api/3/search?jql=" + URLEncoder.encode(jql, StandardCharsets.UTF_8);
        System.out.println(searchUrl);

        HttpURLConnection connection = AndroidManager.connect(searchUrl, "GET", username, jiraApiToken );

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream is = connection.getInputStream()) {
                String response = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray issues = jsonResponse.getJSONArray("issues");

                if (issues.length() > 0) {
                    String issueKey = issues.getJSONObject(0).getString("key");
                    System.out.println("최근 생성된 Issue Key: " + issueKey);
                    return issueKey;
                } else {
                    System.out.println("해당 프로젝트에 테스트 실행 이슈가 없습니다.");
                    return null;
                }
            }
        } else {
            System.out.println("Jira 이슈 검색 실패. 응답 코드: " + responseCode);
            return null;
        }
    }

//testplen key 생성

    public static String Testpelncreate( String jiraUrl, String username, String apiToken) throws Exception {

        String summary = "Test issue summary";
        String description = "Description for the test issue";


        String jsonPayload = "{"
                + "\"fields\": {"
                + "\"project\": { \"key\": \"TW\" },"
                + "\"summary\": \"" + summary + "\","
                + "\"description\": \"" + description + "\","
                + "\"issuetype\": { \"name\": \"Test Plen\" }"
                + "}"
                + "}";


        System.out.println("json페이로드: " + jsonPayload);
        // HTTP 연결 설정
        URL url = new URL(jiraUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + apiToken).getBytes()));
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // 요청 본문 전송
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // 응답 확인
        int responseCode = connection.getResponseCode();
        System.out.println("Jira Issue Creation Response Code: " + responseCode);


        // 응답 본문 처리
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            // 이슈 생성이 성공적으로 이루어졌다면, 응답 본문을 통해 이슈 키를 추출
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                System.out.println("진입 ㅊㅋ:");
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // 예시로 JSON 응답에서 "key" 값을 추출
                String issueKey = extractIssueKeyFromResponse(response.toString());
                System.out.println("이슈 키 잘 생성되나 가자미 테스트:" + issueKey);

//                XrayReportUploader.setLinkissuekey(issueKey);

                return issueKey;
            }
        } else {
            System.out.println("Failed to create issue.");
            return null;
        }
    }



    //update isuue
    public static void update_summary_description(String issueKey, String summary, String jiraApiToken, String username) throws Exception {
        // Jira URL 설정
        String url = JIRA_URL + "/rest/api/3/issue/" + issueKey;
        HttpURLConnection connection = AndroidManager.connect(url, "PUT", username, jiraApiToken);

        // Jira에서 기존 issue 정보를 가져와서 description 읽기
        String existingDescription = getExistingDescription(issueKey, jiraApiToken, username);
        System.out.println("Existing Description: " + existingDescription);

        // 새로운 cucumber 파일 읽기
        String jsonFilePath = "target/target_"+ dateOnly +"/cucumber_" + dateOnly + ".json";
//        String jsonFilePath = "target/target_2025-01-07/cucumber_2025-01-07.json";
        File jsonFile = new File(jsonFilePath);
        JsonNode jsonResults = objectMapper.readTree(jsonFile);

        // 기존 description이 JSON 객체일 수 있으므로 이를 String으로 변환 후 사용
        String description = generateScenarioReport(jsonResults, issueKey, existingDescription);

        // Jira Payload 생성 (summary와 description 포함)
        String jsonPayload = "{"
                + "\"fields\": {"
                + "\"summary\": \"" + summary + "\","
                + "\"description\": " + description  // 생성된 테이블을 description에 넣기
                + "}"
                + "}";

        System.out.println("jsonPayload:" + jsonPayload);

        // 요청 본문을 출력 스트림에 작성
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 응답 코드 확인
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println("이슈 이름 바꾸기 성공.");
        } else {
            System.out.println("이슈 이름 바꾸기 실패. Response code: " + responseCode);
            try (InputStream errorStream = connection.getErrorStream()) {
                String errorResponse = new String(errorStream.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Error Response: " + errorResponse);
            }
        }
    }




    // Jira에서 기존 Description을 가져오는 함수
    // Jira에서 기존 Description을 가져오는 함수
    private static String getExistingDescription(String issueKey, String jiraApiToken, String username) throws Exception {
        String url = JIRA_URL + "/rest/api/3/issue/" + issueKey;
        HttpURLConnection connection = AndroidManager.connect(url, "GET", username, jiraApiToken);

        // 요청을 보낸 후 응답을 처리
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = connection.getInputStream()) {
                String response = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                // 기존 description을 추출
                JSONObject jsonResponse = new JSONObject(response);
                JSONObject fields = jsonResponse.getJSONObject("fields");

                // description이 JSON 형식일 경우, content가 JSONArray로 포함됨
                if (fields.has("description")) {
                    Object descriptionObj = fields.get("description");
                    System.out.println("가져온 설명: " + descriptionObj);

                    if (descriptionObj instanceof JSONObject) {
                        // description이 JSONObject일 경우, content 배열을 반환
                        JSONObject descriptionJson = (JSONObject) descriptionObj;
                        System.out.println("가져온 content 배열: " + descriptionJson.getJSONArray("content").toString());
                        return descriptionJson.getJSONArray("content").toString();  // JSONArray로 반환
                    } else if (descriptionObj instanceof String) {
                        // description이 단순 문자열일 경우
                        return (String) descriptionObj;
                    }
                }
                return "";  // description이 없을 경우 빈 문자열 반환
            }
        } else {
            throw new Exception("Error fetching issue details, Response code: " + responseCode);
        }
    }





    //테스트 결과 테스트 플랜하고 링크하기
    public static void LinkTeatPlen(String executKey,String testplenKey, String jiraApiToken, String username )throws Exception {
        String TestPlenurl = JIRA_URL + "/rest/api/3/issueLink";

        System.out.println("테스트 플랜 주소값: " + TestPlenurl);
        System.out.println("테스트 결과 키값" +  executKey);
        System.out.println("테스트 플랜 키값: " +testplenKey);
        getissue(executKey, jiraApiToken, username);
        getissue(testplenKey, jiraApiToken, username);



        HttpURLConnection connection = AndroidManager.connect(TestPlenurl, "POST", username, jiraApiToken);

        String jsonPayload = "{"
                + "\"type\": {"
                + "\"name\": \"Tests\""
                + "},"
                + "\"inwardIssue\": {"
                + "\"key\": \"" + executKey + "\""
                + "},"
                + "\"outwardIssue\": {"
                + "\"key\": \"" + testplenKey + "\""
                + "}"
                + "}";

        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println( testplenKey + "에 링크 성공");
        } else {
            System.out.println("실패. Response code: " + responseCode);
            try (InputStream errorStream = connection.getErrorStream()) {
                String errorResponse = new String(errorStream.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Error Response: " + errorResponse);
            }
        }
    }


    //getissue
    public static void getissue(String issueKey, String jiraApiToken, String username)throws Exception {

        String geturl = JIRA_URL + "/rest/api/3/issue/" + issueKey;

        HttpURLConnection connection = AndroidManager.connect(geturl, "GET", username, jiraApiToken);
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
//                System.out.println("getissue Response: " + response);

            }
        } else {
            System.out.println("Failed: HTTP error code : " + responseCode);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                System.out.println("Error Response: " + errorResponse);
            }
        }
    }

    // Cucumber JSON 결과를 Xray에 업로드하는 메서드
    public static void uploadTestReport(String cucumberJsonFilePath) throws Exception {
        // Cucumber JSON 파일을 읽기
        File cucumberJsonFile = new File(cucumberJsonFilePath);
        System.out.println("cucumber 위치: "+ cucumberJsonFilePath);
        byte[] cucumberJsonBytes = Files.readAllBytes(cucumberJsonFile.toPath());

// HTTP 연결 설정
        URL url = new URL(Xray_API_URL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + XRAY_API_TOKEN);

        // Bearer Token으로 인증
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // JSON 데이터를 요청 본문에 추가
        try (OutputStream os = connection.getOutputStream()) {
            os.write(cucumberJsonBytes);
            System.out.println("JSON data: " + new String(cucumberJsonBytes, StandardCharsets.UTF_8));


        }

        // 응답 확인
        int responseCode = connection.getResponseCode();
        System.out.println("Xray Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Report uploaded successfully.");

        } else {
            System.out.println("Failed to upload report to Xray.");
        }

        //link 하기
    }



    public static void TestresultReport(String[] args) {
        try {
            // Cucumber JSON 파일 경로와 Jira 이슈 키를 전달
            String resourece = "src/main/resources/application.properties";
            String projectKey = "TW";

            loadProperties(resourece);

            String cucumberJsonFilePath = "target/cucumber.json";  // Cucumber JSON 파일 경로
//          String issuekey = Testpelncreate(JIRA_URL, USERNAME, JIRA_TOKEN);
//            uploadTestReport(cucumberJsonFilePath);
//            String issuekey =  findrecentissue(projectKey, JIRA_TOKEN, USERNAME);
            String issuekey = "TW-374";
            String new_summary = "11st test " + "[" + formatdate + "]";



            System.out.println("새로운 제목 : " + new_summary);
            update_summary_description(issuekey,new_summary,JIRA_TOKEN,USERNAME);
            captureandlog(issuekey,USERNAME,JIRA_TOKEN,logpath,errorcaturepath, mp4path);
            LinkTeatPlen(issuekey, oneonetestPlen, JIRA_TOKEN, USERNAME );
            //oneonetestPlen 은 testplen 원본 키
            System.out.println(cucumberJsonFilePath);
            System.out.println("Xray report 테스트 정상 실행");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //log 및 캡쳐 사진 첨부

    public static void zipFiles(File[] files, String zipFileName) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            byte[] buffer = new byte[1024];
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    // Jira에 파일 첨부 메서드
    public static void captureandlog(String issueKey, String username, String jiraApiToken, String logDirPath, String pngDirPath, String mp4DirPath) throws Exception {
        String url = JIRA_URL + "/rest/api/3/issue/" + issueKey + "/attachments";
        System.out.println("첨부파일 url" + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + jiraApiToken).getBytes()));
        connection.setRequestProperty("X-Atlassian-Token", "no-check");  //xsrf 우화
        String boundary = UUID.randomUUID().toString();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setDoOutput(true);

        // .log와 .png 파일 리스트 가져오기
        File[] logFiles = new File(logDirPath).listFiles((dir, name) -> name.endsWith(".log"));
        File[] pngFiles = new File(pngDirPath).listFiles((dir, name) -> name.endsWith(".png"));
        File[] mp4Files = new File(mp4DirPath).listFiles((dir, name) -> name.endsWith(".mp4"));

        // 파일 리스트가 존재하면 압축 생성
        if (logFiles != null || pngFiles != null || mp4Files != null) {
            String zipFilePath = "error_logs_mp4_images.zip"; // 압축 파일 경로

            // 파일을 압축
            File tempZipFile = new File(zipFilePath);
            try {
                zipFiles(concatenateFiles(logFiles, pngFiles, mp4Files), zipFilePath); // 두 종류 파일을 하나로 합쳐서 압축
            } catch (IOException e) {
                System.out.println("파일 압축 중 오류 발생: " + e.getMessage());
                return;
            }

            // 파일을 첨부하는 부분
            try (OutputStream os = connection.getOutputStream()) {
                os.write(("--" + boundary + "\r\n").getBytes());
                os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + tempZipFile.getName() + "\"\r\n").getBytes());
                os.write(("Content-Type: application/zip\r\n").getBytes());
                os.write(("Content-Transfer-Encoding: binary\r\n\r\n").getBytes());

                byte[] fileBytes = Files.readAllBytes(tempZipFile.toPath());
                os.write(fileBytes);
                os.write(("\r\n--" + boundary + "--\r\n").getBytes());
            }

            // 서버 응답 처리
            int responseCode = connection.getResponseCode();
            System.out.println("jira에 압축파일 전송 성공: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("압축 파일 전송 성공");
            } else {
                System.out.println("파일 첨부 실패.");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    System.out.println("Error Response: " + errorResponse);
                }
            }
        } else {
            System.out.println("로그 파일이나 이미지 파일이 없습니다.");
        }
    }

    // .log와 .png 파일들을 하나의 배열로 합침
    public static File[] concatenateFiles(File[] logFiles, File[] pngFiles, File[] mp4Files) {
        int logCount = logFiles == null ? 0 : logFiles.length;
        int pngCount = pngFiles == null ? 0 : pngFiles.length;
        int mp4Count = mp4Files == null ? 0 : mp4Files.length;
        File[] allFiles = new File[logCount + pngCount + mp4Count];

        if (logFiles != null) {
            System.arraycopy(logFiles, 0, allFiles, 0, logCount);
        }
        if (pngFiles != null) {
            System.arraycopy(pngFiles, 0, allFiles, logCount, pngCount);
        }
        if (mp4Files != null) {
            System.arraycopy(mp4Files, 0, allFiles, logCount + pngCount, mp4Count);
        }

        return allFiles;
    }

    //삭제 예정


    public static String generateScenarioReport(JsonNode jsonResults, String issueKey, String existingDescription) {
        int countFailed = 0;
        int existingFailed = 0;

        // 결과를 담을 JSON 객체
        JSONObject docContent = new JSONObject();
        JSONArray content = new JSONArray();

        JSONArray tableContent = new JSONArray();

        // 기존 데이터를 테이블에 추가
        if (!existingDescription.isEmpty()) {
            try {
                JSONArray existingJsonArray = new JSONArray(existingDescription);

                // 기존 실패 항목만 추가
                if (existingJsonArray.length() > 1) {
                    JSONArray existingResultContent = existingJsonArray.getJSONObject(0).getJSONArray("content");
                    JSONArray existingTableContent = existingJsonArray.getJSONObject(1).getJSONArray("content");
                    for (int i = 0; i < existingTableContent.length(); i++) {
                        JSONObject row = existingTableContent.getJSONObject(i);
                        if (row.toString().contains("⛔ Failed")) {
                            tableContent.put(row);
                        }
                    }

                    // 기존 실패 합계 추출
                    JSONArray failedRow = existingResultContent.getJSONObject(0)
                            .getJSONArray("content")
                            .getJSONObject(2)
                            .getJSONArray("content");
                    JSONObject failedObj = failedRow.getJSONObject(0)
                            .getJSONArray("content")
                            .getJSONObject(0);

                    existingFailed = Integer.parseInt(failedObj.getString("text"));
                }
            } catch (Exception e) {
                System.err.println("Error reading existing description: " + e.getMessage());
            }
        }

        // 새로운 테스트 결과 처리 (실패 항목만)
        Map<String, Integer> dateFailCountMap = new HashMap<>(); // 날짜별 실패 횟수를 기록

        for (JsonNode feature : jsonResults) {
            for (JsonNode scenario : feature.get("elements")) {
                String startTime = scenario.get("start_timestamp").asText().split("T")[0]; // 날짜만 추출
                String scenarioName = scenario.get("name").asText();
                boolean isFailed = false;

                for (JsonNode step : scenario.get("steps")) {
                    String stepStatus = step.get("result").get("status").asText();
                    if ("failed".equalsIgnoreCase(stepStatus)) {
                        isFailed = true;
                        countFailed++;
                        break;
                    }
                }

                if (isFailed) {
                    // 날짜별 실패 횟수 기록
                    dateFailCountMap.put(startTime, dateFailCountMap.getOrDefault(startTime, 0) + 1);
                }
            }
        }

        // 날짜별 실패 횟수를 한 번만 추가
        for (Map.Entry<String, Integer> entry : dateFailCountMap.entrySet()) {
            JSONArray row = new JSONArray();
            row.put(createTableCell(entry.getKey(), null)); // 날짜
            row.put(createTableCell("⛔ Failed", "#d32f2f")); // 결과
            row.put(createTableCell(String.valueOf(entry.getValue()), null)); // 실패한 테스트 수
            tableContent.put(createTableRow(row));
        }

        // 합계 테이블 생성
        int totalFailed = existingFailed + countFailed;
        JSONArray footerRow = new JSONArray();
        footerRow.put(createTableCell("합계", null));
        footerRow.put(createTableCell("Failed", null));
        footerRow.put(createTableCell(String.valueOf(totalFailed), "#d32f2f"));

        JSONArray resultTableContent = new JSONArray();
        resultTableContent.put(createTableRow(footerRow));

        // 합계 테이블 추가
        JSONObject resultTable = new JSONObject();
        resultTable.put("type", "table");
        resultTable.put("attrs", new JSONObject()
                .put("isNumberColumnEnabled", false)
                .put("layout", "center")
                .put("width", 900));
        resultTable.put("content", resultTableContent);

        // 실패 항목 테이블 추가
        JSONObject table = new JSONObject();
        table.put("type", "table");
        table.put("attrs", new JSONObject()
                .put("isNumberColumnEnabled", false)
                .put("layout", "center")
                .put("width", 900));
        table.put("content", tableContent);

        // 문서 작성
        content.put(resultTable); // 합계 테이블
        content.put(table);       // 실패 항목 테이블

        docContent.put("type", "doc");
        docContent.put("version", 1);
        docContent.put("content", content);

        return docContent.toString();
    }






    // 테이블 셀 생성
    private static JSONObject createTableCell(String text, String color) {
        JSONObject cell = new JSONObject();
        cell.put("type", "tableCell");
        cell.put("content", new JSONArray()
                .put(new JSONObject().put("type", "paragraph").put("content", new JSONArray()
                        .put(new JSONObject().put("type", "text").put("text", text)
                                .put("marks", color != null ? new JSONArray()
                                        .put(new JSONObject().put("type", "textColor")
                                                .put("attrs", new JSONObject().put("color", color)))
                                        : null)))));
        return cell;
    }

    // 테이블 행 생성
    private static JSONObject createTableRow(JSONArray rowContent) {
        JSONObject row = new JSONObject();
        row.put("type", "tableRow");
        row.put("content", rowContent);
        return row;
    }



    //


    public static void main(String[] args) {
        try {

            TestresultReport(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}