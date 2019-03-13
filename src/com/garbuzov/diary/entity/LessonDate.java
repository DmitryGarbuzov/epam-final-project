package com.garbuzov.diary.entity;

import com.garbuzov.diary.service.LessonService;

import java.sql.Date;

public class LessonDate {
    private long dateId;
    private Date date;

    public LessonDate() {}

    public LessonDate(long dateId, Date date) {
        this.dateId = dateId;
        this.date = date;
    }

    public long getDateId() {
        return dateId;
    }

    public void setDateId(long dateId) {
        this.dateId = dateId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
