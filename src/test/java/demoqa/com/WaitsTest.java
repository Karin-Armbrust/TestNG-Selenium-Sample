package demoqa.com;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.function.Function;

public class WaitsTest {
    WebDriver driver = new FirefoxDriver();

    @BeforeTest
    public void SetUpTest() {

        // Set the driver
        System.setProperty("webdriver.gecko.driver", "C:\\QA-Tools\\drivers\\geckodriver.exe");

        // Using this site since it's set up to delay operations
        driver.get("https://eviltester.github.io/supportclasses/#2000");

    }

    @Test
    public void FluentWaitTest() throws InterruptedException {
        final WebElement resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        /*WebElement singleMessageParent = new WebDriverWait(driver, Duration.ofSeconds(12))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("single")));*/

        WebElement singleMessageParent = driver.findElement(By.id("single-list"));
        FluentWait wait = new FluentWait<WebElement>(singleMessageParent)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No messages found!");

        wait.until(new HistoryMessagesIncreaseInNumber(0));
        final WebElement message = driver.findElement(By.cssSelector("#single-list li.message"));
        Assertions.assertTrue(message.getText().startsWith("Received message"));
    }

    public void ImplicitWaitTest() throws InterruptedException {
        //Implicitly wait has been deprecated and doesn't work well anyway

    }

    @AfterTest
    public void closeTest() throws InterruptedException {
        //driver.quit();
    }

    private class HistoryMessagesIncreaseInNumber implements Function<WebElement, Boolean> {
        private final int initialCount;
        public HistoryMessagesIncreaseInNumber(int initialCount) {
            this.initialCount = initialCount;
        }

        @Override
        public Boolean apply(final WebElement element) {
            int currentCount = element.findElements(By.cssSelector("li.message")).size();
            return currentCount>initialCount;
        }
    }
}
