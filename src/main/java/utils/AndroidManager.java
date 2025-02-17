package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AndroidDriver, WebDriverWait 과 같이 드라이버 관련된 클래스,
 * Id, xPath 같은 Selector 를 사용하게 정의한 클래스
 * */
public class AndroidManager {
    public static final Logger log = LoggerFactory.getLogger(AndroidManager.class);

    private static WebDriverWait wait;
    private static AndroidDriver driver;

    private AndroidManager() {}

    /**
     * UiAutomator2 Driver를 생성한다. 이 Driver의 capability는 app.properties 파일에 의해 결정된다.
     * */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            try {
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                URL remoteUrl = new URL(AppProperty.getInstance().getRemoteUrl());
                log.info(String.valueOf(remoteUrl));

                desiredCapabilities.setCapability("appium:udid", AppProperty.getInstance().getUDID());
                desiredCapabilities.setCapability("platformName", AppProperty.getInstance().getPlatformName());
                desiredCapabilities.setCapability("appium:automationName", AppProperty.getInstance().getAutomationName());
                desiredCapabilities.setCapability("appium:platformVersion", AppProperty.getInstance().getPlatformVersion());
                desiredCapabilities.setCapability("appium:nativeWebScreenshot", AppProperty.getInstance().getNativeWebScreenshot());
                desiredCapabilities.setCapability("appium:newCommandTimeout", AppProperty.getInstance().getNewCommandTimeout());
                desiredCapabilities.setCapability("appium:connectHardwareKeyboard", AppProperty.getInstance().getConnectHardwareKeyboard());
                desiredCapabilities.setCapability("deviceType", AppProperty.getInstance().getDeviceName());

                driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            } catch (MalformedURLException e) {
                log.error("MalformedURLException occur");
                throw new RuntimeException(e);
            }
        }
        return driver;
    }

    /**
     * WebDriverWait Object를 가져온다. duration은 20초
     * */
    public static WebDriverWait getWait() {
        return getWait(10);
    }

    /**
     * WebDriverWait Object를 가져온다. 여기서 duration은 기다리는 시간을 의미
     * @param duration duration time (seconds)
     * */
    public static WebDriverWait getWait(int duration) {
        if (wait == null) {
            if (driver == null) {
                driver = getDriver();
            }
            wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        } else {
            wait.withTimeout(Duration.ofSeconds(duration));
        }
        return wait;
    }

    /**
     * Id를 통해 원하는 Element를 가져온다.
     * @param id element id
     * */
    public static WebElement getElementById(String id) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    /**
     * Accessibility Id를 통해 원하는 Element를 가져온다.
     * @param accessibilityId element id
     * */
    public static WebElement getElementByAccessibilityId(String accessibilityId) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(accessibilityId)));
    }

    /**
     * Id를 통해 원하는 Element를 가져오는데, 기본 duration 10초 이상 기다림이 필요하다고 판단 시, 또는 더 적게 필요할 때에 duration에 원하는 시간을 추가한다.
     * @param id element id
     * @param duration duration time (seconds)
     * */
    public static WebElement getElementByIdUntilDuration(String id, int duration) {
        return getWait(duration).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public static WebElement getElementByXpathUntilDuration(String xPath, int duration) {
        return getWait(duration).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
    }
    /**
     * xPath를 통해 원하는 Element를 가져온다.
     * @param xPath element xPath
     * */
    public static WebElement getElementByXpath(String xPath) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
    }

    /**
     * 같은 아이디의 여러개 Element가 있는 경우, index 번호를 통해 원하는 Element를 가져온다.
     * @param id elements id
     * @param index element index that you want to get
     * */
    public static WebElement getElementsByIdAndIndex(String id, int index) {
        return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(id))).get(index);
    }

    /**
     * 원하는 element가 swipe 가능한 화면 내 존재할 때 text가 있으면 해당 텍스트를 통해 element를 return
     * @param text element text
     * @return 찾으면 WebElement 못 찾으면 null
     * */
    public static WebElement getElementByTextAfterSwipe(String text) {
        log.info("text --> {}", "new UiScrollable(new UiSelector().scrollable(true))" + ".scrollIntoView(new UiSelector().text(\""+text+"\"))");
        try {
            return getDriver().findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))" + ".scrollIntoView(new UiSelector().text(\""+text+"\"))"
                    ));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 원하는 텍스트를 가진 요소를 찾기 위해 아래로, 그 다음 위로 스와이프하며 검색하는 메소드
     * @param text 찾고자 하는 요소의 텍스트
     * @param maxSwipesDown 아래로 스와이프할 최대 횟수
     * @param maxSwipesUp 위로 스와이프할 최대 횟수
     * @return 찾은 WebElement, 못 찾으면 null
     */
    public static WebElement getTextAfterSwipe(String text, int maxSwipesDown, int maxSwipesUp) {
        // 아래로 스와이프하며 검색
        WebElement element = swipeAndFind(text, maxSwipesDown, true);
        if (element != null) {
            return element;
        }

        // 위로 스와이프하며 검색
        return swipeAndFind(text, maxSwipesUp, false);
    }

    /**
     * 지정된 방향으로 스와이프하며 요소를 찾는 메소드
     * @param text 찾고자 하는 요소의 텍스트
     * @param maxSwipes 최대 스와이프 횟수
     * @param swipeDown true면 아래로, false면 위로 스와이프
     * @return 찾은 WebElement, 못 찾으면 null
     */
    private static WebElement swipeAndFind(String text, int maxSwipes, boolean swipeDown) {
        int swipeCount = 0;
        while (swipeCount < maxSwipes) {
            try {
                WebElement element = getDriver().findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"" + text + "\")"));
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (Exception e) {
                // 요소를 찾지 못한 경우 예외 발생, 무시하고 계속 진행
            }

            performSwipe(swipeDown);
            swipeCount++;
        }
        return null;
    }

    /**
     * 화면을 지정된 방향으로 스와이프하는 메소드
     * @param swipeDown true면 아래로, false면 위로 스와이프
     */
    private static void performSwipe(boolean swipeDown) {
        // 화면 크기 가져오기
        Dimension size = getDriver().manage().window().getSize();
        int startY, endY;
        if (swipeDown) {
            // 0.1씩 화면 이동
            // 스와이프 시작 지점 (화면 높이의 70% 지점)
            startY = (int) (size.height * 0.7);
            // 스와이프 종료 지점 (화면 높이의 60% 지점)
            endY = (int) (size.height * 0.6);
        } else {
            startY = (int) (size.height * 0.6);
            endY = (int) (size.height * 0.7);
        }
        // 화면 가운데 X 좌표
        int centerX = size.width / 2;
        // TouchAction을 사용하여 스와이프 동작 수행
        new TouchAction(getDriver())
                .press(PointOption.point(centerX, startY))// 시작 지점 터치
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))// 0.3초 대기
                .moveTo(PointOption.point(centerX, endY))
                .release()// 터치 해제
                .perform();// 동작 실행
        // 스와이프 후 화면 안정화를 위해 잠시 대기
        try {
            Thread.sleep(500); // 0.5초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void gradualSwipe(int startY, int endY, int centerX) {
        int steps = 5; // 스와이프 단계 수
        int stepSize = (endY - startY) / steps;

        for (int i = 0; i < steps; i++) {
            int currentStartY = startY + (stepSize * i);
            int currentEndY = currentStartY + stepSize;
            Utils.dragSourceToTarget(centerX, currentStartY, centerX, currentEndY);
            try {
                Thread.sleep(200); // 각 스와이프 사이에 짧은 대기 시간
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    /**
     * 원하는 텍스트를 찾아 특정 위치로 스와이프하는 메소드
     * @param text 찾고자 하는 요소의 텍스트
     * @param targetY 요소를 위치시키고자 하는 Y 좌표 (화면 상단으로부터의 픽셀 값)
     * @return 찾은 요소를 특정 위치로 스와이프한 후의 WebElement, 못 찾으면 null
     */
    public static WebElement findTextAndSwipeToPosition(String text, int targetY) {
        log.info("텍스트 '{}' 찾기 및 Y 좌표 {}로 스와이프 시도", text, targetY);
//        WebElement element = getElementByTextAfterSwipe(text);

        WebElement element = getTextAfterSwipe(text, 20,20);

        if (element != null) {
            try {
                // 요소의 현재 위치 확인
                Point location = element.getLocation();
                int currentY = location.getY();
                int centerX = getDriver().manage().window().getSize().width / 2;

                // 현재 위치와 목표 위치의 차이 계산
                int diffY = currentY - targetY;

                // 위치 조정이 필요한 경우 스와이프 실행
                if (Math.abs(diffY) > 10) {
                    log.info("요소를 Y 좌표 {}에서 {}로 점진적 스와이프", currentY, targetY);
                    gradualSwipe(currentY, targetY, centerX);

                    // 스와이프 후 요소 재확인
                    element = getTextAfterSwipe(text, 20, 10);
                } else {
                    log.info("요소가 이미 원하는 위치에 있습니다. 추가 조정 불필요.");
                }
            } catch (Exception e) {
                log.error("요소 위치 조정 중 오류 발생: ", e);
                return null;
            }
        } else {
            log.warn("텍스트 '{}'를 찾을 수 없습니다.", text);
        }

        return element;
    }
    /**
     * swipe 없이 화면 내에서 특정 텍스트를 가진 element를 찾아 반환한다.
     * @param text 찾을 element의 텍스트
     */
    public static WebElement getElementByText(String text) {
        log.info("Searching for element with text: {}", text);
        return getDriver().findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\""+text+"\")"
                ));
    }


    /**
    * Parameter로 전달한 Text를 가지고 있는 Element를 얻거나 그 Element까지 Scroll한다. 여기서 Scrollable한 List는 Horizontal List이어야 하고, 해당 List의 Resource-id를 전달해야한다.
    * @param resourceId scrollable list의 resource_id
    * @param text 찾고자 하는 엘리멘트가 가지는 text
    * */
    public static WebElement    getElementByTextAndHorizontalScrollableListIdAfterSwipe(String resourceId, String text) {
        log.info("This is uiautomatorText: {}", "new UiScrollable(new UiSelector().resourceId(\""+resourceId+"\")).setAsHorizontalList()" +
                ".scrollIntoView(new UiSelector().text(\""+text+"\"))");

        return getDriver().findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().resourceId(\""+resourceId+"\")).setAsHorizontalList()" +
                                ".scrollIntoView(new UiSelector().text(\""+text+"\"))"
                ));
    }


    /**
     * 토스트 팝업의 메시지를 리턴한다.
     * @param xPath xPath
     * @return toast message
     * */
    public static String getToastMessageByXpath(String xPath) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath))).getText();
    }

    /**
     * 특정 영역에서 원하는 element가 swipe 가능한 화면 내 존재할 때 포함되는 text가 있으면 해당 텍스트를 통해 element를 return
     * @param resourceId swipe 하고자 하는 특정 영역의 resource id(예: .*:id/recyclerView)
     * @param text element text
     * @return WebElement
     * */
    public static WebElement getElementByTextContainsAfterSwipe(String resourceId, String text) {
        log.info("text --> {}", "new UiScrollable(new UiSelector().resourceIdMatches(\""+resourceId+"\").scrollable(true))" + ".scrollIntoView(new UiSelector().textContains(\""+text+"\"))");
        return getDriver().findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().resourceIdMatches(\""+resourceId+"\").scrollable(true))" + ".scrollIntoView(new UiSelector().textContains(\""+text+"\"))"
                ));
    }

    /**
     * 특정 부모 element 하위에 동일 아이디의 여러 Element가 있는 경우, parentId, childId, index 번호를 통해 원하는 Element를 가져온다.
     * @param parentId elements id of parent
     * @param childId elements id of child
     * @param index element index that you want to get
     * */
    public static WebElement getElementsByIdsAndIndex(String parentId, String childId, int index) {
        WebElement parent = getDriver().findElement(By.id(parentId));
        List<WebElement> elementList = parent.findElements(By.id(childId));

        if(elementList.size() > 1) return elementList.get(index);
        else return parent.findElement(By.id(childId));
    }
    /**
     * 주어진 텍스트를 포함하는 모든 웹 요소를 찾아 반환합니다.
     * @param text 찾고자 하는 텍스트
     * @return 텍스트를 포함하는 WebElement 리스트. 요소를 찾지 못하면 빈 리스트 반환
     */
    public static List<WebElement> findElementsByText(String text) {
        try {// XPath를 사용하여 텍스트를 포함하는 모든 요소를 찾습니다.
            return driver.findElements(By.xpath("//*[contains(text(), '" + text + "')]"));
        } catch (Exception e) {
            // 예외 발생 시 오류 메시지를 출력하고 빈 리스트를 반환합니다.
            System.out.println("An error occurred while finding elements by text: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public static void copyJsonFile(String sourceFilePath, String destinationFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        if (destinationFile.exists()) {
            System.out.println("복사 성공");
            destinationFile.delete(); // 기존 파일 삭제
        }
        Files.copy(sourceFile.toPath(), destinationFile.toPath());
    }

    //여기부터 추가

    /**
     * 원하는 Xpath를 찾아 특정 위치로 스와이프하는 메소드
     * @param text 찾고자 하는 요소의 Xpath
     * @param targetY 요소를 위치시키고자 하는 Y 좌표 (화면 상단으로부터의 픽셀 값)
     * @return 찾은 요소를 특정 위치로 스와이프한 후의 WebElement, 못 찾으면 null
     */
    public static WebElement findXpathAndSwipeToPosition(String text, int targetY) {
        String dynamicXpath = "(//android.widget.TextView[@text='" + text + "'])[2]";
        log.info("동적 XPath 생성: {}", dynamicXpath);

        WebElement element = null;
        try {
            // 2. 생성한 XPath를 이용해 요소를 찾습니다.
            element = AndroidManager.getDriver().findElement(By.xpath(dynamicXpath));
        } catch (Exception e) {
            log.warn("동적 XPath '{}'에 해당하는 요소를 찾지 못했습니다.", dynamicXpath);
        }

        // 3. 요소가 존재하면 해당 요소의 위치를 가져와서 스와이프 진행
        if (element != null) {
            try {
                // 요소의 현재 위치 (Y 좌표) 확인
                Point location = element.getLocation();
                int currentY = location.getY();
                int centerX = AndroidManager.getDriver().manage().window().getSize().width / 2;
                int diffY = currentY - targetY;

                // 4. 현재 위치와 목표 위치의 차이가 10 이상이면 스와이프를 진행
                if (Math.abs(diffY) > 10) {
                    log.info("요소를 Y 좌표 {}에서 {}로 스와이프 시도합니다.", currentY, targetY);
                    // gradualSwipe 메서드: 현재 Y에서 targetY까지 점진적으로 스와이프합니다.
                    gradualSwipe(currentY, targetY, centerX);

                    // 5. 스와이프 후, 요소 위치를 다시 확인합니다.
                    element = AndroidManager.getDriver().findElement(By.xpath(dynamicXpath));
                } else {
                    log.info("요소가 이미 목표 위치({})에 있습니다.", targetY);
                }
            } catch (Exception e) {
                log.error("요소 위치 조정 중 오류 발생: ", e);
                return null;
            }
        }
        return element;
    }


    public static HttpURLConnection connect (String urlString, String method, String username, String apiToken) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + apiToken).getBytes()));
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;

    }
}
