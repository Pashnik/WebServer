package Servlets;

import Templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllRequestServlet extends HttpServlet {

    private static final String FILE_NAME = "page.html";

    /*
    Jetty will call one of these functions when on this Servlet will come get or post request
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = getPageVariables(req);
        pageVariables.put("message", "");

        resp.getWriter().println(PageGenerator.instance().getPage(FILE_NAME, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = getPageVariables(req);
        String message = req.getParameter("message");

        resp.setContentType("text/html;charset=utf-8");
        if (message == null || message.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("message", message == null ? "" : message);
        resp.getWriter().println(PageGenerator.instance().getPage(FILE_NAME, pageVariables));
    }

    private Map<String, Object> getPageVariables(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURI());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }
}
