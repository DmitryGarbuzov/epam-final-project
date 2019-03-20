package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.UserDaoImpl;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.Optional;

public class UserService {

    public Optional<User> findUser(String email, int pass) throws ServiceException{
        try(UserDaoImpl userDao = new UserDaoImpl()) {
            return userDao.find(email, pass);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public boolean isPresent(String email) throws ServiceException {
        try(UserDaoImpl userDao = new UserDaoImpl()) {
            return userDao.isPresent(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}


