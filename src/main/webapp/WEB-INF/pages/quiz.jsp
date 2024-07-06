<%@ page import="Quiz.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<% /* Include global <head> tag */ %>
<% request.setAttribute("customStyle", "/css/quiz.css"); %>
<jsp:include page="/WEB-INF/components/global/head.jsp" />

<body>
<jsp:include page="/WEB-INF/components/layout/header.jsp" />

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
%>

<main>
    <% if(currentQuiz.getDisplayMode() == Quiz.MULTIPLE_PAGES) { %>
    <jsp:include page="/WEB-INF/components/quiz/multiple_pages.jsp" />
    <% } else { %>
    <jsp:include page="/WEB-INF/components/quiz/one_page.jsp" />
    <% } %>
</main>

<jsp:include page="/WEB-INF/components/layout/footer.jsp" />

<!-- Local Javascript -->
<script src="/js/quiz.js" type="text/javascript"></script>

<!-- Global Javascript -->
<jsp:include page="/WEB-INF/components/global/append.jsp" />
</body>
</html>