package Create;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import Account.Account;
import Global.SessionManager;
import Question.*;
import Quiz.Quiz;
import Quiz.QuizManager;
import org.json.JSONArray;
import org.json.JSONObject;
import Question.Choice;

public class CreateServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Pass question type map to the client
        request.setAttribute("qTypes", QuestionType.createMap());

        request.getRequestDispatcher("/WEB-INF/pages/create.jsp")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // String rawData = request.getParameter("data");

        // if user is not logged in.
        SessionManager sessionManager = new SessionManager(request.getSession());
        Account currentUser = sessionManager.getCurrentUserAccount();
        if(currentUser == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "User is not logged in.");
            return;
        }

        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String rawData = jsonBuilder.toString();

        JSONObject jsonObject = new JSONObject(rawData);

        // Grab the information about a quiz.
        String name = jsonObject.getString("quizName");
        String description = jsonObject.getString("quizDescription");
        boolean randomize = jsonObject.getBoolean("randomizeOrder");
        boolean practiceMode = jsonObject.getBoolean("practiceMode");
        boolean immediate = jsonObject.getBoolean("immediateCorrection");
        int display = getDisplay(jsonObject);
        JSONArray questionsArray = jsonObject.getJSONArray("questions");

        if (
                name == null ||
                description == null ||
                name.isEmpty() ||
                description.isEmpty() ||
                questionsArray.isEmpty()
        ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "All fields must be supplied");
            return;
        }

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        QuizManager quizm = ((QuizManager)
                request.getServletContext().getAttribute("quizManager"));

        // Start creating the quiz.
        ArrayList<Question> questions = new ArrayList<Question>();

        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject questionObj = questionsArray.getJSONObject(i);
            int type = questionObj.getInt("type");
            String message = "";
            switch(type) {
                String text = "";
                String answer = "";
                case QuestionType.QUESTION_RESPONSE:
                    text = questionObj.getString("question");
                    answer = questionObj.getString("answer");
                    message = createQuestionResponse(type, text, answer, questions);
                case QuestionType.PICTURE_RESPONSE:
                    text = questionObj.getString("question");
                    String picture = questionObj.getString("picture");
                    answer = questionObj.getString("answer");
                    message = createPictureResponse(type, text, picture, answer, questions);
                case QuestionType.MULTIPLE_CHOICE:
                    text = questionObj.getString("question");
                    JSONArray choices = questionObj.getJSONArray("choices");
                    message = createMultiplceChoice(type, text, choices, questions);
                case QuestionType.FILL_BLANK:
                    text = questionObj.getString("question");
                    answer = questionObj.getString("answer");
                    message = createFillBlank(type, text, answer, questions);
            }

            if(!message.equals("success")) {
                // Tell the client that the creating a quiz failed
                responseObj.put("status", "fail");
                responseObj.put("errorMsg", message);
            }
        }

        // TODO: id??
        Quiz quiz = new Quiz(1, name, currentUser, description, randomize, practiceMode, immediate, display, questions);

        quizm.addQuiz(quiz);

        // Tell the client that the register was successful
        responseObj.put("status", "success");

        // Print the response to the client
        response.getWriter().print(responseObj);
    }

    private String createPictureResponse(int type, String text, String picture, String answer, ArrayList<Question> list) {
        String message = checkAnswer(type, answer);
        if(message.equals("success")) {
            if(!picture.isEmpty()) {
                // TODO: check picture link.
                PictureResponse quest = new PictureResponse(text, picture, parseAnswer(type, answer));
                list.add(quest);
            }
        }
        return message;
    }

    private String createQuestionResponse(int type, String text, String answer, ArrayList<Question> list) {
        String message = checkAnswer(type, answer);
        if(message.equals("success")) {
            QuestionResponse quest = new QuestionResponse(text, parseAnswer(type, answer));
            list.add(quest);
        }
        return message;
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

    private int getDisplay(JSONObject jsonObject) {
        String displayStr = jsonObject.getString("displayMode");
        if(displayStr.equals("ONE_PAGE")) {
            return Quiz.ONE_PAGE;
        }else {
            return Quiz.MULTIPLE_PAGES;
        }
    }


    private ArrayList<String> parseAnswer(int type, String answer) {
        ArrayList<String> answersList = new ArrayList<String>();
        switch(type) {
            case QuestionType.MULTIPLE_CHOICE:

            case QuestionType.FILL_BLANK:

            default:
                // cut "."
                answer = answer.substring(0, answer.length() - 1);
                String[] answers = answer.split(",\\s");
                answersList.addAll(Arrays.asList(answers));
        }
        return answersList;
    }


    private String checkAnswer(int type, String answer) {
        switch(type) {
            case QuestionType.MULTIPLE_CHOICE:

            case QuestionType.FILL_BLANK:

            default:

        }
        return "";
    }

}
