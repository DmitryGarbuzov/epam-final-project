package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.TeacherDao;
import com.garbuzov.diary.dao.UserDao;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TeacherService {

    public boolean isTeacher(Long userId) throws ServiceException {
        try(TeacherDao teacherDao = new TeacherDao()) {
            return teacherDao.isPresent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(String email, String firstName, String lastName, String[] subjectsId, int password) throws ServiceException {
        try (TeacherDao teacherDao = new TeacherDao();
             UserDao userDao = new UserDao()) {
            Teacher teacher = new Teacher(email, firstName, lastName);
            userDao.add(teacher, password);
            for (String id : subjectsId) {
                teacher.addSubject(new Subject(Long.parseLong(id)));
            }
            teacherDao.add(teacher);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Teacher> findAll() throws ServiceException {
        try (TeacherDao teacherDao = new TeacherDao()) {
            return teacherDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(String[] usersId) throws ServiceException {
        try (UserDao userDao = new UserDao();
             TeacherDao teacherDao = new TeacherDao()) {
            for (String userId : usersId) {
                teacherDao.delete(Long.parseLong(userId));
                userDao.delete(Long.parseLong(userId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean hasActiveStudent(long userId) throws ServiceException {
        try (TeacherDao teacherDao = new TeacherDao()) {
            return teacherDao.hasActiveStudent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Teacher createTeacher(User user) throws ServiceException {
        try (TeacherDao teacherDao = new TeacherDao()) {
            Teacher teacher = teacherDao.create(user);
            teacher.setEmail(user.getEmail());
            teacher.setLastName(user.getLastName());
            teacher.setFirstName(user.getFirstName());
            return teacher;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
