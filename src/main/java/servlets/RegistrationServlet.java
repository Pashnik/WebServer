package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationServlet extends HttpServlet {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private final AccountService accountService;


    public RegistrationServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // check if user is registered
        String login = req.getParameter(LOGIN);

        PrintWriter outputWriter = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        if (login == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null) {
            outputWriter.println("User with this login " + login + " is not registered!");
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            outputWriter.println("User with login " + login + " is registered!");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // register new user
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        PrintWriter outputWriter = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (accountService.getUserByLogin(login) != null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            outputWriter.println("User with this login is registered. Please choose other login!");
        } else {
            accountService.addUser(new UserProfile(login, password));
            resp.setStatus(HttpServletResponse.SC_OK);
            outputWriter.println("User with login: " + login + " password: " + password + " is " +
                    "successfully registered!");
        }
    }
}
