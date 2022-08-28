package selenium.automation.pages;

import org.openqa.selenium.By;
import selenium.automation.pages.base.BasePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends BasePage {

    private static final String pageUrl = "https://accounts.spotify.com/en/login";
    private static final String tabName = "Login - Spotify";

    public void loginPageLoadedCheck() {
        logger.warn("==============  Login Page Loaded Check ================");

        String urlWithoutRedirection = methods.driver.getCurrentUrl().substring(0, 37);
        tabNameAndUrlCheck(tabName, methods.driver.getTitle(), pageUrl, urlWithoutRedirection);

        visibleElementCheck(By.id("login-username"), By.id("login-password"),
                By.id("reset-password-link"), By.id("login-button"),
                By.xpath("//button[@data-testid='apple-login']"));

    }

    public void validLogin(String username, String password) {

        loginPageLoadedCheck();

        logger.warn("==============  Valid Login  ================");

        methods.sendKeys(By.id("login-username"), username);
        methods.sendKeys(By.id("login-password"), password);

        methods.waitByMilliSeconds(200);
        assertTrue(methods.isElementClickable(By.id("login-button"), 10));

        methods.clickElement(By.id("login-button"));

        methods.waitBySeconds(2);

    }

}
