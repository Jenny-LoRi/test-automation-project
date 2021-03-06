package app.google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by LoRi on 06.06.2017.
 */

public class SearchTest {

    private WebDriver driver;
    private WebElement element;


    @BeforeTest
    public void setup() {
        //System.setProperty("webdriver.gecko.driver", "D:\\Dev\\Projects\\TAF\\drivers\\geckodriver.exe");//Firefox Browser
        System.setProperty("webdriver.chrome.driver", "C:\\projects\\test-automation-project\\drivers\\chromedriver.exe");//Chrome Browser
        driver = new ChromeDriver();
        driver.get("http://vk.com");
    }

    @Test
    public void loginVK() throws InterruptedException {
        TestData login = new TestData();
        element = driver.findElement(By.id("index_email"));
        element.sendKeys(login.email);
        element = driver.findElement(By.id("index_pass"));
        element.sendKeys(login.password);
        element = driver.findElement(By.id("index_login_button"));
        element.click();

        Thread.sleep(5000);
    }



    @AfterTest
    public void close() {
        driver.quit();
    }
}


