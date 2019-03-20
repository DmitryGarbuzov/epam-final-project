package com.garbuzov.diary.entity;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    private long teacherId;
    private List<Subject> subjectList = new ArrayList<>();

    public Teacher() {}

    public Teacher(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public void addSubject(Subject subject) {
        subjectList.add(subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Teacher teacher = (Teacher) o;

        return subjectList != null ? subjectList.equals(teacher.subjectList) : teacher.subjectList == null &&
               teacherId == teacher.teacherId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (teacherId ^ (teacherId >>> 32));
        result = 31 * result + (subjectList != null ? subjectList.hashCode() : 0);
        return result;
    }

    public String toString() {
        return getLastName() + " " +getFirstName() + " (" + getEmail() + ")";
    }
}
