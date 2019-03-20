package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.TimetableDaoImpl;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.sql.Date;
import java.util.List;

public class TimetableService {

    public List<LessonDate> findPreviousLessons(long dateId, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.findPreviousLessons(dateId, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<LessonDate> findPreviousLessons(Date date, long gradeId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.findPreviousLessons(date, gradeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<LessonDate> findNextLessons(long dateId, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.findNextLessons(dateId, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<LessonDate> findNextLessons(Date date, long gradeId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.findNextLessons(date, gradeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<LessonDate> findLastLessons(long gradeId, long subjectId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.findLastLessons(gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<LessonDate> findLastLessons(long gradeId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.findLastLessons(gradeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(long lessonId, Date date) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            timetableDao.add(lessonId, date);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isValid(Date date, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()){
            return timetableDao.isValid(date, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isPresent(Date date, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            return timetableDao.isPresent(date, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(Date date, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDaoImpl timetableDao = new TimetableDaoImpl()) {
            timetableDao.delete(date, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
