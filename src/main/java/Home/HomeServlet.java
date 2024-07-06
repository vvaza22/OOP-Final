package Home;

import Global.SessionManager;
import Quiz.QuizManager;
import Account.Account;
import Quiz.Quiz;
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
        request.setAttribute("userTakenQuizzes", userTakenQuizzes);
        request.setAttribute("userCreatedQuizzes", userCreatedQuizzes);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp")
                .forward(request, response);
    }


}
