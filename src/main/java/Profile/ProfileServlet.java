package Profile;

import Account.Account;
import Account.AccountManager;
import Account.FriendRequestManager;
import Database.Database;
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
        Account currentUserAccount = sessionManager.getCurrentUserAccount();

        String currentUserName = null;
        if (currentUserAccount != null) {
            currentUserName = currentUserAccount.getUserName();
        }
        session.setAttribute("currentUserName", currentUserName);

        String userName = request.getParameter("username");

        if (userName == null || userName.isEmpty()) {
            // response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username must be non-empty.");
            // If the client just clicked the search button, return them to the home page
            response.sendRedirect("/");
            return;
        }

        if (Account.isValidUsername(userName)) {
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

            if (currentUserName != null) {
                currentUserName = currentUserName.toLowerCase();
                userName = userName.toLowerCase();
                if (currentUserName.equals(userName)) isMyProfile = 1;
            }
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

        response.setContentType("application/json");
        JSONObject responseObj = new JSONObject();
        String action = request.getParameter("action");

        if(action == null){
            return;
        }else{
            if(action.equals("edited")){
                editAboutMe(request, response, responseObj, userName);
            }
            if(action.equals("changePic")){
                changeProfilePicture(request, response, responseObj, userName);
            }
            if(action.equals("addFriend")){
                sendFriendRequest(request, response, responseObj, userName);
            }
        }
        response.getWriter().print(responseObj);
    }

    private void sendFriendRequest(HttpServletRequest request, HttpServletResponse response, JSONObject responseObj, String userName) {
        String toWhoFriendRequest = request.getParameter("friendRequestedUser");
        if(toWhoFriendRequest == null){
            responseObj.put("status", "fail");
        }else{
            AccountManager acm = ((AccountManager) request.getServletContext().getAttribute("accountManager"));
            Account toWhoReqSentAcc = acm.getAccount(toWhoFriendRequest);
            Account fromWhoReqSentAcc = acm.getAccount(userName);
            if(toWhoReqSentAcc == null || fromWhoReqSentAcc == null){
                responseObj.put("status", "fail");
                return;
            }

            int toWhoReqSentId = toWhoReqSentAcc.getUserId();
            int fromWhoReqSentId = fromWhoReqSentAcc.getUserId();

            if (toWhoReqSentId == fromWhoReqSentId){
                responseObj.put("status", "fail");
                responseObj.put("errorMsg", "You can not send friend request to yourself.");
            }
            Database db = ((Database) request.getServletContext().getAttribute("database"));
            FriendRequestManager frm = new FriendRequestManager(db);
            frm.sendRequest(fromWhoReqSentId, toWhoReqSentId);
            responseObj.put("status", "success");
            System.out.println("success");
        }
    }

    private void changeProfilePicture(HttpServletRequest request, HttpServletResponse response, JSONObject responseObj, String userName) {
        String profilePictureLink = request.getParameter("profilePictureLink");
        if(profilePictureLink == null){
            responseObj.put("status", "fail");
        }else {
            AccountManager acm = ((AccountManager) request.getServletContext().getAttribute("accountManager"));
            Account userAccount = acm.getAccount(userName);
            if (profilePictureLink.isEmpty()) profilePictureLink = "/images/profile/default.jpg";
            userAccount.setImage(profilePictureLink);
            acm.updateProfilePictureAccount(userAccount);
            responseObj.put("status", "success");
        }
    }

    private void editAboutMe(HttpServletRequest request, HttpServletResponse response, JSONObject responseObj, String userName) {
        String aboutMe = request.getParameter("aboutMe");
        if (aboutMe == null) {
            responseObj.put("status", "fail");
//            responseObj.put("errorMsg", "User \"" + userName + "\" does not exists.");
        }else{
            AccountManager acm = ((AccountManager) request.getServletContext().getAttribute("accountManager"));
            Account userAccount = acm.getAccount(userName);
            userAccount.setAboutMe(aboutMe);
            acm.updateAboutMeAccount(userAccount);
            responseObj.put("status", "success");
            acm.updateAboutMeAccount(userAccount);
        }
    }
}

