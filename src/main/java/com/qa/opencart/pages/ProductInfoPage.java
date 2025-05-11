package com.qa.opencart.pages;

import com.qa.opencart.logger.Log;
import com.qa.opencart.reports.ExtentReportListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;

	// 1. private By locators: OR
	private final By productHeader = By.tagName("h1");
	private final By productImages = By.cssSelector("ul.thumbnails img");

	// 2. public page constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	
	public String getProductHeader(){
		String header = eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_TIMEOUT).getText();
		Log.info("product header: "+ header);
		ExtentReportListener.logInfo("product header: "+ header);
		return header;
	}
	
	public int getProductImagesCount() {
		int imageCount = 
				eleUtil.waitForAllElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_TIMEOUT).size();
		Log.info("Total number of images: " + imageCount);
		ExtentReportListener.logInfo("Total number of images: " + imageCount);
		return imageCount;
		
	}
	
	

}