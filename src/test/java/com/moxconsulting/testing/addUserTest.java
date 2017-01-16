package com.moxconsulting.testing;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.File;

import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.account.*;
import com.stormpath.sdk.application.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
// for local firefox
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.firefox.FirefoxBinary;
// import org.openqa.selenium.firefox.FirefoxProfile;
// END FOR LOCAL FIREFOX
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class addUserTest {
private WebDriver driver;
private String baseUrl;
private boolean acceptNextAlert = true;
private StringBuffer verificationErrors = new StringBuffer();

@BeforeSuite
public void setUp() throws Exception {

        Client client = Clients.builder().build();
        System.out.println("\n*******************************\n");
        System.out.println(client);
        System.out.println("\n*******************************\n");

        Application application = client
          .getApplications(Applications.where(Applications.name().eqIgnoreCase("My Application")))
          .single();

        //Create the account object
        Account account = client.instantiate(Account.class);

        //Set the account properties
        account.setGivenName("Joe");
        account.setSurname("Stormtrooper");
        account.setUsername("tk421"); //optional, defaults to email if unset
        account.setEmail("tk421@stormpath.com");
        account.setPassword("Changeme1");

        //Create the account using the existing Application object
        account = application.createAccount(account);

        System.out.println("Hello " + account.getFullName());





        // For GRID
        String hubNodeGridUrl = "http://localhost:4444/wd/hub";
        DesiredCapabilities capability = new DesiredCapabilities();
        try {
                // For local firefox
                // driver = new FirefoxDriver();
                // For GRID
                driver = new RemoteWebDriver(new URL(hubNodeGridUrl), DesiredCapabilities.firefox());
        }
        catch (Exception e) {
                System.out.println(e);
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // For local firefox
        // baseUrl = "http://localhost:3000";
        // For GRID
        baseUrl = "http://172.17.0.1:3000";
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


@AfterSuite
public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
                Assert.fail(verificationErrorString);
        }
}
}
