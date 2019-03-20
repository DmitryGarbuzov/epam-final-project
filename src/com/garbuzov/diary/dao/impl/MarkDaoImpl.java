package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.MarkDao;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;

public class MarkDaoImpl implements AutoCloseable, MarkDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String MARK = "mark";
    private final static String SQL_LESSON_EXIST = "SELECT * FROM timetable INNER JOIN lesson ON timetable.lesson_id=lesson.id " +
                                                   "WHERE timetable.date=? AND lesson.subject_id=?";
    private final static String SQL_FIND_MARK = "SELECT mark FROM mark INNER JOIN timetable ON mark.date_id=timetable.id " +
                                                "WHERE mark.student_id=? AND timetable.id=?";
    private final static String SQL_MARK_FOR_STUDENT = "SELECT mark FROM mark INNER JOIN timetable ON mark.date_id=timetable.id " +
                                                       "INNER JOIN lesson l on timetable.lesson_id=l.id WHERE mark.student_id=? " +
                                                       "AND timetable.date=? AND l.subject_id=?";
    private final static String SQL_INSERT_MARK = "INSERT INTO mark (mark, date_id, student_id) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE_MARK = "UPDATE mark SET mark=? WHERE date_id=? AND student_id=?";

    public MarkDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
    public void add(long dateId, long studentId, int mark) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MARK)) {
            statement.setInt(1, mark);
            statement.setLong(2, dateId);
            statement.setLong(3, studentId);
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
    public void update(long dateId, long studentId, int mark) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MARK)) {
            statement.setInt(1, mark);
            statement.setLong(2, dateId);
            statement.setLong(3, studentId);
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
    public int find(long dateId, long studentId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARK)){
            statement.setLong(1, studentId);
            statement.setLong(2, dateId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(MARK);
            }
            return 0;
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
    public int findForStudent(Date date, long subjectId, long studentId) throws DaoException {
        try (PreparedStatement statement1 = connection.prepareStatement(SQL_LESSON_EXIST);
             PreparedStatement statement2 = connection.prepareStatement(SQL_MARK_FOR_STUDENT)) {
            statement1.setDate(1, date);
            statement1.setLong(2, subjectId);
            ResultSet resultSet = statement1.executeQuery();
            if (!resultSet.next()) {
                return -2;
            }
            statement2.setLong(1, studentId);
            statement2.setDate(2, date);
            statement2.setLong(3, subjectId);
            resultSet = statement2.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(MARK);
            }
            return 0;
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
