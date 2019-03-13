package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.*;

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
    private final static String HAS_NO_ACTIVE_SUBJECT = "has_no_active_subject";
    private final static String HAS_NO_ACTIVE_STUDENT = "has_no_active_student";
    private final static String ROLE = "role";
    private final static String USER = "user";
    private final static String GRADES = "grades";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        String email = request.getParameter(EMAIL);
        String pass = request.getParameter(PASS);
        transition.setRedirectType();
        try {
            Optional<User> userOptional = userService.findUser(email, pass.hashCode());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (parentService.isParent(user.getId())) {
                    transition.setPage(PARENT_PAGE_PATH);
                    request.getSession().setAttribute(ROLE, UserType.PARENT);
                    if (!parentService.hasActiveStudent(user.getId())) {
                        transition.setMessage(HAS_NO_ACTIVE_STUDENT);
                    }
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
                transition.setRedirectType();
                transition.setPage(START_PAGE_PATH);
                transition.setMessage(WRONG_USER_MESSAGE);
            }
        } catch (ServiceException e) {

        }
        return transition;
    }
}
