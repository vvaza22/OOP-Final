(function() {

    var origText = document.getElementById("text-about-me");
    var editText = document.getElementById("write-about-me");
    var cancelButt = document.getElementById("cancel-button");
    var saveButt = document.getElementById("save-button");
    var editButt = document.getElementById("edit-about-me");

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
            origText.textContent = editText.value;

            origText.style.display = "block";

            editButt.style.display = "block";

            editText.style.display = "none";

            cancelButt.style.display = "none";

            saveButt.style.display = "none";
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
    }

    hook();
})();