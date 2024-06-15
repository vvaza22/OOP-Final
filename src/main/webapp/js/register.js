(function() {

    function letUserIn(userName) {
        location.href = "/profile/" + userName;
    }

    function drawError(errorMsg) {
        var registerError = document.getElementById("register_error");
        registerError.style.display = "block";
        var registerErrorMsg = document.getElementById("register_error_msg");
        registerErrorMsg.innerText = errorMsg;
    }

    function sendRequest(firstName, lastName, userName, password) {
        // Create XHR object
        var xhr = new XMLHttpRequest();

        // We need to send POST request to /login
        xhr.open("POST", "/register", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                var response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    letUserIn(userName);
                } else {
                    drawError(response.errorMsg);
                }
            }
        }

        // Finally, send the request
        xhr.send("username="+userName+"&password="+password+"&firstname="+firstName+"&lastname="+lastName);
    }

    function attachRegisterForm() {
        var registerForm = document.getElementById("register-form");
        registerForm.onsubmit = function(e) {
            e.preventDefault();
            var userName = document.getElementById("username").value;
            var password = document.getElementById("password").value;
            var firstName = document.getElementById("firstname").value;
            var lastName = document.getElementById("lastname").value;


            sendRequest(firstName, lastName, userName, password);
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
        attachRegisterForm();
        attachCloseButton();
    }

    // Attach event listeners
    hook();

})();