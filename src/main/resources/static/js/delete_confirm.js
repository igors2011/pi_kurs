document.getElementById("open_close_deletion_form").addEventListener("click", confirmDeletionPopup, false)
document.getElementById("delete_abortion").addEventListener("click", confirmDeletionPopup, false)


function confirmDeletionPopup() {
    var deletionForm = document.getElementById("deletion_form");
    if (deletionForm.className != "deletion_form") {
        deletionForm.className = "deletion_form";
    }
    else {
        deletionForm.className = "deletion_form_hidden"
    }
}