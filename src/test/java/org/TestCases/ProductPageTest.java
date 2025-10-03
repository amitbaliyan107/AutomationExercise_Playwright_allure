package org.TestCases;

import com.microsoft.playwright.*;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;

import static org.testng.Assert.*;

public class ProductPageTest extends BaseClass {
    private static final String PRODUCTS_PAGE_URL = "https://automationexercise.com/products";

    @BeforeMethod
    @Step("Navigate to products page and wait for load")
    public void setup() {
        // Check if page is active before using it
        if (!isPageActive()) {
            throw new IllegalStateException("Page is not active - cannot proceed with test");
        }

        try {
            page.navigate(PRODUCTS_PAGE_URL);
            page.waitForLoadState(LoadState.NETWORKIDLE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to products page: " + e.getMessage(), e);
        }
    }

    @Test(priority = 1)
    @Story("Product Listing")
    @Feature("Products")
    @Description("Verify that all products are visible on the products page")
    @Severity(SeverityLevel.CRITICAL)
    public void testProductListingPage() {
        Assert.assertTrue(isPageActive(), "Page should be active for test execution");

        try {
            // Wait for products to load
            page.waitForSelector(".features_items",
                    new WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

            // Verify products are displayed
            Locator products = page.locator(".single-products");
            int productCount = products.count();
            assertTrue(productCount > 0, "No products found on the page");

            // Take screenshot for Allure report
            Allure.addAttachment("Product Listing",
                    "Found " + productCount + " products on the page");

        } catch (Exception e) {
            Allure.addAttachment("Error Screenshot", "image/png",
                    new ByteArrayInputStream(page.screenshot(new Page.ScreenshotOptions()
                            .setPath(java.nio.file.Path.of("screenshots/product-listing-error.png"))
                            .setFullPage(true))), ".png");
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    @Story("Product Details")
    @Feature("Products")
    @Description("Verify that product details are displayed correctly when viewing a single product")
    @Severity(SeverityLevel.NORMAL)
    public void testProductDetailsPage() {
        Assert.assertTrue(isPageActive(), "Page should be active for test execution");

        try {
            // Click on first product
            page.locator(".single-products:first-child .product-overlay a").first().click();

            // Wait for product details to load
            page.waitForSelector(".product-information",
                    new WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

            // Verify product details are displayed
            assertTrue(page.isVisible(".product-information h2"), "Product name not displayed");
            assertTrue(page.isVisible(".product-information .price"), "Product price not displayed");

            // Take screenshot for Allure report
            Allure.addAttachment("Product Details", "image/png",
                    new ByteArrayInputStream(page.screenshot(new Page.ScreenshotOptions()
                            .setPath(java.nio.file.Path.of("screenshots/product-details.png"))
                            .setFullPage(true))), ".png");

        } catch (Exception e) {
            Allure.addAttachment("Error Screenshot", "image/png",
                    new ByteArrayInputStream(page.screenshot(new Page.ScreenshotOptions()
                            .setPath(java.nio.file.Path.of("screenshots/product-details-error.png"))
                            .setFullPage(true))), ".png");
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    @Story("Sale Offer")
    @Feature("Products")
    @Description("Verify sale offer image is visible on the products page")
    @Severity(SeverityLevel.MINOR)
    public void testSaleOfferImage() {
        Assert.assertTrue(isPageActive(), "Page should be active for test execution");

        try {
            // Scroll to the sale offer section
            Locator saleOffer = page.locator("#sale_image");
            saleOffer.scrollIntoViewIfNeeded();

            // Verify sale offer image is visible
            assertTrue(saleOffer.isVisible(), "Sale offer image is not visible");

            // Take screenshot for Allure report
            Allure.addAttachment("Sale Offer", "image/png",
                    new ByteArrayInputStream(page.screenshot(new Page.ScreenshotOptions()
                            .setPath(java.nio.file.Path.of("screenshots/sale-offer.png"))
                            .setFullPage(true))), ".png");

        } catch (Exception e) {
            Allure.addAttachment("Error Screenshot", "image/png",
                    new ByteArrayInputStream(page.screenshot(new Page.ScreenshotOptions()
                            .setPath(java.nio.file.Path.of("screenshots/sale-offer-error.png"))
                            .setFullPage(true))), ".png");
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}