package selenium.automation.pages;

import org.openqa.selenium.By;
import selenium.automation.pages.base.BasePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchPage extends BasePage {


    private static final String pageUrl = "https://open.spotify.com/search";
    private static final String tabName = "Spotify – Search";
    private static final By searchTextBox = By.xpath("//input[@data-testid='search-input']");
    private static final By chipOfSongs = By.xpath("//span[@class='ChipInner-sc-1ly6j4j-0 dLSEQM' and text()='Songs']");

    public void searchPageLoadedCheck() {
        logger.warn("==============  Search Page Loaded Check  ================");

        String urlWithoutKeyword = methods.driver.getCurrentUrl().substring(0, 31);
        tabNameAndUrlCheck(tabName, methods.driver.getTitle(), pageUrl, urlWithoutKeyword);

    }

    public void search(String searchStr) {
        logger.warn("==============  Search  ================");

        assertTrue(methods.isElementVisible(searchTextBox, 10));
        methods.sendKeys(searchTextBox, searchStr);
        searchPageLoadedCheck();

    }

    public void addSongs2Playlist(int songCount) {
        logger.warn("==============  Add Songs to Playlist  ================");

        assertTrue(methods.isElementClickable(chipOfSongs, 10));

        methods.clickElement(chipOfSongs);

        assertTrue(methods.isElementVisible(By.xpath("//div[contains(@aria-label,'All songs for')]"), 20));

        for (int i = 2; i < (songCount + 2); i++) {  // Song list index starts at 2.

            String selectedSongStr = "//div[@class='JUa6JJNj7R_Y3i4P8YUX' and @role='presentation']//div[@aria-rowindex=" + i + "]";

            By selectedListItem = By.xpath(selectedSongStr);
            methods.hoverElement(selectedListItem);

            By selectedSongMoreButton = By.xpath(selectedSongStr + "//button[@data-testid='more-button']");
            assertTrue(methods.isElementClickable(selectedSongMoreButton, 10));
            methods.clickElement(selectedSongMoreButton);

            String moreButtonMenuItems = "//button[@class='wC9sIed7pfp47wZbmU6m']//span[text()=";
            By addToPlaylistMenuItem = By.xpath(moreButtonMenuItems + "'Add to playlist']");
            methods.hoverElement(addToPlaylistMenuItem);

            By myPlaylistMenuItem = By.xpath(moreButtonMenuItems + "'" + playlistName + "']");
            assertTrue(methods.isElementClickable(myPlaylistMenuItem, 10));

            methods.clickElement(myPlaylistMenuItem);
            methods.waitByMilliSeconds(750);
        }
    }
}
