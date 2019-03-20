package com.garbuzov.diary.servlet;

import com.garbuzov.diary.command.Command;
import com.garbuzov.diary.command.CommandMap;
import com.garbuzov.diary.command.Transition;
import com.garbuzov.diary.command.UserType;
import com.garbuzov.diary.connection.ConnectionPool;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class MainServlet extends HttpServlet {

    private final String COMMAND = "command";
    private final String MESSAGE = "message";
    private final String UTF_8 = "UTF-8";
    private final String START_PAGE_PATH = "jsp/start.jsp";
    private final String ROLE = "role";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ROLE, UserType.GUEST);
        response.sendRedirect(START_PAGE_PATH);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(UTF_8);
        String cmdString = request.getParameter(COMMAND);
        Command command = CommandMap.getInstance().get(cmdString);
        Transition transition = command.execute(request);
        if (transition.isMessagePresent()) {
            request.getSession().setAttribute(MESSAGE, transition.getMessage());
        }
        switch (transition.getTransitionType()) {
            case FORWARD: request.getRequestDispatcher(transition.getPage()).forward(request, response); break;
            case REDIRECT: response.sendRedirect(transition.getPage()); break;
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnections();
    }
}