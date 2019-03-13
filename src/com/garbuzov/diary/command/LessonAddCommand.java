package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.LessonService;
import com.garbuzov.diary.service.StudentService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public class LessonAddCommand implements Command {

    private static LessonService lessonService = new LessonService();
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String GRADE = "grade";
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String MESSAGE = "Класс успешно добавлен";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        transition.setPage(TEACHER_PAGE_PATH);
        long teacherId = Long.parseLong(request.getParameter("teacherId"));
        long gradeId = Long.parseLong(request.getParameter("grade"));
        String[] subjectsId = request.getParameterValues("subjects[]");
        try {
            lessonService.add(teacherId, gradeId, subjectsId);
            Map<Grade, ArrayList<Subject>> gradeMap = lessonService.findAllGrades(teacherId);
            request.getSession().setAttribute("grades", gradeMap);
            transition.setMessage(MESSAGE);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
