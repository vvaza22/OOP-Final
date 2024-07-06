package PublishAnno;

import Announcements.Announcement;
import Announcements.AnnouncementManager;
import Global.SessionManager;
import org.json.JSONObject;

import Account.Account;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishAnnoServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rawData = request.getParameter("data");
        JSONObject jsonObject = new JSONObject(rawData);

        SessionManager sm = new SessionManager(request.getSession());
        Account user = sm.getCurrentUserAccount();

        String title = jsonObject.getString("anno_title");
        String text = jsonObject.getString("anno_text");

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        if(text == null || text.isEmpty() || title == null || title.isEmpty()) {
            responseObj.put("status", "fail");
            responseObj.put("errorMsg", "Announcement field is empty");
            // Print the response to the client
            response.getWriter().print(responseObj);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = dateFormat.format(new Date(System.currentTimeMillis()));

        // Start publishing the new announcement.
        AnnouncementManager anm = (AnnouncementManager) request.getServletContext().getAttribute("annoManager");
        // Giving dummy id.
        Announcement anno = new Announcement(0, title, createDate, text, user.getUserId(), 0, 0);

        int annoId = anm.addAnnouncement(anno);
        responseObj.put("status", "success");
        // Print the response to the client
        response.getWriter().print(responseObj);
    }

}
