(function() {


    function letUserIn(userName) {
        location.href = "/profile/" + userName;
    }

    function drawError() {
        var loginError = document.getElementById("login_error");
        loginError.style.display = "block";
    }

    function sendRequest(userName, password) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/login", true);

        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if(xhr.readyState == 4 && xhr.status == 200) {
                var response = xhr.responseText;

                if(response == "ok") {
                    letUserIn(userName);
                } else {
                    drawError();
                }

            }
        }
        xhr.send("username="+userName+"&password="+password);
    }

    var errorClose = document.getElementById("error_close");
    errorClose.onclick = function(e) {
        e.preventDefault();
        var loginError = document.getElementById("login_error");
        loginError.style.display = "none";
    }
    var loginForm = document.getElementById("login-form");
    loginForm.onsubmit = function(e) {
        e.preventDefault();
        var userName = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        sendRequest(userName, password);
    }
})();