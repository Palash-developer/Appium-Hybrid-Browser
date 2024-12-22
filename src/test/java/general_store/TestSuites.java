package general_store;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class TestSuites extends EntryPoint {

    @Test
    public void shoppingTest() throws InterruptedException {
        // ! Open the app
        openApp();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        String toastTxt = driver.findElement(AppiumBy.xpath("//android.widget.Toast[@text='Please enter your name']"))
                .getText();
        Assert.assertEquals("Please enter your name", toastTxt);

        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollAndClickActionHelper("Brazil");
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Brazil']")).click();

        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Palash");
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioMale")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        // ! Add to cart items
        String[] arrProducts = { "Air Jordan 9 Retro", "Converse All Star" };
        scrollAndClickActionHelper(arrProducts[0]);
        int iteamCount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        for (int i = 0; i < iteamCount1; i++) {
            String itemName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i)
                    .getText();
            if (itemName.equalsIgnoreCase(arrProducts[0])) {
                Thread.sleep(3000);
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }
        }
        scrollAndClickActionHelper(arrProducts[1]);
        int iteamCount2 = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        for (int i = 0; i < iteamCount2; i++) {
            String itemName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i)
                    .getText();
            if (itemName.equalsIgnoreCase(arrProducts[1])) {
                Thread.sleep(3000);
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }
        }
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        // ! Check if the desired product is added to the cart
        // ! Check product total

        int itemCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        Double[] productPrice = new Double[itemCount];
        double sum = 0.0;
        for (int i = 0; i < itemCount; i++) {
            String itemName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i)
                    .getText();
            Assert.assertEquals(itemName, arrProducts[i]);
            String itemPrice = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(i)
                    .getText().replace("$", "");
            productPrice[i] = Double.parseDouble(itemPrice);
            System.out.println(productPrice[i]);

        }
        for (double num : productPrice) {
            sum = sum + num;
        }
        String totalPriceStr = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl"))
                .getText().replace("$", "");
        Double totalPrice = Double.parseDouble(totalPriceStr);
        Assert.assertEquals(totalPrice, sum);

        // ! Long press on the terms button and verify
        WebElement element = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
        longPressActionHelper(element);
        String alertTitleText = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/alertTitle"))
                .getText();
        Assert.assertEquals(alertTitleText, "Terms Of Conditions");
        driver.findElement(AppiumBy.id("android:id/button1")).click();

        // ! Check the checkbox and visit to the web page
        driver.findElement(By.xpath(
                "//android.widget.CheckBox[@text=\"Send me e-mails on discounts related to selected products in future\"]"))
                .click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(5000);

        // ! hybrid app handle the web page
        Set<String> contexts = driver.getContextHandles();
        for (String contextName : contexts) {
            System.out.println(contextName);
        }
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys(arrProducts[0]);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        // ! get back to the main app
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");

        Thread.sleep(6000);
    }

}
