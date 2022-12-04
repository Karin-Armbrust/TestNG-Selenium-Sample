package demoqa.com;

import demoqa.com.components.SelectMenuComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class SelectMenuSingleTest {
    // Initialize the Webdriver
    WebDriver driver = new FirefoxDriver();

    // Set up before the tests are run Note: This is different than JUnit in that
    // @BeforeTest is run once before all the tests
    // and @AfterTest Are run once after all the tests are complete.
    @BeforeTest
    public void SetUpTest() throws InterruptedException {
        // Set the driver path
        System.setProperty("webdriver.gecko.driver", "C:\\QA-Tools\\drivers\\geckodriver.exe");

        // Open the website
        driver.get("http://karinarmbrust.com/testdemo.html");
        Thread.sleep(3000);

        // Wait until Select Menu is ready
        SelectMenuComponent singleSelectMenu = new SelectMenuComponent(driver, By.id("elements"));
        singleSelectMenu.elementNowReady();

        // Select the option
        singleSelectMenu.selectSingleItemByValueByVisibleText("Select Menus");
    }

    // Tests that the Select menu is a Single
    @Test(priority=1)
    public void SelectMenuIsSingleTest() throws InterruptedException {

        // Wait until Select Menu is ready
        SelectMenuComponent singleSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu2"));
        singleSelectMenu.elementNowReady();

        // Check to see if the Menu is a Multiple Select menu
        Assertions.assertEquals(false, singleSelectMenu.isMultipleSelect());

        System.out.println("SelectMenuIsMultipleTest()");
        System.out.println("Expected: " + false);
        System.out.println("Actual:   " + singleSelectMenu.isMultipleSelect());
    }

        // Check the number of options
    @Test(priority=2)
    public void SelectMenuSingleOptionNumberTest() throws InterruptedException {
        // Wait until Select Menu is ready
        SelectMenuComponent singleSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu2"));
        singleSelectMenu.elementNowReady();

        // Get the number of options
        int options = singleSelectMenu.getNumberOfOptions();

        // Ensure the result is 5
        Assertions.assertEquals(5, options);

        System.out.println("SelectMenuSingleOptionNumberTest()");
        System.out.println("Expected: " + "5");
        System.out.println("Actual:   " + options);
    }

    // A Test that selects several values but expects only the last one to persist
    @Test(priority=3)
    public void SelectOneValue() throws InterruptedException {
        // Wait until Select Menu is ready
        SelectMenuComponent singleSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu2"));
        singleSelectMenu.elementNowReady();

        // Select several options
        // Expecting that Bike will be deselected leaving only Train
        singleSelectMenu.selectSingleItemByValue("Bike");
        singleSelectMenu.selectSingleItemByValue("Train");

        WebElement outputMessage = new WebDriverWait(driver, Duration.ofSeconds(3))
           .until(ExpectedConditions.presenceOfElementLocated(By.id("selectMenuOutput2")));

        // Ensure Train was selected
        Assertions.assertEquals("You selected some stuff: Train",
                outputMessage.getText());

        System.out.println("SelectOneValue()");
        System.out.println("Expected: " + "You selected some stuff: Train");
        System.out.println("Actual:   " + outputMessage.getText());
    }

    // Close the driver after each test
    @AfterTest
    public void CloseTest() throws InterruptedException {
        driver.quit();
    }
}
