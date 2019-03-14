package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.ParentDao;
import com.garbuzov.diary.dao.UserDao;
import com.garbuzov.diary.entity.Parent;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public class ParentService {

    public boolean isParent(Long userId) throws ServiceException {
        try(ParentDao parentDao = new ParentDao()) {
            return parentDao.isPresent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(String email, String firstName, String lastName, String[] studentsId, int password) throws ServiceException {
        try (ParentDao parentDao = new ParentDao();
             UserDao userDao = new UserDao()) {
            Parent parent = new Parent(email, firstName, lastName);
            userDao.add(parent, password);
            for (String id : studentsId) {
                parent.addStudent(new Student(Long.parseLong(id)));
            }
            parentDao.add(parent);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Parent> findAll() throws ServiceException {
        try (ParentDao parentDao = new ParentDao()) {
            return parentDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(String[] usersId) throws ServiceException {
        try (UserDao userDao = new UserDao();
             ParentDao parentDao = new ParentDao()) {
            for (String userId : usersId) {
                parentDao.delete(Long.parseLong(userId));
                userDao.delete(Long.parseLong(userId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean hasActiveStudent(long userId) throws ServiceException {
        try (ParentDao parentDao = new ParentDao()) {
            return parentDao.hasActiveStudent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
