package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.HomeworkService;
import com.garbuzov.diary.service.SubjectService;
import com.garbuzov.diary.service.TimetableService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FindHomeworkCommand implements Command {

    private static SubjectService subjectService = new SubjectService();
    private static TimetableService timetableService = new TimetableService();
    private static HomeworkService homeworkService = new HomeworkService();
    private final static String PARENT_PAGE_PATH = "jsp/parent.jsp";
    private final static String MESSAGE = "show_homework_block";
    private final static String STUDENT_ID = "studentId";
    private final static String GRADE_ID = "gradeId";
    private final static String SUBJECT_LIST = "subjectList";
    private final static String DATE_LIST = "dateList";
    private final static String STUDENT_HOMEWORK = "studentHomework";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(PARENT_PAGE_PATH);
        long studentId = Long.parseLong(request.getParameter(STUDENT_ID));
        long gradeId = Long.parseLong(request.getParameter(GRADE_ID));
        try {
            List<Subject> subjectList = subjectService.findForStudent(studentId);
            List<LessonDate> dateList = timetableService.findLastLessons(gradeId);
            List<ArrayList<String>> studentHomework = homeworkService.findForStudent(dateList, subjectList, gradeId);
            request.getSession().setAttribute(SUBJECT_LIST, subjectList);
            request.getSession().setAttribute(DATE_LIST, dateList);
            request.getSession().setAttribute(STUDENT_HOMEWORK, studentHomework);
            request.getSession().setAttribute(STUDENT_ID, studentId);
            request.getSession().setAttribute(GRADE_ID, gradeId);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
