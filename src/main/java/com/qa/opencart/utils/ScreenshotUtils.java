package com.qa.opencart.utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    // 1️⃣ Get Screenshot as File and Save to Disk
    public static String getScreenshotFile(WebDriver driver, String testName) {
        String timestamp =  new SimpleDateFormat("dd_MMMM_yyyy_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + "/Reports/Screenshots/";
        String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);

        try {
            new File(screenshotDir).mkdirs(); // Ensure the directory exists
            Files.copy(srcFile.toPath(), destFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshotPath;
    }

    // 2️⃣ Get Screenshot as byte[]
    public static byte[] getScreenshotAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // 3️⃣ Get Screenshot as Base64
    public static String getScreenshotAsBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
