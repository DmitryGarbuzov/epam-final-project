package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.AdminDaoImpl;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.Optional;

public class AdminService {

    public boolean isAdmin(Long userId) throws ServiceException {
        try(AdminDaoImpl adminDao = new AdminDaoImpl()) {
            boolean flag = false;
            Optional<Long> adminOptional = adminDao.find(userId);
            if (adminOptional.isPresent()) {
                flag = true;
            }
            return flag;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
