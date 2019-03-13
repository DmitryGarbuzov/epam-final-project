package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.GradeService;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class GradeForTeacherCommand implements Command {

    private static GradeService gradeService = new GradeService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String TARGET = "target";
    private final static String ADDING_MESSAGE = "show_add_grade_block";
    private final static String FOR_ADDING = "for_adding";
    private final static String FOR_REMOVING = "for_removing";
    private final static String FOR_RECOVERY = "for_recovery";
    private final static String GRADE_LIST = "gradeList";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long teacherId = Long.parseLong(request.getParameter("teacherId"));
        transition.setMessage(ADDING_MESSAGE);
        boolean isActive = true;
        try {
            List<Grade> gradeList = gradeService.gradeForTeacher(teacherId);
            gradeList.sort(Comparator.comparing(Grade::getNumber).thenComparing(Grade::getLetter));
            request.getSession().setAttribute(GRADE_LIST, gradeList);
        } catch (ServiceException e) {
            //log
            //transition to error page
        }
        return transition;
    }
}
