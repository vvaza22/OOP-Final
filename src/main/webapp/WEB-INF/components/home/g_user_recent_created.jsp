<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Account.Account" %>
<%
    Account user = (Account) request.getAttribute("currentUser");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<Quiz> userRecentlyCreated = qm.getRecentlyCreatedQuizzes(user.getUserId());
%>

<section class="group-section g-recent">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Created By You</h4>
            </div>
        </div>
        <div class="g-list pt-4">

            <% if(userRecentlyCreated.isEmpty()) {%>
                <p> Unlock your creativity and inspire others. Create your first quiz today!</p>
            <% } else { %>
                <ol>
                    <%for(int i=0; i<Math.min(5, userRecentlyCreated.size()); i++) { %>
                    <li><a href=<%="/about_quiz?id="+String.valueOf(userRecentlyCreated.get(i).getId())%>><%=userRecentlyCreated.get(i).getName()%></a></li>
                    <% } %>
                </ol>
            <% } %>
        </div>
    </div>
</section>

