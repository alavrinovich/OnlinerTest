package main.java.screens;

import org.apache.log4j.Level;

public class MainScreen extends BaseScreen{

    private static final String CATALOG_LINK = "//a[contains(@href,'http://catalog.onliner.by/') and contains(text(),'Каталог и цены')]";

    public CatalogScreen clickByCatalog(){
     log.log(Level.INFO, String.format("Переходим на страницу 'Каталог и цены' (xpath='%s')", CATALOG_LINK));
     driver.clickByXpathAndWait(CATALOG_LINK);
     return factory.createPage(CatalogScreen.class);
    }

}
