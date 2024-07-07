(function() {
    function sendPublishRequest(annoData) {
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
                    location.reload();
                } else {
                    alert(response.errorMsg);
                }
            }
        }

        // Finally send the request
        xhr.send("data="+encodeURIComponent(JSON.stringify(annoData)));
    }

    function grabAnnoData() {
        var anno_title = document.getElementById("anno_title").value;
        var anno_text = document.getElementById("anno_text").value;
        let data = {};

        const title = anno_title;
        data["anno_title"] = title;

        const text = anno_text;
        data["anno_text"] = text;

        return data;
    }



    function attachPublish() {
        var publish_btn = document.getElementById("anno_publish_btn");
        if(publish_btn != null) {
            publish_btn.onclick = function (e) {
                sendPublishRequest(grabAnnoData());
            };
        }
    }

    function sendReactRequest(action, anno_id, callback) {
        const xhr = new XMLHttpRequest();

        // We need to send POST logout request to the servlet
        xhr.open("POST", "/home", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Parse the server response
                var response = JSON.parse(this.responseText);
                // If the request was successful
                if(response.status === "success") {
                    callback(response);
                } else {
                    alert(response.errorMsg);
                }
            }
        }

        // Finally send the request
        xhr.send("action="+action+"&anno_id="+anno_id);
    }

    function hook() {
        attachPublish();
        window.action_like=function(anno_id) {
            sendReactRequest("like", anno_id, function(r){
                location.reload();
            });
        }
        window.action_dislike=function(anno_id) {
            sendReactRequest("dislike", anno_id, function(r){
                location.reload();
            });
        }
    }

    // Attach event listeners
    hook();
})();