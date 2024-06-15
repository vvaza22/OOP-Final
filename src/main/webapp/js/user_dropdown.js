(function() {

    function sendLogoutRequest(callback) {

        // Create XHR Object
        const xhr = new XMLHttpRequest();

        // We need to send POST logout request to the servlet
        xhr.open("POST", "/logout", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Parse the server response
                var response = JSON.parse(this.responseText);
                // If the request was successful
                callback(response);
            }
        }

        // Finally send the request
        xhr.send("req_type=logoutRequest");
    }

    function attachLogout() {
        var logout = document.getElementById("user-logout");
        if(logout != null) {
            logout.onclick = function(e) {
                e.preventDefault();
                sendLogoutRequest(function(response) {
                    if(response.status === "success") {
                        // Redirect to the homepage
                        location.href = "/";
                    } else {
                        // Probably do nothing
                    }
                });
            }
        }
    }

    function attachDropdown() {
        var dropdown = document.getElementById("user-dropdown");
        if(dropdown != null) {
            dropdown.onclick = function (e) {
                e.preventDefault();
                var dropdownMenu = this.querySelector(".dropdown-menu");

                // Toggle the class
                dropdownMenu.classList.toggle("show");
            };
        }
    }

    function hook() {
        attachDropdown();
        attachLogout();
    }

    // Attach event listeners
    hook();
})();