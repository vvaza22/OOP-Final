<%@ page import="Question.FillBlank" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.Choice" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %><%
    FillBlank qObject = (FillBlank) request.getAttribute("currentQuestion");
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");

    // Replace {?} with an input
    Pattern markerFinder = Pattern.compile("\\{\\?}");
    Matcher regexMatch = markerFinder.matcher(qObject.getQuestion());

    String qCode = regexMatch.replaceFirst("<input class=\"fill-in-blank\" type=\"text\">");
%>
<div class="row">
    <div class="col">
        <div class="question-cont">
            <div class="question">
                <h5>Question #<%= curQuestionIndex %></h5>
                <p><%= qCode %></p>
            </div>
            <div class="action">
                <button class="btn btn-round btn-outline-secondary">Previous</button>
                <button class="btn btn-round btn-outline-success">Next</button>
            </div>
        </div>
    </div>
</div>