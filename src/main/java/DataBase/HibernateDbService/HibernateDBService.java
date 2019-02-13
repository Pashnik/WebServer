package DataBase.HibernateDbService;

import DataBase.DataBaseServiceable;
import DataBase.JdbcDbService.NoDataToGetException;
import accounts.UserProfile;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateDBService implements DataBaseServiceable {

    private final SessionFactory sessionFactory;

    public HibernateDBService() {
        Configuration configuration = setConfiguration();
        this.sessionFactory = setSessionFactory(configuration);
    }

    private static Configuration setConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        return configuration;
    }

    private static SessionFactory setSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void addUserProfile(UserProfile userProfile) {
        // todo
    }

    @Override
    public UserProfile getUserProfileByLogin(String login) throws NoDataToGetException {
        // todo
        return null;
    }

    @Override
    public UserProfile getUserProfileById(long id) throws NoDataToGetException {
        // todo
        return null;
    }
}
