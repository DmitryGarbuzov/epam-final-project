package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.MarkService;
import javax.servlet.http.HttpServletRequest;

public class MarkAddCommand implements Command {

    private static MarkService markService = new MarkService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String FIND_JOURNAL = "find_journal";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long dateId = Long.parseLong(request.getParameter("dateId"));
        long studentId = Long.parseLong(request.getParameter("studentId"));
        int mark = Integer.parseInt(request.getParameter("mark"));
        try {
            markService.add(dateId, studentId, mark);
            transition.setMessage(FIND_JOURNAL);
        } catch (ServiceException e) {

        }
        return transition;
    }
}
