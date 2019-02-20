package DataBase.HibernateDbService;

import DataBase.JdbcDbService.NoDataToGetException;
import accounts.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class DaoHibernate {

    private final Session session;

    public DaoHibernate(Session session) {
        this.session = session;
    }

    public void add(UserProfile userProfile) throws HibernateException {
        UserDataSet userDataSet = new UserDataSet(userProfile.getLogin(), userProfile.getPassword());
        session.save(userDataSet);
    }

    public UserProfile getById(long id) throws NoDataToGetException {
        UserDataSet userDataSet = (UserDataSet) session.get(UserDataSet.class, id);
        if (userDataSet == null) throw new NoDataToGetException();
        return new UserProfile(userDataSet.getLogin(), userDataSet.getPassword());
    }

    public UserProfile getByLogin(String login) throws NoDataToGetException {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        criteria.add(Restrictions.eq("login", login));
        UserDataSet user = (UserDataSet) criteria.uniqueResult();
        if (user == null) throw new NoDataToGetException();
        return new UserProfile(user.getLogin(), user.getPassword());
    }
}
