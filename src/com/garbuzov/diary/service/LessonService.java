package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.LessonDao;
import com.garbuzov.diary.dao.TeacherDao;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;

import java.util.ArrayList;
import java.util.Map;

public class LessonService {

    public void add(long teacherId, long gradeId, String[] subjectsId) throws ServiceException {
        try (LessonDao lessonDao = new LessonDao()){
            for (String subjectId : subjectsId) {
                lessonDao.add(teacherId, gradeId, Long.parseLong(subjectId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Map<Grade, ArrayList<Subject>> findAllGrades(long teacherId) throws ServiceException {
        try (LessonDao lessonDao = new LessonDao()){
            return lessonDao.findAllGrades(teacherId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(long teacherId, String[] gradesId) throws ServiceException {
        try (LessonDao lessonDao = new LessonDao()){
            for (String gradeId : gradesId) {
                lessonDao.delete(teacherId, Long.parseLong(gradeId));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long findLessonId(long gradeId, long teacherId, long subjectId) throws ServiceException {
        try (LessonDao lessonDao = new LessonDao()){
            return lessonDao.findLessonId(gradeId, teacherId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
