package demoqa.com;

import demoqa.com.components.SelectMenuComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SelectMenuMultipleTest {
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

    // Is the Select set to multiple Test
    @Test(priority = 1)
    public void SelectMenuIsMultipleTest() throws InterruptedException {

        // Wait until Select Menu is ready
        SelectMenuComponent multiSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu1"));
        multiSelectMenu.elementNowReady();

        // Check to see if the Menu is a Multiple Select menu
        Assertions.assertEquals(true, multiSelectMenu.isMultipleSelect());

        System.out.println("SelectMenuIsMultipleTest()");
        System.out.println("Expected: " + true);
        System.out.println("Actual:   " + multiSelectMenu.isMultipleSelect());
    }

    // How many options does the Select have Test
    @Test(priority = 2)
    public void SelectMenuOptionNumberTest() throws InterruptedException {

        // Wait until Select Menu is ready
        SelectMenuComponent multiSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu1"));
        multiSelectMenu.elementNowReady();

        // Get the number of options
        int options = multiSelectMenu.getNumberOfOptions();
        Assertions.assertEquals(5, options);

        System.out.println("SelectMenuOptionNumberTest()");
        System.out.println("Expected: " + "5");
        System.out.println("Actual:   " + options);
    }

    // Select multiple values
    @Test(priority=3)
    public void SelectMultipleOptions() throws InterruptedException {

        // Wait until Select Menu is ready
        SelectMenuComponent multiSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu1"));
        multiSelectMenu.elementNowReady();

        multiSelectMenu.selectSingleItemByValue("Red");
        multiSelectMenu.selectSingleItemByValue("Yellow");

        List<WebElement> selectedOptionList = multiSelectMenu.getMultiSelections();

        Assertions.assertEquals(2, selectedOptionList.size());
        Assertions.assertEquals("Red", selectedOptionList.get(0).getText());
        Assertions.assertEquals("Yellow", selectedOptionList.get(1).getText());

        WebElement outputMessage = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("selectMenuOutput")));
        Assertions.assertEquals("You selected some stuff: Red Yellow", outputMessage.getText());

        System.out.println("SelectMultipleOptions()");
        System.out.println("Expected: " + "You selected some stuff: Red Yellow");
        System.out.println("Actual:   " + outputMessage.getText());
    }

    // Select and deselect multiple values
    @Test(priority=4)
    public void DeselectMultipleOptions() throws InterruptedException {
        // Wait until Select Menu is ready
        SelectMenuComponent multiSelectMenu = new SelectMenuComponent(driver, By.id("selectmenu1"));
        multiSelectMenu.elementNowReady();

        // Set a couple of selections
        multiSelectMenu.selectSingleItemByValue("Red");
        multiSelectMenu.selectSingleItemByValue("Yellow");

        // deselect all the selections
        multiSelectMenu.deselectAll();

        // Check that there are no selections
        List<WebElement> selectedOptionList = multiSelectMenu.getMultiSelections();
        Assertions.assertEquals(0, selectedOptionList.size());


        System.out.println("DeselectMultipleOptions()");
        System.out.println("Expected: " + "0");
        System.out.println("Actual:   " + selectedOptionList.size());
    }

    // Close the driver after each test
    @AfterTest
    public void CloseTest() throws InterruptedException {
        driver.quit();
    }
}
