package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.TimetableService;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimetableDeletionCommand implements Command {


    private static TimetableService timetableService = new TimetableService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String FIND_JOURNAL = "find_journal";
    private final static String WRONG_DATE = "incorrect_date";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long gradeId = Long.parseLong(request.getParameter("gradeId"));
        long subjectId = Long.parseLong(request.getParameter("subjectId"));
        String date = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);
        try {
            if (timetableService.isPresent(sqlDate, gradeId, subjectId)) {
                timetableService.delete(sqlDate, gradeId, subjectId);
                transition.setMessage(FIND_JOURNAL);
            } else {
                transition.setMessage(WRONG_DATE);
            }
        } catch (ServiceException e) {

        }
        return transition;
    }
}
