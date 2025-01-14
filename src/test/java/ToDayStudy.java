import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepdefinitions.SmartAllFullTcStepDefine;
import utils.AndroidManager;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;
import static utils.AndroidManager.log;

public class ToDayStudy {
    /*
     * 해당 학습 화면에 진입했다면
     * 플레이 진행시 정지 버튼, 또는 타이틀이 있을 것
     * */
    @Test
    public void 오늘의학습진입확인(){
        try {
            Utils.touchCenterInViewer(AndroidManager.getDriver());
            WebElement playBtn = AndroidManager.getElementById("com.wjthinkbig.mvideo2:id/player_overlay_play_study");
            if (playBtn.isDisplayed()) {
                log.info("오늘의 학습 플레이1 실행 확인");
                playBtn.click();
                return;
            }
        }catch (java.lang.Exception ignored){}
        try {
            Utils.touchCenterInViewer(AndroidManager.getDriver());
            WebElement playBtn = AndroidManager.getElementById("com.wjthinkbig.mepubviewer2:id/btnBottomPlayAndPause");
            if (playBtn.isDisplayed()) {
                log.info("오늘의 학습 플레이2 실행 확인");
                playBtn.click();
                return;
            }
        }catch (java.lang.Exception ignored){}
        try {
            WebElement title = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvTitle");
            if (title.isDisplayed()) {
                log.info("오늘의 학습 플레이3 실행 확인 : {}",title.getText());
            }
        }catch (java.lang.Exception ignored){}
    }

    @Test
    public void literacy() throws InterruptedException {
        log.info("북클럽 - 투데이 - 모두의 문해력 클릭");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/aiBanner").click();
        SmartAllFullTcStepDefine smartAll = new SmartAllFullTcStepDefine();
        smartAll.checkLiteracyForAll();
    }

    @Test
    public void myStudy(){
        AndroidManager.getElementByXpath
                ("//android.view.View[@content-desc=" +
                        "\"나의 학습 안녕, 자녀1! 너만을 위한 맞춤 학습을 준비했어! 시작하기\"]").click();

        log.info("AI연산 나의 학습 확인");
        boolean contents = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"Help 레벨 테스트\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"학습 시작\"]").isDisplayed()
                && AndroidManager.getElementByText(" 나의 학습 완료하고 오늘의 랭킹 도전!").isDisplayed();

        Assert.assertTrue("웅진 스마트올 AI연산 나의 학습 실행이 확인되지 않습니다.", contents);

        //나가기
        Utils.touchSpecificCoordinates(92,60);

    }

    @Test
    public void testException() {
        Exception exception = assertThrows(Exception.class, () -> {
            // 예외를 발생시키는 코드
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

                log.info("{} 클릭", subTitleText);
                element.click();

                studyGameMenu(i);

                TimeUnit.SECONDS.sleep(2);
            }
        });
        Assert.assertEquals("예상되는 예외 메시지", exception.getMessage());
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
                boolean contents2 = AndroidManager.getElementByText("Listen and Repeat").isDisplayed();
                Assert.assertTrue("Ai 영어 회화 실행이 확인되지 않습니다.", contents2);
                AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"btn-close\"]").click();
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

    @Test
    public void Exit() throws InterruptedException {

        WebElement
        element = AndroidManager.getElementsByIdsAndIndex(
                "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_list",
                "com.wjthinkbig.mlauncher2:id/sa_aicenter_support_aischool_title", 2);
        String subTitleText = element.getText();
        log.info("[{}] 메뉴 노출 확인", subTitleText);

        log.info("{} 클릭", subTitleText);
        element.click();

        TimeUnit.SECONDS.sleep(15);

//        boolean contents2 = AndroidManager.getElementByXpath("//*[contains(text(),'3분 회화')]").isDisplayed();
//        Assert.assertTrue("Ai 영어 회화 실행이 확인되지 않습니다.", contents2);
//        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"btn-close\"]").click();
    }
}
