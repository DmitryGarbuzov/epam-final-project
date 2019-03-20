package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.*;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SignUpCommand implements Command {

    private static UserService userService = new UserService();
    private static ParentService parentService = new ParentService();
    private static TeacherService teacherService = new TeacherService();
    private static AdminService adminService = new AdminService();
    private static LessonService lessonService = new LessonService();
    private final static String EMAIL = "email";
    private final static String PASS = "password";
    private final static String START_PAGE_PATH = "jsp/start.jsp";
    private final static String PARENT_PAGE_PATH = "jsp/parent.jsp";
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String WRONG_USER_MESSAGE = "wrong_user_message";
    private final static String INCORRECT_DATA_MESSAGE = "incorrect_data_message";
    private final static String HAS_NO_ACTIVE_SUBJECT = "has_no_active_subject";
    private final static String HAS_NO_ACTIVE_STUDENT = "has_no_active_student";
    private final static String EMAIL_PATTERN = "[a-z0-9._]{2,20}@[a-z0-9._]{1,10}\\.[a-z]{2,5}";
    private final static String PASS_PATTERN = "[\\w]{8,19}";
    private final static String ROLE = "role";
    private final static String USER = "user";
    private final static String GRADES = "grades";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        String email = request.getParameter(EMAIL);
        String pass = request.getParameter(PASS);
        transition.setRedirectType();
        if (!email.matches(EMAIL_PATTERN) || !pass.matches(PASS_PATTERN)) {
            transition.setPage(START_PAGE_PATH);
            transition.setMessage(INCORRECT_DATA_MESSAGE);
        } else {
            try {
                Optional<User> userOptional = userService.findUser(email, pass.hashCode());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (parentService.isParent(user.getId())) {
                        Parent parent = parentService.createParent(user);
                        transition.setPage(PARENT_PAGE_PATH);
                        request.getSession().setAttribute(ROLE, UserType.PARENT);
                        if (!parentService.hasActiveStudent(user.getId())) {
                            transition.setMessage(HAS_NO_ACTIVE_STUDENT);
                        }
                        request.getSession().setAttribute(USER, parent);
                    } else if (teacherService.isTeacher(user.getId())) {
                        Teacher teacher = teacherService.createTeacher(user);
                        transition.setPage(TEACHER_PAGE_PATH);
                        request.getSession().setAttribute(ROLE, UserType.TEACHER);
                        if (!teacherService.hasActiveStudent(user.getId())) {
                            transition.setMessage(HAS_NO_ACTIVE_SUBJECT);
                        }
                        request.getSession().setAttribute(USER, teacher);
                        Map<Grade, ArrayList<Subject>> gradeMap = lessonService.findAllGrades(teacher.getTeacherId());
                        request.getSession().setAttribute(GRADES, gradeMap);
                    } else if (adminService.isAdmin(user.getId())) {
                        request.getSession().setAttribute(ROLE, UserType.ADMIN);
                        transition.setPage(ADMIN_PAGE_PATH);
                    }
                } else {
                    transition.setPage(START_PAGE_PATH);
                    transition.setMessage(WRONG_USER_MESSAGE);
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                request.getSession().setAttribute(ERROR, e);
                transition.setPage(ERROR_PAGE_PATH);
            }
        }
        return transition;
    }
}
