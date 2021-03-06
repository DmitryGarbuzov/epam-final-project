package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.GradeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class GradeAddCommand implements Command {

    private static GradeService gradeService = new GradeService();
    private final static String GRADE_NUMBER = "number";
    private final static String GRADE_LETTER = "letter";
    private final static String WRONG_GRADE = "wrong_grade";
    private final static String SUCCESS_ADD = "grade_success_add";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        Integer number = Integer.parseInt(request.getParameter(GRADE_NUMBER));
        String letter = request.getParameter(GRADE_LETTER);
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        try {
            if (gradeService.isPresent(number, letter)) {
                transition.setMessage(WRONG_GRADE);
            } else {
                gradeService.add(number, letter);
                transition.setMessage(SUCCESS_ADD);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
