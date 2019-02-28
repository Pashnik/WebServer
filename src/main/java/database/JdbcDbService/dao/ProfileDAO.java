package database.JdbcDbService.dao;

import database.JdbcDbService.NoDataToGetException;
import database.JdbcDbService.executor.Executor;
import accounts.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfileDAO {

    private Executor executor;

    public ProfileDAO(Connection connection) {
        executor = new Executor(connection);
    }

    public UserProfile getProfileById(long id) throws SQLException, NoDataToGetException {
        StringBuilder query = new StringBuilder();
        query
                .append("select * from users where id = ")
                .append(id);
        return executor.makeQuery(query, resultSet -> {
            if (!resultSet.isBeforeFirst()) throw new NoDataToGetException();
            resultSet.next();
            return new UserProfile(resultSet.getString(2), resultSet.getString(3));
        });
    }

    public UserProfile getUserProfileByLogin(String login) throws SQLException, NoDataToGetException {
        StringBuilder query = new StringBuilder();
        query
                .append("select * from users where login = ")
                .append("'")
                .append(login)
                .append("'");
        return executor.makeQuery(query, resultSet -> {
            if (!resultSet.isBeforeFirst()) throw new NoDataToGetException();
            resultSet.next();
            return new UserProfile(resultSet.getString("login"), resultSet.getString("password"));
        });
    }

    public void insertProfile(UserProfile profile) throws SQLException {
        StringBuilder query = new StringBuilder();
        query
                .append("insert into users (login, password) values")
                .append("('")
                .append(profile.getLogin())
                .append("','")
                .append(profile.getPassword())
                .append("')");
        executor.makeUpdate(query);
    }

    public void createTable() throws SQLException {
        StringBuilder query = new StringBuilder();
        query
                .append("create table if not exists users (")
                .append("id bigint auto_increment NOT NULL,")
                .append("login varchar(255),")
                .append("password varchar(255),")
                .append("primary key (id));");
        executor.makeUpdate(query);
    }

    public void dropTable() throws SQLException {
        executor.makeUpdate("drop table users");
    }
}
