package main.java.screens;

import org.apache.log4j.Level;

public class CatalogScreen extends BaseScreen {

    private static final String CATALOG_ITEM_LINK = "//a[contains(.,'%s')]";

    public SearchScreen clickByCatalogItem(String itemName) {
        log.log(Level.INFO, String.format("Переходим на страницу '%s' (xpath='%s')", itemName,
                String.format(CATALOG_ITEM_LINK, itemName)));
        driver.clickByXpathAndWait(String.format(CATALOG_ITEM_LINK, itemName));
        return factory.createPage(SearchScreen.class);
    }

}
