package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TimetableService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimetableAddCommand implements Command {

    private static TimetableService timetableService = new TimetableService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String FIND_JOURNAL = "find_journal";
    private final static String WRONG_DATE = "incorrect_add_date";
    private final static String DATE = "date";
    private final static String DATE_PATTERN = "uuuu-MM-dd";
    private final static String GRADE_ID = "gradeId";
    private final static String SUBJECT_ID = "subjectId";
    private final static String LESSON_ID = "lessonId";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long lessonId = Long.parseLong(request.getParameter(LESSON_ID));
        long gradeId = Long.parseLong(request.getParameter(GRADE_ID));
        long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
        String date = request.getParameter(DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);
        try {
            if (timetableService.isValid(sqlDate, gradeId, subjectId)) {
                timetableService.add(lessonId, sqlDate);
                transition.setMessage(FIND_JOURNAL);
            } else {
                transition.setMessage(WRONG_DATE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
