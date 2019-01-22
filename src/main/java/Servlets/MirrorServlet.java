package Servlets;

import Templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MirrorServlet extends HttpServlet {

    private static final String URL_KEY = "key";
    private static final String KEY = "value";
    private static final String TEST = "test.html";
    private static final String MIRROR = "greeting.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pageName;
        Map<String, Object> pageVariables = new HashMap<>();
        if (req.getParameter(URL_KEY) == null) {
            pageName = MIRROR;
        } else {
            pageName = TEST;
            pageVariables.put(KEY, req.getParameter(URL_KEY));
        }
        resp.getWriter().println(PageGenerator.instance().getPage(pageName, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
