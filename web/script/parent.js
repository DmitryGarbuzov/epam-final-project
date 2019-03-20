
$(document).ready(function() {
    var marks = document.querySelectorAll('.mark_div');
    for (var i = 0; i < marks.length; i += 1) {
        var text = marks[i].innerText;
        if (text == 0) {
            marks[i].innerHTML = "";
            marks[i].style.backgroundColor = "#4CAF50";
        } else if (text == -1) {
            marks[i].innerHTML = "н";
            marks[i].style.backgroundColor = "#e74c3c";
        } else if (text == -2) {
            marks[i].innerHTML = "";
            marks[i].style.backgroundColor = "#2b3b4e";
        } else if (text <= 4){
            marks[i].style.backgroundColor = "#ffdb58";
        } else {
            marks[i].style.backgroundColor = "#4CAF50";
        }
    }

    var homework = document.querySelectorAll('.homework_div');
    for (i = 0; i < homework.length; i += 1) {
        text = homework[i].innerText;
        if (text == "undefined") {
            homework[i].innerHTML = "";
            homework[i].style.backgroundColor = "#2b3b4e";
        } else if (text !="-") {
            homework[i].innerHTML = "+";
            homework[i].style.backgroundColor = "#4CAF50";
        } else {
            homework[i].innerHTML = "-";
            homework[i].style.backgroundColor = "#e74c3c";
        }
    }
});

var modal = document.getElementById('homework_modal');
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

window.history.pushState({}, "");
window.onpopstate = function() {
    history.go(0);
};