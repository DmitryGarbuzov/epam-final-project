package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.TeacherDaoImpl;
import com.garbuzov.diary.dao.impl.UserDaoImpl;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.List;

public class TeacherService {

    public boolean isTeacher(Long userId) throws ServiceException {
        try(TeacherDaoImpl teacherDao = new TeacherDaoImpl()) {
            return teacherDao.isPresent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(String email, String firstName, String lastName, String[] subjectsId, int password) throws ServiceException {
        try (TeacherDaoImpl teacherDao = new TeacherDaoImpl()) {
            Teacher teacher = new Teacher(email, firstName, lastName);
            for (String id : subjectsId) {
                teacher.addSubject(new Subject(Long.parseLong(id)));
            }
            teacherDao.add(teacher, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Teacher> findAll() throws ServiceException {
        try (TeacherDaoImpl teacherDao = new TeacherDaoImpl()) {
            return teacherDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(String[] usersId) throws ServiceException {
        try (TeacherDaoImpl teacherDao = new TeacherDaoImpl()) {
            for (String userId : usersId) {
                teacherDao.delete(Long.parseLong(userId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean hasActiveStudent(long userId) throws ServiceException {
        try (TeacherDaoImpl teacherDao = new TeacherDaoImpl()) {
            return teacherDao.hasActiveStudent(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Teacher createTeacher(User user) throws ServiceException {
        try (TeacherDaoImpl teacherDao = new TeacherDaoImpl()) {
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
