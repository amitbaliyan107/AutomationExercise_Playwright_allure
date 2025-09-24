package org.example;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HomePage extends BaseClass {

    @BeforeMethod
    public void setup() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://example.com");
    }

    @Test
    public void testLogo() {

        //page.getByAltText("Website for automation practice").click();
        Locator logo = page.locator(
                "xpath=//img[@alt='Website for automation practice']"
        );
    }

}