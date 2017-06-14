package app.taskvizor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Test on 07.06.2017.
 */
public class TaskVizor {

    private WebDriver driver;
    private WebElement emailInput;
    private WebElement passInput;
    private WebElement buttonSubmit;
    private WebElement btnRoute;
    private WebElement createRouteBtn;
    private WebElement selectElem;
    private WebElement datefromInput;
    private WebElement datetoInput;
    private WebElement btnOK;
    private WebElement addRouteForUserBtn;
    private WebElement pointInput;
    private WebElement pointName;
    private WebElement createTaskBtn;
    private SoftAssert verify = new SoftAssert();
    private Assertion assertion = new Assertion();
    private WebDriverWait wait;

    @BeforeTest
    public void setup() {
        //System.setProperty("webdriver.gecko.driver", "D:\\Dev\\Projects\\TAF\\drivers\\geckodriver.exe");//Firefox Browser
        System.setProperty("webdriver.chrome.driver", "C:\\projects\\test-automation-project\\drivers\\chromedriver.exe");//Chrome Browser
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://addr.com");
    }

    @Test
    public void loginTV() throws InterruptedException {
        DataLogin login = new DataLogin();
        emailInput = driver.findElement(By.name("email"));
        assertion.assertTrue(emailInput.isDisplayed());
        login.setEmail("email");
        emailInput.sendKeys(login.getEmail());
        passInput = driver.findElement(By.name("password"));
        assertion.assertTrue(passInput.isDisplayed());
        login.setPassword("password");
        passInput.sendKeys(login.getPassword());
        buttonSubmit = driver.findElement(By.cssSelector("button"));
        assertion.assertTrue(buttonSubmit.isDisplayed());
        buttonSubmit.click();
    }

    @Test(dependsOnMethods = "loginTV")
    public void createRoute() throws InterruptedException {
        //Thread.sleep(5000);
        List<WebElement> userRoutes = driver.findElements(By.xpath("//section[contains(@class, 'routes-list-group')]"));
        List<String> routeIdUser = new ArrayList<>();
        System.out.println("size: " + userRoutes.size());
        for (WebElement route : userRoutes) {
            System.out.println(route.getText());
            WebElement myRoute = route.findElement(By.xpath("//div//section[@class = 'routes-list-group ng-scope']"));
            String tmpData = myRoute.getText();
            System.out.println(tmpData);
            if (myRoute.getText().contains("Ларютина")) {
                routeIdUser.add(route.getAttribute("route-id"));
                System.out.println("route :" + routeIdUser);
            }
        }
        btnRoute = driver.findElement(By.xpath("//section[contains(@class, 'routes-options-panel')]//a/span"));
        assertion.assertTrue(btnRoute.isDisplayed());
        btnRoute.click();
        createRouteBtn = driver.findElement(By.xpath("//a[contains(text(), 'Создать маршрут')]"));
        assertion.assertTrue(btnRoute.isDisplayed());
        createRouteBtn.click();
        selectElem = driver.findElement(By.xpath("//select[@ng-model='modalForm.owner']"));
        assertion.assertTrue(selectElem.isDisplayed());
        Select ownerSelect = new Select(selectElem);
        ownerSelect.selectByVisibleText("Ларютина Евгения");
        datefromInput = driver.findElement(By.name("dateFrom"));
        assertion.assertTrue(datefromInput.isDisplayed());
        LocalDate dateNow = LocalDate.now();
        assertion.assertEquals(datefromInput.getAttribute("value"), dateNow.toString());
        datetoInput = driver.findElement(By.name("dateTo"));
        assertion.assertEquals(datetoInput.getAttribute("value"), dateNow.toString());
        btnOK = driver.findElement(By.xpath("//button[contains(text(), 'OK')]"));
        assertion.assertTrue(btnOK.isDisplayed());
        btnOK.click();
        Thread.sleep(5000);

        userRoutes = driver.findElements(By.xpath("//div[@class='visits-container-overflow']//span[contains(text(),'Ларютина')]"));
        List<String> routeIdUserNew = new ArrayList<>();
        for (WebElement routeId : userRoutes) {//по правилам здесь долно быть в ед. числе. Не routes, а route. Так как выборка идет по ОДНОМУ элементу
            routeIdUserNew.add(routeId.getAttribute("route_id"));
            System.out.println(routeId.getAttribute("route_id"));
        }
        boolean newRoute = routeIdUser.containsAll(routeIdUserNew);
    }

    /* @Test(dependsOnMethods = "createRoute")
     public void createTask() throws InterruptedException {
         String currentUser = "Ларютина";
         List<WebElement> routes = driver.findElements(By.xpath("//div[@class='visits-container-overflow']//span[contains(@class, 'employee-part-in-title-route')]"));//получаем количество записей маршрутов с текущей фамилией
         int element = 1;
         int userRouteLastNum = 0;
         WebElement last = routes.get(routes.size() - 1);
         for (WebElement route : routes) {
             if (route.getText().contains(currentUser)) {
                 userRouteLastNum = element;
             }
             element++;
         }
         */
        /*for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getText().contains(currentUser))
                userRouteLastNum = i;
        }*/
       /*
        addRouteForUserBtn = driver.findElement(By.xpath("//div[@class='visits-container-overflow']//section[" + userRouteLastNum + "]//span[@title='Добавить задачу']"));
        addRouteForUserBtn.click();
        Thread.sleep(5000);

        pointInput = driver.findElement(By.xpath("//input[@ng-model = 'modalForm.selectedPoint']"));
        assertion.assertTrue(pointInput.isDisplayed());
        pointInput.sendKeys("Martin");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'custom-popup-wrapper ng-isolate-scope']")));

        Thread.sleep(5000);

        pointName = driver.findElement(By.xpath("//strong[contains(text(), 'Martin')]/parent::*"));
        pointName.click();
        selectElem = driver.findElement(By.xpath("//select[@ng-model='modalForm.taskKind']"));
        assertion.assertTrue(selectElem.isDisplayed());
        Select taskKindSelect = new Select(selectElem);
        taskKindSelect.selectByVisibleText("Элементы");
        selectElem = driver.findElement(By.xpath("//select[@ng-model='modalForm.taskType']"));
        assertion.assertTrue(selectElem.isDisplayed());
        Select taskTypeSelect = new Select(selectElem);
        taskTypeSelect.selectByVisibleText("QR код");
        btnOK = driver.findElement(By.xpath("//button[contains(text(), 'OK')]"));
        assertion.assertTrue(btnOK.isDisplayed());
        btnOK.click();
        Thread.sleep(5000);
    }

*/
    @AfterTest
    public void close() {
        driver.quit();
    }
}
