package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.ParentService;
import javax.servlet.http.HttpServletRequest;

public class ParentDeletionCommand implements Command {

    private static ParentService parentService = new ParentService();
    private final static String PARENTS = "parents[]";
    private final static String MESSAGE = "user_delete";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        try {
            String[] usersId = request.getParameterValues(PARENTS);
            parentService.delete(usersId);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
