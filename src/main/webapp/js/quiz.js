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

    /* Text Based */
    function sendText(questionId, callback) {
        let dataObj = {};

        // Get the text answer
        const textField = document.getElementById("text_" + questionId);
        if(textField == null || textField.value.length === 0)  {
            callback();
        } else {
            dataObj["answer"] = textField.value;
            sendRequest(questionId, dataObj, callback);
        }
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
        } else {
            callback();
        }
    }

    window.goToPrev = function(questionId, questionIndex, questionType) {
        switch(questionType) {
            case 'QUESTION_RESPONSE':
            case 'PICTURE_RESPONSE':
            case 'FILL_BLANK':
                sendText(questionId, function() {
                    location.href = "/quiz?q="+(questionIndex - 1);
                });
                break;
            case 'MULTIPLE_CHOICE':
                sendMultipleChoice(questionId, function() {
                    location.href = "/quiz?q="+(questionIndex - 1);
                })
                break;
        }
    }

    window.goToNext = function(questionId, questionIndex, questionType) {
        switch(questionType) {
            case 'QUESTION_RESPONSE':
            case 'PICTURE_RESPONSE':
            case 'FILL_BLANK':
                sendText(questionId,function() {
                    location.href = "/quiz?q="+(questionIndex + 1);
                });
                break;
            case 'MULTIPLE_CHOICE':
                sendMultipleChoice(questionId,function() {
                    location.href = "/quiz?q="+(questionIndex + 1);
                })
                break;
        }
    }

    window.goToReview = function(questionId, questionIndex, questionType) {
        switch(questionType) {
            case 'QUESTION_RESPONSE':
            case 'PICTURE_RESPONSE':
            case 'FILL_BLANK':
                sendText(questionId, function() {
                    location.href = "/quiz?q=review";
                });
                break;
            case 'MULTIPLE_CHOICE':
                sendMultipleChoice(questionId, function() {
                    location.href = "/quiz?q=review";
                })
                break;
        }
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

})();