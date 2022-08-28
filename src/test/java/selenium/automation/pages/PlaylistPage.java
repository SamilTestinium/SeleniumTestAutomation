package selenium.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.automation.pages.base.BasePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistPage extends BasePage {

    private static final String pageUrl = "https://open.spotify.com/playlist/";
    private static final By moreButton = By.xpath("//div[@data-testid='action-bar-row']//button[@data-testid='more-button']");
    private static final By playListNameBox = By.xpath("//button[@class='wCkmVGEQh3je1hrbsFBY']");
    private static final By playlistEditTextBox = By.xpath("//input[@data-testid='playlist-edit-details-name-input']");
    private static final By playlistEditSaveButton = By.xpath("//button[@data-testid='playlist-edit-details-save-button']");
    private static final By playlistMoreButton = By.xpath("//div[@class='eSg4ntPU2KQLfpLGXAww']//button[@data-testid='more-button']");
    private static final By dropdownMenuDeleteButton = By.xpath("//button[@class='wC9sIed7pfp47wZbmU6m']//span[text()='Delete']");
    private static final By deletionConfirmationButton = By.xpath("//button[@class='Button-qlcn5g-0 hgTVhT']//span[text()='DELETE']");
    private static final By playlistDeletedPopUp = By.cssSelector("div[class=AOaoydTb5lrGytHbTAAy]");
    private static final By moreOptionsMenu = By.xpath("//div[@id='context-menu']");
    private static final By actualPlaylistName = By.xpath("//span[@class='o4KVKZmeHsoRZ2Ltl078']//h1");
    private static By selectedMenuItem;

    public void playlistPageLoadedCheck() {

        logger.warn("==============  Playlist Page Loaded Check  ================");
    
        
        String urlWithoutRedirection = methods.driver.getCurrentUrl().substring(0, 34);

        tabNameAndUrlCheck(methods.driver.getTitle(), methods.driver.getTitle(), pageUrl, urlWithoutRedirection);
        sideBarCheck();

        assertTrue(methods.isElementVisible(moreButton, 20));

    }

    public void changePlaylistName(String newPlaylistName) {

        logger.warn("==============  Change Playlist Name  ================");

        playlistName = newPlaylistName;

        assertTrue(methods.isElementClickable(playListNameBox, 10));
        methods.clickElement(playListNameBox);

        assertTrue(methods.isElementVisible(playlistEditTextBox, 10));
        methods.sendKeys(playlistEditTextBox, newPlaylistName);

        assertTrue(methods.isElementClickable(playlistEditSaveButton, 10));
        methods.clickElement(playlistEditSaveButton);
        
        assertEquals(playlistName, methods.getText(actualPlaylistName));

    }

    public void playSong(String songName, long duration) {
        
        playlistPageLoadedCheck();

        logger.info("==============  Play Song  ================");

        String selectedMenuItemStr = "//div[contains(text(),'" + songName + "')]";
        selectedMenuItem = By.xpath(selectedMenuItemStr);
        methods.scrollElementIfNeeded(selectedMenuItem);

        methods.hoverElement(selectedMenuItem);

        By playButton = By.xpath("//button[@class='RfidWIoz8FON2WhFoItU' and contains(@aria-label,'" + songName + "')]");
        assertTrue(methods.isElementClickable(playButton, 10));
        methods.clickElement(playButton);

        long songPos = getSongPosition();
        methods.waitBySeconds(9);

        while (songPos < (duration * 1000)) {
            methods.waitByMilliSeconds(200);
            songPos = getSongPosition();
            logger.info("Song pos : " + songPos + " and aimed duration : " + (duration * 1000));
        }

        methods.clickElement(By.xpath("//button[@data-testid='control-button-playpause']"));
        methods.waitByMilliSeconds(200);

        List<WebElement> sideBarPlaylistSoundIcon = methods.driver.findElements(By.xpath("//div[@class='g_jOSq3pLY5p4tldskrw']//button[@class='CCeu9OfWSwIAJqA49n84 ZcKzjCkYGeMizcSAP8UX']"));
        assertEquals(sideBarPlaylistSoundIcon.size(), 0);

    }


    public void removeSongFromPlaylist(int index) {

        logger.info("==============  Remove Song From Playlist  ================");


        String playlistSizeXPath = "//div[@aria-label='" + playlistName + "']//div[@role='row']";
        int playlistSize = methods.countListSize(playlistSizeXPath);

        String selectedMenuItemStr = "//div[@class='JUa6JJNj7R_Y3i4P8YUX']//div[@aria-rowindex='" + (index + 1) + "']";
        selectedMenuItem = By.xpath(selectedMenuItemStr);
        methods.hoverElement(selectedMenuItem);

        By selectedSongMoreButton = By.xpath(selectedMenuItemStr + "//button[@data-testid='more-button']");
        methods.hoverElement(selectedSongMoreButton);
        methods.clickElement(selectedSongMoreButton);

        assertTrue(methods.isElementVisible(moreOptionsMenu, 5));

        String moreButtonMenuItems = "//button[@class='wC9sIed7pfp47wZbmU6m']//span[text()=";
        By removeSongFromPlaylistMenuItem = By.xpath(moreButtonMenuItems + "'Remove from this playlist']");
        methods.clickElement(removeSongFromPlaylistMenuItem);
        methods.waitByMilliSeconds(200);

        assertNotEquals(methods.countListSize(playlistSizeXPath), playlistSize);
        logger.info("Şarkı listeden çıkarıldı.");
    }

    public void removePlaylist() {

        logger.info("==============  Remove Playlist  ================");

        methods.scrollElementIfNeeded(playlistMoreButton);

        assertTrue(methods.isElementClickable(playlistMoreButton, 10));
        methods.clickElement(playlistMoreButton);

        assertTrue(methods.isElementClickable(dropdownMenuDeleteButton, 20));
        methods.clickElement(dropdownMenuDeleteButton);

        assertTrue(methods.isElementClickable(deletionConfirmationButton, 10));
        methods.clickElement(deletionConfirmationButton);

        assertTrue(methods.isElementVisible(playlistDeletedPopUp, 3));
        logger.info("Playlist silindi.");
        methods.waitBySeconds(2);

    }

}
