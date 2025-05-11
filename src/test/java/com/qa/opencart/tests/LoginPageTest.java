package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.qa.opencart.reports.ExtentReportListener.*;
import com.qa.opencart.base.BaseTest;

import static com.qa.opencart.constants.AppConstants.*;


public class LoginPageTest extends BaseTest{




	@Test
	public void loginPageTitleTest() {
		try {
			String actTitle = loginPage.getLoginPageTitle();
			logInfo("Actual login page title: " + actTitle);
			Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
			logPass("Login page title matched expected: " + LOGIN_PAGE_TITLE);
		} catch (AssertionError e) {
			logFail("Login page title mismatch: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void loginPageURLTest() {
		try {
			String actURL = loginPage.getLoginPageURL();
			logInfo("Actual login page URL: " + actURL);
			Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
			logPass("Login page URL contains expected fraction: " + LOGIN_PAGE_FRACTION_URL);
		} catch (AssertionError e) {
			logFail("Login page URL mismatch: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void forgotPwdLinkExistTest() {
		try {
			boolean isDisplayed = loginPage.isForgotPwdLinkExist();
			logInfo("Forgot password link visible: " + isDisplayed);
			Assert.assertTrue(isDisplayed);
			logPass("Forgot password link is present.");
		} catch (AssertionError e) {
			logFail("Forgot password link is not present: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = Short.MAX_VALUE)
	public void doLoginTest() {
		try {
			accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
			String actualTitle = accPage.getAccPageTitle();
			logInfo("Account page title after login: " + actualTitle);
			Assert.assertEquals(actualTitle, HOME_PAGE_TITLE);
			logPass("Successfully logged in. Home page title matched: " + HOME_PAGE_TITLE);
		} catch (AssertionError e) {
			logFail("Login failed or Home page title mismatch: " + e.getMessage());
			throw e;
		}
	}

	

}