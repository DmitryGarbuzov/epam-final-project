package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.LessonDaoImpl;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.ArrayList;
import java.util.Map;

public class LessonService {

    public void add(long teacherId, long gradeId, String[] subjectsId) throws ServiceException {
        try (LessonDaoImpl lessonDao = new LessonDaoImpl()){
            for (String subjectId : subjectsId) {
                long subjId = Long.parseLong(subjectId);
                if (lessonDao.findLessonId(gradeId, teacherId, subjId) != 0) {
                    lessonDao.update(teacherId, gradeId, subjId);
                } else {
                    lessonDao.add(teacherId, gradeId, subjId);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Map<Grade, ArrayList<Subject>> findAllGrades(long teacherId) throws ServiceException {
        try (LessonDaoImpl lessonDao = new LessonDaoImpl()){
            return lessonDao.findAllGrades(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(long teacherId, String[] gradesId) throws ServiceException {
        try (LessonDaoImpl lessonDao = new LessonDaoImpl()){
            for (String gradeId : gradesId) {
                lessonDao.update(   teacherId, Long.parseLong(gradeId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long findLessonId(long gradeId, long teacherId, long subjectId) throws ServiceException {
        try (LessonDaoImpl lessonDao = new LessonDaoImpl()){
            return lessonDao.findLessonId(gradeId, teacherId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
