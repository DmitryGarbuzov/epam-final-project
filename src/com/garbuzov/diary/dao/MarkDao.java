package com.garbuzov.diary.dao;

import com.garbuzov.diary.connection.ConnectionPool;
import com.garbuzov.diary.exception.DaoException;
import java.sql.*;

public class MarkDao implements AutoCloseable {

    private Connection connection;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SQL_FIND_MARK = "SELECT mark FROM mark INNER JOIN timetable ON mark.data_id=timetable.id " +
                                                "WHERE mark.student_id=? AND timetable.id=?";

    public MarkDao() {
        connection = connectionPool.getConnection();
    }

    public int find(long dateId, long studentId, long subjectId) throws DaoException {
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
