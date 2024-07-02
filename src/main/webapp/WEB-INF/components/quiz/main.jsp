<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.*" %>

<link rel="stylesheet" href="/css/quiz.css" />

<%
    ArrayList<Question> questionList = (ArrayList<Question>) request.getAttribute("questionList");
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
    Integer quizId = (Integer) request.getAttribute("quizId");
%>

<div class="quiz-cont">
    <div class="container">

        <div class="row">
            <div class="col">
                <div class="question-list">
                    <!-- I use this weird technique to remove Whitespace characters between <a> elements -->
                    <!--
                    <% for(int i=1; i<=questionList.size(); i++) { %>
                        <% if(false)  { %>
                        --><a class="btn btn-outline-secondary question-link answered"><%= i %></a><!--
                        <% } else if(i == curQuestionIndex) { %>
                        --><a href="/quiz?id=<%= quizId %>&q=<%= i %>" class="btn btn-outline-secondary question-link active"><%= i %></a><!--
                        <% } else { %>
                        --><a href="/quiz?id=<%= quizId %>&q=<%= i %>" class="btn btn-outline-secondary question-link"><%= i %></a><!--
                        <% } %>
                    <% } %>
                    -->
                </div>
            </div>
        </div>

        <%
            Question curQuestion = questionList.get(curQuestionIndex - 1);

            // Pass the current question attribute to the component
            request.setAttribute("currentQuestion", curQuestion);

            switch(curQuestion.getType()) {
                case Question.QUESTION_RESPONSE:
                %><jsp:include page="question_response.jsp" /><%
                break;

                case Question.FILL_BLANK:
                %><jsp:include page="fill_blank.jsp" /><%
                break;

                case Question.MULTIPLE_CHOICE:
                %><jsp:include page="multiple_choice.jsp" /><% break;

                case Question.PICTURE_RESPONSE:
                %><jsp:include page="picture_response.jsp" /><%
                break;
            }

        %>



    </div>
</div>
