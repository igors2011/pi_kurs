document.getElementById("open_menu").addEventListener("click", openCloseMenu, false)

if (document.documentElement.clientWidth < 1000)
{
    var menu = document.getElementById("menu_list");
    menu.className = "nav_hide";
}

function openCloseMenu() {
    var menu = document.getElementById("menu_list");
    if (menu.className != "nav") {
        menu.className = "nav";
    }
    else {
        menu.className = "nav_hide";
    }
}