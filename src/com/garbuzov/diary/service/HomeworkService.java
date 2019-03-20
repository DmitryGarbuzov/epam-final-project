package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.HomeworkDaoImpl;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;

public class HomeworkService {

    private final static String REGEX = "'|\"";
    private final static String REPLACEMENT = "\\\\'";
    private final static String MINUS = "-";

    public List<String> findAll(List<LessonDate> dateList) throws ServiceException {
        try (HomeworkDaoImpl homeworkDao = new HomeworkDaoImpl()) {
            List<String> homeworkList = new ArrayList<>();
            for (LessonDate date : dateList) {
                homeworkList.add(homeworkDao.find(date.getDateId()));
            }
            return homeworkList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(long dateId, String homework) throws ServiceException {
        try (HomeworkDaoImpl homeworkDao = new HomeworkDaoImpl()){
            homework = homework.replaceAll(REGEX, REPLACEMENT);
            if (!homeworkDao.find(dateId).equals(MINUS)) {
                homeworkDao.update(dateId, homework);
            } else {
                homeworkDao.add(dateId, homework);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<ArrayList<String>> findForStudent(List<LessonDate> dateList, List<Subject> subjectList, long gradeId) throws ServiceException {
        try (HomeworkDaoImpl homeworkDao = new HomeworkDaoImpl()){
            List<ArrayList<String>> studentHomework = new ArrayList<>();
            for (int i = 0; i < subjectList.size(); i += 1) {
                studentHomework.add(new ArrayList<>());
                for (int j = 0; j < dateList.size(); j += 1) {
                    studentHomework.get(i).add(homeworkDao.findForStudent(dateList.get(j).getDate(),
                                                                          subjectList.get(i).getSubjectId(), gradeId));
                }
            }
            return studentHomework;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
