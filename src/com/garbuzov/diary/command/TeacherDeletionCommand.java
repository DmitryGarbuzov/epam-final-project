package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TeacherService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class TeacherDeletionCommand implements Command {

    private static TeacherService teacherService = new TeacherService();
    private final static String TEACHERS = "teachers[]";
    private final static String MESSAGE = "user_delete";
    private final static String ERROR = "error";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private static Logger logger = LogManager.getLogger();

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
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
