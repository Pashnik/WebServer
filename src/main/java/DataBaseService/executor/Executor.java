package DataBaseService.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    /*
    Class for process requests to data base.
    */

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    /*
    Process: insert, delete, create, update
     */

    public void makeUpdate(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    /*
    Process: select
     */

    public <T> T makeQuerry(String query, ResultGetter<T> resultGetter) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return resultGetter.getResult(resultSet);
        }
    }
}
