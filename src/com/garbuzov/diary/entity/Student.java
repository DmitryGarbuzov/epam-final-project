package com.garbuzov.diary.entity;

public class Student extends Entity {

    private long studentId;
    private String firstName;
    private String lastName;
    private Grade grade;

    public Student() {}

    public Student(long studentId) {
        this.studentId = studentId;
    }

    public Student(String firstName, String lastName, Grade grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    public Student(long studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return grade != null ? grade.equals(student.grade) : student.grade == null &&
               firstName != null ? firstName.equals(student.firstName) : student.firstName == null &&
               lastName != null ? lastName.equals(student.lastName) : student.lastName == null &&
               studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (studentId ^ (studentId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + (grade != null ? grade : "");
    }
}
