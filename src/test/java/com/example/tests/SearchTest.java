package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class SearchTest extends BaseTest {

    @DataProvider(name = "checkboxStates")
    public Object[][] checkboxStates() {
        return new Object[][]{
                {0, true},   // first checkbox -> check it
                {1, false}   // second checkbox -> uncheck it
        };
    }

    @Test(groups = {"smoke"}, dataProvider = "checkboxStates")
    public void testCheckboxToggle(int index, boolean expectedState) {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        List<WebElement> checkboxes = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[type='checkbox']")));
        WebElement checkbox = checkboxes.get(index);

        if (checkbox.isSelected() != expectedState) {
            checkbox.click();
        }

        Assert.assertEquals(checkbox.isSelected(), expectedState,
                "Checkbox " + index + " should be " + (expectedState ? "checked" : "unchecked"));
    }

    @Test(groups = {"smoke"})
    public void testDropdown() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown")));
        dropdown.findElement(By.cssSelector("option[value='2']")).click();

        WebElement selected = dropdown.findElement(By.cssSelector("option[selected]"));
        Assert.assertEquals(selected.getText(), "Option 2", "Option 2 should be selected");
    }

    @Test(groups = {"regression"})
    public void testAddRemoveElements() {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[onclick='addElement()']")));

        addButton.click();
        addButton.click();
        addButton.click();

        List<WebElement> deleteButtons = driver.findElements(By.cssSelector(".added-manually"));
        Assert.assertEquals(deleteButtons.size(), 3, "Should have 3 delete buttons");

        deleteButtons.get(0).click();
        deleteButtons = driver.findElements(By.cssSelector(".added-manually"));
        Assert.assertEquals(deleteButtons.size(), 2, "Should have 2 delete buttons after removing one");
    }
}
