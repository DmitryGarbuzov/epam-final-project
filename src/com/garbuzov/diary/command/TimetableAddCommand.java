package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TimetableService;

import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimetableAddCommand implements Command {

    private static TimetableService timetableService = new TimetableService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long lessonId = Long.parseLong(request.getParameter("lessonId"));
        String date = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);
        try {
            timetableService.add(lessonId, sqlDate);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
