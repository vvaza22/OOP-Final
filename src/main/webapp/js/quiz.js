(function() {
    function sendRequest(questionId, questionIndex, data, callback, server) {
        // Create XHR object
        const xhr = new XMLHttpRequest();

        let reqServer = server === undefined ? "/quiz" : server;

        xhr.open("POST", reqServer, true);
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

    function practiceNextQuestionRequest(callback) {
        // Create XHR object
        const xhr = new XMLHttpRequest();

        // We need to send POST request to /login
        xhr.open("POST", "/practice", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                let response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    callback(response);
                } else {
                    // Error
                }
            }
        }

        // Finally, send the request
        xhr.send("action=next_question");
    }

    /* Text Based */
    function sendText(questionId, questionIndex, callback, server) {
        let dataObj = {};

        // Get the text answer
        const textField = document.getElementById("text_" + questionId);
        if(textField == null || textField.value.length === 0)  {
            callback(questionIndex);
        } else {
            dataObj["answer"] = textField.value;
            sendRequest(questionId, questionIndex, dataObj, callback, server);
        }
    }

    /* Multiple Choice */
    function sendMultipleChoice(questionId, questionIndex, callback, server) {
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
            sendRequest(questionId, questionIndex, dataObj, callback, server);
        } else {
            callback(questionIndex);
        }
    }

    function sendAnswerToServlet(questionId, questionIndex, questionType, callback, server) {
        switch(questionType) {
            case 'QUESTION_RESPONSE':
            case 'PICTURE_RESPONSE':
            case 'FILL_BLANK':
                sendText(questionId, questionIndex, callback, server);
                break;
            case 'MULTIPLE_CHOICE':
                sendMultipleChoice(questionId, questionIndex, callback, server)
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

    window.checkAnswer = function(questionId, questionIndex, questionType) {
        sendAnswerToServlet(questionId, questionIndex, questionType,function() {
            location.reload();
        })
    }

    window.checkPracticeAnswer = function(questionId, questionType) {
        sendAnswerToServlet(questionId, 0, questionType,function() {
            location.reload();
        }, "/practice");
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

    function sendFinishPractice(callback) {
        // Create XHR object
        const xhr = new XMLHttpRequest();

        // We need to send POST request to /login
        xhr.open("POST", "/practice", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                let response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    callback(response.return_to);
                } else {
                    // Error
                }
            }
        }

        // Finally, send the request
        xhr.send("action=end_practice");
    }

    window.finishAttempt = function() {
        sendFinishAttempt(function (attempt_id) {
            location.href = "/attempt?attempt_id=" + attempt_id;
        });
    }

    window.finishPractice = function() {
        sendFinishPractice(function (quizId) {
            location.href = "/about_quiz?id="+quizId;
        });
    }

    window.getNextPracticeQuestion = function() {
        practiceNextQuestionRequest(function(response) {
            if(response.practice_status === "continue") {
                location.reload();
            } else {
                Swal.fire({
                    icon: "success",
                    title: "Practice Finished!",
                    showConfirmButton: false,
                    timer: 3000
                }).then(function() {
                    location.href = "/about_quiz?id=" + response.return_to;
                });
            }
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