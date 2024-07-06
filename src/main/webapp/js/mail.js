(function() {

    function sendRequest(id, status, callback) {
        // Create XHR object
        var xhr = new XMLHttpRequest();

        // We need to send POST request to /login
        xhr.open("POST", "/mail", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        // Listen for the state change
        xhr.onreadystatechange = function() {
            if(this.readyState === 4 && this.status === 200) {
                // Read the server response
                var response = JSON.parse(xhr.responseText);
                if(response.status === "success") {
                    callback();
                } else {

                }
            }
        }

        // Finally, send the request
        xhr.send("request_id="+id+"&status="+status);
    }

    function reloadPage(){
        location.reload();
    }

    window.acceptReq = function acceptReq(id){
        sendRequest(id, "ACCEPTED", reloadPage);
    }

    window.rejectReq = function rejectReq(id){
        sendRequest(id, "REJECTED", reloadPage);
    }

    window.acceptChal = function acceptChal(id, quiz_id){
        sendRequest(id, "CHL_ACCEPTED", function(){
            location.href = "/about_quiz?id=" + quiz_id;
        });
    }

    window.rejectChal = function rejectChal(id){
        sendRequest(id, "CHL_REJECTED", reloadPage);
    }

})();