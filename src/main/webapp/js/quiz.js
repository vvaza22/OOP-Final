(function() {
    function sendRequest(questionId, data, callback) {
        // Create XHR object
        const xhr = new XMLHttpRequest();

        // We need to send POST request to /login
        xhr.open("POST", "/quiz", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                let response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    callback();
                } else {
                    // Error
                }
            }
        }

        // Finally, send the request
        xhr.send("action=save_answer&question_id="+questionId+"&data="+JSON.stringify(data));
    }

    function sendQuestionResponse(questionId, callback) {
        let dataObj = {};

        // Get the text answer
        const textArea = document.getElementById("text_" + questionId);
        dataObj["answer"] = textArea.value;

        sendRequest(questionId, dataObj, callback);
    }

    /* Question Response */
    window.questionResponsePrev = function(questionId, questionIndex) {
        sendQuestionResponse(questionId, function() {
            location.href = "/quiz?q="+(questionIndex - 1);
        })
    }
    window.questionResponseNext = function(questionId, questionIndex) {
        sendQuestionResponse(questionId, function() {
            location.href = "/quiz?q="+(questionIndex + 1);
        })
    }
    window.questionResponseRev = function(questionId, questionIndex) {
        sendQuestionResponse(questionId, function() {
            location.href = "/quiz?q=review";
        })
    }

    /* Fill Blank */
    function sendFillBlank(questionId, callback) {
        let dataObj = {};

        // Get the text answer
        const input = document.getElementById("inp_" + questionId);
        dataObj["answer"] = input.value;

        sendRequest(questionId, dataObj, callback);
    }
    window.fillBlankPrev = function(questionId, questionIndex) {
        sendFillBlank(questionId, function() {
            location.href = "/quiz?q="+(questionIndex - 1);
        })
    }
    window.fillBlankNext = function(questionId, questionIndex) {
        sendFillBlank(questionId, function() {
            location.href = "/quiz?q="+(questionIndex + 1);
        })
    }
    window.fillBlankRev = function(questionId, questionIndex) {
        sendFillBlank(questionId, function() {
            location.href = "/quiz?q=review";
        })
    }

    /* Multiple Choice */
    function sendMultipleChoice(questionId, callback) {
        let dataObj = {};

        // Find correct answer index
        let inputList = document.getElementsByName("multi_choice_"+questionId);
        let markedIndex = "";
        for(let i=0; i<inputList.length; i++) {
            if(inputList[i].checked) {
                markedIndex = inputList[i].value;
            }
        }
        if(markedIndex.length > 0) {
            dataObj["answer_index"] = markedIndex;
            sendRequest(questionId, dataObj, callback);
        }
    }
    window.multipleChoicePrev = function(questionId, questionIndex) {
        sendMultipleChoice(questionId, function() {
            location.href = "/quiz?q="+(questionIndex - 1);
        })
    }
    window.multipleChoiceNext = function(questionId, questionIndex) {
        sendMultipleChoice(questionId, function() {
            location.href = "/quiz?q="+(questionIndex + 1);
        })
    }
    window.multipleChoiceRev = function(questionId, questionIndex) {
        sendMultipleChoice(questionId, function() {
            location.href = "/quiz?q=review";
        })
    }

    /* Picture Response */
    function sendPictureResponse(questionId, callback) {
        let dataObj = {};

        // Get the text answer
        const textArea = document.getElementById("text_" + questionId);
        dataObj["answer"] = textArea.value;

        sendRequest(questionId, dataObj, callback);
    }

    window.pictureResponsePrev = function(questionId, questionIndex) {
        sendQuestionResponse(questionId, function() {
            location.href = "/quiz?q="+(questionIndex - 1);
        })
    }
    window.pictureResponseNext = function(questionId, questionIndex) {
        sendQuestionResponse(questionId, function() {
            location.href = "/quiz?q="+(questionIndex + 1);
        })
    }
    window.pictureResponseRev = function(questionId, questionIndex) {
        sendQuestionResponse(questionId, function() {
            location.href = "/quiz?q=review";
        })
    }


})();