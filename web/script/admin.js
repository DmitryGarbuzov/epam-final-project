
var blockList = ["#add_parent_block", "#add_subject_block", "#add_grade_block", "#add_student_block",
                 "#add_teacher_block", "#delete_parent_block", "#delete_teacher_block", "#delete_student_block",
                 "#delete_grade_block", "#delete_subject_block", "#recover_grade_block", "recover_student_block",
                 "#recover_subject_block"];

$(function () {
    $("#add_subject").click(function(){
        for (var i = 0; i < blockList.length; i += 1) {
            $(blockList[i]).css({display: 'none'});
        }
        $("#add_subject_block").css({display: 'block'});
    });
    $("#add_grade").click(function(){
        for (var i = 0; i < blockList.length; i += 1) {
            $(blockList[i]).css({display: 'none'});
        }
        $("#add_grade_block").css({display: 'block'});
    });
});

$(document).ready(function() {
    $('.select2').select2();
});

window.history.pushState({}, "");
window.onpopstate = function() {
    history.go(1);
};
