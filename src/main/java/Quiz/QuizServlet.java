package Quiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import Question.*;

public class QuizServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String quizId = request.getParameter("id");
        String questionIndex = request.getParameter("q");

        if(quizId == null || quizId.isEmpty() || !isPosNumber(quizId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz id.");
            return;
        }

        if(questionIndex == null || questionIndex.isEmpty() || !isPosNumber(questionIndex)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid question index.");
            return;
        }

        // Parse the value of the id string
        Integer id = Integer.parseInt(quizId);
        request.setAttribute("quizId", id);

        // Parse the value of the question index
        Integer qIndex = Integer.parseInt(questionIndex);
        request.setAttribute("curQuestionIndex", qIndex);

        // TEST
        ArrayList<Question> questionList = getQuestionList(id);
        request.setAttribute("questionList", questionList);

        request.getRequestDispatcher("/WEB-INF/pages/quiz.jsp")
                .forward(request, response);
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

    private ArrayList<Question> getQuestionList(int id) {
        ArrayList<Question> questionList = new ArrayList<Question>();

        String text1 = "The set of problems that can be solved in polynomial time is called {?}.";
        ArrayList<String> answer1 = new ArrayList<String>();
        answer1.add("P");
        Question q1 = new FillBlank(text1, answer1);

        String text2 = "Who is the most badass heavy metal composer?";
        ArrayList<String> answer2 = new ArrayList<String>();
        answer2.add("mick");
        answer2.add("mick gordon");
        answer2.add("the guy who did the doom music");
        Question q2 = new QuestionResponse(text2, answer2);

        String text3 = "What is this?";
        ArrayList<String> answer3 = new ArrayList<String>();
        answer3.add("a cat");
        answer3.add("cat");
        answer3.add("grumpy cat");
        String imageLink = "https://upload.wikimedia.org/wikipedia/commons/d/dc/Grumpy_Cat_%2814556024763%29_%28cropped%29.jpg";
        Question q3 = new PictureResponse(text3, imageLink, answer3);

        String text4 = "Mark the true sentence that we know for sure";
        ArrayList<Choice> choice4 = new ArrayList<Choice>();
        choice4.add(new Choice("P = NP", 1));
        choice4.add(new Choice("P != NP", 2));
        choice4.add(new Choice("HALTING is NP-HARD", 100));
        choice4.add(new Choice("ignorance is bliss", 123));
        int answer4 = 100;
        Question q4 = new MultipleChoice(text4, choice4, answer4);

        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        questionList.add(q4);

        return questionList;
    }
}
