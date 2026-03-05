package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.b2b.testfactory.annotations.TestFactoryMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.List;

public class BasicPageTest extends BaseTest {

    @DataProvider(name = "pages")
    public Object[][] pages() {
        return new Object[][]{
                {"https://the-internet.herokuapp.com/", "Welcome to the-internet"},
                {"https://the-internet.herokuapp.com/login", "Login Page"},
                {"https://the-internet.herokuapp.com/dropdown", "Dropdown List"}
        };
    }

    @TestFactoryMethod(value = "testPageTitle", group = "hk")
    @Test(groups = {"hk"}, dataProvider = "pages")
    public void testPageTitle(String url, String expectedHeading) {
        driver.get(url);

        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        Assert.assertTrue(heading.getText().contains(expectedHeading),
                "Page heading should contain: " + expectedHeading);
    }

//    @Test()
////    @Parameters({"baseUrl"})
//    public void testLinksPresent() {
//        driver.get("https://the-internet.herokuapp.com/");
//
//        List<WebElement> links = driver.findElements(By.cssSelector("ul li a"));
//        Assert.assertTrue(links.size() > 10, "Main page should have many example links");
//    }

    @Test(groups = {"hk5"})
    @TestFactoryMethod(value = "testLinksPresent", group = "hk5")
    @Parameters({"baseUrl"})
    public void testLinksPresent(@Optional String baseUrl) {
        driver.get(baseUrl);

        List<WebElement> links = driver.findElements(By.cssSelector("ul li a"));
        Assert.assertTrue(links.size() > 10, "Main page should have many example links");
    }

    @TestFactoryMethod(value = "testStatusCodes", group = "hk")
    @Test(groups = {"hk"})
    public void testStatusCodes() {
        driver.get("https://the-internet.herokuapp.com/status_codes");

        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h3")));
        Assert.assertEquals(header.getText(), "Status Codes");

        List<WebElement> codeLinks = driver.findElements(By.cssSelector("a[href*='status_codes/']"));
        Assert.assertEquals(codeLinks.size(), 4, "Should have 4 status code links");
    }
}
