package com.garbuzov.diary.entity;

import java.util.ArrayList;
import java.util.List;

public class Parent extends User {
    private long parentId;
    private List<Student> studentList = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Parent parent = (Parent) o;

        return studentList != null ? studentList.equals(parent.studentList) : parent.studentList == null &&
               parentId == parent.parentId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (parentId ^ (parentId >>> 32));
        result = 31 * result + (studentList != null ? studentList.hashCode() : 0);
        return result;
    }

    public String toString() {
        return getLastName() + " " +getFirstName() + " (" + getEmail() + ")";
    }
}
