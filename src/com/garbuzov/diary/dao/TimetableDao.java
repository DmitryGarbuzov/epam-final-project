package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableDao implements AutoCloseable {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_INSERT_TIMETABLE = "INSERT INTO timetable (date, lesson_id) VALUES (?, ?)";
    private final static String SQL_FIND_LAST_TEN = "SELECT a.id, date FROM (SELECT t.id, date FROM timetable t INNER JOIN lesson l ON " +
                                                    "t.lesson_id=l.id WHERE grade_id=? AND subject_id=? ORDER BY id DESC LIMIT 10) a ORDER BY id ";

    public TimetableDao() {
        connection = connectionPool.getConnection();
    }

    public List<LessonDate> findLastTen(long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LAST_TEN)){
            statement.setLong(1, gradeId);
            statement.setLong(2, subjectId);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                long dateId = resultSet.getLong("id");
                Date date = resultSet.getDate("date");
                dateList.add(new LessonDate(dateId, date));
            }
            return dateList;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    public void add(long lessonId, Date date) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TIMETABLE)){
            statement.setDate(1, date);
            statement.setLong(2, lessonId);
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
