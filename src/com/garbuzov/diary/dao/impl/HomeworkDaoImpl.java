package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.HomeworkDao;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;

public class HomeworkDaoImpl implements AutoCloseable, HomeworkDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String HOMEWORK = "homework";
    private final static String MINUS = "-";
    private final static String UNDEFINED = "undefined";
    private final static String SQL_SELECT_HOMEWORK = "SELECT h.homework FROM homework h WHERE date_id=?";
    private final static String SQL_UPDATE_HOMEWORK = "UPDATE homework SET homework=? WHERE date_id=?";
    private final static String SQL_INSERT_HOMEWORK = "INSERT INTO homework (date_id, homework) VALUES (?, ?)";
    private final static String SQL_LESSON_EXIST = "SELECT * FROM timetable INNER JOIN lesson ON timetable.lesson_id=lesson.id " +
                                                   "WHERE timetable.date=? AND lesson.subject_id=?";
    private final static String SQL_HOMEWORK_FOR_STUDENT = "SELECT homework FROM homework h INNER JOIN timetable t ON h.date_id=t.id " +
                                                           "INNER JOIN lesson l on t.lesson_id=l.id WHERE t.date=? AND l.subject_id=? " +
                                                           "AND l.grade_id=?";

    public HomeworkDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
    public void add(long dateId, String homework) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_HOMEWORK)) {
            statement.setLong(1, dateId);
            statement.setString(2, homework);
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
    public void update(long dateId, String homework) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_HOMEWORK)) {
            statement.setString(1, homework);
            statement.setLong(2, dateId);
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
    public String find(long dateId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_HOMEWORK)) {
            statement.setLong(1, dateId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(HOMEWORK);
            }
            return MINUS;
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
    public String findForStudent(Date date, long subjectId, long gradeId) throws DaoException {
        try (PreparedStatement statement1 = connection.prepareStatement(SQL_LESSON_EXIST);
             PreparedStatement statement2 = connection.prepareStatement(SQL_HOMEWORK_FOR_STUDENT)) {
            statement1.setDate(1, date);
            statement1.setLong(2, subjectId);
            ResultSet resultSet = statement1.executeQuery();
            if (!resultSet.next()) {
                return UNDEFINED;
            }
            statement2.setDate(1, date);
            statement2.setLong(2, subjectId);
            statement2.setLong(3, gradeId);
            resultSet = statement2.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(HOMEWORK);
            }
            return MINUS;
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
