package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	
	
	@BeforeClass
	public void productInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	//AAA --- Arrange Act Assert(1)
	@Test
	public void productHeaderTest() {
		searchResultsPage =  accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		String actHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, "MacBook Pro");
	}
	
	@Test
	public void productImageCountTest() {
		searchResultsPage =  accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		int actImageCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImageCount, 3);
	}
	
	
	

}