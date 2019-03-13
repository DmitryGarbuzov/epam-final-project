package com.garbuzov.diary.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter("/*")
public class LocaleFilter extends HttpFilter {

    private final static String REFERER = "referer";
    private final static String CURRENT_PAGE = "current_page";
    private final static String PAGE_REGEX = "jsp/.+";
    private final static String LANGUAGE = "language";
    private final static String RU = "ru";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getSession().getAttribute(LANGUAGE) == null) {
            request.getSession().setAttribute(LANGUAGE, RU);
        }
        String referer = request.getHeader(REFERER);
        if (referer != null) {
            Pattern pattern = Pattern.compile(PAGE_REGEX);
            Matcher matcher = pattern.matcher(referer);
            if (matcher.find()) {
                referer = matcher.group();
            }
            request.getSession().setAttribute(CURRENT_PAGE, referer);
        }
        chain.doFilter(request, response);
    }
}
