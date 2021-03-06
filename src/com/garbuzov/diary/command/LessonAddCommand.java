package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.LessonService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public class LessonAddCommand implements Command {

    private static LessonService lessonService = new LessonService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String MESSAGE = "grade_success_added";
    private final static String TEACHER_ID = "teacherId";
    private final static String GRADE = "grade";
    private final static String SUBJECTS = "subjects[]";
    private final static String GRADES = "grades";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(TEACHER_PAGE_PATH);
        long teacherId = Long.parseLong(request.getParameter(TEACHER_ID));
        long gradeId = Long.parseLong(request.getParameter(GRADE));
        String[] subjectsId = request.getParameterValues(SUBJECTS);
        try {
            lessonService.add(teacherId, gradeId, subjectsId);
            Map<Grade, ArrayList<Subject>> gradeMap = lessonService.findAllGrades(teacherId);
            request.getSession().setAttribute(GRADES, gradeMap);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
