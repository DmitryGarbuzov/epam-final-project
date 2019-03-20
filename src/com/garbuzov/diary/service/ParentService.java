package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.ParentDaoImpl;
import com.garbuzov.diary.dao.impl.UserDaoImpl;
import com.garbuzov.diary.entity.Parent;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.List;

public class ParentService {

    public boolean isParent(Long userId) throws ServiceException {
        try(ParentDaoImpl parentDao = new ParentDaoImpl()) {
            return parentDao.isPresent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(String email, String firstName, String lastName, String[] studentsId, int password) throws ServiceException {
        try (ParentDaoImpl parentDao = new ParentDaoImpl()) {
            Parent parent = new Parent(email, firstName, lastName);
            for (String id : studentsId) {
                parent.addStudent(new Student(Long.parseLong(id)));
            }
            parentDao.add(parent, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Parent> findAll() throws ServiceException {
        try (ParentDaoImpl parentDao = new ParentDaoImpl()) {
            return parentDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(String[] usersId) throws ServiceException {
        try (ParentDaoImpl parentDao = new ParentDaoImpl()) {
            for (String userId : usersId) {
                parentDao.delete(Long.parseLong(userId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean hasActiveStudent(long userId) throws ServiceException {
        try (ParentDaoImpl parentDao = new ParentDaoImpl()) {
            return parentDao.hasActiveStudent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Parent createParent(User user) throws ServiceException {
        try (ParentDaoImpl parentDao = new ParentDaoImpl()) {
            Parent parent = parentDao.create(user);
            parent.setEmail(user.getEmail());
            parent.setLastName(user.getLastName());
            parent.setFirstName(user.getFirstName());
            return parent;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
