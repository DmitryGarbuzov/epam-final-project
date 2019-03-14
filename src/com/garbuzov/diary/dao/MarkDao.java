package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;

public class MarkDao implements AutoCloseable {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_FIND_MARK = "SELECT mark FROM mark INNER JOIN timetable ON mark.date_id=timetable.id " +
                                                "WHERE mark.student_id=? AND timetable.id=?";
    private final static String SQL_INSERT_MARK = "INSERT INTO mark (mark, date_id, student_id) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE_MARK = "UPDATE mark SET mark=? WHERE date_id=? AND student_id=?";

    public MarkDao() {
        connection = connectionPool.getConnection();
    }

    public void update(long dateId, long studentId, int mark) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MARK)) {
            statement.setInt(1, mark);
            statement.setLong(2, dateId);
            statement.setLong(3, studentId);
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

    public void add(long dateId, long studentId, int mark) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MARK)) {
            statement.setInt(1, mark);
            statement.setLong(2, dateId);
            statement.setLong(3, studentId);
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

    public int find(long dateId, long studentId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARK)){
            statement.setLong(1, studentId);
            statement.setLong(2, dateId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("mark");
            }
            return 0;
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
