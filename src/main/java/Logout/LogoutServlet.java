package Logout;

import Global.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String logoutRequest = request.getParameter("req_type");

        if(logoutRequest == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Malformed request");
            return;
        }

        // Access the current HTTP session
        SessionManager sessionManager = new SessionManager(request.getSession());

        // Set the Content Type
        response.setContentType("text/html");

        if(logoutRequest.equals("logoutRequest")) {
            // Reset the session
            sessionManager.resetCurrentUser();

            response.getWriter().print("ok");
        } else {
            response.getWriter().print("no");
        }
    }
}
