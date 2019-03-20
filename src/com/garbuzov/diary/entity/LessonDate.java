package com.garbuzov.diary.entity;

import java.sql.Date;

public class LessonDate extends Entity {
    private long dateId;
    private Date date;

    public LessonDate() {}

    public LessonDate(Date date) {
        this.date = date;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LessonDate that = (LessonDate) o;

        return date != null ? date.equals(that.date) : that.date == null &&
               dateId != that.dateId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (dateId ^ (dateId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
