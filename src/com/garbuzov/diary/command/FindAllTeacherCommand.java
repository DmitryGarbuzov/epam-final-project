package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TeacherService;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllTeacherCommand implements Command {

    private static TeacherService teacherService = new TeacherService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String MESSAGE = "show_delete_teacher_block";
    private final static String TEACHER_LIST = "teacherList";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setMessage(MESSAGE);
        transition.setPage(ADMIN_PAGE_PATH);
        transition.setRedirectType();
        try {
            List<Teacher> teacherList = teacherService.findAll();
            teacherList.sort(Comparator.comparing(Teacher::getLastName).thenComparing(Teacher::getFirstName));
            request.getSession().setAttribute(TEACHER_LIST, teacherList);
        } catch (ServiceException e) {
            //log
            //transition to error page
        }
        return transition;
    }
}
