package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import java.util.List;
import java.util.Optional;

public interface MainRoleDao<T extends User> extends BaseDao<T> {
    void delete(long userId) throws DaoException;
    List<T> findAll() throws DaoException;
    boolean isPresent(long userID) throws DaoException;
    T create(User user) throws DaoException;
}
