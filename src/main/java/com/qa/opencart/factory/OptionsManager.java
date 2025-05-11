package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Properties;

public class OptionsManager {

    private Properties prop;

    public OptionsManager(Properties prop) {
        this.prop = prop;
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions co = new ChromeOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            System.out.println("---Running in headless mode----");
            co.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            System.out.println("---Running in incognito mode----");
            co.addArguments("--incognito");
        }
        return co;
    }

    public FirefoxOptions getFirefoxOptions() {
        FirefoxOptions fo = new FirefoxOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            System.out.println("---Running in headless mode----");
            fo.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            System.out.println("---Running in incognito mode----");
            fo.addArguments("--incognito");
        }
        return fo;
    }

    public EdgeOptions getEdgeOptions() {
        EdgeOptions eo = new EdgeOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            System.out.println("---Running in headless mode----");
            eo.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            System.out.println("---Running in incognito mode----");
            eo.addArguments("--inprivate");
        }
        return eo;
    }
}
