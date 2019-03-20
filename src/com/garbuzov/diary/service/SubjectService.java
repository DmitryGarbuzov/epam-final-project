package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.SubjectDaoImpl;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.List;

public class SubjectService {

    public boolean isPresent(String subjectName) throws ServiceException {
        try (SubjectDaoImpl subjectDao = new SubjectDaoImpl()) {
            return subjectDao.isPresent(subjectName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(String subjectName)throws ServiceException {
        try (SubjectDaoImpl subjectDao = new SubjectDaoImpl()) {
            Subject subject = new Subject(subjectName);
            subjectDao.add(subject);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Subject> findAll(boolean isActive) throws ServiceException {
        try (SubjectDaoImpl subjectDao = new SubjectDaoImpl()) {
            return subjectDao.findAll(isActive);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void update(String[] subjectsId, boolean isActive) throws ServiceException {
        try (SubjectDaoImpl subjectDao = new SubjectDaoImpl()) {
            for (String subjectId : subjectsId) {
                subjectDao.update(Long.parseLong(subjectId), isActive);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Subject> findForStudent(long studentId) throws ServiceException {
        try (SubjectDaoImpl subjectDao = new SubjectDaoImpl()) {
            return subjectDao.findForStudent(studentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
