package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.GradeService;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllGradeCommand implements Command {
    private static GradeService gradeService = new GradeService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String TARGET = "target";
    private final static String ADDING_MESSAGE = "show_add_student_block";
    private final static String REMOVING_MESSAGE = "show_delete_grade_block";
    private final static String RECOVERY_MESSAGE = "show_recover_grade_block";
    private final static String FOR_ADDING = "for_adding";
    private final static String FOR_REMOVING = "for_removing";
    private final static String FOR_RECOVERY = "for_recovery";
    private final static String GRADE_LIST = "gradeList";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(ADMIN_PAGE_PATH);
        transition.setRedirectType();
        boolean isActive = true;
        switch (request.getParameter(TARGET)) {
            case FOR_ADDING: transition.setMessage(ADDING_MESSAGE); break;
            case FOR_REMOVING: transition.setMessage(REMOVING_MESSAGE); break;
            case FOR_RECOVERY:
                transition.setMessage(RECOVERY_MESSAGE);
                isActive = false;
                break;
        }
        try {
            List<Grade> gradeList = gradeService.findAll(isActive);
            gradeList.sort(Comparator.comparing(Grade::getNumber).thenComparing(Grade::getLetter));
            request.getSession().setAttribute(GRADE_LIST, gradeList);
        } catch (ServiceException e) {
            //log
            //transition to error page
        }
        return transition;
    }
}
