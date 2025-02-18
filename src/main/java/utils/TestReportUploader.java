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
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestReportUploader {

    static String dateOnly = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    static String JIRA_URL;

    static String USERNAME;
    static String JIRA_TOKEN;

    static String oneonetestPlen;

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

    // app.properties 에서 불러오기
    public static void loadProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);

            JIRA_URL = properties.getProperty("jira.base.url");
            JIRA_TOKEN = properties.getProperty("jira.api.token");
            USERNAME = properties.getProperty("username");
            oneonetestPlen = properties.getProperty("11st.plen");

        }
    }

    // jira 최근 이슈 jql 로 찾기
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

    // testplen key 생성
    public static String Testpelncreate( String jiraUrl, String username, String apiToken) throws Exception {

        String summary = "Test issue summary";
        String description = "Description for the test issue";

        String jsonPayload = "{"
                + "\"fields\": {"
                + "\"project\": { \"key\": \"WON\" },"
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
        connection.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + apiToken).getBytes()));
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
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                System.out.println("진입 ㅊㅋ:");
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                String issueKey = extractIssueKeyFromResponse(response.toString());
                System.out.println("이슈 키 잘 생성되나 가자미 테스트:" + issueKey);

                return issueKey;
            }
        } else {
            System.out.println("Failed to create issue.");
            return null;
        }
    }

    // update issue
    public static void update_summary_description(String issueKey, String summary, String jiraApiToken, String username) throws Exception {
        String url = JIRA_URL + "/rest/api/3/issue/" + issueKey;
        HttpURLConnection connection = AndroidManager.connect(url, "PUT", username, jiraApiToken);

        // 기존 issue 정보를 가져와서 description 읽기
        String existingDescription = getExistingDescription(issueKey, jiraApiToken, username);
        System.out.println("Existing Description: " + existingDescription);

        // 새로운 cucumber 파일 읽기
        String jsonFilePath = "target_" + dateOnly + "/cucumber_" + dateOnly + ".json";
        File jsonFile = new File(jsonFilePath);
        JsonNode jsonResults = objectMapper.readTree(jsonFile);

        // 기존 description이 JSON 객체일 수 있으므로 이를 String으로 변환 후 사용
        String description = generateScenarioReport(jsonResults, issueKey, existingDescription);

        // Jira Payload 생성 (summary와 description 포함)
        String jsonPayload = "{"
                + "\"fields\": {"
                + "\"summary\": \"" + summary + "\","
                + "\"description\": " + description
                + "}"
                + "}";

        System.out.println("jsonPayload:" + jsonPayload);

        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

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
    private static String getExistingDescription(String issueKey, String jiraApiToken, String username) throws Exception {
        String url = JIRA_URL + "/rest/api/3/issue/" + issueKey;
        HttpURLConnection connection = AndroidManager.connect(url, "GET", username, jiraApiToken);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = connection.getInputStream()) {
                String response = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                JSONObject jsonResponse = new JSONObject(response);
                JSONObject fields = jsonResponse.getJSONObject("fields");

                if (fields.has("description")) {
                    Object descriptionObj = fields.get("description");
                    System.out.println("가져온 설명: " + descriptionObj);

                    if (descriptionObj instanceof JSONObject) {
                        JSONObject descriptionJson = (JSONObject) descriptionObj;
                        System.out.println("가져온 content 배열: " + descriptionJson.getJSONArray("content").toString());
                        return descriptionJson.getJSONArray("content").toString();
                    } else if (descriptionObj instanceof String) {
                        return (String) descriptionObj;
                    }
                }
                return "";
            }
        } else {
            throw new Exception("Error fetching issue details, Response code: " + responseCode);
        }
    }

    // get issue
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

    public static void TestresultReport(String[] args) {
        try {
            String resourece = "src/main/resources/app.properties";
            String projectKey = "WON";

            loadProperties(resourece);

            String cucumberJsonFilePath = "target/cucumber.json";
            // String issuekey = Testpelncreate(JIRA_URL, USERNAME, JIRA_TOKEN);
            // uploadTestReport(cucumberJsonFilePath);
            // String issuekey = findrecentissue(projectKey, JIRA_TOKEN, USERNAME);
            String issuekey = "WON-1";
            String new_summary = "11st test " + "[" + formatdate + "]";

            System.out.println("새로운 제목 : " + new_summary);
            update_summary_description(issuekey, new_summary, JIRA_TOKEN, USERNAME);

            System.out.println(cucumberJsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 수정된 generateScenarioReport 메소드:
    // passed 대신 JSON에서 읽어온 전체 시나리오 갯수를 "전체 시나리오"로 표기함
    public static String generateScenarioReport(JsonNode jsonResults, String issueKey, String existingDescription) {
        int countPassed = 0;
        int countFailed = 0;
        int existingPassed = 0;
        int existingFailed = 0;

        // 결과를 담을 JSON 객체
        JSONObject docContent = new JSONObject();
        JSONArray content = new JSONArray();

        JSONArray tableContent = new JSONArray();
        JSONArray resultTableContent = new JSONArray(); // 합계만 포함할 새로운 테이블

        // 날짜 및 시나리오 정보 추출
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // 테이블 헤더는 한 번만 추가
        boolean isHeaderAdded = false;

        // 기존 데이터를 테이블에 추가
        if (!existingDescription.isEmpty()) {
            try {
                JSONArray existingJsonArray = new JSONArray(existingDescription);

                // 기존 테이블이 있을 경우 그 내용을 추가
                if (existingJsonArray.length() > 1) {
                    JSONArray existingTableContent = existingJsonArray.getJSONObject(1).getJSONArray("content");
                    System.out.println("existingTableContent : " + existingTableContent);
                    tableContent.putAll(existingTableContent);
                    isHeaderAdded = true;

                    // 기존 합계 값 추출 (실패 건수)
                    JSONArray FaiiledRow = existingJsonArray.getJSONObject(0).getJSONArray("content").getJSONObject(1).getJSONArray("content");
                    JSONObject Faiiledobja = FaiiledRow.getJSONObject(2);
                    JSONArray Faiiledarra = Faiiledobja.getJSONArray("content");
                    JSONObject Faiiledobjb = Faiiledarra.getJSONObject(0);
                    JSONArray Faiiledarrb = Faiiledobjb.getJSONArray("content");
                    JSONObject Failledobjc = Faiiledarrb.getJSONObject(0);

                    existingFailed = Integer.parseInt(Failledobjc.getString("text"));
                    System.out.println("existingPassed : " + existingPassed);
                }
            } catch (Exception e) {
                System.err.println("Error reading existing description: " + e.getMessage());
            }
        }

        // 새로운 테스트 결과 처리
        for (JsonNode feature : jsonResults) {
            for (JsonNode scenario : feature.get("elements")) {
                String startTime = scenario.get("start_timestamp").asText();
                String scenarioName = scenario.get("name").asText();
                String testResult = "✅ Passed";

                for (JsonNode step : scenario.get("steps")) {
                    String stepStatus = step.get("result").get("status").asText();
                    if ("failed".equalsIgnoreCase(stepStatus)) {
                        testResult = "⛔ Failed";
                        countFailed++;
                        break;
                    }
                }

                if ("✅ Passed".equals(testResult)) {
                    countPassed++;
                }

                // 테이블 헤더는 한 번만 추가
                if (!isHeaderAdded) {
                    JSONArray headerRow = new JSONArray();
                    headerRow.put(createTableCell("테스트 기간", null));
                    headerRow.put(createTableCell("시나리오", null));
                    headerRow.put(createTableCell("테스트 결과", null));
                    tableContent.put(createTableRow(headerRow));
                    isHeaderAdded = true;
                }

                // 날짜 포맷 처리
                try {
                    Date date = dateFormat.parse(startTime);
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

                    JSONArray row = new JSONArray();
                    row.put(createTableCell(formattedDate, null));
                    row.put(createTableCell(scenarioName, null));
                    row.put(createTableCell(testResult, "✅ Passed".equals(testResult) ? "#006644" : "#d32f2f"));
                    tableContent.put(createTableRow(row));
                } catch (Exception e) {
                    System.err.println("Invalid date format: " + startTime);
                }
            }
        }

        // 전체 시나리오 갯수 계산 (passed + failed)
        int totalScenario = countPassed + countFailed;
        // 기존 실패 값과 합산
        int totalFailed = existingFailed + countFailed;

        // 합계 테이블 업데이트: "전체 시나리오"로 표기
        JSONArray footerRow = new JSONArray();
        footerRow.put(createTableCell("합계", null));
        footerRow.put(createTableCell("전체 시나리오", null));
        footerRow.put(createTableCell(String.valueOf(totalScenario), "#006644"));
        resultTableContent.put(createTableRow(footerRow));

        footerRow = new JSONArray();
        footerRow.put(createTableCell("합계", null));
        footerRow.put(createTableCell("Failed", null));
        footerRow.put(createTableCell(String.valueOf(totalFailed), "#d32f2f"));
        resultTableContent.put(createTableRow(footerRow));

        // 두 번째 테이블 생성 (합계 전용)
        JSONObject resultTable = new JSONObject();
        resultTable.put("type", "table");
        resultTable.put("attrs", new JSONObject()
                .put("isNumberColumnEnabled", false)
                .put("layout", "center")
                .put("width", 900)
                .put("displayMode", "default"));
        resultTable.put("content", resultTableContent);

        // 첫 번째 테이블 생성 (기존 테스트 결과)
        JSONObject table = new JSONObject();
        table.put("type", "table");
        table.put("attrs", new JSONObject()
                .put("isNumberColumnEnabled", false)
                .put("layout", "center")
                .put("width", 900)
                .put("displayMode", "default"));
        table.put("content", tableContent);

        // 문서 내용에 두 테이블 추가
        content.put(resultTable);
        content.put(table);

        docContent.put("type", "doc");
        docContent.put("version", 1);
        docContent.put("content", content);

        return docContent.toString();
    }

    // 응답에서 이슈 키 추출
    public static String extractIssueKeyFromResponse(String response) {
        String key = null;
        int keyStart = response.indexOf("\"key\":\"") + 7;
        int keyEnd = response.indexOf("\"", keyStart);
        if (keyStart != -1 && keyEnd != -1) {
            key = response.substring(keyStart, keyEnd);
        }
        return key;
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

    public static void main(String[] args) {
        try {
            TestresultReport(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
