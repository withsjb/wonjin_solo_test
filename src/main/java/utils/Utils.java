package utils;

import com.github.jaiimageio.impl.common.ImageUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    private static final String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_WINDOWS = OS.contains("win");
    public static boolean IS_MAC = OS.contains("mac");

    /**
     * Play type depending on setCommand()
     */
    public enum PlayType {
        AUDIOBOOK,
        VIDEO,
        BOOK,
        NONE
    }

    public enum CheckPoint {
        VIDEO_SPEED,
        RUNNING_STATE
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Dimension X
     */
    public static int getScreenCenterX() {
        return AndroidManager.getDriver().manage().window().getSize().getWidth() / 2;
    }

    /**
     * Dimension Y
     */
    public static int getScreenCenterY() {
        return AndroidManager.getDriver().manage().window().getSize().getHeight() / 2;
    }

    /**
     * 뷰어 화면의 가운데 영역 터치, 터치 시 상단바/하단바 노출
     */
    public static void touchCenterInViewer(AndroidDriver driver) {
        /* TouchAction deprecated
        TouchAction<?> touchAction =  new TouchAction<>(driver);
        touchAction.tap(PointOption.point(getScreenCenterX(), getScreenCenterY())).perform();
         */
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), driver.manage().window().getSize().getWidth() / 2, driver.manage().window().getSize().getHeight() / 2));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }


    /**
     * 뷰어 화면의 하단 영역 터치, 터치 시 상단바/하단바 노출
     */
    public static void touchBottomInViewer(AndroidDriver driver) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), driver.manage().window().getSize().getWidth() / 2, driver.manage().window().getSize().getHeight()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    /**
     * 임의의 지점을 클릭한다.
     *
     * @param xOffset x 좌표
     * @param yOffset y 좌표
     */
    public static void touchSpecificCoordinates(int xOffset, int yOffset) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), xOffset, yOffset));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AndroidManager.getDriver().perform(List.of(swipe));
    }

    /**
     * 스크롤을 이동한다.
     *
     * @param startX 시작 x 좌표
     * @param startY 시작 y 좌표
     * @param endX 끝 x 좌표
     * @param endY 끝 y 좌표
     */
    public static void swipeToScroll(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        // 시작 위치로 이동
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // 끝 위치로 이동 (스크롤)
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));

        // 손 떼기
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // 동작 실행
        AndroidManager.getDriver().perform(List.of(swipe));
    }


    /**
     * Viewer 화면의 우측 중단을 클릭 (Landscape 상태에서 (근데 상관없을것 같음))
     */
    public static void nextPageInViewer(AndroidDriver driver) {
        int centerWidth = driver.manage().window().getSize().getWidth() / 2;
        int centerHeight = driver.manage().window().getSize().getHeight() / 2;
        int rightWidth = driver.manage().window().getSize().getWidth() - (centerWidth / 4);

        /* TouchAction deprecated
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.tap(PointOption.point(rightWidth, centerHeight)).perform();
         */
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), rightWidth, centerHeight));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    /**
     * Viewer 화면의 좌측 중단을 클릭 (Landscape 상태에서 (근데 상관없을것 같음))
     */
    public static void prevPageInViewer(AndroidDriver driver) {
        int centerWidth = driver.manage().window().getSize().getWidth() / 2;
        int centerHeight = driver.manage().window().getSize().getHeight() / 2;
        int leftWidth = centerWidth / 4;

        /* TouchAction deprecated
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.tap(PointOption.point(leftWidth, centerHeight)).perform();
         */

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), leftWidth, centerHeight));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    /**
     * 현재 화면의 스크린샷을 찍고 스크린샷 파일의 이름을 리턴한다.
     */
    public static String takeScreenShot() {
        //File file = AndroidManager.getDriver().getScreenshotAs(OutputType.FILE);
        String scrShotDir = AppProperty.getInstance().getProperty("screenshotDir");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh__mm__ssaa");
        new File(scrShotDir).mkdirs();
        String dest = dateFormat.format(new Date()) + ".png";

        try {
            //FileUtils.copyFile(file, new File(scrShotDir + "/" + dest));
            String strFile = ((TakesScreenshot) AndroidManager.getDriver()).getScreenshotAs(OutputType.BASE64);
            byte[] decodedBytes = Base64.decodeBase64(strFile);
            OutputStream stream = new FileOutputStream(scrShotDir + "/" + dest);
            stream.write(decodedBytes);
            stream.close();
        } catch (IOException e) {
            log.error("Image not transferred to screenshot folder");
            e.printStackTrace();
        }
        return dest;
    }

    /**
     * 저장한 이미지를 Tess4J 를 이용하여 Image의 Text들을 리턴한다.
     * (inspector에 element로 아예 가져올 수가 없을때 사용하기 위함)
     *
     * @param imageName 저장된 Image name
     * @return image를 text로 전환했을 때 전환된 텍스트들
     * @throws RuntimeException 변환한 image의 text 길이가 0일 때
     */
    public static String imageToText(String imageName) {
        File imageFile = new File(new java.io.File("./" + AppProperty.getInstance().getProperty("screenshotDir") + "/"), imageName);

        Tesseract tesseract = new Tesseract();

        if (IS_WINDOWS) {
            tesseract.setDatapath(AppProperty.getInstance().getProperty("windowTessdataPath"));
        }
        if (IS_MAC) {
            tesseract.setDatapath(AppProperty.getInstance().getProperty("tessdataPath"));
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert img != null;
        BufferedImage blackNWhite = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphics = blackNWhite.createGraphics();
        graphics.drawImage(img, 0, 0, null);
        tesseract.setLanguage("kor");

        String result = null;
        try {
            result = tesseract.doOCR(blackNWhite);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        if (result == null) throw new RuntimeException("Can't transfer image to text");
        return result.trim();
    }

    /**
     * 저장한 이미지를 Tess4J 를 이용하여 Image의 Text들을 리턴한다.
     * (inspector에 element로 아예 가져올 수가 없을때 사용하기 위함)
     *
     * @param imageName 저장된 Image name
     * @param language 언어 설정(kor/eng 등)
     * @return image를 text로 전환했을 때 전환된 텍스트들
     * @throws RuntimeException 변환한 image의 text 길이가 0일 때
     */
    public static String imageToText(String imageName, String language) {
        File imageFile = new File(new java.io.File("./" + AppProperty.getInstance().getProperty("screenshotDir") + "/"), imageName);

        Tesseract tesseract = new Tesseract();

        if (IS_WINDOWS) {
            tesseract.setDatapath(AppProperty.getInstance().getProperty("windowTessdataPath"));
        }
        if (IS_MAC) {
            tesseract.setDatapath(AppProperty.getInstance().getProperty("tessdataPath"));
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert img != null;
        BufferedImage blackNWhite = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphics = blackNWhite.createGraphics();
        graphics.drawImage(img, 0, 0, null);

        //kor, eng 등 language 설정
        tesseract.setLanguage(language);

        String result = null;
        try {
            result = tesseract.doOCR(blackNWhite);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        if (result == null) throw new RuntimeException("Can't transfer image to text");
        return result.trim();
    }

    /**
     * 화면의 가운데 지점에서 원하는 특정 지점 (A)로 드래그
     *
     * @param driver  driver
     * @param xOffset 원하는 지점 (x)
     * @param yOffset 원하는 지점 (y)
     */
    public static void dragScreenCenterToA(AndroidDriver driver, int xOffset, int yOffset) {
        /* TouchAction deprecated
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.press(PointOption.point(getScreenCenterX(), getScreenCenterY()))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .perform()
                .moveTo(pointOption)
                .release()
                .perform();
         */

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), (driver.manage().window().getSize().getWidth() / 2), (driver.manage().window().getSize().getHeight() / 2)));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), xOffset, yOffset));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    /**
     * 특정 지점부터 특정 지점까지 드래그한다.
     *
     * @param sourceX 시작 지점의 x 좌표
     * @param sourceY 시작 지점의 y 좌표
     * @param targetX 도착 지점의 x 좌표
     * @param targetY 도착 지점의 y 좌표
     */
    public static void dragSourceToTarget(int sourceX, int sourceY, int targetX, int targetY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), sourceX, sourceY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), targetX, targetY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AndroidManager.getDriver().perform(List.of(swipe));
    }

    /**
     * 우측 끝에서 좌측 끝으로 스와이프 제스쳐
     */
    public static void swipeRightToLeft() {
        /* TouchAction deprecated

        TouchAction<?> touchAction = new TouchAction<>(AndroidManager.getDriver());
        touchAction.press(PointOption.point((AndroidManager.getDriver().manage().window().getSize().getWidth() -1), getScreenCenterY()))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(1, getScreenCenterY()))
                .release()
                .perform();
         */

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), (AndroidManager.getDriver().manage().window().getSize().getWidth() - 1), getScreenCenterY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), 1, Utils.getScreenCenterY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AndroidManager.getDriver().perform(List.of(swipe));
    }

    /**
     * 좌측 끝에서 우측 끝으로 스와이프 제스쳐
     */
    public static void swipeLeftToRight() {
        /* TouchAction deprecated

        TouchAction<?> touchAction = new TouchAction<>(AndroidManager.getDriver());
        touchAction.press(PointOption.point(1, getScreenCenterY()))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point((AndroidManager.getDriver().manage().window().getSize().getWidth() -1), getScreenCenterY()))
                .release()
                .perform();
         */

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), 1, getScreenCenterY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), (AndroidManager.getDriver().manage().window().getSize().getWidth() - 1), Utils.getScreenCenterY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AndroidManager.getDriver().perform(List.of(swipe));
    }

    /**
     * 커맨드 명령어 입력 후 동영상 재생여부 true/false return
     *
     * @return boolean (started - true / paused - false)
     */
    public static boolean isVideoPlayerRunning(){
        return readStreamOutput(setCommand(PlayType.VIDEO, CheckPoint.RUNNING_STATE));
    }


    /**
     * 독서 실행 시 재생이 가능한 도서(오디오북, 오디오이북 등등)의 현재 재생여부
     *
     * @return boolean (started - true / stopped - false)
     */
    public static boolean isBookPlayerRunning(){
        return readStreamOutput(setCommand(PlayType.BOOK,CheckPoint.RUNNING_STATE));
    }

    /**
     * 내가 만든 오디오북을 재생할 때의 현재 재생여부 체크
     *
     * @return boolean (started - true / stopped - false)
     */
    public static boolean isAudioBookBGMRunning() {
        return readStreamOutput(setCommand(PlayType.AUDIOBOOK, CheckPoint.RUNNING_STATE));
    }

    /**
     * 현재 실행중인 비디오의 속도 <br>
     * adb log = tablet displayed <br>
     * 0.80 = x0.8 <br>
     * 1.00 = x1 <br>
     * 1.10 = x1.2 <br>
     * 1.20 = x1.5 <br>
     * 1.40 = x1.8 <br>
     * 1.60 = x2 <br>
     */
    public static String currentVideoPlayerSpeed() {
        return readStreamOutput(setCommand(PlayType.NONE, CheckPoint.VIDEO_SPEED), CheckPoint.VIDEO_SPEED).substring(6, 10);
    }


    /**
     * return process instance by type
     *
     * @param type PlayType enum (AUDIOBOOK, BOOK, VIDEO)
     */
    public static Process setCommand(PlayType type, CheckPoint checkPoint) {

        Process process = null;
        try {
            if (checkPoint == CheckPoint.RUNNING_STATE) {
                if (IS_MAC) {
                    switch (type) {
                        case AUDIOBOOK:
                            process = Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", "adb shell dumpsys audio | grep \"type:android.media.MediaPlayer\""});
                            break;
                        case BOOK:
                            process = Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", "adb shell dumpsys audio | grep \"OpenSL ES AudioPlayer\""});
                            break;
                        case VIDEO:
                            process = Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", "adb shell dumpsys audio | grep \"android.media.AudioTrack\""});
                            break;
                    }
                }
                if (IS_WINDOWS) {
                    switch (type) {
                        case AUDIOBOOK:
                            process = Runtime.getRuntime().exec("cmd /c " + "adb shell dumpsys audio | findstr /c:\"type:android.media.MediaPlayer\"");
                            break;
                        case BOOK:
                            process = Runtime.getRuntime().exec("cmd /c " + "adb shell dumpsys audio | findstr /c:\"OpenSL ES AudioPlayer\"");
                            break;
                        case VIDEO:
                            process = Runtime.getRuntime().exec("cmd /c " + "adb shell dumpsys audio | findstr /c:\"android.media.AudioTrack\"");
                            break;
                    }
                }
            }

            if (checkPoint == CheckPoint.VIDEO_SPEED && type == PlayType.NONE) {
                if (IS_MAC) {
                    process = Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", "adb logcat -d | grep \"playbackParameters\""});
                }
                if (IS_WINDOWS) {
                    process = Runtime.getRuntime().exec("cmd /c " + "adb logcat -d | findstr \"playbackParameters\"");
                }
            }

            if (process == null) {
                throw new RuntimeException("getRuntime() execution error");
            }
            return process;
        } catch (IOException e) {
            throw new RuntimeException("When executed getRuntime().exec(), IO Exception occurred");
        }
    }

    /**
     * Read input stream and print output
     */
    public static boolean readStreamOutput(Process process) {
        if (process == null) throw new RuntimeException("Process is null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                log.info("line => {}", line);
                if (line.contains("state:")) {

                    int startIndex = line.indexOf("state:");
                    int endIndex = startIndex + 15;

                    String result = line.substring(startIndex, endIndex);
                    if (result.contains("started")) {
                        return true;
                    } 
                    if(result.contains("paused")) {
                        if(!getDeviceType().equals("SM-T500")) if (reader.readLine() != null) continue;
                        return false;
                    }
                    if(result.contains("stopped")) {
                        if(!getDeviceType().equals("SM-T500")) if (reader.readLine() != null) continue;
                        return false;
                    }
                } else {
                    if ((line = reader.readLine()) != null) continue;
                    throw new RuntimeException("String 'state:' not found");
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("IO Exception occurred");
        }
    }

    /**
     * Video Speed check
     * adb log = tablet displayed
     * 0.80 = x0.8
     * 1.00 = x1
     * 1.10 = x1.2
     * 1.20 = x1.5
     * 1.40 = x1.8
     * 1.60 = x2
     */
    public static String readStreamOutput(Process process, CheckPoint checkPoint) {
        if (process == null) throw new RuntimeException("Process is null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String result = "";
        try {
            if (checkPoint == CheckPoint.VIDEO_SPEED) {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("speed")) {
                        int startIndex = line.indexOf("speed");
                        int endIndex = startIndex + 12;

                        result = line.substring(startIndex, endIndex);
                    } else {
                        throw new RuntimeException("String 'speed' does not exists");
                    }
                }
            }
            if (result.equals("")) throw new RuntimeException("While reading stream buffer, error occurred");
            return result;
        } catch (IOException e) {
            throw new RuntimeException("IO Exception occurred");
        }
    }

    /**
     * swipe screen(DOWN, UP, LEFT, RIGHT)
     */
    public static void swipeScreen(Direction dir) {
        System.out.println("swipeScreen(): dir: '" + dir + "'");

        int edgeBorder = 10;

        Dimension dims = AndroidManager.getDriver().manage().window().getSize();
        int pointStartX = dims.width / 2;
        int pointStartY = dims.height / 2;

        int pointEndX = 0;
        int pointEndY = 0;
        switch (dir) {
            case DOWN:
                pointEndX = dims.width / 2;
                pointEndY = dims.height - edgeBorder;
                break;
            case UP:
                pointEndX = dims.width / 2;
                pointEndY = edgeBorder;
                break;
            case LEFT:
                pointEndX = edgeBorder;
                pointEndY = dims.height / 2;
                break;
            case RIGHT:
                pointEndX = dims.width - edgeBorder;
                pointEndY = dims.height / 2;
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        System.out.println("swipeScreen(): pointStartX: '" + pointStartX + "', pointStartY: '"+pointStartY+"', pointEndX: '"+pointEndX+"', pointEndY: "+pointEndY);

        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), pointStartX, pointStartY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                    PointerInput.Origin.viewport(), pointEndX, pointEndY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            AndroidManager.getDriver().perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
        }
    }

    /**
     * DeviceType 정보 리턴
     * T500, T583, M2 등
     */
    public static String getDeviceType() {
        String deviceType = AndroidManager.getDriver().getCapabilities().getCapability("deviceType").toString();
        return deviceType;
    }

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 원본 이미지와 비교 이미지 일치/포함 여부 확인 (Appium images plugin 활용)
     * mode = compare   이미지 2개가 서로 일치하는지 비교
     * mode = contain   이미지가 다른 이미지 안에 포함되어 있는 영역인지
     */
    public static void comparePixelToPixel(String mode) throws IOException {

        double threshold = 0.5;         // 유사도

        String screenShotDir = AppProperty.getInstance().getProperty("screenshotDir");          //이미지 경로

        File directory = new File(screenShotDir);
        File[] files = directory.listFiles();

        File originImage = null;                //원본 이미지
        File targetImage = null;                //비교 이미지

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.contains("_origin")) {
                    originImage = file;
                    log.info("원본 이미지: " + originImage.getName());
                } else if (fileName.contains("_target")) {
                    targetImage = file;
                    log.info("비교 이미지: " + targetImage.getName());
                }
            }
        }

        if(originImage == null)
            log.info("원본 이미지 파일이 없습니다.");
        if(targetImage == null)
            log.info("비교 이미지 파일이 없습니다.");

        if(mode == "Compare") {                                                                                         //2개의 이미지가 서로 일치하는지
            SimilarityMatchingResult result;
            result = AndroidManager.getDriver().getImagesSimilarity(originImage, targetImage,new SimilarityMatchingOptions().withEnabledVisualization());

            assertTrue("시각화 정보 이미지가 없습니다.",result.getVisualization().length > 0);
            assertTrue("시각화 정보 이미지의 유사도가 일치하지 않습니다.",result.getScore() > threshold);        //유사도 체크
        }
        else if(mode == "Contain"){                                                                                     //하나의 이미지 안에 다른 이미지가 포함되어 있는지 확인(Appium)
            OccurrenceMatchingResult result;
            result = AndroidManager.getDriver().findImageOccurrence(originImage, targetImage, new OccurrenceMatchingOptions().withEnabledVisualization());

            assertTrue("시각화 정보 이미지가 없습니다.",result.getVisualization().length > 0);               //어떤 이미지라도 보이는게 있는지
            assertNotNull("일치하는 이미지 영역이 없습니다.",result.getRect());                                       //이미지가 일치하는 사각형(rect) 영역 정보가 null이 아닌지
        }

    }

}
