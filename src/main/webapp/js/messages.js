function loadMessages() {
    let messages = document.getElementsByClassName("closebtn");
    for (let i = 0; i < messages.length; i++) {
        messages[i].addEventListener("click", closeMessage);
    }
}

function createElement(className, strong, message) {
    let div = document.createElement("div");
    div.classList.add("alert", className);
    let span = document.createElement("span");
    span.classList.add("closebtn");
    span.addEventListener("click", closeMessage);
    span.innerHTML = '&times;';
    $(div).append(span);
    $(div).append("<strong>  " + strong + "</strong>" + message);
    $(".alert-boxes")[0].prepend(div);
}

function closeMessage() {
    let div = this.parentElement;
    div.style.opacity = "0";
    setTimeout(function () {
        div.style.display = "none";
    }, 600);
}