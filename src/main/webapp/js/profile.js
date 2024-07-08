(function() {

    var origText = document.getElementById("text-about-me");
    var editText = document.getElementById("write-about-me");
    var cancelButt = document.getElementById("cancel-button");
    var saveButt = document.getElementById("save-button");
    var editButt = document.getElementById("edit-about-me");

    function getNotePromise(toWhichUserSendingNote, noteToSend) {
        return new Promise(function(resolve, reject) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/profile", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    let response = JSON.parse(xhr.responseText);
                    resolve(response);
                }
            }
            xhr.send("action=sendNote&toWhichUserSent="+ toWhichUserSendingNote + "&noteMessage=" + encodeURIComponent(noteToSend));
        });
    }

    window.sendNote = function(toWhichUserSendingNote) {
        Swal.fire({
            title: "Enter Message: ",
            input: "text",
            inputAttributes: {
                autocapitalize: "off",
                autocomplete: "off"
            },
            showCancelButton: true,
            confirmButtonText: "Send",
            showLoaderOnConfirm: true,
            preConfirm: (message) => {
                return getNotePromise(toWhichUserSendingNote, message);
            }
        }).then(function(result) {
            if(result.isConfirmed) {
                let response = result.value;
                if(response.status === "success") {
                    Swal.fire({
                        icon: "success",
                        title: "Note Sent!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else {
                    Swal.fire({
                        icon: "error",
                        title: "Could not send the note!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            }
        });
    }

    window.removeFriend = function (toWhichUserRemoveFriend){
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/profile", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                location.reload();
            }
        }
        xhr.send("action=remFriend&friendRemUser="+ toWhichUserRemoveFriend);
    }

    window.addFriend = function(toWhichUserReqSent){
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/profile", true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                location.reload();
            }
        }
        xhr.send("action=addFriend&friendRequestedUser="+ toWhichUserReqSent);
    }

    function updatePicture(pictureLink) {
        return new Promise(function(resolve, reject) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/profile", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function () {
                if(xhr.readyState === 4 && xhr.status === 200) {
                    let response = JSON.parse(xhr.responseText);
                    resolve(response);
                }
            }
            xhr.send("action=changePic&profilePictureLink=" + encodeURIComponent(pictureLink));
        });
    }

    function changeProfilePicture() {
        Swal.fire({
            title: "Please enter your new profile picture link: ",
            input: "text",
            inputAttributes: {
                autocapitalize: "off",
                autocomplete: "off"
            },
            showCancelButton: true,
            confirmButtonText: "Update Picture",
            showLoaderOnConfirm: true,
            preConfirm: (pictureLink) => {
                return updatePicture(pictureLink);
            }
        }).then(function(result) {
            if(result.isConfirmed) {
                let response = result.value;
                if(response.status === "success") {
                    location.reload();
                }
            }
        });
    }

    function hookChangeProfilePicture() {
        var changePicButt = document.getElementById("change-profile-pic");
        var profilePicURL = document.getElementById("profile-picture");
        if(changePicButt != null) {
            changePicButt.onclick = function(e) {
                e.preventDefault();
                changeProfilePicture();
            }
        }
    }

    function cancelButton(){
        if(cancelButt) {
            cancelButt.onclick = function (e) {
                e.preventDefault();
                editText.value = origText.textContent;
                origText.style.display = "block";
                editButt.style.display = "block";
                editText.style.display = "none";
                cancelButt.style.display = "none";
                saveButt.style.display = "none";
            }
        }
    }


    function saveButton(){
        if(saveButt) {
            saveButt.onclick = function (e) {
                e.preventDefault();
                var aboutMeText = editText.value;

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/profile", true);
                xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        origText.textContent = editText.value;
                        origText.style.display = "block";
                        editText.style.display = "none";
                        editButt.style.display = "block";
                        cancelButt.style.display = "none";
                        saveButt.style.display = "none";
                    }
                }
                xhr.send("action=edited&aboutMe=" + aboutMeText);
            }
        }
    }

    function editButton(){
        if(editButt) {
            editButt.onclick = function (e) {
                e.preventDefault();
                origText.style.display = "none";
                editText.style.display = "block";
                editButt.style.display = "none";
                cancelButt.style.display = "block";
                saveButt.style.display = "block";
            }
        }
    }



    function hook(){
        editButton();
        saveButton();
        cancelButton();
        hookChangeProfilePicture();
    }

    hook();
})();