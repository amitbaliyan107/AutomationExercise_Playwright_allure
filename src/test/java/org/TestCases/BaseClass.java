package org.TestCases;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

public class BaseClass {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected String traceName;
    protected String tracePath;

    @BeforeSuite
    public void launchBrowser() {
        playwright = Playwright.create();

        // Check if running in Docker
        boolean isDocker = System.getenv("DOCKER_CONTAINER") != null;
        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless",
                        System.getenv().getOrDefault("HEADLESS", "true"))
        );

        String browserType = System.getProperty("browser",
                System.getenv().getOrDefault("BROWSER", "chromium"));

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless);

        // Add Docker-specific arguments
        if (isDocker || isHeadless) {
            launchOptions.setArgs(java.util.Arrays.asList(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--disable-web-security",
                    "--allow-running-insecure-content",
                    "--window-size=1920,1080"
            ));
        }

        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;
            default:
                browser = playwright.chromium().launch(launchOptions);
        }

        System.out.println("🌐 Browser launched: " + browserType +
                " (Headless: " + isHeadless + ", Docker: " + isDocker + ")");
    }

    // ... rest of your BaseClass methods remain the same
    @BeforeMethod
    public void createContextAndPage(ITestResult result) {
        traceName = result.getMethod().getMethodName() + "_" + System.currentTimeMillis();
        tracePath = "test-results/traces/" + traceName + ".zip";

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setIgnoreHTTPSErrors(true)
        );

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
                .setName(traceName)
        );

        page = context.newPage();
        page.setDefaultTimeout(30000);
    }

    @AfterMethod
    public void closeContext(ITestResult result) {
        try {
            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get(tracePath))
                );

                if (result.getStatus() == ITestResult.FAILURE) {
                    attachArtifactsToAllure();
                }

                context.close();
            }
        } catch (Exception e) {
            System.err.println("Error during context cleanup: " + e.getMessage());
        }
    }

    private void attachArtifactsToAllure() {
        try {
            byte[] traceBytes = java.nio.file.Files.readAllBytes(Paths.get(tracePath));
            Allure.addAttachment("Playwright Trace", "application/zip",
                    new ByteArrayInputStream(traceBytes), ".zip");

            if (page != null && !page.isClosed()) {
                byte[] screenshot = page.screenshot();
                Allure.addAttachment("Screenshot on Failure", "image/png",
                        new ByteArrayInputStream(screenshot), ".png");
            }
        } catch (Exception e) {
            System.err.println("Failed to attach artifacts: " + e.getMessage());
        }
    }

    @AfterSuite
    public void closeBrowser() {
        try {
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        } catch (Exception e) {
            System.err.println("Error closing browser: " + e.getMessage());
        }
    }

    // Helper method to check if context is active
    protected boolean isContextActive() {
        return context != null;
    }

    // Helper method to check if page is active
    protected boolean isPageActive() {
        return page != null && !page.isClosed();
    }
}