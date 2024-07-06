(function() {
    function sendTakeQuizRequest(quizId, callback) {
        // Create XHR object
        const xhr = new XMLHttpRequest();

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
        xhr.send("action=take_quiz&quiz_id=" + quizId);
    }

    function hook() {
        const takeQuizBtn = document.getElementById("take-quiz");
        const quizIdInput = document.getElementById("quiz-id");
        const quizId = quizIdInput.value;
        takeQuizBtn.onclick = function() {
            sendTakeQuizRequest(quizId, function() {
                location.href = "/quiz"
            });
        }
    }
    hook();
})();