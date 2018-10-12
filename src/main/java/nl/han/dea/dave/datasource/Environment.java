package nl.han.dea.dave.datasource;

import java.util.ResourceBundle;

public class Environment {

    public String getEnvVariable(EnvironmentType type) {
        ResourceBundle input = ResourceBundle.getBundle("environment");
        switch (type) {
            case USERNAME:
                return input.getString("USERNAME");
            case PASSWORD:
                return input.getString("PASSWORD");
            case DBURL:
                return input.getString("DBURL");
            case MYSQLDRIVER:
                return input.getString("MYSQLDRIVER");
            default:
                return null;
        }
    }
}
