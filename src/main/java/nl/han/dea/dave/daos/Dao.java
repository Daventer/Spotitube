package nl.han.dea.dave.daos;

import nl.han.dea.dave.controllers.dto.TrackDTO;
import nl.han.dea.dave.database.Repository;

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
