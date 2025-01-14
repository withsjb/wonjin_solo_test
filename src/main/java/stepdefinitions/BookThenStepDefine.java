package stepdefinitions;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AndroidManager;
import utils.Constant;
import utils.Utils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static utils.AndroidManager.*;

public class BookThenStepDefine {

    private final Logger log = LoggerFactory.getLogger(getClass());

    BookWhenStepDefine bookWhen = new BookWhenStepDefine();
    CommonStepDifine comm = new CommonStepDifine();


    @Then("웅진 북클럽 확인")
    public void 웅진북클럽확인() {
        try {
            log.info("웅진 북클럽 확인");
            // 상단 메뉴 구성으로 확인 (ui 변경 : 독서, 학습)
            boolean isDisplayedContents =
                    AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"투데이\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"스마트씽크빅\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"라이브러리\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.ImageButton[@content-desc=\"검색\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.ImageButton[@content-desc=\"독서 앨범\"]").isDisplayed() &&
                            AndroidManager.getElementByXpath("//android.widget.ImageButton[@content-desc=\"스마트올 백과\"]").isDisplayed();
            assertTrue("웅진 북클럽 확인되지 않습니다.", isDisplayedContents);

            // 홈 - 라이브러리 클릭
            AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"라이브러리\"]").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("바나쿠 캐릭터 설정")
    public void 바나쿠캐릭터설정() {
        try {
            AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_setting").click();
            boolean banaes = AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_choco").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_berry").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_bana").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_apple").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_kiwi").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_feel").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_tough").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_kind").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ll_smart").isDisplayed();
            assertTrue("북클럽 캐릭터 확인 되지 않습니다.", banaes);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("북클럽 프렌즈 메뉴 확인")
    public void 북클럽프렌즈메뉴확인() {
        try {
            boolean banaes_1 = AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_today").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_game").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/ly_message").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_search").isDisplayed();
            assertTrue("북클럽 메뉴 확인 되지 않습니다.", banaes_1);
            TimeUnit.SECONDS.sleep(2);
            Utils.dragSourceToTarget(1400, 1050, 630, 1050);
            boolean banae2 = AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_en").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_album").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_voice").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_camera").isDisplayed() &&
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_coockie").isDisplayed();
            assertTrue("북클럽 메뉴 확인 되지 않습니다.", banae2);

            AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_close").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @When("북클럽 나의 프렌즈 ON_OFF")
    public void 북클럽나의프렌즈ON_OFF() {
        try {
            comm.프로필버튼클릭();
            TimeUnit.SECONDS.sleep(5);
            log.info("나의 프렌즈 ON");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/myGenieOnOff").click();
            comm.checkToastPopup("이제부터 북클럽프렌즈를 크게 볼 수 있습니다.");

            TimeUnit.SECONDS.sleep(5);
            log.info("나의 프렌즈 OFF");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/myGenieOnOff").click();
            comm.checkToastPopup("다시 터치하면 북클럽프렌즈를 크게 볼 수 있습니다.");

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("My 프로필 메인 구성 확인")
    public void my프로필메인구성확인() {
        try {
            log.info("My 프로필 메인 구성 확인");
            //화면구성을 통해 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/img_profile_pic").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/relative_reword_view").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/my_smartall_bookclub_view").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ll_tab").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ns_parent_webview_scroll").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_alarm").isDisplayed();

            assertTrue("My 프로필 메인 구성 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("라이브러리 화면 구성 확인")
    public void 라이브러리화면구성확인() {
        try {
            log.info("라이브러리 화면 구성 확인");
            //화면구성을 통해 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/banner").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recommendIcon").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/originalIcon").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/popularIcon").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/leftLabel").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rightLabel").isDisplayed();

            assertTrue("라이브러리 화면 구성 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("투데이 화면 구성 확인")
    public void 투데이화면구성확인() {
        try {
            log.info("투데이 화면 구성 확인");
            //화면구성을 통해 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/todayBook").isDisplayed()
                            && AndroidManager.getElementByText("AI맞춤 투데이").isDisplayed()
                            && AndroidManager.getElementByText("교과").isDisplayed()
                            && AndroidManager.getElementByText("메타버스").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/aiBanner").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/backgroundButton").isDisplayed();

            assertTrue("투데이 화면 구성 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("스마트씽크빅 화면 구성 확인")
    public void 스마트씽크빅화면구성확인() {
        try {
            log.info("스마트씽크빅 화면 구성 확인");
            //화면구성을 통해 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rbStudy").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnLiteracy").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnCurriculum").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnStudyLib").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnTogether").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnFriends").isDisplayed();
            assertTrue("라이브러리 화면 구성 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("AI맞춤 {string} 콘텐츠 확인")
    public void ai맞춤콘텐츠확인(String title) {
        try {
            log.info("AI맞춤 콘텐츠 클릭, {}", title);
            //화면 최상단으로 이동
            scrollToTop();
            try {
                if (title.equals("이번 주 추천 책")){
                    String expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recommendBook").getText();
                    assertEquals(expectedTitle,"이번 주 추천 책");
                    log.info("expectedTitle 확인: {}", expectedTitle);

                }
                else if (title.equals("AI맞춤 투데이에서 읽고 보는중")) {
                    String expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText();
                    assertEquals(expectedTitle, title);
                    log.info("{} 확인 == [{}]", title, expectedTitle);
                }
                else {
                    // 해당 타이틀의 위치로 이동해서 제목 확인
                    checkViewThumbnail(title);
                }

                WebElement parents = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/recyclerView", 0);
                parents.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail")).get(0).click();

            } catch (Exception e) {
                log.info("AI맞춤 콘텐츠 실행 오류");
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("AI맞춤 {string} 확인")
    public void todayRecommendBook(String title) throws InterruptedException {
        log.info("확인: {}", title);
        // 화면 최상단으로 이동하기
        scrollToTop();

        if (title.equals("이번 주 추천 책")){
            String expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recommendBook").getText();
            assertEquals(expectedTitle,"이번 주 추천 책");
            log.info("expectedTitle 확인: {}", expectedTitle);

        }
        else if (title.equals("AI맞춤 투데이에서 읽고 보는중")) {
            String expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText();
            assertEquals(expectedTitle,title);
            log.info("{} 확인 == [{}]", title, expectedTitle);
        }
        else {
            // 해당 타이틀의 위치로 이동해서 제목 확인
            checkViewThumbnail(title);
        }

        WebElement unit = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView");
        List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));
        log.info("콘텐츠 {}개 노출 확인", allUnits.size());
        // 0일 수 없음
        assertFalse("AI맞춤 구성 확인 되지 않습니다.", allUnits.isEmpty());
    }

    // 타이틀 확인을 위해 시작전 무조건 최상단에서 시작하기
    public void scrollToTop() throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            log.info("위로 이동");
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
        }
    }


    // 새로 나온 영상의 경우 화면 최하단에 위치하기 때문에 바로 최하단으로 가기
    public void scrollToBottom() throws InterruptedException {
        log.info("맨아래로");
        for (int i = 0; i <9; i++) {
            // 특정 위치 지정하여 스크롤
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    // 원하는 값을 찾아서 해당 값 확인
    //AI맞춤 {string} 확인
    public void checkViewThumbnail(String title) throws InterruptedException {
        if (title.equals("새로 나온 영상")){
            // 새로 나온 영상의 경우 화면 최하단에 위치하기 때문에 바로 최하단으로 가기
            scrollToBottom();
        }
        else if (title.equals("새로 나온 책")){
            // 새로 나온 영상의 경우 화면 최하단에 위치하기 때문에 바로 최하단으로 가기
            scrollToBottom();
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            AndroidManager.findTextAndSwipeToPosition(title, 250);
        }
        else if (title.equals("인기 세트 도서")){
            // 새로 나온 영상의 경우 화면 최하단에 위치하기 때문에 바로 최하단으로 가기
            scrollToBottom();
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            AndroidManager.findTextAndSwipeToPosition(title, 300);
        }
        else {
            // 찾는 text값을 특정 위치까지 가지고 와서 찾기 > 특정위치는 스위이프 되는 Y값 기준
            // 동일한 id 값일때 0번째 값을 찾기 때문에 해당 위치로 설정하여 찾고자 하는 값이 0번째가 되게 한 뒤 찾음
            AndroidManager.findTextAndSwipeToPosition(title, 300);
            // 화면 안정화를 위해 2초 대기
            TimeUnit.SECONDS.sleep(2);
        }
        String expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText();
        assertEquals(expectedTitle,title);
        log.info("{} 확인 == [{}]",title, expectedTitle);
    }


    @Then("투데이 스마트 독서 구성 확인")
    public void 투데이스마트독서구성확인() {
        try {
            String title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText();
            log.info("스마트 독서 주제 확인: {}", title);

            WebElement unit = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/main");
            List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/layout"));
            log.info("콘텐츠 {}개 노출 확인", allUnits.size());
            // 6개 노츨
            assertTrue("투데이-스마트독서 구성 확인 되지 않습니다.", !allUnits.isEmpty());
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("투데이 - 모두의 문해력 확인")
    public void 투데이모두의문해력확인() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        WebElement txt1 = AndroidManager.getElementByTextAfterSwipe("모두의 문해력");
        if (txt1.isDisplayed()) assertTrue(true);
        try {
            AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.j1/android.view.View/android.view.View/android.view.View[1]").click();
        } catch (Exception e) {
            Utils.touchSpecificCoordinates(73, 54);
        }
    }

    /**
     * 교과 투데이 화면구성 확인
     */
    @Then("교과 투데이 화면구성 확인")
    public void 교과투데이화면구성확인() {
        try {
            log.info("교과 투데이 화면구성 확인");
            TimeUnit.SECONDS.sleep(5);
            //4개 과목(국어/수학/사회/과학) 배치, 지난호보기 버튼
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/firstSection").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/secondSection").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/thirdSection").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/fourthSection").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/previous").isDisplayed(); //특정 과목 콘텐츠
            assertTrue("교과 투데이 화면구성이 확인되지 않습니다.", isDisplayedContents);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("{string} 과정테스트 확인")
    public void 국어과정테스트확인(String subject) {
        try {
            log.info("{} 과목 문항뷰어 확인", subject);
            TimeUnit.SECONDS.sleep(10);

            boolean isDisplayedContents = false;
            String imageName = Utils.takeScreenShot();
            String result = Utils.imageToText(imageName);
            log.info("image to text: {}", result);

            if (subject.equals("국어")) {
                isDisplayedContents = result.contains("동 건 이 가 나 무 에 올 라 간 까 닭 은 무 엇 인 가 요 ?") ||
                        result.contains("동건이가 나무에 올라간 까닭은 무엇인가요?");
            } else { // 초등국어
                isDisplayedContents = result.contains("이 름이 바 르 게 짝 지 어 진 것 은 어 느 것 인 가 요 ?") ||
                        result.contains("이름이 바르게 짝지어진 것은 어느 것인가요?");
            }

            assertTrue(isDisplayedContents);

        } catch (
                NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("문항뷰어 화면 확인")
    public void 문항뷰어화면확인() {
        try {
            bookWhen.이어서풀기나오면취소클릭();

            WebElement title = AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/tvTitleName");
            log.info("[{}] 문항뷰어 화면 확인", title.getText());
            assertEquals(title.getText(), "확인 문제");

            if (Utils.getDeviceType().equals("SM-T500")) {
                //1번 문항 채점 한 생태여서 화면구성 다른 기기와 다름 : 점수, 연습장, 개념보기, 풀이보기, 해설영상 버튼 노출
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/tvScore").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/ivDraw").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btnNote").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btnHintMovie").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btnSolveMovie").isDisplayed();
            } else {
                //화면구성 확인 : 점수, 연습장, 채점하기 버튼 노출
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/tvScore").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/ivDraw").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btnEachResult").isDisplayed();

                assertTrue("확인문제 문항뷰어 화면 구성이 확인되지 않습니다.", isDisplayedContents);
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * 문항 팝업 확인
     */
    @Then("문항 {string} 팝업 확인")
    public void 문항팝업확인(String toastText) {
        try {
            try {
                log.info("{} 문구의 팝업을 확인합니다.", toastText);
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/tv_msg");
                String result = element.getText();
                assertTrue("예상한 팝업이 노출되지 않았습니다. 실제결과: " + result + " expected contains text: " + toastText, result.contains(toastText));
            } catch (Exception e) {
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("라이브러리 홈 추천 도서 {string} 확인")
    @Then("라이브러리 홈 {string} 확인")
    public void 라이브러리홈추천도서확인(String menu) {
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("{} 클릭", menu);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/banner").click();
            log.info("북클럽 라이브러리 홈 배너 확인");
            try {
                TimeUnit.SECONDS.sleep(2);
                WebElement banner = AndroidManager.getElementByText("북클럽 레터");
                if (banner.isDisplayed()) {
                    boolean content = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/thumbnail").isDisplayed();
                    assertTrue("북클럽 레더 확인 안됩니다.", content);
                    // 뒤로가기
                    TimeUnit.SECONDS.sleep(1);
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back").click();
                }
            } catch (Exception e) {
                try {
                    log.info("배너 추천도서 확인");
                    TimeUnit.SECONDS.sleep(2);
                    String title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title").getText();
                    // 북클럽 레터 3번째 콘텐츠 클릭 및 확인
                    bookWhen.북클럽레터메인번째콘텐츠클릭(3);
                    북클럽레터메인콘텐츠확인();
                    // 뒤로가기
                    TimeUnit.SECONDS.sleep(1);
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_toolbar_back").click();
                } catch (Exception ie) {
                    try {
                        log.info("학습 베너");
                        TimeUnit.SECONDS.sleep(5);
                        Utils.touchCenterInViewer(getDriver());
                        WebElement event = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtTitleForEPUB");
                        assertTrue("북클럽 레더 확인 안됩니다.", event.isDisplayed());
//                        assertEquals(event.getText(),"<모두의 문해력> 순공 챌린지 이벤트...");
                        AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/today_button_home").click();
                        return;
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 북클럽 레터 메인 콘텐츠 확인
     */
    @Then("북클럽 레터 메인 콘텐츠 확인")
    public void 북클럽레터메인콘텐츠확인() {
        try {

            comm.clickCastBookOkBtn();
            comm.notReadingContinue("No");
            try {
                TimeUnit.SECONDS.sleep(2);
                WebElement bana = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/imageViewBana");
                if (bana.isDisplayed()) {
                    log.info("오디오이북 바나쿠 팝업 노출");
                    Utils.touchBottomInViewer(getDriver());
                }
            } catch (Exception ie) {
            }
            try {
                TimeUnit.SECONDS.sleep(5);
                log.info("북클럽 레터 제목 확인");
                Utils.touchBottomInViewer(getDriver());
                WebElement title = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtPieceHeadTitle");
                if (title.isDisplayed()) {
                    log.info(title.getText());
                    BookClubLetterExit();
                    bookWhen.짝꿍책나가기();
                }
            } catch (Exception e) {
                TimeUnit.SECONDS.sleep(5);
                log.info("Exception 북클럽 레터 제목 확인");
                Utils.touchBottomInViewer(getDriver());
                WebElement title = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtTitleForEPUB");
                if (title.isDisplayed()) {
                    log.info(title.getText());
                    BookClubLetterExit();
                    bookWhen.짝꿍책나가기();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void BookClubLetterExit() {
        try {
            try {
                log.info("북클럽 레터 콘텐츠 나가기");
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/groupCloseButton").click();
            } catch (Exception e) {
                try {
                    log.info("Exception : 북클럽 레터 콘텐츠 나가기");
                    AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/today_button_home").click();
                } catch (Exception e1) {
                    try {
                        log.info("북클럽 레터 콘텐츠 나가기");
                        Utils.touchCenterInViewer(getDriver());
                        AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/groupCloseButton").click();
                    } catch (Exception e2) {
                        TimeUnit.SECONDS.sleep(5);
                        log.info("Exception : 북클럽 레터 콘텐츠 나가기");
                        Utils.touchCenterInViewer(getDriver());
                        AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/today_button_home").click();
                    }
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("초등홈 북클럽 강력 추천-{string} 학년 확인")
    public void 라이브러리초등홈북클럽강력추천확인(String title) {
        try {
            // 계정 학년 설정 : 1,2,3 == 저학년 / 4,5,6 == 고학년
            log.info("라이브러리 홈 북클럽 강력 추천 콘텐츠 타이틀 {} 확인", title);
            WebElement con_title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");

            String expectedTitle;

            //  T500 4학년 설정, X200 저학년 설정
            boolean isSenior = Utils.getDeviceType().equals("SM-T500");

            switch (title) {
                case "전문가 추천":
                    expectedTitle = isSenior ? "전문가 추천 (고학년)" : "전문가 추천 (저학년)";
                    break;
                case "교과수록 필독서":
                    expectedTitle = isSenior ? "교과서 수록 & 초등 필독서 (고학년)" : "교과서 수록 & 초등 필독서 (저학년)";
                    break;
                default:
                    throw new IllegalArgumentException("title: " + title);
            }

            log.info("{} 확인", con_title.getText());
            assertEquals(con_title.getText(), expectedTitle);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("라이브러리 {string}홈 {string} 진입 확인")
    public void 라이브러리홈북클럽오리지널진입확인(String year, String menu) {
        try {
            String expectedTitle = "";

            if (menu.equals("북클럽 오리지널")) {
                switch (year) {
                    case "초등":
                        expectedTitle = "북클럽 오리지널 (초등)";
                        break;
                    case "유아":
                        expectedTitle = "북클럽 오리지널 (유아)";
                        break;
                    default:
                        throw new IllegalArgumentException("올바르지 않은 year 값: " + year);
                }
            } else {
                log.info("인기 시리즈/독서 놀이터 세트");
                expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title").getText();
            }
            // 2초 대기
            TimeUnit.SECONDS.sleep(2);

            // 현재 타이틀 가져오기
            WebElement ele_tilte = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");
            log.info("{} 홈 북클럽 {} : {} 확인", year, menu, ele_tilte.getText());
            // 예상 타이틀과 실제 타이틀 비교
            assertEquals(ele_tilte.getText(), expectedTitle);

            // 썸네일 및 기타 요소 확인
            라이브러리썸네일및기타확인();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 라이브러리썸네일및기타확인() {
        // 세트 목록에서 0번째 선택 후, 오른쪽 콘텐츠 화면에서 노출되는 썸네일 확인
        log.info("썸네일등 노출 영역 확인");
        boolean contents = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_library_set_title").isDisplayed()
                || AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_all_play").isDisplayed()
                || AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_all_download").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_library_set_contents").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/iv_item_content_thumbnail").isDisplayed();

        assertTrue("라이브러리 콘텐츠 화면 확인 되지 않습니다.", contents);
    }

    /**
     * 라이브러리 홈 분야 전체 콘텐츠 제목 저장해서 확인하기
     */
    @Then("라이브러리 초등홈 분야별 인기 메뉴 {string} 진입 확인")
    @Then("라이브러리 유아홈 분야별 인기 메뉴 {string} 진입 확인")
    public void 라이브러리초등홈분야별인기메뉴진입확인(String title) {
        try {
            TimeUnit.SECONDS.sleep(1);
            log.info("라이브러리 초등홈 분야별 메뉴 화면 클릭");
            //화면 상당으로 이동
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            //화면 하단으로 이동
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);

            if (title.equals("전체보기")) {
                log.info(title + " 확인");
                // 전체보기 화면으로 이동 되었는지 확인
                totalView();

            } else {
                // 인기분야 진입 확인
                popularField(title);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 라이브러리 홈 분야 전체 콘텐츠 제목 저장해서 확인하기
     * 콘텐츠 클릭해서 콘텐츠 진입 확인까지
     */
    @Then("라이브러리 초등홈 분야별 인기 메뉴 {string} 확인")
    @Then("라이브러리 유아홈 분야별 인기 메뉴 {string} 확인")
    public void 라이브러리초등홈분야별인기메뉴클릭(String title) {
        try {
            TimeUnit.SECONDS.sleep(1);
            log.info("라이브러리 초등홈 분야별 메뉴 화면 클릭");
            //화면 상당으로 이동
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            //화면 하단으로 이동
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);

            if (title.equals("전체보기")) {
                log.info(title + " 확인");
                // 전체보기 화면으로 이동 되었는지 확인
                totalView();

            } else {
                popularField(title);
                bookWhen.라이브러리홈추천도서세트콘텐츠클릭();
                라이브러리홈추천도서콘텐츠실행확인();
                bookWhen.라이브러리뒤로가기버튼클릭();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
     * 북클럽 - 초등홈 - 분야별 메뉴 - 전체메뉴 이동 확인
     * 북클럽 - 유아홈 - 인기 분야 - 전체메뉴 이동 확인
     * */
    public void totalView() {
        try {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/showAll").click();

            TimeUnit.SECONDS.sleep(3);
            log.info("북클럽 라이브러리 전체메뉴 화면구성 확인");

            // 탭 레이아웃 요소를 찾습니다
            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tabLayout");
            // 탭 레이아웃 내의 모든 루트 요소를 찾습니다
            List<WebElement> allTextViews = element.findElements(By.className("android.view.ViewGroup"));
            // 찾은 요소의 개수를 로그에 기록합니다
            log.info("전체 메뉴 단계 {}개 확인", allTextViews.size());

            // 예상되는 레벨 목록을 정의합니다
            List<String> allLevels = Arrays.asList("Lv.1", "Lv.2~4", "Lv.5~6", "Lv.7", "Lv.7이상~");
            // 모든 항목이 일치하는지 추적하는 변수를 초기화합니다
            boolean allMatch = true;

            // 모든 텍스트 뷰에 대해 반복합니다
            for (int i = 1; i <= 5; i++) {
                // 각 단계의 레이블 요소를 찾습니다
                element = AndroidManager.getElementByXpath("//*[contains(@class, 'android.widget.LinearLayout')]/android.view.ViewGroup["
                        + i + "]/android.view.ViewGroup/android.widget.TextView");
                String subTitleText = element.getText();

                // 현재 인덱스가 예상 레벨 목록의 범위 내에 있고, 텍스트가 일치하는지 확인합니다
                if (i - 1 < allLevels.size() && subTitleText.equals(allLevels.get(i - 1))) {
                    // 현재 단계의 UI를 확인했다는 로그를 기록합니다
                    log.info("{} 단계 UI 확인", subTitleText);
                    // 모든 항목이 일치하는지 최종 확인합니다
                    assertTrue("북클럽 라이브러리 전체메뉴 화면구성이 예상과 다릅니다.", allMatch);
                } else {
                    // 불일치하는 경우 오류 로그를 기록합니다
                    log.error("{}번째 단계 불일치: 예상값 {}, 실제값 {}", i,
                            (i - 1 < allLevels.size()) ? allLevels.get(i - 1) : "없음", subTitleText);
                    // 불일치가 발생했으므로 allMatch를 false로 설정합니다
                    allMatch = false;
                }
            }
        } catch (
                NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * 라이브러리 초등홈 분야별 메뉴 인기6개 인덱스 순서 가져오기
     * 메뉴 6개 추천은 인기순 > 그때 그때 변동 됨
     */
    public int getIndexFromTitle(String title) {
        switch (title) {
            case "1":
                return 0;
            case "2":
                return 1;
            case "3":
                return 2;
            case "4":
                return 3;
            case "5":
                return 4;
            case "6":
                return 5;
            default:
                throw new IllegalArgumentException("Unexpected title: " + title);
        }
    }

    /*
     *  분야별 메뉴 6개 추천은 인기순 >
     * 그때 그때 변동 됨 따라서 인덱스 순서로 클릭해여 해당 분야 확인
     */
    public void popularField(String title) {
        try {
            // 분야별 메뉴 6개 추천은 인기순 > 그때 그때 변동 됨 따라서 인덱스 순서로 클릭해여 해당 분야 확인
            int num = getIndexFromTitle(title);

            WebElement popularBook = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/popularBookRecyclerView");
            String titleName = "";
            // 인기 메뉴 순서대로 클릭전 이름 저장
            titleName = popularBook.findElements(By.id("com.wjthinkbig.mlauncher2:id/name")).get(num).getText();

            log.info("분야별 인기 메뉴 {} 확인", title);

            //해당 id 클릭
            popularBook.findElements(By.id("com.wjthinkbig.mlauncher2:id/rootLayout")).get(num).click();
            TimeUnit.SECONDS.sleep(2);

            // 클릭 후 메뉴 타이틀 이름 저장
            WebElement ele_title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");
            String comtentNameForMatching = "";
            comtentNameForMatching = ele_title.getText();

            // 클릭전 메뉴와 클릭 후 메뉴 동일 한지 확인
            if (titleName.equals(comtentNameForMatching)) {
                log.info("분야별 인기 메뉴 [{}]: {} 확인", titleName, comtentNameForMatching);
                라이브러리썸네일및기타확인();
            } else {
                log.info("분야별 인기 메뉴 [{}] : {} 확인", titleName, comtentNameForMatching);
                fail("초등홈 북클럽 분야별 인기 메뉴 정상 노출 되지 않았습니다.");
            }
        } catch (
                NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("북클럽 레터 회면 구성 확인")
    public void 북클럽레터회면구성확인() {
        WebElement unit = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/RecyclerView");
        List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));

        // 5열 2행 10개
        log.info("콘텐츠 {}개 노출 확인", allUnits.size());
        assertEquals(allUnits.size(), 10);
        assertTrue("이번 주 라벨이 0번째 콘텐츠에 없습니다.",
                unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/badge")).get(0).isDisplayed());

    }


    @Then("라이브러리 내 책장 {string} 확인")
    public void 라이브러리내책장확인(String bookLabel) {
        try {
            log.info("북클럽 내 책장 상단 메뉴: [{}] 확인", bookLabel);

            switch (bookLabel) {
                case "읽던 책 끝까지 읽기":
                    라이브러리읽던책끝까지읽기();
                    break;
                case "생각 표현하기":
                    log.info("생각 표현하기 확인");
                    boolean albumBooks = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/albumBooks").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/albumStar").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/bookDiaryButton").isDisplayed();
                    assertTrue("생각 표현하기 확인되지 않습니다.", albumBooks);
                    break;
                case "도전 100권 읽기":
                    log.info("도전 100권 읽기");
                    boolean challenge =
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/challengeDisable").isDisplayed();
                    assertTrue("도전 100권 읽기 상단 콘텐츠가 없습니다.", challenge);
                    break;
            }

        } catch (
                NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 라이브러리읽던책끝까지읽기() {
        try {
            boolean allbooks =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readingBookLabel").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/stars").isDisplayed();
            assertTrue("읽던 책 끝까지 읽기 확인되지 않습니다.", allbooks);

            WebElement readingBooks = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readingBooks");
            List<WebElement> books = readingBooks.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));
            // 읽던 책 최대 3권 노출되어야 함
            if (readingBooks.isDisplayed()) {
                log.info("읽던 책 끝까지 읽기 {}개 노출 확인", books.size());

            }
        } catch (Exception e) {
            // 하루에 정해진 책을 다 읽으면 더이상 책 노출 안 됨
            WebElement complete = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readingBooksNoContents");
            if (complete.isDisplayed()) log.info("읽던 책 끝까지 읽기 다 읽음");
        }

    }


    @Then("라이브러리 내 책장 {string} 콘텐츠 확인")
    public void 라이브러리내책장상단콘텐츠확인(String menu) throws InterruptedException {
        log.info("{} 콘텐츠 확인", menu);
        if (menu.equals("도전 100권 읽기")) {
            log.info("도전100권 UI 확인");
            TimeUnit.SECONDS.sleep(3);
            boolean contents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/challengeDisable").isDisplayed();

            assertTrue("100권읽기 확인 되지 않습니다.", contents);
        } else {
            라이브러리홈추천도서콘텐츠실행확인();
        }

    }


    @Then("라이브러리 홈 추천 도서 콘텐츠 실행 확인")
    public void 라이브러리홈추천도서콘텐츠실행확인() throws InterruptedException {
        TimeUnit.SECONDS.sleep(6);

        try {
            if (AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_toolbar_title").isDisplayed()) {
                bookWhen.상세정보팝업창에서미리보기바로보기버튼클릭();
                bookWhen.라이브러리뒤로가기버튼클릭();
                bookWhen.상세정보팝업창닫기버튼클릭();
                return;
            }
        } catch (Exception e) {
        }
        comm.checkFirstHelpLayoutScreen();      // 코치마크 노출 시 닫기
        comm.clickCastBookOkBtn();               // 속속 캐스트북 확인 버튼
        comm.notReadingContinue("No");  // 이어보기 NO
        TimeUnit.SECONDS.sleep(2);
        bookWhen.오디오북종료하기();
    }


    /**
     * 라이브러리 전체메뉴 독서 라이브러리 서브메뉴 클릭
     */
    @And("라이브러리 전체메뉴 독서 라이브러리 {string} 서브메뉴 클릭")
    @And("라이브러리 전체메뉴 {string} {string} 클릭")
    public void 라이브러리전체메뉴독서라이브러리서브메뉴클릭(String tab, String menu) {
        try {
            TimeUnit.SECONDS.sleep(3);
            라이브러리전체메뉴메뉴클릭(tab);
            TimeUnit.SECONDS.sleep(3);
            log.info("{} 클릭", menu);
            if (menu.equals("오늘도 즐거워!")) AndroidManager.getElementByTextAfterSwipe(menu + " ").click();
            else AndroidManager.getElementByTextAfterSwipe(menu).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 라이브러리전체메뉴메뉴클릭(String tab) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        int i = 0;
        switch (tab) {
            case "1":
                i = 0;
                break;
            case "2":
                i = 1;
                break;
            case "3":
                i = 2;
                break;
            case "4":
                i = 3;
                break;
            case "5":
                i = 4;
                break;
        }

        WebElement menu = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/viewPager")
                .findElement(By.id("com.wjthinkbig.mlauncher2:id/tabLayout"))
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/label")).get(i);

        log.info("홈 > 라이브러리 > {} 클릭", menu.getText());
        menu.click();
    }

    // 메뉴에 따른 인덱스 가져오기
    private int getMenuIndex(String menu) {
        switch (menu) {
            case "추천 키워드":
                return 0;
            case "분야 전체":
                return 1;
            case "새로나온 콘텐츠":
                return 2;
            case "교과서 수록 도서":
                return 3;
            case "기관 추천 어린이 도서":
                return 4;
            case "기관 추천 청소년 도서":
                return 5;
            case "그림책":
                return 6;
            case "문학":
                return 7;
            case "과학/수학":
                return 8;
            case "사회/문화":
                return 9;
            case "인성•철학":
                return 10;
            case "인물":
                return 11;
            case "역사":
                return 12;
            case "백과":
                return 13;
            case "유아학습":
                return 14;
            case "초등학습":
                return 15;
            case "중고등 필독서":
                return 16;
            case "아트":
                return 17;
            case "뮤직":
                return 18;  // 화면 스와이프 및 인덱스 설정 변경
            case "만화":
                return 19;
            case "토이":
                return 20;
            case "생활주제책읽기":
                return 21;
            case "안전•생활 습관":
                return 22;
            case "의사소통":
                return 23;
            case "사회관계":
                return 24;
            case "자연탐구":
                return 25;
            case "예술경험":
                return 26;
            case "신체운동건강":
                return 27;
            case "플레이북":
                return 28;
            case "투데이":
                return 29;
            case "Animation":
                return 30;
            case "Storybook":
                return 31;
            case "Readers":
                return 32;
            case "인터랙티브북":
                return 46;
            case "Interactive Book":
                return 47;
            case "AR Science":
                return 48;
            case "앱":
                return 49;
            default:
                return 0;
        }
    }


    @Then("{string} 교과 연계 도서 진입 확인")
    public void 교과연계도서진입확인(String menu) {
        try {
            log.info("{} 진입 확인", menu);
            TimeUnit.SECONDS.sleep(2);

            // 메뉴에 따른 인덱스와 타이틀 설정
            MenuInfo menuInfo = getMenuInfo(menu);

            // 메뉴 요소 찾기
            WebElement listIdx = findMenuElement(menuInfo.idx);

            // 타이틀 확인
            verifyTitle(menuInfo.title);

            // 선택된 메뉴 텍스트 확인
            verifySelectedMenu(listIdx, menu);

            // 뒤로 가기
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_toolbar_back").click();

        } catch (NoSuchElementException e) {
            fail("요소를 찾을 수 없습니다");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("키즈 라이브올 확인")
    public void 키즈라이브올확인() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        String imageName = Utils.takeScreenShot();
        String result = Utils.imageToText(imageName);
        log.info("image to text: {}", result);
        assertTrue("키즈 라이브올 화면이 아닙니다.",
                result.contains("아 이 브 올")
                || result.contains("라 이 브 올")
                || result.contains("라이브올")
                );

        // 나가기
        AndroidManager.getElementByText("뒤로 가기").click();
    }

    // 책 클릭해서 열리는지 확인 하는 내부 클래스
    @Then("추천도서 확인")
    public void bookCountOpenCheck(){
        // 상단 노출
        bookWhen.showUpperBar();
        WebElement title = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtPieceHeadTitle");
        assertTrue("추천책 열리지 않았습니다.",title.isDisplayed());
        log.info("추천책 : [{}] 확인",title.getText());
    }

    // 메뉴 정보를 담는 내부 클래스
    public static class MenuInfo {
        int idx;
        String title;

        MenuInfo(int idx, String title) {
            this.idx = idx;
            this.title = title;
        }
    }

    // 메뉴에 따른 인덱스와 타이틀 반환
    public MenuInfo getMenuInfo(String menu) {
        switch (menu) {
            case "누리 1단계":
                return new MenuInfo(0, "누리 과정 연계 도서");
            case "누리 2단계":
                return new MenuInfo(1, "누리 과정 연계 도서");
            case "누리 3단계":
                return new MenuInfo(2, "누리 과정 연계 도서");
            case "예비 초등":
                return new MenuInfo(3, "초등 교과 연계 도서");
            case "초등 1학년":
                return new MenuInfo(4, "초등 교과 연계 도서");
            case "초등 2학년":
                return new MenuInfo(5, "초등 교과 연계 도서");
            case "초등 3학년":
                return new MenuInfo(6, "초등 교과 연계 도서");
            case "초등 4학년":
                return new MenuInfo(7, "초등 교과 연계 도서");
            case "초등 5학년":
                return new MenuInfo(8, "초등 교과 연계 도서");
            case "초등 6학년":
                return new MenuInfo(9, "초등 교과 연계 도서");
            case "중등":
                return new MenuInfo(10, "중등 필독서");
            default:
                throw new IllegalArgumentException("잘못된 메뉴: " + menu);
        }
    }

    // 메뉴 요소 찾기
    public WebElement findMenuElement(int idx) {
        return AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_library_curriculum_filter")
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/tv_item_filter_expandable_title")).get(idx);
    }

    // 타이틀 확인
    public void verifyTitle(String expectedTitle) {
        WebElement titleMenu = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");
        Assert.assertEquals(titleMenu.getText().trim(), expectedTitle);
    }

    // 선택된 메뉴 텍스트 확인
    public void verifySelectedMenu(WebElement listIdx, String expectedMenu) {
        Assert.assertEquals(listIdx.getText().trim(), expectedMenu);
    }


    @Then("전체메뉴 {string} 진입 확인")
    public void 전체메뉴카테고리진입확인(String menu) {
        try {
            log.info("{} 진입 확인", menu);
            try {
                WebElement titleMenu = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");
                // 클릭한 메뉴명 확인 되어야 함
                Assert.assertEquals(titleMenu.getText().trim(), menu);

                // 버튼 2개 확인 (세트버튼/구성버튼)
                boolean isDisplay = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_list").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_composition").isDisplayed();
                assertTrue("라이브러리 전제메뉴 세트 진입 확인되지 않습니다.",isDisplay);

                // 뒤로가기
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_toolbar_back").click();

            } catch (Exception e) {
                log.info("{} 메뉴 진입 확인 안됩니다.", menu);
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("프로필 설정 팝업 확인")
    public void My() {
        // 프로필 선택
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/img_profile_pic").click();
        boolean profile_pop =
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_custom_popup_subtitle").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/gallery").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/camera").isDisplayed();
        assertTrue("프로필 변경 팝업 노출되지 않았습니다.", profile_pop);
        // 나가기
        getElementById("com.wjthinkbig.mlauncher2:id/exit").click();
    }


    @Then("나의 리워드 별 확인")
    public void starshop() {
        try {
//          나의 리워드 선택 >> 스타샵 이동 , 알림
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/startshoplayout").click();

            bookWhen.starBtn("내 별 현황");
            boolean startshopTap01 =
                    AndroidManager.getElementByText("스타샵").isDisplayed()
                            && AndroidManager.getElementByText("번호").isDisplayed()
                            && AndroidManager.getElementByText("구분").isDisplayed()
                            && AndroidManager.getElementByText("상세 내용").isDisplayed()
                            && AndroidManager.getElementByText("날짜").isDisplayed()
                            && AndroidManager.getElementByText("별").isDisplayed();
            assertTrue("내 별 현황 노출되지 않았습니다.", startshopTap01);

            bookWhen.starBtn("나의 목표");
            boolean myGoal =
                    AndroidManager.getElementByText("목표 설정 변경").isDisplayed();
            assertTrue("프로필 변경 팝업 노출되지 않았습니다.", myGoal);

            bookWhen.starBtn("무엇을 바꿔볼까?");
            boolean exchangeGifts =
                    AndroidManager.getElementByText("선물 교환 내역").isDisplayed()
                            && AndroidManager.getElementByText("선물 이용 방법").isDisplayed();
            assertTrue("선물 교환 페이지 노출되지 않았습니다.", exchangeGifts);

            // 나가기
            AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/btnExit").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("우상단 알림 확인")
    public void 우상단알림확인() {
        try {
            log.info("My > 알람 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_alarm").click();
            TimeUnit.SECONDS.sleep(2);
            boolean display =
                    AndroidManager.getElementByText("알림").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rl_empty_area").isDisplayed();
            assertTrue("알림 화면 노출되지 않았습니다.", display);

            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rl_back_area").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("설정 뒤로가기 클릭")
    public void 설정뒤로가기클릭() {
        try {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("내 작품 보기 {string} 화면구성 확인")
    public void 독서앨범내작품보기탭화면구성확인(String menu) {
        try {
            log.info("독서앨범 내 작품 보기 탭 {} 확인", menu);
            try {
                //콘텐츠가 없는 경우 화면 버튼 클릭헤서 이동 화면 확인
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/no_content_view");
                if (element.isDisplayed()) {
                    log.info("{} 버튼 클릭", menu);
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/no_content_text").click();
                    메뉴이동확인(menu);
                }
            } catch (Exception e) {
                // 저장된 콘템츠가 있다면 확인
                switch (menu) {
                    case "사용자 오디오북":
                        checkUserAudiobooks();
                        break;
                    case "감상문":
                        checkReviews();
                        break;
                    case "사진":
                        checkPhotos();
                        break;
                    // case "동영상":
                    // case "일기":
                    // case "학습":
                    //     향후 추가될 메뉴에 대한 처리
                    //     break;
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    // 사용자 오디오북 확인 메소드
    public void checkUserAudiobooks() {
        WebElement gridView = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/gridView");
        List<WebElement> thumbnails = gridView.findElements(By.id("com.wjthinkbig.thinkplayground:id/thumbnail"));
        log.info("사용자 오디오북 {}개 노출 확인", thumbnails.size());
        assertTrue("독서앨범 내 작품 보기 사용자 오디오북 확인되지 않습니다.", gridView.isDisplayed());
    }

    // 감상문 확인 메소드
    public void checkReviews() {
        WebElement recyclerView = AndroidManager.getElementById("androidx.recyclerview.widget.RecyclerView");
        List<WebElement> bookTitles = recyclerView.findElements(By.id("com.wjthinkbig.thinkplayground:id/book_title"));
        log.info("감상문 {}개 노출 확인", bookTitles.size());
        assertTrue("독서앨범 내 작품 보기 감상문 확인되지 않습니다.", recyclerView.isDisplayed());
    }

    // 사진 확인 메소드
    public void checkPhotos() {
        WebElement gridView = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/gridView");
        List<WebElement> photoItems = gridView.findElements(By.id("com.wjthinkbig.thinkplayground:id/photo_grid_items"));
        log.info("사진 {}개 노출 확인", photoItems.size());
        assertTrue("독서앨범 내 작품 보기 사진 확인되지 않습니다.", gridView.isDisplayed());
    }


    @Then("{string} 메뉴 이동 확인")
    public void 메뉴이동확인(String title) {
        try {
            log.info("독서앨범 {} 이동 확인", title);
            if (title.equals("라이브러리")) {
                boolean isDisplayedContents =
                        AndroidManager.getElementByXpath(Constant.라이브러리_xPath).isDisplayed();
                assertTrue("라이브러리 메뉴가 선택되지 않았습니다.", isDisplayedContents);
            } else if (title.equals("일기쓰기") || title.equals("일기")) {
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.mbookdiaryactivitytool:id/iv_template_title").isDisplayed();
                assertTrue("일기쓰기 메뉴가 선택되지 않았습니다.", isDisplayedContents);
                AndroidManager.getElementById("com.wjthinkbig.mbookdiaryactivitytool:id/btnClosed").click();
            } else if (title.equals("사진") || title.equals("동영상")) {
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.mcamera2:id/btnCapture").isDisplayed();
                assertTrue("카메라 메뉴가 선택되지 않았습니다.", isDisplayedContents);
                AndroidManager.getElementById("com.wjthinkbig.mcamera2:id/btnClose").click();
            } else if (title.equals("학습투데이") || title.equals("학습")) {
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rbStudy").isDisplayed();
                assertTrue("학습투데이 메뉴가 선택되지 않았습니다.", isDisplayedContents);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }

    /**
     * 독서앨범 새소식 콘텐츠 확인
     */
    @Then("독서앨범 새소식 화면 구성 확인")
    @Then("독서앨범 새소식 콘텐츠 확인")
    public void 독서앨범새소식콘텐츠확인() {
        try {
            log.info("독서앨범 새소식 콘텐츠 확인");
            try {
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/no_news_img");
                if (element.isDisplayed()) return;
            } catch (Exception e) {
            }
            TimeUnit.SECONDS.sleep(2);
            //콘텐츠 확인 로직
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 새소식 추가 화면구성 확인
     */
    @Then("독서앨범 새소식 추가 화면구성 확인")
    public void 독서앨범새소식추가화면구성확인() {
        try {
            log.info("독서앨범 새소식 추가 화면구성 확인");

            //라이브러리/일기쓰기/카메라/학습투데이 영역 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/menu_library").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/menu_diary").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/menu_camera").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/menu_study").isDisplayed();

            assertTrue("독서앨범 새소식 추가 화면구성이 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("독서앨범 {string} 확인")
    public void 확인(String menu) {
        try {
            log.info("독서앨범 {} 확인", menu);
            //화면구성을 통해 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/top_ten_area").isDisplayed();
            assertTrue("독서앨범 친구 작품 보기 화면 구성 확인되지 않습니다.", isDisplayedContents);

            랭킹이미지카운트확인();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 랭킹이미지카운트확인() {
        try {
            log.info("랭킹 이미지 카운트 확인");

            // 상위 recyclerview 요소 찾기
            WebElement recyclerview = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/recyclerview");
            // recyclerview 내의 모든 img_ranking 요소 찾기
            List<WebElement> rankingImages = recyclerview.findElements(By.id("com.wjthinkbig.thinkplayground:id/img_ranking"));

            // 찾은 요소의 개수 확인
            int count = rankingImages.size();
//            log.info("랭킹 이미지 총 개수: {}", count);

            // 첫 번째 화면에서 5개 확인
            int firstScreenCount = Math.min(count, 5);
            log.info("첫 번째 화면 랭킹 이미지 개수: {}", firstScreenCount);
            assertEquals("첫 번째 화면의 랭킹 이미지 개수가 5개가 아닙니다.", 5, firstScreenCount);

            // 오른쪽으로 스와이프
            Utils.swipeScreen(Utils.Direction.LEFT);
            TimeUnit.SECONDS.sleep(1); // 스와이프 후 화면 안정화를 위한 대기
            Utils.swipeScreen(Utils.Direction.LEFT);
            TimeUnit.SECONDS.sleep(2); // 스와이프 후 화면 안정화를 위한 대기

            // 맨앞 이미지는 요소는 보이나 랭킹 숫자는 안보임 >> 5번 랭킹 앞에서 이미 카운트
            // 스와이프 후 두 번째 화면에서 5개 확인 (맨 앞의 요소 제외)
            recyclerview = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/recyclerview");
            rankingImages = recyclerview.findElements(By.id("com.wjthinkbig.thinkplayground:id/img_ranking"));
            int secondScreenCount = Math.min(rankingImages.size(), 5);
            log.info("두 번째 화면 랭킹 이미지 개수: {}", secondScreenCount);
            assertEquals("두 번째 화면의 랭킹 이미지 개수가 5개가 아닙니다.", 5, secondScreenCount);

        } catch (NoSuchElementException e) {
            fail("요소를 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            fail("예외 발생: " + e.getMessage());
        }
    }


    @Then("검색 결과 {string} 배너 확인")
    public void 검색결과배너확인(String menu) {
        try {
            log.info("검색결과 배너 {} 확인", menu);
            WebElement element;
            int idx = 33;
            switch (menu) {
                case "전체":
                    idx = 0;
                    break;
                case "독서":
                    idx = 1;
                    break;
                case "학습영상":
                    idx = 2;
                    break;
            }
            if (idx != 33) {
                log.info("검색결과 배너 {} 클릭", menu);
                element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.dictionary:id/tab_math", idx);
                element.click();
                TimeUnit.SECONDS.sleep(15);
                if (element.isSelected()) {
                    switch (menu) {
                        case "전체":
                            검색화면확인();
                            break;
                        case "독서":
                            String[] typeOptions = {"이북", "오디오 이북", "멀티 터치북", "동영상", "인터랙티브북", "전체"};
                            for (String typeOption : typeOptions) {
                                TimeUnit.SECONDS.sleep(5);
                                검색결과독서유형확인(typeOption);
                            }
                            TimeUnit.SECONDS.sleep(5);
                            String[] menuOptions = {"영아 0~3세", "유아 4~6세", "초저 7~9세", "초고 10~13세"};
                            for (String menuOption : menuOptions) {
                                TimeUnit.SECONDS.sleep(5);
                                검색결과독서연령확인(menuOption);
                            }
                            TimeUnit.SECONDS.sleep(5);
                            무료유료유형확인();
                            String[] btnOptions = {"정확도순", "연령순", "신작순", "인기순"};
                            for (String btnOption : btnOptions) {
                                콘텐츠노출순서버튼클릭(btnOption);
                                // 순서 변경 확인 로직 만들어야 함
                            }
                            break;
                        case "포토백과":
                            포토백과확인();
                            break;
                        case "학습영상":
                            학습영상확인("초등 1학년", "바슬즐");    // UI변경 봄여름가을겨울>바슬즐
                            TimeUnit.SECONDS.sleep(3);
                            학습영상확인("초등 3학년", "사회");
                            break;
                    }
                    AndroidManager.getElementById("com.wjthinkbig.dictionary:id/close").click();
                }
            } else if (menu.equals("스마트올 백과 보기")) {
                log.info("검색결과 배너 {} 클릭", menu);
                AndroidManager.getElementById("com.wjthinkbig.dictionary:id/encyclopedia_btn").click();
                TimeUnit.SECONDS.sleep(25);
                친구들이많이찾는검색어확인();
//                AndroidManager.getElementByTextAfterSwipe("메인화면으로 돌아가기").click();
                스마트올백과좌우스크롤동작("연관 검색어");
                스마트올백과좌우스크롤동작("사전 구분");
                스마트올백과좌우스크롤동작("맨위로");
                TimeUnit.SECONDS.sleep(5);
                스마트올백과클릭();
                TimeUnit.SECONDS.sleep(15);
                스마트올백과확인("포토백과");
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("검색 화면 확인")
    public void 검색화면확인() {
        try {
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.dictionary:id/block_book_result").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.dictionary:id/encyclopedia_btn").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.dictionary:id/layout_tab_container").isDisplayed();
            assertTrue("검색 화면구성이 확인되지 않습니다.", isDisplayedContents);
            try {
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/txt_related_keyword");
                if (element.isDisplayed()) {
                    String keyword = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/input_txt").getText();
                    String result = element.getText();
                    log.info("{} 연관검색어 : {} 확인", keyword, result);
                }
            } catch (Exception e) {
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    public void 검색결과독서유형확인(String type) {
        try {
            bookWhen.라이브러리투데이도서서브메뉴클릭("유형");
            bookWhen.검색결과독서유형클릭(type);
            TimeUnit.SECONDS.sleep(1);
            log.info("{} 확인", type);
            if (type.equals("동영상")) {
                boolean contents = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/recyclerViewVolResult").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/text_level").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/play_btn").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/text_movie_title").isDisplayed();
                assertTrue("검색 결과 화면이 아닙니다.", contents);
            } else {
                boolean contents = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/recyclerViewVolResult").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/text_level").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/img_thumb").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/ivContentIcon").isDisplayed();
                assertTrue("검색 결과 화면이 아닙니다.", contents);
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 검색결과독서연령확인(String type) {
        try {
            bookWhen.라이브러리투데이도서서브메뉴클릭("연령");
            bookWhen.라이브러리투데이도서서브메뉴클릭(type);
            log.info("{} 확인", type);
            String expectedLevel = "";
            if (type.equals("영아 0~3세")) {
                expectedLevel = "영아";
            } else if (type.equals("유아 4~6세")) {
                expectedLevel = "유아";
            } else if (type.equals("초저 7~9세")) {
                expectedLevel = "초저";
            } else if (type.equals("초고 10~13세")) {
                expectedLevel = "초고";
            }
            String count = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/text_level").getText();
            Assert.assertEquals(count, expectedLevel);
            bookWhen.라이브러리투데이도서서브메뉴클릭(type);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 무료유료유형확인() {
        try {
            bookWhen.라이브러리투데이도서서브메뉴클릭("무료");
            TimeUnit.SECONDS.sleep(3);
            try {
                WebElement charge = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/contents_charge");
                if (charge.isDisplayed()) {
                    log.info("유료 콘텐츠 입니다.");
                } else {
                    log.info("무료 콘텐츠 입니다.");
                }
            } catch (Exception e) {
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 검색결과탭번째콘텐츠클릭(int num) {
        try {
            log.info(num + "번째 클릭");
            AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.dictionary:id/img_thumb", num).click();
            TimeUnit.SECONDS.sleep(3);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 콘텐츠노출순서버튼클릭(String btn) {
        try {
            String rID = "";
            switch (btn) {
                case "정확도순":
                    rID = "com.wjthinkbig.dictionary:id/sortAccuracy";
                    break;
                case "연령순":
                    rID = "com.wjthinkbig.dictionary:id/sortAge";
                    break;
                case "신작순":
                    rID = "com.wjthinkbig.dictionary:id/sortNew";
                    break;
                case "인기순":
                    rID = "com.wjthinkbig.dictionary:id/sortPopular";
                    break;
            }
            log.info(btn + " 클릭");
            AndroidManager.getElementById(rID).click();
            TimeUnit.SECONDS.sleep(3);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 포토백과확인() {
        try {
            검색결과탭번째콘텐츠클릭(0);
            log.info("상세정보 확인");
            AndroidManager.getElementById("com.wjthinkbig.dictionary:id/ivDetail").click();
            String title = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/popup_title_tv").getText();
            if (title.equals("상세정보")) {
                boolean content =
                        AndroidManager.getElementById("com.wjthinkbig.dictionary:id/popup_cpr_title_tv").isDisplayed() &&
                                AndroidManager.getElementById("com.wjthinkbig.dictionary:id/popup_place_ex_tv").isDisplayed();
                assertTrue("상세정보 팝업이 확인되지 않습니다.", content);
                AndroidManager.getElementById("com.wjthinkbig.dictionary:id/popup_exit_iv").click();
            }
            AndroidManager.getElementById("com.wjthinkbig.dictionary:id/btnExit").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 학습영상확인(String year, String type) {
        try {
            bookWhen.라이브러리투데이도서서브메뉴클릭(year);        // 초등 1학년, 3학년
            log.info("유형 {} 서브메뉴 클릭", type);       // 봄여름가을겨울, 국어, 수학, 사회, 과학
            AndroidManager.getElementByTextContainsAfterSwipe(".*:id/study_menu", type).click();
            log.info("{} {} 확인", year, type);
            String expectedLevel = "";
            if (type.equals("초등 1학년")) {
                expectedLevel = "1학년1학기>가족이나 친척이 함께하는 행사 알아보기";
            } else if (type.equals("초등 3학년")) {
                expectedLevel = "3학년2학기>명절과 세시 풍속";
            }
            String content = AndroidManager.getElementById("com.wjthinkbig.dictionary:id/block_result_movie_link")
                    .findElements(By.className("android.widget.LinearLayout")).get(1).getText();

            Assert.assertEquals(content, expectedLevel);
            bookWhen.라이브러리투데이도서서브메뉴클릭(type);
            bookWhen.라이브러리투데이도서서브메뉴클릭(year);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("친구들이 많이 찾는 검색어 확인")
    public void 친구들이많이찾는검색어확인() {
        try {
            boolean content = AndroidManager.getElementByTextAfterSwipe("친구들이 많이 찾는 검색어") != null;
            assertTrue("친구들이 많이 찾는 검색어가 없습니다.", content);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("스마트올백과 {string} 스크롤 동작")
    public void 스마트올백과좌우스크롤동작(String menu) {
        try {
            log.info("북클럽 -> 검색 -> 스마트올 백과");
            if (menu.equals("연관 검색어")) {
                log.info("{} 좌우 스크롤", menu);
                Utils.dragSourceToTarget(1275, 500, 775, 500);
                TimeUnit.SECONDS.sleep(2);
                Utils.dragSourceToTarget(775, 500, 1275, 500);
                TimeUnit.SECONDS.sleep(2);
            } else if (menu.equals("사전 구분")) {
                log.info("{} 좌우 스크롤", menu);
                Utils.dragSourceToTarget(1100, 610, 815, 610);
                TimeUnit.SECONDS.sleep(2);
                Utils.dragSourceToTarget(815, 610, 1100, 610);
                TimeUnit.SECONDS.sleep(2);
            } else if (menu.equals("맨위로")) {
                Utils.swipeScreen(Utils.Direction.DOWN);
                TimeUnit.SECONDS.sleep(1);
                Utils.swipeScreen(Utils.Direction.DOWN);
                TimeUnit.SECONDS.sleep(1);
                // 최상단 올라가는 버튼 노출 나올떄 있고 없을때 있음
                Utils.swipeScreen(Utils.Direction.UP);
                TimeUnit.SECONDS.sleep(1);
                Utils.swipeScreen(Utils.Direction.UP);
                TimeUnit.SECONDS.sleep(1);
                log.info("사전 화면 최상단으로 이동 ");
                try {
                    WebElement element = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"맨위로\"]");
                    if (element.isDisplayed()) {
                        element.click();
                        if (AndroidManager.getElementById("lyNKeyword").isDisplayed()) {
                            log.info("사전 화면 최상단 입니다.");
                        }
                    } else {
                        log.info("사전 화면 최상단 이동");
                        Utils.swipeScreen(Utils.Direction.DOWN);
                        TimeUnit.SECONDS.sleep(1);
                        Utils.swipeScreen(Utils.Direction.DOWN);
                        TimeUnit.SECONDS.sleep(1);
                        if (AndroidManager.getElementById("lyNKeyword").isDisplayed()) {
                            log.info("사전 화면 최상단 입니다.");
                        }
                    }
                } catch (Exception e) {
                    log.info("화면 최상단 아닙니다. >> 버튼 노출 안됐습니다. (스와이프로 위로 올라가기 안 됨)");
                }
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("스마트올백과 사전 종류 클릭")
    public void 스마트올백과클릭() {
        try {
            log.info("웅진학습백과 클릭");
            WebElement element = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"웅진 학습백과 6\"]");
            element.click();

            String result_1 = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"웅진학습백과 추석 (秋夕) 음력 8월 15일로, 한가위·중추절 등으로 불리는 우리나라 명절.\"]/android.widget.TextView[1]").getText();
            String result_2 = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"웅진학습백과 명절 (名節) 세시풍속 중에서 특별히 중요한 의미를 부여해 행사와 잔치를 벌이는 날.\"]/android.widget.TextView[1]").getText();
            String result_3 = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"웅진학습백과 세시풍속 (歲時風俗) 일 년을 단위로 해마다 되풀이되는 풍속.\"]/android.widget.TextView[1]").getText();

            if (result_1.equals("웅진학습백과") && result_2.equals("웅진학습백과") && result_3.equals("웅진학습백과")) {
                log.info("웅진학습백과 확인");
                assertTrue(true);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("스마트올백과 {string} 확인")
    public void 스마트올백과확인(String menu) {
        try {
            log.info("{} 클릭", menu);
            switch (menu) {
                case "포토백과":
                    TimeUnit.SECONDS.sleep(5);
                    AndroidManager.getElementByTextAfterSwipe("더보기").click();
//                    AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View[5]/android.widget.Button").click();
                    TimeUnit.SECONDS.sleep(10);
                    comm.상하스크롤동작();
                    TimeUnit.SECONDS.sleep(5);
                    WebElement photo_2 = AndroidManager.getElementByXpath("(//android.view.View[@content-desc=\"searchMain#\"])[6]");
                    if (photo_2.isDisplayed()) {
                        log.info("{} 화면 확인, 0번째 포토 클릭", menu);
                        photo_2.click();
                        TimeUnit.SECONDS.sleep(5);
                        Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("웅진학습백과")).isDisplayed();
                    }
                    TimeUnit.SECONDS.sleep(5);
                    log.info("포토백과 팝업 종료");
                    Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("닫기")).click();
                    TimeUnit.SECONDS.sleep(3);
                    Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("닫기")).click();
                    break;
                case "테마":
//                    Utils.dragSourceToTarget(841, 870, 840, 560);
                    Utils.swipeScreen(Utils.Direction.DOWN);
                    Utils.swipeScreen(Utils.Direction.DOWN);
                    TimeUnit.SECONDS.sleep(1);
                    bookWhen.스마트올백과테마클릭();
                    break;
                case "내가 본 사전":
                    // 필요하다면 넣기
                    break;
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("음성검색 네이버 클로바 팝업 확인")
    public void 음성검색네이버클로바팝업확인() {
        try {
            WebElement title = AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/tvTitle");
            WebElement clova = AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/tvClovaComment");
            if (title.isDisplayed() && clova.isDisplayed()) {
                String txt = clova.getText();
                log.info("{} 확인", txt);
                Assert.assertEquals(clova.getText(), txt);
                AndroidManager.getElementById("com.wjthinkbig.NFhtml5viewer:id/dialog_close_btn").click();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("교과 투데이 확인")
    public void 교과투데이확인() {
        WebElement unit = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView");
        List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));

        // 5개
        log.info("콘텐츠 {}개 노출 확인", allUnits.size());
        assertEquals(allUnits.size(), 5);
    }

    /**
     * 메타버스 투데이 화면구성 확인
     */
    @Then("메타버스 투데이 화면구성 확인")
    public void 메타버스투데이화면구성확인() {
        try {
            log.info("메타버스 투데이 화면구성 확인");
            //타이틀/콘텐츠 확인
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").isDisplayed() &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/contents").isDisplayed();
            assertTrue("메타버스 투데이 화면구성이 확인되지 않습니다.", isDisplayedContents);
            log.info(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/title").getText());
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("{string} 진단검사 확인")
    public void 진단검사확인(String menu) {
        try {
            // 메뉴 선택 및 클릭
            selectAndClickMenu(menu);

            TimeUnit.SECONDS.sleep(5);

            // 진단검사 화면 확인
            verifyDiagnosticTestScreen(menu);

            TimeUnit.SECONDS.sleep(3);
            // 검사 종료
            exitTest(menu);
        } catch (NoSuchElementException e) {
            fail("요소를 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            fail("예외 발생: " + e.getMessage());
        }
    }


    // 메뉴 선택 및 클릭 메소드
    @Then("진단검사 {string} 클릭")
    public void selectAndClickMenu(String menu) {
        try {
            log.info("{} 클릭", menu);
            String buttonId;
            switch (menu) {
                case "AI문해력 라이트":
                    buttonId = "light";
                    break;
                case "AI수학":
                    buttonId = "aiMath";
                    break;
                default:
                    buttonId = "full";
                    break;
            }
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/" + buttonId).click();
            testStartBtn();
            TimeUnit.SECONDS.sleep(7);
        } catch (NoSuchElementException e) {
            fail("요소를 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            fail("예외 발생: " + e.getMessage());
        }
    }

    // 진단검사 화면 확인 메소드
    public void verifyDiagnosticTestScreen(String menu) {
        log.info("진단검사 진입 확인");
        switch (menu) {
            case "AI문해력 라이트":
                문해력진단검사LiteGetText();
                break;
            case "AI문해력":
                문해력진단검사GetText();
                break;
            case "AI수학":
                수학진단검사GetText();
                break;
            default:
                throw new IllegalArgumentException("지원되지 않는 메뉴 옵션: " + menu);
        }
    }

    @Then("AI문해력 진단검사 확인")
    public void 문해력진단검사GetText() {
//        log.info("현재 페이지 소스: " + getDriver().getPageSource());
        log.info("AI문해력 진단검사 진입 확인");
        String imageName = Utils.takeScreenShot();
        String result = Utils.imageToText(imageName);
        log.info("image to text: {}", result);
        assertTrue("AI문해력 진단검사 화면이 아닙니다.",
                result.contains("문 해 력 진 단 검 사")
                        || result.contains("전 문 적 인 문 해 력")
                        || result.contains("문해력 진단 검사")
                        || result.contains("전문적인 문해력")
        );
    }


    @Then("AI문해력 라이트 진단검사 확인")
    public void 문해력진단검사LiteGetText() {
        log.info("Lite 진단검사 진입 확인");
        String imageName = Utils.takeScreenShot();
        String result = Utils.imageToText(imageName);
        log.info("image to text: {}", result);
        assertTrue("Lite 진단검사 화면이 아닙니다.",
                result.contains("문 해 력 진 단 검 사")
                || result.contains("약 식 진 단 검 사")
                || result.contains("문해력 진단 검사") //jenkins
                || result.contains("약식 진단 검사")  //jenkins
                );
    }

    @Then("AI수학 진단검사 확인")
    public void 수학진단검사GetText(){
//        log.info("현재 페이지 소스: " + getDriver().getPageSource());
        log.info("AI수학 진단검사 진입 확인");
        String imageName = Utils.takeScreenShot();
        String result = Utils.imageToText(imageName);
        log.info("image to text: {}", result);
        assertTrue("AI수학 진단검사 화면이 아닙니다.",
                result.contains("문 항 풀 이 걸 과")
                        || result.contains(" 오 답 원 인 을 추 적")
                        || result.contains("문항풀이 걸과")
                        || result.contains("오답 원인을 추적")
        );

    }


    // 검사 종료 메소드
    @And("{string} 진단검사 종료")
    public void exitTest(String menu) {
        try {
            switch (menu) {
                case "AI문해력":
                    AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"검사 종료\"]").click();
                    break;
                case "AI문해력 라이트":
                    AndroidManager.getElementByText("검사 종료 ").click();
                    break;
                case "AI수학":
                    AndroidManager.getElementByText("닫기").click();
                    break;
                default:
                    // 기본 동작 또는 예외 처리
                    throw new IllegalArgumentException("지원되지 않는 메뉴: " + menu);
            }
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementByText("확인").click();
        } catch (NoSuchElementException e) {
            fail("요소를 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            fail("예외 발생: " + e.getMessage());
        }
    }




    public void testStartBtn() {
        log.info("스마트씽크빅 진단검사 시작하기");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/start").click();
    }

    @Then("스마트씽크빅 커리큘럼 콘텐츠 확인")
    public void 스마트씽크빅커리큘럼콘텐츠확인() {
        log.info("스마트씽크빅 커리큘럼 화면구성 확인");
        WebDriverWait wait = new WebDriverWait(AndroidManager.getDriver(), Duration.ofSeconds(30));
        try {
            boolean 내진도보기 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='내 진도 보기']"))).isDisplayed();
            boolean 전체커리큘럼 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text, '전체 커리큘럼 보기')]"))).isDisplayed();

            boolean contents = 내진도보기 && 전체커리큘럼;

            assertTrue("스마트씽크빅 커리큘럼 화면구성 확인 되지 않습니다.", contents);
            log.info("스마트씽크빅 커리큘럼 화면구성 확인 완료");
        } catch (NoSuchElementException e) {
            fail("요소를 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            fail("예외 발생: " + e.getMessage());
        }
    }


    @Then("스마트씽크빅 라이브러리 {string} 진입 확인")
    public void 스마트씽크빅라이브러리진입확인(String subject) throws InterruptedException {
        if (subject.equals("짝꿍책")) {
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementById("com.wjthinkbig.mstudylibrary:id/btnUp").click();
            TimeUnit.SECONDS.sleep(1);
        }

        String title = AndroidManager.getElementById("com.wjthinkbig.mstudylibrary:id/txtTitle").getText();
        log.info("{} >> {} 확인", title, subject);

        boolean displayConts = false;

        switch (subject) {
            case "동화":
                displayConts = checkElementsDisplayed(
                        "com.wjthinkbig.mstudylibrary:id/imgStoryThumb",
                        "com.wjthinkbig.mstudylibrary:id/btnBookmark",
                        "com.wjthinkbig.mstudylibrary:id/btnOrderList"
                );
                break;
            case "동요":
                displayConts = checkElementsDisplayed(
                        "com.wjthinkbig.mstudylibrary:id/recycler_view_recent",
                        "com.wjthinkbig.mstudylibrary:id/btnMusicLine",
                        "com.wjthinkbig.mstudylibrary:id/btnAllPlay",
                        "com.wjthinkbig.mstudylibrary:id/btnSelectPlay",
                        "com.wjthinkbig.mstudylibrary:id/btnAllDownload",
                        "com.wjthinkbig.mstudylibrary:id/btnOrderList"
                );
                break;
            case "놀이학습":
                displayConts = checkElementsDisplayed(
                        "com.wjthinkbig.mstudylibrary:id/imgPlayThumb",
                        "com.wjthinkbig.mstudylibrary:id/btnBookmark",
                        "com.wjthinkbig.mstudylibrary:id/btnOrderList"
                );
                break;
            case "짝꿍책":
            case "형성평가":
                displayConts = checkElementsDisplayed(
                        "com.wjthinkbig.mstudylibrary:id/viewIcon",
                        "com.wjthinkbig.mstudylibrary:id/btnBookmark",
                        "com.wjthinkbig.mstudylibrary:id/btnOrderList"
                );
                break;
            default:
                log.error("Unknown subject: {}", subject);
                return;
        }

        assertTrue(subject + " 화면 구성이 올바르지 않습니다.", displayConts);
    }

    private boolean checkElementsDisplayed(String... elementIds) {
        for (String id : elementIds) {
            if (!AndroidManager.getElementById(id).isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    @Then("학습 초등 커리큘럼 화면 확인")
    public void 학습초등커리큘럼화면확인() {
        try {
            try {
                log.info("학습 초등 교과 커리큘럼 화면 확인");
                // 가져오는 텍스트가 예상과 실제가 동일한지 확인
                Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.school1.main:id/curriculum_title").getText(), "커리큘럼");
                TimeUnit.SECONDS.sleep(5);
                AndroidManager.getElementById("com.wjthinkbig.school1.main:id/curriculum_close").click();
            } catch (Exception e) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    log.info("학습 초등 교과 내 진도 보기 화면 확인");
                    AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"전체 커리큘럼 보기\"]").click();
                    TimeUnit.SECONDS.sleep(3);
                    // 가져오는 텍스트가 예상과 실제가 동일한지 확인
                    Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/curriculum_title").getText(), "내   진 도   보 기");
                    TimeUnit.SECONDS.sleep(5);
                    AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/curriculum_close").click();
                    TimeUnit.SECONDS.sleep(3);
                    AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"back\"]").click();
                } catch (Exception ignored) {
                    boolean text = AndroidManager.getElementByTextAfterSwipe("내 진도 보기 - 수학") != null;
                    assertTrue(text);
                    Utils.touchSpecificCoordinates(73, 50);
                }
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("학습 {string} 안내 문구 확인")
    @Then("스마트씽크빅 {string} 안내 문구 확인")
    public void 학습투게더알림팝업확인(String text) {
        try {
            comm.checkCommonPopupWithText(text);
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnR").click();
        } catch (Exception e) {
            try {
                AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/btn_left").click();
            }catch (Exception e1){
                AndroidManager.getElementById("com.wjthinkbig.school1.main:id/btn_cancel").click();
            }
        }
    }


    @Then("학습 미완료 모아보기 확인")
    public void 학습미완료모아보기확인() {
        try {
            String unfinished = "com.wjthinkbig.integratedquration.main:id/txt_content_menu";
            if (AndroidManager.getElementById(unfinished).equals("미 완 료   모 아 보 기")) {
                // 가져오는 텍스트가 예상과 실제가 동일한지 확인
                Assert.assertEquals(AndroidManager.getElementById(unfinished).getText(), "미 완 료   모 아 보 기");
                log.info("미완료 모아보기 화면 입니다.");
            } else if (AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/iv_expand_header_title").isDisplayed()) {
                log.info("학습 전체보기 화면 입니다.");
            } else {
                log.info("학습 화면 입니다.");
            }

        } catch (Exception e) {
        }
    }


    @Then("학습노트 확인")
    public void 학습노트확인() {
        try {
            TimeUnit.SECONDS.sleep(20);
            log.info("학습노트 실행 확인");
            String imageName = Utils.takeScreenShot();
            String result = Utils.imageToText(imageName);
            log.info("image to text: {}", result);
            assertTrue("학습노트 실행 화면이 아닙니다.",
                    result.contains("학 습 노 트") || result.contains("학습노트")
                            || result.contains("「 개)"));
            TimeUnit.SECONDS.sleep(3);
            Utils.touchSpecificCoordinates(65, 55);     // 뒤로가기
            TimeUnit.SECONDS.sleep(3);
            Utils.touchSpecificCoordinates(1020, 790);     // 확인
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("일대일교실 학습 인증번호 팝업 확인")
    public void 일대일교실학습인증번호팝업확인() {
        try {
            log.info("인증번호 팝업 확인");
            try {
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.school1.main:id/certpop_tv_title");
                String text = element.getText();
                if (element.isDisplayed() && text.equals("인증번호 입력")) {
                    log.info("{} 안내 문구 일반 팝업 확인", text);
                    AndroidManager.getElementById("com.wjthinkbig.school1.main:id/certpop_btn_cancel").click();
                }
//                fail(text + " 라는 문구가 담긴 안내 팝업이 노출되지 않았습니다.");
            } catch (Exception e) {
                try {
                    log.info("인증번호 입력창 확인");
                    // 비밀번호 입력 키보드 내리기
                    if (Utils.getDeviceType().equals("X200")) {
                        Utils.touchSpecificCoordinates(1175, 1140);
                    } else {
                        Utils.touchSpecificCoordinates(1175, 1075);
                    }
                    WebElement element = AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/certpop_tv_title");
                    String text = element.getText();
                    if (element.isDisplayed() && text.equals("인 증 번 호   입 력")) {
                        log.info("{} 안내 문구 일반 팝업 확인", text);
                        AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/certpop_btn_left").click();
                    }
                } catch (Exception ie) {
                    log.info("Exception : 인증번호 입력창 확인");
                    Utils.touchSpecificCoordinates(1290, 1060);     // 비밀번호 입력 키보드 내리기
                    AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/certpop_btn_left").click();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("학습 검정교과서 시험대비 확인")
    public void 학습검정교과서시험대비확인() {
        try {
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/frmTopLayout").isDisplayed()
                            && AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/tv_progress_finished").isDisplayed();
            assertTrue("개정수학 검정교과 화면 확인되지 않습니다.", isDisplayedContents);
            AndroidManager.getElementById("com.wjthinkbig.examprep.questionviewer:id/lnaBack").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("학습 초등 Workbook 화면 확인")
    public void 학습초등Workbook화면확인() {
        try {
            if (AndroidManager.getElementById("com.wjthinkbig.workbookaudio:id/list1").isDisplayed()) {
                assertTrue(true);
                log.info("Workbook 화면 확인 되었습니다.");
                AndroidManager.getElementById("com.wjthinkbig.workbookaudio:id/button").click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("학습 Vacabulary Master N_투게더 확인")
    public void 학습VacabularyMasterN_투게더확인() {
        try {
            log.info("학습 Vacabulary Master N_투게더 확인");
            if (Utils.getDeviceType().equals("M1 Pad")) {
                TimeUnit.SECONDS.sleep(5);
                log.info("M1 Pad 나의 투게더 확인");
                //N투게더시작, 스템프 구성 확인
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.together.homeintro:id/btnTogetherStart").isDisplayed() &&
                                AndroidManager.getElementById("com.wjthinkbig.together.homeintro:id/btnStamp").isDisplayed() &&
                                AndroidManager.getElementById("com.wjthinkbig.together.homeintro:id/vgMyContainer").isDisplayed();
                assertTrue("학습 Vacabulary Master N_투게더가 확인되지 않습니다.", isDisplayedContents);
                AndroidManager.getElementById("com.wjthinkbig.together.homeintro:id/btnExit").click();
            } else {
                try {
                    comm.checkCommonPopupWithText("투게더 회원이 아닙니다.");
                    AndroidManager.getElementById("com.wjthinkbig.school1.main:id/btn_cancel").click();
                } catch (Exception e) {
                    AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/btn_left").click();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("학습 Vacabulary Master Together Cam 확인")
    public void 학습VacabularyMasterTogetherCam확인() {
        try {
            if (Utils.getDeviceType().equals("M1 Pad")) {
                log.info("M1 Pad: Vacabulary Master Together Cam 확인");
                // 사진찍기, 첨삭보관함 구성 확인
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.together.cam:id/cameraBtn").isDisplayed() &&
                                AndroidManager.getElementById("com.wjthinkbig.together.cam:id/cabinetBtn").isDisplayed();
                assertTrue("학습 Vacabulary Master Together Cam 확인되지 않습니다.", isDisplayedContents);
                AndroidManager.getElementById("com.wjthinkbig.together.cam:id/exitBtn").click();
                TimeUnit.SECONDS.sleep(1);
                AndroidManager.getElementById("com.wjthinkbig.together.cam:id/rightBtn").click();
            } else {
                try {
                    log.info("학습 Vacabulary Master Together Cam 확인");
                    comm.checkCommonPopupWithText("teN 투 게 더   회 원 만   이 용 가 능 한   앱 입 니 다 .xt");
                    AndroidManager.getElementById("com.wjthinkbig.school1.main:id/btn_cancel").click();
                } catch (Exception e) {
                    AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/btn_left").click();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 내 작품 보기 탭 화면구성 확인
     */
    @Then("독서앨범 내 작품 보기 탭 화면구성 확인")
    public void 독서앨범내작품보기탭화면구성확인() {
        try {
            log.info("독서앨범 내 작품 보기 탭 화면구성 확인");

            try {
                //콘텐츠가 있는 경우에만 처리
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/no_content_view");
                if (element.isDisplayed()) return;

            } catch (Exception e) {
            }

            //동영상 탭의 경우
            try {
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/titlebar").isDisplayed() &&
                                AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/content_viewpager").isDisplayed();

                assertTrue("독서앨범 내 작품 보기 탭 화면구성이 확인되지 않습니다.", isDisplayedContents);

                //뒤로가기 버튼 클릭
                AndroidManager.getElementById(Constant.commonBackButton_id).click();
            } catch (Exception e) {
            }
            // 사진 탭
            try {
                boolean isDisplayedContents =
                        AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/content_panel").isDisplayed() &&
                                AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/right_area").isDisplayed();

                assertTrue("독서앨범 내 작품 보기 탭 화면구성이 확인되지 않습니다.", isDisplayedContents);

            } catch (Exception e) {
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("커리큘럼 화면 확인")
    public void 커리큘럼화면확인() {
        try {
            log.info("커리큘럼 화면 확인");
            // 가져오는 텍스트가 예상과 실제가 동일한지 확인
            Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.babyintg:id/curriculum_title").getText(), "커리큘럼");
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementById("com.wjthinkbig.babyintg:id/curriculum_close").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("한글깨치기 학습 라이브러리 화면 확인")
    public void 한글깨치기학습라이브러리화면확인() {
        try {
            log.info("한글깨치기 학습 라이브러리 화면 확인");
            // 가져오는 텍스트가 예상과 실제가 동일한지 확인
            Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mstudylibrary:id/txtTitle").getText(), "학습 라이브러리");
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("수학마스터 {string} 페이지 확인")
    public void 수학마스터강의페이지확인(String menu) {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("{} 페이지 확인", menu);
            switch (menu) {
                case "동영상 강의":
                    boolean isDisplayedContents =
                            AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"javascript:doViewVOD('42PKmBe')\"]").isDisplayed();
                    assertTrue("학습 수학마스터 동영상 강의 페이지가 확인되지 않습니다.", isDisplayedContents);
                    break;
                case "개념 완성 학습":
                case "레벨 테스트":
                    // 요소들의 accessibilityId를 배열로 정의합니다.
                    String[] elementAccessibilityIds = {"기본개념문제", "오답맞춤문제", "레벨테스트", "개념동영상"};
                    for (String accessibilityId : elementAccessibilityIds) {
                        log.info(accessibilityId + " 클릭");
                        AndroidManager.getElementByAccessibilityId(accessibilityId).click();
                        TimeUnit.SECONDS.sleep(5);
                        // 모든 요소가 표시되는지 확인
                        for (String checkAccessibilityId : elementAccessibilityIds) {
                            assertTrue("학습 개념 완성 학습 페이지가 확인되지 않습니다.",
                                    AndroidManager.getElementByAccessibilityId(checkAccessibilityId).isDisplayed());
                        }
                        // "개념동영상" 요소를 클릭하고, 해당 페이지가 표시되는지 확인
                        if (accessibilityId.equals("개념동영상")) {
                            AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"javascript:doViewVOD('42PKmBe')\"]").isDisplayed();
                            assertTrue(true);
                        }
                    }
                    break;
                case "내신 완성 학습":
                    String[] elementAccessibilityIds_1 = {"서술형클리닉", "시험대비클리닉"};
                    for (String accessibilityId : elementAccessibilityIds_1) {
                        log.info(accessibilityId + " 클릭");
                        AndroidManager.getElementByAccessibilityId(accessibilityId).click();
                        TimeUnit.SECONDS.sleep(5);
                        // 모든 요소가 표시되는지 확인
                        for (String checkAccessibilityId : elementAccessibilityIds_1) {
                            assertTrue("학습 개념 완성 학습 페이지가 확인되지 않습니다.",
                                    AndroidManager.getElementByAccessibilityId(checkAccessibilityId).isDisplayed());
                        }
                    }
                    break;
                case "학습 결과 분석":
                    String[] elementAccessibilityIds_2 = {"월말분석표", "기간별분석표", "레벨테스트"};
                    for (String accessibilityId : elementAccessibilityIds_2) {
                        log.info(accessibilityId + " 클릭");
                        AndroidManager.getElementByAccessibilityId(accessibilityId).click();
                        TimeUnit.SECONDS.sleep(5);
                        // 모든 요소가 표시되는지 확인
                        for (String checkAccessibilityId : elementAccessibilityIds_2) {
                            assertTrue("학습 결과 분석 페이지가 확인되지 않습니다.",
                                    AndroidManager.getElementByAccessibilityId(checkAccessibilityId).isDisplayed());
                        }
                    }
                    break;

            }
            // 뒤로 가기 버튼을 클릭합니다.
            AndroidManager.getElementById("com.wjthinkbig.mid.master:id/btnExit").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 라이브러리 홈 투데이 라이브러리 콘텐츠 확인
     * 패드 계정별 학년 (고학년/저학년) 설정 필요
     */
    @Then("라이브러리 홈 투데이 라이브러리-{string} 콘텐츠 확인")
    @Then("라이브러리 홈 북클럽 강력 추천-{string} 콘텐츠 확인")
    public void 라이브러리홈투데이라이브러리콘텐츠확인(String title) {
        try {
            // 계정 학년 설정 : 1,2,3 == 저학년 / 4,5,6 == 고학년
            log.info("라이브러리 홈 북클럽 강력 추천 콘텐츠 타이틀 {} 확인", title);
            WebElement con_title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");

            String expectedTitle;

            //  T500 4학년 설정, X200 저학년 설정
            boolean isSenior = Utils.getDeviceType().equals("SM-T500");

            switch (title) {
                case "전문가 추천":
                    expectedTitle = isSenior ? "전문가 추천 (고학년)" : "전문가 추천 (저학년)";
                    break;
                case "교과수록 필독서":
                    expectedTitle = isSenior ? "교과서 수록 & 초등 필독서 (고학년)" : "교과서 수록 & 초등 필독서 (저학년)";
                    break;
                default:
                    throw new IllegalArgumentException("title: " + title);
            }

            log.info("{} 확인", con_title.getText());
            assertEquals(con_title.getText(), expectedTitle);

            세트목록보기확인(expectedTitle);

            세트썸네일확인();
            bookWhen.라이브러리콘텐츠구성버튼클릭();
            라이브러리콘텐츠세트소개확인();
            TimeUnit.SECONDS.sleep(1);
            // 뒤로가기
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_toolbar_back").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }

    public void 세트썸네일확인() {
        log.info("세트 썸네일 정상 노출 확인");

        boolean isDisplayedContents =
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_library_set_list")
                        .findElement(By.id("com.wjthinkbig.mlauncher2:id/iv_item_set_thumbnail")).isDisplayed();
        assertTrue("세트 썸네일 확인되지 않습니다.", isDisplayedContents);
    }

    @And("라이브러리 콘텐츠 세트 소개 확인")
    public void 라이브러리콘텐츠세트소개확인() {
        log.info("세트 소개, 구성 확인");
        // 세트소개, 소개, 구성 확인
        boolean content = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_toolbar_title").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_composition_subtitle_1").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_composition_subtitle_2").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_composition_intro_content").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_composition_content").isDisplayed();
        assertTrue("세트소개 팝업이 확인 되지 않습니다.", content);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_dialog_toolbar_close").click();
    }


    @Then("{string} 세트 목록 보기 확인")
    public void 세트목록보기확인(String menu) {
        try {
            log.info("{} 세트 목록 보기 클릭", menu);
            try {
                // id 변경_24.07 북클럽 개편
                // 세트 목록보기 클릭
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_list").click();
                TimeUnit.SECONDS.sleep(2);
                log.info("{} 세트 목록 보기 확인", menu);
                Assert.assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_toolbar_title")
                        .getText().trim(), menu);
                WebElement setMenu = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/pager_dialog_set_list");
                List<WebElement> allMenus = setMenu.findElements(By.id("com.wjthinkbig.mlauncher2:id/iv_item_set_thumbnail"));
                log.info("[{}]메뉴 {}개 세트 노출 확인", menu, allMenus.size());
                // 세트목록보기 팝업 닫기
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_dialog_toolbar_close").click();
            } catch (Exception e) {
                log.info("세트 목록보기 없습니다.");
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("라이브러리 {string}홈 {string} 리스트 확인")
    public void 라이브러리홈북클럽오리지널리스트확인(String year, String menu) {
        try {
            log.info("세트 목록 보기 클릭");
            // 세트 목록 보기 클릭 및 팝업 확인
            bookWhen.라이브러리세트목록보기클릭();

            String expectedTitle = "";

            if (menu.equals("북클럽 오리지널")) {
                switch (year) {
                    case "초등":
                        expectedTitle = "북클럽 오리지널 (초등)";
                        break;
                    case "유아":
                        expectedTitle = "북클럽 오리지널 (유아)";
                        break;
                    default:
                        throw new IllegalArgumentException("올바르지 않은 year 값: " + year);
                }
            } else {
                // 해당 부분 세트 팝업 변동 되기 때문에 세트 목록 주제와 메뉴 주제가 동일한지 비교하는 로직으로 변경
                log.info("인기 시리즈/독서 놀이터 세트");
                expectedTitle = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_toolbar_title").getText();
            }

            // 예상 타이틀 가져오기
            라이브러리세트팝업확인(expectedTitle);
            bookWhen.라이브러리세트목록번째클릭(0);

            // 2초 대기
            TimeUnit.SECONDS.sleep(2);

            // 현재 타이틀 가져오기
            WebElement ele_tilte = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title");
            log.info("{} 홈 북클럽 {} : {} 확인", year, menu, ele_tilte.getText());
            // 예상 타이틀과 실제 타이틀 비교
            assertEquals(ele_tilte.getText(), expectedTitle);

            // 썸네일 및 기타 요소 확인
            라이브러리썸네일및기타확인();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 라이브러리세트팝업확인(String title) {
        WebElement ele_tilte = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_toolbar_title");
        log.info("라이브러리 북클럽 세트 목록: {} 확인", ele_tilte.getText());
        assertEquals(ele_tilte.getText(), title);

        log.info("썸네일등 노출 영역 확인");
        boolean contents = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/pager_dialog_set_list").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/iv_item_set_thumbnail").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_item_set_age").isDisplayed();
        assertTrue("라이브러리 세트팝업 화면 확인 되지 않습니다.", contents);
    }


    @And("라이브러리 홈 감상문 리스트 확인 및 클릭")
    public void 라이브러리홈감상문리스트확인및클릭() {
        try {
            // 초기 감상문 썸네일 요소 가져오기
            WebElement reviewContainer = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ugcNestedHost");
            List<WebElement> initialReviews = reviewContainer.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));

            // 초기 감상문 개수 로그 출력
            log.info("북클럽 감상문 초기 {}개 노출 확인", initialReviews.size());

            // 화살표 클릭하여 추가 감상문 확인
            log.info("감상문 화살표 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/lastIcon").click();
            TimeUnit.SECONDS.sleep(1); // 화살표 클릭 후 감상문 로드 대기

            // 동일한 WebElement로 업데이트된 감상문 썸네일 요소 가져오기 > 한 화면에 3개씩 노출됨
            reviewContainer = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/ugcNestedHost");
            List<WebElement> updatedReviews = reviewContainer.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));

            // 추가 감상문 개수 > 화살표 클릭 후, 3개 노출 된다면 최대 4개 노출 확인으로 간주
            log.info("북클럽 감상문 업데이트 후 {}개 노출 확인", updatedReviews.size());

            // 감상문 썸네일이 화면에 노출되는지 확인
            boolean reviewsDisplayed = reviewContainer.isDisplayed();
            assertTrue("북클럽 홈 감상문 썸네일 확인되지 않습니다.", reviewsDisplayed);

            // 마지막 감상문 클릭
            initialReviews.get(2).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("감상문어워즈 감상문 확인")
    public void 북클럽감상문어워즈감상문확인() {
        boolean isDisplayedContents =
                AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/imageview").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/book_thumbnail").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/friend_name").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/like_area").isDisplayed() &&
                        AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_friendswork").isDisplayed();
        assertTrue("독서앨범 친구 작품 보기 감상문 확인되지 않습니다.", isDisplayedContents);
    }


    @Then("북클럽 레터 안내 팝업 동작 확인")
    public void 북클럽레터안내동작확인() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        if (AndroidManager.getElementByText("안내").isDisplayed()) {
            log.info("북클럽 레터 안내 팝업 확인");
            try {
                WebElement next = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/next");
                if (next.isDisplayed()) {
                    log.info("화면 넘기기 버튼 클릭");
                    next.click();
                    log.info("화면 스와이프하여 화면 넘기기");
                    TimeUnit.SECONDS.sleep(1);
                    Utils.dragSourceToTarget(660, 760, 1280, 760);
                    TimeUnit.SECONDS.sleep(1);
                    Utils.dragSourceToTarget(1280, 760, 660, 760);
                }
            } catch (Exception e) {
                log.info("페이지 넘김 없습니다.");
            }
            log.info("안내 팝업창 닫기");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back").click();
        }
    }

    /**
     * 라이브러리 우리아이 책장 화면구성 확인
     */
    @Then("라이브러리 우리아이 책장 화면구성 확인")
    public void 라이브러리우리아이책장화면구성확인() {
        try {
            log.info("라이브러리 우리아이 책장 화면구성 확인");

            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readButton").getText()
                            .equals("읽은 종이책") &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/unReadButton").getText()
                                    .equals("읽지 않은 종이책") &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/reg").isDisplayed();

            assertTrue("라이브러리 우리아이 책장 화면구성이 확인되지 않습니다.", isDisplayedContents);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("읽은 종이책 등록 팝업 확인")
    public void 읽은종이책등록팝업확인() {
        try {
            boolean isDisplayedContents =
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/searchTab")
                            .getText().equals("직접입력하기") &&
                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/buyListTab")
                                    .getText().equals("구매목록보기");
            assertTrue("라이브러리 우리아이 책장 읽은 종이책 등록 팝업 확인 확인되지 않습니다.", isDisplayedContents);

            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/close").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("라이브러리 내책장 연속 재생 안내 팝업 확인")
    public void 라이브러리내책장연속재생안내팝업확인() {
        try {
            //콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
            if (noData) {
                log.info("콘텐츠가 없어요.\n" +
                        "다양한 독서 활동으로 이 공간을 채워주세요.");
                return;
            }
        } catch (Exception e) {
        }

        WebElement play_selection = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_content");
        log.info("[{}] 안내 문구 확인", play_selection.getText());

        boolean selections = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_audio").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_video").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_play_sound").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_fairytale").isDisplayed();
        assertTrue("연속 재생 유형 선택 알림창 뜨지 않습니다.", selections);
    }


    @Then("라이브러리 내책장 콘텐츠 영역 확인")
    public void 라이브러리내책장콘텐츠영역확인() {
        try {
            //콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
            if (noData) {
                log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                return;
            }
        } catch (Exception e) {
        }
        WebElement bookCount = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/bookCount");
        log.info("[{}] 확인", bookCount.getText());

        WebElement unit = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView");
        List<WebElement> allUnits = unit.findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail"));

        // 한 줄에 5개씩 노출
        log.info("콘텐츠 {}개 노출 확인", allUnits.size());
        boolean units = unit.isDisplayed()
                // 연속재생 버튼 or 삭제 버튼 id 값 변경으로 확인 가능
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/continuousPlay").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deleteButton").isDisplayed();
        assertTrue("연속재생/삭제 화면아닌 일반 화면 확인되지 않습니다.", units);

    }


    @Then("내책장 삭제 {string} 적용 및 위로가기 확인")
    @Then("내책장 연속재생 {string} 적용 및 위로가기 확인")
    public void 내책장연속재생적용및연속재생모드확인(String type) throws InterruptedException {
        log.info("{} 유형 선택 확인", type);
        TimeUnit.SECONDS.sleep(2);
        assertEquals(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/optionMenu").getText(), type);

        try {
            //콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
            if (noData) {
                log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                return;
            }
        } catch (Exception e) {
        }

        log.info("내책장 연속재생/삭제 모드 확인");
        boolean continuousPlay
                = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deleteAllCheckBox").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/checkBox").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deletedCount").isDisplayed();
        assertTrue("내책장 연속재생 상태 확인되지 않습니다.", continuousPlay);


        // 화면 아래로 스크롤 하면 프로팅 버튼 노출 됨
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);

        try {
            WebElement scroll = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/floatingScrollUp");
            WebElement floating = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/floatingLayout");

            if (scroll.isDisplayed() && floating.isDisplayed()) {
                log.info("위로가기 버튼 클릭");
                scroll.click();

                log.info("위로가기 확인");
                boolean scrollUp =  // 화면 최상단 : 읽던책, 생각표현, 도전100권으로 확인
                        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readingBookLabel").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/albumLabel").isDisplayed()
                                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/challengeDisable").isDisplayed();
                assertTrue("연속재생 버튼 클릭 후, 화면 위로가기 버튼 미노출 되었습니다.", scrollUp);

                //화면 하단 특정 영역으로 이동
                Utils.dragSourceToTarget(950, 890, 950, 450);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            log.info("스크롤버튼 생길 만큼의 콘텐츠가 없습니다. 콘텐츠를 더 추가해주세요.");
            // 화면 아래로 스크롤 하면 프로팅 버튼 노출 됨
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            //화면 하단 특정 영역으로 이동
            Utils.dragSourceToTarget(950, 890, 950, 450);
            TimeUnit.SECONDS.sleep(1);
        }
    }


    @Then("내책장 연속재생 전체선택 확인")
    @Then("내책장 삭제 전체선택 확인")
    public void 내책장연속재생전체선택확인() {
        try {
            //콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
            if (noData) {
                log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                return;
            }
        } catch (Exception e) {
        }

        WebElement bookCount = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deletedCount");
        if (bookCount.isDisplayed()) {
            // 전체 선택 한번에 가능한 책은 100권까지
            log.info("전체 선택한 책 {} 확인", bookCount.getText());
            String book = bookCount.getText();

            // 숫자 추출을 위한 정규 표현식
            String number = book.replaceAll("[^0-9]", "");
            int num = Integer.parseInt(number);
            if (num > 0) {
                log.info("전체 선택 확인"); // 숫자가 0보다 크면 참
            }
        } else fail("전체 선택 오류");
    }


    @Then("{string} 재생목록 {int}개 확인")
    public void 재생목록개확인(String type, int count) {
        try {
            log.info("{} 재생목록 {}개 확인", type, count);
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요.\n" +
                            "다양한 독서 활동으로 이 공간을 채워주세요.");
                    return;
                }
            } catch (Exception e) {
            }

            try {
                if (type.equals("오디오 이북")) {
                    log.info("오디오이북 연속 재생 플레이 리스트 확인");
                    AndroidManager.getElementById(Constant.더보기_id).click();
                    for (int i = 0; i < count; i++) {
                        AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mepubviewer2:id/layoutPlayList", i).isDisplayed();
                        TimeUnit.SECONDS.sleep(2);
                    }
                } else {
                    log.info("동영상/소리동요 연속 재생 플레이 리스트 확인");
                    try {
                        for (int i = 0; i < count; i++) {
                            AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mvideo2:id/tvTitle", i).isDisplayed();
                            TimeUnit.SECONDS.sleep(2);
                        }
                        log.info("재생목록 확인 후, 삭제");
                        bookWhen.재생목록전체삭제동작();
                    } catch (Exception e) {
                        log.info("동영상 플레이리스트 2개 이상일때 플레이 리스트 삭제후, 재실행");
                        bookWhen.재생목록전체삭제동작();
                        bookWhen.라이브러리내책장연속재생버튼클릭();
                        bookWhen.라이브러리내책장연속할책선택하기(2);
                        bookWhen.라이브러리내책장연속재생버튼클릭();
                        TimeUnit.SECONDS.sleep(5);
                        log.info("동영상/소리동요 연속 재생 플레이 리스트 확인");
                        for (int i = 0; i < count; i++) {
                            AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mvideo2:id/tvTitle", i).isDisplayed();
                            TimeUnit.SECONDS.sleep(2);
                        }
                        log.info("재생목록 확인 후, 삭제");
                        bookWhen.재생목록전체삭제동작();
                    }
                }
            } catch (Exception e) {
                // 재생목록 안보이면 상단 터치 후, 더보기 눌러서 보기 추가_23.04.11
                Utils.touchCenterInViewer(getDriver());
                AndroidManager.getElementById(Constant.더보기_id).click();
                for (int i = 0; i < count; i++) {
                    AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mepubviewer2:id/layoutPlayList", i).isDisplayed();
                    TimeUnit.SECONDS.sleep(2);
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    private int countCheck;     // 삭제전 책 권수 저장
    private int compareCount;   // 삭제후 책 권수 저장

    @Then("라이브러리 내책장 삭제전 확인")
    public void 라이브러리내책장삭제전확인() {
        String bookCount = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/bookCount").getText();
        log.info("[{}] 확인", bookCount);
        // 문자열에서 숫자만 추출
        String BookNum = bookCount.replaceAll("[^0-9]", "");
        // 추출한 숫자를 int로 변환
        int originBookCnt = Integer.parseInt(BookNum);
        // 삭제전 책 권수 저장
        countCheck = originBookCnt;
    }


    @Then("내책장 연속재생 버튼 - 삭제 버튼 변경 확인")
    public void 내책장연속재생버튼삭제버튼변경확인() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        WebElement play = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/continuousPlay");
        if (play.isDisplayed()) {
            log.info("삭제 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deleteButton").click();
            WebElement delete = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/executeDelete");
            WebElement cancel = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/executeCancel");
            if (delete.isDisplayed() && cancel.isDisplayed()) {
                log.info("연속 재생 버튼 >> 삭제 버튼 변경 확인");
            } else fail("연속 재생 버튼 >> 삭제 버튼 변경 오류");
        }
    }

    @Then("라이브러리 내책장 삭제 확인")
    public void 라이브러리내책장삭제확인() {
        String deleteBookCount = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/bookCount").getText();
        log.info("[{}] 확인", deleteBookCount);
        // 문자열에서 숫자만 추출
        String deleteBookNum = deleteBookCount.replaceAll("[^0-9]", "");
        // 추출한 숫자를 int로 변환
        int deleteBookCnt = Integer.parseInt(deleteBookNum);
        // 삭제후 책 권수 저장
        compareCount = deleteBookCnt;
        if (countCheck > compareCount) {
            log.info("삭제 전: {} || 삭세 후: {}", countCheck, deleteBookCnt);
        }
    }


    @Then("라이브러리 내책장 {string} {string} 정렬 확인")
    public void 라이브러리마이세트확인(String menu, String type) {
        try {
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                    return;
                }
            } catch (Exception e) {
            }

            log.info("라이브러리 - 내책장: {}/{}", menu, type);

            TimeUnit.SECONDS.sleep(1);
            AndroidManager.getElementByText("담은 순").click();
            bookWhen.라이브러리마이세트첫번째콘텐츠클릭(1);
            TimeUnit.SECONDS.sleep(10);
            comm.notReadingContinue("No");
            TimeUnit.SECONDS.sleep(15);
            bookWhen.showUpperBar();
            컨텐츠제목저장하기();
            bookWhen.타입별로콘텐츠종료하기(type);
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementByText("읽은 순").click();
            bookWhen.라이브러리마이세트첫번째콘텐츠클릭(1);
            TimeUnit.SECONDS.sleep(10);
            comm.notReadingContinue("No");
            TimeUnit.SECONDS.sleep(15);
            bookWhen.showUpperBar();
            비교컨텐츠제목저장하기();
            bookWhen.타입별로콘텐츠종료하기(type);


        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    private String comtentNameForCheck;
    private String comtentNameForMatching;


    @Then("컨텐츠 제목 저장하기")
    public void 컨텐츠제목저장하기() {
        try {
            log.info("컨텐츠 제목 저장하기");
            WebElement book = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtPieceHeadTitle");
            if (book.isDisplayed()) {
                comtentNameForCheck = book.getText();
                log.info(book.getText());
            } else fail("No content title found");

        } catch (Exception e) {
            log.info("동영상 컨텐츠 제목 저장하기");
            WebElement video = AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/tvTitle");
            if (video.isDisplayed()) {
                comtentNameForCheck = video.getText();
                log.info(video.getText());
            } else fail("No content title found");
        }
    }

    @Then("컨텐츠 제목 비교하기")
    public void 비교컨텐츠제목저장하기() {
        try {
            WebElement comparebook = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/txtPieceHeadTitle");
            if (comparebook.isDisplayed()) {
                log.info("이북 비교 컨텐츠 제목 저장하기");
                comtentNameForMatching = comparebook.getText();
                if (comtentNameForCheck.equals(comtentNameForMatching)) {
                    log.info("비교 하기: {} == {}", comtentNameForCheck, comtentNameForMatching);
                } else {
                    fail("선택한 컨텐츠가 다릅니다.");
                }

            } else fail("No content title found");
        } catch (Exception e) {
            WebElement comparevideo = AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/tvTitle");
            if (comparevideo.isDisplayed()) {
                log.info("동영상 비교 컨텐츠 제목 저장하기");
                comtentNameForMatching = comparevideo.getText();
                if (comtentNameForCheck.equals(comtentNameForMatching)) {
                    log.info("비교 하기: {} == {}", comtentNameForCheck, comtentNameForMatching);
                } else {
                    fail("선택한 컨텐츠가 다릅니다.");
                }
            } else fail("No content title found");
        }
    }


    @Then("내 책장 좋아요 {int}개 화면 구성 확인")
    public void 내책장좋아요화면구성확인(int num) {
        try {
            // 화면 하단 이동
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            //콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
            if (noData) {
                log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                return;
            }
        } catch (Exception e) {
        }

        WebElement bookCount = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/bookCount");
        log.info("[{}] 확인", bookCount.getText());
        if (bookCount.getText().equals("총 " + num + "권")) {
            assertEquals(bookCount.getText(), "총 " + num + "권");  // 이전 step에서 3권 선택
        } else {
            log.info("이전 즐겨찾기 해제 오류 : [{}] 확인", bookCount.getText());
        }

        boolean units = bookCount.isDisplayed()
                // 연속재생 버튼만 존재, 삭제 버튼 없음
                && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/continuousPlay").isDisplayed();
        assertTrue("좋아요 화면 확인되지 않습니다.", units);
    }


    @Then("즐겨찾기 컨텐츠 없음 확인")
    public void 즐겨찾기컨텐츠없음확인() {
        try {
            log.info("즐겨찾기 등록_해제 확인");
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                    return;
                }
            } catch (Exception e) {
            }

            log.info("즐겨찾기 등록 확인");

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("{string} 교과 연계 도서 확인")
    public void 라이브러리전체메뉴교과도서확인(String year) throws InterruptedException {
        log.info("교과 연계 도서 확인");
        // 클릭 후 메뉴 타이틀 이름 저장
        String title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_toolbar_title").getText();
        log.info("[{}]", title);
        boolean contents =
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rg_library_curriculum").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_library_curriculum_filter").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_item_curriculum_title").isDisplayed()
                        && AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/iv_item_content_thumbnail").isDisplayed();
        assertTrue("라이브러리 콘텐츠 화면 확인 되지 않습니다.", contents);

        bookWhen.라이브러리학습연계도서서브메뉴클릭(0);
        TimeUnit.SECONDS.sleep(5);
        if (year.equals("중등")) {
            comm.notReadingContinue("No");
            TimeUnit.SECONDS.sleep(13);
            bookWhen.오디오북종료하기();
        } else {
            // 나가기
            try {
                log.info("나가기");
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/today_button_home").click();
            } catch (Exception e) {
                log.info("Exception 나가기");
                bookWhen.showUpperBar();
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/today_button_home").click();
            }
        }
    }


    @Then("{string} 전체 재생 버튼 기능 확인")
    public void 라이브러리전체메뉴버튼기능확인(String menu) {
        // 세트목록보기, 콘텐츠 노출확인 0개 아닌지, 구성버튼(필수), 전체 재생 버튼 동작 확인
        // 전체 다운로드 동작 확인
        if (!Utils.getDeviceType().equals("SM-T583")) {
            if (Arrays.asList("옹알종알 말놀이", "별별 장소와 직업", "팡팡 과학 실험", "슬기로운 생활").contains(menu)) {
                try {
                    WebElement play = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_all_play");
                    if (play.isDisplayed()) {
                        play.click();
                        try {
                            WebElement download = AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/btn_download_cancel", 30);
                            if (download.isDisplayed()) {
                                log.info(AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_download_progress").getText());
                                log.info("다운로드 취소");
                                download.click();
                                TimeUnit.SECONDS.sleep(5);
                            }
                        } catch (Exception e) {
                            try {
                                WebElement info = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_content");
                                if (info.isDisplayed()) {
                                    log.info("유형 선택 안내 : {}", info.getText());
                                    assertTrue("전체 재생 안내 팝업 오류",
                                            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_audio").isDisplayed() &&
                                                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_video").isDisplayed()
                                    );
                                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_dialog_toolbar_close").click();
                                }
                            } catch (Exception e1) {
                                log.info("안내 팝업 없이 바로 재생 됩니다.");
                                TimeUnit.SECONDS.sleep(5);
                                comm.clickCastBookOkBtn();
                                comm.notReadingContinue("No");
                                bookWhen.오디오북종료하기();
                                bookWhen.감상문팝업종료();
                            }
                        }
                    }
                } catch (Exception e) {
                    log.info("연속 재생은 오디오이북/동영상만 가능합니다.");
                }
            } else {
                try {
                    if (AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_all_play").isDisplayed()) {
                        log.info("전체 재생 버튼 노출 확인");
                    }
                } catch (Exception e) {
                    log.info("연속 재생은 오디오이북/동영상만 가능합니다.");
                }
            }
        } else {
            log.info("T583 : 메모리 용량 부족으로 해당 step 제외");
        }


    }


    @Then("전체다운로드 버튼 노출 확인")
    public void 전체다운로드버튼확인(){
        try {
            TimeUnit.SECONDS.sleep(5);
            WebElement set_download = AndroidManager.getElementByIdUntilDuration("com.wjthinkbig.mlauncher2:id/btn_library_set_all_download",10);
            if (set_download.isDisplayed()) log.info("다운로드 버튼 노출 확인");
        }catch (Exception e1){
            log.info("다운로드 완료 되어 완료 버튼 확인");
            WebElement set_download = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_library_set_download_complete");
            if (set_download.isDisplayed()) log.info(set_download.getText());
        }
    }

    @Then("단계 고르기 TIP 확인")
    public void 단계고르기TIP확인() {
        log.info("북클럽 전체메뉴 > 단계고그리 tip! 팝업 확인");
        String title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tv_dialog_toolbar_title").getText();
        assertEquals(title,"단계 고르기");

        boolean content = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/iv_dialog_smart_read_guide").isDisplayed();
        assertTrue("단계 고르기 TIP 팝업 확인되지 않습니다.",content);

        AndroidManager.getElementById(Constant.북클럽닫기_id).click();


    }
}
