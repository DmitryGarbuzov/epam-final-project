package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.exception.DaoException;
import java.sql.Date;
import java.util.List;

public interface TimetableDao {

    void add(long lessonId, Date date) throws DaoException;
    void delete(Date date, long gradeId, long subjectId) throws DaoException;
    boolean isPresent(Date date, long gradeId, long subjectId) throws DaoException;
    boolean isValid(Date date, long gradeId, long subjectId) throws DaoException;
    List<LessonDate> findLastLessons(long gradeId) throws DaoException;
    List<LessonDate> findLastLessons(long gradeId, long subjectId) throws DaoException;
    List<LessonDate> findPreviousLessons(Date date, long gradeId) throws DaoException;
    List<LessonDate> findPreviousLessons(long dateId, long gradeId, long subjectId) throws DaoException;
    List<LessonDate> findNextLessons(Date date, long gradeId) throws DaoException;
    List<LessonDate> findNextLessons(long dateId, long gradeId, long subjectId) throws DaoException;
}
