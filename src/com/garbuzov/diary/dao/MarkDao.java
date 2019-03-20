package com.garbuzov.diary.dao;

import com.garbuzov.diary.exception.DaoException;

import java.sql.Date;

public interface MarkDao {

    void add(long dateId, long studentId, int mark) throws DaoException;
    void update(long dateId, long studentId, int mark) throws DaoException;
    int find(long dateId, long studentId) throws DaoException;
    int findForStudent(Date date, long subjectId, long studentId) throws DaoException;
}
