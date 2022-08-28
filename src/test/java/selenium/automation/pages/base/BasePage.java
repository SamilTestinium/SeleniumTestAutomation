package selenium.automation.pages.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import selenium.automation.methods.Methods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasePage {

    protected Methods methods;
    protected static final Logger logger = LogManager.getLogger(Methods.class);
    protected static String playlistName;
    protected static String userName;

    public BasePage() {
        this.methods = new Methods();
    }

    public void tabNameAndUrlCheck(String expectedTabName, String actualTabName, String expectedURL, String actualURL) {

        assertEquals(expectedTabName, actualTabName);
        assertEquals(expectedURL, actualURL);

        logger.info("URL and Tab name checked.");

    }

    public void sideBarCheck() {
        visibleElementCheck(By.cssSelector("a[href='/search']"), By.cssSelector("a[href='/collection']"));
        logger.info("Side bar checked.");
    }

    public void clickCustomPlaylist(String playlistName) {
        loggedInCheck(userName);
        By customPlaylistButton = By.xpath("//a[contains(@href,'playlist')]//span[text()='" + playlistName + "']");
        assertTrue(methods.isElementClickable(customPlaylistButton, 10));
        methods.clickElementJs(customPlaylistButton);
        logger.info("Playlist " + playlistName + " opened.");
    }

    public void loggedInCheck(String username) {
        userName = username;
        String usernameElement = "figure[data-testid=\"user-widget-avatar\"]" + "[title=\"" + username + "\"]";
        String usernameTitle = methods.getAttribute(By.cssSelector("figure[data-testid=\"user-widget-avatar\"]"), "title");
        String actualUserName = methods.getText(By.xpath("//span[@data-testid='user-widget-name']"));

        Assertions.assertTrue(methods.isElementVisible(By.cssSelector(usernameElement), 20));
        Assertions.assertEquals(username, usernameTitle);
        Assertions.assertEquals(username, actualUserName);
        logger.info("Log situation is checked." + username + " has logged in.");
    }

    public void visibleElementCheck(By... by) {
        for (By byCurrent : by) {
            assertTrue(methods.isElementVisible(byCurrent, 5));
        }
    }


    public void clickSearchButton() {

        By createPlaylistButton = By.xpath("//a[@class='link-subtle ATUzFKub89lzvkmvhpyE' and @href='/search']");
        assertTrue(methods.isElementClickable(createPlaylistButton, 10));
        methods.clickElement(createPlaylistButton);

    }

    public void clickCreatePlaylistButton() {

        By createPlaylistButton = By.xpath("//button[@data-testid='create-playlist-button']");
        assertTrue(methods.isElementClickable(createPlaylistButton, 10));
        methods.clickElement(createPlaylistButton);

    }

    public long getSongPosition() {
        String songDurationStr = methods.getAttribute(By.xpath("//div[@data-testid='playback-duration']"), "data-test-position");
        return Long.parseLong(songDurationStr);
    }


}
