<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<% /* Include global <head> tag */ %>
<%
    request.setAttribute("customStyle", "/css/quiz.css");
%>
<jsp:include page="/WEB-INF/components/global/head.jsp" />

<body>
<jsp:include page="/WEB-INF/components/layout/header.jsp" />
<main>
    <jsp:include page="/WEB-INF/components/practice/main.jsp" />
</main>
<jsp:include page="/WEB-INF/components/layout/footer.jsp" />

<!-- Local Javascript -->
<script src="/js/quiz.js" type="text/javascript"></script>

<!-- Global Javascript -->
<jsp:include page="/WEB-INF/components/global/append.jsp" />

</body>
</html>