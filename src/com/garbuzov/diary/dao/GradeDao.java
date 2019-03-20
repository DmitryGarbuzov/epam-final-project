package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.DaoException;

import java.util.List;

public interface GradeDao {

    void add(Grade grade) throws DaoException;
    void update(long gradeId, boolean isActive) throws DaoException;
    boolean isPresent(int number, String letter) throws DaoException;
    List<Grade> findAll(boolean isActive) throws DaoException;
    List<Grade> gradeForTeacher(long teacherId) throws DaoException;
}
