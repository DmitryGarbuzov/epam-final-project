package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.TimetableDao;
import com.garbuzov.diary.entity.LessonDate;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableDaoImpl implements AutoCloseable, TimetableDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String ID = "id";
    private final static String DATE = "date";
    private final static String SQL_INSERT_TIMETABLE = "INSERT INTO timetable (date, lesson_id) VALUES (?, ?)";
    private final static String SQL_IS_PRESENT = "SELECT t.id FROM timetable t INNER JOIN lesson l on t.lesson_id = l.id WHERE " +
                                                 "date=? AND grade_id=? AND subject_id=?";
    private final static String SQL_IS_VALID_DATE = "SELECT timetable.id FROM timetable INNER JOIN lesson ON timetable.lesson_id=lesson.id " +
                                                    "WHERE date >= ? AND subject_id=? AND grade_id=? LIMIT 1";
    private final static String SQL_FIND_LAST_LESSONS = "SELECT a.id, date FROM (SELECT t.id, date FROM timetable t INNER JOIN lesson l ON " +
                                                        "t.lesson_id=l.id WHERE grade_id=? AND subject_id=? ORDER BY id DESC LIMIT 13) a ORDER BY id";
    private final static String SQL_LAST_LESSONS_FOR_GRADE = "SELECT date FROM (SELECT DISTINCT date FROM timetable t INNER JOIN lesson l ON " +
                                                             "t.lesson_id=l.id WHERE grade_id=? ORDER BY date DESC LIMIT 13) a ORDER BY date";
    private final static String SQL_FIND_PREVIOUS_LESSONS = "SELECT a.id, date FROM (SELECT t.id, date FROM timetable t INNER JOIN lesson l ON " +
                                                            "t.lesson_id=l.id WHERE grade_id=? AND subject_id=? AND t.id < ? ORDER BY id DESC LIMIT 13) a " +
                                                            "ORDER BY id";
    private final static String SQL_PREVIOUS_LESSONS_FOR_STUDENT = "SELECT date FROM (SELECT DISTINCT date FROM timetable t INNER JOIN lesson l ON " +
                                                                   "t.lesson_id=l.id WHERE grade_id=? AND date < ? ORDER BY date DESC LIMIT 13) a " +
                                                                   "ORDER BY date";
    private final static String SQL_FIND_NEXT_LESSONS = "SELECT a.id, date FROM (SELECT t.id, date FROM timetable t INNER JOIN lesson l ON " +
                                                        "t.lesson_id=l.id WHERE grade_id=? AND subject_id=? AND t.id > ? ORDER BY id DESC LIMIT 13) a " +
                                                        "ORDER BY id";
    private final static String SQL_NEXT_LESSONS_FOR_STUDENT = "SELECT date FROM (SELECT DISTINCT date FROM timetable t INNER JOIN lesson l ON " +
                                                               "t.lesson_id=l.id WHERE grade_id=? AND date > ? ORDER BY date DESC LIMIT 13) a " +
                                                               "ORDER BY date";
    private final static String SQL_DELETE_TIMETABLE = "DELETE t FROM timetable t INNER JOIN lesson l ON t.lesson_id=l.id WHERE " +
                                                       "date=? AND grade_id=? AND subject_id=?";
    private final static String SQL_DELETE_MARK = "DELETE m FROM timetable t INNER JOIN lesson l ON t.lesson_id=l.id INNER JOIN " +
                                                  "mark m ON m.date_id=t.id WHERE date=? AND grade_id=? AND subject_id=?";
    private final static String SQL_DELETE_HOMEWORK = "DELETE h FROM timetable t INNER JOIN lesson l ON t.lesson_id=l.id INNER JOIN " +
                                                      "homework h ON h.date_id=t.id WHERE date=? AND grade_id=? AND subject_id=?";


    public TimetableDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<LessonDate> findLastLessons(long gradeId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_LAST_LESSONS_FOR_GRADE)){
            statement.setLong(1, gradeId);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                Date date = resultSet.getDate(DATE);
                dateList.add(new LessonDate(date));
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

    @Override
    public List<LessonDate> findLastLessons(long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LAST_LESSONS)){
            statement.setLong(1, gradeId);
            statement.setLong(2, subjectId);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                long dateId = resultSet.getLong(ID);
                Date date = resultSet.getDate(DATE);
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

    @Override
    public List<LessonDate> findPreviousLessons(Date date, long gradeId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_PREVIOUS_LESSONS_FOR_STUDENT)) {
            statement.setLong(1, gradeId);
            statement.setDate(2, date);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                Date lessonDate = resultSet.getDate(DATE);
                dateList.add(new LessonDate(lessonDate));
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

    @Override
    public List<LessonDate> findPreviousLessons(long dateId, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PREVIOUS_LESSONS)) {
            statement.setLong(1, gradeId);
            statement.setLong(2, subjectId);
            statement.setLong(3, dateId);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID);
                Date date = resultSet.getDate(DATE);
                dateList.add(new LessonDate(id, date));
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

    @Override
    public List<LessonDate> findNextLessons(Date date, long gradeId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_NEXT_LESSONS_FOR_STUDENT)) {
            statement.setLong(1, gradeId);
            statement.setDate(2, date);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                Date lessonDate = resultSet.getDate(DATE);
                dateList.add(new LessonDate(lessonDate));
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

    @Override
    public List<LessonDate> findNextLessons(long dateId, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_NEXT_LESSONS)) {
            statement.setLong(1, gradeId);
            statement.setLong(2, subjectId);
            statement.setLong(3, dateId);
            ResultSet resultSet = statement.executeQuery();
            List<LessonDate> dateList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID);
                Date date = resultSet.getDate(DATE);
                dateList.add(new LessonDate(id, date));
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
