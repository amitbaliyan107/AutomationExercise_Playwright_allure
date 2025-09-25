package org.TestCases;

import com.microsoft.playwright.Locator;
import org.testng.annotations.*;

public class HomePage extends BaseClass {

    @BeforeMethod
    public void setup() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://automationexercise.com");
    }

    @Test(priority = 1)
    public void testLogo() {

        //page.getByAltText("Website for automation practice").click();
        Locator logo = page.locator(
                "xpath=//img[@alt='Website for automation practice']"
        );

        logo.isVisible();
        System.out.println("Logo is visible");

    }

    @Test(priority = 2)
    public void ScrollToFooter() {
        //page.locator(".fa fa-arrow-circle-o-right").scrollIntoViewIfNeeded();
        Locator arrow = page.locator(".fa.fa-arrow-circle-o-right");
        //arrow.scrollIntoViewIfNeeded();
        arrow.hover();
        arrow.isVisible();
        arrow.isEnabled();
        System.out.println("Scrolled to footer");
    }

    @Test(priority = 3)
    public void ScrollToMiddle() {
        //Locator element = page.locator("//div[@class='single-products']").getByAltText("Lace Top For Women");
        Locator element = page.locator("//div[@class='single-products']")
                        .getByText("Lace Top For Women").nth(1);

        element.scrollIntoViewIfNeeded();
        element.isVisible();
    }

}