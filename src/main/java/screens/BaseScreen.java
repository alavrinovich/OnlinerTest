package main.java.screens;

import main.java.selenium.Driver;
import main.java.selenium.PageFactory;
import main.java.utils.ThreadLogger;
import org.apache.log4j.Logger;

public class BaseScreen {

    protected Driver driver = Driver.getDriver();
    protected PageFactory factory = new PageFactory();
    protected Logger log = ThreadLogger.getThreadLogger();

    public <T extends BaseScreen> T initThisPageAs(Class<T> clazz) {
        return factory.createPage(clazz);
    }

}
