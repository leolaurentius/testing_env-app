package com.moxconsulting.testing;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Iterator;

import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.account.*;
import com.stormpath.sdk.application.*;

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

public class addUserTestFirefox {
private WebDriver driver;
private String baseUrl;
private boolean acceptNextAlert = true;
private StringBuffer verificationErrors = new StringBuffer();

@Parameters({"hub-node-grid-url", "base-url"})
@BeforeClass
public void setUp(String hubNodeGridUrl, String baseUrl) throws Exception {

        DesiredCapabilities capability = new DesiredCapabilities();
        try {
                driver = new RemoteWebDriver(new URL(hubNodeGridUrl), DesiredCapabilities.firefox());
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
        driver.findElement(By.id("email")).sendKeys("lorenz@linux.it");
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
        driver.findElement(By.id("email")).sendKeys("lorenz@linux.it");
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
        System.out.println("driver firefox quit");
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
                Assert.fail(verificationErrorString);
        }
}

@AfterSuite
public void removeUsers() throws Exception {
    // Clean
    Iterator<Account> it = Clients.builder()
                                .build()
                                .getApplications(Applications.where(Applications.name().eqIgnoreCase("My Application")))
                                .single()
                                .getAccounts()
                                .iterator();
    while(it.hasNext()) {
            Account cur = it.next();
            System.out.println("bye bye " + cur.getFullName());
            cur.delete();
    }
}
}
