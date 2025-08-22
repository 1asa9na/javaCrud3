package com.example.repository.jdbc;

/**
 * JDBC parameter types.
 */

public enum JdbcParamType {
    /**
     * Long parameter type.
     */
    LONG(Long.class),

    /**
     * Integer parameter type.
     */
    INT(Integer.class),

    /**
     * String parameter type.
     */
    STRING(String.class);

    private Class<?> type;

    JdbcParamType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
