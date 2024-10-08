<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%
    QuizManager qm = (QuizManager)(request.getServletContext().getAttribute("quizManager"));
    ArrayList<Quiz> popularsList = qm.getPopularQuizzes();
%>

<section class="group-section g-popular">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Popular</h4>
            </div>
        </div>
        <div class="g-list pt-4">
            <% if(popularsList.isEmpty()) { %>
                <p>No quizzes yet.</p>
            <%} else {%>
                <ol>
                    <%for(int i=0; i<popularsList.size(); i++) { %>
                        <li><a href="/about_quiz?id=<%=popularsList.get(i).getId()%>"><%=popularsList.get(i).getName()%></a></li>
                    <% } %>
                </ol>
            <%}%>
        </div>
    </div>
</section>