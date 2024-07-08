<%@ page import="Practice.PracticeQuiz" %>
<%@ page import="Practice.PracticeQuestion" %>
<%@ page import="Question.*" %>
<%@ page import="java.util.HashMap" %>
<%
    PracticeQuiz curPracticeQuiz = (PracticeQuiz)
            request.getAttribute("currentPracticeQuiz");

    PracticeQuestion curPracticeQuestion = curPracticeQuiz.getCurrentPracticeQuestion();
    Question curQuestion = curPracticeQuestion.getQuestion();

    HashMap<Integer, QuestionType> typeMap = QuestionType.createMap();
    QuestionType questionType = typeMap.get(curQuestion.getType());
    String questionTypeStr = questionType == null ? "UNKNOWN" : questionType.getTypeName();

    // Set the practice mode flag
    request.setAttribute("practiceModeFlag", true);

    // Question JSP files require access to the quiz object
    request.setAttribute("currentQuiz", curPracticeQuiz.getQuiz());
%>

<div class="container practice-container">
    <%
        // Pass the current question attribute to the component
        request.setAttribute("currentQuestion", curQuestion);
    %>

    <div class="practice-statistics">
        <div class="statistics-wrapper">
            <div>Answered Correctly: <b style="color: green"><%= curPracticeQuestion.getAnsweredCorrectly() %></b></div>
            <div>Answered Incorrectly: <b style="color: red"><%= curPracticeQuestion.getAnsweredIncorrectly() %></b></div>
            <span>You need to answer this answer correctly <b><%= curPracticeQuestion.getRepeatTimes() %></b> times <b>in a row</b>.</span>
        </div>
    </div>

    <% if(curQuestion.hasAnswer()) { %>
    <div class="row answer-container">
        <% if(curQuestion.countPoints() == curQuestion.getMaxScore()) { %>
        <p class="answered-box answer-correct">Your answer was correct!</p>
        <% } else if(curQuestion.countPoints() == 0) { %>
        <p class="answered-box answer-wrong">Your answer was wrong!</p>
        <% } %>
    </div>
    <% } %>

    <form autocomplete="off">
    <%
        switch(curQuestion.getType()) {
            case QuestionType.QUESTION_RESPONSE:
            %><jsp:include page="../quiz/question_response.jsp" /><%
            break;

            case QuestionType.FILL_BLANK:
            %><jsp:include page="../quiz/fill_blank.jsp" /><%
            break;

            case QuestionType.MULTIPLE_CHOICE:
            %><jsp:include page="../quiz/multiple_choice.jsp" /><%
            break;

            case QuestionType.PICTURE_RESPONSE:
            %><jsp:include page="../quiz/picture_response.jsp" /><%
            break;
        } %>
    </form>

    <div class="action">
        <% if(curQuestion.hasAnswer()) { %>
        <button onclick="getNextPracticeQuestion()" class="btn btn-round btn-outline-secondary">Next Question</button>
        <% } else { %>
        <button onclick="checkPracticeAnswer(<%= curQuestion.getId() %>, '<%= questionTypeStr %>')" class="btn btn-round btn-primary">Check Answer</button>
        <% } %>
    </div>

</div>