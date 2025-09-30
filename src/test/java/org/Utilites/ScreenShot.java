package org.Utilites;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.qameta.allure.Attachment;

public class ScreenShot {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] TakeScreenShot(Page page, String fileName) {
        try {
            // Add timestamp to avoid overwriting
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String finalName = fileName + "_" + timestamp + ".png";

            // Take screenshot and save to file
            byte[] screenshotBytes = page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(Paths.get("screenshots/" + finalName)) // ✅ saves file
                            .setFullPage(false) // full page screenshot
            );

            System.out.println("✅ Screenshot saved: screenshots/" + finalName);
            return screenshotBytes; // ✅ return bytes for Allure attachment
        } catch (Exception e) {
            System.out.println("❌ Failed to take screenshot: " + e.getMessage());
            return new byte[0];
        }
    }
}
