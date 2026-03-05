package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    @Parameters({"headless"})
    public void setUp(@Optional("true") String headless) {
        ChromeOptions opts = new ChromeOptions();
        if (Boolean.parseBoolean(headless)) {
            opts.addArguments("--headless=new");
        }
        opts.addArguments("--disable-gpu", "--no-first-run", "--no-default-browser-check");

        driver = new ChromeDriver(opts);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
