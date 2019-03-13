package com.garbuzov.diary.command;

public enum  UserType {

    GUEST("start.jsp", "/jsp/start.jsp"), ADMIN("admin.jsp", "/jsp/admin.jsp"),
    PARENT("parent.jsp", "/jsp/parent.jsp"), TEACHER("teacher.jsp", "/jsp/teacher.jsp");

    private String pagePath;
    private String userURI;

    UserType(String pagePath, String userURI) {
        this.pagePath = pagePath;
        this.userURI = userURI;
    }

    public String getPagePath() {
        return pagePath;
    }

    public String getUserURI() {
        return userURI;
    }
}