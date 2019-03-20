package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.DaoException;
import java.util.List;

public interface StudentDao {

    void add(Student student) throws DaoException;
    void update(long studentId, boolean isActive) throws DaoException;
    List<Student> findAll(boolean isActive) throws DaoException;
    List<Student> findByGradeId(long gradeId) throws DaoException;
}
