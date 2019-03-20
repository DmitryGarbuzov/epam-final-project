package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.StudentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllStudentCommand implements Command {

    private static StudentService studentService = new StudentService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String TARGET = "target";
    private final static String ADDING_MESSAGE = "show_add_parent_block";
    private final static String REMOVING_MESSAGE = "show_delete_student_block";
    private final static String RECOVERY_MESSAGE = "show_recover_student_block";
    private final static String FOR_ADDING = "for_adding";
    private final static String FOR_REMOVING = "for_removing";
    private final static String FOR_RECOVERY = "for_recovery";
    private final static String STUDENT_LIST = "studentList";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(ADMIN_PAGE_PATH);
        transition.setRedirectType();
        boolean isActive = true;
        switch (request.getParameter(TARGET)) {
            case FOR_ADDING: transition.setMessage(ADDING_MESSAGE); break;
            case FOR_REMOVING: transition.setMessage(REMOVING_MESSAGE); break;
            case FOR_RECOVERY:
                transition.setMessage(RECOVERY_MESSAGE);
                isActive = false;
                break;
        }
        try {
            List<Student> studentList = studentService.findAll(isActive);
            studentList.sort(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName));
            request.getSession().setAttribute(STUDENT_LIST, studentList);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
