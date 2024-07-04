<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.*" %>
<%@ page import="Quiz.*" %>

<link rel="stylesheet" href="/css/quiz.css" />

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    ArrayList<Question> questionList = currentQuiz.getQuestions();
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
    Integer quizId = (Integer) request.getAttribute("quizId");
    boolean reviewFlag = request.getAttribute("reviewFlag") != null;
%>

<div class="quiz-cont">
    <div class="container">

        <div class="row">
            <div class="col">
                <div class="question-list">
                    <!-- I use this weird technique to remove Whitespace characters between <a> elements -->
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
                Question curQuestion = questionList.get(curQuestionIndex - 1);

                // Pass the current question attribute to the component
                request.setAttribute("currentQuestion", curQuestion);

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
                }
            }
        %>

    </div>
</div>
