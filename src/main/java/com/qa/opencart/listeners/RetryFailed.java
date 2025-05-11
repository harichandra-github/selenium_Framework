package com.qa.opencart.listeners;

import com.qa.opencart.logger.Log;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


import java.io.IOException;


public class RetryFailed implements IRetryAnalyzer {




    int counter=1;
    int maxRetryCount=3;

    public RetryFailed() throws IOException {
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (counter < maxRetryCount) {
                counter++;
                Log.info("Retrying test: " + result.getName() + " | Attempt: " + counter);
                result.setStatus(ITestResult.FAILURE); // Let TestNG know it's still failing
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE); // Mark as final failure
            }
        } else {
            result.setStatus(ITestResult.SUCCESS); // Mark as passed
        }
        return false;
    }
}
