package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.GradeDaoImpl;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.List;

public class GradeService {

    public boolean isPresent(Integer number, String letter) throws ServiceException {
        try (GradeDaoImpl gradeDao = new GradeDaoImpl()){
            return gradeDao.isPresent(number, letter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(Integer number, String letter) throws ServiceException {
        try (GradeDaoImpl gradeDao = new GradeDaoImpl()){
            Grade grade = new Grade(number, letter);
            gradeDao.add(grade);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Grade> findAll(boolean isActive) throws ServiceException {
        try (GradeDaoImpl gradeDao = new GradeDaoImpl()){
            return gradeDao.findAll(isActive);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void update(String[] gradesId, boolean isActive) throws ServiceException {
        try (GradeDaoImpl gradeDao = new GradeDaoImpl()){
            for (String gradeId : gradesId) {
                gradeDao.update(Long.parseLong(gradeId), isActive);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Grade> gradeForTeacher(long teacherId) throws ServiceException {
        try (GradeDaoImpl gradeDao = new GradeDaoImpl()) {
            return gradeDao.gradeForTeacher(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
