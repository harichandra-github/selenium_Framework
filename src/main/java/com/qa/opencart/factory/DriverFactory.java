
package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.BrowserException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to initialize the driver on the basis of given browser name
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {

		Log.info("properties: "+ prop);

		String browserName = prop.getProperty("browser");
		//System.out.println("browser name : " + browserName);
		Log.info("browser name : "+browserName);


		optionsManager = new OptionsManager(prop);

		highlight = prop.getProperty("highlight");
		switch (browserName.toLowerCase().trim()) {
			case "chrome":
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				break;
			case "edge":
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				break;
			case "firefox":
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				break;
			case "safari":
				tlDriver.set(new SafariDriver());
				break;
			default:
				Log.error("Plz pass the valid browser name..." +browserName);
				throw new BrowserException("===INVALID BROWSER===");
		}

		getDriver().get(prop.getProperty("url"));// login page url
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
	}

	/**
	 * getDriver: get the local thready copy of the driver
	 */

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this is used to init the config properties
	 *
	 * @return
	 */

	// mvn clean install -Denv="stage"
	public Properties initProp() {

		String envName = System.getProperty("env");
		FileInputStream ip = null;
		prop = new Properties();

		try {
			if (envName == null) {
				//System.out.println("env is null, hence running the tests on QA env by default...");
				Log.warn("env is null, hence running the tests on QA env by default...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				System.out.println("Running tests on env: " + envName);
				Log.info("Running tests on env: " + envName);
				switch (envName.toLowerCase().trim()) {
					case "qa":
						ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
						break;
					case "dev":
						ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
						break;
					case "stag":
						ip = new FileInputStream("./src/test/resources/config/stag.config.properties");
						break;
					case "uat":
						ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
						break;
					case "prod":
						ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
						break;

					default:
						Log.error("----invalid env name---"+ envName);
						throw new FrameworkException("===INVALID ENV NAME==== : "+ envName);
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FrameworkException e) {
            throw new RuntimeException(e);
        }

        try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}


	/**
	 * takescreenshot
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}

