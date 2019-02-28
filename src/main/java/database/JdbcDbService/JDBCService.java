package database.JdbcDbService;

import database.DataBaseServiceable;
import database.JdbcDbService.dao.ProfileDAO;
import accounts.UserProfile;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCService implements DataBaseServiceable {

    private final Connection connection;

    public JDBCService() {
        this.connection = getConnection();
    }

    private Connection getConnection() { // use ~/test for artifacts
        try {
            String url = "jdbc:h2:~/test";
            String name = "sa";
            String password = "";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(password);

            return DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUserProfile(UserProfile userProfile) {
        try {
            connection.setAutoCommit(false);
            ProfileDAO dao = new ProfileDAO(connection);
            dao.createTable();
            dao.insertProfile(userProfile);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public UserProfile getUserProfileById(long id) throws NoDataToGetException {
        try {
            ProfileDAO dao = new ProfileDAO(connection);
            dao.createTable();
            return dao.getProfileById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserProfile getUserProfileByLogin(String login) throws NoDataToGetException {
        try {
            ProfileDAO dao = new ProfileDAO(connection);
            dao.createTable();
            return dao.getUserProfileByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dropTable() {
        try {
            ProfileDAO dao = new ProfileDAO(connection);
            dao.dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

