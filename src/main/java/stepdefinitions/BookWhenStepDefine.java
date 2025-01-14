package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AndroidManager;
import utils.Constant;
import utils.Utils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static utils.AndroidManager.log;

public class BookWhenStepDefine {

    CommonStepDifine commStep = new CommonStepDifine();
    private final Logger log = LoggerFactory.getLogger(getClass());



    @Given("북클럽 버튼 클릭")
    public void 북클럽버튼클릭() {
        try {
            try {
                // 라이브러리 - 북클럽
                log.info("라이브러리 - 북클럽 버튼 클릭");
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnBookClub").click();
            } catch (Exception e) {
                try {
                    // 스마트올 - 북클럽
                    log.info("스마트올 - 북클럽 버튼 클릭");
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rltBookClub").click();
                } catch (Exception e1) {
                    log.info("북클럽 버튼 클릭 할 수 없습니다.");
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
     * 홈 화면 > 상단 "학습" 버튼 클릭
     * */
    @Given("스마트씽크빅 버튼 클릭")
    @Given("학습 버튼 클릭")
    public void clickStudyBtn() {
        try {
            try {
                // 학습 ui 변경 (23.11.01)
                log.info("스마트씽크빅(학습)_버튼 클릭");
                WebElement element = AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"스마트씽크빅\"]");
                element.click();
                TimeUnit.SECONDS.sleep(2);
                //간혹 한번에 클릭되지 않는 경우가 있어 미선택시 다시 한번 클릭하도록 처리함
                if (!element.isSelected()) element.click();
                TimeUnit.SECONDS.sleep(2);
            }catch (Exception e){
                // 라이브러리 - 북클럽
                log.info("라이브러리 - 북클럽 버튼 클릭");
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnBookClub").click();
                TimeUnit.SECONDS.sleep(2);
                WebElement element = AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"스마트씽크빅\"]");
                element.click();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 홈 화면 > 상단 "라이브러리" 버튼 클릭
     */
    @Given("라이브러리 버튼 클릭")
    public void clickLibraryBtn() {
        try {
            log.info("홈 > 라이브러리 버튼 클릭");
            WebElement element = AndroidManager.getElementByXpath(Constant.라이브러리_xPath);
            element.click();
            TimeUnit.SECONDS.sleep(1);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 라이브러리 - 서브메뉴 클릭
     *
     * @param menu 라이브러리 메뉴의 하위 메뉴
     */
    @When("라이브러리 - {string} 서브메뉴 클릭")
    public void 라이브러리서브메뉴클릭(String menu) {
        try {
            log.info("홈 > 라이브러리 > {} 클릭", menu);
            AndroidManager.getElementByXpath("//android.widget.TextView[@text='" + menu + "']").click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("라이브러리 홈 {string} 버튼 클릭")
    public void 라이브러리홈버튼클릭(String btn) {
        if (btn.equals("유아")){
            try {
                // 유아 전용 홈 화면이여야 하는데 초등 홈인 경우 해당 버튼 존재 > 클릭해서 유아 홈으로 변경
                WebElement right = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/right");
                if (right.isDisplayed()) {
                    log.info("{} 토글 버튼 클릭", btn);
                    right.click();
                }
            }catch (Exception e){
                log.info("{} 홈 화면 입니다.",btn);
            }
        }else {
            try {
                // 초등 홈이 아닌 경우 > 유아 버튼 클릭해서 변경
                WebElement left = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/left");
                if (left.isDisplayed()) {
                    log.info("{} 토글 버튼 클릭", btn);
                    left.click();
                }
            }catch (Exception e){
                log.info("{} 홈 화면 입니다.",btn);
            }
        }
    }


    @And("바나쿠 캐릭터 클릭")
    public void 바나쿠캐릭터클릭() {
        try {
            log.info("바나쿠 캐릭터 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnFriends").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("My 프로필 나가기")
    public void my프로필나가기() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_back_area").click();
    }

    @Given("라이브러리 초등 홈 화면 클릭")
    public void checkLibraryClick(){
        clickLibraryBtn();
        라이브러리서브메뉴클릭("홈");
        라이브러리홈버튼클릭("초등");
    }

    /**
     * 홈 화면 > 상단 "독서" 버튼 클릭
     */
    @Given("독서 버튼 클릭")
    @Given("투데이 버튼 클릭")
    public void clickReadingBtn() {
        try {
            try {
//                23.11.01_UI변경
                log.info("투데이(독서)_버튼 클릭");
                AndroidManager.getElementByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"투데이\"]").click();
            }catch (Exception e) {
                log.info("홈 > 독서 버튼 클릭");
                WebElement element = AndroidManager.getElementByXpath(Constant.독서_xPath);
                element.click();
                TimeUnit.SECONDS.sleep(5);
                //간혹 한번에 클릭되지 않는 경우가 있어 미선택시 다시 한번 클릭하도록 처리함
                if (!element.isSelected()) element.click();
                TimeUnit.SECONDS.sleep(5);
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 투데이(독서) - 서브메뉴 클릭
     *
     * @param menu 투데이 메뉴의 하위 메뉴
     */
    @When("투데이 - {string} 서브메뉴 클릭")
    @When("독서 - {string} 서브메뉴 클릭")
    public void 독서서브메뉴클릭(String menu) {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("홈 > 투데이 > 서브메뉴 클릭, {}", menu);
            // 서브 메뉴 클릭 하면 서브 메뉴명 변경 되어 먼저 0번째 클릭 해준 후에 보이는 메뉴명 클릭 해주기
            String parentId = "com.wjthinkbig.mlauncher2:id/tabLayout";
            String childId = "com.wjthinkbig.mlauncher2:id/indicatorText";
            AndroidManager.getElementsByIdsAndIndex(parentId, childId, 0).click();
            TimeUnit.SECONDS.sleep(1);
            AndroidManager.getElementByTextAfterSwipe(menu).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("오디오북 종료하기")
    public void 오디오북종료하기() {
        try {
            try {
                log.info("이북 뷰어 종료");
                Utils.touchBottomInViewer(AndroidManager.getDriver());
                AndroidManager.getElementById(Constant.viewerClose_id).click();
                return;
            } catch (Exception ignored) {}
            try {
                log.info("멀티 터치북 뷰어 종료");
                Utils.touchBottomInViewer(AndroidManager.getDriver());
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/today_button_home").click();
                return;
            } catch (Exception ignored) {}
            try {
                try {
                    log.info("감상문 보기 팝업에서 X 버튼 클릭_종료");
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_close").click();
                }catch (Exception e){}
                log.info("바나쿠 캐릭터 팝업 10초");
                TimeUnit.SECONDS.sleep(10);
                log.info("오디오 이북 종료");
                TimeUnit.SECONDS.sleep(1);
                Utils.touchBottomInViewer(AndroidManager.getDriver());
                AndroidManager.getElementById(Constant.viewerClose_id).click();
                return;
            } catch (Exception e) {}
            //[동영상 플레이어] 닫기
            try {
                log.info("[동영상 플레이어] 닫기");
                AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btnBack").click();
                AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();
            } catch (Exception e) {
                try {
                    //동영상 플레이어인 경우에만, 닫기버튼이 안보일 경우 viewer 클릭 후 플레이어 닫기
                    log.info("[동영상 플레이어] 닫기(Exception)");
                    Utils.touchBottomInViewer(AndroidManager.getDriver());
                    AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btnBack");
                    AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btn_confirm").click();

                } catch (Exception ie) {}
            }
        }catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("짝꿍책 팝업 나가기")
    public void 짝꿍책팝업나가기() {
        try {
            try {
                log.info("짝꿍책 팝업 나가기");
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/button_rating_ok").click();
            }catch (Exception e){
                // 짝꿍책 팝업X 종료
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/btn_nextbook_close").click();
            }
        }catch (Exception ignored){
            log.info("짝꿍책 팝업 없는 책입니다.");
        }
    }

    @And("짝꿍책 나가기")
    public void 짝꿍책나가기() {
        try {
            log.info("짝꿍책 나가기");
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/button_rating_ok").click();
        } catch (Exception e) {
            log.info("짝꿍책 팝업 없는 콘텐츠 입니다.");
        }
    }

    @And("모두의 문해력 클릭")
    public void 모두의문해력클릭() {
        log.info("모두의 문해력 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/aiBanner").click();
    }

    /**
     * 메타버스 콘텐츠 클릭
     */
    @When("메타버스 콘텐츠 클릭")
    public void 메타버스콘텐츠클릭() {
        try {
            if (Utils.getDeviceType().equals("SM-T500")) return;
            log.info("메타버스 메인 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/main").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("스마트씽크빅 메타버스 과목 클릭")
    public void 스마트씽크빅메타버스과목클릭() {
        try {
            log.info("스마트씽크빅 메타버스 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/imgOrder_metabus").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 학습 과목 클릭
     */
    @When("{string} 과목 클릭")
    public void 학습과목클릭(String subject) {
        try {
            log.info("학습 과목 {} 클릭", subject);
            commStep.scrollTop();
            int index = 0;
            switch (subject) {
                case "국어":
                    index = 0;
                    break;
                case "초등국어":
                    index = 1;
                    break;
                case "개정수학":
                    index = 4;
                    break;
            }
            String parentId = "com.wjthinkbig.mlauncher2:id/rvStudyList";
            String childId = "com.wjthinkbig.mlauncher2:id/imgOrder_elem";
            AndroidManager.getElementsByIdsAndIndex(parentId, childId, index).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 학습 과목 : 추천도서 클릭
     */
    @When("추천도서 클릭")
    public void recomendBook() throws InterruptedException {
        commStep.scrollTop();
        log.info("스마트씽크빅 - 추찬도서 클릭");
        //해당 과목 위치로 swipe 처리
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        try {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/imgSeasonal").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 학습 과목 : 키즈 라이브올 클릭
     */
    @When("키즈 라이브올 과목 클릭")
    public void kidsLiveAll() throws InterruptedException {
        commStep.scrollTop();
        log.info("스마트씽크빅 - 키즈라이브올 클릭");
        //해당 과목 위치로 swipe 처리
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        try {
            // 라이브올 ID
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/thumbnail").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }



    /**
     * 과목별 학습단계 확인 버튼 클릭
     */
    @When("{string} 학습단계 확인 버튼 클릭")
    public void 과목별학습단계확인버튼클릭(String subject) {
        try {
            log.info("과목별 학습단계 확인 버튼 클릭, {}", subject);

            String rId = "";
            switch (subject) {
                case "한글깨치기":
                    rId = "com.wjthinkbig.babyintg:id/stage_rel_btn_ho";
                    break;
                case "개정수학":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
                case "초등국어":
                case "초고수학":
                    rId = "com.wjthinkbig.school1.main:id/stage_rel_btn_ho";
                    break;
                case "국어":
                    rId = "com.wjthinkbig.school1.main:id/stage_rel_btn_ho";
                    break;
                case "사회":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
                case "과학":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
                case "Vacabulary Master":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
                case "생각토론":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
                case "테마논술":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
                case "수학마스터":
                    rId = "com.wjthinkbig.integratedquration.main:id/txt_current_ho";
                    break;
            }
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementById(rId).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 국어 F단계 클릭
     */
    @When("{string} 문항뷰어 확인 단계 클릭")
    public void 초등국어D단계클릭(String stage) {
        try {
            TimeUnit.SECONDS.sleep(2);
            if (stage.equals("초등국어")){
                log.info("초등국어 D단계 클릭");
                AndroidManager.getElementByTextContainsAfterSwipe("com.wjthinkbig.school1.main:id/stage_list1_txt_stagename", "D단계").click();

            }if (stage.equals("국어")){
                log.info("국어 F단계 클릭");
                AndroidManager.getElementByTextContainsAfterSwipe("com.wjthinkbig.integratedquration.main:id/backinfopop_lv_stagelist", "F단계").click();

            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 세부 단계 클릭
     */
    @When("{string} 과목 {string} 단계 클릭")
    public void 단계클릭(String subject, String step) {
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("{} 과목 {} 단계 클릭", subject, step);
            //step명에 " "가 붙어 있어서 변환 처리
            String tStep = "";
            if(subject.equals("개정수학") || subject.equals("사회") || subject.equals("과학") || subject.equals("Vacabulary Master")
                    || subject.equals("생각토론") || subject.equals("테마논술")) {
                for (int i = 0; i < step.length(); i++) {
                    tStep += step.substring(i, i + 1) + " ";
                }
                step = tStep;
            }

            AndroidManager.getElementByTextContainsAfterSwipe(".*:id/backinfopop_lv_holist", step).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("학습 {string} 과목 {string} 클릭")
    public void 학습과목클릭(String subject, String day) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        log.info("{} : {} 클릭", subject,day);
        AndroidManager.getElementById("com.wjthinkbig.school1.main:id/stage_btn_day_five").click();
    }

    @And("초등국어 과정테스트 클릭")
    @And("국어 과정테스트 클릭")
    public void 국어과정테스트클릭() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        log.info("국어 과정 테스트 클릭");
        AndroidManager.getElementById("com.wjthinkbig.school1.main:id/todaybfragment_framely_2").click();
        TimeUnit.SECONDS.sleep(8);

        // 과정테스트 바로 시작하기 클릭
        Utils.touchSpecificCoordinates(1152,860);
        TimeUnit.SECONDS.sleep(3);

    }

    @And("웅진씽크빅 홈 화면 이동")
    public void 웅진씽크빅홈화면이동() throws InterruptedException {
        log.info("과정 테스트 나가기 > 북클럽 - 스마트씽크빅 화면 홈 이동");
        Utils.touchSpecificCoordinates(65,60);
        TimeUnit.SECONDS.sleep(1);
        // 과목 메인으로 이동할까요 > 확인 버튼 클릭
        Utils.touchSpecificCoordinates(1025,870);
        TimeUnit.SECONDS.sleep(3);
        AndroidManager.getElementById("com.wjthinkbig.school1.main:id/top_imgbtn_home").click();
    }

    /**
     * 개정수학 D단계 클릭
     */
    @When("개정수학 D단계 클릭")
    public void 개정수학D단계클릭() {
        try {
            log.info("개정수학 D단계 클릭");
            AndroidManager.getElementByTextContainsAfterSwipe("com.wjthinkbig.integratedquration.main:id/backinfopop_lv_stagelist", "D단계").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("개념완성문제1 영역 콘텐츠 클릭")
    @And("확인문제1 영역 콘텐츠 클릭")
    public void 확인문제1영역콘텐츠클릭() {
        try {
            log.info("확인문제1 영역 콘텐츠 클릭");
            //콘텐츠 이미지가 backView인 경우, 콘텐츠 한번 더 클릭하여 frontView로 만든 후 실행하기
            try {
                WebElement backView = AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/back_thumb");
                if (backView.isDisplayed()) {
                    log.info("콘텐츠 이미지가 backView인 경우, 한번 클릭하여 frontView가 보여지도록 처리");
                    backView.click();
                }
            } catch (Exception e) {
            }
            String parentId = "com.wjthinkbig.integratedquration.main:id/math_grid_view";
            String childId = "com.wjthinkbig.integratedquration.main:id/ib_module";
            int index = 2;
            AndroidManager.getElementsByIdsAndIndex(parentId, childId, index).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("이어서 풀기 나오면 취소 클릭")
    public void 이어서풀기나오면취소클릭() {
        try {
            try {
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btn_cancel");
                if (element.isDisplayed()) {
                    log.info("문제 이어서 풀기 나오면 취소");
                    element.click();
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

    @And("학습 종료 버튼 클릭")
    public void 학습종료버튼클릭() {
        try {
            TimeUnit.SECONDS.sleep(5);
            log.info("학습 종료 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btnExit").click();
        } catch (Exception e) {
            fail("Element you found not shown");
        }
    }


    @When("문항 확인 버튼 클릭")
    public void 문항확인버튼클릭() {
        try {
            try{
                log.info("문항 팝업 예 버튼 클릭");
                AndroidManager.getElementById("com.wjthinkbig.questionviewer:id/btn_confirm").click();
            }catch (Exception e){}
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("문항 홈 버튼 클릭")
    @And("학습 홈 버튼 클릭")
    public void 문항홈버튼클릭() {
        try {
            try{
                log.info("문항 홈 버튼 클릭");
                TimeUnit.SECONDS.sleep(3);
                AndroidManager.getElementById("com.wjthinkbig.integratedquration.main:id/btn_home").click();
            }catch (Exception e){
                AndroidManager.getElementById("com.wjthinkbig.school1.main:id/top_imgbtn_home").click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("북클럽 레터 메인 {int}번째 콘텐츠 클릭")
    public void 북클럽레터메인번째콘텐츠클릭(int idx) {
        log.info("북클럽 레터 {}번째 클릭", idx);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/main")
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail")).get(idx).click();
    }

    /**
     * 라이브러리 홈 투데이 라이브러리 콘텐츠 클릭
     */
    @When("라이브러리 홈 투데이 라이브러리-{string} 콘텐츠 클릭")
    @When("라이브러리 홈 북클럽 강력 추천-{string} 콘텐츠 클릭")
    public void 라이브러리홈투데이라이브러리역사콘텐츠클릭(String title) {
        try {
            log.info("라이브러리 홈 북클럽 강력 추천 {} 콘텐츠 클릭", title);
            String rId = "";
            switch (title) {
                case "전문가 추천":
                    rId = "com.wjthinkbig.mlauncher2:id/recommendLibraryBook";
                    break;
                case "교과수록 필독서":
                    rId = "com.wjthinkbig.mlauncher2:id/recommendSchoolBook";
                    break;
            }
            //해당 id 클릭
            AndroidManager.getElementById(rId).click();

            //북클럽 메시지 팝업 뜨는 경우, 닫고 다시한번 콘텐츠 클릭
            try {
                WebElement element = AndroidManager.getElementById("com.wjthinkbig.genie:id/lt_character");

                if (element.isDisplayed()) {
                    log.info("북클럽 메시지 팝업 닫은 후 해당 콘텐츠 재클릭");
                    AndroidManager.getElementById("com.wjthinkbig.genie:id/btn_close").click();

                    //해당 콘텐츠 클릭
                    AndroidManager.getElementById(rId).click();
                    return;
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

    /**
     * 라이브러리 홈에서 '북클럽 오리지널' 또는 '인기 시리즈' 메뉴를 클릭하는 메서드
     *
     * @param year '초등' 또는 '유아'를 나타내는 문자열
     * @param menu 클릭할 메뉴의 이름 ('북클럽 오리지널' 또는 '인기 시리즈')
     */
    @When("라이브러리 {string}홈 {string} 클릭")
    public void 라이브러리홈오리지널인기클릭(String year, String menu) {
        try {
            String rId = 북클럽홈getResourceId(year, menu); // year와 menu에 따른 리소스 ID 가져오기
            if (rId != null) {
                log.info("라이브러리 홈 {} 클릭", menu);
                AndroidManager.getElementById(rId).click(); // 해당 요소 클릭
            } else {
                log.error("올바르지 않은 {} 또는 {} 값입니다.",year,menu);
                fail("올바르지 않은 유아/초등 또는 메뉴 값입니다.");
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * year와 menu에 따라 적절한 리소스 ID를 반환하는 메서드
     *
     * @param year '초등' 또는 '유아'를 나타내는 문자열
     * @param menu 클릭할 메뉴의 이름 ('북클럽 오리지널' 또는 '인기 시리즈')
     * @return 해당 요소의 리소스 ID
     */
    public String 북클럽홈getResourceId(String year, String menu) {
        if ("초등".equals(year)) {
            switch (menu) {
                case "북클럽 오리지널":
                    return "com.wjthinkbig.mlauncher2:id/originalBook";
                case "인기 시리즈":
                    return "com.wjthinkbig.mlauncher2:id/popularBook";
                default:
                    return null; // 올바르지 않은 menu 값인 경우 null 반환
            }
        } else if ("유아".equals(year)) {
            switch (menu) {
                case "북클럽 오리지널":
                    return "com.wjthinkbig.mlauncher2:id/originalBook";
                case "독서 놀이터": // 인기시리즈 변경
                    return "com.wjthinkbig.mlauncher2:id/playgroundBook";
                default:
                    return null; // 올바르지 않은 menu 값인 경우 null 반환
            }
        } else {
            return null; // 올바르지 않은 year 값인 경우 null 반환
        }
    }


    @When("라이브러리 북클럽 레터 {string} 탭 클릭")
    public void 라이브러리북클럽레터(String menu) {
        try {
            int i = 0;
            switch (menu){
                case "유아":
                    i = 0;
                    break;
                case "초등":
                    i = 1;
                    break;
            }
            log.info("북클럽 레터 {} 클릭", menu);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/headerLayout")
                    .findElement(By.id("com.wjthinkbig.mlauncher2:id/tabLayout"))
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/root")).get(i).click();
            //".*:id/recyclerView" 영역에서 swipe 처리
//            AndroidManager.getElementByTextContainsAfterSwipe(".*:id/recyclerView", menu).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @When("라이브러리 내 책장 {string} 콘텐츠 클릭")
    public void 라이브러리홈내책장상단콘텐츠클릭(String bookLabel) {
        try {
            log.info("북클럽 내 책장 {} 콘텐츠 클릭", bookLabel);

            switch (bookLabel){
                case "읽던 책 끝까지 읽기":
                    읽던책끝까지읽기콘텐츠클릭();
                    break;
                case "생각 표현하기":
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/albumBooks").click();
                    break;
                case "도전 100권 읽기":
                    log.info("미가입 콘텐츠");
//                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/challengeDisable").click();
                    break;
            }

        }catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void 읽던책끝까지읽기콘텐츠클릭(){
        try {
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readingBooks")
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail")).get(0).click();
        }catch (Exception e){
            boolean complete = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/readingBooksNoContents").isDisplayed();
            assertTrue("읽던책 모두 읽기 확인 오류",complete);
            log.info("읽던 책 모두 읽었습니다. 완료~~!");
        }
    }

    /**
     * 상세정보 팝업창에서 미리보기(바로보기) 버튼 클릭
     */
    @When("상세정보 팝업창에서 미리보기\\(바로보기) 버튼 클릭")
    public void 상세정보팝업창에서미리보기바로보기버튼클릭() {
        try {
            TimeUnit.SECONDS.sleep(3);
            //미리보기 버튼 클릭
            log.info("상세정보 팝업창에서 미리보기 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_dialog_detail_preview").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            try {
                //미리보기 버튼이 없는 경우, 바로보기 버튼 클릭
                log.info("상세정보 팝업창에서 바로보기 버튼 클릭");
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/downBtn").click();
                return;
            } catch (Exception e1) {
                fail(e.getMessage());
                System.exit(0);
            }
        }
    }

    /**
     * 라이브러리 뒤로가기 버튼 클릭
     */
    @And("북클럽 뒤로가기")
    @When("라이브러리 뒤로가기 버튼 클릭")
    public void 라이브러리뒤로가기버튼클릭() {
        try {
            log.info("라이브러리 뒤로가기 버튼 클릭");
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_toolbar_back").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 상세정보 팝업창 닫기 버튼 클릭
     */
    @When("상세정보 팝업창 닫기 버튼 클릭")
    public void 상세정보팝업창닫기버튼클릭() {
        try {
            log.info("상세정보 팝업창 닫기 버튼 클릭");
            try {
                //상세정보 팝업창이 뜬 경우에만 닫기
                AndroidManager.getElementById(Constant.북클럽닫기_id).click();
            } catch(Exception e) {}
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    public void starBtn(String btn) throws InterruptedException {
        String xPath = "";
        switch (btn){
            case "내 별 현황":
                xPath = "//android.view.View[@content-desc=\"내 별 현황\"]";
                break;
            case "나의 목표":
                xPath = "//android.view.View[@content-desc=\"나의 목표\"]";
                break;
            case "무엇을 바꿔볼까?":
                xPath = "//android.view.View[@content-desc=\"무엇을 바꿔볼까?\"]";
                break;
        }

        TimeUnit.SECONDS.sleep(1);
        log.info("{} 클릭", btn);
        AndroidManager.getElementByXpath(xPath).click();
        TimeUnit.SECONDS.sleep(3);

    }

    /**
     * 홈 화면 > 우측 상단 독서앨범 버튼 클릭
     * */
    @Given("독서앨범 버튼 클릭")
    public void 독서앨범버튼클릭() {
        log.info("홈 > 독서앨범 버튼 클릭");
        AndroidManager.getElementById(Constant.독서앨범_id).click();
    }


    /**
     * 독서앨범 내 작품 보기 메뉴 클릭
     */
    @When("독서앨범 내 작품 보기 메뉴 클릭")
    public void 독서앨범내작품보기메뉴클릭() {
        try {
            try {
                // 내 작픔 보기탭 활성화 되어있는데 클릭시 로딩 오류로 로직 수정_23.03.21
                WebElement img = AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/profile_img");
                if(!img.isDisplayed()){
                    // 프로필 이미지 안보일 때 내 작품 보기 클릭 하기
                    log.info("독서앨범 내 작품 보기 메뉴 클릭");
                    AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_mywork").click();
                }
            }catch (Exception e){}
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 내 작품 보기 탭 클릭
     */
    @When("독서앨범 내 작품 보기 {string} 탭 클릭")
    public void 독서앨범내작품보기탭클릭(String menu) {
        try {
            log.info("독서앨범 내 작품 보기 {} 탭 클릭", menu);

            String rId = "";
            switch (menu) {
                case "감상문":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_report";
                    break;
                case "사용자 오디오이북":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_useraudio";
                    break;
                case "일기":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_diary";
                    break;
                case "사진":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_photo";
                    break;
                case "동영상":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_video";
                    break;
                case "학습":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_study";
                    break;
            }

            //해당 id 클릭
            AndroidManager.getElementById(rId).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 새소식 메뉴 클릭
     */
    @Given("독서앨범 새소식 메뉴 클릭")
    public void 독서앨범새소식메뉴클릭() {
        try {
            log.info("독서앨범 새소식 메뉴 클릭");
            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_news").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 새소식 추가 버튼 클릭
     */
    @When("독서앨범 새소식 추가 버튼 클릭")
    public void 독서앨범새소식추가버튼클릭() {
        try {
            log.info("독서앨범 새소식 추가 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/create_btn").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @When("독서앨범 새소식 {string} 클릭")
    public void 독서앨범새소식클릭(String title) {
        try {
            log.info("독서앨범 새소식 {} 추가 버튼 클릭", title);
            TimeUnit.SECONDS.sleep(3);
            String rId = "";
            switch (title) {
                case "라이브러리":
                    rId = "com.wjthinkbig.thinkplayground:id/btn_library";
                    break;
                case "일기쓰기":
                    rId = "com.wjthinkbig.thinkplayground:id/btn_diary";
                    break;
                case "카메라":
                    rId = "com.wjthinkbig.thinkplayground:id/btn_camera";
                    break;
                case "학습투데이":
                    rId = "com.wjthinkbig.thinkplayground:id/btn_study";
                    break;
            }
            //해당 id 클릭
            AndroidManager.getElementById(rId).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 친구 작품 보기 메뉴 클릭
     */
    @When("독서앨범 친구 작품 보기 메뉴 클릭")
    public void 독서앨범친구작품보기메뉴클릭() {
        try {
            log.info("독서앨범 친구 작품 보기 메뉴 클릭");
            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_tab_friendswork").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 독서앨범 친구 작품 보기 탭 클릭
     */
    @When("친구 작품 보기 {string} 탭 클릭")
    public void 독서앨범친구작품보기탭클릭(String menu) {
        try {
            log.info("독서앨범 친구 작품 보기 {} 탭 클릭", menu);

            String rId = "";
            switch (menu) {
                case "감상문":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_report";
                    break;
                case "사용자 오디오이북":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_useraudio";
                    break;
                case "일기":
                    rId = "com.wjthinkbig.thinkplayground:id/tab_diary";
                    break;
            }
            //해당 element 클릭
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById(rId).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 라이브러리 투데이도서 서브메뉴 클릭
     * 투데이/유형
     */
    @And("라이브러리 검색 {string} 서브메뉴 클릭")
    @And("내책장 투데이도서 {string} 클릭")
    @When("라이브러리 투데이도서 {string} 서브메뉴 클릭")
    @When("라이브러리 검색 유형 {string} 서브메뉴 클릭")
    @When("라이브러리 투데이도서 유형 {string} 서브메뉴 클릭")
    public void 라이브러리투데이도서서브메뉴클릭(String menu) {
        try {
            log.info("투데이도서 {} 서브메뉴 클릭", menu);
            //".*:id/recyclerView" 영역에서 swipe 처리
            AndroidManager.getElementByTextContainsAfterSwipe(".*:id/recyclerView", menu).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    public void 검색결과독서유형클릭(String type) {
        try {
            log.info("{} 클릭", type);
            int idx = 0;
            switch (type) {
                case "전체":
                    idx = 0;
                    break;
                case "이북":
                    idx = 1;
                    break;
                case "오디오 이북":
                    idx = 2;
                    break;
                case "멀티 터치북":
                    idx = 3;
                    break;
                case "동영상":
                    idx = 4;
                    break;
                case "인터랙티브북":
                    idx = 7;
                    break;
            }
            AndroidManager.getElementById("com.wjthinkbig.dictionary:id/recyclerViewSub")
                    .findElements(By.id("com.wjthinkbig.dictionary:id/root")).get(idx).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    public void 스마트올백과테마클릭() {
        try {
            log.info("웅진지식백과 테마# 클릭");
            // 기기별 화면 위치 확인 필요
            Utils.touchSpecificCoordinates(350,780);
            TimeUnit.SECONDS.sleep(3);
            log.info("테마 팝업 확인");
            WebElement element = AndroidManager.getElementByTextAfterSwipe("테마#");
            assert element != null;
            if (element.isDisplayed()) {
                try {
                    Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("Next slide")).click();
                    TimeUnit.SECONDS.sleep(2);
                    Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("Previous slide")).click();
                    TimeUnit.SECONDS.sleep(2);
                    Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("Next slide")).click();
                    TimeUnit.SECONDS.sleep(2);
                    Objects.requireNonNull(AndroidManager.getElementByTextAfterSwipe("Previous slide")).click();
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception ignored){}
                log.info("하단 # 0번째 단어 선택");
                Utils.touchSpecificCoordinates(595,995);
                TimeUnit.SECONDS.sleep(10);
                WebElement element1 = AndroidManager.getElementByTextAfterSwipe("웅진학습백과");
                assert element1 != null;
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 홈 화면 > 우측 상단 스마트올 백과 버튼 클릭
     * */
    @Given("스마트올 백과 버튼 클릭")
    public void 스마트올백과버튼클릭() {
        log.info("홈 > 스마트올 백과 버튼 클릭");
        AndroidManager.getElementById(Constant.스마트올백과_id).click();
    }

    @And("백과 검색 실행 버튼 클릭")
    public void 백과검색실행버튼클릭() {
        try {
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementByTextAfterSwipe("검색").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("백과 음성 검색 실행 버튼 클릭")
    public void 백과음성검색실행버튼클릭() {
        try {
            TimeUnit.SECONDS.sleep(3);
            Utils.touchSpecificCoordinates(1678, 307);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("스마트올백과 나가기")
    public void 스마트올백과나가기() throws InterruptedException {
        try {
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementByText("메인화면으로 돌아가기").click();
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementByText("화면 벗어나기").click();
        } catch (Exception e){
            Utils.touchSpecificCoordinates(50,45);
            TimeUnit.SECONDS.sleep(5);
            AndroidManager.getElementByText("화면 벗어나기").click();
        }
    }


    @And("교과 투데이 과목 {int}번 클릭")
    public void 교과투데이과목번클릭(int idx) {
        try {
            log.info("교과 투데이 과목 {}번 클릭", idx);
            String rId = "";
            switch (idx) {
                case 1:
                    rId = "com.wjthinkbig.mlauncher2:id/firstSection";
                    break;
                case 2:
                    rId = "com.wjthinkbig.mlauncher2:id/secondSection";
                    break;
                case 3:
                    rId = "com.wjthinkbig.mlauncher2:id/thirdSection";
                    break;
                case 4:
                    rId = "com.wjthinkbig.mlauncher2:id/fourthSection";
                    break;
            }
            //해당 element 클릭
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById(rId).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("스마트씽크빅 진단검사 클릭")
    public void 스마트씽크빅진단검사클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnLiteracy").click();
    }

    @When("스마트씽크빅 커리큘럼 버튼 클릭")
    public void 스마트씽크빅커리큘럼버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnCurriculum").click();
    }



    /**
     * 학습 라이브러리 버튼 클릭
     */
    @When("스마트씽크빅 라이브러리 버튼 클릭")
    public void 학습라이브러리버튼클릭() {
        try {
            log.info("학습 라이브러리 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnStudyLib").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("스마트씽크빅 라이브러리 {string} 클릭")
    public void 학습라이브러리클릭(String subject) {
        try {
            String rID = "";
            switch (subject) {
                case "동화":
                    rID = "com.wjthinkbig.mstudylibrary:id/btnStory";
                    break;
                case "짝꿍책":
                    rID = "com.wjthinkbig.mstudylibrary:id/btnBook";
                    break;
                case "형성평가":
                    rID = "com.wjthinkbig.mstudylibrary:id/btnTest";
                    break;
                case "놀이학습":
                    rID = "com.wjthinkbig.mstudylibrary:id/btnPlay";
                    break;
                case "동요":
                    rID = "com.wjthinkbig.mstudylibrary:id/btnMusic";
                    break;
            }
            log.info("{} 클릭", subject);
            AndroidManager.getElementById(rID).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 북클럽 스마트씽크빅 과목 C단계 클릭
     */
    @When("생각토론 {string} 클릭")
    @When("테마논술 {string} 클릭")
    @When("초고수학 {string} 클릭")
    @When("국어 {string} 클릭")
    @When("사회 {string} 클릭")
    @When("과학 {string} 클릭")
    @When("Vacabulary Master {string} 클릭")
    public void 스마트씽크빅과목단계클릭(String stage) {
        log.info("스마트씽크빅 과목 {} 클릭",stage);
        AndroidManager.getElementByTextContainsAfterSwipe("com.wjthinkbig.integratedquration.main:id/backinfopop_lv_stagelist", stage).click();

    }

    @And("학습 {string} {string} 클릭")
    public void 스마트씽크빅과목학습과목클릭(String subject, String btn) {
        try {
            if (subject.equals("국어") || subject.equals("초고수학")) {
                String rID = "";
                switch (btn) {
                    case "일대일교실":
                        rID = "com.wjthinkbig.school1.main:id/top_imgbtn_classroom";
                        break;
                    case "학습노트":
                        rID = "com.wjthinkbig.school1.main:id/top_imgbtn_note";
                        break;
                    case "학습배틀":
                        rID = "com.wjthinkbig.school1.main:id/top_imgbtn_battle";
                        break;
                    case "투게더":
                        rID = "com.wjthinkbig.school1.main:id/top_imgbtn_together";
                        break;
                    case "커리큘럼":
                        rID = "com.wjthinkbig.school1.main:id/stage_btn_curriculum";
                        break;
                }
                TimeUnit.SECONDS.sleep(3);
                log.info("국어 {} 클릭", btn);
                AndroidManager.getElementById(rID).click();
            }
            else {
                String parentId = "com.wjthinkbig.integratedquration.main:id/ll_app_container";
                String child = "android.widget.ImageView";
                int idx = 0;
                String rID = "";
                switch (btn) {
                    case "커리큘럼":
                    case "Workbook":
                        idx = 0;
                        break;
                    case "학습노트":
                    case "Curriculum":
                        idx = 1;
                        break;
                    case "학습배틀":
                    case "StudyNote":
                    case "일대일교실":
                        idx = 2;
                        break;
                    case "1:1_class":
                        idx = 3;
                        break;
                    case "N_투게더":
                        idx = 4;
                        break;
                    case "투게더":
                        rID = "com.wjthinkbig.integratedquration.main:id/iv_together";
                        break;
                    case "Together Cam":
                        rID = "com.wjthinkbig.integratedquration.main:id/ib_togetherCam";
                }
                TimeUnit.SECONDS.sleep(3);
                log.info("{} {} 클릭", subject, btn);
                // 24.6월 2주차 적용
//                log.info("초등통합과목투데이 1.0.268버전 개정수학 투게더 버튼 삭제");
                if (btn.equals("Together Cam") || btn.equals("투게더")) {
                    AndroidManager.getElementById(rID).click();
                } else {
                    AndroidManager.getElementById(parentId)
                            .findElements(By.className(child)).get(idx).click();
                }
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("학습 상단 {string} 클릭")
    public void 학습모아보기(String btn){
        try {
            String rID = "";
            switch (btn) {
                case "전체보기":
                    rID = "com.wjthinkbig.integratedquration.main:id/txt_show_all";
                    break;
                case "미완료":
                    rID = "com.wjthinkbig.integratedquration.main:id/txt_incomplete";
                    break;
                case "다운로드":
                    rID = "com.wjthinkbig.integratedquration.main:id/ib_download";
                    break;
                case "검정교과서 시험대비":
                    rID= "com.wjthinkbig.integratedquration.main:id/btnBlackBook";
                    break;
            }
            log.info("{} 클릭", btn);
            AndroidManager.getElementById(rID).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }



    /**
     * 본학습단계 클릭
     */
    @When("본학습단계 클릭")
    public void 본학습단계클릭() {
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("본학습단계 클릭");
            String parentId = "com.wjthinkbig.babyintg:id/backinfopop_lv_stagelist";
            String childId = "com.wjthinkbig.babyintg:id/stage_list1_linearly";
            int index = 1;
            AndroidManager.getElementsByIdsAndIndex(parentId, childId, index).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("한글깨치기 독서앨범 버튼 클릭")
    public void 한글깨치기독서앨범버튼클릭() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        log.info("한글깨치기 > 독서앨범 버튼 클릭");
        AndroidManager.getElementById("com.wjthinkbig.babyintg:id/top_imgbtn_gallery").click();
    }

    @And("커리큘럼 버튼 클릭")
    public void 커리큘럼버튼클릭() {
        try {
            log.info("커리큘럼 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.babyintg:id/btnCurriculum").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("한글깨치기 학습 라이브러리 버튼 클릭")
    public void 한글깨치기학습라이브러리버튼클릭() {
        try {
            log.info("한글깨치기 학습 라이브러리 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.babyintg:id/top_imgbtn_studylib").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("수학 마스터 {string} 클릭")
    public void 수학마스터클릭(String menu) throws InterruptedException {
        try {
            WebElement alert = AndroidManager.getElementById("android:id/aerr_restart");
            if (alert.isDisplayed()){
                log.info("수학마스터 앱 중지 알림");
                alert.click();
            }
        }catch (Exception ignored){}
        String rID = "";
        switch (menu){
            case "동영상 강의":
                rID="com.wjthinkbig.mid.master:id/imgTile1";
                break;
            case "개념 완성 학습":
                rID="com.wjthinkbig.mid.master:id/imgTile2";
                break;
            case "내신 완성 학습":
                rID="com.wjthinkbig.mid.master:id/imgTile3";
                break;
            case "학습 결과 분석":
                rID="com.wjthinkbig.mid.master:id/imgTile4";
                break;
            case "레벨 테스트":
                rID = "com.wjthinkbig.mid.master:id/btnLevelTest";
                break;
        }
        TimeUnit.SECONDS.sleep(5);
        AndroidManager.getElementById(rID).click();
    }

    @And("학습 마스터 홈 버튼 클릭")
    public void 학습마스터홈버튼클릭() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        AndroidManager.getElementById("com.wjthinkbig.mid.master:id/btnHome").click();
    }

    @When("한글깨치기 학습 홈 버튼 클릭")
    @When("홈 버튼 클릭")
    public void 홈버튼클릭() {
        try {
            log.info("홈 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mstudylibrary:id/btnHome").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("라이브러리 콘텐츠 구성 버튼 클릭")
    public void 라이브러리콘텐츠구성버튼클릭() {
        log.info("라이브러리 구성 버튼 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_composition" ).click();

    }

    public void 라이브러리세트목록보기클릭(){
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_library_set_list").click();
    }

    public void 라이브러리세트목록번째클릭(int num){
        try {
            log.info("라이브러리 세트 목록 {}번 째 클릭",num);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/pager_dialog_set_list")
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/iv_item_set_thumbnail")).get(num).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 라이브러리 홈 추천 도서 세트 0번째 콘텐츠 클릭
     * 북클럽 개편
     */
    @When("라이브러리 홈 추천 도서 세트 콘텐츠 클릭")
    public void 라이브러리홈추천도서세트콘텐츠클릭() {
        try {
            log.info("라이브러리 홈 추천 도서 세트 콘텐츠 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_library_set_contents")
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/iv_item_content_thumbnail")).get(0).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * 라이브러리 홈 감상문 어워즈 콘텐츠 클릭
     */
    @When("라이브러리 홈 첫번째 콘텐츠 클릭")
    @When("라이브러리 홈 감상문 어워즈 콘텐츠 클릭")
    public void 라이브러리홈감상문어워즈콘텐츠클릭() {
        try {
            log.info("라이브러리 홈 첫번째 콘텐츠 클릭");
            //화면 상당으로 이동
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);
            //화면 하단으로 이동
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.UP);
            TimeUnit.SECONDS.sleep(1);
            // 콘텐츠 클릭
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/awardsBook").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("북클럽 레터 안내 버튼 클릭")
    public void 북클럽레터안내버튼클릭() {
//        ".*:id/recyclerView"
//            AndroidManager.getElementByXpath(".*:id/recyclerView").click();
        // 'recyclerView' id를 포함하고 'ImageView' 클래스를 가진 첫 번째 요소를 클릭
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView";
        AndroidManager.getElementByXpath(xpath).click();
    }

    @And("라이브러리 북클럽 레터 {int}번째 콘텐츠 클릭")
    public void 라이브러리북클럽레터번째콘텐츠클릭(int idx) {
        log.info("라이브러리 북클럽 레터 {int}번째 콘텐츠 클릭", idx);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/RecyclerView")
                .findElements(By.id("com.wjthinkbig.mlauncher2:id/thumbnail")).get(idx).click();
    }

    /**
     * 나가기 버튼 클릭 액션
     */
    @When("나가기 버튼 클릭")
    public void 나가기버튼클릭() {
        try {
            log.info("나가기 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btnBack").click();
        } catch (Exception e) {
            log.info("나가기 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back").click();
        }
    }


    /**
     * 라이브러리 우리아이 책장 버튼 클릭
     */
    @When("라이브러리 우리아이 책장 버튼 클릭")
    public void 라이브러리우리아이책장버튼클릭() {
        try {
            log.info("라이브러리 우리아이 책장 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/childBookCase").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("읽은 종이책 등록 클릭")
    public void 읽은종이책등록클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/reg").click();

    }

    @And("책장 뒤로가기 버튼 클릭")
    public void 책장뒤로가기버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/back").click();
    }

    /**
     * 라이브러리 - 서브메뉴 클릭
     *
     * @param tab 라이브러리 화면 책 탭 메뉴 클릭
     */
    @When("라이브러리 내 책장 {string} 탭 클릭")
    public void 라이브러리내책장탭클릭(String tab) {
        try {
            //화면 상단으로 이동
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);

            int i = 0;
            switch (tab){
                case "읽을 책":
                    i = 0;
                    break;
                case "다 읽은 책":
                    i = 1;
                    break;
                case "좋아요":
                    i = 2;
                    break;
            }
            log.info("라이브러리 내 책장 > {} 클릭", tab);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/userLayout")
                    .findElement(By.id("com.wjthinkbig.mlauncher2:id/tabLayout"))
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/root")).get(i).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @Then("라이브러리 내책장 {string} 드롭박스 버튼 클릭")
    public void 라이브러리내책장드롭박스버튼클릭(String type) {
        try {
            //화면 상단으로 이동
            TimeUnit.SECONDS.sleep(1);
            Utils.swipeScreen(Utils.Direction.DOWN);
            TimeUnit.SECONDS.sleep(1);

            //화면 하단 특정 영역으로 이동
            Utils.dragSourceToTarget(950,580, 950,227);
            TimeUnit.SECONDS.sleep(1);

            log.info("내 책장 유형 드롭박스 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/optionMenu").click();

            TimeUnit.SECONDS.sleep(1);
            int x = 1425;
            int y = 0;

            String deviceType = Utils.getDeviceType();

            // getYCoordinate 메서드를 사용하여 기기별로 y 좌표를 할당
            switch (type) {
                case "전체":
                    y = getYCoordinate(deviceType, 630, 695, 700, 695, 695);    // x200,t500,t583,m1,m2
                    break;
                case "이북":
                    y = getYCoordinate(deviceType, 700, 765, 780, 765, 765);
                    break;
                case "오디오 이북":
                    y = getYCoordinate(deviceType, 770, 845, 860, 845, 845);
                    break;
                case "멀티 터치북":
                    y = getYCoordinate(deviceType, 840, 920, 940, 920, 920);
                    break;
                case "동영상":
                    y = getYCoordinate(deviceType, 965, 995, 1020, 995, 995);
                    break;
                case "소리동요":
                    y = getYCoordinate(deviceType, 980, 1070, 1060, 1070, 1070);
                    break;
            }

            // 기기별로 화면 위치 확인 필요_Path 확인 불가
            Utils.touchSpecificCoordinates(x,y);

        }catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    //  coordinates 배열에는 각 기기의 y 값을 순서대로 전달
    public int getYCoordinate(String deviceType, int... coordinates) {
        switch (deviceType) {
            case "X200": return coordinates[0];
            case "SM-T500": return coordinates[1];
            case "SM-T583": return coordinates[2];
            case "M1 Pad": return coordinates[3];
            case "M2 Pad": return coordinates[4];
            default: return 0; // 기본 값
        }
    }

    @And("라이브러리 내책장 연속 재생 버튼 클릭")
    public void 라이브러리내책장연속재생버튼클릭() {
        try {
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                    return;
                }
            }catch (Exception e){}
            log.info("연속 재생 버튼 클릭");
            TimeUnit.SECONDS.sleep(3);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/continuousPlay").click();

        }catch (Exception e) {
            log.info("라이브러리 유형 선택 후, 연속 재생 버튼 클릭");
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/executeDelete").click();
        }
    }




    @And("라이브러리 내책장 연속 재생 안내 팝업 나가기")
    public void 라이브러리내책장연속재생안내팝업나가기() {
        try {
            AndroidManager.getElementById(Constant.북클럽닫기_id).click();
        }catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("내책장 연속재생 {string} 버튼 클릭")
    public void 내책장연속재생유형선택버튼클릭(String type) {
        try {
            log.info("연속 재생 유형 [{}]버튼 클릭 ", type);

            String rId = "";
            switch (type){
                case "오디오 이북":
                    rId = "com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_audio";
                    break;
                case "동영상":
                    rId = "com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_video";
                    break;
                case "소리 동요":
                    rId = "com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_play_sound";
                    break;
                case "소리 동화":
                    rId = "com.wjthinkbig.mlauncher2:id/tv_dialog_play_selection_fairytale";
                    break;
            }
            AndroidManager.getElementById(rId).click();


        }catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }


    @And("라이브러리 내책장 전체선택 버튼 클릭")
    @And("라이브러리 내책장 전체선택 해제 클릭")
    public void 라이브러리내책장전체선택버튼클릭() throws InterruptedException {
        try {
            //콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
            if (noData) {
                log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                return;
            }
        }catch (Exception e){}

        log.info("라이브러리 내책장 [전체 선택] 클릭");
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deleteAllCheckBox").click();
    }


    @When("라이브러리 내책장 삭제할 책 {int}권 선택")
    @When("라이브러리 내책장 연속 재생할 책 {int}권 선택")
    public void 라이브러리내책장연속할책선택하기(int count){
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

            TimeUnit.SECONDS.sleep(3);
            log.info("라이브러리 내책장 책 {}권 선택", count);
            for (int i = 0; i < count; i++) {
                AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/checkBox", i).click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("재생목록 편집 버튼 클릭")
    public void 재생목록편집버튼클릭() {
        try {
            log.info("재생목록편집버튼클릭");
            AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btnPlayList").click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("전체 삭제 버튼 클릭")
    public void 전체삭제버튼클릭() {
        try {
            try {
                log.info("전체삭제버튼클릭");
                AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btnDeleteAll").click();
            }catch (Exception e){
                TimeUnit.SECONDS.sleep(10);
                log.info("전체삭제버튼클릭");
                AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/btnDeleteAll").click();
            }
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("내책장 삭제 버튼 클릭")
    public void 내책장삭제버튼클릭() {
        try {
            log.info("내책장 삭제 버튼 클릭");
            // 라이브러리에서 연속재생 버튼 삭제 버튼으로 변경 후, 삭제 버튼
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/executeDelete").click();

        }catch (Exception e){
            log.info("라이브러리 내책장 삭제 버튼 클릭");
            // 라이브러리 - 내책장 - 삭제 버큰
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/deleteButton").click();
        }
    }

    @And("삭제 {string} 버튼 클릭")
    public void 삭제취소확인버튼클릭(String dialog) {
        try {
            log.info("삭제 {} 버튼", dialog);
            if (dialog.equals("확인"))
            {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_dialog_default_right").click();
            }
            else if (dialog.equals("취소")) {
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btn_dialog_default_left").click();
            }
        } catch (Exception e) {
            fail("Element you found not shown");
        }
    }

    @When("재생목록 전체 삭제 동작")
    public void 재생목록전체삭제동작() {
        try {
            //재생목록이 없는 경우, return 처리
            try {
                if(AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/rlNoPlayList").isDisplayed()) return;
            } catch (Exception e) {}

            TimeUnit.SECONDS.sleep(5);
            //재생목록이 있는 경우, 전체삭제 처리
            try {
                log.info("재생목록 전체 삭제 동작");
                재생목록편집버튼클릭();
                전체삭제버튼클릭();
                commStep.팝업확인("전체 재생목록이 삭제됩니다.");
                log.info("{}초 대기", 2);
                TimeUnit.SECONDS.sleep(2);
                commStep.예버튼클릭();
            } catch (Exception e){}
        } catch (NoSuchElementException e) {
            fail("재생목록 전체 삭제 동작이 실행되지 않았습니다.");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 플레이어 내에서 상단바 노출
     */
    @When("플레이어 내에서 상단바 노출")
    public void showUpperBar() {
        try {
            try {
                TimeUnit.SECONDS.sleep(5);
                log.info("플레이어 내에서 상단바 노출");
                Utils.touchBottomInViewer(AndroidManager.getDriver());
            }catch (Exception ignored){}
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("{string} 종료하기")
    public void 타입별로콘텐츠종료하기(String type) throws InterruptedException {
        try {//콘텐츠가 없는 경우, return
            boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/no_data").isDisplayed();
            if (noData) return;
        } catch (Exception e) {}
        if (type.equals("이북")) {
            try {
                log.info("{} 닫기",type);
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/btnFirst").click();
            }catch (Exception e){
                log.info("Exception {} 닫기",type);
                Utils.touchCenterInViewer(AndroidManager.getDriver());
                AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/btnFirst").click();
            }
        } else if (type.equals("오디오 이북")) {
            TimeUnit.SECONDS.sleep(12);
            log.info("{} 닫기",type);
            Utils.touchCenterInViewer(AndroidManager.getDriver());
            AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/btnFirst").click();
        }
        짝꿍책나가기();
        감상문팝업종료();
    }

    @Then("감상문 팝업 종료")
    public void 감상문팝업종료() {
        try {
            try {
                log.info("감상문 팝업 종료");
                AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_close").click();
            }catch (Exception e){
                try {
                    log.info("Exception1: 감상문 팝업 종료");
                    AndroidManager.getElementById("com.wjthinkbig.mbookdiaryactivitytool:id/btnClosed").click();
                }catch (Exception e1) {
                    try {
                        log.info("Exception2: 감상문 팝업 종료");
                        AndroidManager.getElementById("com.wjthinkbig.mbookdiaryactivitytool:id/btn_number_close").click();
                    }catch (Exception e2){
                        log.info("감상문 팝업 없음");
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
     * 라이브러리 마이 세트 첫번째 콘텐츠 클릭
     */
    @When("라이브러리 마이 세트 첫번째 콘텐츠 클릭")
    @When("라이브러리 내책장 {int}번째 콘텐츠 클릭")
    public void 라이브러리마이세트첫번째콘텐츠클릭(int index) {
        try {
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerViewNoContents").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요. 다양한 독서 활동으로 이 공간을 채워주세요.");
                    return;
                }
            }catch (Exception e){}
            log.info("라이브러리 {}번째 콘텐츠 클릭", index);
            AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView")
                    .findElements(By.id("com.wjthinkbig.mlauncher2:id/rootLayout")).get(index).click();
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @When("컨텐츠 {int}개 즐겨찾기 클릭")
    @When("컨텐츠 {int}개 즐겨찾기 해제")
    public void 컨텐츠리스트중번째즐겨찾기클릭(int idx) {
        try {
            try {
                //콘텐츠가 없는 경우, return
                boolean noData = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/no_data").isDisplayed();
                if (noData) {
                    log.info("콘텐츠가 없어요.");
                    return;
                }
            }catch (Exception e){}

            for (int i = 1; i <= idx; i++) {
                TimeUnit.SECONDS.sleep(1);
                AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/recyclerView")
                        .findElements(By.id("com.wjthinkbig.mlauncher2:id/rootLayout"))
                        .get(i).findElement(By.id("com.wjthinkbig.mlauncher2:id/favorite")).click();
            }

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 라이브러리 학습연계도서 서브메뉴 클릭
     */
    @When("교과 연계 도서 {int}번째 클릭")
    public void 라이브러리학습연계도서서브메뉴클릭(int idx) {
        try {
            WebElement content = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/rv_library_curriculum_contents");
            String title = content.findElements(By.id("com.wjthinkbig.mlauncher2:id/tv_item_curriculum_title")).get(0).getText();

            log.info("교과 연계 도서 {} : {}번째 클릭",title, idx);

            content.findElements(By.id("com.wjthinkbig.mlauncher2:id/rv_item_curriculum_contents"))
                    .get(0).findElements(By.id("com.wjthinkbig.mlauncher2:id/iv_item_content_thumbnail")).get(idx).click();

        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("라이브러리 전체메뉴 단계 고르기 TIP 클릭")
    public void 라이브러리전체메뉴단계고르기TIP클릭() {
        AndroidManager.getElementByXpath(Constant.전체메뉴단계고르기_xPath).click();
    }


    @And("커리큘럼 나가기")
    public void 커리큘럼나가기() {
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"back\"]").click();
    }

    @And("독서앨범 나가기")
    public void 독서앨범나가기() {
        AndroidManager.getElementById(Constant.commonBackButton_id).click();
    }

    @And("학교 공부 예습 나가기")
    public void 학교공부예습나가기() {
        AndroidManager.getElementById(Constant.뒤로가기_id).click();
    }

    @When("투게더 수업 버튼 클릭")
    public void 투게더수업버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnTogether").click();
    }
}