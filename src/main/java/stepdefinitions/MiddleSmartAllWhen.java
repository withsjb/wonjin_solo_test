package stepdefinitions;

import io.cucumber.java.en.And;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * 스마트올중학 When
 */
public class MiddleSmartAllWhen {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 스마트올중학 GNB 영역 각 메뉴 선택
     */
    @When("상단 메뉴바 {string} 이동")
    public void clickGNB_middle(String menu) throws InterruptedException {
        int idx=0;
        switch (menu) {
            case "홈":
                idx=0;
                break;
            case "SMART내신":
                idx=1;
                break;
            case "대치TOP":
                idx=2;
                break;
            case "AI학습관":
                idx=3;
                break;
            case "영어전문관":
                idx=4;
                break;
            case "수학전문관":
                idx=5;
                break;
            case "TEST라운지":
                idx=6;
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 메뉴: " + menu);
        }
        TimeUnit.SECONDS.sleep(15);
        log.info("{} 메뉴 이동",menu);
        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/lay_study_view_default")
                .findElements(By.className("android.widget.LinearLayout")).get(idx).click();
        TimeUnit.SECONDS.sleep(5);
    }

    @When("중학 상단 검색 버튼 클릭")
    public void 중학상단검색버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/iv_search").click();
    }

    @And("검색 수학 문항 검색 클릭")
    public void 검색수학문항검색클릭() {
        AndroidManager.getElementByText("수학 문항 검색").click();
    }

    // 검색어 저장을 위한 정적 변수
    public static String popularWord;

    @And("인기 검색어 첫번째 클릭")
    public void 인기검색어번째클릭() {
        WebElement word = AndroidManager.getElementByXpath(Constant.중학첫번째인기검색어_xPath);
        popularWord = word.getText().trim(); // 공백 제거 후 저장

        log.info("인기 검색어 클릭: {}", popularWord);
        word.click();
    }

    // 검색어 저장을 위한 정적 변수 추가
    public static String enteredSearchText;
    @And("중학 검색창에 {string} 입력")
    public void 중학검색창에입력(String word) {
        try {
            log.info("중학 검색창에 {} 입력", word);

            WebDriverWait wait = new WebDriverWait(AndroidManager.getDriver(), Duration.ofSeconds(30));
            TimeUnit.SECONDS.sleep(5);
            WebElement inputText = // 정규식으로 패키지 이름 무시하고 ID만 매칭
                    wait.until(ExpectedConditions.elementToBeClickable(
                            By.className("android.widget.EditText")
                    ));
            inputText.sendKeys(word);
            enteredSearchText = word; // 입력값 저장
            TimeUnit.SECONDS.sleep(3);
        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    @And("중학 검색창 검색 버튼 클릭")
    public void 중학검색창검색버튼클릭() {
        // 검색버튼 특정 id 값 없어서 해당 좌표값으로 클릭
        Utils.touchSpecificCoordinates(1317,60);
    }

    @When("상단 햄버거 버튼 클릭")
    public void 상단햄버거버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/iv_menu").click();
    }

    @And("중학 [전체메뉴] 검색 버튼 클릭")
    public void 중학전체메뉴검색버튼클릭() {
        AndroidManager.getElementByText("search").click();
    }

    @And("시간표 가이드 클릭")
    public void 시간표가이드클릭() {
        AndroidManager.getElementByText("guide").click();
    }

    @And("시간표 가이드 - {string} 클릭")
    public void 시간표가이드클릭(String guide) {
        int i = 0;
        if (guide.equals("강좌 추가")) i=1;
        if (guide.equals("스케줄 변경")) i=2;
        if (guide.equals("스케줄 삭제")) i=3;
        if (guide.equals("시간표 초기화")) i=4;

        // ListView 내 2번째 View의 Image 클릭 (인덱스 사용)
        String xpath = "//android.widget.ListView/android.view.View["+i+"]/android.view.View/android.widget.Image";

        try {
            AndroidManager.getElementByXpath(xpath).click();
            log.info("{} 클릭",guide);

        } catch (Exception e) {
            log.error("요소 클릭 실패: {}", e.getMessage());
            throw e;
        }

    }

    // 오늘 계획학습 - 나의 계획학습 학습카드 count 저장을 위한 정적 변수
    public static int studyCount ;
    @And("중학 필수 어휘 학습 카드 바로가기 클릭")
    public void 중학필수어휘학습카드바로가기클릭() {
        // 학습 카드 수가 5개 이상이면 오른쪽에서 왼쪽으로 밀어서 확인
        String baseXPath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View/android.widget.ListView/android.view.View";
        int count = 0;
        try {
            List<WebElement> topLevelViews = AndroidManager.getDriver().findElements(By.xpath(baseXPath));
            // ListView 내 최상위 View 요소 개수
            studyCount = topLevelViews.size();
            log.info("학습카드 노출 개수: {}", studyCount);
        } catch (Exception e) {
            log.error("요소 카운트 중 오류 발생: {}", e.getMessage());
        }
        // 카드수가 4개 라면 4번째 확인
        try {
            if(studyCount >= 5) {
                log.info("5개 이상 카드 존재 → 스크롤 수행");
                // 가로 스크롤 수행 (오른쪽 → 왼쪽)
                Utils.dragSourceToTarget(1843,430,100,430);
            }
            // 필수어휘 카드 직접 클릭
            AndroidManager.getElementByText("바로가기").click();
        } catch (Exception e) {
            log.error("카드 클릭 실패: {}", e.getMessage());
            throw new AssertionError("카드 선택 실패", e);
        }

    }

    @And("중학 전체메뉴 나가기")
    @And("시간표 설정 가이드 나가기")
    public void 시간표설정가이드나가기() {
        AndroidManager.getElementByText("닫기").click();
    }

    @And("중학 전체메뉴 - {string} 클릭")
    public void 중학전체메뉴클릭(String menu) {
        try {
            if (menu.equals("나의 강좌 Q&A")){
                // 좌표클릭
                Utils.touchSpecificCoordinates(970,915);
                return;
            }
            TimeUnit.SECONDS.sleep(2);
            log.info("전체메뉴 - {} 클릭",menu);
            WebElement element = AndroidManager.getElementByTextAfterSwipe(menu);
            TimeUnit.SECONDS.sleep(2);
            element.click();
            TimeUnit.SECONDS.sleep(5);


        } catch (NoSuchElementException e) {
            fail("Element you found not shown");
        } catch (Exception e) {
            fail(e.getMessage());
            System.exit(0);
        }
    }

    /*
    * 왼쪽 상단 내서랍 클릭
    */
    @And("내서랍 클릭")
    @And("내서랍 닫기")
    public void 전체메뉴내서랍버튼클릭() {
        AndroidManager.getElementByText("서랍").click();
    }

    @And("중등 스마트올 홈으로")
    public void 중등스마트올홈으로() {
        try {
            AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/iv_home").click();
        }catch (Exception e){
            log.info("홈 버튼 없는 메뉴 화면 입니다.");
        }
    }

    @And("전체메뉴 {string} 화면 나가기")
    public void 전체메뉴화면나가기(String submenu) throws InterruptedException {
        if (submenu.equals("스마트올백과")){
            log.info("스마트올백과 나가기");
            중등스마트올나가기();
        }
        else {
            log.info("전체메뉴 {} 화면 나가기", submenu);
            중등스마트올홈으로();
        }
    }

    public void 중등스마트올나가기() throws InterruptedException {
        log.info("스마트올백과 나가기");
        TimeUnit.SECONDS.sleep(2);
        Utils.touchSpecificCoordinates(60,50);
        TimeUnit.SECONDS.sleep(2);
        AndroidManager.getElementByText("닫기").click();
    }


    @When("상단 마이패이지 버튼 클릭")
    public void 상단마이패이지버튼클릭() {
        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/iv_user").click();
    }

    @And("개인정보수정 클릭")
    public void 개인정보수정클릭() {
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"개인정보 수정\"]").click();
    }

    /**
     * 내 서랍 메뉴 클릭 동작을 처리하는 메소드
     * @param menu 클릭할 메뉴 이름 (예: "알림함", "바로가기 메뉴", "이번주 계획학습 현황" 등)
     */
    @And("내서랍 {string} 클릭")
    public void 내서랍클릭(String menu) {
        try {
            TimeUnit.SECONDS.sleep(1);
            // 2. 스크롤 필요 메뉴 처리
            if (menu.equals("이번주 계획학습 현황") || menu.equals("나의 강좌 Q&A") || menu.equals("최근 학습하지 않은 강의")
                    || menu.equals("오늘의 계획학습 추가") || menu.equals("오답노트 바로 확인") || menu.equals("편집"))
            {
                log.info("드래그 후 클릭 필요한 메뉴: {}", menu);

                // 화면 스크롤 동작 (시작 Y 좌표 > 종료 Y 좌표: 아래->위 스크롤)
                // 화면 해상도 1440x2960 기준 좌표 (실제 기기 해상도에 맞게 조정 필요)
                Utils.dragSourceToTarget(
                        300,  // 시작 X 좌표 (화면 왼쪽에서 300px)
                        1140, // 시작 Y 좌표 (화면 하단에서 1140px)
                        300,  // 종료 X 좌표 (동일 X축 유지)
                        150   // 종료 Y 좌표 (화면 상단으로 150px 이동)
                );

                // 스크롤 후 메뉴 클릭
                AndroidManager.getElementByText(menu).click();
                return;
            }
            if (menu.equals("알림함")){
                log.info("직접 클릭 가능한 메뉴: 알림함");
                AndroidManager.getElementByText("알림함 (0)").click();
                return;
            }

            // 1. 즉시 클릭 가능한 메뉴 처리
            log.info("직접 클릭 가능한 메뉴: {}", menu);
            AndroidManager.getElementByText(menu).click(); // 실제 메뉴 텍스트로 클릭

        } catch (NoSuchElementException e) {
            log.error("'{}' 메뉴 요소를 찾을 수 없습니다", menu);
            fail(menu + " 메뉴 요소 탐색 실패: " + e.getMessage());
        } catch (Exception e) {
            log.error("메뉴 클릭 중 예외 발생: {}", e.getMessage());
            fail("메뉴 클릭 실패: " + e.getMessage());
        }
    }

    /*
     * 중학 홈에서 택스트로 된 버튼 클릭
     * 중학의 경우 id/xPath 없이 거의 text깂으로 되어 있음
     */
    @When("중학 홈 {string} 클릭")
    @And("나의 계획학습 {string} 버튼 클릭")
    public void 중학홈나의계획학습클릭(String text) {
        AndroidManager.getElementByText(text).click();
    }

    @And("과목별 보기 화면 뒤로가기")
    public void 과목별보기화면뒤로가기() {
        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/iv_back").click();
    }


    @And("홈 - 대치TOP {string} 클릭")
    public void 홈대치TOP클릭(String submenu) throws InterruptedException {
        log.info("아래로 스크롤 하기");
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);
        Utils.swipeScreen(Utils.Direction.UP);
        TimeUnit.SECONDS.sleep(1);

        int index = 0;
        switch (submenu) {
            case "C&A논술학원":
                index = 1;
                break;
            case "세계로학원":
                index = 2;
                break;
            case "에이프로아카데미":
                index = 3;
                break;
            case "함영원영어학원":
                index = 4;
                break;
            case "함수학학원":
                index = 5;
                break;
            case "미래탐구(과학)":
                index = 6;
                break;
            case "미래탐구(국어)":
                index = 7;
                break;
            case "ILE":
                index = 8;
                break;
            case "깊은생각":
                index = 9;
                break;
            default:
                throw new IllegalArgumentException("Invalid menu: " + submenu);
        }

        TimeUnit.SECONDS.sleep(2);

        int swipeCount = getSwipeCount(index);

        for (int i = 0; i < swipeCount; i++) {
            log.info("화면 오른쪽에서 왼쪽으로 스와이프");
            Utils.dragSourceToTarget(1880,910,100,910);
            TimeUnit.SECONDS.sleep(1);
        }

        log.info("홈 - 대치TOP 배너 {} 클릭",submenu);
        String xpath = "(//android.view.View[@content-desc=\"대치탑배너\"])["+index+"]";
        AndroidManager.getElementByXpath(xpath).click();

    }
    private int getSwipeCount(int index) {
        if (index >= 7) return 2;
        if (index >= 4) return 1;
        return 0;
    }

    /* 강의목록 클릭 후 나오는 학습화면 (강의목차)에서 특정 버튼 클릭*/
    public void 특정버튼클릭(String text) {
        // 주어진 텍스트를 기반으로 XPath 동적 생성
        String xpath = String.format("//android.widget.TextView[@text='%s']", text);

        try {
            // 요소 찾기
            WebElement button = AndroidManager.getElementByXpath(xpath);
            button.click();
            log.info(text + " 버튼을 클릭했습니다.");

        } catch (java.util.NoSuchElementException e) {
            log.info(text + " 버튼을 찾을 수 없습니다.");
        }
    }


    @And("강의 학습화면 닫기버튼 클릭")
    public void 강의학습화면닫기버튼클릭() {
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"닫기버튼\"]").click();
    }

    //----------

    @When("AI 수학 클릭")
    public void AImathclick(){
        WebElement mathbtn = AndroidManager.getElementByXpath("//android.widget.Button[@text=\"AI수학 바로가기\"]");
        log.info("ai 수학 클릭");
        try{
            mathbtn.click();
        }catch (Exception e){
            e.getMessage();
        }

    }

    @When("AI 영어 클릭")
    public void AIenglisclick(){
        WebElement englishbtn = AndroidManager.getElementByXpath("//android.widget.Button[@text=\"AI영어 바로가기\"]");
        log.info("ai 영어 클릭");
        try{
            englishbtn.click();
        }catch (Exception e){
            e.getMessage();
        }

    }


    @When("Ai 수학 {string} 탭 클릭")
    public void AImathtabclick(String tab)throws InterruptedException{

        switch (tab) {
            case "AI 리포트":
                AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"AI 리포트\"]").click();
                log.info("Ai 리포트 클릭");
                break;
            case "단원별 AI 추천 문항":
                AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"단원별 AI 추천 문항\"]").click();
                log.info("단원별 AI 추천 문항");
                break;
            case "AI 추천 커리큘럼":
                AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"AI 추천 커리큘럼\"]").click();
                log.info("AI 추천 커리큘럼");
                break;
            case "시험대비 AI 집중공략":
                AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"시험대비 AI 집중공략\"]").click();
                log.info("시험대비 AI 집중공략");
                break;
            case "AI 시험대비 모의고사":
                AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"AI 시험대비 모의고사\"]").click();
                log.info("AI 시험대비 모의고사");
                break;

            default:
                throw new IllegalArgumentException("올바르지 않은 메뉴: " + tab);
        }
        log.info("Ai 수학 {} 탭 클릭",tab);

        TimeUnit.SECONDS.sleep(5);

    }



    @When("가이드? 버튼 클릭")
    public void 가이드버튼클릭(){

        try {

            WebElement guidebtn = null;
            try {
                guidebtn =
                        AndroidManager.getElementByXpath("//android.widget.Button[@text=\"가이드 가이드 이미지\"]");
            } catch (Exception e) {
                guidebtn = AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.widget.Button");
            }
            guidebtn.click();
        } catch (Exception e) {
            log.info("가이드 버튼을 찾을 수 없습니다.");
            throw new RuntimeException(e);
        }

    }

    @When("진단평가 응시하기 확인 및 클릭")
    public void 진단평가버튼클릭() throws InterruptedException {
        try {
            // 진단평가 응시하기 버튼 클릭
            WebElement jindanbtn =  AndroidManager.getElementByXpath("//android.widget.Button[@text=\"진단평가 응시하기\"]");
            if(jindanbtn.isDisplayed()){
                log.info("진단평가 응시하기 버튼 화면 노출 확인");
            }else{
                log.info("화면에 노출되지 않음");
            }

            jindanbtn.click();
            log.info("진단평가 응시하기 선택");

            // 팝업이 있는지 확인
            try {
                WebElement popup = AndroidManager.getElementByXpath("//android.widget.TextView[@text=\"진단평가 응시 안내\"]");
                if (popup.isDisplayed()) {
                    AndroidManager.getElementByXpath("//android.widget.Button[@text=\"응시하기\"]").click();
                    log.info("응시하기 선택");
                }
            } catch (Exception e) {
                // 팝업이 없는 경우 그냥 진행
                log.info("진단평가 응시 안내 팝업이 없습니다.");
            }
        } catch (Exception e) {
            log.error("진단평가 응시 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("진단평가 응시 실패", e);
        }
        TimeUnit.SECONDS.sleep(3);
    }



    @When("상단 서브 메뉴바 {string} 클릭")
    public void 서브메뉴바확인(String submenuname) throws InterruptedException{
        int idx=0;
        switch (submenuname) {
            case "전체 강좌":
                idx=0;
                break;
            case "내신진도":
                idx=1;
                break;
            case "방학특강":
                idx=2;
                break;
            case "시험특강":
                idx=3;
                break;
            case "예비중":
                idx=4;
                break;
            case "예비고":
                idx=5;
                break;

            default:
                throw new IllegalArgumentException("올바르지 않은 메뉴: " + submenuname);
        }

        WebElement submenubar = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_sub_title");
        submenubar.click();

        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/lay_study_list")
                .findElements(By.className("android.widget.TextView")).get(idx).click();
        TimeUnit.SECONDS.sleep(5);
    }

    @When("강좌없는 학년 이동")
    public void 예비중국어이동() {
        try {
            AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"gradCd\"]").click();
            TimeUnit.SECONDS.sleep(2);
            Utils.touchSpecificCoordinates(1194, 323);
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"courseCode\"]").click();
            TimeUnit.SECONDS.sleep(2);
            Utils.touchSpecificCoordinates(1360, 103);
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            log.info("특정 드롭다운 선택에 실패하였습니다.");
            throw new RuntimeException(e);
        }
    }



    @When("하단 첫번째 강의 클릭")
    public void 첫번째강의클릭() {
        try{
            TimeUnit.SECONDS.sleep(3);

            WebElement fristbook = AndroidManager.getElementByXpath("(//android.widget.TextView[@text=\"그라데이션\"])[1]");
            log.info(fristbook.getText());

            fristbook.click();
            log.info("강의 클릭");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("학원 표지 순서 강좌 순서 일치 확인")
    public void 학원순서일치확인() {
        try{
            List<WebElement> listViews = AndroidManager.getDriver().findElements(By.xpath("//android.widget.ListView/android.view.View"));
            List<String> Academylist = new ArrayList<>();
            int referenceY = 0;


            for (int i = 0; i < listViews.size(); i++) {
                // 각 android.view.View 내부의 첫 번째 android.widget.TextView 텍스트 가져오기
                try {
                    WebElement textView = listViews.get(i).findElement(By.xpath(".//android.widget.TextView[1]"));
                    String text = textView.getText();
                    log.info("View " + i + "의 첫 번째 텍스트: " + text);
                    Academylist.add(text);


                } catch (Exception e) {
                    log.info("View " + i + "에 TextView가 없습니다.");
                }
                System.out.println(Academylist);
            }



            for(int i = 0; i < Academylist.size(); i++){
                String text = Academylist.get(i);
                AndroidManager.findXpathAndSwipeToPosition(text, 150);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @And("중등 닫기 버튼 클릭")
    public void 강의화면나가기버튼() {
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\"닫기버튼\"]").click();
    }

    @And("중학 상단 드롭바 {string} 클릭")
    public void 중학상단드롭바클릭(String menu) throws InterruptedException {
        WebElement menuTile = AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_group_title");
        menuTile.click();
        int i = 0;
        switch (menu){
            case "SMART내신":
                i=1;
                break;
            case "대치TOP":
                i=2;
                break;
            case "AI학습관":
                i=3;
                break;
            case "영어전문관":
                i=4;
                break;
            case "수학전문관":
                i=5;
                break;
            case "TEST라운지":
                i=6;
                break;
        }

        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/lay_study_list")
                .findElements(By.className("android.widget.TextView")).get(i).click();

        TimeUnit.SECONDS.sleep(10);
    }

    // 학원명 저장을 위한 정적 변수 추가
    public static String TopAcademyText;
    @When("대치TOP {int}번째 학원 클릭")
    public void 대치TOP메인학원리스트클릭(int num) throws InterruptedException {
        log.info("대치TOP 메인 학원 {}번째 학원 클릭",num);
        WebElement unit = AndroidManager.getElementByXpath("//android.view.View[2]/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View/android.widget.ListView");
        TopAcademyText = unit.findElements(By.className("android.view.View")).get(num).findElement(By.xpath("//android.widget.TextView[1]")).getText();

        log.info(TopAcademyText+"클릭");
        unit.findElements(By.className("android.view.View")).get(num).click();
        TimeUnit.SECONDS.sleep(10);
    }


    @When("하단 과목 버튼 {string} 클릭")
    public void 하단과목버튼클릭(String subject) throws InterruptedException{
        int idx=0;
        switch (subject) {
            case "전체":
                idx=0;
                break;
            case "국어·논술":
                idx=1;
                break;
            case "영어":
                idx=2;
                break;
            case "수학":
                idx=3;
                break;
            case "과학":
                idx=4;
                break;
            case "역사":
                idx=5;
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 메뉴: " + subject);
        }

        AndroidManager.getElementByXpath("//android.view.View[@resource-id=\"root\"]/android.view.View[2]/android.widget.ListView")
                .findElements(By.className("android.view.View")).get(idx).click();
        TimeUnit.SECONDS.sleep(5);
    }


    @When("영어전문관 화면 확인")
    public void 영어전문관화면확인(){
        try {
            WebElement munbob = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_GRAMMAR\"]"); //문법
            log.info("문법 확인");
            WebElement dockhae = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_READ\"]"); //독해
            log.info("독해 확인");
            WebElement listen = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_LISTEN\"]"); //듣기
            log.info("리슨 확인");
            WebElement voca = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_VOCA\"]"); //어휘
            log.info("어휘 확인");
            WebElement write = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_WRITE\"]"); //쓰기
            log.info("쓰기 확인");

            if(munbob.isDisplayed() && dockhae.isDisplayed() && listen.isDisplayed() && voca.isDisplayed() && write.isDisplayed()){
                log.info("영어전문관 화면 구성요소 확인 완료");
            }
        } catch (Exception e) {
            log.info("화면을 찾을 수 없음");
            throw new RuntimeException(e);
        }


    }


    @When("영어 전문관 {string} 클릭")
    public void 영어전문관과목클릭(String subject) throws InterruptedException{

        int idx=0;
        switch (subject) {
            case "문법":
                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_GRAMMAR\"]").click();
                break;
            case "독해":
                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_READ\"]").click();
                break;
            case "듣기":
                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_LISTEN\"]").click();
                break;
            case "어휘":
                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_VOCA\"]").click();
                break;
            case "쓰기/영작":
                AndroidManager.getElementByXpath("//android.widget.Image[@text=\"ICON_ENGMP_WRITE\"]").click();
                break;

            default:
                throw new IllegalArgumentException("올바르지 않은 메뉴: " + subject);
        }

        TimeUnit.SECONDS.sleep(5);
    }

    @When("드롭박스 {string} 클릭")
    public void 드롭박스클릭(String subject) throws InterruptedException{

        TimeUnit.SECONDS.sleep(10);
        // 드롭박스 열기
        AndroidManager.getDriver().findElement(By.id("com.wjthinkbig.nfmiddle.client:id/tv_sub_title")).click();

        String xpath = String.format("//android.widget.TextView[@text='%s']", subject);


        WebElement targetElement = null;
        if(subject.equals("내신진도")){
            targetElement = AndroidManager.getElementByXpath("(//android.widget.TextView[@text=\"내신진도\"])[2]");
        }else{
            targetElement = AndroidManager.getElementByXpath(xpath);
        }


        targetElement.click();
        System.out.println("클릭 완료: " + subject);
        TimeUnit.SECONDS.sleep(5);
    }

    @And("수학전문관 상단 드롭박스 {string} 이동")
    public void 수학전문관영역별수준별드롭박스클릭(String submenu){
        log.info("수학전문관 상단 드롭박스 이동");
        WebElement title =AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/tv_sub_title");
        title.click();
        int i = 0;
        if (submenu.equals("수준별")){
            log.info("수준별 이동");
            i = 1;
        } else if (submenu.equals("영역별")) {
            i = 2;
        }

        AndroidManager.getElementById("com.wjthinkbig.nfmiddle.client:id/lay_study_list")
                .findElements(By.className("android.widget.TextView")).get(i).click();
    }




    @When("수학전문관 화면 확인")
    public void 수학전문관화면확인(){
        try {
            WebElement topimg = AndroidManager.getElementByXpath("//android.widget.Image[@text=\"상단 대표 이미지\"]");
            log.info("상단 이미지 체크");

            List<WebElement> imageElements = AndroidManager.getDriver().findElements(By.xpath(
                    "//android.view.View[@resource-id='root']/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.widget.ListView//android.widget.Image"
            ));
            for(int i = 0; i < imageElements.size(); i++){
                log.info("list 체크: " + imageElements.get(i).getText());
            }

            WebElement gradation = AndroidManager.getElementByXpath("(//android.widget.TextView[@text=\"그라데이션\"])[1]");

            if(topimg.isDisplayed() && imageElements.size() == 8 && gradation.isDisplayed()){
                log.info("수학전문관 화면 확인 완료");
            }


        } catch (Exception e) {
            log.info("화면을 찾을 수 없음");
            throw new RuntimeException(e);
        }


    }

    @When("수학전문관 {string} 클릭")
    public void 수학전문관하단버튼클릭(String icon) throws InterruptedException {
        int idx=0;
        switch (icon) {
            case "입문":
                idx=0;
                break;
            case "기본":
                idx=1;
                break;
            case "발전":
                idx=2;
                break;
            case "심화":
                idx=3;
                break;
            case "수와연산":
                idx=4;
                break;
            case "방부등식":
                idx=5;
                break;
            case "함수":
                idx=6;
                break;
            case "기하":
                idx=7;
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 버튼: " + icon);
        }

        log.info("{} 클릭", icon);
        if (icon.equals("기하")){Utils.dragSourceToTarget(1580,470,1320,470);}
        TimeUnit.SECONDS.sleep(1);
        AndroidManager.getElementByXpath("//android.view.View[@resource-id='root']/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.widget.ListView")
                .findElements(By.className("android.widget.Image")).get(idx).click();
        TimeUnit.SECONDS.sleep(5);
    }


    @When("성적 우수자 보기 선택")
    public void 성적우수자클릭() throws InterruptedException {

        try{
            log.info("성적 우수자 버튼 클릭");
            AndroidManager.getElementByXpath("//android.widget.Button[@text=\"성적 우수자 보기\"]").click();
        } catch (Exception e) {
            log.info("성적우수자 버튼을 찾을수 없습니다.");
            throw new RuntimeException(e);
        }

        TimeUnit.SECONDS.sleep(5);
    }

    @And("강좌 드롭박스 {string} - {string} 변경")
    public void 강좌드롭박스변경(String icon,String menu) throws InterruptedException {
        // 빨간 버튼 -> 수준별 강좌 | 파란 버튼 -> 영역별 강좌
        log.info("강좌 드롭박스 클릭");
        Utils.touchSpecificCoordinates(1780,630);
        TimeUnit.SECONDS.sleep(2);

        int x=1780;
        int y=0;
        switch (menu){
            case "기본":
                y=400;
                break;
            case "발전":
                y=480;
                break;
            case "심화":
                y=560;
                break;
            case "입문":
                y=330;
                break;
            case "방부등식":
                y=480;
                break;
            case "함수":
                y=560;
                break;
            case "수와연산":
                y=400;
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 과목: " + icon);
        }

        log.info("{} --> {} 이동", icon, menu);
        Utils.touchSpecificCoordinates(x,y);

    }

    @And("AI영어 {string} 탭 클릭")
    public void ai영어탭클릭(String tap) {
        log.info("AI학습관 - AI 영어 - 교과서별 필수 영문법 {} 탭 클릭",tap);
        AndroidManager.getElementByXpath("//android.view.View[@content-desc=\""+tap+"\"]").click();
    }

    @And("AI영어 교과서별 {string} 클릭")
    public void ai영어교과서별클릭(String tap) {
        log.info("AI영어 교과서별 {} 클릭",tap);
        if (tap.equals("시험대비 교과서 AI 문법 공략")){
            AndroidManager.getElementByText("시험대비 교과서 AI  문법 공략").click();
        }
        else {
            AndroidManager.getElementByText(tap).click();
        }
    }

}






