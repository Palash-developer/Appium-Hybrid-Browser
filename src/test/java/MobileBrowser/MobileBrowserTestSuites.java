package MobileBrowser;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileBrowserTestSuites extends MobileBrowserEntryPoint {

    @Test
    public void mobileBrowserTest() throws Exception {
        // driver.get("https://google.com");
        // System.out.println("Browser title is " + driver.getTitle());
        // driver.findElement(By.name("q")).sendKeys("Udemy");
        // driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        // Thread.sleep(5000);

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        System.out.println("Browser title is " + driver.getTitle());
        driver.findElement(By.className("navbar-toggler-icon")).click();
        int count = driver.findElements(By.className("nav-item")).size();
        for (int i = 0; i < count; i++) {
            driver.findElements(By.className("nav-item")).get(i).click();
            if (i == 0) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-1000)", "");
                String text = driver.findElements(By.className("list-group-item")).get(2).findElement(By.tagName("a"))
                        .getText();
                System.out.println(text);
                Assert.assertEquals(text, "Devops");
            }
            Thread.sleep(2000);
        }

    }

}
