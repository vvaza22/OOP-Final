(function() {

  // List of questions: State variable
  let questionList = [];
  let uniqueIdCounter = 1;

  function genFillBlank(uniqueId, questionNumber, typeText, typeId, typeName) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.id = "question_" + uniqueId;
    container.setAttribute("data-id", uniqueId);
    container.setAttribute("data-type", typeName);
    container.setAttribute("data-type-id", typeId);
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + uniqueId + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">" + typeText + "</span>\n" +
        "    </div>\n" +
        "    <p><i>*Type {?} where the blank goes.</i></p>\n" +
        "    <textarea id=\"text_" + uniqueId + "\" class=\"form-control\" placeholder=\"e.g. The best heavy metal composer is {?}.\"></textarea>\n" +
        "    <div class=\"answer-list\">\n" +
        "        <label for=\"correct_" + uniqueId + "\">Correct Answers(Separate with ,): </label>\n" +
        "        <input id=\"correct_" + uniqueId + "\" class=\"form-control\" type=\"text\" placeholder=\"e.g. Mick, Mick Gordon, The guy who did doom music\" />\n" +
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

  function genQuestionResponse(uniqueId, questionNumber, typeText, typeId, typeName) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.id = "question_" + uniqueId;
    container.setAttribute("data-id", uniqueId);
    container.setAttribute("data-type", typeName);
    container.setAttribute("data-type-id", typeId);
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + uniqueId + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">" + typeText + "</span>\n" +
        "    </div>\n" +
        "    <textarea id=\"text_" + uniqueId + "\" class=\"form-control\" placeholder=\"e.g. Who is the creator of the C Programming Language?\"></textarea>\n" +
        "    <div class=\"answer-list\">\n" +
        "        <label for=\"correct_" + uniqueId + "\">Correct Answers(Separate with ,): </label>\n" +
        "        <input id=\"correct_" + uniqueId + "\" class=\"form-control\" type=\"text\" placeholder=\"e.g. Dennis Ritchie, Ritchie\" />\n" +
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

  function genPictureResponse(uniqueId, questionNumber, typeText, typeId, typeName) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.id = "question_" + uniqueId;
    container.setAttribute("data-id", uniqueId);
    container.setAttribute("data-type", typeName);
    container.setAttribute("data-type-id", typeId);
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + uniqueId + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">" + typeText + "</span>\n" +
        "    </div>\n" +
        "    <textarea id=\"text_" + uniqueId + "\" class=\"form-control\" placeholder=\"e.g. Which species of hummingbird do you see in the picture?\"></textarea>\n" +
        "<div class=\"picture-input\">\n" +
        "    <label>Picture Link:</label>\n" +
        "    <input id=\"picture_" + uniqueId + "\" type=\"text\" class=\"form-control\" name=\"picture\" placeholder=\"e.g. https://www.mywebsite.com/path/to/picture.jpg\" />\n" +
        "</div>" +
        "    <div class=\"answer-list\">\n" +
        "        <label for=\"correct_" + uniqueId + "\">Correct Answers(Separate with ,): </label>\n" +
        "        <input id=\"correct_" + uniqueId + "\" class=\"form-control\" type=\"text\" placeholder=\"e.g. Anna's Hummingbird\" />\n" +
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

  function genChoice(choiceLen) {
    let choiceCont = document.createElement("div");

    if(choiceLen === 0) {
      choiceCont.innerHTML =
          "<input type=\"radio\" name=\"choice\" checked />\n";
    } else {
      choiceCont.innerHTML =
          "<input type=\"radio\" name=\"choice\" />\n";
    }

    choiceCont.className = "choice-cont";
    choiceCont.innerHTML +=
        "<input class=\"form-control\" type=\"text\" placeholder=\"Type possible choice here...\" />\n" +
        "<div onclick=\"removeChoice(this)\" class=\"btn btn-outline-danger btn-round remove-choice\">Remove</div>";

    return choiceCont;
  }

  function genMultipleChoice(uniqueId, questionNumber, typeText, typeId, typeName) {
    let container = document.createElement("div");
    container.className = "question-cont";
    container.id = "question_" + uniqueId;
    container.setAttribute("data-id", uniqueId);
    container.setAttribute("data-type", typeName);
    container.setAttribute("data-type-id", typeId);
    container.innerHTML =
        "<div class=\"question\">\n" +
        "    <div class=\"q-heading\">\n" +
        "        <h5><b>Question #<span id=\"question_header_" + uniqueId + "\">" + questionNumber + "</span></b></h5>\n" +
        "        <span class=\"q-type-label\">" + typeText + "</span>\n" +
        "    </div>\n" +
        "    <textarea id=\"text_" + uniqueId + "\" class=\"form-control\" placeholder=\"e.g. Which species of hummingbird do you see in the picture?\"></textarea>\n" +
        "    <div class=\"answer-list\">\n" +
        "<p class=\"choice-inst\">Add choices and then mark the correct one.</p>\n" +
        "<div id=\"choice_wrapper_" + uniqueId + "\" class=\"choice-wrapper\">" +
        "</div>" +
        "<div onclick=\"addChoice(" + uniqueId + ")\" class=\"btn btn-round btn-outline-secondary\">Add Choice</div>\n" +
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

  function addQuestion(qType, selectedTypeName, qTypeText) {
    // Generate Question Number
    let questionNumber = questionList.length + 1;
    switch(selectedTypeName) {
      case "QUESTION_RESPONSE":
        appendQuestion(uniqueIdCounter, genQuestionResponse(uniqueIdCounter, questionNumber, qTypeText, qType, selectedTypeName));
        break;
      case "FILL_BLANK":
        appendQuestion(uniqueIdCounter, genFillBlank(uniqueIdCounter, questionNumber, qTypeText, qType, selectedTypeName));
        break;
      case "MULTIPLE_CHOICE":
        appendQuestion(uniqueIdCounter, genMultipleChoice(uniqueIdCounter, questionNumber, qTypeText, qType, selectedTypeName));
        break;
      case "PICTURE_RESPONSE":
        appendQuestion(uniqueIdCounter, genPictureResponse(uniqueIdCounter, questionNumber, qTypeText, qType, selectedTypeName));
        break;
    }
    uniqueIdCounter++;
  }

  function dataQuestionResponse(elemNode) {
    let questionObj = {};

    const questionId = elemNode.dataset["id"];
    const typeId = parseInt(elemNode.getAttribute("data-type-id"));

    questionObj["type"] = typeId;

    const questionText =
        document.getElementById("text_" + questionId);
    questionObj["question"] = questionText.value;

    const answerText =
        document.getElementById("correct_" + questionId);
    questionObj["answer"] = answerText.value;

    return questionObj;
  }

  function dataFillBlank(elemNode) {
    let questionObj = {};

    const questionId = elemNode.dataset["id"];
    const typeId = parseInt(elemNode.getAttribute("data-type-id"));

    questionObj["type"] = typeId;

    const questionText =
        document.getElementById("text_" + questionId);
    questionObj["question"] = questionText.value;

    const answerText =
        document.getElementById("correct_" + questionId);
    questionObj["answer"] = answerText.value;

    return questionObj;
  }

  function dataMultipleChoice(elemNode) {
    let questionObj = {};

    const questionId = elemNode.dataset["id"];
    const typeId = parseInt(elemNode.getAttribute("data-type-id"));

    questionObj["type"] = typeId;

    const questionText =
        document.getElementById("text_" + questionId);
    questionObj["question"] = questionText.value;
    questionObj["choices"] = [];

    const choices =
        elemNode.querySelectorAll(".choice-wrapper .choice-cont");

    for(let i=0; i<choices.length; i++) {
      let choiceObj = {};

      let curChoice = choices[i];
      let textInput = curChoice.querySelector("input[type=text]");
      let choiceRadio = curChoice.querySelector("input[type=radio]");
      choiceObj["text"] = textInput.value;
      choiceObj["isCorrect"] = choiceRadio.checked;

      questionObj["choices"].push(choiceObj);
    }

    return questionObj;
  }

  function dataPictureResponse(elemNode) {
    let questionObj = {};

    const questionId = elemNode.dataset["id"];
    const typeId = parseInt(elemNode.getAttribute("data-type-id"));

    questionObj["type"] = typeId;

    const questionText =
        document.getElementById("text_" + questionId);
    questionObj["question"] = questionText.value;

    const pictureLink =
        document.getElementById("picture_" + questionId);
    questionObj["picture"] = pictureLink.value;

    const answerText =
        document.getElementById("correct_" + questionId);
    questionObj["answer"] = answerText.value;

    return questionObj;
  }

  function getQuestionData(elemNode) {
    const typeName = elemNode.dataset["type"];

    switch(typeName) {
      case "QUESTION_RESPONSE":
        return dataQuestionResponse(elemNode);
      case "FILL_BLANK":
        return dataFillBlank(elemNode);
      case "MULTIPLE_CHOICE":
        return dataMultipleChoice(elemNode);
      case "PICTURE_RESPONSE":
        return dataPictureResponse(elemNode);
    }

    return null;
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

  function grabQuizData() {
    let quizData = {};

    const quizName = document.getElementById("quiz-name");
    quizData["quizName"] = quizName.value;

    const quizDescription = document.getElementById("quiz-description");
    quizData["quizDescription"] = quizDescription.value;

    const quizPicture = document.getElementById("quiz-picture");
    quizData["quizPicture"] = quizPicture.value;

    const randCheckbox = document.getElementById("randomize");
    quizData["randomizeOrder"] = randCheckbox.checked;

    const practiceCheckbox = document.getElementById("practice_mode");
    quizData["practiceMode"] = practiceCheckbox.checked;

    const immediateCheckbox = document.getElementById("immediate");
    quizData["immediateCorrection"] = immediateCheckbox.checked;

    let mulPageRadio = document.getElementById("d_mul_page");
    if(mulPageRadio.checked) {
      quizData["displayMode"] = "MULTIPLE_PAGE";
    } else {
      quizData["displayMode"] = "ONE_PAGE";
    }

    // Array for questions
    quizData["questions"] = [];

    let questionContList = document.querySelectorAll("#question-wrapper > div.question-cont");
    for(let i=0; i<questionContList.length; i++) {
      quizData["questions"].push(getQuestionData(questionContList[i]));
    }

    return quizData;
  }

  function hook() {
    const questionType = document.getElementById("q-type");
    const addButton = document.getElementById("add-question");
    const publishButton = document.getElementById("publish");

    publishButton.onclick = function(e) {
      e.preventDefault();
      sendRequest(grabQuizData());
    }

    addButton.onclick = function(e) {
      e.preventDefault();
      let selectedValue = questionType.value;
      let selectedIndex = questionType.selectedIndex;
      let selectedTypeName = questionType.options[selectedIndex].dataset.name;
      addQuestion(selectedValue, selectedTypeName, questionType.options[selectedIndex].text);
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

    window.removeChoice = function(elem) {
      if(elem != null) {
        let choiceCont = elem.parentNode;
        let choiceContCont = choiceCont.parentNode;
        // Is Checked?
        let radio = choiceCont.querySelector("input[type=radio]");
        let reAdd = false;
        if(radio != null && radio.checked) {
          reAdd = true;
        }
        choiceCont.parentNode.removeChild(choiceCont);

        if(reAdd) {
          let firstLeftElem = choiceContCont.firstElementChild;
          if(firstLeftElem != null) {
            let r2 = firstLeftElem.querySelector("input[type=radio]");
            if(r2 != null) {
              r2.checked = true;
            }
          }
        }
      }
    }

    window.addChoice = function(uniqueId) {
      const elemContainer = document.getElementById("choice_wrapper_" + uniqueId);
      const existingChoiceList = elemContainer.querySelectorAll(".choice-cont");
      elemContainer.appendChild(genChoice(existingChoiceList.length));
    }

    window.pageChange = function(elem) {
      const immediate = document.getElementById("immediate");
      if(elem.id === "d_one_page") {
        immediate.checked = false;
        immediate.disabled = true;
      } else {
        immediate.disabled = false;
      }
    }

  }

  function sendRequest(quizData) {
    // Create XHR object
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/create", true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    // Listen for the state change
    xhr.onreadystatechange = function() {
      if(this.readyState === 4 && this.status === 200) {
        // Read the server response
        let response = JSON.parse(xhr.responseText);
        if(response.status === "success") {
          location.href = "/about_quiz?id=" + response.quiz_id;
        } else {
          alert(response.errorMsg);
        }
      }
    }

    console.log(JSON.stringify(quizData));
    console.log(encodeURIComponent(JSON.stringify(quizData)));

    // Finally, send the request
    xhr.send("data=" + encodeURIComponent(JSON.stringify(quizData)));
  }

  hook();
})();