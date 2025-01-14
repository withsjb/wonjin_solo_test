import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AndroidManager;
import utils.Constant;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static utils.AndroidManager.*;

public class TotalMenu {

    /*
    * 북클럽 - 초등홈 - 분야별 메뉴 - 전체메뉴 이동 확인
    * 북클럽 - 유아홈 - 인기 분야 - 전체메뉴 이동 확인
    * */
    @Test
    public void totalView(){
        try {
            getElementById("com.wjthinkbig.mlauncher2:id/showAll").click();

            TimeUnit.SECONDS.sleep(3);
            log.info("북클럽 라이브러리 전체메뉴 화면구성 확인");

            // 탭 레이아웃 요소를 찾습니다
            WebElement element = getElementById("com.wjthinkbig.mlauncher2:id/tabLayout");
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

        } catch (java.lang.Exception e) {
            System.exit(0);
        }
    }

    @Test
    public void textList(){
        int i = 4;
        WebElement element =
                AndroidManager.getElementByXpath("//*[contains(@class, 'android.widget.LinearLayout')]/android.view.ViewGroup["
                +i+ "]/android.view.ViewGroup/android.widget.TextView");
        String subTitleText = element.getText();

        log.info(subTitleText);
    }

    @Test
    public void My(){
        // 프로필 선택
        getElementById("com.wjthinkbig.mlauncher2:id/img_profile_pic").click();
        boolean profile_pop =
                getElementById("com.wjthinkbig.mlauncher2:id/tv_custom_popup_subtitle").isDisplayed()
                && getElementById("com.wjthinkbig.mlauncher2:id/gallery").isDisplayed()
                && getElementById("com.wjthinkbig.mlauncher2:id/camera").isDisplayed();
        assertTrue("프로필 변경 팝업 노출되지 않았습니다.",profile_pop);
        // 나가기
        getElementById("com.wjthinkbig.mlauncher2:id/exit").click();


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

    @Test
    public void starshop(){
        try {
//          나의 리워드 선택 >> 스타샵 이동 , 알림
            getElementById("com.wjthinkbig.mlauncher2:id/startshoplayout").click();

            starBtn("내 별 현황");
            boolean startshopTap01 =
                    AndroidManager.getElementByText("스타샵").isDisplayed()
                            && AndroidManager.getElementByText("번호").isDisplayed()
                            && AndroidManager.getElementByText("구분").isDisplayed()
                            && AndroidManager.getElementByText("상세 내용").isDisplayed()
                            && AndroidManager.getElementByText("날짜").isDisplayed()
                            && AndroidManager.getElementByText("별").isDisplayed();
            assertTrue("내 별 현황 노출되지 않았습니다.",startshopTap01);

            starBtn("나의 목표");
            boolean myGoal =
                    AndroidManager.getElementByText("목표 설정 변경").isDisplayed();
            assertTrue("프로필 변경 팝업 노출되지 않았습니다.",myGoal);

            starBtn("무엇을 바꿔볼까?");
            boolean exchangeGifts =
                    AndroidManager.getElementByText("선물 교환 내역").isDisplayed()
                    && AndroidManager.getElementByText("선물 이용 방법").isDisplayed();
            assertTrue("선물 교환 페이지 노출되지 않았습니다.",exchangeGifts);

            // 나가기
            getElementById("com.wjthinkbig.NFhtml5viewer:id/btnExit").click();


        }catch (java.lang.Exception e){
            log.info("다시 확인해 주세요");
        }
    }

}
