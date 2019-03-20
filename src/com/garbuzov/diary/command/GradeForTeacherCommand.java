package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.GradeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class GradeForTeacherCommand implements Command {

    private static GradeService gradeService = new GradeService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String ADDING_MESSAGE = "show_add_grade_block";
    private final static String GRADE_LIST = "gradeList";
    private final static String TEACHER_ID = "teacherId";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long teacherId = Long.parseLong(request.getParameter(TEACHER_ID));
        transition.setMessage(ADDING_MESSAGE);
        try {
            List<Grade> gradeList = gradeService.gradeForTeacher(teacherId);
            gradeList.sort(Comparator.comparing(Grade::getNumber).thenComparing(Grade::getLetter));
            request.getSession().setAttribute(GRADE_LIST, gradeList);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
