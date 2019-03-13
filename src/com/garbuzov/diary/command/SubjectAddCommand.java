package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.SubjectService;
import javax.servlet.http.HttpServletRequest;

public class SubjectAddCommand implements Command {
    private static SubjectService subjectService = new SubjectService();
    private final static String SUBJECT_NAME = "name";
    private final static String WRONG_SUBJECT = "wrong_subject";
    private final static String SUCCESS_ADD = "subject_success_add";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        String subject = request.getParameter(SUBJECT_NAME);
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        try {
            if (subjectService.isPresent(subject)) {
                transition.setMessage(WRONG_SUBJECT);
            } else {
                subjectService.add(subject);
                transition.setMessage(SUCCESS_ADD);
            }
        } catch (ServiceException e) {

        }
        return transition;
    }
}
