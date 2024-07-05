<%@ page import="java.util.ArrayList" %>
<%@ page import="Announcements.Announcement" %>
<%@ page import="Account.Account" %>


<%
    Account currentUser = (Account) request.getAttribute("currentUser");
%>

<% if(currentUser!=null && currentUser.isAdmin()) { %>
        <textarea id="anno_text" class="form-control"> </textarea>
        <div style="text-align: right">
            <button id="anno_publish_btn" class="btn btn-round btn-primary mt-2">Publish</button>
        </div>
<% } %>

<%
    ArrayList<Announcement> data = new ArrayList<Announcement>();
    String title1 = "There are now more than 10 quizzes on this website!";
    String date1 = "June 12, 2077";
    String text1 = "This is such a huge achievement! I want thank everybody who made the quizzes or whatever! And please do not make anymore quizzes or our server won't be able to handle that much content and will crash...";
    String author1 = "Administrator";
    int num_like1 = 2;
    int num_dislikes1 = 300;

    Announcement ann1 = new Announcement(title1, date1, text1, author1, num_like1, num_dislikes1);
    data.add(ann1);

    String title2 = "Why is nobody making quizzes???";
    String date2 = "June 12, 2024";
    String text2 = "Since the website started operating, only one guy has made a quiz and that was one guy was me. C'mon guys, can't you see the website is very fun and creative. Taking quizzes is such a productive way to waste your finite life.";
    String author2 = "Administrator";
    int num_like2 = 7;
    int num_dislikes2 = 9;

    Announcement ann2 = new Announcement(title2, date2, text2, author2, num_like2, num_dislikes2);
    data.add(ann2);

    String title3 = "Welcome to MyCoolQuiz!";
    String date3 = "June 12, 2004";
    String text3 = "Welcome. Welcome to MyCoolQuiz. You have chosen or been chosen to take and make quizzes. I thought so much of MyCoolQuiz, that I elected my administration here, in the Tomcat server so thoughfully provided by our benefactors(Apache). I have been proud to call MyCoolQuiz my home. And so, whether you are here to stay, or passing through on your way to parts unknown, welcome to MyCoolQuiz. It's safer here.";
    String author3 = "Administrator";
    int num_like3 = 5;
    int num_dislikes3 = 10;

    Announcement ann3 = new Announcement(title3, date3, text3, author3, num_like3, num_dislikes3);
    data.add(ann3);
%>

<section class="anno-section">
    <h2>Announcements: </h2>
    <div class="anno-content">

        <%for(Announcement announcement: data){ %>
            <!-- Item -->
            <div class="anno-item">
                <div class="anno-header">
                    <h3><%= announcement.getTitle() %></h3>
                </div>
                <div class="anno-meta">
                    <div class="anno-date"><i class="fa-solid fa-calendar-days me-1"></i><%= announcement.getDate() %></div>
                    <div class="ms-1">By <span class="anno-by-admin"><%= announcement.getAuthor() %></span></div>
                </div>
                <div class="anno-text">
                    <p><%= announcement.getText() %></p>
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