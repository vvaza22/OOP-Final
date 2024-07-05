package Mail;

import Global.SessionManager;
import Database.*;
import Account.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MailServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Initialize the session manager
        SessionManager sessionManager =
                new SessionManager(request.getSession());

        Database db = (Database)request.getServletContext().getAttribute("database");
        AccountManager amgr = (AccountManager) request.getServletContext().getAttribute("accountManager");

        MailManager mmgr = new MailManager(db, amgr);
        String currentTab = getCurrentTab( request.getParameter("tab") );

        // Pass the current tab to the JSP file
        request.setAttribute("currentTab", currentTab);
        request.setAttribute("mailManager", mmgr);

        request.getRequestDispatcher("/WEB-INF/pages/mail.jsp")
                .forward(request, response);
    }

    private String getCurrentTab(String requestedTab) {
        if(requestedTab == null) {
            return "all";
        } else if(
                requestedTab.equals("friend_req") ||
                requestedTab.equals("challenges") ||
                requestedTab.equals("notes")
        ) {
            return requestedTab;
        }

        // Show every mail by default
        return "all";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("request_id");
        String requestStatus = request.getParameter("status");

        if(idString == null || requestStatus == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Malformed request");
            return;
        }

        int requestId = Integer.parseInt(idString);

        // Access the current HTTP session
        SessionManager sessionManager = new SessionManager(request.getSession());

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        // Connect to database
        Database db = ((Database) request.getServletContext().getAttribute("database"));
        FriendRequestManager frm = new FriendRequestManager(db);

        if(requestStatus.equals("ACCEPTED")){
            frm.changeStatus(requestId, "ACCEPTED");
            responseObj.put("status", "success");
        } else {
            frm.changeStatus(requestId, "REJECTED");
            responseObj.put("status", "success");
        }

        // Print the response to the client
        response.getWriter().print(responseObj);
    }
}
