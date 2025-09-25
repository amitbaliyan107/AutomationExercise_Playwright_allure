package org.TestCases;

import com.microsoft.playwright.Locator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductPage extends BaseClass {

    @BeforeMethod
    public void setup() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://automationexercise.com/products");
    }

    @Test
    public void SaleOfferImage() {
        Locator offerImage = page.locator("#sale_image");

        offerImage.isVisible();
        System.out.println("Sale offer image is visible");
    }

    @Test
    public void searchProduct() {
        //Locator Searchbox = page.getByPlaceholder("#search_product");
        Locator Searchbox = page.locator("#search_product");
        Searchbox.fill("Top");
        //Locator SearchIcon = page.locator("#submit_search");
        Locator SearchIcon = page.locator(".btn.btn-default.btn-lg");
        SearchIcon.click();

        Locator searchResult = //page.locator("xpath=//div[text()='Summer White Top']");
                page.locator("text=Summer White Top").nth(1);

        //searchResult.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        searchResult.scrollIntoViewIfNeeded();
        searchResult.isVisible();
        System.out.println("Search results are visible");
    }

}
