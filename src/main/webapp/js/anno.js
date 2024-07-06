(function() {
    function sendRequest(annoData) {
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
                if(response.status === "success") {
                    // TODO: is it correct way?:D works tho.
                    location.href="/home";
                } else {
                    alert(response.errorMsg);
                }
            }
        }

        // Finally send the request
        xhr.send("data="+JSON.stringify(annoData));
    }

    function grabAnnoData() {
        var anno_text = document.getElementById("anno_text").value;

        let data = {};

        //TODO:
        const title = "ATTENTION EVERYONE!";
        data["anno_title"] = title;

        const text = anno_text;
        data["anno_text"] = text;

        return data;
    }



    function attachPublish() {
        var publish_btn = document.getElementById("anno_publish_btn");
        if(publish_btn != null) {
            publish_btn.onclick = function (e) {
                sendRequest(grabAnnoData());
            };
        }
    }

    function hook() {
        attachPublish();
    }

    // Attach event listeners
    hook();
})();