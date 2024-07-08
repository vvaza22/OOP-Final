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

    function sendPracticeRequest(quizId, callback) {
        // Create XHR object
        const xhr = new XMLHttpRequest();

        xhr.open("POST", "/practice", true);
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
        xhr.send("action=start_practice&quiz_id=" + quizId);
    }

    window.sendChallenge = function sendChallenge(quizId){
        //var changePicButt = document.getElementById("challenge-friend");
        let challengedFrnd = prompt("Enter your friends username", "");
        if(challengedFrnd != null){
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/about_quiz", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function () {
                let response = JSON.parse(xhr.responseText);
                if(xhr.readyState === 4){
                    if(response.status === "success") {
                        location.reload();
                    } else {
                        // Error
                        alert(response.errorText);
                    }
                }
            }
            xhr.send("action=sendChallenge&challenged="+challengedFrnd.toString()+"&quizId="+quizId);
        }
    }

    function hook() {
        const takeQuizBtn = document.getElementById("take-quiz");
        const practiceBtn = document.getElementById("practice");
        const quizIdInput = document.getElementById("quiz-id");
        const quizId = quizIdInput.value;
        takeQuizBtn.onclick = function() {
            sendTakeQuizRequest(quizId, function() {
                location.href = "/quiz"
            });
        }
        if(practiceBtn != null) {
            practiceBtn.onclick = function() {
                sendPracticeRequest(quizId, function() {
                    location.href = "/practice";
                });
            }
        }
    }
    hook();
})();