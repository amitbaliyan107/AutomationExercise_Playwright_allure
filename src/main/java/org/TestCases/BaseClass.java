package org.example;


import com.microsoft.playwright.*;
import org.testng.annotations.BeforeSuite;


public class BaseClass {

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeSuite
    public void beforeSuite() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

    }


}
