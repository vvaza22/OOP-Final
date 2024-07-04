package Profile;

import Account.Account;
import Account.AccountManager;
import Global.SessionManager;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        SessionManager sessionManager = new SessionManager(session);
        String currentUserName = sessionManager.getCurrentUserAccount().getUserName();
        String userName = request.getParameter("username");

        if(userName == null || userName.isEmpty()) {
            // response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username must be non-empty.");
            // If the client just clicked the search button, return them to the home page
            response.sendRedirect("/");
            return;
        }

        if(Account.isValidUsername(userName)) {
            // Get the Account Manager
            AccountManager acm = ((AccountManager)
                    request.getServletContext().getAttribute("accountManager"));

            // Get the user account
            Account userAccount = acm.getAccount(userName);

            // Pass the user account to the JSP files
            // If the user does not exist userAccount = null
            request.setAttribute("userAccount", userAccount);

            // Also pass the requested username
            request.setAttribute("reqUsername", userName);

            Integer isMyProfile = 0;
            if(currentUserName != null && currentUserName.equals(userName)) isMyProfile = 1;
            request.setAttribute("isMyOwnProfile", isMyProfile);

            // Display the page
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp")
                    .forward(request, response);
        } else {
            // response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Username.");
            // Tell the client that such user does not exist.
            request.setAttribute("reqUsername", userName);

            // TODO: FIX XSS VULNERABILITY

            // Display the page
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp")
                    .forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        SessionManager sessionManager = new SessionManager(session);
        String userName = sessionManager.getCurrentUserAccount().getUserName();
        String aboutMe = request.getParameter("aboutMe");

        if(
                userName == null ||
                userName.isEmpty()
        ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "User name field not supplied");
            return;
        }

        response.setContentType("application/json");
        JSONObject responseObj = new JSONObject();
        AccountManager acm = ((AccountManager)
                request.getServletContext().getAttribute("accountManager"));
        Account userAccount = acm.getAccount(userName);

        if(!Account.isValidUsername(userName)){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Username is not valid");
            responseObj.put("status", "fail");
            responseObj.put("errorMsg", "User \"" + userName + "\" does not exists.");

        }else {

            responseObj.put("status", "success");
            userAccount.setAboutMe(aboutMe);
            acm.updateAccount(userAccount);

        }
        response.getWriter().print(responseObj);
    }

}
