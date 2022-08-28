package selenium.automation.pages;

import selenium.automation.pages.base.BasePage;

public class HomePage extends BasePage {

    private static final String pageUrl = "https://open.spotify.com/";
    private static final String tabName = "Spotify – Web Player";

    public void homePageLoadedCheck() {

        logger.warn("============== Home Page Loaded Check ================");
        tabNameAndUrlCheck(tabName, methods.driver.getTitle(), pageUrl, methods.driver.getCurrentUrl());
        sideBarCheck();
        loggedInCheck("Samil"); // Might take as a parameter.

    }

}
