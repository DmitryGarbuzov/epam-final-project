package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.StudentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class StudentUpdateCommand implements Command {

    private static StudentService studentService = new StudentService();
    private final static String STUDENTS = "students[]";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String RESTORE_MESSAGE = "student_restore";
    private final static String DELETE_MESSAGE = "student_delete";
    private final static String TARGET = "target";
    private final static String DELETE = "delete";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        String[] studentsId = request.getParameterValues(STUDENTS);
        String target = request.getParameter(TARGET);
        boolean isActive = true;
        if (target.equals(DELETE)) {
            isActive = false;
            transition.setMessage(DELETE_MESSAGE);
        } else {
            transition.setMessage(RESTORE_MESSAGE);
        }
        try {
            studentService.update(studentsId, isActive);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
