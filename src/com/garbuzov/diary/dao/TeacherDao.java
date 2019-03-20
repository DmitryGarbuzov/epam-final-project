package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;

import java.util.List;

public interface TeacherDao {

    void add(Teacher teacher, int password) throws DaoException;
    void delete(long userId) throws DaoException;
    Teacher create(User user) throws DaoException;
    boolean isPresent(long userId) throws DaoException;
    boolean hasActiveStudent(long userId) throws DaoException;
    List<Teacher> findAll() throws DaoException;
}
