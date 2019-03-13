package com.garbuzov.diary.connection;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
    private BlockingQueue<ProxyConnection> connections;
    private final String CONNECTION_PROPERTIES = "connection.properties";
    private final String POOL_SIZE = "db.poolSize";
    private final String URL = "db.url";
    private final String USER = "db.user";
    private final String PASS = "db.password";
    private final int DEFAULT_POOL_SIZE = 10;
    private static ConnectionPool instance;
    private static ReentrantLock locker = new ReentrantLock();
    private static Logger logger = LogManager.getLogger();

    private ConnectionPool() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream(CONNECTION_PROPERTIES));
            init(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(Properties properties) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            int poolSize = properties.getProperty(POOL_SIZE) != null ? Integer.parseInt(properties.getProperty(POOL_SIZE)) : DEFAULT_POOL_SIZE;
            connections = new LinkedBlockingDeque<>(poolSize);
            for (int i = 0; i < poolSize; i += 1) {
                try {
                    ProxyConnection temp = new ProxyConnection(DriverManager.getConnection(properties.getProperty(URL),
                                                                                           properties.getProperty(USER),
                                                                                           properties.getProperty(PASS)));
                    temp.setAutoCommit(false);
                    connections.put(temp);
                } catch (InterruptedException e) {
                    //
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        locker.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPool();
            }
            return instance;
        } finally {
            locker.unlock();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            //log
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {//todo
            //log
            Thread.currentThread().interrupt();
        }
    }

    public void destroyConnections() {
        int poolSize = connections.size();
        for (int i = 0; i < poolSize; i += 1) {
            try {
                ProxyConnection temp = connections.take();
                temp.closeConnection();
                //Deregister driver
            } catch (InterruptedException | SQLException e) {
                //
            }
        }
    }
}
