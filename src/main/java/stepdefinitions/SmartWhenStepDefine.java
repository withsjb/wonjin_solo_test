package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AndroidManager;
import utils.AppProperty;
import utils.Constant;
import utils.Utils;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utils.AndroidManager.log;

public class SmartWhenStepDefine {

    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * 홈 화면 > 좌측 상단 웅진 스마트올 버튼 클릭
     * */
    @Given("웅진 스마트올 버튼 클릭")
    public void 웅진스마트올버튼클릭() {
        log.info("홈 > 웅진 스마트올 버튼 클릭");
        try {
            //웅진 북클럽 영역에서 클릭하는 경우,
            WebElement smartAll = AndroidManager.getElementById(Constant.웅진스마트올_id);
            if (smartAll.isDisplayed()) {
                smartAll.click();
                try {
                    // 이벤트 팝업
                    WebElement event = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnEventClose");
                    if (event.isDisplayed()) {
                        log.info("안내 팝업 나가기");
                        event.click();
                        return;
                    }
                }catch (Exception ie){}
            }

        } catch (Exception e) {
            try {
                //웅진 스마트올 초등 영역에서 클릭하는 경우,
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rlt2020Smart").click();
            }catch (Exception e1){
                // 키즈화면에서 클릭하는 경우
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/imgSmartallKids").click();
            }
        }
        try {
            WebElement info = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/fl_info");
            if (info.isDisplayed()){
                log.info("스마트올 안내 화면 나가기");
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/info_btn_exit").click();
            }
        }catch (Exception ignored){
            try {
                // 안내 팝업
                WebElement event = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnEventClose");
                if (event.isDisplayed()) {
                    log.info("안내 팝업 나가기");
                    event.click();
                }
            }catch (Exception ie){}
        }
    }

    @And("스마트올 학습캘린더 클릭")
    public void 스마트올학습캘린더클릭() throws InterruptedException {
        log.info("스마트올 학습캘린더 클릭");
        TimeUnit.SECONDS.sleep(2);
        try {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnCalEle").click();
        } catch (Exception e) {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnCalendar").click();
        }
    }


    @And("학습캘린더 학년 변경 > {string}")
    public void 학습캘린더학년변경(String year) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lldropChangeClass").click(); // 학년 드롭다운 클릭
        ChangelearningGrade(year);  // 학습 캘린더 변경할 학년 선택
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnChange").click();// 변경 버튼 클릭
        TimeUnit.SECONDS.sleep(3);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnExit").click();   // 학습 캘린더 나가기
        TimeUnit.SECONDS.sleep(5);
    }

    // 학습 캘린더 변경할 학년 선택
     public void ChangelearningGrade(String year) {
        try {
            log.info("스마트올 학습캘린더 학년 {} 변경", year);
            int idx = 0;
            switch (year) {
                case "4세":
                    idx = 0;
                    break;
                case "1단계":
                    idx = 1;
                    break;
                case "2단계":
                    idx = 2;
                    break;
                case "예비초":
                    idx = 3;
                    break;
                case "1학년":
                    idx = 4;
                    break;
                case "2학년":
                    idx = 5;
                    break;
                case "3학년":
                    idx = 6;
                    break;
                case "4학년":
                    idx = 7;
                    break;
                case "5학년":
                    idx = 8;
                    break;
                case "6학년":
                    idx = 9;
                    break;
            }

            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView")
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/root")).get(idx).click();
            TimeUnit.SECONDS.sleep(2);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("학습시작하기 처음부터 클릭")
    public void 학습시작하기처음부터클릭() {
        try {
            log.info("학습시작하기 처음부터 클릭");
            WebElement alertMsg;
            try {
                alertMsg = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvTitle");
            } catch (Exception e) {
                return;
            }
            if (alertMsg.isDisplayed() && alertMsg.getText().contains("학습 시작하기")) {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnLeft").click();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 웅진 스마트올 - {string} 서브메뉴 클릭
     */
    @When("웅진 스마트올 - {string} 서브메뉴 클릭")
    public void 웅진스마트올서브메뉴클릭(String menu) {
        try {
            log.info("홈 > 웅진 스마트올 > 서브메뉴 {} 클릭", menu);

            //서브메뉴 클릭_id 추가23.08.23
            String accessibilityId = "";
            switch (menu) {
                case "오늘의 학습":
                    accessibilityId = "오늘의 학습";
                    break;
                case "AI 학습센터":
                    accessibilityId = "AI 학습센터";
                    break;
                case "ALL도서관":
                    accessibilityId = "ALL도서관";
                    break;
                case "초등 포털":
                    accessibilityId = "초등 포털";
                    break;
                case "전체과목":
                    accessibilityId = "전체과목";
                    break;
                case "라이브러리":
                    accessibilityId = "라이브러리";
                    break;
                case "스타샵":
                    accessibilityId = "스타샵";
                    break;
                case "올링고 번역":
                    accessibilityId = "올링고 번역";
                    break;
                case "선생님이랑":
                    accessibilityId = "선생님이랑";
                    break;
                case "설정":
                    accessibilityId = "설정";
                    break;
            }

            AndroidManager.getElementByAccessibilityId(accessibilityId).click();
            TimeUnit.SECONDS.sleep(2);
            try {
                // 안내 팝업 >> 이벤트 팝업으로 특정 기간 동안 생겼다가 사라짐
                WebElement event = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnEventClose");
                if (event.isDisplayed()) {
                    log.info("안내 팝업 나가기");
                    event.click();
                }
            }catch (Exception ie){}

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 웅진 스마트올 AI 학습센터 {string} 서브메뉴 클릭
     */
    @When("웅진 스마트올 AI 학습센터 {string} 서브메뉴 클릭")
    public void 웅진스마트올AI학습센터서브메뉴클릭(String menu) {
        try {
            log.info("홈 > 웅진 스마트올 > 서브메뉴 {} 클릭", menu);
            WebElement parent = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/frmBottomLayout");
            parent.findElement(By.id("com.wjthinkbig.mlauncher2:id/llMenu"))
                    .findElement(By.xpath("//*[@text='" + menu + "']"))
                    .click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("웅진 스마트올 AI 학습센터 독서 {string} 클릭")
    public void 웅진스마트올ai학습센터독서클릭(String menu) {
        try {
            log.info("웅진 스마트올 AI 학습센터 독서 {} 클릭", menu);
            // 화면 스와이프
            TimeUnit.SECONDS.sleep(3);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            String rID = "";
            switch (menu) {
                case "독서 감상문 보기":
                    rID = "com.wjthinkbig.mlauncher2:id/mustRead_reviewbtn_vg";
                    break;
                case "교과서 독심술":
                    rID = "com.wjthinkbig.mlauncher2:id/mustRead_bottomBtnArea_1_btn";
                    break;
                case "초등 필독서":
                    rID = "com.wjthinkbig.mlauncher2:id/mustRead_bottomBtnArea_2_btn";
                    break;
                case "초등 교양서":
                    rID = "com.wjthinkbig.mlauncher2:id/mustRead_bottomBtnArea_3_btn";
                    break;
                case "BEARPORT":
                    rID = "com.wjthinkbig.mlauncher2:id/premium_bottomBtnArea_1_btn";
                    break;
                case "프리미엄 원서 마스터":
                    rID = "com.wjthinkbig.mlauncher2:id/premium_bottomBtnArea_2_btn";
                    break;
                case "수준별 영어 도서관":
                    rID = "com.wjthinkbig.mlauncher2:id/premium_bottomBtnArea_3_btn";
                    break;
                case "Disney":
                    rID = "com.wjthinkbig.mlauncher2:id/premium_bottomBtnArea_4_btn";
                    break;
            }
            AndroidManager.getElementById(rID).click();
            TimeUnit.SECONDS.sleep(2);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("초등포털 인기 영역 클릭")
    public void 초등포털인기영역클릭() throws InterruptedException {
        //화면 하단으로 이동
        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);

        // UI 확인
        boolean isDisplayedContents =
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ivThumbnail").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvPopularBookName").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvPopularTitle").isDisplayed();
        assertTrue("웅진 스마트올 초등 포털 인기 영역 UI 확인 되지 않습니다.", isDisplayedContents);

        log.info("초틍 포털 인기 영역 UI 확인 및 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layoutPopular").click();
    }

    @And("초등포털 퀴즈풀기 클릭")
    public void 초등포털퀴즈클릭() {
        log.info("초등포털 퀴즈풀기 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnQuestion").click();
    }

    @And("초등포털 영어TV 클릭")
    public void 초등포털영어TV클릭() throws InterruptedException {
        //화면 하단으로 이동
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        log.info("초등포털 영어TV 클릭");

        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layoutEnglishTv").click();

    }

    @And("초등포털 3분_회화 클릭")
    public void 초등포털분회화클릭() {
        log.info("초등포털 3분_회화 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnEnglish1MinDetail").click();

    }

    @And("초등포털 우리끼리 투표 클릭")
    public void 초등포털우리끼리투표클릭() {
        log.info("초등포털 우리끼리 투표 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ivVoting").click();

    }

    @And("초등포털 체험의 발견 클릭")
    public void 초등포털체험의발견클릭() {
        log.info("초등포털 체험의 발견 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layoutExperience").click();

    }

    @And("초등포털 그람책 클릭")
    public void 초등포털그람책클릭() {
        log.info("초등포털 그람책 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layoutBook").click();
    }

    @And("초등포털 직업영역 클릭")
    public void 초등포털직업영역클릭() {
        log.info("초등포털 직업영역 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layoutJob").click();
    }

    @Given("전체과목 버튼 클릭")
    public void 전체과목버튼클릭() {
        try {
            try {
                log.info("전체과목 버튼 클릭");
                WebElement element = AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"전체과목\"]/android.widget.TextView");
                if (element.isDisplayed()) {
                    TimeUnit.SECONDS.sleep(2);
                    element.click();
                }
            }catch (Exception e){
                //웅진 북클럽 영역에서 클릭하는 경우,
                TimeUnit.SECONDS.sleep(1);
                웅진스마트올버튼클릭();
                log.info("Exception 전체과목 버튼 클릭");
                WebElement element = AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"전체과목\"]/android.widget.TextView");
                if (element.isDisplayed()) {
                    TimeUnit.SECONDS.sleep(1);
                    element.click();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("{int}학년 버튼 클릭")
    public void 학년버튼클릭(int grade) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        try {
            // 예비초 화면이면 초등1-6학년 클릭 위해 초등 버튼 클릭하고 넘어가줘야 함_24.06월 정책 변경{UI}
            WebElement kids_year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_ele");
            if (kids_year.isDisplayed()) {
                log.info("초등 버튼 클릭");
                kids_year.click();
            }
            TimeUnit.SECONDS.sleep(2);

            WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/tab_title", grade);
            log.info("초등버튼 클릭 후, {}학년 버튼 클릭", grade);
            element.click();
        } catch (Exception e) {
            try {
                WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/tab_title", grade);
                log.info("{}학년 버튼 클릭", grade);
                element.click();
            }catch (Exception ignored){
                log.info("{}학년 버튼 클릭", grade);
            }
        }
    }


    @When("전체과목 {string} 클릭")
    @And("키즈 바로가기 {string} 클릭")
    public void 학습플러스클릭(String arg0) {
        try {
            log.info("{} 클릭", arg0);
            WebElement element;
            TimeUnit.SECONDS.sleep(5);
            if (arg0.equals("AI 추천")){
                AndroidManager.getElementByTextAfterSwipe(arg0);
                element = AndroidManager.getElementsByIdsAndIndex("com.wjthinkbig.mlauncher2:id/subject_list5", "com.wjthinkbig.mlauncher2:id/subject_list_item", 0);
                if (element.isDisplayed()) element.click();
            } else if (arg0.equals("수학 AI 추천")) {
                AndroidManager.getElementByTextAfterSwipe("AI 추천");
                element = AndroidManager.getElementsByIdsAndIndex("com.wjthinkbig.mlauncher2:id/subject_list4", "com.wjthinkbig.mlauncher2:id/subject_list_item", 0);
                if (element.isDisplayed()) element.click();
            }
            else {
                element = AndroidManager.getElementByTextAfterSwipe(arg0);
                if (element.isDisplayed()) element.click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("스마트올 콘텐츠 구성 버튼 클릭")
    public void 스마트올콘텐츠구성버튼클릭() {
        String[] buttonIds =
                {"btn_library_set_composition", "compositionBtn", "bookInfoBtn", "musicCompositionBtn", "compositionBtn2"};
        for (String id : buttonIds) {
            log.info("{} 구성 버튼 클릭", id);
            try {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/" + id).click();
                return;
            } catch (Exception ignored) {
                log.info("{} 구성 버튼 클릭 실패, 다음 버튼 시도", id);
            }
        }
    }

    @And("세트 목록 {int}번째 클릭")
    public void 세트목록클릭(int num) {
        try {
            log.info("{} 번 클릭", num);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/library_set_recyclerview")
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/set_img")).get(num).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 강의보기선택(String menu) {
        try {
            log.info("{} 강의보기 선택", menu);
            String rID = "";
            switch (menu) {
                case "전체":
                    rID = "전체\n" +
                            "강의보기";
                    break;
                case "문제별":
                    rID = "문제별\n" +
                            "강의보기";
                    break;
                case "챌린지":
                    rID = "챌린지\n" +
                            "힌트보기";
                    break;
                default:
                    rID = menu;
                    break;
            }
            TimeUnit.SECONDS.sleep(3);
            // 해당 인덱스로 클릭하기
            AndroidManager.getElementByTextAfterSwipe(rID).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }
    @And("학습 안내 버튼 클릭")
    public void 학습안내버튼클릭() {
        log.info("학습안내 버튼 클릭");
        AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/btn_study_guide", 10).click();
    }

    @And("전체과목 내책장 {string} 메뉴 클릭")
    public void 전체과목내책장서브메뉴클릭(String tab) {
        try {
            log.info("{} 버튼 클릭", tab);
            TimeUnit.SECONDS.sleep(5);
            if (tab.equals("올 도서")) {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnTab1").click();
            } else if (tab.equals("투데이 도서")) {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnTab2").click();
            } else if (tab.equals("7세")) {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/filterRecycler")
                        .findElements(By.id("com.wjthinkbig.mlauncher2:id/filter_title")).get(0).click();
                TimeUnit.SECONDS.sleep(3);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("전체과목 독서력 {string} 서브메뉴 클릭")
    public void 전체과목독서력서브메뉴클릭(String menu) {
        try {
            log.info("{} 클릭", menu);
            AndroidManager.getElementByTextAfterSwipe(menu).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 검정교과서학년선택(int arg0) {
        try {
            log.info("학년 드롭다운 선택");
            TimeUnit.SECONDS.sleep(2);
            WebElement grade = AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/dropdownGrade");
            grade.click();
            TimeUnit.SECONDS.sleep(2);
            WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.examprep.questionviewer:id/txtDropdownOneRowText", arg0);
            if (element.isDisplayed()) {
                String num = element.getText();
                log.info("{}학년 버튼 클릭", num);
                element.click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 우리학교설정클릭() {
        try {
            log.info("우리학교설정 클릭");
            AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/btn_school_setting").click();
            log.info("학교 설정 팝업 확인");
            WebElement school = AndroidManager.getElementByTextAfterSwipe("우리 학교 이름");
            assert school != null;
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/imgClose").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }
    @And("상세정보 바로보기 클릭")
    public void 상세정보팝업창에서바로보기버튼클릭() {
        try {
            try {
                log.info("상세정보 팝업창에서 바로보기 버튼 클릭");
                TimeUnit.SECONDS.sleep(3);
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/downBtn").click();
            } catch (Exception e) {
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("{int}학년 전체과목 분수 행성 클릭")
    public void 학년전체과목분수행성클릭(int year) throws InterruptedException {
        WebElement element;
        TimeUnit.SECONDS.sleep(2);
        if (year <= 4) {
            // 3,4학년 교과명
            log.info("분수 행성 테라포밍 클릭");
            element = AndroidManager.getElementByTextAfterSwipe("분수 행성 테라포밍");
            if (element.isDisplayed()) element.click();
        } else {
            // 5,6학년 교과명
            log.info("분수 행성 디펜스 클릭");
            element = AndroidManager.getElementByTextAfterSwipe("분수 행성 디펜스");
            if (element.isDisplayed()) element.click();
        }
    }

    @When("키즈 과목 바로가기 클릭")
    public void 오늘의학습과목바로가기() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        // 바로가기 클릭
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lottieQuickBtn").click();
    }


    @And("키즈 {string} 바로가기 클릭")
    public void todayKidsSubClick(String sub) {
        try {
            int index = 1; // 기본값 설정
            switch (sub) {
                case "한글": index = 1; break;
                case "국어": index = 2; break;
                case "수학": index = 3; break;
                case "영어": index = 4; break;
                case "독서": index = 5; break;
                case "한자": index = 6; break;
            }
            TimeUnit.SECONDS.sleep(1);
            // 인덱스를 동적으로 XPath에 적용
            WebElement element = AndroidManager.getDriver()
                    .findElement(By.xpath("(//android.widget.LinearLayout[@resource-id='com.wjthinkbig.mlauncher2:id/root'])[" + index + "]"));
            element.click();
        } catch (NoSuchElementException e) {
            fail("Unable to find the element with the given XPath and index.");
        } catch (Exception e) {
            fail("Unexpected error: " + e.getMessage());
            System.exit(0);
        }
    }

    @And("스마트올 키즈 더보기 {string} 클릭")
    public void 스마트올키즈더보기버튼클릭(String menu) throws InterruptedException {
        log.info("스마트올 키즈 햄버거 버튼 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lnaMore").click();

        // 메뉴 클릭
        clickMoreMenu(menu);
    }


    // 더보기 메뉴 클릭 메서드
    private void clickMoreMenu(String menu) throws InterruptedException {
        // 메뉴 인덱스 가져오기
        int index = getMenuIndex(menu);
        TimeUnit.SECONDS.sleep(1);
        String deviceName = AppProperty.getInstance().getDeviceName();
        if ("T500".equals(deviceName) && "선생님이랑".equals(menu)) {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnClose").click();
            TimeUnit.SECONDS.sleep(3);
            return; // 아무 작업도 하지 않고 메서드 종료
        }
        //T500 일때 index 5 이상 이면 index -1
        if ("T500".equals(deviceName) && index > 5) {
            index -= 1;
        }
        // 해당 인덱스의 메뉴 항목 클릭
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/llMenuList")
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/root")).get(index).click();
        TimeUnit.SECONDS.sleep(3);
    }

    // 메뉴 이름에 해당하는 인덱스 반환 메서드
    private int getMenuIndex(String menu) {

        switch (menu) {
            case "스타샵":
                return 0;
            case "스티커판":
                return 1;
            case "학습 전체보기":
                return 2;
            case "스마드올 NEWS":
                return 3;
            case "올링고":
                return 4;
            case "선생님이랑":
                return 5;
            case "독서앨범":
                return 6;
            case "학교 공부 예습":
                return 7;
            default:
                return -1; // 유효하지 않은 메뉴일 경우 -1 반환
        }
    }

    @And("오늘의학습 학습캘린더 AI오답노트 클릭")
    public void 오늘의학습학습캘린더AI오답노트클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnNote").click();
    }

    @And("오늘의 학습 NEWS 클릭")
    public void 오늘의학습NEWS클릭() throws InterruptedException {
        log.info("오늘의 학습 NEWS(우측 신문 아이콘) 클릭");
        TimeUnit.SECONDS.sleep(2);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/smartAllNewsBanner").click();
    }
    @And("오늘의 학습 매타버스 클릭")
    public void 오늘의학습매타버스클릭() throws InterruptedException {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/clLeftBanner").click();
        TimeUnit.SECONDS.sleep(15);
    }


    @And("AI 학습센터 {string} 클릭")
    public void ai학습센터클릭(String menu) {
        try {
            log.info("AI 학습센터 {} 클릭", menu);
            String rID = "";
            switch (menu) {
                case "자녀의 학습 완료율":
                    rID = "com.wjthinkbig.mlauncher2:id/complete_rate_guide_iv";
                    break;
                case "단원평가 점수":
                    rID = "com.wjthinkbig.mlauncher2:id/unit_score_guide_iv";
                    break;
                case "나는 무슨 상을 받았을까":
                    rID = "com.wjthinkbig.mlauncher2:id/award_area_guide_iv";
                    break;
                case "스타샵":
                    rID = "com.wjthinkbig.mlauncher2:id/star_btn";
                    break;
                case "게임하러 가기":
                    rID = "com.wjthinkbig.mlauncher2:id/game_btn";
                    break;
                case "3분 회화 자세히 보기":
                    rID = "com.wjthinkbig.mlauncher2:id/btn_aicenter_eng_3min_detail";
                    break;
                case "AI 학습하기":
                    rID = "com.wjthinkbig.mlauncher2:id/btn_aicenter_eng_ailearn";
                    break;
                case "영어 진단 검사":
                    rID = "com.wjthinkbig.mlauncher2:id/btn_aicenter_eng_level_test";
                    break;
                case "올링고 영어 진단 검사":
                    rID = "com.wjthinkbig.mlauncher2:id/tv_check_english";
                    break;
            }
            TimeUnit.SECONDS.sleep(2);
            if (menu.equals("3분 회화 자세히 보기")) Utils.swipeScreen(Utils.Direction.UP);

            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById(rID).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("Listen and Repeat 버튼 클릭")
    public void listenAndRepeat버튼클릭() {
        try {
            WebElement element = AndroidManager.getElementByTextAfterSwipe("Listen and Repeat");
            if (element != null && element.isDisplayed()) {
                element.click();
            } else {
                // element가 한번에 클릭 안될 경우 처리
                element.click();
            }
        } catch (Exception e) {
            // 예외 처리
            AndroidManager.getElementByTextAfterSwipe("Listen and Repeat").click();
        }

    }

    @And("오늘의 학습 설정 버튼 클릭")
    public void 오늘의학습설정버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_set_current_study").click();
    }

    @And("원서 소개 버튼 클릭")
    public void 원서소개버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_book_introduce").click();
    }


    @And("스마트올 뒤로가기 버튼")
    public void 스마트올뒤로가기버튼() {
        try {
            try {
                WebElement b1 = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back");
                TimeUnit.SECONDS.sleep(5);
                b1.click();
            } catch (Exception e) {
                WebElement b2 = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn");
                TimeUnit.SECONDS.sleep(5);
                b2.click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("{int}학년 드롭다운 선택")
    public void 학년드롭다운선택(int idx) {
        try {
            log.info("학년 드롭다운 선택");
            TimeUnit.SECONDS.sleep(2);
            WebElement select = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class");
            select.click();
            TimeUnit.SECONDS.sleep(2);
            WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/txtDropdownOneRowText", idx);
            if (element.isDisplayed()) {
                String num = element.getText();
                log.info("{} 버튼 클릭", num);
                element.click();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void archieveSelector(int idx){
        try {
            log.info("학년 드롭다운 선택");
            TimeUnit.SECONDS.sleep(2);
            WebElement select = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_class");
            select.click();
            TimeUnit.SECONDS.sleep(2);
            WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/txtDropdownOneRowText", idx);
            if (element.isDisplayed()) {
                String num = element.getText();
                log.info("{} 버튼 클릭", num);
                element.click();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("{int}과목 드롭다운 선택")
    public void 과목드롭다운선택(String menu) {
        try {
            int idx = 0;
            switch (menu) {
                case "파닉스":
                case "국어":
                    idx = 0;
                    break;
                case "말하기":
                case "수학":
                    idx = 1;
                    break;
                case "쓰기":
                case "사회":
                case "바슬즐":
                case "봄여름가을겨울":
                    idx = 2;
                    break;
                case "읽기":
                case "과학":
                    idx = 3;
                    break;
                case "문법":
                    idx = 4;
                    break;
            }
            log.info("과목 드롭다운 선택");
            TimeUnit.SECONDS.sleep(2);
            WebElement select = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_subject");
            select.click();
            log.info("{}과목 버튼 클릭", menu);
            TimeUnit.SECONDS.sleep(2);
            WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/txtDropdownOneRowText", idx);
            if (element.isDisplayed()) element.click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("인포메이션 버튼 클릭")
    public void 인포메이션버튼클릭() {
        try {
            try {
                log.info("인포메이션 버튼 클릭");
                AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/btn_study_guide", 10).click();
            } catch (Exception e) {
                TimeUnit.SECONDS.sleep(3);
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_guide_layout").click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("기")
    public void 커리큘럼나가기() {
    }
}
