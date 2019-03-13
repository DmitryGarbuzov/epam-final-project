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

    public String toString() {
        return getLastName() + " " +getFirstName() + " (" + getEmail() + ")";
    }
}
