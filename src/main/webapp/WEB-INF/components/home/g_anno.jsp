<%@ page import="java.util.ArrayList" %>
<%@ page import="Announcements.Announcement" %>
<%@ page import="Account.Account" %>
<%@ page import="Account.AccountManager" %>
<%@ page import="Announcements.AnnouncementManager" %>


<%
    Account currentUser = (Account) request.getAttribute("currentUser");
    AccountManager acmn = (AccountManager) request.getServletContext().getAttribute("accountManager");
    AnnouncementManager anm = (AnnouncementManager) request.getServletContext().getAttribute("annoManager");
    ArrayList<Announcement> list = anm.getAnnouncements();
%>

<% if(currentUser!=null && currentUser.isAdmin()) { %>
        <textarea id="anno_text" class="form-control"> </textarea>
        <div style="text-align: right">
            <button id="anno_publish_btn" class="btn btn-round btn-primary mt-2">Publish</button>
        </div>
<% } %>

<section class="anno-section">
    <h2>Announcements: </h2>
    <div class="anno-content">

        <%for(Announcement announcement: list){ %>
            <!-- Item -->
            <div class="anno-item">
                <div class="anno-header">
                    <h3><%= announcement.getTitle() %></h3>
                </div>
                <div class="anno-meta">
                    <div class="anno-date"><i class="fa-solid fa-calendar-days me-1"></i><%= announcement.getDate() %></div>
                    <div class="ms-1">By <span class="anno-by-admin"><%=acmn.getAccountById(announcement.getAuthorId()).getUserName()%></span></div>
                </div>
                <div class="anno-text">
                    <p><%= announcement.getBody() %></p>
                </div>
                <div class="anno-reaction">
                    <div class="anno-loved me-2">
                        <i class="fa-solid fa-heart"></i>
                        <span><%= announcement.getNumLike() %></span>
                    </div>
                    <div class="anno-hated">
                        <i class="fa-solid fa-thumbs-down"></i>
                        <span><%= announcement.getNumDislike() %></span>
                    </div>
                </div>
            </div>
            <!-- Item end -->
        <%}%>
    </div>
</section>