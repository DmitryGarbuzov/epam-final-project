package com.garbuzov.diary.command;

import javax.servlet.http.HttpServletRequest;

public class LanguageSwitchCommand implements Command {

    private final static String LANGUAGE = "language";
    private final static String CURRENT_PAGE = "current_page";

    @Override
    public Transition execute(HttpServletRequest request) {
        Transition transition = new Transition();
        transition.setRedirectType();
        String language = request.getParameter(LANGUAGE);
        request.getSession().setAttribute(LANGUAGE, language);
        transition.setPage((String)request.getSession().getAttribute(CURRENT_PAGE));
        return transition;
    }
}
