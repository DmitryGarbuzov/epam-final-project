package com.garbuzov.diary.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Transition execute(HttpServletRequest request);
}
