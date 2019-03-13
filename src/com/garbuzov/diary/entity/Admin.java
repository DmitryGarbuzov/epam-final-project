package com.garbuzov.diary.entity;

public class Admin extends User {
    private long adminId;

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }
}
