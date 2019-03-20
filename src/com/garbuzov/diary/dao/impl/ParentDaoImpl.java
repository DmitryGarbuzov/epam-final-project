package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.ParentDao;
import com.garbuzov.diary.entity.*;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParentDaoImpl implements AutoCloseable, ParentDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String ID = "id";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String EMAIL = "email";
    private final static String IS_ACTIVE = "isActive";
    private final static String STUDENT_ID = "s.id";
    private final static String GRADE_ID = "g.id";
    private final static String LETTER = "letter";
    private final static String NUMBER = "number";
    private final static String SQL_INSERT_PARENT = "INSERT INTO parent (user_id) VALUES (?)";
    private final static String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";
    private final static String SQL_INSERT_USER = "INSERT INTO user (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";
    private final static String SQL_INSERT_PARENT_STUDENT = "INSERT INTO parent_student (parent_id, student_id) VALUES (?, ?)";
    private final static String SQL_DELETE_PARENT = "DELETE FROM parent WHERE user_id=?";
    private final static String SQL_DELETE_PARENT_STUDENT = "DELETE FROM parent_student WHERE parent_id=?";
    private final static String SQL_SELECT_PARENT = "SELECT id FROM parent WHERE user_id=?";
    private final static String SQL_FIND_ALL_PARENT = "SELECT user.id, first_name, last_name, email FROM parent INNER JOIN user ON " +
                                                      "parent.user_id=user.id";
    private final static String SQL_HAS_ACTIVE_STUDENT = "SELECT isActive FROM parent INNER JOIN parent_student ON " +
                                                         "parent.id=parent_student.parent_id INNER JOIN student ON " +
                                                         "parent_student.student_id=student.id WHERE parent.user_id=?";
    private final static String SQL_SELECT_STUDENT_FOR_PARENT = "SELECT s.id, s.first_name, s.last_name, g.id, g.number, g.letter " +
                                                                "FROM parent p INNER JOIN parent_student ps ON p.id = ps.parent_id INNER JOIN student s ON " +
                                                                "ps.student_id=s.id INNER JOIN grade g on s.grade_id = g.id WHERE " +
                                                                "p.user_id=? AND s.isActive=TRUE AND g.isActive=TRUE";

    public ParentDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
    public void add(Parent parent, int password) throws DaoException {
        try (PreparedStatement statement1 = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = connection.prepareStatement(SQL_INSERT_PARENT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statement3 = connection.prepareStatement(SQL_INSERT_PARENT_STUDENT)) {
            statement1.setString(1, parent.getEmail());
            statement1.setInt(2, password);
            statement1.setString(3, parent.getFirstName());
            statement1.setString(4, parent.getLastName());
            statement1.executeUpdate();
            ResultSet resultSet = statement1.getGeneratedKeys();
            if (resultSet.next()) {
                parent.setId(resultSet.getLong(1));
            }
            statement2.setLong(1, parent.getId());
            statement2.executeUpdate();
            resultSet = statement2.getGeneratedKeys();
            if (resultSet.next()) {
                parent.setParentId(resultSet.getLong(1));
            }
            statement3.setLong(1, parent.getParentId());
            for (int i = 0; i < parent.getStudentList().size(); i += 1) {
                statement3.setLong(2, parent.getStudentList()
                        .get(i)
                        .getStudentId());
                statement3.executeUpdate();
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
        try (PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_PARENT);
             PreparedStatement statement2 = connection.prepareStatement(SQL_DELETE_PARENT_STUDENT);
             PreparedStatement statement3 = connection.prepareStatement(SQL_SELECT_PARENT);
             PreparedStatement statement4 = connection.prepareStatement(SQL_DELETE_USER)) {
            statement3.setLong(1, userId);
            statement1.setLong(1, userId);
            statement4.setLong(1, userId);
            ResultSet resultSet = statement3.executeQuery();
            if (resultSet.next()) {
                statement2.setLong(1, resultSet.getLong(ID));
            }
            statement2.executeUpdate();
            statement1.executeUpdate();
            statement4.executeUpdate();
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
    public Parent create(User user) throws DaoException {
        try ( PreparedStatement statement1 = connection.prepareStatement(SQL_SELECT_PARENT);
             PreparedStatement statement2 = connection.prepareStatement(SQL_SELECT_STUDENT_FOR_PARENT)) {
            Parent parent = new Parent();
            statement1.setLong(1, user.getId());
            statement2.setLong(1, user.getId());
            ResultSet resultSet = statement1.executeQuery();
            if (resultSet.next()) {
                parent.setParentId(resultSet.getLong(ID));
            }
            resultSet = statement2.executeQuery();
            while (resultSet.next()) {
                long studentId = resultSet.getLong(STUDENT_ID);
                String firstName = resultSet.getString(FIRST_NAME);
                String lastName = resultSet.getString(LAST_NAME);
                long gradeId = resultSet.getLong(GRADE_ID);
                String letter = resultSet.getString(LETTER);
                int number = resultSet.getInt(NUMBER);
                Grade grade = new Grade(number, letter, gradeId);
                Student student = new Student(studentId, firstName, lastName);
                student.setGrade(grade);
                parent.addStudent(student);
            }
            return parent;
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PARENT)){
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
    public boolean hasActiveStudent(long userId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_HAS_ACTIVE_STUDENT)) {
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
    public List<Parent> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_PARENT)){
            ResultSet resultSet = statement.executeQuery();
            List<Parent> parentList = new ArrayList<>();
            while (resultSet.next()) {
                Parent parent = new Parent();
                parent.setId(resultSet.getLong(ID));
                parent.setFirstName(resultSet.getString(FIRST_NAME));
                parent.setLastName(resultSet.getString(LAST_NAME));
                parent.setEmail(resultSet.getString(EMAIL));
                parentList.add(parent);
            }
            return parentList;
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
