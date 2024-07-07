<%@ page import="java.util.ArrayList" %>
<%@ page import="Announcements.Announcement" %>
<%@ page import="Account.Account" %>
<%@ page import="Account.AccountManager" %>
<%@ page import="Announcements.AnnouncementManager" %>
<%@ page import="org.owasp.encoder.Encode" %>


<%
    Account currentUser = (Account) request.getAttribute("currentUser");
    AccountManager acmn = (AccountManager) request.getServletContext().getAttribute("accountManager");
    AnnouncementManager anm = (AnnouncementManager) request.getServletContext().getAttribute("annoManager");
    ArrayList<Announcement> list = anm.getAnnouncements();
%>

<% if(currentUser!=null && currentUser.isAdmin()) { %>
    <div class="anno-post-parent">
        <div class="anno-post-text">
            <input type="text" id="anno_title" class="form-control post-title" placeholder="Title:" autocomplete="off">
            <textarea id="anno_text" class="form-control mt-2" placeholder="What's new?"></textarea>
        </div>
        <div style="text-align: right">
            <button id="anno_publish_btn" class="btn btn-round btn-primary mt-2">Publish</button>
        </div>
    </div>
<% } %>

<section class="anno-section">
    <h2>Announcements: </h2>
    <div class="anno-content">

        <%for(Announcement announcement: list){ %>
            <!-- Item -->
            <div class="anno-item">
                <div class="anno-header">
                    <h3><%= Encode.forHtml(announcement.getTitle())%></h3>
                </div>
                <div class="anno-meta">
                    <div class="anno-date"><i class="fa-solid fa-calendar-days me-1"></i><%= announcement.getDate() %></div>
                    <%
                        String userName = acmn.getAccountById(announcement.getAuthorId()).getUserName();
                    %>
                    <div class="ms-1">By <a href="/profile?username=<%=userName%>" class="anno-by-admin"><%=userName%></a></div>
                </div>
                <div class="anno-text">
                    <p><%=Encode.forHtml(announcement.getBody())%></p>
                </div>
                <div class="anno-reaction">
                    <div class="anno-loved me-2">
                        <span onclick="action_like(<%=announcement.getId()%>)" class="fa-solid fa-heart symbol"></span>
                        <span><%= announcement.getNumLike() %></span>
                    </div>
                    <div class="anno-hated">
                        <span onclick="action_dislike(<%=announcement.getId()%>)" class="fa-solid fa-thumbs-down symbol"></span>
                        <span><%= announcement.getNumDislike() %></span>
                    </div>
                </div>
            </div>
            <!-- Item end -->
        <%}%>
    </div>
</section>