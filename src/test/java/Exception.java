import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import utils.AndroidManager;

import java.util.concurrent.TimeUnit;

import static utils.AndroidManager.log;

public class Exception extends Throwable {
    @Test
    public void Refresh() throws InterruptedException {
        try{
            AndroidManager.getDriver().pressKey(new KeyEvent(AndroidKey.HOME));
        }catch (java.lang.Exception e){
            try {
                // 웅진 북클럽 홉앱으로 사용 팝업 나오면 항상 클릭 해주기
                WebElement allways = AndroidManager.getElementById("android:id/button_always");
                if (allways.isDisplayed()) allways.click();
            }catch (java.lang.Exception ignored){}
            try {
                // timeout 오류 팝업 클릭해주기
                WebElement timeout = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvMessage");
                if (timeout.getText().equals("timeout")){
                    AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/btnRight").click();
                }
            }catch (java.lang.Exception ignored){}
            AndroidManager.getDriver().pressKey(new KeyEvent(AndroidKey.HOME));
        }
        int arg0 = 3;
        log.info("NoSuchElementException : 새로고침 클릭");
        WebElement refresh = AndroidManager.getElementById("com.wjthinkbig.mlauncher2:id/tvRefresh");
        if (refresh.isDisplayed()) {
            refresh.click();
            TimeUnit.SECONDS.sleep(10);
            log.info("{}학년 버튼 클릭", arg0);
            TimeUnit.SECONDS.sleep(2);
            WebElement element = AndroidManager.getElementsByIdAndIndex("com.wjthinkbig.mlauncher2:id/tab_title", arg0);
            if (element.isDisplayed()) element.click();
        }
    }
}
