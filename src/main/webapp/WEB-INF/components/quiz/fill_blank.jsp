<%@ page import="Question.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.HashMap" %>
<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    Integer numQuestions = currentQuiz.getNumberOfQuestions();

    FillBlank qObject = (FillBlank) request.getAttribute("currentQuestion");
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");

    // Replace {?} with an input
    Pattern markerFinder = Pattern.compile("\\{\\?}");
    Matcher regexMatch = markerFinder.matcher(qObject.getQuestionText());

    String userAnswer = qObject.getUserAnswer() == null ? "" : qObject.getUserAnswer();

    String qCode;
    if(currentQuiz.isImmediateCorrectionOn() && qObject.hasAnswer()) {
        if(qObject.countPoints() > 0) {
            qCode = regexMatch.replaceFirst("<b class=\"fill-blank-correct\">"+userAnswer+"</b>");
        } else {
            qCode = regexMatch.replaceFirst("<b class=\"fill-blank-wrong\">"+userAnswer+"</b>");
        }
    } else {
        qCode = regexMatch.replaceFirst("<input id=\"text_"+qObject.getId()+"\" class=\"fill-in-blank\" type=\"text\" value=\""+userAnswer+"\">");

    }

    HashMap<Integer, QuestionType> typeMap = QuestionType.createMap();
    QuestionType questionType = typeMap.get(qObject.getId());
    String questionTypeStr = questionType == null ? "UNKNOWN" : questionType.getTypeName();
%>
<div class="row">
    <div class="col">
        <div class="question-cont" data-index="<%= curQuestionIndex %>" data-type="<%= questionTypeStr %>" data-id="<%= qObject.getId() %>">
            <div class="question">
                <h5>Question #<%= curQuestionIndex %></h5>
                <p><%= qCode %></p>
            </div>
            <% if(currentQuiz.isImmediateCorrectionOn() && qObject.hasAnswer() && qObject.countPoints() == 0) { %>
                <div><span>Correct Answer: </span></div>
                <b class="text-ans text-correct"><%= String.join(", ", qObject.getCorrectAnswers()) %></b>
            <% } %>
        </div>
    </div>
</div>