package com.garbuzov.diary.filter;

import com.garbuzov.diary.command.UserType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/jsp/*")
public class UserFilter extends HttpFilter {

    private final static String ROLE = "role";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        UserType userType = (UserType) request.getSession().getAttribute(ROLE);
        if (requestURI != null && !requestURI.equals(userType.getUserURI())) {
            request.getRequestDispatcher(userType.getPagePath()).forward(request, response);
        }
        chain.doFilter(request, response);
    }
}
