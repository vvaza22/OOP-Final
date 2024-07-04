<%@ page import="Question.FillBlank" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %><%
    FillBlank qObject = (FillBlank) request.getAttribute("currentQuestion");
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");

    // Replace {?} with an input
    Pattern markerFinder = Pattern.compile("\\{\\?}");
    Matcher regexMatch = markerFinder.matcher(qObject.getQuestion());

    String userAnswer = qObject.getUserAnswer() == null ? "" : qObject.getUserAnswer();
    String qCode = regexMatch.replaceFirst("<input id=\"inp_"+qObject.getId()+"\" class=\"fill-in-blank\" type=\"text\" value=\""+userAnswer+"\">");

    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    Integer numQuestions = currentQuiz.getNumberOfQuestions();
%>
<div class="row">
    <div class="col">
        <div class="question-cont">
            <div class="question">
                <h5>Question #<%= curQuestionIndex %></h5>
                <p><%= qCode %></p>
            </div>
            <div class="action">
                <% if(curQuestionIndex > 1) { %>
                <button onclick="fillBlankPrev(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-secondary">Previous</button>
                <% } else { %>
                <button class="btn disabled btn-round btn-outline-secondary">Previous</button>
                <% } %>
                <% if(curQuestionIndex.equals(numQuestions)) { %>
                <button onclick="fillBlankRev(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-success">Review</button>
                <% } else { %>
                <button onclick="fillBlankNext(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-success">Next</button>
                <% } %>
            </div>
        </div>
    </div>
</div>