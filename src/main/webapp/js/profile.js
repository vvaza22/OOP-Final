(function() {

    var origText = document.getElementById("text-about-me");
    var editText = document.getElementById("write-about-me");
    var cancelButt = document.getElementById("cancel-button");
    var saveButt = document.getElementById("save-button");
    var editButt = document.getElementById("edit-about-me");

    function changeProfilePicture() {
        var changePicButt = document.getElementById("change-profile-pic");
        var profilePicURL = document.getElementById("profile-picture");
        changePicButt.onclick = function (e) {
            e.preventDefault();
            let imageLink = prompt("please enter your new profile picture link", "");
            if (imageLink != null) {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/profile", true);
                xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        profilePicURL.src = imageLink;
                    }
                }
                xhr.send("profilePictureLink=" + imageLink);
            }
            location.reload();
        }
    }


    function cancelButton(){
        cancelButt.onclick = function (e){
            e.preventDefault();
            editText.value = origText.textContent;
            origText.style.display = "block";
            editButt.style.display = "block";
            editText.style.display = "none";
            cancelButt.style.display = "none";
            saveButt.style.display = "none";
        }
    }


    function saveButton(){
        saveButt.onclick = function (e){
            e.preventDefault();
            var aboutMeText = editText.value;

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/profile", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if(xhr.readyState === 4 && xhr.status === 200){
                    origText.textContent = editText.value;
                    origText.style.display = "block";
                    editText.style.display = "none";
                    editButt.style.display = "block";
                    cancelButt.style.display = "none";
                    saveButt.style.display = "none";
                }
            }
            xhr.send("aboutMe="+aboutMeText);
        }
    }

    function editButton(){
        editButt.onclick = function (e){
            e.preventDefault();
            origText.style.display = "none";
            editText.style.display = "block";
            editButt.style.display = "none";
            cancelButt.style.display = "block";
            saveButt.style.display = "block";
        }
    }

    function hook(){
        editButton();
        saveButton();
        cancelButton();
        changeProfilePicture();
    }

    hook();
})();