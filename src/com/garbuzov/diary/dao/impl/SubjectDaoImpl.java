package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.SubjectDao;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements AutoCloseable, SubjectDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String SQL_IS_PRESENT_SUBJECT = "SELECT id FROM subject WHERE name=?";
    private final static String SQL_FIND_ALL_SUBJECT = "SELECT id, name FROM subject WHERE isActive=?";
    private final static String SQL_INSERT_SUBJECT = "INSERT INTO subject (name) VALUES (?)";
    private final static String SQL_UPDATE_SUBJECT = "UPDATE subject SET isActive=? WHERE id=?";
    private final static String SQL_FIND_FOR_STUDENT = "SELECT DISTINCT s.id, name FROM subject s INNER JOIN lesson l on s.id = l.subject_id " +
                                                       "INNER JOIN grade g on l.grade_id = g.id INNER JOIN student st on g.id = st.grade_id " +
                                                       "WHERE st.id=? AND l.isActive=TRUE";

    public SubjectDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
    public void add(Subject subject) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SUBJECT)) {
            statement.setString(1, subject.getName());
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
    public void update(long subjectId, boolean isActive) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_SUBJECT)) {
            statement.setBoolean(1, isActive);
            statement.setLong(2, subjectId);
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
    public boolean isPresent(String subjectName) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_IS_PRESENT_SUBJECT)){
            boolean flag = false;
            statement.setString(1, subjectName);
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
    public List<Subject> findAll(boolean isActive) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_SUBJECT)){
            statement.setBoolean(1, isActive);
            ResultSet resultSet = statement.executeQuery();
            List<Subject> subjectList = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString(NAME);
                long subjectId = resultSet.getLong(ID);
                subjectList.add(new Subject(subjectId, name));
            }
            return subjectList;
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
    public List<Subject> findForStudent(long studentId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_FOR_STUDENT)) {
            List<Subject> subjectList = new ArrayList<>();
            statement.setLong(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(resultSet.getLong(ID));
                subject.setName(resultSet.getString(NAME));
                subjectList.add(subject);
            }
            return subjectList;
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
