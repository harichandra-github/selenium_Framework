package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {
	
	public static String configFile ="./src/test/resources/config/config.properties";
	
	public static final int DEFAULT_TIMEOUT = 5;
	public static final int MEDIUM_DEFAULT_TIMEOUT = 10;
	public static final int LONG_DEFAULT_TIMEOUT = 15;

	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String HOME_PAGE_TITLE = "My Account";
	
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String HOME_PAGE_FRACTION_URL = "route=account/account";
	
	public static List<String> expectedAccPageHeadersList = List.of("My Account", 
																	"My Orders",
																	"My Affiliate Account",
																	"Newsletter");
	public static final String REGISTER_SUCCESS_MESSG = "Your Account Has Been Created!";


	//************Sheet Name********/
	public static final String REGISTER_SHEET_NAME = "register";
	public static final String PRODUCT_SHEET_NAME = "product";

	public static final String CONFIG_QA_FILE_PATH="./src/test/resources/configuration/qa.config.properties";
	public static final String CONFIG_PROD_FILE_PATH="./src/test/resources/configuration/config.properties";
	public static final String CONFIG_UAT_FILE_PATH="./src/test/resources/configuration/uat.config.properties";
	public static final String CONFIG_STAG_FILE_PATH="./src/test/resources/configuration/stag.config.properties";
	public static final String CONFIG_DEV_FILE_PATH="./src/test/resources/configuration/dev.config.properties";
}
