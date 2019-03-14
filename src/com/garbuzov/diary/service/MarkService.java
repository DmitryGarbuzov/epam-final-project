package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.MarkDao;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class MarkService  {

    public List<ArrayList<Integer>> find(List<LessonDate> dateList, List<Student> studentList) throws ServiceException {
        try (MarkDao markDao = new MarkDao()){
            List<ArrayList<Integer>> studentsMarks = new ArrayList<>();
            for (int i = 0; i < studentList.size(); i += 1) {
                studentsMarks.add(new ArrayList<>());
                for (int j = 0; j < dateList.size(); j += 1) {
                    studentsMarks.get(i).add(markDao.find(dateList.get(j).getDateId(), studentList.get(i).getStudentId()));
                }
            }
            return studentsMarks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void add(long dateId, long studentId, int mark) throws ServiceException {
        try (MarkDao markDao = new MarkDao()){
            if (markDao.find(dateId, studentId) != 0) {
                markDao.update(dateId, studentId, mark);
            } else {
                markDao.add(dateId, studentId, mark);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
