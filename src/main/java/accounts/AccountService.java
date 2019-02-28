package accounts;

import database.DataBaseServiceable;
import database.HibernateDbService.HibernateDBService;
import database.JdbcDbService.NoDataToGetException;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private final DataBaseServiceable dbService;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        dbService = new HibernateDBService();
        sessionIdToProfile = new HashMap<>();
    }

    public void addUser(UserProfile profile) {
        dbService.addUserProfile(profile);
    }

    public UserProfile getUserByLogin(String login) throws NoDataToGetException {
        return dbService.getUserProfileByLogin(login);
    }

    public void addSession(String sessionId, UserProfile user) {
        sessionIdToProfile.put(sessionId, user);
    }

    public UserProfile getUserBySession(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
