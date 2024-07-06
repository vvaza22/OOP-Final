<head>
    <title>Page Title</title>
    <meta charset="UTF-8" />

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
</head>