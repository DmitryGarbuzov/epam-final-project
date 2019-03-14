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
    private final static String SQL_IS_PRESENT = "SELECT t.id FROM timetable t INNER JOIN lesson l on t.lesson_id = l.id WHERE " +
                                                 "date=? AND grade_id=? AND subject_id=?";
    private final static String SQL_IS_VALID_DATE = "SELECT timetable.id FROM timetable INNER JOIN lesson ON timetable.lesson_id=lesson.id " +
                                                    "WHERE date > ? AND subject_id=? AND grade_id=? LIMIT 1";
    private final static String SQL_FIND_LAST_TEN = "SELECT a.id, date FROM (SELECT t.id, date FROM timetable t INNER JOIN lesson l ON " +
                                                    "t.lesson_id=l.id WHERE grade_id=? AND subject_id=? ORDER BY id DESC LIMIT 13) a ORDER BY id ";
    private final static String SQL_DELETE_TIMETABLE = "DELETE t FROM timetable t INNER JOIN lesson l ON t.lesson_id=l.id WHERE " +
                                                       "date=? AND grade_id=? AND subject_id=?";
    private final static String SQL_DELETE_MARK = "DELETE m FROM timetable t INNER JOIN lesson l ON t.lesson_id=l.id INNER JOIN " +
                                                  "mark m ON m.date_id=t.id WHERE date=? AND grade_id=? AND subject_id=?";
    private final static String SQL_DELETE_HOMEWORK = "DELETE h FROM timetable t INNER JOIN lesson l ON t.lesson_id=l.id INNER JOIN " +
                                                      "homework h ON h.date_id=t.id WHERE date=? AND grade_id=? AND subject_id=?";

    public TimetableDao() {
        connection = connectionPool.getConnection();
    }

    public void delete(Date date, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TIMETABLE);
             PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_MARK);
             PreparedStatement statement2 = connection.prepareStatement(SQL_DELETE_HOMEWORK)) {
            statement.setDate(1, date);
            statement.setLong(2, gradeId);
            statement.setLong(3, subjectId);
            statement1.setDate(1, date);
            statement1.setLong(2, gradeId);
            statement1.setLong(3, subjectId);
            statement2.setDate(1, date);
            statement2.setLong(2, gradeId);
            statement2.setLong(3, subjectId);
            statement1.executeUpdate();
            statement2.executeUpdate();
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

    public boolean isPresent(Date date, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_IS_PRESENT)) {
            statement.setDate(1, date);
            statement.setLong(2, gradeId);
            statement.setLong(3, subjectId);
            boolean flag = false;
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

    public boolean isValid(Date date, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_IS_VALID_DATE)){
            statement.setDate(1, date);
            statement.setLong(2, subjectId);
            statement.setLong(3, gradeId);
            ResultSet resultSet = statement.executeQuery();
            boolean flag = true;
            if (resultSet.next()) {
                flag = false;
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
