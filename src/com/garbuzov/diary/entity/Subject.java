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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Subject subject = (Subject) o;

        return name != null ? name.equals(subject.name) : subject.name == null &&
               subjectId != subject.subjectId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (subjectId ^ (subjectId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
