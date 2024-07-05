(function() {

    function publish(text) {

    }

    function sendRequest(text) {

        // Create XHR Object
        const xhr = new XMLHttpRequest();

        // We need to send POST logout request to the servlet
        xhr.open("POST", "/publish", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Parse the server response
                var response = JSON.parse(this.responseText);
                // If the request was successful
                publish(response);
            }
        }

        // Finally send the request
        xhr.send("text="+text);
    }

    function attachPublish() {
        var publish_btn = document.getElementById("anno_publish_btn");
        if(publish_btn != null) {
            publish_btn.onclick = function (e) {
                var publish_text = document.getElementById("anno_text");
                sendRequest(publish_text);
            };
        }
    }

    function hook() {
        attachPublish();
    }

    // Attach event listeners
    hook();
})();