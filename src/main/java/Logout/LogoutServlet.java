package Logout;

import Global.SessionManager;
import org.json.JSONObject;

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

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        if(logoutRequest.equals("logoutRequest")) {
            // Reset the session
            sessionManager.resetCurrentUser();

            // Logout was successful
            responseObj.put("status", "success");
        } else {
            // Logout failed
            responseObj.put("status", "fail");
        }

        // Print the response to the client
        response.getWriter().print(responseObj);
    }
}
