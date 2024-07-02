(function() {

  // List of questions: State variable
  let questionList = [];

  function genFillBlank(questionNumber) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + questionNumber + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">Type: Fill in the blank</span>\n" +
        "    </div>\n" +
        "    <p><i>*Type {?} where the blank goes.</i></p>\n" +
        "    <textarea class=\"form-control\" placeholder=\"e.g. The best heavy metal composer is {?}.\"></textarea>\n" +
        "    <div class=\"answer-list\">\n" +
        "        <label for=\"correct\">Correct Answers(Separate with ,): </label>\n" +
        "        <input id=\"correct\" class=\"form-control\" type=\"text\" placeholder=\"e.g. Mick, Mick Gordon, The guy who did doom music\" />\n" +
        "    </div>\n" +
        "</div>\n" +
        "<div class=\"question-command\">\n" +
        "    <button class=\"command-delete command-btn\"><i class=\"fa-solid fa-xmark\"></i></button>\n" +
        "    <div class=\"q-move\">\n" +
        "        <button class=\"command-btn\"><i class=\"fa-solid fa-arrow-up\"></i></button>\n" +
        "        <button class=\"command-btn\"><i class=\"fa-solid fa-arrow-down\"></i></button>\n" +
        "    </div>\n" +
        "</div>";
    return container;
  }

  function genQuestionResponse(questionNumber) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + questionNumber + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">Type: Question-Response</span>\n" +
        "    </div>\n" +
        "    <textarea class=\"form-control\" placeholder=\"e.g. Who is the creator of the C Programming Language?\"></textarea>\n" +
        "    <div class=\"answer-list\">\n" +
        "        <label for=\"correct\">Correct Answers(Separate with ,): </label>\n" +
        "        <input id=\"correct\" class=\"form-control\" type=\"text\" placeholder=\"e.g. Dennis Ritchie, Ritchie\" />\n" +
        "    </div>\n" +
        "</div>\n" +
        "<div class=\"question-command\">\n" +
        "    <button class=\"command-delete command-btn\"><i class=\"fa-solid fa-xmark\"></i></button>\n" +
        "    <div class=\"q-move\">\n" +
        "        <button class=\"command-btn\"><i class=\"fa-solid fa-arrow-up\"></i></button>\n" +
        "        <button class=\"command-btn\"><i class=\"fa-solid fa-arrow-down\"></i></button>\n" +
        "    </div>\n" +
        "</div>";
    return container;
  }

  function appendQuestion(questionElem) {
    const questionWrapper = document.getElementById("question-wrapper");
    questionWrapper.append(questionElem);
    questionList.push(questionElem);
  }

  function addQuestion(qType, qTypeText) {
    // Generate Question Number
    let questionNumber = questionList.length + 1;
    switch(qType) {
      case "q_type_1":
        appendQuestion(genQuestionResponse(questionNumber));
        break;
      case "q_type_2":
        appendQuestion(genFillBlank(questionNumber));
        break;
    }
  }

  function hook() {
    const questionType = document.getElementById("q-type");
    const addButton = document.getElementById("add-question");

    addButton.onclick = function(e) {
      e.preventDefault();
      let selectedValue = questionType.value;
      let selectedIndex = questionType.selectedIndex;
      addQuestion(selectedValue, questionType.options[selectedIndex].text);
    }
  }

  hook();
})();