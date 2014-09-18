package main.java.selenium;

import main.java.screens.BaseScreen;

public class PageFactory {

    public <T extends BaseScreen> T createPage(Class<T> cl) {
        T page;
        try {
            page = cl.newInstance();
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
        return page;
    }

}
