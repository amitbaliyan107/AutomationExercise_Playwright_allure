package org.Utilites;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {

    public static void TakeScreenShot(Page page, String fileName) {
        // Code to take screenshot
        try {
            // Add timestamp to avoid overwriting
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String finalName = fileName + "_" + timestamp + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("screenshots/" + finalName))
                    .setFullPage(false));

            System.out.println("✅ Screenshot saved: screenshots/" + finalName);
        } catch (Exception e) {
            System.out.println("❌ Failed to take screenshot: " + e.getMessage());
        }
    }
}
