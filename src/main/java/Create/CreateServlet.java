package Create;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Question.*;
import org.json.JSONObject;

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

        String rawData = request.getParameter("data");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        responseObj.put("status", "fail");
        responseObj.put("errorMsg", rawData);

        // Print the response to the client
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

}
