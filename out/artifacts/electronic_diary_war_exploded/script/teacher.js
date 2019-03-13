
var blockList = ["#add_grade_block", "#journal"];

$(function () {
    $("#lesson_deletion").click(function(){
        for (var i = 0; i < blockList.length; i += 1) {
            $(blockList[i]).css({display: 'none'});
        }
        $("#delete_grade_block").css({display: 'block'});
    });
});

$(document).ready(function() {
    $('.select2').select2();

    var elements = document.querySelectorAll('.mark_div');
    for (var i = 0; i < elements.length; i += 1) {
        var text = elements[i].innerText;
        if (text == 0) {
            elements[i].innerHTML = "";
            elements[i].style.backgroundColor = "green";
        } else if (text == -1) {
            elements[i].innerHTML = "н";
            elements[i].style.backgroundColor = "red";
        } else {
            elements[i].style.backgroundColor = "green";
        }
    }
});

window.history.pushState({}, "");
window.onpopstate = function() {
    history.go(1);
};

var modal = document.getElementById('add_lesson_modal');
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};