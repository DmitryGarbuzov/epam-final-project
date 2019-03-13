package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.entity.Parent;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParentDao implements MainRoleDao<Parent> {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_INSERT_PARENT = "INSERT INTO parent (user_id) VALUES (?)";
    private final static String SQL_INSERT_PARENT_STUDENT = "INSERT INTO parent_student (parent_id, student_id) VALUES (?, ?)";
    private final static String SQL_DELETE_PARENT = "DELETE FROM parent WHERE user_id=?";
    private final static String SQL_DELETE_PARENT_STUDENT = "DELETE FROM parent_student WHERE parent_id=?";
    private final static String SQL_SELECT_PARENT = "SELECT id FROM parent WHERE user_id=?";
    private final static String SQL_FIND_ALL_PARENT = "SELECT user.id, first_name, last_name, email FROM parent INNER JOIN user ON " +
                                                      "parent.user_id=user.id";
    private final static String SQL_HAS_ACTIVE_STUDENT = "SELECT isActive FROM parent INNER JOIN parent_student ON " +
                                                         "parent.id=parent_student.parent_id INNER JOIN student ON " +
                                                         "parent_student.student_id=student.id WHERE parent.user_id=?";
    private final static String ID = "id";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String EMAIL = "email";
    private final static String IS_ACTIVE = "isActive";

    public ParentDao() {
        connection = connectionPool.getConnection();
    }

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
    public void add(Parent parent) throws DaoException {
        try(PreparedStatement statement1 = connection.prepareStatement(SQL_INSERT_PARENT, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(SQL_INSERT_PARENT_STUDENT)) {
            statement1.setLong(1, parent.getId());
            statement1.executeUpdate();
            ResultSet resultSet = statement1.getGeneratedKeys();
            if (resultSet.next()) {
                parent.setParentId(resultSet.getLong(1));
            }
            statement2.setLong(1, parent.getParentId());
            for (int i = 0; i < parent.getStudentList().size(); i += 1) {
                statement2.setLong(2, parent.getStudentList()
                        .get(i)
                        .getStudentId());
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
        try(PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_PARENT);
            PreparedStatement statement2 = connection.prepareStatement(SQL_DELETE_PARENT_STUDENT);
            PreparedStatement statement3 = connection.prepareStatement(SQL_SELECT_PARENT)) {
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
        try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PARENT)){
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
    public Parent create(User user) throws DaoException {
        return null;
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
