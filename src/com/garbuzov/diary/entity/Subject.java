package com.garbuzov.diary.entity;

public class Subject extends Entity {
    private long subjectId;
    private String name;


    public Subject() {}

    public Subject(String name) {
        this.name = name;
    }

    public Subject(long subjectId) {
        this.subjectId = subjectId;
    }

    public Subject(long subjectId, String name) {
        this.subjectId = subjectId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return name;
    }
}
