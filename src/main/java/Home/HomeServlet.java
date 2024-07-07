package Home;

import Announcements.Announcement;
import Announcements.AnnouncementManager;
import Global.SessionManager;
import Quiz.QuizManager;
import Account.Account;
import Quiz.Quiz;
import org.json.JSONObject;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class HomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        SessionManager sm = new SessionManager(request.getSession());
        QuizManager qm = (QuizManager)(request.getServletContext().getAttribute("quizManager"));
        Account currentUser = sm.getCurrentUserAccount();
        ArrayList<Quiz> userTakenQuizzes = new ArrayList<Quiz>();
        ArrayList<Quiz> userCreatedQuizzes = new ArrayList<Quiz>();
        if(currentUser!=null) {
            userTakenQuizzes = qm.getRecentlyTakenQuizzes(currentUser.getUserId());
            userCreatedQuizzes = qm.getRecentlyCreatedQuizzes(currentUser.getUserId());
        }

        request.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String annoId = request.getParameter("anno_id");
        SessionManager sm = new SessionManager(request.getSession());
        if(!sm.isUserLoggedIn()) {
            return;
        }
        Account user = sm.getCurrentUserAccount();
        AnnouncementManager anm = (AnnouncementManager) request.getServletContext().getAttribute("annoManager");
        Announcement anno = anm.getAnnouncement(Integer.parseInt(annoId));
        if(action==null) {
            return;
        }

        String previousReact = anm.getReaction(user.getUserId(), anno.getId());
        if(previousReact==null) {
            addReact(action, anm, Integer.parseInt(annoId), user.getUserId());
        }else {
            anm.deleteReaction(Integer.parseInt(annoId), user.getUserId());
            if(!previousReact.equals(action.toUpperCase())) {
                addReact(action, anm, Integer.parseInt(annoId), user.getUserId());
            }
        }

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        responseObj.put("status", "success");
        // Print the response to the client
        response.getWriter().print(responseObj);

    }

    private void addReact(String action, AnnouncementManager anm, int annoId, int userId) {
        if(action.equals("like")) {
            anm.reactAnnouncement(annoId, userId, "LIKE");
        }else if(action.equals("dislike")) {
            anm.reactAnnouncement(annoId, userId, "DISLIKE");
        }
    }
}
