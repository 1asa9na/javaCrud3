package com.example.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Label;
import com.example.repository.LabelRepository;

public class JdbcLabelRepositoryImpl implements LabelRepository {

    private final String JDBC_URL;
    private final String JDBC_USER;
    private final String JDBC_PASSWORD;

    public JdbcLabelRepositoryImpl(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.JDBC_URL = jdbcUrl;
        this.JDBC_USER = jdbcUser;
        this.JDBC_PASSWORD = jdbcPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public Label getById(Long id) throws SQLException {
        String query = "SELECT * FROM labels WHERE id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Label(
                    rs.getLong("id"),
                    rs.getString("name")
                );
            }
        }
        return null;
    }

    @Override
    public List<Label> getAll() throws SQLException {
        String query = "SELECT * FROM labels";
        List<Label> labels = new ArrayList<>();
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                labels.add(new Label(
                    rs.getLong("id"),
                    rs.getString("name")
                ));
            }
        }
        return labels;
    }

    @Override
    public Label save(Label entity) throws SQLException {
        String query = "INSERT INTO labels (name) VALUES (?)";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, entity.getName());
            stmt.executeQuery();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
        }
        return entity;
    }

    @Override
    public Label update(Label entity) throws SQLException {
        String query = "UPDATE labels SET name = ? WHERE id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, entity.getName());
            stmt.setLong(2, entity.getId());
            stmt.executeQuery();
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM labels WHERE id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            stmt.executeQuery();
        }
    }
}
