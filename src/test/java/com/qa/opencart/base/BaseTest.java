package com.qa.opencart.base;

import java.util.Properties;

import com.qa.opencart.pages.*;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.qa.opencart.factory.DriverFactory;
import org.testng.annotations.Parameters;

public class BaseTest {
	
	WebDriver driver;
	
	DriverFactory df;
	protected Properties prop;
	
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;

	@Description("init the driver and properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();

		//if the browser name is passed from xml
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}

		driver = df.initDriver(prop);//login page
		loginPage = new LoginPage(driver);
	}


	@Description("closing the browser..")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	

}