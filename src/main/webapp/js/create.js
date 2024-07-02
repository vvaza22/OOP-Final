(function() {

  // List of questions: State variable
  let questionList = [];
  let uniqueIdCounter = 1;

  function genFillBlank(uniqueId, questionNumber) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.id = "question_" + uniqueId;
    container.setAttribute("data-id", uniqueId);
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + uniqueId + "\">" + questionNumber + "</span></b></h5>\n" +
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
        "    <div onclick=\"removeQuestion(" + uniqueId + ")\" class=\"command-delete command-btn\"><i class=\"fa-solid fa-xmark\"></i></div>\n" +
        "    <div class=\"q-move\">\n" +
        "        <div onclick=\"moveUp(" + uniqueId + ")\" class=\"command-btn\"><i class=\"fa-solid fa-arrow-up\"></i></div>\n" +
        "        <div onclick=\"moveDown(" + uniqueId + ")\" class=\"command-btn\"><i class=\"fa-solid fa-arrow-down\"></i></div>\n" +
        "    </div>\n" +
        "</div>";
    return container;
  }

  function genQuestionResponse(uniqueId, questionNumber) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.id = "question_" + uniqueId;
    container.setAttribute("data-id", uniqueId);
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + uniqueId + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">Type: Question-Response</span>\n" +
        "    </div>\n" +
        "    <textarea class=\"form-control\" placeholder=\"e.g. Who is the creator of the C Programming Language?\"></textarea>\n" +
        "    <div class=\"answer-list\">\n" +
        "        <label for=\"correct\">Correct Answers(Separate with ,): </label>\n" +
        "        <input id=\"correct\" class=\"form-control\" type=\"text\" placeholder=\"e.g. Dennis Ritchie, Ritchie\" />\n" +
        "    </div>\n" +
        "</div>\n" +
        "<div class=\"question-command\">\n" +
        "    <div onclick=\"removeQuestion(" + uniqueId + ")\" class=\"command-delete command-btn\"><i class=\"fa-solid fa-xmark\"></i></div>\n" +
        "    <div class=\"q-move\">\n" +
        "        <div onclick=\"moveUp(" + uniqueId + ")\" class=\"command-btn\"><i class=\"fa-solid fa-arrow-up\"></i></div>\n" +
        "        <div onclick=\"moveDown(" + uniqueId + ")\" class=\"command-btn\"><i class=\"fa-solid fa-arrow-down\"></i></div>\n" +
        "    </div>\n" +
        "</div>";
    return container;
  }

  function appendQuestion(uniqueId, questionElem) {
    const questionWrapper = document.getElementById("question-wrapper");
    questionWrapper.append(questionElem);
    questionList.push(uniqueId);
  }

  function addQuestion(qType, qTypeText) {
    // Generate Question Number
    let questionNumber = questionList.length + 1;
    switch(qType) {
      case "q_type_1":
        appendQuestion(uniqueIdCounter, genQuestionResponse(uniqueIdCounter, questionNumber));
        break;
      case "q_type_2":
        appendQuestion(uniqueIdCounter, genFillBlank(uniqueIdCounter, questionNumber));
        break;
    }
    uniqueIdCounter++;
  }

  function reNumberQuestions() {
    let questionContList = document.querySelectorAll("#question-wrapper > div");
    let counter = 1;
    for(let i=0; i<questionContList.length; i++) {
      let questionCont = questionContList[i];
      let uniqueId = parseInt(questionCont.getAttribute("data-id"));
      document.getElementById("question_header_" + uniqueId).innerText = counter;
      counter++;
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

    window.removeQuestion = function(questionId) {
      const questionContainer = document.getElementById("question_" + questionId);
      questionList = questionList.filter(function(elem) {
        return elem !== questionId;
      })
      questionContainer.parentNode.removeChild(questionContainer);
      reNumberQuestions();
    }

    window.moveUp = function(questionId) {
      const questionContainer = document.getElementById("question_" + questionId);
      let parentNode = questionContainer.parentNode;
      let prevElement = questionContainer.previousElementSibling;

      if(prevElement != null) {
        parentNode.insertBefore(questionContainer, prevElement);
      }

      reNumberQuestions();
    }

    window.moveDown = function(questionId) {
      const questionContainer = document.getElementById("question_" + questionId);
      let parentNode = questionContainer.parentNode;
      let nextNode = questionContainer.nextElementSibling;

      if(nextNode != null) {
        parentNode.insertBefore(nextNode, questionContainer);
      }

      reNumberQuestions();
    }
  }

  hook();
})();