package com.qa.opencart.pages;

import com.qa.opencart.logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private final By resultsProduct = By.cssSelector("div.product-thumb");
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public int getResultsProductCount() {
		int searchCount = 
				eleUtil.waitForAllElementsVisible(resultsProduct, AppConstants.MEDIUM_DEFAULT_TIMEOUT).size();
		Log.info("total number of search products: "+ searchCount);
		return searchCount;
	}
	
	
	public ProductInfoPage selectProduct(String productName) {
		Log.info("product name: "+ productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
	
	

}