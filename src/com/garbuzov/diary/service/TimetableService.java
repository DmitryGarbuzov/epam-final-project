package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.TimetableDao;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.sql.Date;
import java.util.List;

public class TimetableService {

    public List<LessonDate> findLastTen(long gradeId, long subjectId) throws ServiceException {
        try (TimetableDao timetableDao = new TimetableDao()) {
            return timetableDao.findLastTen(gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(long lessonId, Date date) throws ServiceException {
        try (TimetableDao timetableDao = new TimetableDao()) {
            timetableDao.add(lessonId, date);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isValid(Date date, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDao timetableDao = new TimetableDao()){
            return timetableDao.isValid(date, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isPresent(Date date, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDao timetableDao = new TimetableDao()) {
            return timetableDao.isPresent(date, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(Date date, long gradeId, long subjectId) throws ServiceException {
        try (TimetableDao timetableDao = new TimetableDao()) {
            timetableDao.delete(date, gradeId, subjectId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
