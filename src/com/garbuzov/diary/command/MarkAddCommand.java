package com.garbuzov.diary.command;

import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.MarkService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class MarkAddCommand implements Command {

    private static MarkService markService = new MarkService();
    private final static String TEACHER_PAGE_PATH = "jsp/teacher.jsp";
    private final static String FIND_JOURNAL = "find_journal";
    private final static String STUDENT_ID = "studentId";
    private final static String DATE_ID = "dateId";
    private final static String MARK = "mark";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setPage(TEACHER_PAGE_PATH);
        transition.setRedirectType();
        long dateId = Long.parseLong(request.getParameter(DATE_ID));
        long studentId = Long.parseLong(request.getParameter(STUDENT_ID));
        int mark = Integer.parseInt(request.getParameter(MARK));
        try {
            markService.add(dateId, studentId, mark);
            transition.setMessage(FIND_JOURNAL);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
