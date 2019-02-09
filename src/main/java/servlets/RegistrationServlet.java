package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import servers.MainServer;

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
        resp.setContentType(MainServer.CONTENT_TYPE);
        if (login == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null) {
            StringBuilder outputLine = new StringBuilder();
            outputLine
                    .append("User with this login ")
                    .append(login)
                    .append(" is not registered!");
            outputWriter.println(outputLine);
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            StringBuilder outputLine = new StringBuilder();
            outputLine
                    .append("User with login ")
                    .append(login)
                    .append(" is registered!");
            outputWriter.println(outputLine);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // register new user
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        PrintWriter outputWriter = resp.getWriter();
        resp.setContentType(MainServer.CONTENT_TYPE);
        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (accountService.getUserByLogin(login) != null) {
            outputWriter.println("User with this login is registered. Please choose other login!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            accountService.addUser(new UserProfile(login, password));
            resp.sendRedirect(req.getContextPath() + "/index.html");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
