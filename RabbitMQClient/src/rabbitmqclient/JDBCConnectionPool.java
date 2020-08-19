/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmqclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haris Tanwir
 */
public class JDBCConnectionPool {

    private String drivername = null;
    private String url = null;
    private String user = null;
    private String password = null;
    private Integer poolSize = 0;
    private Long connectionWait = 500L;
    private List<Connection> connectionPool = null;

    public JDBCConnectionPool(String drivername, String url, String user, String password) {
        this.drivername = drivername;
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = new ArrayList<>();
    }

    public JDBCConnectionPool(String drivername, String url, String user, String password, Integer poolSize) {
        this.drivername = drivername;
        this.url = url;
        this.user = user;
        this.password = password;
        if (poolSize > this.poolSize) {
            this.poolSize = poolSize;
        }
        this.connectionPool = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            Connection connection = createConnection(drivername, url, user, password);
            if (connection != null) {
                connectionPool.add(connection);
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        if (!connectionPool.isEmpty()) {
            synchronized (connectionPool) {
                if (!connectionPool.isEmpty()) {
                    connection = connectionPool.remove(0);
                } else {
                    connection = createConnection(drivername, url, user, password);
                }
            }
        } else {
            if (poolSize == 0) {
                connection = createConnection(drivername, url, user, password);
            } else {
                while (connection == null) {
                    try {
                        Thread.sleep(connectionWait);
                    } catch (InterruptedException ex) {
                    }
                    if (!connectionPool.isEmpty()) {
                        synchronized (connectionPool) {
                            if (!connectionPool.isEmpty()) {
                                connection = connectionPool.remove(0);
                            } else {
                                connection = createConnection(drivername, url, user, password);
                            }
                        }
                    }
                }
            }
        }
        try {
            if (!connection.isValid(0)) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                }
                connection = null;
                connection = createConnection(drivername, url, user, password);
            }
        } catch (Exception ex) {
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        synchronized (connectionPool) {
            return connectionPool.add(connection);
        }
    }

    private Connection createConnection(String drivername, String url, String user, String password) {
        Connection connection = null;
        try {
            Class.forName(drivername);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return connection;
    }

    public void shutdown() {
        synchronized (connectionPool) {
            for (Connection connection : connectionPool) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                }
                connection = null;
            }
            try {
                connectionPool.clear();
            } catch (Exception ex) {
            }
        }
    }

    public int getSize() {
        return connectionPool.size();
    }

    public String getDrivername() {
        return drivername;
    }

    public Long getConnectionWait() {
        return connectionWait;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

}
