package AboutQuiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import Account.*;
import Database.Database;
import Global.SessionManager;
import Quiz.*;
import org.json.JSONObject;
import Mail.ChallengeManager;

public class AboutQuizServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        SessionManager sm = new SessionManager(request.getSession());

        if(sm.isTakingQuiz()) {
            response.sendRedirect("/quiz");
            return;
        }
        if(sm.isTakingPracticeQuiz()) {
            response.sendRedirect("/practice");
            return;
        }

        String quizIdStr = request.getParameter("id");

        if(quizIdStr == null || quizIdStr.isEmpty() || !isPosNumber(quizIdStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz id.");
            return;
        }

        // Get the QuizManager
        QuizManager qm = (QuizManager)
                request.getServletContext().getAttribute("quizManager");

        // Get the quiz object
        int quizId = Integer.parseInt(quizIdStr);
        Quiz currentQuiz = qm.getQuiz(quizId);

        if(currentQuiz == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
            return;
        }

        // Pass the quiz object to the client-side
        request.setAttribute("quizObj", currentQuiz);
        request.getRequestDispatcher("/WEB-INF/pages/about_quiz.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        SessionManager sessionManager = new SessionManager(session);
        String fromAcc = sessionManager.getCurrentUserAccount().getUserName();
        AccountManager acm = ((AccountManager) request.getServletContext().getAttribute("accountManager"));
        int quizId = Integer.parseInt(request.getParameter("quizId"));

        response.setContentType("application/json");
        JSONObject responseObj = new JSONObject();
        String action = request.getParameter("action");


        if(action == null){
            return;
        }else{
            if(action.equals("sendChallenge")){
                sendChallenge(request, response, responseObj, fromAcc, quizId, acm);
            }
        }
        response.getWriter().print(responseObj);
    }

    private boolean isPosNumber(String s) {
        try {
            if(Integer.parseInt(s) >= 1) {
                return true;
            }
        } catch(NumberFormatException e) {
            return false;
        }
        return false;
    }

    private void sendChallenge(HttpServletRequest request, HttpServletResponse response, JSONObject responseObj, String fromAcc, int quizId, AccountManager acm) {
        String challenged = request.getParameter("challenged");
        if(challenged == null){
            responseObj.put("status", "fail");
            return;
        }

        Account from = acm.getAccount(fromAcc);
        Account to = acm.getAccount(challenged);
        if(to == null || from == null){
            responseObj.put("status", "fail");
            responseObj.put("errorText", "Account not found");
            return;
        }

        int toId = to.getUserId();
        int fromId = from.getUserId();

        if (toId == fromId){
            responseObj.put("status", "fail");
            responseObj.put("errorText", "You can't challenge yourself.");
            return;
        }
        Database db = ((Database) request.getServletContext().getAttribute("database"));
        ChallengeManager cmgr = new ChallengeManager(db);
        FriendsManager frmgr = new FriendsManager(db);
        ArrayList<Integer> friends = frmgr.friendsList(fromId);
        if(!friends.contains(toId)){
            responseObj.put("status", "fail");
            responseObj.put("errorText", "You are not friends with this user.");
            return;
        }

        if(cmgr.challengeExists(fromId, toId, quizId)){
            responseObj.put("status", "fail");
            responseObj.put("errorText", "You have already sent this challenge to this user.");
            return;
        }

        cmgr.sendChallenge(fromId, toId, quizId);
        responseObj.put("status", "success");
    }
}