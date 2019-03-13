package com.garbuzov.diary.entity;

import java.util.ArrayList;
import java.util.List;

public class Parent extends User {
    private long parentId;
    private List<Student> studentList;

    public Parent() {}

    public Parent( String email, String firstName, String lastName) {
        super(email, firstName, lastName);
        studentList = new ArrayList<>();
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public String toString() {
        return getLastName() + " " +getFirstName() + " (" + getEmail() + ")";
    }
}
