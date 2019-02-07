package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorizationServlet extends HttpServlet {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private AccountService accountService;

    public AuthorizationServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String session = req.getSession().getId();

        PrintWriter outputWriter = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        if (accountService.getUserBySession(session) == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            outputWriter.println("Please authorize!");
        } else {
            outputWriter.println("User with login: " + accountService.getUserBySession(session).getLogin() +
                    " is online!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        PrintWriter outputWriter = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile currentProfile = accountService.getUserByLogin(login);
        if (currentProfile == null) {
            outputWriter.println("User: " + login + " is not registered!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (currentProfile.getPassword().equals(password)) {
            accountService.addSession(req.getSession().getId(), new UserProfile(login, password));
            outputWriter.println("Congratulations user: " + login + " you have just authorized!");
        } else {
            outputWriter.println("Please select true password!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String session = req.getSession().getId();
        accountService.deleteSession(session);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("Good buy!");
    }
}
