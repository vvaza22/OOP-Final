<%@ page import="Global.SessionManager" %>
<%@ page import="Account.Account" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.QuizManager" %>

<%
    Account currentUser = (Account) request.getAttribute("currentUser");

%>


<main class="main-content pt-4">
    <div class="container">
        <div class="row">
            <div class="col-4">
                <!-- Popular Quizzes -->
                <jsp:include page="g_popular.jsp" />

                <!-- Recent Quizzes -->
                <jsp:include page="g_recent.jsp" />

                <% if(currentUser!=null) { %>
                    <jsp:include page="g_user_recent_taken.jsp" />
                    <jsp:include page="g_user_recent_created.jsp" />
                <% } %>
            </div>
            <div class="col-8">
                <!-- Admin announcements -->
                <jsp:include page="g_anno.jsp" />
            </div>
        </div>
    </div>
</main>