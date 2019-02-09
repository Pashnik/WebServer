package accounts;

import dbService.DBService;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private final DBService dbService;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        dbService = new DBService();
        sessionIdToProfile = new HashMap<>();
    }

    public void addUser(UserProfile profile) {
        dbService.addUserProfile(profile);
    }

    public UserProfile getUserByLogin(String login) {
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
