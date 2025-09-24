package org.TestCases;


import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.Utilites.ScreenShot;

import java.io.File;


public class BaseClass {

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    private static final String SCREENSHOT_DIR = "screenshots";

    @BeforeSuite
    public void beforeSuite() {

        cleanScreenshotsFolder();
        
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

    }

    @AfterSuite
    public void afterSuite() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private void cleanScreenshotsFolder() {
        File folder = new File(SCREENSHOT_DIR);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println("Deleted: " + file.getName());
                        }
                    }
                }
            }
        } else {
            // Create folder if it doesn't exist
            folder.mkdirs();
            System.out.println("Screenshots folder created: " + SCREENSHOT_DIR);
        }
    }

    @AfterMethod
    public void captureScreenshotOnFinish(ITestResult result) {
        try {
            // ✅ Take screenshot first
            if (page != null) {
                ScreenShot.TakeScreenShot(page, result.getMethod().getMethodName());
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to take screenshot: " + e.getMessage());
        } finally {
            // ✅ Then close context
            if (context != null) context.close();
        }
    }

}
