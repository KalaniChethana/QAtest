package com.example.demo.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddTaskUITest extends BaseSeleniumTest {

    @Test
    void testAddNewTask() {
        driver.get("http://localhost:8080/login");

        // Login first
        driver.findElement(By.name("username")).sendKeys("chamsha");
        driver.findElement(By.name("password")).sendKeys("chamsha");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Fill in task details directly
        WebElement taskName = driver.findElement(By.name("title"));
        WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit']"));

        taskName.sendKeys("Write Selenium Tests");
        saveButton.click();

        // Verify task appears on page
        assertTrue(driver.getPageSource().contains("Write Selenium Tests"));
    }

}
