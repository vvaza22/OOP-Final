<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<% /* Include global <head> tag */ %>
<jsp:include page="/WEB-INF/components/global/head.jsp" />

<body>
    <jsp:include page="/WEB-INF/components/layout/header.jsp" />
    <jsp:include page="/WEB-INF/components/register/main.jsp" />
    <jsp:include page="/WEB-INF/components/layout/footer.jsp" />

    <!-- Local Javascript -->
    <script src="/js/register.js" type="text/javascript"></script>

    <!-- Global Javascript -->
    <jsp:include page="/WEB-INF/components/global/append.jsp" />
</body>
</html>