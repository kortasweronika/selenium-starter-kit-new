package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() {
        return new Object[][]{
                {"tomsmith", "SuperSecretPassword!", true},
                {"invalidUser", "wrongPass", false}
        };
    }

    @Test(groups = {"smoke", "login"}, dataProvider = "loginCredentials")
    public void testLogin(String username, String password, boolean shouldSucceed) {
        driver.get("https://the-internet.herokuapp.com/login");

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        WebElement flash = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash")));
        if (shouldSucceed) {
            Assert.assertTrue(flash.getText().contains("You logged into a secure area!"),
                    "Should see success message");
        } else {
            Assert.assertTrue(flash.getText().contains("Your username is invalid!"),
                    "Should see error message");
        }
    }

    @Test(groups = {"regression34", "login","regression-moved-tf"})
    @Parameters({"baseUrl", "newparam"})
    public void testLoginPageElements(@Optional("https://the-internet.herokuapp.com") String baseUrl, String newparam) {
        driver.get(baseUrl + "/login");

        Assert.assertTrue(driver.findElement(By.id("username")).isDisplayed(), "Username field visible");
        Assert.assertTrue(driver.findElement(By.id("password")).isDisplayed(), "Password field visible");
        Assert.assertTrue(driver.findElement(By.cssSelector("button[type='submit']")).isDisplayed(), "Login button visible");
    }
}
