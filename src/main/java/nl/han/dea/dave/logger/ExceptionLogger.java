package nl.han.dea.dave.logger;

import java.util.logging.Logger;

public class ExceptionLogger {

    private Logger logger;

    public ExceptionLogger(String className){
        logger = Logger.getLogger(className);
    }

    public void serveLogger(Exception e){
        logger.severe(e.getMessage());
    }
}
