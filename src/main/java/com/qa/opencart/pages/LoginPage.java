package com.qa.opencart.pages;

import com.qa.opencart.logger.Log;
import com.qa.opencart.reports.ExtentReportListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtils;

import static com.qa.opencart.constants.AppConstants.*;

public class LoginPage {
	private WebDriver driver;
	private ElementUtils eleUtil;

	// 1. private By locators: OR
	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By registerLink = By.linkText("Register");

	// 2. public page constr...
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	// 3. public page actions/methods
	public String getLoginPageTitle() {
		String title = eleUtil.waitFotTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
		Log.info("login page title: " + title);
		ExtentReportListener.logInfo("login page title: " + title);
		return title;
	}

	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		Log.info("login page url: " + url);
		ExtentReportListener.logInfo("login page url: " + url);
		return url;
	}

	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}

	public AccountsPage doLogin(String username, String pwd) {
		Log.info("user credentials: " + username + ":" + pwd);
		eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		//after clicking on login button ---> landing on Accounts Page
		//responsible to return the AccountsPage class object
		return new AccountsPage(driver);
	}


	public RegisterPage navigateToRegisterPage() {
		eleUtil.clickWhenReady(registerLink, DEFAULT_TIMEOUT);
		return new RegisterPage(driver);
	}
}