package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import java.util.List;

public interface SubjectDao {

    void add(Subject subject) throws DaoException;
    void update(long subjectId, boolean isActive) throws DaoException;
    boolean isPresent(String subjectName) throws DaoException;
    List<Subject> findAll(boolean isActive) throws DaoException;
    List<Subject> findForStudent(long studentId) throws DaoException;
}
