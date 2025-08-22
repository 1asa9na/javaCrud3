package com.example.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract class for JDBC repositories.
 */

public abstract class JdbcRepository {
    private final String url;
    private final String user;
    private final String password;

    /**
     * Constructor for JdbcRepository.
     * @param jdbcUrl
     * @param jdbcUser
     * @param jdbcPassword
     */

    public JdbcRepository(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.url = jdbcUrl;
        this.user = jdbcUser;
        this.password = jdbcPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void execute(
        PreparedStatement stmt,
        List<Object> params,
        List<JdbcParamType> paramTypes
    ) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            switch (paramTypes.get(i)) {
                case LONG:
                    stmt.setLong(i + 1, (Long) params.get(i));
                    break;
                case STRING:
                    stmt.setString(i + 1, (String) params.get(i));
                    break;
                default:
                    throw new UnsupportedOperationException("Error: unsupported data type.");
            }
        }
    }

    /**
     * Executes a SELECT statement and returns the ResultSet.
     * @param query
     * @param params
     * @param paramTypes
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     */

    public ResultSet executeQuery(String query, List<Object> params, List<JdbcParamType> paramTypes)
        throws SQLException, IllegalArgumentException {
        if (params.size() != paramTypes.size()) {
            throw new IllegalArgumentException("params.size() and paramTypes.size() not equal.");
        }
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            execute(stmt, params, paramTypes);
            return stmt.executeQuery();
        }
    }

    /**
     * Executes an INSERT statement and returns the generated keys.
     * @param query
     * @param params
     * @param paramTypes
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     */

    public ResultSet getGeneratedKeys(String query, List<Object> params, List<JdbcParamType> paramTypes)
        throws SQLException, IllegalArgumentException {
        if (params.size() != paramTypes.size()) {
            throw new IllegalArgumentException("params.size() and paramTypes.size() not equal.");
        }
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            execute(stmt, params, paramTypes);
            stmt.executeQuery();
            return stmt.getGeneratedKeys();
        }
    }
}
