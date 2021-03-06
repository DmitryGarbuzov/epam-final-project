package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.LessonDao;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LessonDaoImpl implements AutoCloseable, LessonDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String  ID = "id";
    private final static String GRADE_ID = "grade.id";
    private final static String LETTER = "letter";
    private final static String NUMBER = "number";
    private final static String SUBJECT_ID = "subject.id";
    private final static String NAME = "name";
    private final static String SQL_UPDATE_DELETE_LESSON = "UPDATE lesson SET isActive=FALSE WHERE teacher_id=? AND grade_id=?";
    private final static String SQL_UPDATE_INSERT_LESSON = "UPDATE lesson SET isActive=TRUE WHERE teacher_id=? AND grade_id=? AND subject_id=?";
    private final static String SQL_INSERT_LESSON = "INSERT INTO lesson (teacher_id, grade_id, subject_id) VALUES (?, ?, ?)";
    private final static String SQL_SELECT_LESSON_ID = "SELECT id FROM lesson WHERE grade_id=? AND teacher_id=? AND subject_id=?";
    private final static String SQL_FIND_ALL_GRADES = "SELECT grade.id, subject.id, number, letter, name FROM lesson INNER JOIN " +
                                                      "grade ON grade.id=lesson.grade_id INNER JOIN subject ON lesson.subject_id=subject.id " +
                                                      "WHERE lesson.teacher_id=? AND subject.isActive=TRUE AND grade.isActive=TRUE " +
                                                      "AND lesson.isActive=TRUE";

    public LessonDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
    public void add(long teacherId, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LESSON)){
            statement.setLong(1, teacherId);
            statement.setLong(2, gradeId);
            statement.setLong(3, subjectId);
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
    public void update(long teacherId, long gradeId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DELETE_LESSON)){
            statement.setLong(1, teacherId);
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
    public void update(long teacherId, long gradeId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INSERT_LESSON)){
            statement.setLong(1, teacherId);
            statement.setLong(2, gradeId);
            statement.setLong(3, subjectId);
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
    public long findLessonId(long gradeId, long teacherId, long subjectId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_LESSON_ID)){
            statement.setLong(1, gradeId);
            statement.setLong(2, teacherId);
            statement.setLong(3, subjectId);
            ResultSet resultSet = statement.executeQuery();
            long lessonId = 0;
            if (resultSet.next()) {
                lessonId = resultSet.getLong(ID);
            }
            return lessonId;
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
    public Map<Grade, ArrayList<Subject>> findAllGrades(long teacherId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_GRADES)) {
            statement.setLong(1, teacherId);
            Map<Grade, ArrayList<Subject>> gradeMap = new TreeMap<>(Comparator.comparingInt(Grade::getNumber)
                                                                              .thenComparing(Grade::getLetter));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long gradeId = resultSet.getLong(GRADE_ID);
                String letter = resultSet.getString(LETTER);
                int number = resultSet.getInt(NUMBER);
                long subjectId = resultSet.getLong(SUBJECT_ID);
                String name = resultSet.getString(NAME);
                Grade grade = new Grade(number, letter, gradeId);
                Subject subject = new Subject(subjectId, name);
                if (!gradeMap.containsKey(grade)) {
                    gradeMap.put(grade, new ArrayList<>());
                }
                gradeMap.get(grade).add(subject);
            }
            return gradeMap;
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