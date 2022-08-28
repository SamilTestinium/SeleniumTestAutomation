package selenium.automation.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {

    private static final Logger logger = LogManager.getLogger(Driver.class);
    public static WebDriver driver;

    @BeforeEach
    public void beforeEach() {

        logger.info("========= Before =========");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://open.spotify.com/");
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("========= After =========");
    }


}
