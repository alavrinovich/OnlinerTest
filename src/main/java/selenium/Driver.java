package main.java.selenium;

import main.java.utils.Property;
import main.java.utils.ThreadLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver extends RemoteWebDriver {

    private static ThreadLocal<Driver> webDriverLocal = new ThreadLocal<>();
    private static final int TIMEOUT = 15;

    public Driver(URL urlRemoteServer, DesiredCapabilities desiredCapabilities) {
        super(urlRemoteServer, desiredCapabilities);
        manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        manage().window().maximize();
        get(Property.getProperty("baseUrl"));
    }

    public static Driver getDriver() {
        Driver driver;
        if ((driver = webDriverLocal.get()) == null) {
            try {
                driver = new Driver(new URL(Property.getProperty("serverGrid")), DesiredCapabilities.firefox());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            webDriverLocal.set(driver);
            return driver;
        } else return driver;
    }

    public static Driver getCurrentDriver() {
        return webDriverLocal.get();
    }

    public static void stopDriver(WebDriver driver) {
        if (driver != null) {
            webDriverLocal.remove();
            driver.quit();
        }
    }

    public void waitElementPresentAndVisible(String xpath) {
        WebDriverWait wait = new WebDriverWait(this, TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public boolean isElementPresentAndVisible(String xpath) {
        WebDriverWait wait = new WebDriverWait(this, TIMEOUT);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException exc) {
            return false;
        }
        return true;
    }

    public void waitElementEnabled(String xpath) {
        WebDriverWait wait = new WebDriverWait(this, TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public void waitElementEnabled(WebElement element) {
        WebDriverWait wait = new WebDriverWait(this, TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPageToLoad(int timeout) {
        WebDriverWait webDriverWait = new WebDriverWait(this, timeout);
        webDriverWait.until(new ExpectedCondition<Object>() {
            Object result;

            @Override
            public Object apply(WebDriver driver) {
                String script = "return document.readyState";
                result = ((JavascriptExecutor) driver).executeScript(script);
                if (result != null) {
                    if (!(result instanceof String)) {
                        ThreadLogger.getThreadLogger().log(Level.WARN, String.format("Executing script '%s' return not " +
                                "string result, current type of result is: '%s'", script, result.getClass().getName()));
                        return false;
                    }
                    if (!((String) result).equalsIgnoreCase("complete")) {
                        ThreadLogger.getThreadLogger().log(Level.WARN, String.format("Page is not loaded yet, current " +
                                "value of readyState is: '%s'", result));
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void clickByXpath(String xpath) {
        waitElementPresentAndVisible(xpath);
        waitElementEnabled(xpath);
        findElementByXPath(xpath).click();
    }

    public void typeByXpath(String xpath, String text) {
        waitElementPresentAndVisible(xpath);
        waitElementEnabled(xpath);
        findElementByXPath(xpath).clear();
        if (StringUtils.isEmpty(text)) {
            text = "";
        }
        findElementByXPath(xpath).sendKeys(text);
    }

    public int getElementsCount(String xpath) {
        return findElementsByXPath(xpath).size();
    }

    public String getText(String xpath) {
        waitElementPresentAndVisible(xpath);
        return findElementByXPath(xpath).getText();
    }

    public void click(WebElement element) {
        waitElementEnabled(element);
        element.click();
    }

    public void clickByXpathAndWait(String xpath) {
        clickByXpath(xpath);
        waitForPageToLoad(10);
    }

    public void selectOptionByText(String xpath, String option) {
        String xpathPattern = String.format(xpath + "%s", "/option");
        waitElementPresentAndVisible(xpath);
        waitElementEnabled(xpath);
        int count = getElementsCount(xpathPattern);
        for (int i = 0; i < count; i++) {
            waitElementPresentAndVisible(String.format(xpathPattern + "[%d]", (i + 1)));
            WebElement element = findElementByXPath(String.format(xpathPattern + "[%d]", (i + 1)));
            if (element.getText().equals(option)) {
                click(element);
                break;
            }
        }
    }
}
