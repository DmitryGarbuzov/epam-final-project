package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.SubjectService;
import javax.servlet.http.HttpServletRequest;

public class SubjectUpdateCommand implements Command {

    private static SubjectService subjectService = new SubjectService();
    private final static String SUBJECTS = "subjects[]";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String RESTORE_MESSAGE = "subject_restore";
    private final static String DELETE_MESSAGE = "subject_delete";
    private final static String TARGET = "target";
    private final static String DELETE = "delete";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        String[] subjectsId = request.getParameterValues(SUBJECTS);
        String target = request.getParameter(TARGET);
        boolean isActive = true;
        if (target.equals(DELETE)) {
            isActive = false;
            transition.setMessage(DELETE_MESSAGE);
        } else {
            transition.setMessage(RESTORE_MESSAGE);
        }
        try {
            subjectService.update(subjectsId, isActive);
        } catch (ServiceException e) {

        }

        return transition;
    }
}
