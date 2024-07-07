<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<Quiz> list = qm.getRecentQuizzes();
%>
<main>
    <div class="container">
        <div class="row">
            <!-- Popular Quizzes -->
            <jsp:include page="../home/showcase_popular.jsp" />
        </div>
        <div class="row">
            <h4>Explore More</h4>
            <ul>
                <%for(int i=0; i<list.size(); i++) { %>
                <li><a href=<%="/about_quiz?id="+String.valueOf(list.get(i).getId())%>><%=list.get(i).getName()%></a></li>
                <% } %>
            </ul>
        </div>
    </div>
</main>


