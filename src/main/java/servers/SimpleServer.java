package servers;

import servlets.AllRequestServlet;
import servlets.MirrorServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SimpleServer {
    public static void main(String[] args) {

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new MirrorServlet()), "/mirror");
        handler.addServlet(new ServletHolder(new AllRequestServlet()), "/*");

        Server server = new Server(8080); // Jetty server object
        server.setHandler(handler);

        try {
            server.start();
            java.util.logging.Logger.getGlobal().info("Server started\n");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
