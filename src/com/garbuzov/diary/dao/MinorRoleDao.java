package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.Entity;
import com.garbuzov.diary.exception.DaoException;
import java.util.List;

public interface MinorRoleDao<T extends Entity> extends BaseDao<T> {
    void update(long userId, boolean isActive) throws DaoException;
    List<T> findAll(boolean isActive) throws DaoException;
}
