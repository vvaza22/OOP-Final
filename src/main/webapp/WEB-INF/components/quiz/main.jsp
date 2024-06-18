<link rel="stylesheet" href="/css/quiz.css" />

<%
    int curQuestionIndex = 7;
%>

<div class="quiz-cont">
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="question-list">
                    <!-- I use this weird technique to remove Whitespace characters between <a> elements -->
                    <!--
                    <% for(int i=1; i<=50; i++) { %>
                        <% if(i < curQuestionIndex)  { %>
                        --><a class="btn btn-outline-secondary question-link answered"><%= i %></a><!--
                        <% } else if(i == curQuestionIndex) { %>
                        --><a class="btn btn-outline-secondary question-link active"><%= i %></a><!--
                        <% } else { %>
                        --><a class="btn btn-outline-secondary question-link"><%= i %></a><!--
                        <% } %>
                    <% } %>
                    -->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="question-cont">
                    <div class="question">
                        <h5>Question #7</h5>
                        <p>It's so sad Steve Jobs died of ligma.</p>
                    </div>
                    <div class="answer">
                        <label class="answer-label" for="answer-text">Your answer: </label>
                        <textarea id="answer-text" class="answer-text" spellcheck="false" autocomplete="off" autocapitalize="off"></textarea>
                    </div>
                    <div class="action">
                        <button class="btn btn-round btn-outline-secondary">Previous</button>
                        <button class="btn btn-round btn-outline-success">Next</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>