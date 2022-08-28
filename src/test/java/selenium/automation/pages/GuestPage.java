package selenium.automation.pages;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import selenium.automation.pages.base.BasePage;

public class GuestPage extends BasePage {

    private static final String pageUrl = "https://open.spotify.com/";
    private static final String tabName = "Spotify – Web Player";
    private static final By signUpButton = By.xpath("//div[@class='LKFFk88SIRC9QKKUWR5u']//button[text()='Sign up']");
    private static final By loginButton = By.cssSelector("button[data-testid='login-button']");
    private static final By componentShelf = By.xpath("//section[@data-testid='component-shelf' and @aria-label='Spotify Playlists']");
    private static final By closeTermsPopUpButton = By.cssSelector("div[id='onetrust-banner-sdk']");

    public GuestPage() {
        guestPageLoadedCheck();
        closeTermsFooterIfOpen();
        logger.warn(" Browser language should be English! ");
    }

    public void guestPageLoadedCheck() {
        logger.warn("============== Guest Page Loaded Check ================");

        tabNameAndUrlCheck(tabName, methods.driver.getTitle(), pageUrl, methods.driver.getCurrentUrl());
        sideBarCheck();
        visibleElementCheck(signUpButton, loginButton, componentShelf);

    }

    public void clickLoginButton() {

        logger.warn("============== Click Login Button ================");
        assertTrue(methods.isElementClickable(loginButton, 10));
        methods.clickElement(loginButton);
        methods.waitBySeconds(1);

    }

    public void closeTermsFooterIfOpen() {

        logger.warn("============== Close Footer ================");
        if (methods.isElementVisible(closeTermsPopUpButton, 3)) {
            By termsAndCookiesButton = By.xpath("//button[@class='onetrust-close-btn-handler onetrust-close-btn-ui banner-close-button ot-close-icon']");
            assertTrue(methods.isElementClickable(termsAndCookiesButton, 10));
            methods.clickElement(termsAndCookiesButton);
        }

    }

}
