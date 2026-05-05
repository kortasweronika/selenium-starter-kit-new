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

    @TestFactoryMethod(value = "testPageTitle", group = "zmiana89842")
    @Test(groups = {"hk8734"}, dataProvider = "pages")
    @Parameters({"url", "expectedHeading", "expectedText"})
    public void testPageTitle(String url, String expectedHeading, String expectedText) {
        driver.get(url);

        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        Assert.assertTrue(heading.getText().contains(expectedHeading),
                "Page heading should contain: " + expectedHeading);
    }

    @TestFactoryMethod(value = "testLink", group = "zmiana89842")
    @Test()
//    @Parameters({"baseUrl"})
    public void testLinksPresent() {
        driver.get("https://the-internet.herokuapp.com/");

        List<WebElement> links = driver.findElements(By.cssSelector("ul li a"));
        Assert.assertTrue(links.size() > 10, "Main page should have many example links");
    }
}
