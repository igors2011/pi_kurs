window.addEventListener("load", onLoad, false);

function getPathName() {
    var a = document.createElement('a');
    a.href = document.location.href;
    var urlArr = a.pathname.split('/');
    return urlArr[1];
}

function onLoad() {
    var selected_section = document.getElementById(getPathName());
    if (selected_section !== null) {
        selected_section.className = "selected_nav";
    }
}