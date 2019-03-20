package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class BackJournalCommand implements Command {

    private static TimetableService timetableService = new TimetableService();
    private static HomeworkService homeworkService = new HomeworkService();
    private static MarkService markService = new MarkService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String MESSAGE = "show_journal_block";
    private final static String BACK = "back";
    private final static String DATE = "date";
    private final static String EMPTY_STRING = "";
    private final static String GRADE_ID = "gradeId";
    private final static String LAST_COMMAND = "lastCommand";
    private final static String DATE_LIST = "dateList";
    private final static String STUDENTS_MARKS = "studentsMarks";
    private final static String STUDENT_LIST = "studentList";
    private final static String SUBJECT_ID = "subjectId";
    private final static String HOMEWORK_LIST = "homeworkList";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(TEACHER_PAGE_PATH);
        long dateId = Integer.MAX_VALUE;
        if (!request.getParameter(DATE).equals(EMPTY_STRING)) {
            dateId = Long.parseLong(request.getParameter(DATE));
        }
        List<Student> studentList = (List<Student>)request.getSession().getAttribute(STUDENT_LIST);
        long gradeId = (long)request.getSession().getAttribute(GRADE_ID);
        long subjectId = (long)request.getSession().getAttribute(SUBJECT_ID);
        try {
            List<LessonDate> dateList = timetableService.findPreviousLessons(dateId, gradeId, subjectId);
            List<ArrayList<Integer>> studentsMarks = markService.find(dateList, studentList);
            List<String> homeworkList = homeworkService.findAll(dateList);
            request.getSession().setAttribute(DATE_LIST, dateList);
            request.getSession().setAttribute(STUDENTS_MARKS, studentsMarks);
            request.getSession().setAttribute(HOMEWORK_LIST, homeworkList);
            request.getSession().setAttribute(LAST_COMMAND, BACK);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
