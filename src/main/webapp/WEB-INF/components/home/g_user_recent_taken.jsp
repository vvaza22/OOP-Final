<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Account.Account" %>
<%
    Account user = (Account) request.getAttribute("currentUser");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<Quiz> userRecentlyTaken = qm.getRecentlyTakenQuizzes(user.getUserId());
%>

<section class="group-section g-recent">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Recently Taken</h4>
            </div>
        </div>
        <div class="g-list pt-4">
            <% if(userRecentlyTaken.isEmpty()) { %>
                <p> Challenge yourself and have fun. Take your first quiz today!</p>
            <% } else { %>
                <ol>
                    <%for(int i=0; i<Math.min(5, userRecentlyTaken.size()); i++) { %>
                    <li><a href=<%="/about_quiz?id="+String.valueOf(userRecentlyTaken.get(i).getId())%>><%=userRecentlyTaken.get(i).getName()%></a></li>
                    <% } %>
                </ol>
            <% } %>
        </div>
    </div>
</section>