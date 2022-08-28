package selenium.automation.methods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import selenium.automation.driver.Driver;

import java.time.Duration;


public class Methods {

    private static final Logger logger = LogManager.getLogger(Methods.class);
    public WebDriver driver;
    FluentWait<WebDriver> fluentWait;
    JavascriptExecutor jsDriver;
    public Methods(){
        this.driver = Driver.driver;
        jsDriver = (JavascriptExecutor) driver;
        fluentWait = setFluentWait(30);
    }

    public FluentWait<WebDriver> setFluentWait(long timeout){
         return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class);
    }

    public WebElement findElementWait(By by){
        return fluentWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void clickElement(By by){
        findElementWait(by).click();
        waitBySeconds(1);
        logger.info("Clicked on " + by.toString());
    }

    public void sendKeys(By by, String text){
        findElementWait(by).sendKeys(text);
        logger.info("Text input -> Input element : " + by.toString() + " text :  " + text);
    }

    public String getText(By by){
        return findElementWait(by).getText();
    }

    public String getAttribute(By by, String attribute){
        return findElementWait(by).getAttribute(attribute);
    }

    public void hoverElement(By by){
        WebElement webElement = findElementWait(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).build().perform();
        logger.info("Hovered : " + by.toString());
    }

    public void clickElementJs(By by){
        WebElement webElement = findElementWait(by);
        jsDriver.executeScript("arguments[0].click();", webElement);
        logger.info("Clicked on " + by.toString() + " with js");
    }

    public int countListSize(String xPathOfParent){
        return driver.findElements(By.xpath(xPathOfParent)).size()-1;
    }

    public void scrollElementIfNeeded(By by){

        WebElement webElement = findElementWait(by);
        jsDriver.executeScript("arguments[0].scrollIntoViewIfNeeded();", webElement);
        logger.info(by.toString() + " scrollElementIfNeeded");
    }

    public boolean isElementVisible(By by, long timeout){
        try {
            setFluentWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
            logger.info(by.toString() + " true");
            return true;
        }catch (Exception e){
            logger.info(by.toString() + " false");
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean isElementClickable(By by, long timeout){
        try {
            setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(by));
            logger.info(by.toString() + " true");
            return true;
        }catch (Exception e){
            logger.info(by.toString() + " false");
            logger.error(e.getMessage());
            return false;
        }
    }

    public void waitBySeconds(long seconds){
        waitByMilliSeconds(1000*seconds);
        logger.info("Waited " + seconds + " seconds");
    }
    public void waitByMilliSeconds(long milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (milliSeconds % 1000 != 0)
            logger.info("Waited " + milliSeconds + " milliseconds");
    }
    
}
