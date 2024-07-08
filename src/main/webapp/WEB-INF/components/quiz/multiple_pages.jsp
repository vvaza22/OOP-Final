<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.Question.*" %>
<%@ page import="Quiz.*" %>
<%@ page import="java.util.HashMap" %>

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    ArrayList<Quiz.Question> questionList = currentQuiz.getQuestions();
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
    Integer quizId = (Integer) request.getAttribute("quizId");
    boolean reviewFlag = request.getAttribute("reviewFlag") != null;

    HashMap<Integer, QuestionType> typeMap = QuestionType.createMap();
%>

<div class="quiz-cont">
    <div class="container">

        <div class="row">
            <div class="col">
                <div class="question-list">
                    <!-- I use this weird technique to remove Whitespace characters between <a> elements -->

                    <% if(currentQuiz.isImmediateCorrectionOn()) { %>

                        <!--
                        <% for(int i=1; i<=questionList.size(); i++) { %>
                            <% if(i == curQuestionIndex)  { %>
                            --><span class="btn btn-outline-secondary question-link active"><%= i %></span><!--
                            <% } else if(questionList.get(i-1).hasAnswer()) { %>
                                <% if(questionList.get(i-1).countPoints() > 0) { %>
                            --><a href="/quiz?q=<%= i %>" class="btn btn-outline-secondary question-link answered-correctly"><%= i %></a><!--
                                <% } else { %>
                            --><a href="/quiz?q=<%= i %>" class="btn btn-outline-secondary question-link answered-wrong"><%= i %></a><!--
                                <% } %>
                            <% } else { %>
                            --><a href="/quiz?q=<%= i %>" class="btn btn-outline-secondary question-link"><%= i %></a><!--
                            <% } %>
                        <% } %>

                    <% } else { %>

                        <!--
                        <% for(int i=1; i<=questionList.size(); i++) { %>
                            <% if(i == curQuestionIndex)  { %>
                            --><span class="btn btn-outline-secondary question-link active"><%= i %></span><!--
                            <% } else if(questionList.get(i-1).hasAnswer()) { %>
                            --><a href="/quiz?q=<%= i %>" class="btn btn-outline-secondary question-link answered"><%= i %></a><!--
                            <% } else { %>
                            --><a href="/quiz?q=<%= i %>" class="btn btn-outline-secondary question-link"><%= i %></a><!--
                            <% } %>
                        <% } %>

                    <% } %>

                    <% if(!reviewFlag) { %>
                    --><a href="/quiz?q=review" class="btn btn-outline-secondary question-link review">review</a>
                    <% } else { %>
                    --><span class="btn btn-outline-secondary question-link review active">review</span>
                    <% } %>
                </div>
            </div>
        </div>

        <% if(reviewFlag) { %>
                <jsp:include page="review.jsp" />
        <%
            } else {
                Quiz.Question curQuestion = questionList.get(curQuestionIndex - 1);

                // Pass the current question attribute to the component
                request.setAttribute("currentQuestion", curQuestion);
        %>


        <% if(currentQuiz.isImmediateCorrectionOn()) { %>

            <% if(curQuestion.hasAnswer()) { %>
            <div class="row answer-container">
            <% if(curQuestion.countPoints() == curQuestion.getMaxScore()) { %>
                <p class="answered-box answer-correct">Your answer was correct!</p>
            <% } else if(curQuestion.countPoints() == 0) { %>
                <p class="answered-box answer-wrong">Your answer was wrong!</p>
            <% } %>
            </div>
            <% } %>

        <% } %>

        <%
                switch(curQuestion.getType()) {
                    case QuestionType.QUESTION_RESPONSE:
                    %><jsp:include page="question_response.jsp" /><%
                    break;

                    case QuestionType.FILL_BLANK:
                    %><jsp:include page="fill_blank.jsp" /><%
                    break;

                    case QuestionType.MULTIPLE_CHOICE:
                    %><jsp:include page="multiple_choice.jsp" /><% break;

                    case QuestionType.PICTURE_RESPONSE:
                    %><jsp:include page="picture_response.jsp" /><%
                    break;
                } %>

        <div class="row">
            <div class="action">
                <% if(currentQuiz.isImmediateCorrectionOn()) { %>
                    <%
                        QuestionType questionType = typeMap.get(curQuestion.getType());
                        String questionTypeStr = questionType == null ? "UNKNOWN" : questionType.getTypeName();
                    %>

                    <% if(curQuestionIndex > 1) { %>
                    <button onclick="goToPrevPage(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-secondary">Jump to Next Quiz.Question</button>
                    <% } else { %>
                    <button class="btn disabled btn-round btn-outline-secondary">Jump to Previous Quiz.Question</button>
                    <% } %>

                    <% if(!curQuestion.hasAnswer()) { %>
                        <button onclick="checkAnswer(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-success">Check Answer</button>
                    <% } %>

                    <% if(curQuestionIndex.equals(questionList.size())) { %>
                    <button onclick="goToReviewPage(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-success">Jump to Review Page</button>
                    <% } else { %>
                    <button onclick="goToNextPage(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-success">Jump to Next Quiz.Question</button>
                    <% } %>

                <% } else { %>
                    <%
                        QuestionType questionType = typeMap.get(curQuestion.getType());
                        String questionTypeStr = questionType == null ? "UNKNOWN" : questionType.getTypeName();
                    %>
                    <% if(curQuestionIndex > 1) { %>
                    <button onclick="goToPrev(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-secondary">Previous</button>
                    <% } else { %>
                    <button class="btn disabled btn-round btn-outline-secondary">Previous</button>
                    <% } %>
                    <% if(curQuestionIndex.equals(questionList.size())) { %>
                    <button onclick="goToReview(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-success">Review</button>
                    <% } else { %>
                    <button onclick="goToNext(<%= curQuestion.getId() %>, <%= curQuestionIndex %>, '<%= questionTypeStr %>')" class="btn btn-round btn-outline-success">Next</button>
                    <% } %>
                <% } %>
            </div>
        </div>

        <% } %>

    </div>
</div>
