package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.UserDao;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.Optional;

public class UserService {

    public Optional<User> findUser(String email, int pass) throws ServiceException{
        try(UserDao userDao = new UserDao()) {
            return userDao.find(email, pass);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public boolean isPresent(String email) throws ServiceException {
        try(UserDao userDao = new UserDao()) {
            return userDao.isPresent(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}


