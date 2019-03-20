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

public class FindJournalCommand implements Command {

    private static StudentService studentService = new StudentService();
    private static TimetableService timetableService = new TimetableService();
    private static LessonService lessonService = new LessonService();
    private static HomeworkService homeworkService = new HomeworkService();
    private static MarkService markService = new MarkService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String MESSAGE = "show_journal_block";
    private final static String GRADE_ID = "gradeId";
    private final static String DATE_LIST = "dateList";
    private final static String STUDENTS_MARKS = "studentsMarks";
    private final static String STUDENT_LIST = "studentList";
    private final static String SUBJECT_ID = "subjectId";
    private final static String HOMEWORK_LIST = "homeworkList";
    private final static String TEACHER_ID = "teacherId";
    private final static String LESSON_ID = "lessonId";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(TEACHER_PAGE_PATH);
        long gradeId = Long.parseLong(request.getParameter(GRADE_ID));
        long teacherId = Long.parseLong(request.getParameter(TEACHER_ID));
        long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
        try {
            List<Student> studentList = studentService.findByGradeId(gradeId);
            List<LessonDate> dateList = timetableService.findLastLessons(gradeId, subjectId);
            List<ArrayList<Integer>> studentsMarks = markService.find(dateList, studentList);
            List<String> homeworkList = homeworkService.findAll(dateList);
            long lessonId = lessonService.findLessonId(gradeId, teacherId, subjectId);
            request.getSession().setAttribute(STUDENT_LIST, studentList);
            request.getSession().setAttribute(DATE_LIST, dateList);
            request.getSession().setAttribute(STUDENTS_MARKS, studentsMarks);
            request.getSession().setAttribute(LESSON_ID, lessonId);
            request.getSession().setAttribute(GRADE_ID, gradeId);
            request.getSession().setAttribute(SUBJECT_ID, subjectId);
            request.getSession().setAttribute(HOMEWORK_LIST, homeworkList);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
