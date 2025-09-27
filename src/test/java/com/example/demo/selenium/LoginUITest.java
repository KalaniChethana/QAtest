package com.example.demo.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginUITest extends BaseSeleniumTest {

    @Test
    void testLoginWithValidCredentials() {
        driver.get("http://localhost:8080/login"); // adjust path

        // Enter username and password
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("chamsha");
        passwordField.sendKeys("chamsha");
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement tasksPage = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );

        // Validate redirect to dashboard
        assertTrue(driver.getCurrentUrl().contains("/tasks"));
    }
}
