package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.LessonService;
import com.garbuzov.diary.service.MarkService;
import com.garbuzov.diary.service.StudentService;
import com.garbuzov.diary.service.TimetableService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FindJournalCommand implements Command {

    private static StudentService studentService = new StudentService();
    private static TimetableService timetableService = new TimetableService();
    private static LessonService lessonService = new LessonService();
    private static MarkService markService = new MarkService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(TEACHER_PAGE_PATH);
        long gradeId = Long.parseLong(request.getParameter("gradeId"));
        long teacherId = Long.parseLong(request.getParameter("teacherId"));
        long subjectId = Long.parseLong(request.getParameter("subjectId"));
        try {
            List<Student> studentList = studentService.findByGradeId(gradeId);
            List<LessonDate> dateList = timetableService.findLastTen(gradeId, subjectId);
            ArrayList<ArrayList<Integer>> studentsMarks = markService.find(dateList, studentList, subjectId);
            long lessonId = lessonService.findLessonId(gradeId, teacherId, subjectId);
            request.getSession().setAttribute("studentList", studentList);
            request.getSession().setAttribute("dateList", dateList);
            request.getSession().setAttribute("studentsMarks", studentsMarks);
            request.getSession().setAttribute("lessonId", lessonId);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
