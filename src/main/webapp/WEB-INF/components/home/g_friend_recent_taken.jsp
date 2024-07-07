<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Account.Account" %>
<%@ page import="Account.AccountManager" %>

<%
    Account user = (Account) request.getAttribute("currentUser");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<Quiz> friendsRecentlyTaken = qm.getFriendTakenQuizzes(user.getUserId());
    AccountManager acm = (AccountManager) request.getServletContext().getAttribute("accountManager");
%>

<section class="group-section g-recent">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Taken By Friends</h4>
            </div>
        </div>
        <div class="g-list pt-4">

            <% if(friendsRecentlyTaken.isEmpty()) {%>
            <p> No friends with taken quizzes yet.</p>
            <% } else { %>
            <ol>
                <%for(int i=0; i<friendsRecentlyTaken.size(); i++) { %>
                <%
                    String author = acm.getAccountById(friendsRecentlyTaken.get(i).getAuthorId()).getUserName();
                %>
                <li><a href=<%="/about_quiz?id="+String.valueOf(friendsRecentlyTaken.get(i).getId())%>><%=friendsRecentlyTaken.get(i).getName()%></a> (By <a class="friend-quiz-author" href="/profile?username=<%=author%>"><%=author%></a>)</li>
                <% } %>
            </ol>
            <% } %>
        </div>
    </div>
</section>

