(function() {
    function sendRequest(questionId, questionIndex, data, callback) {
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
                    callback(questionIndex);
                } else {
                    // Error
                }
            }
        }

        // Finally, send the request
        xhr.send("action=save_answer&question_id="+questionId+"&data="+JSON.stringify(data));
    }

    /* Text Based */
    function sendText(questionId, questionIndex, callback) {
        let dataObj = {};

        // Get the text answer
        const textField = document.getElementById("text_" + questionId);
        if(textField == null || textField.value.length === 0)  {
            callback(questionIndex);
        } else {
            dataObj["answer"] = textField.value;
            sendRequest(questionId, questionIndex, dataObj, callback);
        }
    }

    /* Multiple Choice */
    function sendMultipleChoice(questionId, questionIndex, callback) {
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
            sendRequest(questionId, questionIndex, dataObj, callback);
        } else {
            callback();
        }
    }

    function sendAnswerToServlet(questionId, questionIndex, questionType, callback) {
        switch(questionType) {
            case 'QUESTION_RESPONSE':
            case 'PICTURE_RESPONSE':
            case 'FILL_BLANK':
                sendText(questionId, questionIndex, callback);
                break;
            case 'MULTIPLE_CHOICE':
                sendMultipleChoice(questionId, questionIndex, callback)
                break;
        }
    }

    window.goToPrev = function(questionId, questionIndex, questionType) {
        sendAnswerToServlet(questionId, questionIndex, questionType,function() {
            location.href = "/quiz?q="+(questionIndex - 1);
        })
    }

    window.goToNext = function(questionId, questionIndex, questionType) {
        sendAnswerToServlet(questionId, questionIndex, questionType,function() {
            location.href = "/quiz?q="+(questionIndex + 1);
        })
    }

    window.goToReview = function(questionId, questionIndex, questionType) {
        sendAnswerToServlet(questionId, questionIndex, questionType,function() {
            location.href = "/quiz?q=review";
        })
    }

    window.checkAnswer = function(questionId, questionIndex, questionType) {
        sendAnswerToServlet(questionId, questionIndex, questionType,function() {
            location.reload();
        })
    }

    window.goToPrevPage = function(questionId, questionIndex, questionType) {
        location.href = "/quiz?q="+(questionIndex - 1);
    }

    window.goToNextPage = function(questionId, questionIndex, questionType) {
        location.href = "/quiz?q="+(questionIndex + 1);
    }

    window.goToReviewPage = function(questionId, questionIndex, questionType) {
        location.href = "/quiz?q=review";
    }

    function sendFinishAttempt(callback) {
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
                    callback(response.attempt_id);
                } else {
                    // Error
                }
            }
        }

        // Finally, send the request
        xhr.send("action=finish_attempt");
    }

    window.finishAttempt = function() {
        sendFinishAttempt(function (attempt_id) {
            location.href = "/attempt?attempt_id=" + attempt_id;
        });
    }

    function checkIfFinished(statusObj) {
        let questionIndices = Object.keys(statusObj);
        for(let k=0; k<questionIndices.length; k++) {
            let qIndex = questionIndices[k];
            if(!statusObj[qIndex]) {
                return false;
            }
        }
        return true;
    }

    window.finishAttemptForOnePage = function(elem) {
        elem.disabled = true;
        const elemList = document.querySelectorAll(".question-cont");

        let statusObj = {};
        for(let i=0; i<elemList.length; i++) {
            let questionIndex = elemList[i].getAttribute("data-index");
            statusObj[questionIndex] = false;
        }

        for(let i=0; i<elemList.length; i++) {
            let questionId = elemList[i].getAttribute("data-id");
            let questionType = elemList[i].getAttribute("data-type");
            let questionIndex = elemList[i].getAttribute("data-index");
            sendAnswerToServlet(questionId, questionIndex, questionType, function(qIndex) {
                statusObj[qIndex] = true;
            });
        }

        let waitInterval = setInterval(function() {

            if(checkIfFinished(statusObj)) {
                clearInterval(waitInterval);
                finishAttempt();
            }

        }, 500);

    }

})();