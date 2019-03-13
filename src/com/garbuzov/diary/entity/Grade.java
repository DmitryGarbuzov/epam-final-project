package com.garbuzov.diary.entity;

public class Grade extends Entity {
    private long gradeId;
    private int number;
    private String letter;

    public Grade() {}

    public Grade(long gradeId) {
        this.gradeId = gradeId;
    }

    public Grade(int number, String letter) {
        this.number = number;
        this.letter = letter;
    }

    public Grade(int number, String letter, long gradeId) {
        this.number = number;
        this.letter = letter;
        this.gradeId = gradeId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade = (Grade) o;
        return letter != null ? letter.equals(grade.letter) : grade.letter == null &&
               number == grade.number && gradeId == grade.gradeId ;
    }

    @Override
    public int hashCode() {
        int result = (int) (gradeId ^ (gradeId >>> 32));
        result = 31 * result + number;
        result = 31 * result + (letter != null ? letter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return number + letter;
    }
}
