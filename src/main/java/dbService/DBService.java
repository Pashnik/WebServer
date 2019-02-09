package dbService;

import dbService.dao.ProfileDAO;
import accounts.UserProfile;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {

    private final Connection connection;

    public DBService() {
        this.connection = getConnection();
    }

    private Connection getConnection() {
        try {
            String url = "jdbc:h2:/Users/pashnik/IdeaProjects";
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

    public UserProfile getUserProfileById(long id) {
        try {
            ProfileDAO dao = new ProfileDAO(connection);
            dao.createTable();
            return dao.getProfileById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserProfile getUserProfileByLogin(String login) {
        try {
            ProfileDAO profileDAO = new ProfileDAO(connection);
            profileDAO.createTable();
            return profileDAO.getUserProfileByLogin(login);
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

