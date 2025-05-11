package com.qa.opencart.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.logger.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
   // private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();// if we dont want parent child structure then we will use this
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> childTest = new ThreadLocal<>();


    private static final String REPORT_DIR = System.getProperty("user.dir") + "/Reports/TestReport/";
    private static final String TIMESTAMP = new SimpleDateFormat("dd_MMMM_yyyy_HHmmss").format(new Date());
    private static final String REPORT_NAME = TIMESTAMP + "_Opencart_Automation_report.html";
    private static final String REPORT_PATH = REPORT_DIR + REPORT_NAME;

    // Singleton ExtentReports instance
    private static ExtentReports createInstance() {
        File reportDir = new File(REPORT_DIR);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle(REPORT_NAME);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setReportName("Automation Report");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System details
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Host Name", getHostName());
        extent.setSystemInfo("Environment", System.getProperty("env", "prod"));

        return extent;
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

//    public static void createTest(String testName) {
//        ExtentTest extentTest = extent.createTest(testName);
//        test.set(extentTest);
//    }

    /** Create a test with the class name and method name to get parent-child structure
     * **/
    public static void createTest(ITestResult result) {
        String className = result.getTestClass().getName();  // Full class name
        String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
        String methodName = result.getMethod().getMethodName();

        ExtentTest parent = parentTest.get();
        if (parent == null || !parent.getModel().getName().equals(simpleClassName)) {
            parent = extent.createTest(simpleClassName);
            parentTest.set(parent);
        }

        ExtentTest child = parent.createNode(methodName);
        childTest.set(child);
    }

    // This method is used to get the current test instance
    public static ExtentTest getTest() {
        return childTest.get();
    }


//    public static ExtentTest getTest() {
//        return test.get();
//    }
    public static void logInfo(String message) {
        if (getTest() != null){
            getTest().info(message);
        }
    }

    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        }
    }

    public static void logFail(String message) {
        if (getTest() != null){
            getTest().fail(message);
        }

    }
    public static void logWarning(String message) {
        if (getTest() != null){
            getTest().warning(message);
        }
    }

    public static void logSkip(String message) {
        if (getTest() != null){
            getTest().skip(message);
        }
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown Host";
        }
    }

    // Listener methods

    @Override
    public void onStart(ITestContext context) {
        Log.info("*** Test Suite " + context.getName() + " started ***");
        getInstance(); // Ensure report is initialized
    }

    @Override
    public void onFinish(ITestContext context) {
        Log.info("*** Test Suite " + context.getName() + " ending ***");
        flushReports();
    }

//    @Override
//    public void onTestStart(ITestResult result) {
//        String testName = result.getMethod().getMethodName();
//        createTest(testName);
//        Log.info("*** Starting test: " + testName + " ***");
//        logInfo("Test started: " + result.getMethod().getMethodName());
//    }

        @Override
        public void onTestStart(ITestResult result) {
            createTest(result);
            Log.info(">>> Test Started: " + result.getMethod().getMethodName());
            logInfo("Test started: " + result.getMethod().getMethodName());
        }



    @Override
    public void onTestSuccess(ITestResult result) {
        Log.info("*** Test passed: " + result.getMethod().getMethodName() + " ***");
        logPass("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Log.error("*** Test failed: " + testName + " ***");
        logFail("Test failed: " + result.getThrowable());

        // Attempt to capture a file-based screenshot (currently commented out)
        // This section for capturing file-based screenshots is not currently working but will be revisited and potentially updated in the future.
        // try {
        //     File screenshotFile = DriverFactory.getScreenshotFile(testName);
        //
        //     if (screenshotFile != null && screenshotFile.exists()) {
        //         String relativePath = "./screenshots/" + screenshotFile.getName();
        //         getTest().addScreenCaptureFromPath(relativePath, testName);
        //         Log.info("Attached screenshot from file: " + relativePath);
        //     } else {
        // If file-based screenshot fails or is not found, fallback to Base64 screenshot
        try{
            Log.warn("Screenshot file not found. Falling back to base64 screenshot.");
            String base64Screenshot = DriverFactory.getScreenshotBase64();
            getTest().addScreenCaptureFromBase64String(base64Screenshot, testName);
            Log.info("Attached screenshot using Base64 for test: " + testName);

        } catch (Exception e) {
            Log.error("Error while attaching screenshot for test '" + testName + "': " + e.getMessage());
            e.printStackTrace();
        }
        //     }
        // } catch (Exception e) {
        //     Log.error("Error during file-based screenshot capture for test '" + testName + "': " + e.getMessage());
        //     e.printStackTrace();
        // }
    }




    @Override
    public void onTestSkipped(ITestResult result) {
        Log.warn("*** Test skipped: " + result.getMethod().getMethodName() + " ***");
        logSkip("Test skipped: " + result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Log.warn("*** Test partially passed: " + result.getMethod().getMethodName() + " ***");
        logWarning("Test partially passed: " + result.getMethod().getMethodName());
    }
}
