package com.garbuzov.diary.dao.impl;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.dao.AdminDao;
import com.garbuzov.diary.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AdminDaoImpl implements AutoCloseable, AdminDao {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String ID = "id";
    private final static String SQL_SELECT_ADMIN = "SELECT id FROM administrator WHERE user_id=?";

    public AdminDaoImpl() {
        connection = connectionPool.getConnection();
    }

    @Override
    public Optional<Long> find(long userId) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ADMIN)){
            Long parentId = null;
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                parentId = resultSet.getLong(ID);
            }
            return Optional.ofNullable(parentId);
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
