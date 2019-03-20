package com.garbuzov.diary.dao;

import com.garbuzov.diary.exception.DaoException;

import java.util.Optional;

public interface AdminDao {

    Optional<Long> find(long userId) throws DaoException;
}
