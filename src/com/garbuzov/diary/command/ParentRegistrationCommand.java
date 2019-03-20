package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.MailException;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.generator.PasswordGenerator;
import com.garbuzov.diary.mail.MailSender;
import com.garbuzov.diary.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class ParentRegistrationCommand implements Command {

    private static UserService userService = new UserService();
    private static ParentService parentService = new ParentService();
    private final static String EMAIL = "email";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String STUDENTS = "students[]";
    private final static String WRONG_EMAIL = "email_error";
    private final static String SUCCESS_ADD = "user_success_add";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static int PASSWORD_SIZE = 8;
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        String email = request.getParameter(EMAIL);
        transition.setRedirectType();
        transition.setPage(ADMIN_PAGE_PATH);
        try {
            if (userService.isPresent(email)) {
                transition.setMessage(WRONG_EMAIL);
            } else {
                String firstName = request.getParameter(FIRST_NAME);
                String lastName = request.getParameter(LAST_NAME);
                String[] studentsId = request.getParameterValues(STUDENTS);
                String password = PasswordGenerator.getInstance().generate(PASSWORD_SIZE);
                int hashPassword = password.hashCode();
                parentService.add(email, firstName, lastName, studentsId, hashPassword);
                transition.setMessage(SUCCESS_ADD);
                MailSender.getInstance().send(email, firstName, lastName, password);
            }
        } catch (ServiceException | MailException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
