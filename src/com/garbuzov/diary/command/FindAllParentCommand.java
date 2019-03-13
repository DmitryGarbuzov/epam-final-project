package com.garbuzov.diary.command;

import com.garbuzov.diary.entity.Parent;
import com.garbuzov.diary.exception.ServiceException;
import com.garbuzov.diary.service.ParentService;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class FindAllParentCommand implements Command {

    private static ParentService parentService = new ParentService();
    private final static String ADMIN_PAGE_PATH = "jsp/admin.jsp";
    private final static String MESSAGE = "show_delete_parent_block";
    private final static String PARENT_LIST = "parentList";

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
            //log
            //transition to error page
        }
        return transition;
    }
}
