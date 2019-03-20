package com.garbuzov.diary.service;

import com.garbuzov.diary.dao.impl.StudentDaoImpl;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.DaoException;
import com.garbuzov.diary.exception.ServiceException;
import java.util.Comparator;
import java.util.List;

public class StudentService {

    public void add(String firstName, String lastName, Long gradeId) throws ServiceException {
        try (StudentDaoImpl studentDao = new StudentDaoImpl()) {
            Grade grade = new Grade(gradeId);
            Student student = new Student(firstName, lastName, grade);
            studentDao.add(student);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Student> findAll(boolean isActive) throws ServiceException {
        try (StudentDaoImpl studentDao = new StudentDaoImpl()) {
            return studentDao.findAll(isActive);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void update(String[] studentsId, boolean isActive) throws ServiceException {
        try (StudentDaoImpl studentDao = new StudentDaoImpl()) {
            for (String studentId : studentsId) {
                studentDao.update(Long.parseLong(studentId), isActive);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Student> findByGradeId(long gradeId) throws ServiceException {
        try (StudentDaoImpl studentDao = new StudentDaoImpl()) {
            List<Student> studentList = studentDao.findByGradeId(gradeId);
            studentList.sort(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName));
            return studentList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
