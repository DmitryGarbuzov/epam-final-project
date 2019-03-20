package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Parent;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.ParentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllParentCommand implements Command {

    private static ParentService parentService = new ParentService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String MESSAGE = "show_delete_parent_block";
    private final static String PARENT_LIST = "parentList";
    private final static String ERROR_PAGE_PATH = "jsp/error.jsp";
    private final static String ERROR = "error";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setMessage(MESSAGE);
        transition.setPage(ADMIN_PAGE_PATH);
        transition.setRedirectType();
        try {
            List<Parent> parentList = parentService.findAll();
            parentList.sort(Comparator.comparing(Parent::getLastName).thenComparing(Parent::getFirstName));
            request.getSession().setAttribute(PARENT_LIST, parentList);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(ERROR, e);
            transition.setPage(ERROR_PAGE_PATH);
        }
        return transition;
    }
}
