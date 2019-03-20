package com.garbuzov.diary.dao;

import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import java.util.Optional;

public interface UserDao {

    boolean isPresent(String email) throws DaoException;
    Optional<User> find(String email, int pass) throws DaoException;
}
