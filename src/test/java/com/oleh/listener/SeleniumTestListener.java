package com.oleh.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class SeleniumTestListener implements ITestListener {
    private static final Logger LOG = LogManager.getLogger(SeleniumTestListener.class);

    private static final String ERROR_SYMBOL = " *********";
    private static final String TEST_CASE_FAILURE = "***** Test FAILED : ";
    private static final String TEST_START = "Starting test case : ";
    private static final String TEST_SUCCESS = "Test PASSED : ";
    private static final String TEST_SKIPPED = "Test SKIPPED ";

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info(TEST_START + result.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info(TEST_SUCCESS + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error(TEST_CASE_FAILURE + result.getName() + ERROR_SYMBOL);
        LOG.error(result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn(TEST_SKIPPED + result.getName() + ", " + result.getSkipCausedBy());
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
//        WebDriverProvider.destroyAndClear();
    }
}
