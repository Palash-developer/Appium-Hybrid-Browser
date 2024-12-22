package MobileBrowser;

import java.io.File;
import java.net.URI;
import java.time.Duration;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class MobileBrowserEntryPoint {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;

    @BeforeMethod
    public void configAppiumTest() throws Exception {
        service = new AppiumServiceBuilder().withAppiumJS(
                new File("C://Users//Palash//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723)
                .withArgument(() -> "--allow-insecure", "chromedriver_autodownload").build();

        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Appium-Pixel 7");
        options.setCapability("browserName", "Chrome");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void qshutDown() {
        driver.quit();
        service.stop();
    }
}
