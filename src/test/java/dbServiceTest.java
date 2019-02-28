import database.JdbcDbService.JDBCService;
import accounts.UserProfile;
import database.JdbcDbService.NoDataToGetException;
import org.junit.Test;

import static org.junit.Assert.*;


public class dbServiceTest {

    private static final UserProfile me = new UserProfile("Pashnik", "asfvafsd214");
    private static final UserProfile malik = new UserProfile("MHiraevJava", "Java is the best");


    @Test
    public void getUserById() {
        JDBCService dbService = new JDBCService();
        dbService.addUserProfile(malik);
        dbService.addUserProfile(me);

        try {
            assertEquals(me, dbService.getUserProfileById(2));
            assertEquals(malik, dbService.getUserProfileById(1));
        } catch (NoDataToGetException e) {
            e.printStackTrace();
        }

        dbService.dropTable();
    }

    @Test
    public void getUsersByLogin() {
        JDBCService dbService = new JDBCService();
        dbService.addUserProfile(me);
        dbService.addUserProfile(malik);

        try {
            assertEquals(me, dbService.getUserProfileByLogin(me.getLogin()));
            assertEquals(malik, dbService.getUserProfileByLogin(malik.getLogin()));
        } catch (NoDataToGetException e) {
            e.printStackTrace();
        }

        dbService.dropTable();
    }
}
