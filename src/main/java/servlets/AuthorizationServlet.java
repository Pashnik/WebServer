package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import DataBase.JdbcDbService.NoDataToGetException;
import servers.MainServer;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationServlet extends HttpServlet {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String INCORRECT_PASS = "Incorrect password";
    private static final String NOT_REGISTERED = "User is not registered";
    private static final String ERRORS = "auth_false.html";
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
        if (login.equals("") || password.equals("")) {
            StringBuilder outputLine = new StringBuilder();
            outputLine.append("Please fill all fields!");
            outputWriter.println(outputLine);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Map<String, Object> variables = new HashMap<>();
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
                variables.put("message", INCORRECT_PASS);
                resp.getWriter().println(PageGenerator.instance().getPage(ERRORS, variables));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NoDataToGetException e) {
            variables.put("message", NOT_REGISTERED);
            resp.getWriter().println(PageGenerator.instance().getPage(ERRORS, variables));
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