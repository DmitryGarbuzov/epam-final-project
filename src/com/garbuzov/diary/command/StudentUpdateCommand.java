package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.StudentService;
import javax.servlet.http.HttpServletRequest;

public class StudentUpdateCommand implements Command {

    private static StudentService studentService = new StudentService();
    private final static String STUDENTS = "students[]";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String RESTORE_MESSAGE = "student_restore";
    private final static String DELETE_MESSAGE = "student_delete";
    private final static String TARGET = "target";
    private final static String DELETE = "delete";

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

        }

        return transition;
    }
}
