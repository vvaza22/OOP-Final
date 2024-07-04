package Create;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import Account.Account;
import Global.SessionManager;
import Question.*;
import Quiz.Quiz;
import Quiz.QuizManager;
import org.json.JSONArray;
import org.json.JSONObject;
import Question.Choice;
import sun.java2d.SurfaceDataProxy;

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


        // if user is not logged in.
        SessionManager sessionManager = new SessionManager(request.getSession());
        Account currentUser = sessionManager.getCurrentUserAccount();
        if(currentUser == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "User is not logged in.");
            return;
        }

        String rawData = request.getParameter("data");
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
            String text = questionObj.getString("question");
            String answer = "";
            String message = "";
            switch(type) {
                case QuestionType.QUESTION_RESPONSE:
                    answer = questionObj.getString("answer");
                    message = createQuestionResponse(type, text, answer, questions);
                    break;
                case QuestionType.PICTURE_RESPONSE:
                    System.out.println("here too");
                    String picture = questionObj.getString("picture");
                    answer = questionObj.getString("answer");
                    message = createPictureResponse(type, text, picture, answer, questions);
                    break;
                case QuestionType.MULTIPLE_CHOICE:
                    JSONArray choices = questionObj.getJSONArray("choices");
                    message = createMultipleChoice(text, choices, questions);
                    break;
                case QuestionType.FILL_BLANK:
                    answer = questionObj.getString("answer");
                    message = createFillBlank(type, text, answer, questions);
                    break;
            }

            if(!message.equals("success")) {
                // Tell the client that the creating a quiz failed
                responseObj.put("status", "fail");
                responseObj.put("errorMsg", message);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = dateFormat.format(new Date(System.currentTimeMillis()));

        // Give dummy id.
        Quiz quiz = new Quiz(0, name, currentUser.getUserId(), description, randomize, practiceMode, immediate, display, createDate, questions);
        quizm.addQuiz(quiz);

        // Tell the client that the register was successful
        responseObj.put("status", "success");

        // Print the response to the client
        response.getWriter().print(responseObj);
    }

    private String createFillBlank(int type, String text, String answer, ArrayList<Question> list) {
        String message = checkFillBlankText(text);
        if(message.equals("success")) {
            message = checkAnswer(type, text, answer);
            if(message.equals("success")) {
                // Giving dummy id.
                FillBlank quest = new FillBlank(text, 0, parseAnswer(answer));
                list.add(quest);
            }
        }
        return message;
    }

    private String createMultipleChoice(String questText, JSONArray jsonChoices, ArrayList<Question> list) {
        String message = "";
        ArrayList<Choice> choices = new ArrayList<Choice>();
        int correctAnswerIndx = 0;
        for(int i=0; i<jsonChoices.length(); i++) {
            JSONObject choiceObj = jsonChoices.getJSONObject(i);
            String choiceText = choiceObj.getString("text");
            boolean isCorrect = choiceObj.getBoolean("isCorrect");

            message = checkChoice(choiceText);
            if (!message.equals("success")) {
                return message;
            }

            if(isCorrect) {
                correctAnswerIndx = i;
            }

            // Giving dummy id.
            Choice choice = new Choice(choiceText, 0, isCorrect);
            choices.add(choice);
        }
        MultipleChoice quest = new MultipleChoice(questText, 0, choices, correctAnswerIndx);
        list.add(quest);
        return message;
    }

    private String createPictureResponse(int type, String text, String picture, String answer, ArrayList<Question> list) {
        String message = checkAnswer(type, text, answer);
        if(message.equals("success")) {
            message = checkPicture(picture);
            if(message.equals("success")) {
                return message;
            } else {
                // Giving dummy id.
                PictureResponse quest = new PictureResponse(text, picture, 0, parseAnswer(answer));
                list.add(quest);
            }
        }
        return message;
    }

    private String createQuestionResponse(int type, String text, String answer, ArrayList<Question> list) {
        String message = checkAnswer(type, text, answer);
        if(message.equals("success")) {
            // Giving dummy id.
            QuestionResponse quest = new QuestionResponse(text, 0, parseAnswer(answer));
            list.add(quest);
        }
        return message;
    }


    private int getDisplay(JSONObject jsonObject) {
        String displayStr = jsonObject.getString("displayMode");
        if(displayStr.equals("ONE_PAGE")) {
            return Quiz.ONE_PAGE;
        }else {
            return Quiz.MULTIPLE_PAGES;
        }
    }

    private ArrayList<String> parseAnswer(String answer) {
        ArrayList<String> answersList = new ArrayList<String>();
        // cut "."
        answer = answer.substring(0, answer.length() - 1);
        String[] answers = answer.split(",\\s");
        answersList.addAll(Arrays.asList(answers));
        return answersList;
    }

    //TODO
    private String checkPicture(String pictureLink) {
        String message = "";
        if(pictureLink.isEmpty()) {
            message = "Please, add a picture";
        } else if(!pictureLink.endsWith(".jpg") || !pictureLink.endsWith(".com")) {
            message = "Please, add a valid picture";
        }
        return  message;
    }

    //TODO
    private String checkFillBlankText(String text) {
        if(text.isEmpty()) {
            return "Please, fill the question field.";
        }else if(!text.contains("{}")) {
            return "Please, make sure to add at least one blank field in question.";
        }else {
            boolean isPair = true;
//            for(int i=0; i<text.length(); i++) {
//                if(isPair && text.charAt(i) == '{') {
//                    isPair = false;
//                }else if (!isPair && text.charAt(i) == '{') {
//                    return "Please, put valid nest symbols";
//                } else if(isPair && text.charAt(i) == '}') {
//                    return "Please, put valid nest symbols";
//                } else if(!isPair && text.charAt(i) == '}') {
//                    isPair = true;
//                }
//            }

            if(!isPair) {
                return "Please, put valid nest symbols";
            }
        }
        return "success";
    }

    //TODO
    private String checkAnswer(int type, String text, String answer) {
        if(answer.isEmpty()) {
            return "Please, fill the answer field.";
        }

        if(type == QuestionType.FILL_BLANK) {
            int counter = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '}') {
                    counter++;
                }
            }
            ArrayList<String> answers = parseAnswer(answer);
            if (answers.size() != counter) {
                return "Please, include valid number of answers.";
            }
        }
        return "success";
    }


    //TODO
    private String checkChoice(String choiceText) {
        if(choiceText.isEmpty()) {
             return "Please, fill all choice fields.";
        }
        return "success";
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

}
