package nl.han.dea.dave.database;

import nl.han.dea.dave.enums.EnvironmentType;

import java.util.ResourceBundle;

public class Environment {

    private ResourceBundle input = null;

    public String getEnvVariable(EnvironmentType type) {
        input = ResourceBundle.getBundle("config");
        switch (type) {
            case USERNAME:
                return input.getString("USERNAME");
            case PASSWORD:
                return input.getString("PASSWORD");
            case DBURL:
                return input.getString("DBURL");
            default:
                return null;
        }
    }
}
