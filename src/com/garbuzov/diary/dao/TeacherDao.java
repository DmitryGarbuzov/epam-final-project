package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.entity.Grade;
import com.garbuzov.diary.entity.Subject;
import com.garbuzov.diary.entity.Teacher;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherDao implements MainRoleDao<Teacher> {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_INSERT_TEACHER = "INSERT INTO teacher (user_id) VALUES (?)";
    private final static String SQL_INSERT_TEACHER_SUBJECT = "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
    private final static String SQL_DELETE_TEACHER = "DELETE FROM teacher WHERE user_id=?";
    private final static String SQL_DELETE_TEACHER_SUBJECT = "DELETE FROM teacher_subject WHERE teacher_id=?";
    private final static String SQL_SELECT_TEACHER = "SELECT id FROM teacher WHERE user_id=?";
    private final static String SQL_FIND_ALL_TEACHER = "SELECT user.id, first_name, last_name, email FROM teacher INNER JOIN user ON " +
                                                       "teacher.user_id=user.id";
    private final static String SQL_HAS_ACTIVE_SUBJECT = "SELECT isActive FROM teacher INNER JOIN teacher_subject ON " +
                                                         "teacher.id=teacher_subject.teacher_id INNER JOIN subject ON " +
                                                         "teacher_subject.subject_id=subject.id WHERE teacher.user_id=?";
    private final static String SQL_SELECT_SUBJECT_FOR_TEACHER = "SELECT subject.id, name FROM teacher INNER JOIN teacher_subject ON " +
                                                                 "teacher.id=teacher_subject.teacher_id INNER JOIN subject ON " +
                                                                 "teacher_subject.subject_id=subject.id WHERE teacher.user_id=? AND isActive=TRUE";
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String EMAIL = "email";
    private final static String IS_ACTIVE = "isActive";

    public TeacherDao() {
        connection = connectionPool.getConnection();
    }

    public boolean hasActiveStudent(long userId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_HAS_ACTIVE_SUBJECT)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            boolean flag = false;
            while (resultSet.next()) {
                if (resultSet.getBoolean(IS_ACTIVE)) {
                    flag = true;
                }
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
    public void add(Teacher teacher) throws DaoException {
        try(PreparedStatement statement1 = connection.prepareStatement(SQL_INSERT_TEACHER, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(SQL_INSERT_TEACHER_SUBJECT)) {
            statement1.setLong(1, teacher.getId());
            statement1.executeUpdate();
            ResultSet resultSet = statement1.getGeneratedKeys();
            if (resultSet.next()) {
                teacher.setTeacherId(resultSet.getLong(1));
            }
            statement2.setLong(1, teacher.getTeacherId());
            for (int i = 0; i < teacher.getSubjectList().size(); i += 1) {
                statement2.setLong(2, teacher.getSubjectList()
                        .get(i)
                        .getSubjectId());
                statement2.executeUpdate();
            }
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
    public void delete(long userId) throws DaoException {
        try(PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_TEACHER);
            PreparedStatement statement2 = connection.prepareStatement(SQL_DELETE_TEACHER_SUBJECT);
            PreparedStatement statement3 = connection.prepareStatement(SQL_SELECT_TEACHER)) {
            statement3.setLong(1, userId);
            statement1.setLong(1, userId);
            ResultSet resultSet = statement3.executeQuery();
            if (resultSet.next()) {
                statement2.setLong(1, resultSet.getLong(ID));
            }
            statement2.executeUpdate();
            statement1.executeUpdate();
            connection.commit();
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
    public boolean isPresent(long userId) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TEACHER)){
            boolean flag = false;
            statement.setLong(1, userId);
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
    public List<Teacher> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_TEACHER)){
            ResultSet resultSet = statement.executeQuery();
            List<Teacher> teacherList = new ArrayList<>();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getLong(ID));
                teacher.setFirstName(resultSet.getString(FIRST_NAME));
                teacher.setLastName(resultSet.getString(LAST_NAME));
                teacher.setEmail(resultSet.getString(EMAIL));
                teacherList.add(teacher);
            }
            return teacherList;
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
    public Teacher create(User user) throws DaoException {
        try(PreparedStatement statement1 = connection.prepareStatement(SQL_SELECT_TEACHER);
            PreparedStatement statement2 = connection.prepareStatement(SQL_SELECT_SUBJECT_FOR_TEACHER)) {
            Teacher teacher = new Teacher();
            statement1.setLong(1, user.getId());
            statement2.setLong(1, user.getId());
            ResultSet resultSet = statement1.executeQuery();
            if (resultSet.next()) {
                teacher.setTeacherId(resultSet.getLong(ID));
            }
            resultSet = statement2.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID);
                String name = resultSet.getString(NAME);
                teacher.addSubject(new Subject(id, name));
            }
            return teacher;
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
