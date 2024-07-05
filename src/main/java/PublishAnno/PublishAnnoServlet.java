package PublishAnno;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PublishAnnoServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = request.getParameter("text");

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        if(text == null || text.isEmpty()) {
            responseObj.put("status", "fail");
            // Print the response to the client
            response.getWriter().print(responseObj);
            return;
        }




        responseObj.put("status", "success");

    }

}
