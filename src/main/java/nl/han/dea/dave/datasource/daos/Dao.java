package nl.han.dea.dave.datasource.daos;

import nl.han.dea.dave.datasource.Repository;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Dao {

    private Repository repository;

    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement) {
        repository.closeConnection(resultSet, preparedStatement);
    }

    public Repository getRepository() {
        return this.repository;
    }

    @Inject
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

}
