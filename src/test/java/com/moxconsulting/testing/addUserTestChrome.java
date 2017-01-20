package com.moxconsulting.testing;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class addUserTestChrome {
private WebDriver driver;
private String baseUrl;
private boolean acceptNextAlert = true;
private StringBuffer verificationErrors = new StringBuffer();

@Parameters({"hub-node-grid-url", "base-url"})
@BeforeClass
public void setUp(String hubNodeGridUrl, String baseUrl) throws Exception {

        DesiredCapabilities capability = new DesiredCapabilities();
        try {
                driver = new RemoteWebDriver(new URL(hubNodeGridUrl), DesiredCapabilities.chrome());
        }
        catch (Exception e) {
                System.out.println(e);
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.baseUrl = baseUrl;
}

@Test(description="Create new user.")
public void createUser() throws Exception {
        driver.get(baseUrl + "/");

        driver.findElement(By.linkText("Registration")).click();
        driver.findElement(By.id("givenName")).sendKeys("Lorenzo");
        driver.findElement(By.id("surname")).click();
        driver.findElement(By.id("surname")).sendKeys("Lobba");
        driver.findElement(By.id("email")).sendKeys("lorenz3@linux.it");
        driver.findElement(By.id("customData.color")).sendKeys("blue");
        driver.findElement(By.id("password")).sendKeys("Aa123456789");
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        try {
                String ans = "Welcome Lorenzo!";
                Assert.assertEquals(driver.findElement(By.cssSelector("h2.text-center")).getText(), ans);
        } catch (Error e) {
                verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Logout")).click();
}

@Test(description="Try to create new user. Already created")
public void createUserAgain() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Registration")).click();
        driver.findElement(By.id("givenName")).sendKeys("Lorenzo");
        driver.findElement(By.id("surname")).click();
        driver.findElement(By.id("surname")).sendKeys("Lobba");
        driver.findElement(By.id("email")).sendKeys("lorenz3@linux.it");
        driver.findElement(By.id("customData.color")).sendKeys("blue");
        driver.findElement(By.id("password")).sendKeys("Aa123456789");
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        try {
                String ans = "Account with that email already exists. Please choose another email.";
                Assert.assertEquals(driver.findElement(By.cssSelector("p.alert-danger")).getText(), ans);
        } catch (Error e) {
                verificationErrors.append(e.toString());
        }
}

@AfterClass
public void tearDown() throws Exception {
        System.out.println("driver chrome quit");
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
                Assert.fail(verificationErrorString);
        }
}
}
