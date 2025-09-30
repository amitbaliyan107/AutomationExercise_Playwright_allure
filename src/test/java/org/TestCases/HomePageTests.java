package org.TestCases;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class HomePageTests extends BaseClass {

    private static final String BASE_URL = "https://automationexercise.com";

    @BeforeMethod
    @Step("Navigate to homepage and wait for load")
    public void setup() {
        // Check if page is active before using it
        if (!isPageActive()) {
            throw new IllegalStateException("Page is not active - cannot proceed with test");
        }

        try {
            page.navigate(BASE_URL);
            page.waitForLoadState(LoadState.NETWORKIDLE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to homepage: " + e.getMessage(), e);
        }
    }

    @Test(priority = 1)
    @Story("Homepage Visual Elements")
    @Feature("Homepage")
    @Description("Verify company logo is visible on homepage")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogoVisibility() {
        // Verify page is active
        Assert.assertTrue(isPageActive(), "Page should be active for test execution");

        Locator logo = page.locator("img[alt='Website for automation practice']");
        assertTrue(logo.isVisible(), "Company logo should be visible");

        Allure.addAttachment("Logo Check", "Logo is successfully displayed on homepage");
    }

    @Test(priority = 2)
    @Story("Homepage Navigation")
    @Feature("Homepage")
    @Description("Verify ability to scroll to footer section")
    @Severity(SeverityLevel.MINOR)
    public void testScrollToFooter() {
        Assert.assertTrue(isPageActive(), "Page should be active for test execution");

        Locator footerArrow = page.locator(".fa.fa-arrow-circle-o-right");
        footerArrow.scrollIntoViewIfNeeded();

        assertTrue(footerArrow.isVisible(), "Footer arrow should be visible after scrolling");
        Allure.addAttachment("Footer Scroll", "Successfully scrolled to footer section");
    }

    @Test(priority = 3)
    @Story("Homepage Products")
    @Feature("Homepage")
    @Description("Verify product section is accessible by scrolling")
    @Severity(SeverityLevel.NORMAL)
    public void testScrollToProductSection() {
        Assert.assertTrue(isPageActive(), "Page should be active for test execution");

        // Use more reliable locator
        Locator productTitle = page.getByText("Lace Top For Women").first();
        productTitle.scrollIntoViewIfNeeded();

        assertTrue(productTitle.isVisible(), "Product title should be visible after scrolling");
        Allure.addAttachment("Product Section", "Successfully scrolled to product section");
    }
}