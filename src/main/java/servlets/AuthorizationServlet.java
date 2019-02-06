package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (accountService.getUserBySession(session) == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("Please authorize!");
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("User with login: " + accountService.getUserBySession(session) +
                    " is online!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        if (login == null || password == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile currentProfile = accountService.getUserByLogin(login);
        if (currentProfile == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("User: " + login + " is not registered!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (currentProfile.getPassword().equals(password)) {
            accountService.addSession(req.getSession().getId(), new UserProfile(login, password));
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Congratulations user: " + login + " you have just authorized!");
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Please select true password!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String session = req.getSession().toString();
        accountService.deleteSession(session);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("Good buy!");
    }
}
