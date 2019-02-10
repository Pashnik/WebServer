package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.NoDataToGetException;
import servers.MainServer;

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
        resp.setContentType(MainServer.CONTENT_TYPE);
        if (accountService.getUserBySession(session) == null) {
            outputWriter.println("Please authorize!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            StringBuilder outputLine = new StringBuilder();
            outputLine
                    .append("User with login: ")
                    .append(accountService.getUserBySession(session).getLogin())
                    .append(" is online!");
            outputWriter.println(outputLine);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        PrintWriter outputWriter = resp.getWriter();
        resp.setContentType(MainServer.CONTENT_TYPE);
        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            UserProfile currentProfile = accountService.getUserByLogin(login);
            if (currentProfile.getPassword().equals(password)) {
                accountService.addSession(req.getSession().getId(), new UserProfile(login, password));
                StringBuilder outputLine = new StringBuilder();
                outputLine
                        .append("Congratulations user: ")
                        .append(login)
                        .append(" you have just authorized!");
                outputWriter.println(outputLine);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                outputWriter.println("Please select true password!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NoDataToGetException e) {
            StringBuilder outputLine = new StringBuilder();
            outputLine
                    .append("User: ")
                    .append(login)
                    .append(" is not registered!");
            outputWriter.println(outputLine);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String session = req.getSession().getId();
        accountService.deleteSession(session);
        resp.setContentType(MainServer.CONTENT_TYPE);
        resp.getWriter().println("Good buy!");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}