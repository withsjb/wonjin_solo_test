import org.junit.Test;
import org.openqa.selenium.WebElement;
import utils.AndroidManager;

import java.util.concurrent.TimeUnit;

public class Alert {
    @Test
    public void restart() throws InterruptedException {
        WebElement alert = AndroidManager.getElementById("android:id/aerr_restart");
        if (alert.isDisplayed()){
            alert.click();
            TimeUnit.SECONDS.sleep(2);
            AndroidManager.getElementById("com.wjthinkbig.mid.master:id/imgTile1").click();
        }
    }

    @Test
    public void delete() throws InterruptedException {
        for (int i=0; i<85; i++){
            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/btn_delete").click();
            TimeUnit.SECONDS.sleep(1);
            AndroidManager.getElementById("com.wjthinkbig.thinkplayground:id/bt_right").click();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
