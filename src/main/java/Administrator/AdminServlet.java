package Administrator;

import Account.Account;
import Account.AccountManager;
import Database.Database;
import Global.SessionManager;
import Mail.ChallengeManager;
import Quiz.Quiz;
import Quiz.QuizManager;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AdminServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/pages/admin.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String actionType = request.getParameter("action");
        if(actionType == null) {
            return;
        }
        if(actionType.equals("delete_account")) {
            String userIdStr = request.getParameter("user_id");
            if(userIdStr == null) {
                return;
            }

            SessionManager sm = new SessionManager(request.getSession());
            Account currUser = sm.getCurrentUserAccount();
            if(currUser==null) {
                return;
            }
            if(!currUser.isAdmin()) {
                return;
            }
            int userId = Integer.parseInt(userIdStr);
            if(currUser.getUserId() == userId) {
                return;
            }
            AccountManager acm = (AccountManager) request.getServletContext().getAttribute("accountManager");
            acm.removeAccount(acm.getAccountById(userId).getUserName());
        } else if(actionType.equals("delete_quiz")){
            String quizIdStr = request.getParameter("quiz_id");
            if(quizIdStr == null){
                return;
            }
            int quizId = Integer.parseInt(quizIdStr);
            QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
            qm.deleteQuiz(quizId);
        }

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        // Tell the client that the register was successful
        responseObj.put("status", "success");

        // Print the response to the client
        response.getWriter().print(responseObj);
    }


}
