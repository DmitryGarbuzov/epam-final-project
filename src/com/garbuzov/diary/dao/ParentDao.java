package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Parent;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import java.util.List;

public interface ParentDao {

    void add(Parent parent, int password) throws DaoException;
    void delete(long userId) throws DaoException;
    Parent create(User user) throws DaoException;
    boolean isPresent(long userId) throws DaoException;
    boolean hasActiveStudent(long userId) throws DaoException;
    List<Parent> findAll() throws DaoException;
}
