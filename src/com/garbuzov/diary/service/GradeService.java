package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.GradeDao;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public class GradeService {

    public boolean isPresent(Integer number, String letter) throws ServiceException {
        try (GradeDao gradeDao = new GradeDao()){
            return gradeDao.isPresent(number, letter);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(Integer number, String letter) throws ServiceException {
        try (GradeDao gradeDao = new GradeDao()){
            Grade grade = new Grade(number, letter);
            gradeDao.add(grade);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Grade> findAll(boolean isActive) throws ServiceException {
        try (GradeDao gradeDao = new GradeDao()){
            return gradeDao.findAll(isActive);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void update(String[] gradesId, boolean isActive) throws ServiceException {
        try (GradeDao gradeDao = new GradeDao()){
            for (String gradeId : gradesId) {
                gradeDao.update(Long.parseLong(gradeId), isActive);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Grade> gradeForTeacher(long teacherId) throws ServiceException {
        try (GradeDao gradeDao = new GradeDao()) {
            return gradeDao.gradeForTeacher(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
