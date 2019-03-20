package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.HomeworkService;
import com.garbuzov.diary.service.TimetableService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ForwardHomeworkCommand implements Command {

    private static TimetableService timetableService = new TimetableService();
    private static HomeworkService homeworkService = new HomeworkService();
    private final static String TEACHER_PAGE_PATH = "jsp/parent.jsp";
    private final static String MESSAGE = "show_homework_block";
    private final static String FORWARD = "forward";
    private final static String DATE = "date";
    private final static String DEFAULT_DATE = "0001-01-01";
    private final static String EMPTY_STRING = "";
    private final static String DATE_PATTERN = "uuuu-MM-dd";
    private final static String GRADE_ID = "gradeId";
    private final static String SUBJECT_LIST = "subjectList";
    private final static String LAST_COMMAND = "lastCommand";
    private final static String DATE_LIST = "dateList";
    private final static String STUDENT_HOMEWORK = "studentHomework";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(TEACHER_PAGE_PATH);
        String date = DEFAULT_DATE;
        if (!request.getParameter(DATE).equals(EMPTY_STRING)) {
            date = request.getParameter(DATE);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);
        long gradeId = (long)request.getSession().getAttribute(GRADE_ID);
        List<Subject> subjectList = (List<Subject>)request.getSession().getAttribute(SUBJECT_LIST);
        try {
            List<LessonDate> dateList = timetableService.findNextLessons(sqlDate, gradeId);
            List<ArrayList<String>> studentHomework = homeworkService.findForStudent(dateList, subjectList, gradeId);
            request.getSession().setAttribute(DATE_LIST, dateList);
            request.getSession().setAttribute(STUDENT_HOMEWORK, studentHomework);
            request.getSession().setAttribute(LAST_COMMAND, FORWARD);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        transition.setMessage(MESSAGE);
        return transition;
    }
}
