package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.HomeworkDao;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class HomeworkService {

    public List<String> findAll(List<LessonDate> dateList) throws ServiceException {
        try (HomeworkDao homeworkDao = new HomeworkDao()) {
            List<String> homeworkList = new ArrayList<>();
            for (LessonDate date : dateList) {
                homeworkList.add(homeworkDao.find(date.getDateId()));
            }
            return homeworkList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
