package DataBase.HibernateDbService;

import accounts.UserProfile;
import org.hibernate.Session;

public class DaoHibernate {

    private final Session session;

    public DaoHibernate(Session session) {
        this.session = session;
    }

    public void add(UserProfile userProfile) {
        UserDataSet userDataSet = new UserDataSet(userProfile.getLogin(), userProfile.getPassword());
        session.save(userDataSet);
    }

    public UserProfile getById(long id) {
        UserDataSet userDataSet = (UserDataSet) session.get(UserDataSet.class, id);
        return new UserProfile(userDataSet.getLogin(), userDataSet.getPassword());
    }

    public UserProfile getByLogin(String login) {
        UserDataSet userDataSet = (UserDataSet) session.get(UserDataSet.class, login);
        return new UserProfile(userDataSet.getLogin(), userDataSet.getPassword());
    }
}
