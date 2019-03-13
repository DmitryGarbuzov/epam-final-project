package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Entity;
import com.garbuzov.diary.exception.DaoException;

public interface BaseDao<T extends Entity> extends AutoCloseable {//todo
    void add(T user) throws DaoException;
}
