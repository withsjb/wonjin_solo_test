package utils;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang3.Validate;


/**
 * 각 환경에서 필요한 property 가져오기 위한 클래스
 * */
public class AppProperty {

    private final String propertyFile = "app.properties";
    private Properties properties = new Properties();

    private AppProperty() {
        reload();
    }
    private static AppProperty inst = new AppProperty();
    public static AppProperty getInstance() {
        return inst;
    }

    public void reload() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(propertyFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key){
        Validate.notEmpty(key, "property를 가져올 때 key 값이 반드시 필요합니다.");
        String val = properties.getProperty(key);
        Validate.notEmpty(val, key + " is not");
        return val;
    }
    public String getProperty(String key, String defaultVal){
        Validate.notEmpty(key, "property를 가져올 때 key 값이 반드시 필요합니다.");
        return properties.getProperty(key, defaultVal);
    }
    public Integer getIntegerProperty(String key){
        String val = this.getProperty(key);
        return  Integer.parseInt(val);
    }
    public Boolean getBooleanProperty(String key) {
        String val = this.getProperty(key);
        return Boolean.parseBoolean(val);
    }

    /**
    "appium:deviceName"
    "platformName"
    "appium:automationName"
    "appium:platformVersion"
    "appium:skipUnlock"
    "appium:autoGrantPermissions"
    "appium:ensureWebviewsHavePages"
    "appium:nativeWebScreenshot"
    "appium:newCommandTimeout"
    "appium:connectHardwareKeyboard"
     */

    public String getRemoteUrl() {
        return getProperty("remoteUrl");
    }
    public String getSystemPort() {
        return getProperty("systemPort");
    }
    public String getDeviceName() {
        return getProperty("deviceName");
    }
    public String getUDID() { return getProperty("udid"); }
    public String getPlatformName() {
        return getProperty("platformName", "Android");
    }
    public String getAutomationName() {
        return getProperty("automationName", "UiAutomator2");
    }
    public String getPlatformVersion() {
        return getProperty("platformVersion", "8");
    }
    public Boolean getSkipUnlock() {
        return getBooleanProperty("skipUnlock");
    }
    public Boolean getAutoGrantPermissions() {
        return getBooleanProperty("autoGrantPermissions");
    }
    public Boolean getEnsureWebviewsHavePages() {
        return getBooleanProperty("ensureWebviewsHavePages");
    }
    public Boolean getNativeWebScreenshot() {
        return getBooleanProperty("nativeWebScreenshot");
    }
    public Integer getNewCommandTimeout() {
        return getIntegerProperty("newCommandTimeout");
    }
    public Boolean getConnectHardwareKeyboard() {
        return getBooleanProperty("connectHardwareKeyboard");
    }

}
