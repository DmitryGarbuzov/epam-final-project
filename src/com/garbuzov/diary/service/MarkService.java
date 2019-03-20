package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.MarkDaoImpl;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;

public class MarkService  {

    public List<ArrayList<Integer>> find(List<LessonDate> dateList, List<Student> studentList) throws ServiceException {
        try (MarkDaoImpl markDao = new MarkDaoImpl()){
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
        try (MarkDaoImpl markDao = new MarkDaoImpl()){
            if (markDao.find(dateId, studentId) != 0) {
                markDao.update(dateId, studentId, mark);
            } else {
                markDao.add(dateId, studentId, mark);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<ArrayList<Integer>> findForStudent(List<LessonDate> dateList, List<Subject> subjectList, long studentId) throws ServiceException {
        try (MarkDaoImpl markDao = new MarkDaoImpl()){
            List<ArrayList<Integer>> studentMarks = new ArrayList<>();
            for (int i = 0; i < subjectList.size(); i += 1) {
                studentMarks.add(new ArrayList<>());
                for (int j = 0; j < dateList.size(); j += 1) {
                    studentMarks.get(i).add(markDao.findForStudent(dateList.get(j).getDate(),
                                                                   subjectList.get(i).getSubjectId(), studentId));
                }
            }
            return studentMarks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
