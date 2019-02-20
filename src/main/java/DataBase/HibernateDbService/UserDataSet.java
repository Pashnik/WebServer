package DataBase.HibernateDbService;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "usersHibernate")
public class UserDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "password", unique = true, updatable = false)
    private String password;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public UserDataSet() {

    }

    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @SuppressWarnings("UnusedDeclaration")
    public UserDataSet(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @SuppressWarnings("UnusedDeclaration")
    public long getId() {
        return id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setPassword(String password) {
        this.password = password;
    }
}
