
var blockList = ["#add_grade_block", "#journal_block"];

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

    var marks = document.querySelectorAll('.mark_div');
    for (var i = 0; i < marks.length; i += 1) {
        var text = marks[i].innerText;
        if (text == 0) {
            marks[i].innerHTML = "";
            marks[i].style.backgroundColor = "#4CAF50";
        } else if (text == -1) {
            marks[i].innerHTML = "н";
            marks[i].style.backgroundColor = "#e74c3c";
        } else if (text <= 4){
            marks[i].style.backgroundColor = "#ffdb58";
        } else {
            marks[i].style.backgroundColor = "#4CAF50";
        }
    }

    var homework = document.querySelectorAll('.homework_div');
    for (i = 0; i < homework.length; i += 1) {
        text = homework[i].innerText;
        if (text != "-") {
            homework[i].innerHTML = "+";
            homework[i].style.backgroundColor = "#4CAF50";
        } else {
            homework[i].innerHTML = "-";
            homework[i].style.backgroundColor = "#e74c3c";
        }
    }

    var modal1 = document.getElementById('add_lesson_modal');
    var modal2 = document.getElementById('delete_lesson_modal');
    var modal3 = document.getElementById('add_mark_modal');
    var modal4 = document.getElementById('add_homework_modal');
    window.onclick = function(event) {
        if (event.target === modal1) {
            modal1.style.display = "none";
        }
        if (event.target === modal2) {
            modal2.style.display = "none";
        }
        if (event.target === modal3) {
            modal3.style.display = "none";
        }
        if (event.target === modal4) {
            modal4.style.display = "none";
        }
    }
});

window.history.pushState({}, "");
window.onpopstate = function() {
    history.go(1);
};

function scroll() {
    $("html,body").animate({
        scrollTop: $(window).scrollTop() + 360
    }, 800);
}