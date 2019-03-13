package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.*;
import javax.servlet.http.HttpServletRequest;

public class StudentAddCommand implements Command {

    private static StudentService studentService = new StudentService();
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String GRADE = "grade";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String MESSAGE = "student_add";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        Long gradeId = Long.parseLong(request.getParameter(GRADE));
        try {
            studentService.add(firstName, lastName, gradeId);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
