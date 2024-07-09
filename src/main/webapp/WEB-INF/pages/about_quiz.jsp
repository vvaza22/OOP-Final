<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<% /* Include global <head> tag */ %>
<% request.setAttribute("customStyle", "/css/about_quiz.css"); %>
<jsp:include page="/WEB-INF/components/global/head.jsp" />

<body style="height: 100%!important">
<jsp:include page="/WEB-INF/components/layout/header.jsp" />
<main class="container">
<jsp:include page="/WEB-INF/components/about_quiz/main.jsp" />
</main>
<jsp:include page="/WEB-INF/components/layout/footer.jsp" />

<!-- Local Javascript -->
<script type="text/javascript" src="/js/about_quiz.js"></script>
<!-- Global Javascript -->
<jsp:include page="/WEB-INF/components/global/append.jsp" />
</body>
</html>