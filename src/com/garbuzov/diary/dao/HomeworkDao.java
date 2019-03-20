package com.garbuzov.diary.dao;

import com.garbuzov.diary.exception.DaoException;
import java.sql.Date;

public interface HomeworkDao {

    void add(long dateId, String homework) throws DaoException;
    void update(long dateId, String homework) throws DaoException;
    String find(long dateId) throws DaoException;
    String findForStudent(Date date, long subjectId, long gradeId) throws DaoException;
}
