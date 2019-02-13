package DataBase.HibernateDbService;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "usersHibernate")
public class UserDataSet implements Serializable {
    private static final long serialVersionUID = -1231234141241242141L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    public UserDataSet() {

    }

    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDataSet(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }
}
