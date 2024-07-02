package Create;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Question.*;

public class CreateServlet extends HttpServlet {

    private HashMap<Integer, String> qTypes;

    public CreateServlet() {
        qTypes = new HashMap<Integer, String>();
        qTypes.put(Question.QUESTION_RESPONSE, "Question-Response");
        qTypes.put(Question.FILL_BLANK, "Fill in the Blank");
        qTypes.put(Question.MULTIPLE_CHOICE, "Multiple Choice");
        qTypes.put(Question.PICTURE_RESPONSE, "Picture-Response");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setAttribute("qTypes", qTypes);

        request.getRequestDispatcher("/WEB-INF/pages/create.jsp")
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

}
