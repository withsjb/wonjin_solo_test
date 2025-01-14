package stepdefinitions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AndroidManager;
import utils.AppProperty;
import utils.Constant;
import utils.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static utils.AndroidManager.*;
import static utils.AndroidManager.log;
import static utils.Utils.touchSpecificCoordinates;

public class SmartThenStepDefine {

    private final Logger log = LoggerFactory.getLogger(getClass());

    SmartWhenStepDefine smartWhen = new SmartWhenStepDefine();

    /**
     * 웅진 스마트올 메뉴구성 확인
     */
    @Then("웅진 스마트올 메뉴구성 확인")
    public void 웅진스마트올메뉴구성확인() {
        try {
            log.info("웅진 스마트올 메뉴구성 확인");
            try {
                // 키즈화면인 경우
                WebElement kids = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/imgSmartallKids");
                if (kids.isDisplayed()) {

                    smartWhen.스마트올학습캘린더클릭();
                    if (Utils.getDeviceType().equals("SM-T583")) {
                        smartWhen.학습캘린더학년변경("4학년");
                    } else {
                        smartWhen.학습캘린더학년변경("3학년");
                    }
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (Exception e) {
                log.info("스마트올 초등 화면 입니다.");
            }

            //웅진 스마트올 화면구성 확인(accessibility id)
            boolean isDisplayedContents =
                    AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"오늘의 학습\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"AI 학습센터\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"초등 포털\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"전체과목\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"스타샵\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"검색\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.LinearLayout[@content-desc=\"올링고 번역\"]").isDisplayed();

            assertTrue("웅진 스마트올 메뉴구성이 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 웅진 스마트올 스타샵 실행 확인
     */
    @Then("웅진 스마트올 스타샵 실행 확인")
    public void 웅진스마트올스타샵실행확인() {
        try {
            log.info("웅진 스마트올 스타샵 실행 확인");
            //백그라운드 이미지, 내 별 현황 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"내 별 현황\"]/android.widget.Button").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"나의 목표\"]/android.widget.Button").isDisplayed();
            assertTrue("웅진 스마트올 스타샵 실행이 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 웅진 스마트올 올링고 번역 실행 확인
     */
    @Then("스마트올 키즈 더보기 올링고 확인")
    @Then("웅진 스마트올 올링고 번역 실행 확인")
    public void 웅진스마트올올링고번역실행확인() {
        try {
            log.info("웅진 스마트올 올링고 번역 실행 확인");

            //번역 버튼 /올링고 타이틀 이미지_ ui변경 24.01.15
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/llVoice").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/imgAllLingo").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/tvText").isDisplayed();

            assertTrue("웅진 스마트올 올링고 번역 실행이 확인되지 않습니다.", isDisplayedContents);

            //올링고 번역 창 닫기 id 변경_23.08.23
            AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/btnExit").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("오늘의 학습 {string} 변경 학습카드 확인")
    public void 오늘의학습학습캘린더변경확인(String year) {
        try {
            try {
                // 지구아이콘클릭안내 팝업
                WebElement event = getElementById("com.wjthinkbig.mlauncher2:id/btnEventClose");
                if (event.isDisplayed()) {
                    log.info("링고시티 안내 팝업 나가기");
                    event.click();
                }
            } catch (Exception ie) {
            }
            log.info("스마트올 학습캘린더 학년 {} 변경 확인", year);
            switch (year) {
                case "4세":
                case "1단계":
                case "2단계":
                case "예비초":
                    SmartAllGradeFirst(year);
                    break;
                case "1학년":
                case "2학년":
                case "3학년":
                case "4학년":
                case "5학년":
                case "6학년":
                    log.info("학년: " + year);
                    todayStudyYear(year);   // 학습 카드 클릭해서 실행 확인까지
                    break;
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
     * 스마트올 오늘의 학습
     * 키즈 화면 확인
     * */
    public void SmartAllGradeFirst(String year) {
        try {
            log.info("오늘의 학습 확인: {}", year);
            오늘의학습과목바로가기확인();
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/flTouchCenter").click();// 가운데 학습 클릭
            오늘의학습콘텐츠종료하기();
            TimeUnit.SECONDS.sleep(2);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 오늘의학습과목바로가기확인() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        // 바로가기 클릭
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lottieQuickBtn").click();
        // 과목 바로가기 아이콘 확인
        boolean contents = getElementById("com.wjthinkbig.mlauncher2:id/llQuickMenuItem").isDisplayed();
        assertTrue("스마트올 오늘의 학습 확인되지 않습니다.", contents);
        WebElement topMenu = getElementById("com.wjthinkbig.mlauncher2:id/llQuickMenuItem");
        List<WebElement> allTopMenus = topMenu.findElements(By.id("com.wjthinkbig.mlauncher2:id/imgIcon"));
        log.info("메뉴 {}개 확인", allTopMenus.size());
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnQuickMenuClose").click(); // 과목바로가기 닫기
    }

    @Then("스마트올 키즈 더보기 스타샵 확인")
    public void moreViewStarShop() {
        // 스타샵 메뉴 확인
        웅진스마트올스타샵실행확인();
        AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/btnExit").click();
    }

    // 더보기 메뉴 클릭 메서드
    private void clickMoreMenu(String menu) throws InterruptedException {
        // 메뉴 인덱스 가져오기
        int index = getMenuIndex(menu);
        TimeUnit.SECONDS.sleep(1);
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

    // 앱 종료 메서드
    private void exitApp(String exitButtonId) {
        // 종료 버튼 클릭
        AndroidManager.getElementById(exitButtonId).click();
    }


    @Then("스마트올 키즈 더보기 스마트올 NEWS 확인")
    @Then("오늘의 학습 NEWS 화면 진입 확인")
    public void todayNEWS() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(AndroidManager.getDriver(), Duration.ofSeconds(30));

        WebElement btn1 = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.view.View[@content-desc=\"전체\"]")));
        WebElement btn2 = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.view.View[@content-desc=\"초등\"]")));
        WebElement btn3 = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.view.View[@content-desc=\"키즈\"]")));

        // 전체와 초등 카테고리 통합
        for (String category : new String[]{"전체", "초등"}) {
            WebElement button = category.equals("전체") ? btn1 : btn2;
            button.click();
            TimeUnit.SECONDS.sleep(1);
            log.info("오늘의 학습 NEWS " + category + " 진입 확인");

            boolean isDisplayed = checkNewsContent(new String[]{
                    "순공 챌린지 이벤트 문해력 향상의 비밀은?",
                    "1학기 WSAT 응시 이벤트!",
                    "2학기 WSAT 응시 이벤트!"
            });
            assertTrue("오늘의 학습 NEWS " + category + " 화면 확인되지 않습니다.", isDisplayed);
        }

        // 키즈 카테고리
        btn3.click();
        TimeUnit.SECONDS.sleep(1);
        log.info("오늘의 학습 NEWS 키즈 진입 확인");

        boolean isDisplayed_btn3 = checkNewsContent(new String[]{
                "메타퍼피와 인생 샷 이벤트!",
                "메타버스 설맞이 독서 이벤트",
                "메타버스 크리스마스 이벤트"
        });
        assertTrue("오늘의 학습 NEWS 키즈 화면 확인되지 않습니다.", isDisplayed_btn3);

        // 나가기
        AndroidManager.getElementByText("back_button").click();

    }

    public boolean checkNewsContent(String[] expectedTexts) {
        try {
            List<WebElement> elements = getDriver().findElements(By.className("android.view.View"));

            for (String expectedText : expectedTexts) {
                boolean found = false;
                for (WebElement element : elements) {
                    if (element.getText().contains(expectedText)) {
                        found = true;
                        log.info("'" + expectedText + "' 텍스트 찾음");
                        break;
                    }
                }
                if (!found) {
                    log.info("'" + expectedText + "' 텍스트 찾지 못함");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("뉴스 콘텐츠 확인 중 오류 발생: ", e);
            return false;
        }
    }


    @Then("스마트올 키즈 더보기 학습 전체보기 확인")
    public void viewLearning() {
        try {
            log.info("스마트올 키즈 더보기 학습 전제 보기 확인");

            // 과목 리스트 등 구성요소 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ll_sitemap").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lottieBgTop").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ll_courses").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnTop").isDisplayed();

            assertTrue("스마트올 키즈 더보기 학습 전제 보기 확인 되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("스마트올 키즈 더보기 학교 공부 예습 확인")
    public void preparationStudy() {
        try {
            log.info("스마트올 키즈 더보기 학교 공부 예습 확인");
            assertEquals("학교 공부 예습", AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title").getText());

            // 과목 리스트 등 구성요소 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layout_tab_container").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_term").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/item_subject_progress").isDisplayed();

            assertTrue("스마트올 키즈 더보기 학교 공부 예습 확인 되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 화면구성 확인
     */
    @Then("스마트올 키즈 더보기 독서앨범 확인")
    @Then("독서앨범 화면구성 확인")
    public void 독서앨범화면구성확인() {
        try {
            log.info("독서앨범 화면구성 확인");

            //새소식/친구 작품 보기/내 작품 보기 탭 구성 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_news").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_friendswork").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_mywork").isDisplayed();

            assertTrue("독서앨범 화면구성이 확인되지 않습니다.", isDisplayedContents);
            TimeUnit.SECONDS.sleep(3);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("스마트올 키즈 더보기 선생님이랑 확인")
    @Then("웅진 스마트올 선생님이랑 실행 확인")
    public void 웅진스마트올선생님이랑실행확인() {
        try {
            log.info("웅진 스마트올 선생님이랑 실행 확인");
            String deviceName = AppProperty.getInstance().getDeviceName();


            if ("T500".equals(deviceName)) {

                return; // 아무 작업도 하지 않고 메서드 종료
            }else{
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/viewTitle_full").isDisplayed();
                assertTrue("웅진 스마트올 선생님이랑 실행이 확인되지 않습니다.", isDisplayedContents);
                // 선생님이랑 창 끄기
                AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/btnExit").click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
     * 스마트올 오늘의 학습
     * 초등 학습 카드 UI 확인
     * */
    @Then("오늘의 학습 {string} 변경 확인")
    public void todayStudy(String year) throws InterruptedException {
        // 첫 번째로 과목들을 확인하는 부분
        WebElement todayStudy = getElementById("com.wjthinkbig.mlauncher2:id/recyclerView");
        List<WebElement> allTextViews = todayStudy.findElements(By.id("com.wjthinkbig.mlauncher2:id/txtCourse"));
        log.info("{} 과목: [{}]개 확인", year, allTextViews.size());

        // 첫 번째 for 루프: 첫 스와이프 전 과목 확인
        for (int i = 0; i < allTextViews.size(); i++) {
            todayStudy = getElementsByIdsAndIndex(
                    "com.wjthinkbig.mlauncher2:id/recyclerView",
                    "com.wjthinkbig.mlauncher2:id/txtCourse", i);
            String subTitleText = todayStudy.getText();
            log.info("과목: [{}] 노출 확인", subTitleText);
            assertTrue("오늘의 학습 확인 되지 않습니다", todayStudy.isDisplayed());
        }

        // 스와이프 동작 수행
        log.info("오른쪽에서 왼쪽으로 스와이프 동작 수행");
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.LEFT);
//        Utils.dragSourceToTarget(1855, 557, 465, 557); // 스와이프 제스처 수행
        TimeUnit.SECONDS.sleep(1);

        // 스와이프 후 다시 과목 확인하는 부분
        todayStudy = getElementById("com.wjthinkbig.mlauncher2:id/recyclerView");
        allTextViews = todayStudy.findElements(By.id("com.wjthinkbig.mlauncher2:id/txtCourse"));

        // 두 번째 for 루프: 스와이프 후 다시 과목 확인
        for (int i = 0; i < allTextViews.size(); i++) {
            todayStudy = getElementsByIdsAndIndex(
                    "com.wjthinkbig.mlauncher2:id/recyclerView",
                    "com.wjthinkbig.mlauncher2:id/txtCourse", i);
            String subTitleText = todayStudy.getText();
            log.info("과목: [{}] 노출 확인", subTitleText);
            assertTrue("오늘의 학습 확인 되지 않습니다", todayStudy.isDisplayed());
        }
    }

    /*
     * 오늘의 학습 학습카드 클릭
     * 초등학교 - 1,2,3,4,5,6 학년
     *
     */
    public void clickTodayCard(int i) {
        try {
            WebElement recyclerView = getElementById("com.wjthinkbig.mlauncher2:id/recyclerView");
            WebElement study = recyclerView.findElements(By.id("com.wjthinkbig.mlauncher2:id/lnaCourseLayout")).get(i);

            log.info("읽어온" + i + "번째: " + study);

            if (i == 1) {
                log.info("겨울방학 특강 학습카드 클릭");
                study.click();
                return;
            }


            // 학습카드 텍스트 가져오기
            WebElement text = recyclerView
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/lnaCourseLayout"))
                    .get(i)
                    .findElement(By.id("com.wjthinkbig.mlauncher2:id/txtCourse"));

            log.info("학습카드 : {} 클릭", text.getText());

            // 다시 study 요소 가져오기 (StaleElement 방지)
            study = recyclerView.findElements(By.id("com.wjthinkbig.mlauncher2:id/lnaCourseLayout")).get(i);
            study.click();
            checkstartstudybtn();

            TimeUnit.SECONDS.sleep(2);
        } catch (NoSuchElementException e) {
            fail("Element not found");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    public void checkstartstudybtn() {
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

    /*
     * 오늘의 학습 첫번째 학습카드
     * 초등학교 - 1,2,3,4,5,6 학년
     *
     */
    public void checkTodayFirstCard() {
        // 모두의 문해력 제외 첫번째 카드 확인
        clickTodayCard(1);
        log.info("겨울방학 특강 학습카드 확인");
        log.info("checkTodayFirstCard");


        String card = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title").getText();
        assertEquals("겨울방학특강", card);

        String title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_guide").getText();
        assertEquals("영역별 단기 집중 겨울방학 특강", title);

        AndroidManager.getElementById(Constant.뒤로가기_id).click();
    }

    /*
     * 오늘의 학습 두번째 학습카드
     * 초등학교 - 1,2,3,4,5,6 학년
     *
     */
    public void checkTodaySecondCard() {
        // 모두의 문해력 제외 두번째 카드 확인
        clickTodayCard(2);
        // smartAll AI 동영상
        log.info("smartAll AI 탐구 학습 동영상 확인");
        log.info("checkTodaySecondCard");
        오늘의학습콘텐츠종료하기();
    }


    public void checkTodayoneyrarThirdCard() {
        // 모두의 문해력 제외 두번째 카드 확인
        clickTodayCard(3);
        오늘의학습콘텐츠종료하기();
        // smartAll AI 동영상
        log.info("영어 탐구 학습 동영상 확인");
        log.info("checkTodayoneyrarThirdCard");

    }


    /*
     * 오늘의 학습 세번째 학습카드
     * 초등학교 - 1,2,3,4,5,6 학년
     *
     */
    public void checkTodayThirdCard() {
        // 모두의 문해력 제외 세번째 카드 확인
        clickTodayCard(3);
        log.info("checkTodayThirdCard");
        log.info("WSAT 학력평가 학습카드 확인");
        // WSAT 학력평가
        String card = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title").getText();
        assertEquals("WSAT 스마트올 학력평가", card);

        boolean contents =
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_year").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_class").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_term").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_ai_report").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_guide").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_subject_list").isDisplayed();
        assertTrue("웅진 스마트올 오늘의 카드 WSAT 학력평가 화면구성이 확인되지 않습니다.", contents);

        AndroidManager.getElementById(Constant.뒤로가기_id).click();
    }

    /*
     * 오늘의 학습
     * 초등학교 - 1,2,3,4,5,6 학년
     * 학습 카드 0번~3번 제외한 과목 카드
     * 동영상 없이 바로 학습 시작하기 버튼 뜨는 학습
     * 처음 학습 시작시, 동영상 학습안내 노출됨 (자동화 시작전 확인 필요)
     *
     */
    // 24.12월 기준 1학년~5학년 모두 동일 (6학년 제외)
    // >> 학습 카드 4,5 존재 스와이프후, index기준 3,4
    public void checkTodayCard(int i) {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("화면 오른쪽으로 스와이프");
            Utils.swipeScreen(Utils.Direction.LEFT);

            clickTodayCard(i);

            TimeUnit.SECONDS.sleep(5);
            // 아래 팝업 뜨면 진입 해서 학습 플레이 실행 했던것
            try {
                WebElement pop = getElementById("com.wjthinkbig.mlauncher2:id/commonpop_container");
                if (pop.isDisplayed()) {
                    log.info("학습 시작하기 : 처음부터 하기");
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnLeft").click();
                }
            } catch (Exception ignored) {
                log.info("시작한 적 없는 학습입니다.");

                return;
            }
            오늘의학습콘텐츠종료하기();
            TimeUnit.SECONDS.sleep(3);
            // 학습화면 뜨면 뒤로가기 클릭
            boolean content = AndroidManager.getElementByText("웅진씽크빅").isDisplayed();
            assertTrue("웅진 스마트올 오늘의 카드 힉습시작 확인되지 않습니다.", content);

            // 나가기
            AndroidManager.getElementById("com.wjthinkbig.nfstudyplayer:id/btnCloseDim").click();
            TimeUnit.SECONDS.sleep(2);
            Utils.touchSpecificCoordinates(1133, 860);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void todayStudyYear(String year) {
        // 학년에 따라 체크할 카드 수 결정
        // 6학년이면 3개, 그 외 학년은 5개의 카드 체크

        checkTodayFirstCard();

        checkTodaySecondCard();
        if (year.equals("1학년")) {
            checkTodayoneyrarThirdCard();
        } else {
            checkTodayThirdCard();
        }

        // 아래 두개는 6학년 제외 모든 학년(1~5학년)
        if (!year.equals("6학년")) {
            // 6학년이 아니면 1학년도 아닌 경우 checkTodayCard(3)과 checkTodayCard(4) 실행
            if (!year.equals("1학년")) {
                checkTodayCard(4);
            }
            // checkTodayCard(4)는 항상 실행
            checkTodayCard(5);
        }
    }

    public void 오늘의학습콘텐츠종료하기() {
        try {
            log.info("[동영상 플레이어-학습] 닫기");
            getElementById("com.wjthinkbig.mvideo2:id/btn_study_back_button").click();
            getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
            return;
        } catch (Exception e) {
            try {
                //동영상 플레이어-학습인 경우에만, 닫기버튼이 안보일 경우 viewer 클릭 후 플레이어 닫기
                log.info("[동영상 플레이어-학습] 닫기(Exception)");
                Utils.touchCenterInViewer(getDriver());
                getElementById("com.wjthinkbig.mvideo2:id/btn_study_back_button").click();
                getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();

                return;
            } catch (Exception ie) {
                try {
                    WebElement element = getElementByIdUntilDuration("com.wjthinkbig.mvideo2:id/tv_msg", 5);
                    if (element.isDisplayed()) {
                        log.info("학습 종료");
                        getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
                        return;
                    }
                } catch (Exception e1) {
                    try {
                        log.info("Exception 학습 종료하기");
                        getElementById("com.wjthinkbig.mlauncher2:id/btnExit").click();
                        return;
                    } catch (Exception e2) {
                        try {
//                            checkAndClickButton(70, 60);
                            touchSpecificCoordinates(70, 60);
                            log.info("버튼 좌표 클릭.");

                        } catch (Exception e3) {
                            try{
                                log.info("수학 ui 버튼 학습 종료하기");
                                getElementById("com.wjthinkbig.nfstudyplayer:id/btnCloseDim").click();
                                return;
                            }
                            catch (Exception ignored){
                                log.info("학습을 종료하지 못했습니다.");
                            }

                        }


                    }
                }
            }
        }
        try {
            // 나가기
            log.info("나가기");
            Utils.touchSpecificCoordinates(61, 50);
            TimeUnit.SECONDS.sleep(2);
            Utils.touchSpecificCoordinates(1133, 860);
        } catch (Exception ignored) {
            log.info("나가기: 학습을 종료하지 못했습니다.");

        }
    }


    @Then("웅진 스마트올 AI 학습센터 - AI 학습센터 구성 확인")
    public void 웅진스마트올AI학습센터구성확인() {

        boolean isDisplayedContents =
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/complete_rate_area_vg").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/learningRateArea_vg").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/unit_score_area_vg").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/award_area_vg").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/todayArea_vg").isDisplayed();
        assertTrue("웅진 스마트올 AI 학습센터 화면구성이 확인되지 않습니다.", isDisplayedContents);
    }

    /**
     * 웅진 스마트올 AI 학습센터 영어 실행 확인
     */
    @Then("웅진 스마트올 AI 학습센터 영어 실행 확인")
    public void 웅진스마트올AI학습센터영어실행확인() {
        try {
            log.info("AI 학습센터 영어 실행 확인");
            // 통합영어  토글
            WebElement mergeEng = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_aicenter_eng_learn_result_toggle_entt_text");
            // 교과영어 토글
            WebElement subjectEng = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_aicenter_eng_learn_result_toggle_enem_text");

            if (mergeEng.isDisplayed() && subjectEng.isDisplayed()) {
                String engText = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ll_aicenter_eng_learn_result_nodata")
                        .findElement(By.className("android.widget.TextView")).getText();
                log.info("Test 미응시 안내 문구 확인 : {}", engText);
                assertEquals("Test 문제를 풀고 너의 실력을 확인해봐!", engText);
            }
            //영역별 성취도 분석 영역 확인
            boolean isDisplayedContents1 =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/img_aicenter_eng_radar_chart_nodata").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_aicenter_eng_radar_chart_area01").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_aicenter_eng_radar_chart_area02").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_aicenter_eng_radar_chart_area03").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_aicenter_eng_radar_chart_area04").isDisplayed();
            assertTrue("웅진 스마트올 AI 학습센터 영어 실행이 확인되지 않습니다.", isDisplayedContents1);

            log.info("화면 스크롤");
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            //
            boolean isDisplayedContents2 =
                    AndroidManager.getElementByTextAfterSwipe("현재 레벨").isDisplayed() &&
                            AndroidManager.getElementByTextAfterSwipe("읽은 도서").isDisplayed() &&
                            AndroidManager.getElementByTextAfterSwipe("읽은 단어").isDisplayed();
            assertTrue("웅진 스마트올 AI 학습센터 영어 실행이 확인되지 않습니다.", isDisplayedContents2);


        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 웅진 스마트올 AI 학습센터 AI 연산 진입 확인
     */
    @Then("웅진 스마트올 AI 학습센터 AI 연산 구성 확인")
    public void 웅진스마트올AI학습센터AI연산확인() {
        try {
            log.info("웅진 스마트올 AI 학습센터 AI 연산 UI 정상 노출 확인");
            TimeUnit.SECONDS.sleep(10);
            // 자녀명 확인 >> 자녀1
            boolean isDisplayedContents =
                    getElementByText("자녀1의 연산 학습 변화").isDisplayed()
                            && getElementByText("매쓰 파워 Help").isDisplayed()
                            && getElementByText("자녀1의 연산 학습 변화").isDisplayed()
                            && getElementByText("나의 학습").isDisplayed()
                            && getElementByText("나의 학년").isDisplayed()
                            && getElementByText("AI 분석 그래프").isDisplayed()
                            && getElementByText("학습하기").isDisplayed();
            assertTrue("웅진 스마트올 AI 학습센터 AI 연산 실행이 확인되지 않습니다.", isDisplayedContents);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 웅진 스마트올 AI 학습센터 AI 연산 실행 확인
     */
    @Then("웅진 스마트올 AI 학습센터 AI 연산 실행 확인")
    public void 웅진스마트올AI학습센터AI연산실행확인() {
        try {
            log.info("웅진 스마트올 AI 학습센터 AI 연산 실행 확인");
            TimeUnit.SECONDS.sleep(10);
            boolean isDisplayedContents =
                    AndroidManager.getElementByTextAfterSwipe("매쓰 파워 Help").isDisplayed()
                            && AndroidManager.getElementByText("자녀1의 연산 학습 변화").isDisplayed()
                            && AndroidManager.getElementByText("나의 학습").isDisplayed()
                            && AndroidManager.getElementByText("나의 학년").isDisplayed()
                            && AndroidManager.getElementByText("AI 분석 그래프").isDisplayed()
                            && AndroidManager.getElementByText("학습하기").isDisplayed();
            assertTrue("웅진 스마트올 AI 학습센터 AI 연산 실행이 확인되지 않습니다.", isDisplayedContents);

            AndroidManager.getElementByText("학습하기").click();
            TimeUnit.SECONDS.sleep(10);
            boolean isDisplayedContents2 =
                    AndroidManager.getElementByAccessibilityId("나의 학습 안녕, 자녀1! 너만을 위한 맞춤 학습을 준비했어! 시작하기").isDisplayed()
                            && AndroidManager.getElementByAccessibilityId("프로페서 K 시간 여행으로 즐기는 사고력 수학 시작하기").isDisplayed();
            assertTrue("웅진 스마트올 AI 학습센터 AI 연산 실행이 확인되지 않습니다.", isDisplayedContents2);

            // 학습 화면으로 이동 확인
            myStudy();

            TimeUnit.SECONDS.sleep(1);
            AndroidManager.getElementByText("스마트올로 나가기 스마트올로 나가기 아이콘").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void myStudy() {
        AndroidManager.getElementByXpath
                ("//android.view.View[@content-desc=" +
                        "\"나의 학습 안녕, 자녀1! 너만을 위한 맞춤 학습을 준비했어! 시작하기\"]").click();

        log.info("AI연산 나의 학습 확인");
        boolean contents = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"Help 레벨 테스트\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"학습 시작\"]").isDisplayed()
                && AndroidManager.getElementByText(" 나의 학습 완료하고 오늘의 랭킹 도전!").isDisplayed();

        Assert.assertTrue("웅진 스마트올 AI연산 나의 학습 실행이 확인되지 않습니다.", contents);

        //나가기
        Utils.touchSpecificCoordinates(92, 60);

    }

    /*
     * 학교공부 도와줘 - 음성검색,스마트 번역, 영어회화, 올링고 영어
     * UI 확인
     */
    @Then("웅진 스마트올 AI 학습센터 - 공부지원_게임 구성 확인")
    public void CheckAiSupport() {
        try {
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);

            log.info("AI야! 학교공부 도와줘 확인");
            WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_textview");
            log.info(title.getText() + " 확인");
            assertEquals(title.getText(), "AI야! 학교공부 도와줘");

            log.info("AI야! 학교공부 도와줘 메뉴 확인");
            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_list");
            List<WebElement> allTextViews = element.findElements(By.id("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_title"));
            log.info("메뉴 [{}]개 확인", allTextViews.size());

            for (int i = 0; i < allTextViews.size(); i++) {
                element = AndroidManager.getElementsByIdsAndIndex(
                        "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_list",
                        "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_title", i);
                String subTitleText = element.getText();
                log.info("[{}] 메뉴 노출 확인", subTitleText);
                assertTrue("AI야! 학교공부 도와줘 메뉴 확인되지 않습니다", allTextViews.size() == 4);

            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("웅진 스마트올 AI 학습센터 독서 {string} 확인")
    public void 웅진스마트올AI학습센터독서확인(String menu) {
        log.info("스마트올 - AI 학습센터 독서 - {} 확인", menu);

        WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/titleArea");
        log.info(title.getText() + " 확인");
        assertEquals(title.getText(), menu);

        String recyclerViewId = "";

        if (menu.equals("초등 필독서") || menu.equals("초등 교양서")) {
            recyclerViewId = "com.wjthinkbig.mlauncher2:id/recyclerView";
        } else if (menu.equals("BEARPORT") || menu.equals("Disney")) {
            recyclerViewId = "com.wjthinkbig.mlauncher2:id/library_contents_recyclerview";
        } else if (menu.equals("수준별 영어 도서관")) {
            recyclerViewId = "com.wjthinkbig.mlauncher2:id/contents_recyclerview";
        } else {
            throw new IllegalArgumentException("Invalid menu: " + menu);
        }

        WebElement unit = AndroidManager.getElementById(recyclerViewId);
        List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/contentsImg"));
        log.info("독서 콘텐츠 {}개 노출 확인", allUnits.size());
        assertTrue("스마트올 독서 콘텐츠 확인되지 않습니다.", unit.isDisplayed());

        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();

    }


    @Then("초등포털 인기 영역 확인")
    @Then("초등포털 영어TV 영역 확인")
    @Then("초등포털 직업영역 확인")
    public void 초등포털인기영역확인() {
        try {
            log.info("힉습 플레이 확인");
            try {
                getElementById("com.wjthinkbig.mvideo2:id/player_overlay_play_study").click();
                log.info("초등 포털 학습 영상 확인 후 나가기");
                getElementById("com.wjthinkbig.mvideo2:id/btn_study_back_button").click();
                log.info("학습 플레이 나가기");
                getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
            } catch (Exception e) {
                try {
                    log.info("Exception1");
                    Utils.touchCenterInViewer(getDriver());
                    getElementById("com.wjthinkbig.mvideo2:id/player_overlay_play_study").click();
                    log.info("초등 포털 학습 영상 확인 후 나가기");
                    getElementById("com.wjthinkbig.mvideo2:id/btn_study_back_button").click();
                    log.info("학습 플레이 나가기");
                    getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
                } catch (Exception e1) {
                    log.info("Exception2");
                    Utils.touchBottomInViewer(getDriver());
                    getElementById("com.wjthinkbig.mepubviewer2:id/layoutForRelationBookParent").click();
                    log.info("초등 포털 확인 후 나가기");
                    getElementById("com.wjthinkbig.mepubviewer2:id/btnFirst").click();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("초틍포털 퀴즈풀기 확인")
    public void 초틍포털퀴즈풀기확인() throws InterruptedException {
        log.info("초틍포털 퀴즈풀기 확인");
        WebElement matchupQuizElement =
                AndroidManager.getElementByXpath("//*[contains(@text, '정답 확인')]");
        boolean isDisplayedContents = matchupQuizElement.isDisplayed();
        assertTrue("초틍포털 퀴즈풀기 확인 되지 않습니다.", isDisplayedContents);

        // 나가기
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"btn-close\"]").click();
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("초등포털 3분_회화 확인")
    public void 초등포털분_회화확인() throws InterruptedException {
        log.info("초등포털 3분_회화 확인");
        WebElement matchupEnglishElement =
                AndroidManager.getElementByXpath("//*[contains(@text, 'Listen and Repeat')]");
        boolean isDisplayedContents = matchupEnglishElement.isDisplayed();
        assertTrue("초틍포털 3분 회화 확인 되지 않습니다.", isDisplayedContents);

        // 나가기
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"btn-close\"]").click();
        TimeUnit.SECONDS.sleep(1);

    }

    @Then("초등포털 우리끼리 투표 확인")
    public void 초등포털우리끼리투표확인() {
        log.info("초등포털 우리끼리 투표 확인");
        try {
            WebElement matchupVoteElement =
                    AndroidManager.getElementById("com.wjthinkbig.mballot:id/start_textview");
            String matchupVoteStartElement = matchupVoteElement.getText();
            assertEquals("투표 하기", matchupVoteStartElement);
// 나가기
            AndroidManager.getElementById("com.wjthinkbig.mballot:id/btnClose").click();
            TimeUnit.SECONDS.sleep(1);

        } catch (Exception e) {
            try {

                boolean isDisplayedVote = AndroidManager.getElementById("com.wjthinkbig.mballot:id/btnStart").isDisplayed();
                assertTrue("초틍포털 우리끼리 투표 확인 되지 않습니다.", isDisplayedVote);
            } catch (Exception a) {
                boolean isDisplayedVote = AndroidManager.getElementById("com.wjthinkbig.mballot:id/btnVote").isDisplayed();
                assertTrue("초틍포털 우리끼리 투표 확인 되지 않습니다.", isDisplayedVote);
            }

// 나가기
            AndroidManager.getElementById("com.wjthinkbig.mballot:id/btnClose").click();
        }

    }

    @Then("초등포털 체험의 발견 확인")
    public void 초등포털체험의발견확인() {
        WebElement matchupElement =
                AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/tvTitle");

        boolean isDisplayedContents = matchupElement.isDisplayed();
        assertTrue("초틍포털 체험의 발견 확인 되지 않습니다.", isDisplayedContents);
        log.info(matchupElement.getText());

        AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/btnBack").click();

    }

    CommonStepDifine comm = new CommonStepDifine();

    @Then("초등포털 그람책 확인")
    public void 초등포털그람책확인() throws InterruptedException {

        TimeUnit.SECONDS.sleep(10);
        comm.notReadingContinue("No");
        TimeUnit.SECONDS.sleep(15);
        Utils.touchCenterInViewer(AndroidManager.getDriver());
        WebElement book = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtPieceHeadTitle");
        String comtentNameForCheck = book.getText();
        // 책 종료
        AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/btnFirst").click();
        TimeUnit.SECONDS.sleep(2);

        WebElement bookTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvBookBookName");
        String comtentNameForMatching = bookTitle.getText();

        if (comtentNameForCheck.equals(comtentNameForMatching)) {
            log.info("확인: {} == {}", comtentNameForCheck, comtentNameForMatching);
        } else {
            fail("선택한 컨텐츠가 다릅니다.");
        }
    }


    @Then("스마트올 전체과목 서브메뉴 {string} {string} 확인")
    public void 스마트올전체과목서브메뉴확인(String type, String titleName) {
        try {
            log.info("실행 체크 확인");
            try {
                if (type.equals("액티브북")) {
                    checkActiveBookSubMenus(titleName);
                    // 뒤로가기
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
                } else if (type.equals("예비초")) {
                    check예비초(titleName);
                } else if (type.equals("플레이어")) {
                    checkDefault(titleName);
                } else {
                    fail("원하는 페이지로 접속하지 못했습니다.");
                }
            } catch (Exception e) {
                log.info("소 메뉴 없을 때 수행");
                checkContentsList();
//                상하스크롤동작();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("{string} 인포메이션 팝업 확인")
    public void 인포메이션팝업확인(String menu) {
        try {
            log.info("{} 인포메이션 팝업 확인", menu);
            TimeUnit.SECONDS.sleep(5);
            if (menu.equals("책 놀이") || menu.equals("영역별 개념 학습") || menu.equals("고고! 상위권 수학") ||
                    menu.equals("도전! 경시 문제") || menu.equals("수준별 영어 도서관") || menu.equals("영어TV") ||
                    menu.equals("해외 브랜드관") || menu.equals("AI Talk") || menu.equals("심화 독해") || menu.equals("대치TOP 심화 영어") ||
                    menu.equals("과학초성능력고사") || menu.equals("맞춤형 학업성취도 평가")) {
                //옆으로 스와이프 하기
                Utils.dragSourceToTarget(1336, 200, 480, 200);
                TimeUnit.SECONDS.sleep(1);
                AndroidManager.getElementByTextAfterSwipe(menu).click();
                TimeUnit.SECONDS.sleep(1);
            } else if (menu.equals("WSAT")) {
                //옆으로 스와이프 하기
                Utils.dragSourceToTarget(1655, 200, 445, 200);
                TimeUnit.SECONDS.sleep(1);
                Utils.dragSourceToTarget(1655, 200, 445, 200);
                TimeUnit.SECONDS.sleep(1);
                AndroidManager.getElementByTextAfterSwipe(menu).click();
                TimeUnit.SECONDS.sleep(1);
            } else if (menu.equals("탐구력") || menu.equals("창의력") || menu.equals("초강력")) {
                AndroidManager.getElementByTextAfterSwipe(menu).click();
                TimeUnit.SECONDS.sleep(1);
            } else if (menu.equals("중학 기본 과학")) {
                log.info("대치TOP중학 과학 학습소개 없음 ");
            } else {
                AndroidManager.getElementByTextAfterSwipe(menu).click();
                TimeUnit.SECONDS.sleep(1);
            }
            try {
                // 안내 창에 화살표 있으면 클릭 해서 확인
                WebElement right = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnRight");
                if (right.isDisplayed()) {
                    log.info("오른쪽 화살표 클릭");
                    right.click();
                    TimeUnit.SECONDS.sleep(2);
                    WebElement lift = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnLeft");
                    log.info("왼쪽 화살표 클릭");
                    lift.click();
                }
            } catch (Exception e) {
            }
            TimeUnit.SECONDS.sleep(3);
            log.info("안내팝업 창 나가기");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnExit").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void check예비초(String title) {
        try {
            WebElement topMenu = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_top_menu");
            List<WebElement> allTopMenus = topMenu.findElements(By.className("android.widget.TextView"));
            log.info("[{}]메뉴 {}개만큼 반복 실행", title, allTopMenus.size());
            // 기본 동작
            // 타이틀클릭 후, 탑메뉴와 각각의 모든 소메뉴 확인
            for (int i = 0; i < allTopMenus.size(); i++) {
                // 기본 동작
                //탑메뉴를 클릭하고 소메뉴를 처리합니다.
                checkActiveBookTopMenu(title, i);
            }
            // 뒤로가기
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
        } catch (Exception e) {
            log.info("예비초 클릭시, 메뉴 없이 바로 콘텐츠 열리는 경우 확인");
            log.info("학습 과목: {}", title);
            int num = 예비초subTitleDivide(title);
            try {
                switch (num) {
                    case 10:
                        log.info("학습(게임) 화면 종료");
                        TimeUnit.SECONDS.sleep(5);
                        Utils.touchSpecificCoordinates(61, 50);
                        TimeUnit.SECONDS.sleep(3);
                        break;
                    case 11:
                        TemplateEnd();
                        break;
                    default:
                        checkContentsList();
//                        상하스크롤동작();
                        break;
                }
            } catch (Exception innerException) {
                log.error("check예비초 Exception Switch 블록 내에서 예외 발생", innerException);
            }

        }
    }

    /*
     * 예비초 학습과목 확인 시,
     * 전체 화면에서 각 학습 메뉴의 주제 클릭후
     * 상단 메뉴 클릭 > 하단 소메뉴 확인
     * */
    public void checkActiveBookTopMenu(String titleName, int index) {
        try {
            TimeUnit.SECONDS.sleep(1);
            WebElement topMenu = AndroidManager.getElementsByIdsAndIndex(
                    "com.wjthinkbig.mlauncher2:id/rv_top_menu",
                    "com.wjthinkbig.mlauncher2:id/subject_list_name", index
            );
            String topMenuTitle = topMenu.getText();
            log.info("{}의 {}번째: {}메뉴 클릭", titleName, index + 1, topMenuTitle);
            // 과목 클릭 >> 화면 상단에 과목명 텝으로 보이는 과목 확인
            topMenu.click();
            TimeUnit.SECONDS.sleep(3);
            int num = 예비초subTitleDivide(topMenuTitle);
//            log.info("메뉴 switch {} 확인", num);
            try {
                switch (num) {
                    case 10:
                        log.info("학습 종료하기");
                        TimeUnit.SECONDS.sleep(5);
                        Utils.touchSpecificCoordinates(61, 50);
                        TimeUnit.SECONDS.sleep(3);
                        break;
                    case 11:
                        TemplateEnd();
                        break;
                    default:
                        checkActiveBookSubMenus(topMenuTitle);
//                        상하스크롤동작();
                        break;
                }
            } catch (Exception innerException) {
                log.error("checkActiveBookTopMenu Switch 블록 내에서 예외 발생", innerException);
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void TemplateEnd() {
        try {
            try {
                log.info("템플릿 화면 확인");
                boolean title = AndroidManager.getElementById(
                        "com.wjthinkbig.mbookdiaryactivitytool:id/iv_template_title").isDisplayed();
                assertTrue("템플릿 선택 화면이 아닙니다.", title);
                // 템플릿 화면 나가기
                AndroidManager.getElementById("com.wjthinkbig.mbookdiaryactivitytool:id/btnClosed").click();

            } catch (Exception e) {
                log.info("학습 과정: 템플릿 종료하기");
                WebElement unit = AndroidManager.getElementById("com.wjthinkbig.nfdictation:id/listview");
                List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.nfdictation:id/unit_item"));
                log.info("학습 콘텐츠 {}개 노출 확인", allUnits.size());
                boolean units = unit.isDisplayed();
                assertTrue("스마트올 학습 콘텐츠 확인되지 않습니다.", units);
                log.info("학습 과정 템플릿 종료하기");
                AndroidManager.getElementById("com.wjthinkbig.nfdictation:id/buttonBack").click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
     * 초등 학습의 경우,
     * 예비초와 달리 각각의 탑메뉴를 전체학습 화면에서 클릭하고 하단 소메뉴 확인
     * 소메뉴가 없는 경우 Exception에서 확인
     */
    public void checkActiveBookSubMenus(String titleName) {
        try {
            // 클릭한 title 메뉴의 하위 메뉴 확인
            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_area");
            List<WebElement> allTextViews = element.findElements(By.className("android.widget.TextView"));
            log.info("[{}]의 하위 메뉴 {}개만큼 반복 실행", titleName, allTextViews.size());
            log.info("하위 메뉴 갯수만큼 반복 확인");
            int index = 0;
            for (int i = 0; i < allTextViews.size(); i++) {
                element = AndroidManager.getElementsByIdsAndIndex(
                        "com.wjthinkbig.mlauncher2:id/rv_area",
                        "com.wjthinkbig.mlauncher2:id/subject_list_name", i);
                String subTitleText = element.getText();
                log.info("{}의 {}번째: {} 메뉴 확인", titleName, i + 1, subTitleText);
                index = subTitleDivide(subTitleText);
                element.click();
                log.info("3초대기");
                TimeUnit.SECONDS.sleep(3);

                switch (index) {
                    case 1:
                        log.info("학습 종료하기");
                        try {
                            element = AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mvideo2:id/tv_msg", 10);
                            if (element.isDisplayed()) {
                                AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
                            }
                        } catch (Exception ignored) {
                        }
                        TimeUnit.SECONDS.sleep(5);
                        Utils.touchSpecificCoordinates(61, 50);
                        TimeUnit.SECONDS.sleep(3);
                        break;
                    case 2:
                        TemplateEnd();
                        break;
                    default:
                        log.info("default 케이스 실행 확인");
                        checkContentsList();
//                        상하스크롤동작();
                        break;
                }
            }
        } catch (Exception e) {
            log.info("SubMenus 소메뉴 없음: {}", titleName);
//            상하스크롤동작();
            checkContentsList();
        }
    }


    @Then("스마트올 전체과목 {string} 확인")
    public void 스마트올전체과목확인(String subject) {
        try {
            log.info("전체과목 {} 확인", subject);
            switch (subject) {
                case "AI 어휘력 한자":
                case "급수 한자":
                case "교과 한자어 탐색":
                case "AI 한자 어휘":
                case "한국사능력시험":
                case "시대별 한국사":
                case "큰별쌤의 문화재":
                    smartWhen.학습안내버튼클릭();
                    인포메이션팝업확인(subject);
                    // 학습하기 버튼 노출 확인
                    boolean contents = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
                    assertTrue("전체과목 학습하기 버튼 확인 되지 않습니다.", contents);
                    break;
                case "사회탐구보고서":
                case "과학스타그램":
                case "과학초성능력고사":
                    smartWhen.학습안내버튼클릭();
                    인포메이션팝업확인(subject);
                    WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_content_detail");
                    List<WebElement> allElement = element.findElements(By.id("com.wjthinkbig.mlauncher2:id/imgAIMathFunfunMathItemImage"));
                    if (element.isDisplayed()) {
                        log.info("콘텐츠 노출 : [{}]개 확인", allElement.size());
                    }
                    break;
                default:
                    // 학년 변경 가능한 기본 과목 확인 (학교공부, 사회과학집중탐구...)
                    checkDefaultSubject(subject);
                    break;
            }
            TimeUnit.SECONDS.sleep(2);
            // 나가기
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 기본 과목 처리 메서드
    public void checkDefaultSubject(String subject) throws InterruptedException {
        smartWhen.학습안내버튼클릭();
        인포메이션팝업확인(subject);

        switch (subject) {
            case "바슬즐":
                바슬즐과목확인(subject);
                break;
            case "과학":
            case "사회":
                // 과학, 사회 > 전체과목-3학년 부터 있는데 콘텐츠 안에서 학년 이동 2학년부터 가능
                SecondGradeCourse();
                break;
            case "개념 사회":
            case "개념 과학":
                // 3학년 부터
                ThirdGradeCourse();
                break;

            case "교과서 실험실":
                // 5학년 부터
                FifthGradeCourse();
                break;
            default:
                if (subject.equals("국어")) {
                    스마트올코스안내확인();
                }
                // case에 없는 모든 과목 기본 처리 메서드
                totalGradeCourse();
                break;
        }
    }

    @Then("스마트올 코스안내 확인")
    public void 스마트올코스안내확인() {
        try {
            log.info("스마트올 전체과목 코스안내 확인");
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_show_course").click();
            TimeUnit.SECONDS.sleep(2);
            WebElement element = AndroidManager.getElementByTextAfterSwipe("국어 학습 코스 안내");
            if (element != null && element.isDisplayed()) assertTrue(true);
            AndroidManager.getElementByAccessibilityId("btn-close").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 바슬즐과목확인(String subject) throws InterruptedException {
        smartWhen.학습안내버튼클릭();
        인포메이션팝업확인(subject);
        TimeUnit.SECONDS.sleep(3);
        LowerGradeCourse();

    }

    //저학년 학년 선택 1,2학년
    public void LowerGradeCourse() throws InterruptedException {
        String[] expectedYear = {
                "1학년",
                "2학년"
        };
        for (int i = 1; i <= 2; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);

            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class").findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i - 1]);

            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_chapter_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

        }
    }

    // 2학년부터 있는 과목 학년 선택
    public void SecondGradeCourse() throws InterruptedException {
        String[] expectedYear = {
                "2학년", "3학년", "4학년", "5학년", "6학년"
        };
        for (int i = 0; i < 5; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);
            // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layout_trial_no").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

        }
    }

    public void ThirdGradeCourse() throws InterruptedException {
        String[] expectedYear = {
                "3학년", "4학년", "5학년", "6학년"
        };
        for (int i = 0; i < 4; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);
            // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_no").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

        }
    }

    public void FifthGradeCourse() throws InterruptedException {
        String[] expectedYear = {
                "5학년", "6학년"
        };
        for (int i = 0; i < 2; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);
            // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_no").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

        }
    }


    public void totalGradeCourse() throws InterruptedException {
        String[] expectedYear = {
                "1학년", "2학년", "3학년", "4학년", "5학년", "6학년"
        };
        for (int i = 1; i <= 6; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i - 1]);
            // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_chapter_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

        }

    }


    @Then("스마트올 전체과목 실력완성문제 확인")
    public void 실력완성문제() throws InterruptedException {
        String[] expectedYear = {
                "1학년", "2학년", "3학년", "4학년", "5학년", "6학년"
        };

        for (int i = 2; i <= 5; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);

            // 학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);

            // 현재 학기 확인
            WebElement semester = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_term")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(semester.getText(), "2학기");

            // 과목명 확인 (학년에 따라 과목명을 다르게 설정)
            String[] expectedSubName;
            if (i < 2) {
                expectedSubName = new String[]{"국어", "수학", "바슬즐"};
            } else {
                expectedSubName = new String[]{"국어", "수학", "사회", "과학"};
            }

            for (String subName : expectedSubName) {
                smartWhen.과목드롭다운선택(subName);
                TimeUnit.SECONDS.sleep(1);
                WebElement subjectName = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_subject")
                        .findElement(By.className("android.widget.TextView"));
                assertEquals(subjectName.getText(), subName);
            }

            // 해당 학년 이동 후, 학습하기 버튼, 과목 단원명 노출 확인
            boolean contents = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contents);
        }

        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();

    }

    @Then("스마트올 전체과목 단원평가 확인")
    @Then("스마트올 전체과목 단원핵심특강 확인")
    @Then("스마트올 전체과목 단원요점정리 확인")
    public void 단원요점정리() throws InterruptedException {
        String[] expectedYear = {
                "1학년", "2학년", "3학년", "4학년", "5학년", "6학년"
        };
        for (int i = 0; i <= 5; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class").findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);

            // 현재 학기 확인
            WebElement semester = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_term")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(semester.getText(), "2학기");

            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_subject_title");
            List<WebElement> allTextViews = element.findElements(By.id("com.wjthinkbig.mlauncher2:id/tv_school_subject_title"));

            // 모든 텍스트 값을 저장할 리스트
            List<String> subjectTitles = new ArrayList<>();

            // 각 TextView 요소의 텍스트 값을 가져와 리스트에 추가
            for (WebElement textView : allTextViews) {
                String text = textView.getText();
                subjectTitles.add(text);
                log.info("{}학년 과목명: [{}]", i + 1, text); // 각 텍스트 값을 출력
            }

            // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_school_subject_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_detail").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

        }
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();

    }

    @Then("스마트올 전체과목 성취도평가 확인")
    public void 성취도평가() throws InterruptedException {
        String[] expectedYear = {
                "1학년", "2학년", "3학년", "4학년", "5학년", "6학년"
        };
        for (int i = 0; i < 6; i++) {
            smartWhen.archieveSelector(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_class").findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);

            // 현재 학기 확인
            WebElement semester = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_term")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(semester.getText(), "2학기");

            // 해당 학년 이동후, 중간.기발 성취도평가
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_school_archieve_content").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_school_archieve_content").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_running_state").isDisplayed();
            assertTrue("전체과목 성취도평가 화면 확인 되지 않습니다.", contetnts);
        }
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();

    }


    // WSAT 과목 처리 메서드
    @Then("스마트올 전체과목 WSAT 확인")
    public void checkWSAT() throws InterruptedException {
        smartWhen.학습안내버튼클릭();
        인포메이션팝업확인("WSAT");

        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);

        WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title");
        WebElement report = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_ai_report");
        if (title.isDisplayed() && report.isDisplayed()) {
            assertTrue(true);
            String result = title.getText();
            log.info("WSAT: {} 확인", result);
            드롭다운선택("평가년도", "2024년");
            String[] expectedYear = {
                    "1학년", "2학년", "3학년", "4학년", "5학년", "6학년"
            };
            for (int i = 0; i < 6; i++) {
                드롭다운선택("평가학년", expectedYear[i]);
                TimeUnit.SECONDS.sleep(2);
                //학년 이동 확인
                WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_class")
                        .findElement(By.className("android.widget.TextView"));
                assertEquals(expectedYear[i], year.getText());

                // 현재 학기 확인
                드롭다운선택("평가차시", "2학기");
                WebElement semester = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/archieve_selector_term")
                        .findElement(By.className("android.widget.TextView"));
                assertEquals("2학기", semester.getText());

                // 해당 학년 이동후 노출 확인
                boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_subject_list").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_school_wsat_content").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_running_state").isDisplayed();
                assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contetnts);

            }
        }
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
    }

    @When("드롭 다운 선택 확인")
    public void 드롭다운선택확인() {
        try {
            try {
                log.info("드롭 다운 확인");
                Utils.swipeScreen(Utils.Direction.UP);
                TimeUnit.SECONDS.sleep(1);
                // 반복할 횟수
                int numberOfClicks = 5;
                for (int i = 1; i <= numberOfClicks; i++) {
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/sort_spinner").click();
                    AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/" +
                            "android.widget.ListView/android.widget.LinearLayout[" + i + "]").click();
                    TimeUnit.SECONDS.sleep(2);
                }
                // 드롭다운 최신으로 다시 설정
                AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/sort_spinner").click();
                AndroidManager.getElementByText("최 신").click();

            } catch (Exception e) {
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 드롭다운선택(String drop, String contnt) {
        try {
            log.info("드롭다운 {} {} 선택", drop, contnt);
            String rID = "";
            switch (drop) {
                case "학년":
                case "레벨":
                    rID = "com.wjthinkbig.mlauncher2:id/llGrade";
                    break;
                case "학기":
                case "원리":
                    rID = "com.wjthinkbig.mlauncher2:id/llSmst";
                    break;
                case "코스":
                    rID = "com.wjthinkbig.mlauncher2:id/llCourse";
                    break;
                case "단원":
                    rID = "com.wjthinkbig.mlauncher2:id/selector_unit";
                    break;
                case "영단어":
                    rID = "com.wjthinkbig.mlauncher2:id/selector_course";
                    break;
                case "심화":
                    rID = "com.wjthinkbig.mlauncher2:id/selector_section";
                    break;
                case "평가년도":
                    rID = "com.wjthinkbig.mlauncher2:id/archieve_selector_year";
                    break;
                case "평가학년":
                    rID = "com.wjthinkbig.mlauncher2:id/archieve_selector_class";
                    break;
                case "평가차시":
                    rID = "com.wjthinkbig.mlauncher2:id/archieve_selector_term";
                    break;
            }
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById(rID).click();
            TimeUnit.SECONDS.sleep(3);
            // 해당 드롭다운 선택 후, 원하는 단계 진행
            WebElement dropBar = AndroidManager.getElementByTextAfterSwipe(contnt);
            dropBar.click();
            TimeUnit.SECONDS.sleep(3);
            CheckGradeChange(drop, contnt);


        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    // 드롭다운으로 변경 됐는지 확인 하는 메소드
    public void CheckGradeChange(String drop, String contnt) {
        try {
            WebElement grade = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvGrade");
            WebElement Smst = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvSmst");
            WebElement course = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvCourse");

            if (drop.equals("학년")) {
                assertTrue("학년 변경되지 않았습니다. 메시지: " + grade.getText(),
                        grade.getText().contains(contnt));
            } else if (drop.equals("학기")) {
                assertTrue("학기 변경되지 않았습니다. 메시지: " + Smst.getText(),
                        Smst.getText().contains(contnt));
            } else if (drop.equals("코스")) {
                assertTrue("코스 변경되지 않았습니다. 메시지: " + course.getText(),
                        course.getText().contains(contnt));
            }
        } catch (Exception e) {
        }
    }

    @Then("대치TOP초등 미래탐구 과학 확인")
    public void elementaryTopScience() throws InterruptedException {
        smartWhen.학습안내버튼클릭();
        인포메이션팝업확인("미래탐구 과학");

        String[] expectedYear = {
                "5학년", "6학년"
        };
        for (int i = 0; i < 2; i++) {
            smartWhen.학년드롭다운선택(i);
            TimeUnit.SECONDS.sleep(2);
            //학년 이동 확인
            WebElement year = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                    .findElement(By.className("android.widget.TextView"));
            assertEquals(year.getText(), expectedYear[i]);
            // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
            boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_no").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_title").isDisplayed()
                    && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
            assertTrue("대치TOP초등 미래탐구 과학 확인 되지 않습니다.", contetnts);
        }
        // 나가기
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
    }

    @Then("전체과목 독서플러스 {} 확인")
    @Then("전체과목 코딩학습관 {} 확인")
    @Then("전체과목 한국사플러스 {} 확인")
    @Then("전체과목 사회과학 집중탐구 {} 확인")
    public void checkDefault(String titleName) throws InterruptedException {
        smartWhen.세트목록클릭(0);
        getSetNameByTitle(titleName);
        TimeUnit.SECONDS.sleep(3);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
    }

    public void getSetNameByTitle(String titleName) {
        WebElement setName = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/set_title_text");
        if (setName.isDisplayed()) {
            assertTrue(true);
            log.info("[{}]세트 이름 확인: {}", titleName, setName.getText());
            boolean contents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/titleArea").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/library_set_recyclerview").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/library_contents_recyclerview").isDisplayed();
            assertTrue("화면 확인 되지 않습니다.", contents);
        } else log.error("getSetNameByTitle 오류");
    }

    public void checkContentsList() {
        try {
            WebElement unit = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_unit");
            List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/iv_subject_thumbnail"));

            log.info("메뉴 클릭 시, 콘텐츠 {}개 노출 확인", allUnits.size());
            boolean units = unit.isDisplayed();
            assertTrue("학습 썸네일 확인되지 않습니다.", units);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("전체과목 초등공감 {string} 확인")
    public void check초등공감(String titleName) {
        try {
            smartWhen.세트목록클릭(0);
            getSetNameByTitle(titleName);   // 해당 세트 목록에서 노출되는 콘텐츠 상단 제목 확인
            구성버튼클릭확인();
//            상하스크롤동작();
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("스마트올 전제과목 Ai올링고 {} 확인")
    public void checkAI올링고(String titleName) {
        try {
            switch (titleName) {
                case "수준별 영어 도서관":
                    smartWhen.강의보기선택("B단계");
                    log.info("선택한 단계 확인");
                    Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/set_title_text")
                            .getText(), "B단계");
                    smartWhen.학습안내버튼클릭();
                    인포메이션팝업확인(titleName);
                    break;
                case "영어TV":
                    smartWhen.세트목록클릭(0);
                    log.info("영어TV 확인");
                    Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/titleArea").getText(), "영어TV");
                    smartWhen.학습안내버튼클릭();
                    인포메이션팝업확인(titleName);
                    break;
            }
            구성버튼클릭확인();
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("전체과목 코딩 실습 확인")
    public void checkCoding() throws InterruptedException {
        log.info("코딩 실습 확인");
        smartWhen.학습안내버튼클릭();
        인포메이션팝업확인("코딩 실습");
        String[] lessons = {"ㅋㅋㅋ코딩타운", "버추얼 코딩 게임"};
        for (String lesson : lessons) {
            TimeUnit.SECONDS.sleep(3);
            log.info("{} 확인", lesson);
            AndroidManager.getElementByTextAfterSwipe(lesson).isDisplayed();
        }
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
    }

    @Then("스마트올 독서플러스 초등 필독서 확인")
    public void check초등필독서() throws InterruptedException {
        WebElement element = AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/filterRecycler", 5);
        if (element.isDisplayed()) {
            smartWhen.전체과목내책장서브메뉴클릭("7세");
            smartWhen.전체과목독서력서브메뉴클릭("수학");
            log.info("초등 필독서 확인");
            Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText(), "수학 19권");
            TimeUnit.SECONDS.sleep(3);
            log.info("뒤로가기");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
        }
    }

    @Then("스마트올 독서플러스 초등 교양서 확인")
    public void check초등교양서() throws InterruptedException {
        WebElement element = AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/filterRecycler", 5);
        if (element.isDisplayed()) {
            smartWhen.전체과목내책장서브메뉴클릭("7세");
            smartWhen.전체과목독서력서브메뉴클릭("동물과 자연");
            TimeUnit.SECONDS.sleep(2);
            log.info("초등 교양서 확인");
            Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText(), "동물과 자연 18권");
            TimeUnit.SECONDS.sleep(2);
//            상하스크롤동작();
            log.info("뒤로가기");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
        }
    }


    public int subTitleDivide(String num) {
        int i = 0;
        // 스마트올 - 전체학습 각 서브 메뉴 종류별 확인 (학습, 게임, 템플릿..)
        List<String> allDirectList = Arrays.asList("또박또박 받아쓰기", "씽씽 맞춤법 공격대", "냠냠 반대말 퀴즈");
        List<String> templateList = Arrays.asList("생각 일기");

        if (allDirectList.contains(num)) i = 1;
        if (templateList.contains(num)) i = 2;
        return i;
    }

    public int 예비초subTitleDivide(String title) {
        int num = 0;
        // 학습게임 화면
        List<String> 예비초DirectList = Arrays.asList("반대말 퀴즈", "비슷한 말 퀴즈", "냠냠 반대말 퀴즈", "맞춤법 공격대", "ㅋㅋㅋ코딩타운", "오싹오싹 수학");
        // 템플릿 화면
        List<String> 예비초templateList = Arrays.asList("또박 받아쓰기", "생각 일기", "또박또박 읽기", "쓱쓱 쓰기");

        if (예비초DirectList.contains(title)) num = 10;
        if (예비초templateList.contains(title)) num = 11;

        return num;
    }


    @Then("전체과목 학습플러스 {string} 확인")
    public void check학습플러스(String titleName) {
        try {
            smartWhen.세트목록클릭(0);
            log.info("가져오는 텍스트가 예상과 실제가 동일한지 확인: {}", titleName);
            if (titleName.equals("구석구석 탐험")) {
                // 가져오는 텍스트가 예상과 실제가 동일한지 확인
                String text = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/kid_title").getText();
                String cleanedText = text.trim();   // 문자열 앞뒤 공백 제거
                Assert.assertEquals(cleanedText, titleName);
            } else {
                // 가져오는 텍스트가 예상과 실제가 동일한지 확인
                Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/titleArea").getText(), titleName);
            }
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("스마트올 전체과목 ARVR_학습관 {string} 확인")
    public void 스마트올전체과목ARVR_학습관확인(String subject) {
        switch (subject) {
            case "AR 과학 백과":
                log.info("{} 확인", subject);
                String imageName = Utils.takeScreenShot();
                String result = Utils.imageToText(imageName);
                log.info("image to text: {}", result);
                assertTrue("AR 과학 백과실행 화면이 아닙니다.", (result.contains("보기")
                        || result.contains("보 기")
                        || result.contains("| 세페 |")));
                // AR 화면, 라이브러리, 사용자가이드 선택 -> element 선택이 안돼서 체크 불가능
                // 뒤로가기
                Utils.touchSpecificCoordinates(95, 80);
                break;
            case "VR 한국사":
                한국사세트목록선택("2.고대사회의 발전");
                구성버튼클릭확인();
                smartWhen.학습안내버튼클릭();
                인포메이션팝업확인("VR 문화유산");
                콘텐츠상태확인();
                // 뒤로가기
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
                break;
            case "VR 문화유산":
                구성버튼클릭확인();
                smartWhen.학습안내버튼클릭();
                인포메이션팝업확인("VR 문화유산");
                콘텐츠상태확인();
                // 뒤로가기
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
                break;
        }
    }

    @Then("스마트올 전체과목 초등공감 체험의 발견 확인")
    public void 스마트올전체과목초등공감체험의발견확인() {
        try {
            log.info("스마트올 체험의 발견 확인");
            String[] menuOptions = {"지역별 보기", "주제별 보기", "날씨별 보기", "체험 처방전"};
            for (String menuOption : menuOptions) {
                TimeUnit.SECONDS.sleep(5);
                AndroidManager.getElementByTextAfterSwipe(menuOption).click();
                getSetNameBy체험의발견();   // 해당 목록에서 노출되는 콘텐츠 상단 제목 확인
//                좌측서브메뉴스크롤("UP");
//                좌측서브메뉴스크롤("DOWN");
                TimeUnit.SECONDS.sleep(3);
//                상하스크롤동작();
                log.info("0번째 콘텐츠 클릭");
                AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/recyclerViewContents")
                        .findElements(By.id("com.wjthinkbig.nfbangbangtrip:id/roundedView")).get(0).click();
                TimeUnit.SECONDS.sleep(5);
                log.info("교과 플러스 확인");
                WebElement plus = AndroidManager.getElementByTextAfterSwipe("교과 플러스");
                assert plus != null;
                if (plus.isDisplayed()) {
                    Utils.swipeScreen(Utils.Direction.UP);
                    check교과플러스();
                    AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/btnBack").click();
                } else {
                    fail("텍스트 매칭 실패");
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void getSetNameBy체험의발견() {
        // 지역별, 주제별, 날씨별, 체험
        WebElement title = AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/tvTabTitle");
        WebElement setName = AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/tvContentsTitle");
        log.info("[{}] 이름 확인: {}", title.getText(), setName.getText());
        // 콘텐츠 썸네일, 제목, 태그
        boolean contents =
                AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/imgContent").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/tvTitle").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/tvTag").isDisplayed();
        assertTrue("화면 확인 되지 않습니다.", contents);
    }

    public void check교과플러스() {
        try {
            WebElement unit = AndroidManager.getElementById("com.wjthinkbig.nfbangbangtrip:id/recyclerViewPlus");
            List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.nfbangbangtrip:id/imgStudyPlus"));
            log.info("교과플러스, 콘텐츠 {}개 노출 확인", allUnits.size());
            boolean units = unit.isDisplayed();
            assertTrue("교과플러스 확인되지 않습니다.", units);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("스마트올 전체과목 경제학교 {string} 확인")
    public void 스마트올전체과목경제학교확인(String subject) {
        try {
            smartWhen.세트목록클릭(0);
            구성버튼클릭확인();
//            상하스크롤동작();
            String setname = "";
            if (subject.equals("지식 가득 경제 도서")) {
                setname = "경제 기초";
            } else if (subject.equals("재미 가득 경제 수업")) {
                setname = "돈도니의 꿀꿀 경제";
            }
            log.info("{} 확인: {}", subject, setname);
            // 가져오는 텍스트가 예상과 실제가 동일한지 확인
            Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/set_title_text").getText(), setname);
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("스마트올 전체과목 검정교과서 {string} 확인")
    public void 스마트올전체과목검정교과서확인(String subject) {
        try {
            log.info("{} 확인", subject);
            검정교과학습안내팝업확인(subject);
            smartWhen.우리학교설정클릭();
            TimeUnit.SECONDS.sleep(3);
            String[] expectedYear = {
                    "3학년", "4학년", "5학년", "6학년"
            };
            for (int i = 0; i < 4; i++) {
                smartWhen.검정교과서학년선택(i);
                TimeUnit.SECONDS.sleep(2);
                //학년 이동 확인
                WebElement year = AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/dropdownGrade")
                        .findElement(By.className("android.widget.TextView"));
                assertEquals(year.getText(), expectedYear[i]);

                // 단원학습 확인 프로그레스, 콘텐츠 상단 타이틀, 학습시작 버튼 확인
                boolean contents =
                        AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/item_subject_progress").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/ll_title_container").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/btn_study_start_one").isDisplayed();
                assertTrue("검정교과 학습 확면 확인되지 않습니다.", contents);

            }
            // 나가기
            AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/lnaBack").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 검정교과학습안내팝업확인(String menu) {
        try {
            log.info("{} 학습안내 팝업 확인", menu);
            AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/btn_study_guide").click();
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementByTextAfterSwipe(menu).click();
            TimeUnit.SECONDS.sleep(2);
            log.info("안내팝업 창 나가기");
            AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/btnExit").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    public void 콘텐츠상태확인() {
        try {
            comm.번째콘텐츠클릭(0);
            smartWhen.상세정보팝업창에서바로보기버튼클릭();

            TimeUnit.SECONDS.sleep(30);
            // 프로그래스바 끝까지 보내서 영상보기 완료
            Utils.touchCenterInViewer(AndroidManager.getDriver());
            학습프로그레스바끝으로이동();
            TimeUnit.SECONDS.sleep(3);
            // 동영상 종료
            AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btn_cancel").click();
            TimeUnit.SECONDS.sleep(5);
            WebElement state = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/contents_read_state");
            if (state.isDisplayed()) {
                assertTrue(true);
                log.info("학습 완료된 컨텐츠 이미지에 완료 표시 확인");
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 학습프로그레스바끝으로이동() {
        try {
            log.info("학습 프로그레스바 영상 끝으로 이동");
            WebElement seekBar = AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/player_overlay_seekbar_study");

            int start = seekBar.getLocation().getX();
            int end = seekBar.getSize().getWidth();
            int y = seekBar.getLocation().getY();
            TouchAction action = new TouchAction(AndroidManager.getDriver());

            int move = (int) (end * 1.5);   // 영상 끝 위치 변경
            action.press(PointOption.point(start, y)).moveTo(PointOption.point(move, y)).release().perform();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 구성버튼클릭확인() {
        try {
            smartWhen.스마트올콘텐츠구성버튼클릭();
            세트소개팝업확인();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("세트 소개 팝업 확인")
    public void 세트소개팝업확인() {
        try {
            log.info("세트 소개, 구성 확인");
            // 세트소개, 소개, 구성 확인
            boolean content = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/topTitle").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/introduceTitle").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/compositionTitle").isDisplayed();
            assertTrue("세트소개 팝업이 확인 되지 않습니다.", content);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/closeBtn").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 한국사세트목록선택(String menu) {
        try {
            int idx = 0;
            switch (menu) {
                case "1.우리 역사의 형성":
                    idx = 0;
                    break;
                case "2.고대사회의 발전":
                    idx = 1;
                    break;
            }
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView")
                    .findElements(By.className("android.view.ViewGroup")).get(idx).click();
            TimeUnit.SECONDS.sleep(3);
            log.info("{} 선택 및 확인", menu);
            Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/set_title_text").getText(), menu);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("스마트올 전체과목 {int}학년 국어 진입 확인")
    public void totalGradeCheckCourse(int year) {
        String expectedYear = year + "학년";
        //학년 이동 확인
        WebElement selectyear = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                .findElement(By.className("android.widget.TextView"));
        assertEquals(expectedYear, selectyear.getText());

        // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
        boolean contents = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_chapter_title").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
        assertTrue("전체과목 학습 화면 확인 되지 않습니다.", contents);
    }

    @Then("스마트올 전체과목 {int}학년 실력완성문제 진입 확인")
    public void Check실력완성문제(int year) throws InterruptedException {
        String expectedYear = year + "학년";
        //학년 이동 확인
        WebElement selectyear = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                .findElement(By.className("android.widget.TextView"));
        assertEquals(expectedYear, selectyear.getText());

        TimeUnit.SECONDS.sleep(1);
        WebElement subjectName = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_subject")
                .findElement(By.className("android.widget.TextView"));
        assertEquals(subjectName.getText(), "국어");  // 실력 완성 문제 집입시, 선택 학년/학기/국어

        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();

    }


    @Then("{int}학년 대치TOP초등 미래탐구 과학 진입 확인")
    public void checkTopScience(int year) throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        String expectedYear = year + "학년";
        //학년 이동 확인
        WebElement selectyear = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/selector_class")
                .findElement(By.className("android.widget.TextView"));
        assertEquals(expectedYear, selectyear.getText());

        // 해당 학년 이동후, 학습하기 버튼, 과목단원명 노출 확인
        boolean contetnts = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_title").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_no").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_content_trial_title").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed();
        assertTrue("대치TOP초등 미래탐구 과학 확인 되지 않습니다.", contetnts);

        // 나가기
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
    }

    @Then("AI 학습센터 독서 교과서 독심술 확인")
    public void ai학습센터독서교과서독심술확인() {
        log.info("교과서 독심술 UI 확인");
        boolean contents =
                AndroidManager.getElementByText("교과서 독심술").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/my_btn").isDisplayed();
        assertTrue("교과서 독심술 UI 확인되지 않습니다.", contents);
        // 뒤로가기
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_ibtn").click();
    }

    @Then("AI교과 필독서 영역 확인")
    public void ai교과필독서영역확인() {
        log.info("AI교과 필독서 영역 확인");
        assertTrue("AI교과 필독서 영역 확인되지 않습니다.",
                getElementById("com.wjthinkbig.mlauncher2:id/mustRead_bookImg_iv").isDisplayed());
    }

    @Then("전체과목 {int}학년 AI수학마스터 분수행성 확인")
    public void 전체과목학년AI수학마스터분수행성확인(int year) throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
        log.info("{}학년 분수 행성 확인", year);
        if (year == 3 || year == 4) {
            log.info("분수 행성 확인 하기");
            boolean contents = AndroidManager.getElementById("com.wjthinkbig.nfFraction:id/unitySurfaceView").isDisplayed();
            assertTrue("분수 행성 게임 화면이 아닙니다", contents);

        } else {
            String imageName = Utils.takeScreenShot();
            String result = Utils.imageToText(imageName);
            log.info("image to text: {}", result);
            assertTrue("분수 행성 실행 화면이 아닙니다.", result.contains("아직 기록이 없네요")
                    || result.contains("정 답 오 답 정 확 도")
                    || result.contains("공 부 ")
                    || result.contains("순위 동급")
                    || result.contains("오답 정확도 콤보"));
        }
        log.info("분수 행성 종료 하기");
        Utils.touchSpecificCoordinates(85, 75);
    }

    @Then("오늘의학습 학습캘린더 AI오답노트 확인")
    public void 오늘의학습학습캘린더AI오답노트확인() throws InterruptedException {
        log.info("학습캘린더 AI오답노트 확인");
        TimeUnit.SECONDS.sleep(10);
        // 마이 > 스마트올AI 오답노트 화면 구성 확인
        boolean isDisplayedContents = false;

        try {
            isDisplayedContents =
                    AndroidManager.getElementByText("MY").isDisplayed() &&
                            AndroidManager.getElementByText("단원별보기").isDisplayed();
        } catch (Exception e) {
            isDisplayedContents =
                    AndroidManager.getElementByText("MY").isDisplayed() &&
                            AndroidManager.getElementByText("learning_good").isDisplayed();
        }

        assertTrue("학습캘린더 AI오답노트 확인 되지 않습니다.", isDisplayedContents);
        // 나가기
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnExit").click();

    }


    @Then("오늘의 학습 날짜 선택 확인")
    public void 오늘의학습날짜선택확인() {
        // 현재 선택된 날짜 저장
        WebElement currentSelectedDate = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvSelectedDate");
        String initialDate = currentSelectedDate.getText();

        // 어제 날짜 클릭 (인덱스 2)
        WebElement yesterday = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/llWeek")
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/tvWeekName")).get(2);
        yesterday.click();

        // 클릭 후 선택된 날짜 확인
        WebElement newSelectedDate = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvSelectedDate");
        String newDate = newSelectedDate.getText();

        // 날짜 변경 여부 확인
        if (!initialDate.equals(newDate)) {
            log.info("날짜가 변경되었습니다. 이전 날짜: {}, 새로운 날짜: {}", initialDate, newDate);
        } else {
            fail("날짜가 변경되지 않았습니다.");
        }

        // 미래 날짜 클릭 (인덱스 3)
        WebElement tomorrow = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/llWeek")
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/tvWeekName")).get(3);
        tomorrow.click();

        // 클릭 후 선택된 날짜 확인
        WebElement futureSelectedDate = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvSelectedDate");
        String futureDate = futureSelectedDate.getText();

        // 미래 날짜 변경 여부 확인
        if (!newDate.equals(futureDate)) {
            log.info("날짜가 변경되었습니다. 이전 날짜: {}, 새로운 날짜: {}", newDate, futureDate);
            // 다시 오늘 날짜로 변경 해주기
            AndroidManager.getElementByText("오늘").click();
        } else {
            fail("날짜가 변경되지 않았습니다.");
        }
    }


    @Then("오늘의 학습 하단 메뉴 이동 확인")
    public void 오늘의학습하단메뉴이동확인() throws InterruptedException {

        log.info("오늘의 학습 하단 메뉴 클릭");
        for (int i = 0; i < 7; i++) {
            WebElement appIcon = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/llAppIcon")
                    .findElements(By.className("android.widget.RelativeLayout")).get(i);

            String appIconTitle = appIcon.findElement(By.id("com.wjthinkbig.mlauncher2:id/txtTitile")).getText();
            log.info(appIconTitle + " 아이콘 클릭");

            TimeUnit.SECONDS.sleep(2);
            appIcon.click();
            TimeUnit.SECONDS.sleep(5);

            // 화면 확인 및 나가기 로직
            checkAndExitScreen(i, appIconTitle);
        }
    }

    /*
     * 스마트올 오늘의 학습 - 하단 메뉴 확인
     * 순서 : 출석이벤트, 일별학습결과, AI오답노트, Ai수학마스터, AI올링고 영어, AI연산매쓰피드, 별목표세우기*/
    private void checkAndExitScreen(int index, String appIconTitle) throws InterruptedException {
        boolean isDisplayedContents = false;

        switch (index) {
            case 0: // 출석이벤트
                isDisplayedContents = AndroidManager.getElementByText("bg_roulette").isDisplayed();
                break;
            case 1: // 일별학습결과
                TimeUnit.SECONDS.sleep(5);
                try {
                    isDisplayedContents = AndroidManager.getElementByText("최근학습일시").isDisplayed();
                } catch (Exception e) {
                    isDisplayedContents = AndroidManager.getElementByText("learning_pass").isDisplayed();
                }
                break;
            case 2: // AI오답노트
                isDisplayedContents = AndroidManager.getElementByText("learning_good").isDisplayed();
                break;
            case 3: // Ai수학마스터
                isDisplayedContents = AndroidManager.getElementByText("AI 수학 마스터").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lnaAIMathTabViewLayout").isDisplayed();
                break;
            case 4: // AI올링고 영어
                isDisplayedContents = AndroidManager.getElementByText("AI 올링고 영어").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layout_tab_container").isDisplayed();
                break;
            case 5: // AI연산매쓰피드
                isDisplayedContents = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"나의 학습 안녕, 자녀1! 너만을 위한 맞춤 학습을 준비했어! 시작하기\"]").isDisplayed() &&
                        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"프로페서 K 시간 여행으로 즐기는 사고력 수학 시작하기\"]").isDisplayed();
                break;
            case 6: // 별목표세우기
                isDisplayedContents = AndroidManager.getElementByText("내 별 현황").isDisplayed() &&
                        AndroidManager.getElementByText("나의 목표").isDisplayed();
                break;
            default:
                log.warn("처리되지 않은 인덱스: " + index);
                return;
        }

        assertTrue(appIconTitle + " 화면 확인되지 않습니다.", isDisplayedContents);

        // 화면 나가기
        if (index == 5) {
            try {
                AndroidManager.getElementByTextAfterSwipe("스마트올로 나가기 스마트올로 나가기 아이콘").click();
            } catch (Exception e) {
                Utils.touchSpecificCoordinates(111, 77);
            }
        } else if (index == 3) {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lnaBack").click();
        } else if (index == 6) {
            smartWhen.웅진스마트올서브메뉴클릭("오늘의 학습");
            try {
                WebElement event = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnEventClose");
                if (event.isDisplayed()) event.click();
            } catch (Exception ignored) {
            }
        } else {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
        }
    }


    @Then("자녀의 학습 완료율 안내 확인")
    public void 자녀의학습완료율안내확인() {
        try {
            log.info("학습완료 안내 팝업 확인");
            boolean isDisplayedContents = AndroidManager.getElementByTextAfterSwipe("이번주의 학습 완료율을 알려드려요.").isDisplayed();
            assertTrue("학습완료 안내 팝업 확인되지 않습니다.", isDisplayedContents);
            // 안내창 닫기
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/complete_rate_guide_iv").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("단원평가 점수 안내 확인")
    public void 단원평가점수안내확인() {
        try {
            log.info("단원평가 점수 안내 확인");
            boolean isDisplayedContents = AndroidManager.getElementByTextAfterSwipe("이번달 단원평가 점수를 확인해볼까요?").isDisplayed();
            assertTrue("단원평가 점수 안내 팝업 확인되지 않습니다.", isDisplayedContents);
            // 안내창 닫기
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/unit_score_guide_iv").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("나는 무슨 상을 받았을까 안내 확인")
    public void 나는무슨상을받았을까안내확인() {
        try {
            log.info("나는 무슨 상을 받았을까 안내 확인");
            boolean isDisplayedContents = AndroidManager.getElementByTextAfterSwipe("상장을 받을 수 있는 기준을 알려드려요.").isDisplayed();
            assertTrue("나는 무슨 상을 받았을까 안내 팝업 확인되지 않습니다.", isDisplayedContents);
            // 안내창 닫기
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/award_area_guide_iv").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("AI 학습센터 회화 실행 확인")
    public void ai학습센터회화실행확인() {
        smartWhen.listenAndRepeat버튼클릭();
        listenAndRepeat실행확인();
    }

    @Then("Listen and Repeat 실행 확인")
    public void listenAndRepeat실행확인() {
        try {
            WebElement rec = AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/btnRec");
            WebElement play = AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/btnPlay");
            if (rec.isDisplayed() && play.isDisplayed()) {
                log.info("Listen and Repeat 실행 확인");
            }

            log.info("종료 및 나가기");
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/btnExit").click();
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/btnFinish").click();
            // 3분 회화 창 끄기
            AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"btn-close\"]/android.widget.Button").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("영어 진단 검사 실행 확인")
    public void 영어진단검사실행확인() {
        try {
            TimeUnit.SECONDS.sleep(10);
            log.info("영어 진단 검사 확인");
            boolean content = AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/container")
                    .isDisplayed();
            assertTrue("영어 진단 검사 화면이 아닙니다.", content);

            // 진단검사 창 나가기
            AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View").click();
            TimeUnit.SECONDS.sleep(1);
            // 나가기 팝업 확인
            AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"확인\"]").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("웅진 스마트올 오늘의 AI 추천도서 확인")
    public void 웅진스마트올오늘의AI추천도서확인() {
        try {
            log.info("AI 연산 주제학습 실행 확인");
            boolean content =
                    AndroidManager.getElementByTextAfterSwipe("오늘의 AI 추천도서").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/suggestion_book_iv").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rabbit_icon_iv").isDisplayed();
            assertTrue("오늘의 AI 추천도서 확인 되지 않습니다", content);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("원서 소개 알림 팝업 확인")
    public void 원서소개알림팝업확인() {
        try {
            TimeUnit.SECONDS.sleep(10);
            WebElement element = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"btn-close\"]/android.widget.Button");
            if (element.isDisplayed()) element.click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("프리미엄 원서 마스터 확인")
    public void 프리미엄원서도서확인() {
        try {
            WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_title");

            log.info("프리미엄 원서 마스터 : {}", title.getText());

            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_content_detail").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_lesn_name").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_start").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_read_book").isDisplayed();
            assertTrue("프리미엄 원서 도서 화면구성이 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
     * 학교공부 도와줘 - 음성검색,스마트 번역, 영어회화, 올링고 영어
     *
     */
    @Then("웅진 스마트올 AI 학습센터 - 공부지원_게임 확인")
    public void AiSupportSchoolStudy() {
        try {
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);

            log.info("AI야! 학교공부 도와줘 확인");
            WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_textview");
            log.info(title.getText() + " 확인");
            assertEquals(title.getText(), "AI야! 학교공부 도와줘");

            log.info("AI야! 학교공부 도와줘 메뉴 확인");
            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_list");
            List<WebElement> allTextViews = element.findElements(By.id("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_title"));
            log.info("메뉴 [{}]개 확인", allTextViews.size());

            for (int i = 0; i < allTextViews.size(); i++) {
                element = AndroidManager.getElementsByIdsAndIndex(
                        "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_list",
                        "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_title", i);
                String subTitleText = element.getText();
                log.info("[{}] 메뉴 노출 확인", subTitleText);
                assertTrue("AI야! 학교공부 도와줘 메뉴 확인되지 않습니다", allTextViews.size() == 4);

                log.info("{} 클릭", subTitleText);
                element.click();

                studyGameMenu(i);
                TimeUnit.SECONDS.sleep(2);

                AiPatternGame();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void studyGameMenu(int i) throws InterruptedException {
        switch (i) {
            case 0:
                boolean contents0 = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/tvClovaComment").isDisplayed();
                Assert.assertTrue("음성 검색 실행이 확인되지 않습니다.", contents0);
                AndroidManager.getElementById("com.wjthinkbig.dictionary:id/dialog_close_btn").click();
                break;
            case 1:
                boolean contents1 = AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/imgAllLingo").isDisplayed();
                Assert.assertTrue("스마트 Ai 번역 실행이 확인되지 않습니다.", contents1);
                AndroidManager.getElementById("com.wjthinkbig.nfalllingo:id/btnExit").click();
                break;
            case 2:
                TimeUnit.SECONDS.sleep(15);
                ai학습센터회화실행확인();
                break;
            case 3:
                boolean contents3 = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/layout_tab_container").isDisplayed();
                Assert.assertTrue("Ai 올링고 영어 실행이 확인되지 않습니다.", contents3);
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
                break;
            default:
                throw new IllegalArgumentException("Invalid menu option: " + i);
        }
    }

    public void AiPatternGame() {
        try {
            log.info("AI 패턴 게임 확인");
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);

            WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aipattern_textview");
            log.info(title.getText() + " 확인");
            assertEquals(title.getText(), "AI 패턴 게임");

            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);

            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aipattern_list");
            List<WebElement> allTextViews = element.findElements(By.id("com.wjthinkbig.mlauncher2:id/sa_aicenter_support_game_title"));
            // 화면에 보이는 게임 수 확인 > 한줄에 3개씩 보임
            log.info("게임 [{}] 노출 확인", allTextViews.size());

            for (int i = 0; i < allTextViews.size(); i++) {
                element = AndroidManager.getElementsByIdsAndIndex(
                        "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aipattern_list",
                        "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_game_title", i);
                String subTitleText = element.getText();
                log.info("[{}] 노출 확인", subTitleText);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("진단검사 응시하기 확인")
    public void 진단검사응시하기확인() {
        try {
            try {
                WebElement exam = AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/iv_exam", 10);
                if (exam.isDisplayed()) {
                    log.info("진단검사 응시하기 클릭");
                    exam.click();
                    TimeUnit.SECONDS.sleep(15);
                    try {
                        log.info("진단검사 시작하기");

                        Utils.touchSpecificCoordinates(1725, 1060);
                        TimeUnit.SECONDS.sleep(1);
                        Utils.touchSpecificCoordinates(1725, 1060);

                        try {
                            // 진단 검사 국어
                            TimeUnit.SECONDS.sleep(3);
                            WebElement kor = AndroidManager.getElementByTextAfterSwipe("진단 검사 한글");
                            if (kor != null) assertTrue(true);
                        } catch (Exception e) {
                            // 진단 검사 수학
                            TimeUnit.SECONDS.sleep(2);
                            log.info("수학 진단 검사");
                            WebElement math = AndroidManager.getElementByTextAfterSwipe("진단 검사 수학");
                            if (math != null) assertTrue(true);
                        }
                    } catch (Exception e) {
                    }
                    진단검사종료하기();
                } else {
                    log.info("진단 검사 응시하기 버튼 없는 과목입니다.");
                }
            } catch (Exception e) {
                log.info("진단 검사 응시하기 화면이 아닙니다.");
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }

    public void 진단검사종료하기() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        log.info("진단검사 종료하기");
        AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View[1]")
                .click();
        TimeUnit.SECONDS.sleep(2);
        AndroidManager.getElementByAccessibilityId("확인").click();
        TimeUnit.SECONDS.sleep(2);
    }


    @Then("키즈 {string} 인포메이션 팝업 확인")
    public void 키즈인포메이션팝업확인(String sub) {
        try {
            log.info("키즈 {} 학습안내 팝업 확인", sub);
            WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title");

            // 과목 주제 저장
            String subTitle = title.getText();

            log.info("학습안내 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_study_guide").click();
            TimeUnit.SECONDS.sleep(3);

            // 학습정포 팝업 제목 확인
            String checkTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvTitle").getText();
            assertEquals(subTitle, checkTitle);

            log.info("학습안내 팝업 확인 [{}] : {}", subTitle, checkTitle);

            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnExit").click();


        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("학습카드 받아오고 확인")
    public void 학습카드받아오기() {
        WebDriver driver = AndroidManager.getDriver();

        // 1. recyclerView 요소 찾기
        WebElement recyclerView = driver.findElement(By.id("com.wjthinkbig.mlauncher2:id/recyclerView"));

        // 2. recyclerView 안의 lnaCourseLayout 요소 리스트 가져오기
        List<WebElement> courseLayouts = recyclerView.findElements(By.id("com.wjthinkbig.mlauncher2:id/lnaCourseLayout"));

        // 3. 결과를 저장할 배열 준비
        List<String> resultList = new ArrayList<>();

        // 4. imgThumbnail에 대한 번호 추적을 위한 전역 카운터 초기화
        AtomicInteger imgThumbnailCount = new AtomicInteger(1); // 숫자 증가를 위해 AtomicInteger 사용

        // 5. 각 lnaCourseLayout 내부의 txtCourse와 imgThumbnail 처리
        processLayouts(courseLayouts, resultList, imgThumbnailCount, 1); // 첫 번째 호출에서 맨 마지막 항목을 건너뛰기

        // 6. 특정 좌표 클릭하여 스크롤 이동
        Utils.swipeToScroll(1850, 903, 170, 903);  // 스크롤을 왼쪽 끝으로 이동
        log.info("스크롤 진입");

        // 7. 화면을 다시 한번 스캔 (스크롤 후 다시 요소 리스트 가져오기)
        courseLayouts = recyclerView.findElements(By.id("com.wjthinkbig.mlauncher2:id/lnaCourseLayout"));

        // 8. 결과 재확인
        processLayouts(courseLayouts, resultList, imgThumbnailCount, 2); // 두 번째 호출에서 맨 첫 번째 항목을 건너뛰기


        List<String> filteredResultList = removeConsecutiveDuplicates(resultList);
        // 결과 출력
        System.out.println("Final result list after scroll: " + filteredResultList);
        Utils.swipeToScroll(170, 903, 1850, 903);
        log.info("스크롤 원래대로");
        case벌학습카드확인(filteredResultList);

    }

    // 연속된 중복 값을 제거하는 메서드
    public List<String> removeConsecutiveDuplicates(List<String> list) {
        List<String> filteredList = new ArrayList<>();
        String lastAdded = null;

        for (String current : list) {
            if (lastAdded == null || !lastAdded.equals(current)) {
                filteredList.add(current);
            }
            lastAdded = current;
        }
        return filteredList;
    }

    // lnaCourseLayout 처리하는 공통 함수
    public void processLayouts(List<WebElement> courseLayouts, List<String> resultList, AtomicInteger imgThumbnailCount, int skipCondition) {
        for (int i = 0; i < courseLayouts.size(); i++) {
            // skipCondition이 1이면 마지막 요소를 건너뜀
            if (skipCondition == 1 && i == courseLayouts.size() - 1) {
                continue; // 맨 마지막 요소는 건너뛰기
            }

            // skipCondition이 2이면 첫 번째 요소를 건너뜀
            if (skipCondition == 2 && i == 0) {
                continue; // 첫 번째 요소는 건너뛰기
            }

            System.out.println("Processing lnaCourseLayout index: " + i);

            // txtCourse와 imgThumbnail 초기화
            WebElement txtCourse = null;
            WebElement imgThumbnail = null;

            // lnaCourseLayout 내부의 txtCourse 요소 접근
            try {
                txtCourse = courseLayouts.get(i).findElement(By.xpath(".//android.widget.TextView[@resource-id='com.wjthinkbig.mlauncher2:id/txtCourse']"));
                String courseText = txtCourse.getText();
                resultList.add(courseText); // txtCourse가 있으면 텍스트 추가
                System.out.println("Course Text: " + courseText);
            } catch (NoSuchElementException e) {
                System.out.println("txtCourse not found in lnaCourseLayout index: " + i);
            }

            // lnaCourseLayout 내부의 imgThumbnail 요소 접근
            try {
                imgThumbnail = courseLayouts.get(i).findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.wjthinkbig.mlauncher2:id/imgThumbnail']"));
                if (txtCourse == null) { // txtCourse가 없는 경우에만 숫자 추가
                    resultList.add(String.valueOf(imgThumbnailCount.getAndIncrement())); // imgThumbnail만 있으면 숫자 추가하고 카운트 증가
                    System.out.println("Thumbnail Resource ID: " + imgThumbnail.getAttribute("resource-id"));
                }
            } catch (NoSuchElementException e) {
                System.out.println("imgThumbnail not found in lnaCourseLayout index: " + i);
            }
        }
    }

//@Then("진입 확인")
    public void case벌학습카드확인(List<String> resultList) {

        log.info(resultList.toString());
        log.info("학습카드 진입 확인");

        for (String item : resultList) {
            // switch문을 활용하여 각 항목에 대해 처리
            switch (item) {
                case "문해력을 시작해 보자!":
                    log.info("문해력 학습 시작");
                    // 문해력 관련 작업 수행
                    break;

                case "1":
                    log.info("첫 번째 학습");
                    겨울특강확인();
                    break;

                case "스마트올AI탐구":
                    log.info("스마트올AI탐구 학습");
                    스마트올AI탐구학습();
                    break;

                case "WSAT":
                    log.info("WSAT 학습");
                    WSAT스마트올학력평가();
                    break;

                case "영어":
                    log.info("영어 학습");
                    // 영어 학습 관련 작업 수행
                    break;

                case "수학":
                    log.info("수학 학습");
                    수학학습카드확인();
                    break;

                case "바슬즐":
                    log.info("바슬즐 학습");
                    바슬즐카드확인();
                    break;

                case "이번 주 독서":
                    log.info("이번 주 독서 학습");
                    이번주독서카드확인();
                    break;
                case "과학":
                    log.info("과학 학습");
                    과학카드확인();
                    break;

                case "국어":
                    log.info("국어 학습");
                    국어학습카드확인();
                    break;
                default:
                    log.info("알 수 없는 항목: " + item);
                    // 예외 처리
                    break;
            }
        }
    }


    public void 겨울특강확인(){
        clickTodayCard(1);
        log.info("겨울방학 특강 학습카드 확인");
        log.info("checkTodayFirstCard");


        String card = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title").getText();
        assertEquals("겨울방학특강", card);

        String title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_guide").getText();
        assertEquals("영역별 단기 집중 겨울방학 특강", title);

        AndroidManager.getElementById(Constant.뒤로가기_id).click();
    }


    public void 스마트올AI탐구학습(){
        clickTodayCard(2);
        // smartAll AI 동영상
        log.info("smartAll AI 탐구 학습 동영상 확인");
        log.info("checkTodaySecondCard");
        오늘의학습콘텐츠종료하기();
    }

    public void WSAT스마트올학력평가(){
        clickTodayCard(3);
        log.info("WSAT스마트올학력평가 학습카드 확인");
        log.info("checkTodayFirstCard");


        String card = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_subject_school_title").getText();
        assertEquals("WSAT 스마트올 학력평가", card);



        AndroidManager.getElementById(Constant.뒤로가기_id).click();
    }

    public void 수학학습카드확인(){
        clickTodayCard(4);
        // smartAll AI 동영상
        log.info("수학 동영상 확인");

        오늘의학습콘텐츠종료하기();
    }

    public void 국어학습카드확인(){
        clickTodayCard(3);
        // smartAll AI 동영상
        log.info("국어 동영상 확인");

        오늘의학습콘텐츠종료하기();
    }

    public void 과학카드확인(){
        try {
            TimeUnit.SECONDS.sleep(5);
            Utils.swipeToScroll(1850, 903, 170, 903);
            clickTodayCard(3);
            // smartAll AI 동영상
            log.info("과학 동영상 확인");

            오늘의학습콘텐츠종료하기();

        }catch (Exception e){
            e.getMessage();
        }
    }

    public void 바슬즐카드확인(){
        try {
            TimeUnit.SECONDS.sleep(5);
            Utils.swipeToScroll(1850, 903, 170, 903);
            clickTodayCard(3);
            // smartAll AI 동영상
            log.info("바슬즐 동영상 확인");

            오늘의학습콘텐츠종료하기();

        }catch (Exception e){
            e.getMessage();
        }
    }


    public void 이번주독서카드확인(){
//        if(year.equals("1학년")){
//            clickTodayCard(4);
//            // smartAll AI 동영상
//            log.info("이번주독서카드확인 확인");
//        }

        clickTodayCard(4);
        // smartAll AI 동영상
        log.info("이번주독서카드확인 확인");

        오늘의학습콘텐츠종료하기();
    }

    @Then("스마트올 키즈 더보기 스티커판 확인")
    public void 스마트올키즈더보기스티커판확인(){
        try {
            log.info("스마트올 키즈 더보기 스티커판 확인");
            TimeUnit.SECONDS.sleep(3);
            boolean contents = AndroidManager.getElementByText("2025년 1월 스티커").isDisplayed();
            assertTrue("스티커판 확인되지 않습니다.",contents);
            // 나가기
            TimeUnit.SECONDS.sleep(1);
            AndroidManager.getElementByText("close").click();
        }catch (Exception e){
            try {
                smartWhen. 스마트올키즈더보기버튼클릭("스티커판");
                log.info("Exception 스마트올 키즈 더보기 스티커판 확인");
                TimeUnit.SECONDS.sleep(3);
                boolean contents = AndroidManager.getElementByText("2025년 1월 스티커").isDisplayed();
                assertTrue("스티커판 확인되지 않습니다.",contents);

                // 나가기
                AndroidManager.getElementByText("close").click();
            }catch (NoSuchElementException e1) {
                fail("Element you found not shown");
            } catch (Exception e1) {
                fail(e1.getMessage());
                System.exit(0);
            }
        }
    }
}







