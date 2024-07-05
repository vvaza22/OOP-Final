(function() {

    var origText = document.getElementById("text-about-me");
    var editText = document.getElementById("write-about-me");
    var cancelButt = document.getElementById("cancel-button");
    var saveButt = document.getElementById("save-button");
    var editButt = document.getElementById("edit-about-me");
    var addFriendButt = document.getElementById("add_friend");
    var remoFriendButt = document.getElementById("rem_friend");
    var requestButt = document.getElementById("request");

    function addFriend(){
        if(addFriendButt && requestButt){
            addFriendButt.onclick = function (e) {
                var toWhichUserReqSent = document.getElementById("username");
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/profile", true);
                xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        addFriendButt.style.display = "none";
                        remoFriendButt.style.display = "none";
                        requestButt.style.display = "block";
                    }
                }
                xhr.send("friendRequestedUser="+ toWhichUserReqSent);
            }
        }
    }

    function changeProfilePicture() {
        var changePicButt = document.getElementById("change-profile-pic");
        var profilePicURL = document.getElementById("profile-picture");
        if(changePicButt) {
            changePicButt.onclick = function (e) {
                e.preventDefault();
                let imageLink = prompt("please enter your new profile picture link", "");
                if (imageLink != null) {
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/profile", true);
                    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            profilePicURL.src = imageLink;
                        }
                    }
                    xhr.send("profilePictureLink=" + encodeURI(imageLink));
                }
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
                xhr.send("aboutMe=" + aboutMeText);
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
        changeProfilePicture();
        addFriend();
    }

    hook();
})();