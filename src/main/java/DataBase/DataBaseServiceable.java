package DataBase;

import DataBase.JdbcDbService.NoDataToGetException;
import accounts.UserProfile;

public interface DataBaseServiceable {

    void addUserProfile(UserProfile userProfile);

    UserProfile getUserProfileByLogin(String login) throws NoDataToGetException;

    UserProfile getUserProfileById(long id) throws NoDataToGetException;
}
