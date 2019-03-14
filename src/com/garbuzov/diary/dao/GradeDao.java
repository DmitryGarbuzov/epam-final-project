package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeDao implements MinorRoleDao<Grade> {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_INSERT_GRADE = "INSERT INTO grade (number, letter) VALUES (?, ?)";
    private final static String SQL_SELECT_GRADE = "SELECT id FROM grade WHERE number=? AND letter=?";
    private final static String SQL_FIND_ALL_GRADE = "SELECT id, number, letter FROM grade WHERE isActive=?";
    private final static String SQL_UPDATE_GRADE = "UPDATE grade SET isActive=? WHERE id=?";
    private final static String SQL_SELECT_GRADE_FOR_TEACHER = "SELECT DISTINCT grade.id, number, letter FROM teacher INNER JOIN " +
                                                               "lesson ON teacher.id=lesson.teacher_id AND teacher.id=? " +
                                                               "RIGHT JOIN grade ON grade.id=lesson.grade_id WHERE grade.isActive=TRUE " +
                                                               "AND (lesson.grade_id IS NULL OR lesson.isActive=FALSE)";
    private final static String ID = "id";
    private final static String LETTER = "letter";
    private final static String NUMBER = "number";

    public GradeDao() {
        connection = connectionPool.getConnection();
    }

    public boolean isPresent(int number, String letter) throws DaoException{
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GRADE)) {
            boolean flag = false;
            statement.setInt(1, number);
            statement.setString(2, letter);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
            return flag;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    public List<Grade> gradeForTeacher(long teacherId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GRADE_FOR_TEACHER)) {
            List<Grade> gradeList = new ArrayList<>();
            statement.setLong(1, teacherId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long gradeId = resultSet.getLong(ID);
                int number = resultSet.getInt(NUMBER);
                String letter = resultSet.getString(LETTER);
                gradeList.add(new Grade(number, letter, gradeId));
            }
            return gradeList;
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
    public void add(Grade grade) throws DaoException{
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GRADE)){
            statement.setInt(1, grade.getNumber());
            statement.setString(2, grade.getLetter());
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
    public void update(long gradeId, boolean isActive) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_GRADE)){
            statement.setBoolean(1, isActive);
            statement.setLong(2, gradeId);
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
    public List<Grade> findAll(boolean isActive) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_GRADE)){
            statement.setBoolean(1, isActive);
            List<Grade> gradeList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String letter = resultSet.getString(LETTER);
                int number = resultSet.getInt(NUMBER);
                long gradeId = resultSet.getLong(ID);
                gradeList.add(new Grade(number, letter, gradeId));
            }
            return gradeList;
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
