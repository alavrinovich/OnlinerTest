package test;

import main.java.selenium.Driver;
import main.java.selenium.PageFactory;
import main.java.utils.ThreadLogger;
import org.apache.log4j.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Preparations {

    private final UUID IDENTIFIER = UUID.randomUUID();
    protected PageFactory factory = new PageFactory();
    protected Logger logger;

    @BeforeClass
    protected void initLogger() throws IOException {
        ThreadLogger.setLogger(Logger.getLogger(IDENTIFIER.toString()));
        logger = ThreadLogger.getThreadLogger();
        logger.setLevel(Level.INFO);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d %m %n")));
        FileAppender fileAppenderForBaseLog = new FileAppender(new PatternLayout("%d %l %m %n"),
                String.format("output/%s_%s_%s.log", getClass().getName(), new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").
                        format(new Date()), IDENTIFIER.toString().substring(24)), true);
        fileAppenderForBaseLog.setThreshold(Level.INFO);
        logger.addAppender(fileAppenderForBaseLog);
    }

    @AfterClass
    public void turnOff() {
        Driver.stopDriver(Driver.getCurrentDriver());
    }

}
