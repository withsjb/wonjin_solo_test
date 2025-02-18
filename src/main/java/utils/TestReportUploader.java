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




    static String JIRA_URL;

    static String USERNAME;
    static String JIRA_TOKEN;

    static String oneonetestPlen;

    static String IssueKey;



    private static ZonedDateTime koreantime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String formatdate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(koreantime);






    //app.properties 에서 불러오기
    public static void loadProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);


            JIRA_URL = properties.getProperty("jira.base.url");
            JIRA_TOKEN = properties.getProperty("jira.api.token");
            USERNAME = properties.getProperty("username");
            IssueKey = properties.getProperty("11st.plen");
        }
    }




    //update isuue
    public static void update_summary_description(String summary, String jiraApiToken, String username) throws Exception {
        // Jira URL 설정
        String url = JIRA_URL + "/rest/api/3/issue/" + IssueKey;
        HttpURLConnection connection = AndroidManager.connect(url, "PUT", username, jiraApiToken);

        // Jira에서 기존 issue 정보를 가져와서 description 읽기
        String existingDescription = getExistingDescription(IssueKey, jiraApiToken, username);
        System.out.println("Existing Description: " + existingDescription);

        // 새로운 cucumber 파일 읽기
        String jsonFilePath = "target/target_"+ dateOnly +"/cucumber_" + dateOnly + ".json";
//        String jsonFilePath = "target/target_2025-01-07/cucumber_2025-01-07.json";
        File jsonFile = new File(jsonFilePath);
        JsonNode jsonResults = objectMapper.readTree(jsonFile);

        // 기존 description이 JSON 객체일 수 있으므로 이를 String으로 변환 후 사용
        String description = generateScenarioReport(jsonResults, IssueKey, existingDescription);

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
        String url = JIRA_URL + "/rest/api/3/issue/" + IssueKey;
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








    public static void TestresultReport(String[] args) {
        try {
            // Cucumber JSON 파일 경로와 Jira 이슈 키를 전달
            String resourece = "src/main/resources/app.properties";
            String projectKey = "TW";

            loadProperties(resourece);

            String cucumberJsonFilePath = "target/cucumber.json";  // Cucumber JSON 파일 경로
//          String issuekey = Testpelncreate(JIRA_URL, USERNAME, JIRA_TOKEN);
//            uploadTestReport(cucumberJsonFilePath);
//            String issuekey =  findrecentissue(projectKey, JIRA_TOKEN, USERNAME);

            String new_summary = "11st test " + "[" + formatdate + "]";



            System.out.println("새로운 제목 : " + new_summary);
            update_summary_description(new_summary,JIRA_TOKEN,USERNAME);
            //oneonetestPlen 은 testplen 원본 키
            System.out.println(cucumberJsonFilePath);
            System.out.println("Xray report 테스트 정상 실행");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //log 및 캡쳐 사진 첨부






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