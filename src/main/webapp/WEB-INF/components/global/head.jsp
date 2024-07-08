<head>
    <title>MyCoolQuiz</title>
    <meta charset="UTF-8" />

    <!-- Tell the browser not to cache pages -->
    <meta http-equiv="cache-control" content="no-cache" />

    <!-- Bootstrap CSS Framework -->
    <link href="/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Font Awesome -->
    <link href="/css/all.min.css" rel="stylesheet" />

    <!-- Custom Fonts -->
    <link href="/css/fonts.css" rel="stylesheet" />

    <!-- Main Style File -->
    <link href="/css/style.css" rel="stylesheet" />

    <%
        String customStyle = (String) request.getAttribute("customStyle");
        if(customStyle != null) {
    %>
    <!-- Page Style File -->
    <link href="<%= customStyle %>" rel="stylesheet" />
    <% } %>

    <!-- Sweet Alert 2 -->
    <link href="/css/sweetalert2.min.css" rel="stylesheet" />
    <script src="/js/sweetalert2.all.min.js" type="text/javascript"></script>
</head>