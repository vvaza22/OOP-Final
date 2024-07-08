(function() {
    window.sendDeleteAccountRequest = function(user_id) {
        // Create XHR object
        var xhr = new XMLHttpRequest();

        xhr.open("POST", "/admin", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                let response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    location.reload();
                } else {
                    alert(response.errorMsg);
                }
            }
        }

        // Finally, send the request
        xhr.send("action=delete_account&user_id=" + user_id);

    }


    window.sendDeleteQuizRequest = function(quiz_id) {
        // Create XHR object
        var xhr = new XMLHttpRequest();

        xhr.open("POST", "/admin", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                let response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    location.reload();
                } else {
                    alert(response.errorMsg);
                }
            }
        }

        // Finally, send the request
        xhr.send("action=delete_quiz&quiz_id=" + quiz_id);
    }


})();