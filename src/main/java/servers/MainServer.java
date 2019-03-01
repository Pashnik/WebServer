package servers;

import accounts.AccountService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AuthorizationServlet;
import servlets.ChatServlet;
import servlets.RegistrationServlet;

import java.util.logging.Logger;

public class MainServer {

    public static final String CONTENT_TYPE = "text/html;charset=utf-8";

    public static void main(String[] args) {
        AccountService accountService = new AccountService();

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new RegistrationServlet(accountService)), "/register");
        handler.addServlet(new ServletHolder(new AuthorizationServlet(accountService)), "/logging");
        handler.addServlet(new ServletHolder(new ChatServlet()), "/chat");

        // static resources
        ResourceHandler resourceHandler = new ResourceHandler(); // use /templates for artifacts
        resourceHandler.setResourceBase("/Users/pashnik/IdeaProjects/MyWebServer/templates");
        resourceHandler.setWelcomeFiles(new String[]{"chat.html"});

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, handler}); // firstly will process static resources


        Server server = new Server(8080); // Jetty server object
        server.setHandler(handlerList);
        try {
            server.start();
            Logger.getGlobal().info("Server started");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
