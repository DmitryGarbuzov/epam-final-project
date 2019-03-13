package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.entity.User;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;
import java.util.Optional;

public class UserDao implements AutoCloseable {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_INSERT_USER = "INSERT INTO user (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";
    private final static String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";
    private final static String SQL_SELECT_USER = "SELECT id, first_name, last_name FROM user WHERE email=? AND password=?";
    private final static String SQL_IS_PRESENT_USER = "SELECT id FROM user WHERE email=?";
    private final static String ID = "id";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";

    public UserDao() {
        connection = connectionPool.getConnection();
    }

    public void delete(long userId) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    public Optional<User> find(String email, int pass) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER)) {
            User user = null;
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setEmail(email);
                user.setId(resultSet.getInt(ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    public boolean isPresent(String email) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_IS_PRESENT_USER)) {
            boolean flag = false;
            statement.setString(1, email);
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

    public void add(User user, int password) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setInt(2, password);
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            connection.commit();//todo
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw  new DaoException(exception);
            }
            throw new DaoException(e);
        }
    }

    public void close() throws DaoException {//todo
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
