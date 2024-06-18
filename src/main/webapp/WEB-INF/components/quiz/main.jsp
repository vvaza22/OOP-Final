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


        <div class="row">
            <div class="col">
                <div class="question-cont">
                    <div class="question">
                        <h5>Question #8</h5>
                        <p>Assume P = NP. Which one of the following conclusions is true?</p>
                    </div>
                    <div class="answer answer-multiple">
                        <div class="answer-selector-cont">
                            <input id="1_answer_for_8" name="answer_8" type="radio" value="Everything in NP is NP-Complete" />
                            <label for="1_answer_for_8">Everything in NP is NP-Complete</label>
                        </div>
                        <div class="answer-selector-cont">
                            <input id="2_answer_for_8" name="answer_8" type="radio" value="Everything in NP is NP-Complete" />
                            <label for="2_answer_for_8">Both Humans and AI are now useless. Wohoo!</label>
                        </div>
                        <div class="answer-selector-cont">
                            <input id="3_answer_for_8" name="answer_8" type="radio" value="Everything in NP is NP-Complete" />
                            <label for="3_answer_for_8">Your mom</label>
                        </div>
                        <div class="answer-selector-cont">
                            <input id="4_answer_for_8" name="answer_8" type="radio" value="Everything in NP is NP-Complete" />
                            <label for="4_answer_for_8">Profit?</label>
                        </div>

                    </div>
                    <div class="action">
                        <button class="btn btn-round btn-outline-secondary">Previous</button>
                        <button class="btn btn-round btn-outline-success">Next</button>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col">
                <div class="question-cont">
                    <div class="question">
                        <h5>Question #9</h5>
                        <p>The craziest dog-shit programming language is <input class="fill-in-blank" type="text" /> and this language is driving me crazy.</p>
                    </div>
                    <div class="action">
                        <button class="btn btn-round btn-outline-secondary">Previous</button>
                        <button class="btn btn-round btn-outline-success">Next</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <div class="question-cont">
                    <div class="question">
                        <h5>Question #10</h5>
                        <p>What is this?</p>
                        <div class="question-image-cont">
                            <img src="https://upload.wikimedia.org/wikipedia/commons/d/dc/Grumpy_Cat_%2814556024763%29_%28cropped%29.jpg" />
                        </div>
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
