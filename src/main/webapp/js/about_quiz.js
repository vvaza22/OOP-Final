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
                    location.href = "/login";
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
                    location.href = "/login";
                }
            }
        }

        // Finally, send the request
        xhr.send("action=start_practice&quiz_id=" + quizId);
    }


    function getChallengePromise(userName, quizId) {
        return new Promise(function(resolve, reject) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/about_quiz", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function () {
                if(xhr.readyState === 4 && xhr.status === 200) {
                    let response = JSON.parse(xhr.responseText);
                    resolve(response);
                }
            }
            xhr.send("action=sendChallenge&challenged="+userName+"&quizId="+quizId);
        });
    }

    window.sendChallenge = function(quizId) {
        Swal.fire({
            title: "Enter your friend's username: ",
            input: "text",
            inputAttributes: {
                autocapitalize: "off",
                autocomplete: "off"
            },
            showCancelButton: true,
            confirmButtonText: "Challenge",
            showLoaderOnConfirm: true,
            preConfirm: (userName) => {
                return getChallengePromise(userName, quizId);
            }
        }).then(function(result) {
            if(result.isConfirmed) {
                let response = result.value;
                if(response.status === "success") {
                    Swal.fire({
                        icon: "success",
                        title: "Challenge Sent!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else {
                    Swal.fire({
                        icon: "error",
                        title: response.errorText,
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            }
        });
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