(function() {

    function letUserIn(userName) {
        location.href = "/profile/" + userName;
    }

    function drawError() {
        var loginError = document.getElementById("login_error");
        loginError.style.display = "block";
    }

    function sendRequest(userName, password) {
        // Create XHR object
        var xhr = new XMLHttpRequest();

        // We need to send POST request to /login
        xhr.open("POST", "/login", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                var response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    letUserIn(userName);
                } else {
                    drawError();
                }
            }
        }

        // Finally, send the request
        xhr.send("username="+userName+"&password="+password);
    }

    function attachLoginForm() {
        var loginForm = document.getElementById("login-form");
        loginForm.onsubmit = function(e) {
            e.preventDefault();
            var userName = document.getElementById("username").value;
            var password = document.getElementById("password").value;

            sendRequest(userName, password);
        }
    }

    function attachCloseButton() {
        var errorClose = document.getElementById("error_close");
        errorClose.onclick = function(e) {
            e.preventDefault();
            var loginError = document.getElementById("login_error");
            loginError.style.display = "none";
        }
    }

    function hook() {
        attachLoginForm();
        attachCloseButton();
    }

    // Attach event listeners
    hook();

})();