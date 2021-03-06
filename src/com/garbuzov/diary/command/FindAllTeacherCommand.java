package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TeacherService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllTeacherCommand implements Command {

    private static TeacherService teacherService = new TeacherService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String MESSAGE = "show_delete_teacher_block";
    private final static String TEACHER_LIST = "teacherList";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

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
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
