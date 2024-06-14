(function() {

    function sendRequest(userName, password) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/login", true);

        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if(xhr.readyState == 4 && xhr.status == 200) {
                var response = xhr.responseText;

                if(response == "ok") {
                    alert("good");
                } else {
                    alert("not good");
                }

            }
        }
        xhr.send("username="+userName+"&password="+password);
    }

    var loginForm = document.getElementById("login-form");
    loginForm.onsubmit = function(e) {
        e.preventDefault();
        var userName = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        sendRequest(userName, password);
    }
})();