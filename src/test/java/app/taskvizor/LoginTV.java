package app.taskvizor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Test on 07.06.2017.
 */
public class LoginTV {

    private WebDriver driver;
    private WebElement element;


    @BeforeTest
    public void setup() {
        //System.setProperty("webdriver.gecko.driver", "D:\\Dev\\Projects\\TAF\\drivers\\geckodriver.exe");//Firefox Browser
        System.setProperty("webdriver.chrome.driver", "C:\\projects\\test-automation-project\\drivers\\chromedriver.exe");//Chrome Browser
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://78.47.141.130");
    }

    @Test
    public void loginTV() throws InterruptedException {
        DataLogin login = new DataLogin("evgeny.moshin@tut.by", "123456");
        element = driver.findElement(By.name("email"));
        element.sendKeys(login.email);
        element = driver.findElement(By.name("password"));
        element.sendKeys(login.password);
        element = driver.findElement(By.cssSelector("button"));
        element.click();

        Thread.sleep(5000);
    }


    @AfterTest
    public void close() {
        driver.quit();
    }
}