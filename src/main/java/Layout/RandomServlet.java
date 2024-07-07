package Layout;

import Account.AccountManager;
import Global.SessionManager;
import Question.QuestionType;
import Quiz.Quiz;
import Quiz.QuizManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RandomServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
        Quiz quiz = qm.getRandomQuiz();
        if(quiz == null) {
            response.sendRedirect("/");
            return;
        }
        int quizId = quiz.getId();
        response.sendRedirect("/about_quiz?id="+quizId);
    }

}
