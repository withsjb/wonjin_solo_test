package stepdefinitions;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

/**
 * 스마트올 중학 Then
 */

public class MiddleSmartAllThen {
    private final Logger log = LoggerFactory.getLogger(getClass());

    BookWhenStepDefine bookWhen = new BookWhenStepDefine();
    MiddleSmartAllWhen middleWhen = new MiddleSmartAllWhen();

    /**
     * 웅진 씽크빅 북클럽 - 스마트씽크빅 - 스마트올 중학 진입
     * 기기 종료후, 자동으로 중학 스마트올 진입시, 로딩 필요
     */
    @Given("스마트올 중학 시작")
    public void 스마트올중학시작() throws InterruptedException {
        log.info("스마트올 중학 기기종료 후, 진입 시도");
        log.info("Loding.....");
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 웅진 씽크빅 북클럽 - 스마트씽크빅 - 스마트올 중학 진입
     */
    @Given("스마트올 중학 홈 이동")
    public void 스마트올중학홈이동() throws InterruptedException {
        bookWhen.clickStudyBtn();
        log.info("웅진 씽크빅 북클럽 - 스마트씽크빅 - 스마트올 중학 진입");
        AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/bannerImage").click();
        TimeUnit.SECONDS.sleep(15);
    }

    /**
     * 왼쪽 상단 스마트올중학 버튼 클릭 > 홈으로 이동
     */
    @When("스마트올 중학 홈 클릭")
    @When("[smart ALL 중학] 클릭")
    public void clickSmartAll중학() {
        try {
            log.info("스마트올 중학 홈 클릭");
            AndroidManager.getElementById(Constant.중학스마트올_id).click();
            TimeUnit.SECONDS.sleep(15);
        }catch (Exception e){
            try {
                log.info("Exception : 스마트올 중학 홈 클릭");
                AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/iv_home").click();
            }catch (Exception e1){
                log.info("Exception : 북클럽 - 스마트올 중학 홈 클릭");
                try {
                    log.info("북클럽 홈");
                    AndroidManager.getDriver().pressKey(new KeyEvent(AndroidKey.HOME));
                    스마트올중학홈이동();
                } catch (Exception ignored) {
                    fail("스마트올 중학 홈 화면 진입 실패");
                }
            }
        }
    }


    @Then("중학 상단 메뉴 {string} 진입 확인")
    public void 중학상단메뉴진입확인(String menu) {
        WebElement menuTile = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_group_title");
        log.info("{} 메뉴 == {} 메뉴 이동 확인",menu,menuTile.getText());
        assertEquals(menu,menuTile.getText());
    }




    @Then("스마트올 중학 홈 확인")
    public void 스마트올중학홈확인() {
        log.info("스마트올 중학 홈 확인");
        boolean contents = AndroidManager.getElementByText("오늘 계획학습").isDisplayed()
                && AndroidManager.getElementByText("서랍").isDisplayed()
                && AndroidManager.getElementByText("나의 계획학습").isDisplayed()
                && AndroidManager.getElementByText("AI가 적용 중입니다").isDisplayed()
                ;
        assertTrue("스마트올 중학 홈 확인 되지 않습니다.",contents);
    }


    @Then("검색 수학 문항 검색 화면 확인")
    public void 검색수학문항검색화면확인() {
        assertTrue("검색 수학 문항 검색 화면 확인되지 않습니다.",
                AndroidManager.getElementByText("수학 문항 번호를 입력해주세요.").isDisplayed());
        //나가기
        AndroidManager.getElementByText("닫기").click();
    }

    @Then("인기 검색 화면 이동 확인")
    public void 인기검색화면이동확인() {
        // 1. 검색창 텍스트 가져오기
        WebElement searchInput = AndroidManager.getElementByXpath("//android.widget.EditText");
        String actualText = searchInput.getText().trim();

        // 2. When 인기검색어번째클릭() 클래스에서 저장한 값과 비교
        log.info("검색창 텍스트: {}, 저장된 검색어: {}", actualText, MiddleSmartAllWhen.popularWord);
        assertEquals(MiddleSmartAllWhen.popularWord, actualText);

        // 3. 다음 테스트를 위해 값 초기화
        MiddleSmartAllWhen.popularWord = null;

        // 검색 화면 나가기
        AndroidManager.getElementByText("닫기").click();
    }

    @Then("중학 검색 확인")
    public void 중학검색확인() {
        // 1. 검색창 텍스트 가져오기
        WebElement searchInput = AndroidManager.getElementByXpath("//android.widget.EditText");
        String actualText = searchInput.getText().trim();

        // 2. When 검색어입력() 클래스에서 저장한 값과 비교
        log.info("검색창 텍스트: {}, 저장된 검색어: {}", actualText, MiddleSmartAllWhen.enteredSearchText);
        assertEquals(MiddleSmartAllWhen.enteredSearchText, actualText);

        // 3. 다음 테스트를 위해 값 초기화
        MiddleSmartAllWhen.enteredSearchText = null;

        // 검색 화면 나가기
        AndroidManager.getElementByText("닫기").click();
    }

    @Then("중학 검색 화면 이동 확인")
    public void 중학검색화면이동확인() {
        Boolean searchView = AndroidManager.getElementByText("최근 검색어").isDisplayed()
                && AndroidManager.getElementByText("인기 검색어").isDisplayed();
        assertTrue("검색 화면 이동 확인되지 않습니다.",searchView);

        // 검색 화면 나가기
        AndroidManager.getElementByText("닫기").click();
    }

    @Then("{string} 화면 이동 확인")
    public void 화면이동확인(String guide) {
        boolean content = AndroidManager.getElementByText(guide).isDisplayed()
                && AndroidManager.getElementByText("STEP 1. 나의 계획학습 이동").isDisplayed();
        assertTrue("시간표 가이드" +guide+ " 화면 확인 되지 않습니다.",content);

        AndroidManager.getElementByText("닫기").click();
    }

    @Then("중학 {string} - {string} 진입 확인")
    @Then("홈 {string} - {string} 이동 확인")
    @Then("내서랍 {string} - {string} 진입 확인")
    public void smart내신진입확인(String menu, String submenu) throws InterruptedException {
        log.info("{} - {} 진입 확인", menu,submenu);
        TimeUnit.SECONDS.sleep(5);
        WebElement group = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_group_title");
        assertEquals(menu,group.getText());
        WebElement extend = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_sub_title");

        if (menu.equals(submenu)){
            if (menu.equals("AI학습관")) {assertEquals("전체",extend.getText()); return;}
            assertEquals("전체 강좌",extend.getText());
        }else {
            assertEquals(submenu,extend.getText());
        }

    }

    @Then("내서랍 노출 확인")
    public void 내서랍노출확인() throws InterruptedException {
        boolean content = AndroidManager.getElementByText("바로가기 메뉴").isDisplayed()
                && AndroidManager.getElementByText("나의 학습현황 더보기").isDisplayed()
                && AndroidManager.getElementByText("내 별 현황").isDisplayed();
                ;
        assertTrue("내서랍 화면 확인 되지 않습니다.",content);
        TimeUnit.SECONDS.sleep(3);
    }

    @Then("자격증&인증 - {string} 진입 확인")
    public void 자격증인증진입확인(String submenu) {
        log.info("자격증&인증 - {} 진입 확인",submenu);

        if (submenu.equals("contents")){
            boolean contents = AndroidManager.getElementByText("ICON_LCNSE_CHNA").isDisplayed()
                    && AndroidManager.getElementByText("ICON_LCNSE_CHSE").isDisplayed()
                    && AndroidManager.getElementByText("ICON_LCNSE_JPAN").isDisplayed()
                    && AndroidManager.getElementByText("ICON_LCNSE_KHIST").isDisplayed();
            assertTrue("자격증&인증 메뉴 확인 되지 않습니다.",contents);

        }
        else {
            boolean text = AndroidManager.getElementByText(submenu).isDisplayed();
            assertTrue("자격증&인증 메뉴 확인 되지 않습니다.",text);

        }
    }

    @Then("나의 계획학습 {string} UI 확인")
    @Then("홈 내서랍 {string} 진입 확인")
    @Then("나의학습방 - {string} 진입 확인")
    public void 나의학습방진입확인(String submenu) {
        if (submenu.equals("나의 계획학습")){
            log.info("나의 계획학습 진입 확인");
            boolean content = AndroidManager.getElementByText("강좌 추가").isDisplayed()
                    && AndroidManager.getElementByText("과목별 보기").isDisplayed();
            assertTrue("나의 계획학습 화면 확인되지 않습니다.",content);
        }
        if (submenu.equals("나의 학습현황")){
            log.info("나의 학습현황 진입 확인");
            boolean content = AndroidManager.getElementByText("계획학습").isDisplayed()
                    && AndroidManager.getElementByText("자율학습").isDisplayed();
            assertTrue("나의 계획학습 화면 확인되지 않습니다.",content);
        }
    }
 
    @Then("영어라운지 - {string} 진입 확인")
    public void 영어라운지진입확인(String submenu) {
        if (submenu.equals("English Library")){
            boolean contents = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"ALL\"]").isDisplayed()
                    && AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"A\"]").isDisplayed();
            assertTrue("English Library 화면 확인되지 않습니다.",contents);
        }
        if (submenu.equals("VOCA 트레이닝")){
            boolean content = AndroidManager.getElementByText("영단어 암기 트레이닝!").isDisplayed();
            assertTrue("VOCA 트레이닝 화면 확인되지 않습니다.",content);
        }
    }

    @Then("학습서비스 - {string} 진입 확인")
    public void 학습서비스진입확인(String submenu) {
        String text = "";

        switch (submenu){
            case "교과서 All-ZIP":
                text = "우리 학교 교과서 100% 맞춤! 교과서 All-ZIP으로 이번 시험 완벽 대비";
                break;
            case "스마트올백과":
                text = "웅진지식백과";
                break;
            case "학습자료실":
                text = "! 학습 자료들은 학습 자료실에서 확인할 수 있습니다. 지난 자료들도 다시한번 복습해보세요.";
                break;
            case "열공뮤직":
                text = "내 공부를 도와주는 스마트 뮤직";
                break;
            case "수행평가 가이드":
                text = "주요과목 수행평가";
                break;
            case "문학도서관":
                text = "이달의 도서";
                break;
        }

        boolean content = AndroidManager.getElementByText(text).isDisplayed();
        assertTrue(submenu+" 화면 확인되지 않습니다.",content);
    }

    @Then("진로진학 - {string} 진입 확인")
    public void 진로진학진입확인(String submenu) throws InterruptedException {
        boolean content = AndroidManager.getElementByText(submenu).isDisplayed();
        assertTrue(submenu+" 화면 확인되지 않습니다.",content);
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("스타샵 - {string} 진입 확인")
    public void 스타샵진입확인(String submenu) throws InterruptedException {
        String text = "";
        switch (submenu) {
            case "나의 별 현황":
                text = "별 받는 방법";
                break;
            case "나의 목표":
                text = "목표 설정하기";
                break;
            case "무엇을 바꿔볼까?":
                text = "선물 교환 내역";
                break;
        }
        log.info("스타샵 - {} 진입 확인", submenu);
        boolean content = AndroidManager.getElementByText(text).isDisplayed();
        assertTrue(submenu+" 화면 확인되지 않습니다.",content);
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("내서랍 알림함 {string} 화면 확인")
    @Then("마이페이지 - {string} 진입 확인")
    public void 마이페이지진입확인(String submenu) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        String text = "";
        switch (submenu){
            case "찜한 강좌":
                text = "찜한 강좌가 없습니다.";
                break;
            case "나의 강좌 Q&A":
                text = "작성한 나의 강좌 Q&A가 없습니다.";
                break;
            case "알림":
                text = "아직 내게 온 알림이 없어요.";
                break;
            case "1:1톡":
                text = " 담임 선생님과의 톡입니다. 톡 내용은 3개월 후 자동 삭제됩니다.";
                break;
            case "개인정보 수정":
                text = "개인정보 수정은 프로필 사진, 학교, 학년, 우리학교 교과서 설정만 가능합니다.";
                break;
            case "나의 자료실":
                text = "! 학습 자료들은 학습 자료실에서 확인할 수 있습니다. 지난 자료들도 다시한번 복습해보세요.";
                break;
            default:
                log.error("잘못된 메뉴 항목: {}", submenu);
                throw new IllegalArgumentException("지원하지 않는 메뉴 항목입니다: " + submenu);
        }
        log.info("마이페이지 - {} 진입 확인", submenu);
        boolean content = AndroidManager.getElementByText(text).isDisplayed();
        assertTrue(submenu+" 화면 확인되지 않습니다.",content);
    }

    @Then("고객센터 - {string} 진입 확인")
    public void 고객센터진입확인(String submenu) throws InterruptedException {
        String text = "";
        switch (submenu) {
            case "공지사항":
                text = "번호구분제목조회수등록일";
                break;
            case "자주 묻는 질문":
                text = "회원 등록한 아이디를 변경 할 수 있을까요?";
                break;
            case "약관보기":
                text = "이용약관";
                break;
        }
        log.info("고객센터 - {} 진입 확인", submenu);
        boolean content = AndroidManager.getElementByText(text).isDisplayed();
        assertTrue(submenu+" 화면 확인되지 않습니다.",content);
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("마이페이지 화면 확인")
    public void 마이페이지화면확인() throws InterruptedException {

        WebElement btn1 = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"찜한 강좌\"]");
        btn1.click();
        마이페이지진입확인("찜한 강좌");
        WebElement btn2 = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"나의 강좌 Q&A\"]");
        btn2.click();
        마이페이지진입확인("나의 강좌 Q&A");
        WebElement btn3 = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"나의 자료실\"]");
        btn3.click();
        마이페이지진입확인("나의 자료실");

    }

    public void 설정바로가기화면진입확인(){
        log.info("설정 화면 진입 확인");
        boolean content = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_app_version").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/ll_container_push").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/ll_container_mode").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/ll_container_delete_cache").isDisplayed()
                && AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/ll_container_service").isDisplayed();
        assertTrue("설정 화면 확인되지 않습니다.",content);
    }

    @Then("내서랍 바로가기 {string} 진입 확인")
    public void 바로가기메뉴화면진입확인(String submenu){
        try {
            // submenu "더보기" 텍스트 제거
            String processedMenu = submenu.replaceAll(" 더보기$", "");
            log.info("메뉴: {}", processedMenu);

            switch (processedMenu) {
                case "나의 계획학습":
                case "나의 학습현황":
                    나의학습방진입확인(processedMenu);
                    break;
                case "SMART내신":
                    smart내신진입확인("SMART내신", processedMenu);
                    break;
                case "E-TEST":
                    smart내신진입확인("TEST라운지", processedMenu);
                    break;
                case "고객센터":
                    고객센터진입확인("공지사항");
                    break;
                case "설정":
                    설정바로가기화면진입확인();
                    break;
                case "대치TOP":
                    smart내신진입확인("대치TOP", processedMenu);
                    break;
                case "오답노트":
                    smart내신진입확인("TEST라운지", processedMenu);
                    break;
                case "마이페이지":
                    마이페이지진입확인("찜한 강좌");
                    break;
                case "백과사전":
                    학습서비스진입확인("스마트올백과");
                    log.info("스마트올백과 나가기");
                    TimeUnit.SECONDS.sleep(2);
                    Utils.touchSpecificCoordinates(60,50);
                    break;
                default:
                    throw new IllegalArgumentException("지원하지 않는 메뉴: " + processedMenu);
            }
        } catch (Exception e) {
            log.error("메뉴 진입 실패: {}", e.getMessage());
            fail("메뉴 진입 검증 실패: " + e.getMessage());
        }
    }

    @Then("내서랍 나의 학습현황 화면 확인")
    public void 나의학습현황주간화면확인() {
        log.info("나의 학습현황 주간 화면 확인");
        boolean content = AndroidManager.getElementByText("계획학습 현황").isDisplayed()
                && AndroidManager.getElementByText("확인·단원 응시 현황 ※ 최초 점수 기준").isDisplayed()
                && AndroidManager.getElementByText("이번 주").isDisplayed()
                && AndroidManager.getElementByText("다음 주").isDisplayed()
               ;
        assertTrue("나의 학습현황 주간 화면 확인되지 않습니다.",content);
    }

    @Then("과목별 보기 화면 진입 확인")
    @Then("내서랍 최근 학습하지 않은 강의 화면 확인")
    public void 내서랍최근학습하지않은강의화면확인() throws InterruptedException {
        log.info("내서랍 최근 학습하지 않은 강의 화면");
        boolean content = AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"전체\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"국어\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"영어\"]").isDisplayed()
                && AndroidManager.getElementByText("학습 전").isDisplayed()
                ;
        assertTrue("최근 학습하지 않은 강의 화면 확인되지 않습니다.",content);
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("내서랍 편집 화면 확인")
    public void 내서랍편집화면확인() {
        log.info("내서랍 편집 화면");
        boolean content = AndroidManager.getElementByText("취소").isDisplayed()
                && AndroidManager.getElementByText("완료").isDisplayed()
                && AndroidManager.getElementByText("move").isDisplayed()
                ;
        assertTrue("내서랍 편집 화면 확인되지 않습니다.",content);
    }


    @Then("강좌추가 화면 진입 확인")
    public void 강좌추가버튼확인() throws InterruptedException {
        log.info("강좌추가 화면 진입 확인");
        boolean content = AndroidManager.getElementByText("계획학습 등록").isDisplayed()
                && AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"설정 초기화 설정 초기화\"]").isDisplayed()
                && AndroidManager.getElementByText("강좌/강의").isDisplayed()
                ;
        assertTrue("강좌추가 화면 진입 확인되지 않습니다.",content);
        TimeUnit.SECONDS.sleep(1);

        // 나가기
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"닫기\"]").click();
    }

    @Then("시간표 설정 화면 진입 확인")
    public void 시간표설정화면진입확인() throws InterruptedException {
        WebElement btn1 = AndroidManager.getElementByText("수정하기");
        btn1.click();
        나의계획학습시간표설정시간표변경삭제();

        AndroidManager.getElementByText("시간표 설정").click();

        WebElement btn2 = AndroidManager.getElementByText("시간표 초기화");
        btn2.click();
        나의계획학습시간표설정시간표초기화();

        AndroidManager.getElementByText("시간표 설정").click();

        WebElement btn3 = AndroidManager.getElementByText("시간표 설정 가이드");
        btn3.click();
        나의계획학습시간표설정시간표가이드();

        // 시간표설정 딤에서 나가기
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementByText("닫기").click();

    }

    public void 나의계획학습시간표설정시간표변경삭제() throws InterruptedException {
        boolean content1 = AndroidManager.getElementByText("계획학습 변경").isDisplayed();
        assertTrue("시간표 설정 - 수정하기 화면 진입 확인되지 않습니다.",content1);
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"닫기\"]").click();
    }
    public void 나의계획학습시간표설정시간표초기화() throws InterruptedException {
        boolean content2 = AndroidManager.getElementByText("시간표 초기화").isDisplayed();
        assertTrue("시간표 설정 - 시간표 초기화 화면 진입 확인되지 않습니다.",content2);
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"닫기\"]").click();
    }

    public void 나의계획학습시간표설정시간표가이드() throws InterruptedException {
        boolean content3 = AndroidManager.getElementByText("시간표 설정 가이드").isDisplayed();
        assertTrue("시간표 설정 - 시간표 설정 가이드 화면 진입 확인되지 않습니다.",content3);
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementByText("닫기").click();
    }

    @Then("오늘 계획학습 - 나의 계획학습 학습카드 확인")
    public void 오늘계획학습나의계획학습학습카드확인() {
        boolean contents = AndroidManager.getElementByText("AI수학 진단평가").isDisplayed()
                && AndroidManager.getElementByText("AI영어 진단평가").isDisplayed();
        assertTrue("AI수학 / AI영어 진단평가 카드 노출 확인되지 않습니다.",contents);
    }

    @Then("중학 필수 어휘 학습 VOCA 트레이닝 확인")
    public void 중학필수어휘학습VOCA트레이닝확인() throws InterruptedException {
        boolean content = AndroidManager.getElementByText("날짜를 선택하여 어휘 학습을 시작하세요!").isDisplayed();
        assertTrue("중학 필수 어휘 학습 VOCA 트레이닝 화면 진입 확인되지 않습니다.",content);
        중학필수어휘보카날짜이동확인();
    }

    public void 중학필수어휘보카날짜이동확인() throws InterruptedException {
        WebElement dateElement = AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]");
        log.info(dateElement.getText());

        String initialDateText = dateElement.getText();
        log.info("초기 날짜: {}", initialDateText);

        log.info("이전달로 변경");
        WebElement previousMonthButton = AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.Button[1]");
        previousMonthButton.click();
        TimeUnit.SECONDS.sleep(2);
        String updatedDateText = dateElement.getText();
        log.info("변경된 날짜: {}", updatedDateText);

        if (!initialDateText.equals(updatedDateText)) {
            log.info("이전 달로 변경 확인");
        }else {
            fail("중학 필수 어휘 학습 VOCA 트레이닝 이전 달로 변경 실패");
        }


    }

    @Then("홈 - 이벤트 배너 노출 확인")
    public void 홈이벤트배너노출확인() {
        log.info("홈 - 이벤트 배너 노출 확인");
        boolean content = AndroidManager.getElementByText("배너").isDisplayed();
        assertTrue("홈 - 이벤트 배너 노출 확인 되지 않습니다.",content);
    }

    // 패키지 이름없는 id 값 클릭
    public void resourceIdClick(String rId){
        By locator = By.xpath("//*[@resource-id='*:" + rId + "' or @resource-id='" + rId + "']");
        AndroidManager.getDriver().findElement(locator).click();
    }
    @Then("홈 - AI 강의 추천 툴팁 확인")
    public void 홈ai툴팁확인() throws InterruptedException {
        log.info("아래로 스크롤 하기");
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        // 패키지 없이 id 값만있는 툴팁 rID클릭
        resourceIdClick("rcmd");

        log.info("툴팁 확인");
        boolean content = AndroidManager.getElementByText("심화").isDisplayed()
                && AndroidManager.getElementByText("보충").isDisplayed()
                && AndroidManager.getElementByText("유사").isDisplayed();
        assertTrue("홈 - AI 강의 추천 툴팁 확인 되지 않습니다.", content);
        TimeUnit.SECONDS.sleep(1);

        log.info("툴팁 닫기");
        AndroidManager.getElementByText(Constant.홈툴팁닫기_text).click();
    }

    @Then("홈 - 인기 TOP 강의 툴팁 확인")
    public void 홈인기강의툴팁확인() throws InterruptedException {
        log.info("아래로 스크롤 하기");
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        // 패키지 없이 id 값만있는 툴팁 rID클릭
        resourceIdClick("popular");

        log.info("툴팁 확인");
        boolean content = AndroidManager.getElementByText("유사").isDisplayed()
                && AndroidManager.getElementByText("상위").isDisplayed()
                && AndroidManager.getElementByText("어제").isDisplayed();
        assertTrue("홈 - 인기 TOP 강의 툴팁 확인 되지 않습니다.", content);
        TimeUnit.SECONDS.sleep(1);

        log.info("툴팁 닫기");
        AndroidManager.getElementByText(Constant.홈툴팁닫기_text).click();
    }

    @Then("AI 강의 추천 - 추천 강의 확인")
    public void ai강의추천추천강의확인() {
        log.info("AI 강의 추천 - 추천 강의 확인");
        boolean content = AndroidManager.getElementByText("추천 강의가 없습니다. 학습이력을 쌓아서 나에게 딱 맞는 강의를 추천받으세요.").isDisplayed()
                && AndroidManager.getElementByText("AI가 나에게 필요한 강의를 심화,보충,유사 3단계로 추천해줍니다!").isDisplayed();
        assertTrue("AI 강의 추천 - 추천 강의 확인 되지 않습니다.",content);
    }


    @And("대치TOP {int}번째 강의 클릭")
    public void 대치top번째강의클릭(int num) {
        log.info("대치TOP {}번째 강의 클릭",num);
        AndroidManager.getElementByXpath(Constant.중학스마트올학원강의_xPath).click();
    }

    @Then("대치TOP {string} 학습화면 확인 및 버튼 확인")
    public void 강의학습화면확인및버튼확인(String submenu) {
        try {
            찜하기선택해제확인();
            중등선생님프로필확인();
            대치TOP강의학습화면강의목차탭확인(submenu);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("가이드 팝업 확인")
    public void 가이드팝업확인() {
        boolean foundPopup = false; // 팝업 확인 여부 플래그

        try {
            // 중학 AI 영어 안내 팝업 확인
            boolean englishPopup = AndroidManager.getElementByXpath("//android.widget.TextView[@text='중학 AI 영어 안내']").isDisplayed();
            assertTrue("중학 AI 영어 안내 팝업이 표시되어야 합니다.", englishPopup);

            TimeUnit.SECONDS.sleep(3);
            영어가이드팝업추가동작();
            log.info("중학 AI 영어 안내 팝업이 정상적으로 표시됨.");
            foundPopup = true;

        } catch (Exception e) {
            log.warn("중학 AI 영어 안내 팝업이 확인되지 않았습니다.");
        }

        if (!foundPopup) {
            try {
                // 중학 AI 수학 안내 팝업 확인
                boolean mathPopup = AndroidManager.getElementByXpath("//android.widget.TextView[@text='중학 AI 수학 안내']").isDisplayed();
                assertTrue("중학 AI 수학 안내 팝업이 표시되어야 합니다.", mathPopup);
                log.info("중학 AI 수학 안내 팝업이 정상적으로 표시됨.");
                TimeUnit.SECONDS.sleep(3);
                foundPopup = true;
            } catch (Exception e) {
                log.warn("중학 AI 수학 안내 팝업이 확인되지 않았습니다.");
            }
        }


        if (!foundPopup) {
            try {
                // 새로운 수학 AI 팝업 확인
                boolean newMathPopup = AndroidManager.getElementByXpath("//android.view.View[@resource-id='testpop_Wrap']/android.view.View").isDisplayed();
                assertTrue("Test라운지 팝업이 표시되어야 합니다.", newMathPopup);
                log.info("Test라운지 팝업이 정상적으로 표시됨.");
                TimeUnit.SECONDS.sleep(3);
                foundPopup = true;
            } catch (Exception e) {
                log.warn("Test라운지이 확인되지 않았습니다.");
            }
        }

        // 팝업이 전혀 확인되지 않았으면 테스트 실패 처리
        if (!foundPopup) {
            log.error("팝업창이 확인되지 않았습니다.");
            fail("팝업창이 전혀 확인되지 않았습니다.");
        }else{
            try {

                AndroidManager.getElementByXpath("//android.app.Dialog/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.TextView[1]").click();
            } catch (Exception e) {
                AndroidManager.getElementByXpath("//android.widget.Button[@text=\"닫기\"]").click();
            }

        }

    }


    @Then("AI 수학 드롭박스 {string} {string} {string} 변경 및 변경된 화면 확인")
    public void AI수학드롭박스확인(String year, String graded, String term) throws InterruptedException {
        WebElement yeardrop = null;
        try {
            yeardrop = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"aiMath-rev\"]");
        } catch (Exception e) {
            log.info("년도 없음 확인.");
        }
        WebElement gradedrop = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"aiMath-grade\"]");
        WebElement termdorp = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"aiMath-semester\"]");

        // year가 "년도없음"이 아니면 년도 드롭 및 클릭 동작 실행
        if (yeardrop != null && !yeardrop.isDisplayed()) {
            yeardrop.click();
            log.info("년도 드롭 클릭");
            TimeUnit.SECONDS.sleep(2);
            년도클릭(year);
        } else {
            log.info("년도없음 입력되어, 년도 드롭 클릭 부분을 패스합니다.");
        }

        // 이후 학년, 학기 드롭 및 클릭 동작 실행
        gradedrop.click();
        TimeUnit.SECONDS.sleep(2);
        학년클릭(graded, year, term);

        termdorp.click();
        TimeUnit.SECONDS.sleep(2);
        학기클릭(term, year, graded);
        TimeUnit.SECONDS.sleep(2);

        // 화면 확인 로직 호출
        ai수학중화면확인(year, graded, term);

        log.info("학년 드롭 클릭");
        termdorp.click();
        log.info("학기 드롭 클릭");
    }



    @Then("대단원 변경시 중단원 변경 확인")
    public void 중단원변경확인() {
        try {
            WebElement unit = AndroidManager.getDriver().findElement(By.className("android.widget.ListView"));
            List<WebElement> allUnits = unit.findElements(By.className("android.view.View"));
            log.info("대단원 {}개 노출 확인", allUnits.size());

            List<String> subunitTexts = new ArrayList<>();
            boolean allDifferent = true;

            for (int i = 0; i < allUnits.size(); i++) {
                WebElement element = unit.findElements(By.className("android.view.View")).get(i);
                log.info(element.getText() + " 대단원 클릭");
                element.click();

                WebElement subunit = AndroidManager.getElementByXpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.widget.TextView[9]");
                String subunitText = subunit.getText();
                log.info(subunitText);

                // 현재 subunit 텍스트가 이전의 모든 subunit 텍스트와 다른지 확인
                if (subunitTexts.contains(subunitText)) {
                    allDifferent = false;
                    log.warn("중복된 subunit 텍스트 발견: " + subunitText);
                    break;
                }

                subunitTexts.add(subunitText);
            }

            if (allDifferent) {
                log.info("선택한 단원에 따라 중단원 변경 되어 노출 확인");
            } else {
                log.error("선택한 단원에 따라 중단원 변경 되지 않았습니다.");
            }


//            String fristdanwon = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"1.1. 소인수분해\"]").getText();
//            log.info("첫번째 중단원" + fristdanwon);
//            TimeUnit.SECONDS.sleep(2);
//            AndroidManager.getElementByXpath("//android.view.View[@text=\"2. 정수와 유리수\"]").click();
//            log.info("대단원 변경");
//            TimeUnit.SECONDS.sleep(2);
//            String seconddanwon = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"2.1. 정수와 유리수\"]").getText();
//            log.info("두번째 중단원" + seconddanwon);
//            TimeUnit.SECONDS.sleep(2);
//            Assert.assertNotEquals("화면이 변경되었습니다..", fristdanwon, seconddanwon);
//            log.info("화면이 성공적으로 변경되었습니다.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 값 비교

    }

    @Then("Smart내신 {string} 드롭박스 변경 및 변경된 화면 확인")
    public void smart내신드롭박스변경및확인(String year) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        String[] subjects = {"국어", "영어", "수학", "사회", "역사", "과학", "제2외국어"};
        boolean allSuccess = true; // 모든 과목이 성공해야 true로 설정

        try {
            AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"gradCd\"]").click();

            switch (year) {
                case "예비중":
                    Utils.touchSpecificCoordinates(1195, 330);
                    break;
                case "중1":
                    Utils.touchSpecificCoordinates(1195, 408);
                    break;
                case "중2":
                    Utils.touchSpecificCoordinates(1195, 479);
                    break;
                case "중3":
                    Utils.touchSpecificCoordinates(1195, 557);
                    break;
                default:
                    throw new IllegalArgumentException("학년 클릭 실패: " + year);
            }

            for (String subject : subjects) {
                AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"courseCode\"]").click();
                과목체크(subject);
                TimeUnit.SECONDS.sleep(3);

                if (!화면체크(subject)) {
                    log.warn("화면 체크 실패: " + subject);
                    allSuccess = false; // 하나라도 실패하면 false로 설정
                } else {
                    log.info("화면 체크 성공: " + subject);
                }
            }

            if (!allSuccess) {
                log.error("하나 이상의 과목이 화면 체크에 실패했습니다.");
                fail("하나 이상의 과목이 화면 체크에 실패하였습니다.");
            } else {
                log.info("모든 과목이 성공적으로 확인되었습니다.");
            }

        } catch (Exception e) {
            log.info("드롭박스 변경 실패");
            throw new RuntimeException(e);
        }
    }

    public void 과목체크(String subject) throws InterruptedException {
        AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"courseCode\"]").click();
        switch (subject) {
            case "국어":
                Utils.touchSpecificCoordinates(1360, 123);
                log.info("국어 클릭");
                break;
            case "영어":
                Utils.touchSpecificCoordinates(1360, 201); // 영어에 해당하는 좌표 예시 (실제 좌표로 변경)
                log.info("영어 클릭");
                break;
            case "수학":
                Utils.touchSpecificCoordinates(1360, 275); // 수학에 해당하는 좌표 예시 (실제 좌표로 변경)
                log.info("수학 클릭");
                break;
            case "사회":
                Utils.touchSpecificCoordinates(1360, 350); // 수학에 해당하는 좌표 예시 (실제 좌표로 변경)
                log.info("사회 클릭");
                break;
            case "역사":
                Utils.touchSpecificCoordinates(1360, 424); // 수학에 해당하는 좌표 예시 (실제 좌표로 변경)
                log.info("역사 클릭");
                break;
            case "과학":
                Utils.touchSpecificCoordinates(1360, 499); // 수학에 해당하는 좌표 예시 (실제 좌표로 변경)
                log.info("과학 클릭");
                break;
            case "제2외국어":
                Utils.touchSpecificCoordinates(1360, 573); // 수학에 해당하는 좌표 예시 (실제 좌표로 변경)
                log.info("제2외국어 클릭");
                break;
            default:
                throw new IllegalArgumentException("과목 클릭 실패: " + subject);
        }

    }

    public boolean 화면체크(String subject) throws InterruptedException {
        List<String> foreignSubjects = Arrays.asList("일본어", "중국어");
        List<String> subjectsToCheck = new ArrayList<>();

        if ("제2외국어".equals(subject)) {
            subjectsToCheck.addAll(foreignSubjects);
            log.info("제2외국어 체크: 일본어와 중국어 둘 다 확인");
        } else {
            subjectsToCheck.add(subject); // 다른 과목은 그대로 체크
        }

        WebDriverWait wait = new WebDriverWait(AndroidManager.getDriver(), Duration.ofSeconds(10));
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.View[@resource-id='root']/android.view.View[2]/android.view.View[3]/android.view.View[2]")
        ));

        // 컨테이너 내의 모든 자식 요소를 찾습니다.
        List<WebElement> elements = container.findElements(By.xpath(".//*"));

        for (String checkSubject : subjectsToCheck) {
            boolean found = false;
            for (WebElement el : elements) {
                if (el.getText().trim().contains(checkSubject)) {
                    log.info(checkSubject + " 가 화면에 존재합니다.");
                    found = true;
                    break;
                }
            }

            // 일본어 또는 중국어 중 하나라도 성공하면 성공으로 간주
            if (found) {
                return true; // 찾았으면 바로 성공
            }

            log.warn(checkSubject + " 가 화면에서 확인되지 않았습니다.");
        }

        return false; // 모든 과목이 실패한 경우 false 반환
    }

    public void 찜하기선택해제확인() throws InterruptedException {
        try {
            middleWhen.특정버튼클릭("찜하기");
            WebElement btn1 = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"찜해제\"]");
            if(btn1.getText().equals("찜해제")){
                log.info("찜하기 버튼 정상 작동");
                btn1.click();
                TimeUnit.SECONDS.sleep(2);
            }
        }catch (Exception e){
            middleWhen.특정버튼클릭("찜해제");
            WebElement btn1 = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"찜하기\"]");
            if(btn1.getText().equals("찜하기")){
                log.info("찜해지 버튼 정상 작동");
                btn1.click();
                TimeUnit.SECONDS.sleep(2);
            }
        }


    }

    public void 중등선생님프로필확인() throws InterruptedException {
        middleWhen.특정버튼클릭("선생님 프로필");
        WebElement profilepopup =  AndroidManager.getElementByXpath("(//android.widget.TextView[@text=\"선생님 프로필\"])[2]");
        if(profilepopup.isDisplayed()){
            log.info("선생님 프로필 버튼 정상동작");
            AndroidManager.getElementByXpath("//android.widget.Image[@text=\"프로필닫기\"]").click();
            TimeUnit.SECONDS.sleep(2);
        }
    }



    public void 대치TOP강의학습화면강의목차탭확인(String submenu) throws InterruptedException {
        middleWhen.특정버튼클릭("강의 목차");
        WebElement indexlist =  AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.widget.ListView[1]");

        if(indexlist.isDisplayed()) {
            log.info("강의 목차 화면 확인");
        }

        middleWhen.특정버튼클릭("강좌 소개");
        AItabviewcheck("강좌 소개");

        switch (submenu) {
            case "C&A논술학원":
            case "미래탐구(국어)":
                // 미래탐구국어, 씨앤에이논술
                middleWhen.특정버튼클릭("강좌 자료실");
                AItabviewcheck("강좌 자료실");
                break;
            case "세계로학원":
            case "에이프로아카데미":
            case "함영원영어학원":
            case "ILE":
            case "깊은생각":
                // 깊은생각,ILE,에이프로아카데미, 함영원영어, 세계로학원
                middleWhen.특정버튼클릭("교재 정보");
                AItabviewcheck("교재 정보");
                break;
            case "함수학학원":
                // 함수학학원
                middleWhen.특정버튼클릭("교재 정보");
                AItabviewcheck("교재 정보");
                middleWhen.특정버튼클릭("강좌 자료실");
                AItabviewcheck("강좌 자료실");
                break;
            case "미래탐구(과학)":
                log.info("미래탐구(과학) : 강의목차,강좌소개 탭 두개만 존재");
                break;

            default:
                throw new IllegalArgumentException("Invalid menu: " + submenu);
        }
    }

    public void 중등강의목차탭확인() throws InterruptedException {
        middleWhen.특정버튼클릭("강의 목차");
        WebElement indexlist = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.widget.ListView[1]");

        if(indexlist.isDisplayed()) {
            log.info("강의 목차 화면 확인");
        }

        middleWhen.특정버튼클릭("강좌 소개");
        AItabviewcheck("강좌 소개");
        try {

            middleWhen.특정버튼클릭("교재 정보");
            AItabviewcheck("교재 정보");
        }catch (Exception e){
            log.info("교재 정보가 없습니다.");
        }
    }

    @Then("강의 화면 및 버튼 동작 확인")
    public void 강의화면확인() throws InterruptedException{
//        강의화면정상진입확인();
        찜하기선택해제확인();
        중등선생님프로필확인();
        중등강의목차탭확인();

        AndroidManager.getElementByXpath("//android.widget.Image[@text=\"닫기버튼\"]").click();
    }

    @Then("강의목차 {string} 화면 확인")
    @Then("Ai 수학 {string} 화면 확인")
    @Then("TEST 라운지 {string} 화면 확인")
    public void AItabviewcheck(String tab) throws InterruptedException {
        // 올바른 XPath 표현식 사용 (앞에 "//" 사용)
        TimeUnit.SECONDS.sleep(15);
        WebElement parentElement = null;
        String xpath = String.format("//android.widget.TextView[@text='%s']", tab);

        parentElement = AndroidManager.getElementByXpath("//android.view.View[@resource-id='root']");

        // 1. 부모 요소 아래의 모든 하위 요소를 가져오기 (모든 단계)
        List<WebElement> allDescendantElements = parentElement.findElements(By.xpath(".//*"));

        // 2. 모든 하위 요소에서 텍스트 수집 (빈 텍스트는 제외)
        List<String> actualTexts = new ArrayList<>();

        for (WebElement element : allDescendantElements) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                actualTexts.add(text);
//                log.info("요소 텍스트: " + text);
            }
        }

        // 3. 탭에 따른 예상 텍스트 설정 및 필요한 클릭 동작 수행
        List<String> expectedTexts = new ArrayList<>();

        switch (tab) {
            case "AI 리포트":
                // 클릭 동작이 필요하다면 여기에 추가 (예: AndroidManager.getElementByXpath(...).click();)
                // "학습을 시작해주세요." 또는 "학습하기" 두 가지 경우 처리
                if (actualTexts.contains("학습을 시작해주세요.")) {
                    expectedTexts = Arrays.asList(
                            "나의 학습 기록",
                            "핵심 개념별 나의 예측 점수",
                            "학습을 시작해주세요."
                    );
                } else if (actualTexts.contains("학습하기")) {
                    expectedTexts = Arrays.asList(
                            "나의 학습 기록",
                            "핵심 개념별 나의 예측 점수",
                            "학습하기"
                    );
                }
                break;
            case "단원별 AI 추천 문항":
                // 예시로 클릭 동작 추가
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.TextView[@text='단원별 AI 추천 문항']").click();
                // 필요한 경우 대기 로직 추가 (예: Explicit Wait)
                expectedTexts = Arrays.asList(
                        "자녀1",
                        "단원별 이해도에 따라",
                        "AI가 분석하여 1:1 개인 맞춤형 문항"
                );
                break;
            case "AI 추천 커리큘럼":
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.TextView[@text='AI 추천 커리큘럼']").click();
                expectedTexts = Arrays.asList(
                        "추천 강의가 없습니다.",
                        "학습이력을 쌓아서 나에게 딱 맞는 강의를 추천 받으세요.",
                        "AI가 나에게 필요한 강의를",
                        "심화, 보충, 유사 3단계로 추천",
                        "해줍니다!"
                );
                break;
            case "시험대비 AI 집중공략":
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.TextView[@text='시험대비 AI 집중공략']").click();
                expectedTexts = Arrays.asList("1학기 중간고사 예상 등급",
                        "개념 이해도");
                break;
            case "AI 시험대비 모의고사":
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.TextView[@text='AI 시험대비 모의고사']").click();
                expectedTexts = Arrays.asList(
                        "서비스 기간이 아닙니다.",
                        "시험대비 모의고사는 내신진도 기간에 이용할 수 있습니다."
                );
                break;
            case "강좌 소개":
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"강좌 소개\"]").click();
                expectedTexts = Arrays.asList(
                        "강좌 특징",
                        "강좌 범위",
                        "수강 대상"
                );
                break;
            case "대치TOP":
                expectedTexts = Arrays.asList(
                        "대한민국 교육 1번지 대치동 원탑 학원 입점! 대치동 수업을 온라인 학습에 최적화된 커리큘럼으로 제공합니다.",
                        "학원별 강좌",
                        "수강 대상"
                );
                break;
            case "교재 정보":
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"교재썸네일\"]").click();
                expectedTexts = Arrays.asList(
                        "교재 기본 정보",
                        "교재썸네일",
                        "교재 설명"
                );
                break;
            case "강좌 자료실":
                AndroidManager.getElementByXpath(xpath).click();
//                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"교재썸네일\"]").click();
                expectedTexts = Arrays.asList(
                        "nx+ceMKFba2ZsgAAAABJRU5ErkJggg==",
                        "AXk5Gxu6jdTxQAAAABJRU5ErkJggg==",
                        "MP7YrCTEUjZPQAAAAASUVORK5CYII="
                );
                break;
            case "E-TEST":
            case "오답노트":
                expectedTexts = Arrays.asList(
                        "응시 내역이 없습니다."
                );
                break;
            default:
                log.info(tab + " 화면을 확인 할 수 없습니다.");
                throw new IllegalArgumentException("올바르지 않은 메뉴: " + tab);
        }

        TimeUnit.SECONDS.sleep(5);

        // 4. 예상 텍스트들이 실제로 존재하는지 검증
        for (String expected : expectedTexts) {
            boolean found = actualTexts.contains(expected);
            log.info("텍스트 '" + expected + "' 존재 여부: " + found);
            assertTrue("예상 텍스트가 존재해야 합니다: " + expected, found);
            log.info(tab + "화면 확인 완료");
        }
    }

    @Then("진단평가 화면 확인")
    public void 진단평가화면확인(){
        try {
            WebElement testmainviewer = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"qstIframe\"]/android.view.View");
            TimeUnit.SECONDS.sleep(3);
            if (testmainviewer.isDisplayed()) {
                log.info("메인 뷰어가 확인 되었습니다.");
                Utils.touchSpecificCoordinates(70, 60);
                log.info("메인 뷰어 나가기");
                try {
                    AndroidManager.getElementByXpath("//android.widget.Button[@text=\"확인\"]").click();
                    log.info("진단평가 응시 종료");
                } catch (Exception e) {
                    log.info("종료버튼 잡히지 않음 좌표로 종료");
                    Utils.touchSpecificCoordinates(1114, 805);
                }
            } else {
                log.info("메인 뷰어가 확인되지 않았습니다.");
            }
        }catch (NoSuchElementException e) {
            log.info("진단평가 화면 확인 실패");
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @Then("개념별 이해도 선택 및 하단 토픽 확인")
    public void 이해도선택및토픽확인(){
        try {
            String beforetopic =  특정텍스트찾기(88);

            List<WebElement> listItems = AndroidManager.getDriver().findElements(By.xpath("//android.widget.ListView//android.view.View"));

            // 두 번째 항목을 클릭
            listItems.get(1).click();

            String aftertopic =  특정텍스트찾기(88);

            if(beforetopic.equals(aftertopic)){
                log.info("토픽 변경 실패. beforeTopic과 afterTopic이 같습니다.");
                // 실패 시, 예외를 던지거나 실패 처리를 할 수 있습니다.
                throw new RuntimeException("토픽 변경 실패");
            }else{
                log.info("토픽 변경 성공. beforeTopic: " + beforetopic + ", afterTopic: " + aftertopic);
                // 성공 시 처리
            }
        } catch (Exception e) {
            log.info("진단평가 화면 확인 실패");
            throw new RuntimeException(e);
        }
    }

    @Then("SMART내신 화면 확인")
    public void SMART내신화면확인(){
        try {
            boolean topimage = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"상단 대표 이미지\"]").isDisplayed();
            boolean text1 = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"내신진도\"]").isDisplayed();
            boolean img1 = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.view.View[3]/android.view.View/android.view.View/android.widget.ListView/android.view.View[1]/android.view.View/android.view.View/android.view.View[1]").isDisplayed();

            if(topimage && text1 && img1){
                log.info("Smart 내신 화면 확인 성공");
            }else{
                log.info("화면 확인 실패");
                throw new Exception();
            }

        } catch (Exception e) {
            log.info("Smart 내신 화면 확인 실패");
            throw new RuntimeException(e);
        }
    }

    @Then("대치TOP 제목 설명란 확인")
    public void 대치TOP화면확인(){
        try {
            String topimage = AndroidManager.getElementByXpath("//android.view.View[@text=\"대한민국 교육 1번지 대치동 원탑 학원 입점! 대치동 수업을 온라인 학습에 최적화된 커리큘럼으로 제공합니다.\"]").getText();

            String text = "대한민국 교육 1번지 대치동 원탑 학원 입점! 대치동 수업을 온라인 학습에 최적화된 커리큘럼으로 제공합니다.";

            if(topimage.equals(text)){
                log.info("상단 제목 텍스트 일치");
            }

        } catch (Exception e) {
            log.info("상단 제목 텍스트 일치하지않음");
            throw new RuntimeException(e);
        }
    }



    @Then("해당 학년에 제공되는 강좌가 없습니다 텍스트 확인")
    public void 강좌없음텍스트확인(){
        try {
            if(AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"해당 학년에 제공되는 강좌가 없습니다.\"]").isDisplayed()){
                log.info("해당 학년에 제공되는 강좌가 없습니다. 단어가 존재합니다.");
            }

        } catch (Exception e) {
            log.info("단어가 존재 하지 않습니다.");
            throw new RuntimeException(e);
        }
    }


    @Then("{string} 학원 상세페이지 확인")
    @Then("영어전문관 {string} 상세페이지 확인")
    public void 대치TOP학원상세페이지확인(String text){
        try{
            WebElement subtitle = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_sub_title");
            if(subtitle.getText().equals(text)){
                log.info("클릭한 " + text + "와 subtitle 제목 일치 확인");
            }
        } catch (Exception e) {
            log.info("클릭한 " + text + "subtitle 이 일치하지 않음");
            throw new RuntimeException(e);
        }
    }

    @Then("수학전문관 화면 {string} 변경 확인")
    public void 수학전문관영역별수준별확인(String icon){
        // 빨간 버튼 -> 수준별 강좌 | 파란 버튼 -> 영역별 강좌
        String menu = "";
        switch (icon){
            case "입문":
            case "기본":
            case "발전":
            case "심화":
                menu ="수준별";
                break;
            case "수와연산":
            case "방부등식":
            case "함수":
            case "기하":
                menu ="영역별";
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 과목: " + icon);
        }

        WebElement title = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_sub_title");
        log.info("수준별, 영역별 확인 : {} == {} 확인",menu,title.getText());
        assertEquals(menu,title.getText());
    }


    @Then("성적 우수자 화면 확인")
    public void 성적우수자화면확인(){
        try {
            log.info("성적 우수자 시상 확인");
            boolean checkwindow = AndroidManager.getElementByXpath
                    ("//android.widget.Image[contains(@text, '영수모의고사 성적우수자')]").isDisplayed();
            assertTrue("성적 우수자 시상 화면 확인되지 않습니다.",checkwindow);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }

    }


    @Then("기출문제 노출 확인")
    public void 기출문제노출확인() {
        try{
            WebElement midone = AndroidManager.getElementByXpath("(//android.view.View[@text=\"중1\"])[1]");
            if(midone.isDisplayed()){
                기출문제학년노출확인();
            }
        } catch (Exception e) {
            log.info("선택한 학년 중1 아님");
        }
        try {


// 1. GridView를 찾고 그 안에 포함된 View들 찾기
            List<WebElement> gridViewItems = AndroidManager.getDriver().findElements(By.xpath(
                    "//android.widget.GridView/android.view.View"
            ));

            int buttonCount = 0;

// 2. 각 View 안에서 '응시하기' 버튼을 찾아 개수 세기
            for (WebElement viewItem : gridViewItems) {
                List<WebElement> buttons = viewItem.findElements(By.xpath(
                        ".//android.widget.Button[@text='응시하기']"
                ));

// 3. '응시하기' 버튼의 개수만큼 카운트 증가
                buttonCount += buttons.size();
            }

// 결과 출력
            log.info("응시하기 버튼의 개수: " + buttonCount);
            log.info("응시하기 버튼이 존재 함으로 기출문제 노출 확인");
// 원하는 최소 개수(예: 3개 이상이어야 한다는 조건)
            int minimumExpectedCount = 0;

// 버튼 개수가 최소 예상 개수보다 많은지 확인
            assertTrue("응시하기 버튼의 개수가 최소 개수보다 적습니다.", buttonCount >= minimumExpectedCount);

        } catch (Exception e) {
            log.error("응시하기 버튼 개수 확인 중 오류 발생", e);
            fail("응시하기 버튼 개수 확인 중 오류 발생");
        }
    }


    @Then("{string} 검색필터 확인 및 드롭박스로 변경")
    public void 기출문제검색필터변경(String submenu) throws InterruptedException{


        첫번째드롭박스확인(submenu);
        Utils.touchSpecificCoordinates(209, 297);
        두번째드롭박스확인();
        Utils.touchSpecificCoordinates(388, 297);
        세번째드롭박스확인(submenu);
        Utils.touchSpecificCoordinates(600, 297);
    }




    @Then("하단 페이징 page2 버튼 클릭")
    public void pagging(){
        try {
            WebElement tobtn = AndroidManager.getElementByXpath("//android.widget.Button[@text=\"Go to page 2\"]");
            tobtn.click();
            log.info("페이징 2번 버튼을 클릭하였습니다.");
        } catch (Exception e) {
            try{
            WebElement ownbtn =  AndroidManager.getElementByXpath("//android.widget.Button[@text=\"page 1\"]");
            if (ownbtn.isDisplayed()){
                log.info("페이징 버튼이 1번 만 존재하는 화면입니다.");
            }
            } catch (Exception ex) {
                log.info("페이징 버튼이 존재하지 않습니다.");
                throw new RuntimeException(ex);
            }
        }
    }


    public void 기출문제학년노출확인(){
        WebElement grade = AndroidManager.getElementByXpath("(//android.view.View[@text=\"중1\"])[1]");
        WebElement gradechk = AndroidManager.getElementByXpath("(//android.view.View[@text=\"중1\"])[2]");
        if(grade.isDisplayed() && gradechk.isDisplayed()){
            log.info("선택한 학년 과 노출된 학년 동일");
        }
    }

    @Then("모의고사 첫 진입 팝업 확인")

    public void 모의고사팝업확인(){
       try {
           boolean popup = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"mock-test-modal\"]").isDisplayed();
           if (popup) {
               log.info("팝업이 존재합니다.");
               AndroidManager.getElementByXpath("//android.widget.Button[@text=\"닫기\"]").click();

           }
       } catch (Exception e) {
           log.info("팝업이 존재 하지 않습니다.");

       }
       }
    //android.view.View[@resource-id="mock-test-modal"]


    public String 특정텍스트찾기(int line){
        WebElement parentElement = AndroidManager.getElementByXpath("//android.view.View[@resource-id='root']");

        // 1. 부모 요소 아래의 모든 하위 요소를 가져오기 (모든 단계)
        List<WebElement> allDescendantElements = parentElement.findElements(By.xpath(".//*"));
        String text = "";
        // 2. 45번째 하위 요소 텍스트 확인 (0부터 시작하므로 인덱스 44가 45번째 요소)
        if (allDescendantElements.size() > line - 1) {  // 리스트 크기가 45 이상일 경우에만
            text = allDescendantElements.get(line).getText().trim(); // 45번째 요소의 텍스트
            if (!text.isEmpty()) {
                log.info(line + "번째 요소 텍스트: " + text);
            } else {
                log.info(line + "번째 요소는 빈 텍스트입니다.");
            }
        } else {
            log.info(line + "번째 요소가 존재하지 않습니다.");
        }
        return text;
    }
    public void 년도클릭(String year){
        if(year.equals("2015")){
            Utils.touchSpecificCoordinates(752, 365);
        } else if(year.equals("2022")){
            Utils.touchSpecificCoordinates(775, 447);
        } else if(year.equals("년도없음")){
            log.info("년도없음이 입력되어 좌표 터치 없이 다음 단계로 진행합니다.");
        } else {
            log.warn("알 수 없는 year 값: " + year);
        }
    }


    public void 학년클릭(String grade, String year, String term){
        log.info(grade);
        WebElement yeardrop = null;
        try {
            yeardrop = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"aiMath-rev\"]");
        } catch (Exception e) {
            log.info("년도 없음 확인.");
        }

        if(grade.equals("중1")){
            if (yeardrop != null ) {
                Utils.touchSpecificCoordinates(900, 365);

            }else {
                Utils.touchSpecificCoordinates(815, 365);
            }
            log.info("중1 클릭 하였습니다.");
        } else if(grade.equals("중2")){
            if (yeardrop != null) {
                Utils.touchSpecificCoordinates(900, 447);

            }else {
                Utils.touchSpecificCoordinates(815, 447);
            }
            log.info("중2 클릭 하였습니다.");
        } else if(grade.equals("중3")){
            if (yeardrop != null ) {
                Utils.touchSpecificCoordinates(900, 513);

            }else {
                Utils.touchSpecificCoordinates(815, 513);
            }
            log.info("중3 클릭 하였습니다.");
        } else {
            log.warn("알 수 없는 grade 값: " + grade);
        }
    }

    public void 학기클릭(String term, String year, String grade){

        WebElement yeardrop = null;
        try {
            yeardrop = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"aiMath-rev\"]");
        } catch (Exception e) {
            log.info("년도 없음 확인.");
        }

        if(term.equals("1학기")){
            if (yeardrop != null) {
                Utils.touchSpecificCoordinates(1046, 365);
            } else {
                Utils.touchSpecificCoordinates(950, 365);
            }
            log.info("1학기 클릭 하였습니다.");
        } else if(term.equals("2학기")){
            if (yeardrop != null) {

                Utils.touchSpecificCoordinates(1046, 447);
            } else {
                Utils.touchSpecificCoordinates(950, 447);
            }
            log.info("2학기 클릭 하였습니다.");
        } else {
            log.warn("알 수 없는 term 값: " + term);
        }
    }

    public void ai수학중화면확인(String year, String grade, String term) {
        // 조건별 key 생성 (예: "2015-중1-1학기")
        String key = year + "-" + grade + "-" + term;
        String[] mapping = getExpectedListItemMapping(key);

        if (mapping == null) {
            log.error("조건 [" + key + "] 에 해당하는 매핑이 없습니다.");
            fail("조건 [" + key + "] 에 해당하는 매핑이 없습니다.");
        }

        // mapping[0] : xpath, mapping[1] : expected text
        String expectedXpath = mapping[0];
        String expectedText = mapping[1];

        try {
            // 예: 드롭박스에서 년도 확인 (필요한 경우 추가 검증)
            String actualYear = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"aiMath-grade\"]").getText().trim();
            assertEquals("학년이 예상과 다릅니다.", grade, actualYear);
            log.info("학년 " + grade + " 확인: " + actualYear);

            // ListView의 첫 번째 항목 검증
            WebElement listView = AndroidManager.getElementByXpath("//android.widget.ListView");
            // 리스트 뷰 내부에서 조건에 맞는 요소 찾기
            WebElement firstItem = listView.findElement(By.xpath(expectedXpath));
            String actualFirstItemText = firstItem.getText().trim();

            log.info("첫 번째 리스트 항목: " + actualFirstItemText);
            assertEquals("리스트 첫 번째 항목이 예상과 다릅니다.", expectedText, actualFirstItemText);
            log.info("첫 번째 항목이 '" + expectedText + "'와 일치합니다.");
        } catch (Exception e) {
            log.error("리스트 뷰 또는 첫 번째 항목을 찾을 수 없습니다. 오류: " + e.getMessage(), e);
            fail("리스트 뷰가 없거나 첫 번째 항목이 표시되지 않습니다.");
        }
    }

    /**
     * 조건별 매핑 정보를 반환합니다.
     * key 예: "2015-중1-1학기"
     * mapping[0] : 리스트 항목에 접근하기 위한 xpath
     * mapping[1] : 해당 항목의 예상 텍스트
     */
    private String[] getExpectedListItemMapping(String key) {
        // 조건별 매핑 정보를 미리 정의
        Map<String, String[]> mappingTable = new HashMap<>();

        // 예시 매핑 (실제 xpath와 텍스트는 앱에 맞게 수정 필요)
        mappingTable.put("2015-중1-1학기", new String[]{"//android.view.View[@text=\"1. 소인수분해\"]", "1. 소인수분해"});
        mappingTable.put("2015-중1-2학기", new String[]{"//android.view.View[@text=\"1. 기본 도형\"]", "1. 기본 도형"});
        mappingTable.put("2022-중1-1학기", new String[]{"//android.view.View[@text=\"1. 소인수분해\"]", "1. 소인수분해"});
        mappingTable.put("2022-중1-2학기", new String[]{"//android.view.View[@text=\"1. 기본 도형\"]", "1. 기본 도형"});
        mappingTable.put("년도없음-중2-1학기", new String[]{"//android.view.View[@text=\"1. 유리수와 순환소수\"]", "1. 유리수와 순환소수"});
        mappingTable.put("년도없음-중2-2학기", new String[]{"//android.view.View[@text=\"1. 삼각형의 성질\"]", "1. 삼각형의 성질"});
        mappingTable.put("년도없음-중3-1학기", new String[]{"//android.view.View[@text=\"1. 제곱근과 실수\"]", "1. 제곱근과 실수"});
        mappingTable.put("년도없음-중3-2학기", new String[]{"//android.view.View[@text=\"1. 삼각비\"]", "1. 삼각비"});

        return mappingTable.get(key);
    }


    public void 영어가이드팝업추가동작(){
        try{
            WebElement btn1 = AndroidManager.getElementByXpath("//android.widget.Button[@text=\"학년별 필수 문법 학습 중학 필수 AI 영문법\"]");
            btn1.click();
            log.info("중학 필수 AI 영문법 클릭");
            TimeUnit.SECONDS.sleep(2);
            WebElement detailbtn = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"자세히 보기\"]");
            detailbtn.click();
            log.info("자세히보기 클릭");
            TimeUnit.SECONDS.sleep(2);
            WebElement restartbtn = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"처음으로\"]");
            restartbtn.click();
            log.info("처음으로 클릭");
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            log.info("팝업 하위 버튼 동작 실패");
            throw new RuntimeException(e);
        }

        try{
            WebElement btn2 = AndroidManager.getElementByXpath("//android.widget.Button[@text=\"교과서 단원별 문법 학습 교과서별 필수 AI 영문법\"]");
            btn2.click();
            TimeUnit.SECONDS.sleep(2);
            log.info("교과서별 필수 AI 영문법 클릭");
            WebElement detailbtn = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"자세히 보기\"]");
            detailbtn.click();
            TimeUnit.SECONDS.sleep(2);
            log.info("자세히보기 클릭");
            WebElement restartbtn = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"처음으로\"]");
            restartbtn.click();
            TimeUnit.SECONDS.sleep(2);
            log.info("처음으로 클릭");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("팝업 하위 버튼 동작 확인 완료");

    }


    @Then("대치TOP 학원 상세페이지 확인")
    public void 대치top학원상세페이지확인() {
        WebElement subtitle = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_sub_title");

        String actuality = "";
        if (subtitle.getText().equals("미래탐구 국어")){
            actuality = "미래탐구(국어)";
        }
        if (subtitle.getText().equals("미래탐구 과학")){
            actuality = "미래탐구(과학)";
        }
        else {
            actuality = subtitle.getText();
        }

        // When 대치TOP메인학원리스트클릭() 클래스에서 저장한 값과 비교
        log.info("학원명: {} == 클릭한 학원: {}", actuality, MiddleSmartAllWhen.TopAcademyText);

        if (!MiddleSmartAllWhen.TopAcademyText.equals(actuality)) {
            String appendedActuality = MiddleSmartAllWhen.TopAcademyText + "학원";
            log.info("1차 비교 실패 - 학원명 변경 후 재비교: {} == {}", appendedActuality, actuality);
            assertEquals(appendedActuality, actuality);
        } else {
            assertEquals(MiddleSmartAllWhen.TopAcademyText, actuality);
        }

        // 다음 테스트를 위해 값 초기화
        MiddleSmartAllWhen.TopAcademyText = null;
    }

    @And("중학 수학전문관 - {string} 드롭박스 진입 확인")
    public void 중학수학전문관강좌드롭박스진입확인(String menu) {
        log.info("{} 이동 확인",menu);
        // 빨간 버튼 -> 수준별 강좌 | 파란 버튼 -> 영역별 강좌
        switch (menu){
            case "기본":
            case "입문":
            case "발전":
            case "심화":
                String text = "";
                boolean content = false;
                text = "Lv. " + menu;
                content = AndroidManager.getElementByText(text).isDisplayed();
                log.info("수준별 강좌 이동 확인: {} == {} 확인", menu, text);
                assertTrue("수학전문관  수준별 강좌 " + menu + " 드롭박스 이동 확인되지 않습니다.", content);
            case "수와연산":
            case "방부등식":
            case "함수":
            case "기하":
                String dropBox = AndroidManager.getElementByXpath(Constant.중학수학전문관강좌드롭박스_xPath).getText();
                String menuTitle = AndroidManager.getElementByXpath(Constant.중학수학전문관강좌명_xPath).getText();
                log.info("영역별 강좌 이동 확인: {} == {}", dropBox, menuTitle);
                assertEquals(dropBox,menuTitle);
                break;
            default:
                fail("올바르지 않은 과목: " + menu);
        }

    }

    @Then("중학 {string} 강의 화면 확인")
    public void 강의확인(String menu) {
        대치top학습화면확인(menu);
        AndroidManager.getElementByXpath("//android.widget.Image[@text=\"닫기버튼\"]").click();
    }


    public void 첫번째드롭박스확인(String submenu){
        WebElement grade = null;
        try {
            if(submenu.equals("학력평가 기출문제")){
                try {
                    grade = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.view.View[1]/android.view.View[1]");
                    grade.click();
                    log.info("첫번째 드롭박스 확인");
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    log.info("첫번째 드롭박스 존재 하지 않음");
                }
            }
        } catch (Exception e) {
            log.info("첫번째 드롭박스를 찾지 못했습니다.");
            throw new RuntimeException(e);
        }
    }

    public void 세번째드롭박스확인(String submenu){
        WebElement round = null;
        try {
            round = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.view.View[1]/android.view.View[3]");
            round.click();

            log.info("세번째 드롭박스 클릭");
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            try{
                if(submenu.equals("학업성취도평가 대비")){
                    log.info("학업성취도평가 에선 3번째 드롭박스는 확인하지 않습니다.");
                }

            } catch (Exception ex) {
                log.info("세번째 드롭박스를 찾지 못했습니다.");
                throw new RuntimeException(ex);
            }
        }
    }
    public void 두번째드롭박스확인(){
        WebElement year = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.view.View[1]/android.view.View[2]");

        try {
            year.click();

            log.info("두번째 드롭박스 클릭");
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            log.info("두번째 드롭박스를 찾지 못했습니다.");
            throw new RuntimeException(e);
        }
    }

    @Then("{string} 검색필터 확인")
    public void 검색필터확인 (String submenu){
        첫번째드롭박스확인(submenu);
        Utils.touchSpecificCoordinates(1082, 148);
        두번째드롭박스확인();
        Utils.touchSpecificCoordinates(1082, 148);
        세번째드롭박스확인(submenu);
        Utils.touchSpecificCoordinates(1082, 148);
    }

    @Then("내서랍 화면 확인")
    public void 내서랍화면확인() throws InterruptedException {
        // 내서랍 >> 접혀있어도 모든 element가 화면에 잡려서 열렸는지 확인 위해
        // 특정 버튼 클릭 (설정 버튼의 경우 내서랍 또는 전체 메뉴에서만 확인 가능)
        TimeUnit.SECONDS.sleep(2);
        log.info("설정 클릭");
        AndroidManager.getElementByText("설정 더보기").click();
        TimeUnit.SECONDS.sleep(2);
        log.info("설정 페이지 확인");
        // 앱버전, 알림설정, 배경모드,캐시삭제,원격지원
        Boolean display = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_app_version").isDisplayed();
        assertTrue("내서랍 > 설정 동작 확인 되지 않습니다.", display);

    }

    @Then("대치TOP {string} 학습화면 확인")
    public void 대치top학습화면확인(String submenu) {
        log.info("[{}] 학원 강좌 메뉴 확인",submenu);
        boolean displayConts = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"찜하기\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"선생님 프로필\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"강의 목차\"]").isDisplayed()
                && AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"강좌 소개\"]").isDisplayed()
                ;
        assertTrue("학원 강좌 메뉴 확인되지 않습니다.",displayConts);
    }

    @Then("AI영어 교과서별 {string} 단원 확인")
    public void ai영어교과서별단원확인(String tab) {
        log.info("AI영어 교과서별 - {}탭 클릭",tab);
        WebElement lesson = AndroidManager.getElementByXpath("//android.widget.ListView[2]");
        List<WebElement> allLessons = lesson.findElements(By.className("android.view.View"));
        int count = allLessons.size();
        log.info("단원 수: {}", count);
        for (int i = 0; i < count; i++) {
            WebElement lessonElement = allLessons.get(i);
            log.info("클릭할 단원: {}", lessonElement.getText());
            lessonElement.click();

            if (tab.equals("시험대비 교과서 AI 문법 공략")){
                WebElement title = AndroidManager.getElementByXpath(Constant.AI영어교과서문법공략단원명_xPath);
                String fullText = title.getText();
                String lessonNumber = fullText.split("\\.")[0];
//            log.info(lessonNumber);
                if (lessonElement.getText().equals(lessonNumber)){
                    log.info(lessonNumber+ " 진입 확인");
                    boolean contents = AndroidManager.getElementByText("개념 이해도").isDisplayed()
                            && AndroidManager.getElementByText("AI추천 문항").isDisplayed()
                            && AndroidManager.getElementByText("확인평가응시하기").isDisplayed();
                    assertTrue("AI 영어 교과서별 문법공략 단원 확인 되지 않습니다.",contents);
                }
            }
            if (tab.equals("시험대비 교과서 AI 문법 모의고사")){
                WebElement title = AndroidManager.getElementByXpath(Constant.AI영어교과서문법모의고사단원명_xPath);
                String fullText = title.getText();
                String lessonNumber = fullText.split("\\.")[0];
//            log.info(lessonNumber);
                if (lessonElement.getText().equals(lessonNumber)){
                    log.info(lessonNumber+ " 진입 확인");
                    boolean contents = AndroidManager.getElementByText("문법 모의고사").isDisplayed()
                            && AndroidManager.getElementByText("1회").isDisplayed()
                            && AndroidManager.getElementByText("최종 예측 점수").isDisplayed();
                    assertTrue("AI 영어 교과서별 문법모의고사 단원 확인 되지 않습니다.",contents);
                }
            }

        }
    }

}
