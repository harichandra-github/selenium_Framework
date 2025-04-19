package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtils;

public class AccountsPage{
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private final By headers = By.cssSelector("div#content > h2");
	private final By search = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	public String getAccPageTitle() {
		String title = eleUtil.waitFotTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
		System.out.println("home page title: " + title);
		return title;
	}

	public String getAccPageURL() {
		String url = eleUtil.waitForURLContains(HOME_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		System.out.println("home page url: " + url);
		return url;
	}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headerList = eleUtil.getElements(headers);
		List<String> headerValList = new ArrayList<String>();
		for(WebElement e: headerList) {
			String text = e.getText();
			headerValList.add(text);
		}
		System.out.println("Acc page headers: " + headerValList);
		return headerValList;
	}
	
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("search key: "+ searchKey);
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
		
	
	
}