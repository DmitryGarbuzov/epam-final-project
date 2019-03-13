package com.garbuzov.diary.command;

public class Transition {

    private String page;
    private String message;
    private TransitionType transitionType = TransitionType.FORWARD;

    public enum  TransitionType {
        FORWARD, REDIRECT
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public TransitionType getTransitionType() {
        return transitionType;
    }

    public void setRedirectType() {
        transitionType = TransitionType.REDIRECT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMessagePresent() {
        return message != null;
    }
}
