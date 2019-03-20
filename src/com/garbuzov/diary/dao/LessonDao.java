package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import java.util.ArrayList;
import java.util.Map;

public interface LessonDao {

    void add(long teacherId, long gradeId, long subjectId) throws DaoException;
    void update(long teacherId, long gradeId) throws DaoException;
    void update(long teacherId, long gradeId, long subjectId) throws DaoException;
    long findLessonId(long gradeId, long teacherId, long subjectId) throws DaoException;
    Map<Grade, ArrayList<Subject>> findAllGrades(long teacherId) throws DaoException;
}
