package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TeacherService;
import javax.servlet.http.HttpServletRequest;

public class TeacherDeletionCommand implements Command {

    private static TeacherService teacherService = new TeacherService();
    private final static String TEACHERS = "teachers[]";
    private final static String MESSAGE = "user_delete";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        try {
            String[] usersId = request.getParameterValues(TEACHERS);
            teacherService.delete(usersId);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
