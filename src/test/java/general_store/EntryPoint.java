package general_store;

import java.io.File;
import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class EntryPoint {
    public AndroidDriver driver;
    public AppiumDriverLocalService service;

    @BeforeMethod
    public void configAppiumTest() throws Exception {
        service = new AppiumServiceBuilder().withAppiumJS(
                new File("C://Users//Palash//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723)
                .withArgument(() -> "--allow-insecure", "chromedriver_autodownload").build();
        // ! Chrome Driver setup:
        // ! <withArgument(() -> "--allow-insecure","chromedriver_autodownload")>

        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Appium-Pixel 7");

        options.setApp("E://Appium//general_store//src//test//java//resources//General-Store.apk");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void qshutDown() {
        driver.quit();
        service.stop();
    }

    public void openApp() {
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/splashscreen")).click();
    }

    public void scrollAndClickActionHelper(String text) {
        driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"))"));
    }

    public void longPressActionHelper(WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(),
                        "duration", 2000));
    }
}
