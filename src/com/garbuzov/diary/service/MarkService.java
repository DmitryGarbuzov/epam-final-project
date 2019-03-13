package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.MarkDao;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class MarkService  {

    public ArrayList<ArrayList<Integer>> find(List<LessonDate> dateList, List<Student> studentList, long subjectId) throws ServiceException {
        try (MarkDao markDao = new MarkDao()){
            ArrayList<ArrayList<Integer>> studentsMarks = new ArrayList<>();
            for (int i = 0; i < studentList.size(); i += 1) {
                studentsMarks.add(new ArrayList<>());
                for (int j = 0; j < dateList.size(); j += 1) {
                    studentsMarks.get(i).add(markDao.find(dateList.get(j).getDateId(), studentList.get(i).getStudentId(), subjectId));
                }
            }
            return studentsMarks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
