package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AndroidManager;
import utils.Constant;
import utils.Utils;

import java.security.InvalidParameterException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utils.AndroidManager.log;

/**
 * 다양한 화면에서 사용될 법한 작업에 대한 클래스
 * */

public class CommonStepDifine {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 스크롤이 가능한 화면에서 스크롤 후 원하는 element의 텍스트 값을 입력받아 가져온 후 클릭
     */
    @When("스크롤 후 {string} 버튼 클릭")
    public void clickElementAfterVerticalSwipe(String elementText) {
        try {
            WebElement element = AndroidManager.getElementByTextAfterSwipe(elementText);
            element.click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("상하 스크롤 동작")
    public void 상하스크롤동작() {
        try {
            log.info("상하 스크롤 동작");
            TimeUnit.SECONDS.sleep(5);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * N초 대기
     */
    @When("{int}초 대기")
    public void sleepByParam(int seconds) {
        try {
            log.info("{}초 대기", seconds);
            TimeUnit.SECONDS.sleep(seconds);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
    * 북클럽 - 학습에서 최상단으로 이동
    * */

    public void scrollTop() throws InterruptedException {
        Dimension dims = AndroidManager.getDriver().manage().window().getSize();
        int pointX = dims.width / 2;
        int pointY = dims.height / 2;

        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.DOWN);
        TimeUnit.SECONDS.sleep(1);
    }


    /**
     * Reading continue yes or no
     * @param yesOrNo "Yes" or "No"
     */
    @When("Reading continue {string}")
    public void notReadingContinue(String yesOrNo) {
        if (!yesOrNo.equals("Yes") && !yesOrNo.equals("No")) throw new InvalidParameterException("yesOrNo parameter only available 'Yes' or 'No'");
        try {

            log.info("Reading continue {}", yesOrNo);
            WebElement alertMsg;

            try {
//                alertMsg = AndroidManager.getElementById(Constant.안내팝업메시지_id);
                alertMsg = AndroidManager.getElementByIdUntilDuration(Constant.안내팝업메시지_id,10);
            } catch (Exception e) {
                return;
            }

            if (alertMsg.getText().equals("이 책은 세로로 보시면 좋아요.\n" +
                    "기기를 세로로 돌려서 보세요.")) {
                AndroidManager.getElementById(Constant.안내팝업확인_id).click();
            } else if (alertMsg.isDisplayed() && alertMsg.getText().contains("읽던 페이지를 이어서 볼까요?")) {
                switch (yesOrNo) {
                    case "Yes":
                        AndroidManager.getElementById(Constant.안내팝업확인_id).click();
                        break;
                    case "No":
                        AndroidManager.getElementById(Constant.안내팝업취소_id).click();
                        break;
                }
            } else if (alertMsg.isDisplayed() && alertMsg.getText().contains("속속 캐스트북입니다.")) {
                AndroidManager.getElementById(Constant.안내팝업확인_id).click();
            } else if (alertMsg.isDisplayed() && alertMsg.getText().contains("기기를 세로로 돌려서 보세요.")) {
                AndroidManager.getElementById(Constant.안내팝업확인_id).click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("{int}번째 컨텐츠 클릭")
    @And("{int}번째 콘텐츠 클릭")
    public void 번째콘텐츠클릭(int order) {
        try {
            log.info("{}번째 콘텐츠 클릭", order);
            AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/contentsImg", order).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 메타버스 콘텐츠 확인
     */
    @Then("메타버스 콘텐츠 확인")
    public void 메타버스콘텐츠확인() {
        try {
            if (Utils.getDeviceType().equals("SM-T500")) return;

            log.info("메타버스 콘텐츠 확인");
            boolean isDisplayedContents = AndroidManager.getElementById("com.wjthinkbig.virtualclass:id/unitySurfaceView").isDisplayed();
            assertTrue("메타버스 콘텐츠가 확인되지 않습니다.", isDisplayedContents);

            // 나가기
            TimeUnit.SECONDS.sleep(3);
            Utils.touchSpecificCoordinates(84,84);

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 일반 뒤로가기 버튼 클릭
     */
    @When("일반 뒤로가기 버튼 클릭")
    public void clickCommonBackBtn() {
        try {
            log.info("일반 뒤로가기 버튼 클릭");
            AndroidManager.getElementById(Constant.commonBackButton_id).click();
            try{
                WebElement element = AndroidManager.getElementById(Constant.독서앨범_id);
                if (element.isDisplayed()){
                    element.click();
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_friendswork").click();
                }
            }catch (Exception e){}
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("뒤로가기 버튼")
    public void 뒤로가기버튼() {
        try {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("뒤로가기 버튼 클릭");
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_toolbar_back").click();
            }catch (Exception e){
                TimeUnit.SECONDS.sleep(2);
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back_btn").click();
                TimeUnit.SECONDS.sleep(2);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("비밀번호 입력 창 표출")
    public void 비밀번호입력창표출() {
        try {
            log.info("비밀번호 입력 문구의 팝업을 확인합니다.");
            WebElement element = AndroidManager.getElementById("com.wjthinkbig.nmmomsclub2:id/title");
            String result = element.getText();
            if (result.equals("비밀번호 입력")) return;
            fail("비밀번호 입력 창이 나타나지 않았습니다.");
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("비밀번호 입력 취소 버튼 클릭")
    public void 비밀번호입력취소버튼클릭() {
        try {
            log.info("비밀번호 입력 취소 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.nmmomsclub2:id/popup_btn_cancel").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 홈 화면 > 우측 상단 프로필 버튼 클릭
     * */
    @Given("프로필 버튼 클릭")
    public void 프로필버튼클릭() {
        log.info("홈 > 프로필 버튼 클릭");
        AndroidManager.getElementById(Constant.프로필_id).click();
    }

    /**
     * 특정 문구가 담긴 토스트 팝업 노출 확인
     * @param toastText 토스트 문구
     */
    @Then("{string} 문구가 담긴 토스트 팝업 노출 확인")
    public void checkToastPopup(String toastText) {
        try {
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요.\n" +
                            "다양한 독서 활동으로 이 공간을 채워주세요.");
                    return;
                }
            }catch (Exception e){}

            try {
                String result = AndroidManager.getToastMessageByXpath(Constant.toast_xPath);
                assertTrue("예상한 토스트 팝업이 노출되지 않았습니다. 실제결과: " + result + " expected contains text: " + toastText, result.contains(toastText));
                log.info("{} 문구의 토스트 팝업을 확인합니다.", toastText);
            }catch (Exception e){}
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * 일반 안내 팝업에서
     * 특정 안내 문구 일반 팝업 확인
     */
    @When("{string} 안내 문구 일반 팝업 확인")
    public void checkCommonPopupWithText(String text) {
        try {
            try {
                WebElement element = AndroidManager.getElementById(Constant.안내팝업메시지_id);
                if (element.isDisplayed() && element.getText().contains(text)) {
                    log.info("{} 안내 문구 일반 팝업 확인", text);
                    return;
                }
                fail(text + " 라는 문구가 담긴 안내 팝업이 노출되지 않았습니다.");
            }catch (Exception e){
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("{string} 팝업 확인")
    public void 팝업확인(String toastText) {
        try {
            log.info("{} 문구의 팝업을 확인합니다.", toastText);
            WebElement element = AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/tv_msg");
            String result = element.getText();
            assertTrue("예상한 팝업이 노출되지 않았습니다. 실제결과: " + result + " expected contains text: " + toastText, result.contains(toastText));

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("팝업 예 버튼 클릭")
    @When("확인 버튼 클릭")
    @When("예 선택")
    @When("다시보기 버튼 클릭")
    public void 예버튼클릭() {
        try {
            log.info("팝업 예 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 속속 캐스트북 확인 버튼 클릭
     */
    @When("속속 캐스트북 확인 버튼 클릭")
    public void clickCastBookOkBtn() {
        try {
            log.info("속속 캐스트북 확인 버튼 클릭");
            WebElement alertMsg;

            try {
                alertMsg = AndroidManager.getElementById(Constant.안내팝업메시지_id);
            } catch (Exception e) {
                return;
            }

            if (alertMsg.isDisplayed() && alertMsg.getText().contains("속속 캐스트북입니다.")) {
                AndroidManager.getElementById(Constant.안내팝업확인_id).click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 코치마크 노출 시 닫기
     */
    @When("코치마크 노출 시 닫기")
    public boolean checkFirstHelpLayoutScreen() {
        try {
            log.info("코치마크 노출 시 닫기");
            try {
                WebElement helpLayout = AndroidManager.getElementByIdUntilDuration(Constant.helpViewLayout_id, 5);
                if (helpLayout.isDisplayed()) {
                    try {
                        WebElement cartoonHelpXBtn = AndroidManager.getElementByIdUntilDuration(Constant.cartoonHelpLayoutCloseBtn_id, 5);
                        if (cartoonHelpXBtn.isDisplayed()) {
                            cartoonHelpXBtn.click();
                            return true;
                        }
                    } catch (Exception e) {
                        try {
                            WebElement audioBookHelpXBtn = AndroidManager.getElementById(Constant.helpViewXBtn_id);
                            if (audioBookHelpXBtn.isDisplayed()) {
                                audioBookHelpXBtn.click();
                                return true;
                            }
                        } catch (Exception e2) {
                            return false;
                        }
                    }
                }
                return true;
            } catch (Exception e) {
                return true;
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
        return false;
    }

    /**
     * 홈 화면 > 상단 "검색" 버튼 클릭
     */
    @When("검색 버튼 클릭")
    public void clickSearchBtn() {
        try {
            log.info("홈 > 검색 버튼 클릭");
            AndroidManager.getElementById(Constant.검색_id).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 검색 화면 진입 > 검색창에 검색어 입력
     * @param searchText 검색어
     */
    @When("검색창에서 {string} 입력")
    public void enterSearchTextOnSearchBar(String searchText) {
        try {
            log.info("검색창에 {} 입력", searchText);
            TimeUnit.SECONDS.sleep(5);
            WebElement inputText = AndroidManager.getElementById(Constant.검색창_id);
            inputText.sendKeys(searchText);
            TimeUnit.SECONDS.sleep(3);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 검색 화면 진입 > 검색창의 검색 실행 버튼 클릭
     */
    @When("검색창에서 검색 실행 버튼 클릭")
    public void clickStartSearchBtn() {
        try {
            log.info("검색창에서 검색 실행 버튼 클릭");
            AndroidManager.getElementById(Constant.검색실행_id).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("삭제 취소 버튼 클릭")
    public void 삭제취소버튼클릭() {
        try {
            log.info("삭제 취소 버튼 클릭");
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/delete_cancel_btn").click();
        } catch (Exception e) {
            log.info("내책장 연속재생/삭제 선택 취소 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/executeCancel").click();
        }
    }



}
