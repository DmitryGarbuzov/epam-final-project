package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Student;
import com.garbuzov.diary.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentDao implements MinorRoleDao<Student> {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_INSERT_STUDENT = "INSERT INTO student (first_name, last_name, grade_id) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE_STUDENT = "UPDATE student SET isActive=? WHERE id=?";
    private final static String SQL_FIND_BY_GRADE_ID = "SELECT id, first_name, last_name FROM student WHERE grade_id=? AND isActive=TRUE";
    private final static String SQL_FIND_ALL_STUDENT = "SELECT student.id, first_name, last_name, grade_id, number, letter FROM " +
                                                       "student INNER JOIN grade ON student.grade_id=grade.id WHERE student.isActive=?";
    private final static String ID = "id";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String LETTER = "letter";
    private final static String NUMBER = "number";

    public StudentDao() {
        connection = connectionPool.getConnection();
    }

    public List<Student> findByGradeId(long gradeId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_GRADE_ID)){
            statement.setLong(1, gradeId);
            List<Student> studentList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getLong(ID));
                student.setFirstName(resultSet.getString(FIRST_NAME));
                student.setLastName(resultSet.getString(LAST_NAME));
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void add(Student student) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_STUDENT)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setLong(3, student.getGrade().getGradeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void update(long studentId, boolean isActive) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STUDENT)) {
            statement.setBoolean(1, isActive);
            statement.setLong(2, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public List<Student> findAll(boolean isActive) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_STUDENT)){
            statement.setBoolean(1, isActive);
            List<Student> studentList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                Grade grade = new Grade();
                student.setStudentId(resultSet.getLong(ID));
                student.setFirstName(resultSet.getString(FIRST_NAME));
                student.setLastName(resultSet.getString(LAST_NAME));
                grade.setNumber(resultSet.getInt(NUMBER));
                grade.setLetter(resultSet.getString(LETTER));
                student.setGrade(grade);
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }
}
