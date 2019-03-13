package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.SubjectService;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllSubjectCommand implements Command {

    private static SubjectService subjectService = new SubjectService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String TARGET = "target";
    private final static String ADDING_MESSAGE = "show_add_teacher_block";
    private final static String REMOVING_MESSAGE = "show_delete_subject_block";
    private final static String RECOVERY_MESSAGE = "show_recover_subject_block";
    private final static String FOR_ADDING = "for_adding";
    private final static String FOR_REMOVING = "for_removing";
    private final static String FOR_RECOVERY = "for_recovery";
    private final static String SUBJECT_LIST = "subjectList";

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
            List<Subject> subjectList = subjectService.findAll(isActive);
            subjectList.sort(Comparator.comparing(Subject::getName));
            request.getSession().setAttribute(SUBJECT_LIST, subjectList);
        } catch (ServiceException e) {
            //log
            //transition to error page
        }
        return transition;
    }
}
