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

import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.account.*;
import com.stormpath.sdk.application.*;

public class stormpathSetSuite {

private String applicationName;

@Parameters({"application-name"})
public stormpathSetSuite(String applicationName) {
        this.applicationName = applicationName;
}

@BeforeSuite
public void setUp() throws Exception {
        // do nothing
}

@AfterSuite
public void removeAllUsers() throws Exception {
        // Clean
        Iterator<Account> it = Clients.builder()
                               .build()
                               .getApplications(Applications
                                                .where(Applications
                                                       .name()
                                                       .eqIgnoreCase(applicationName)))
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
