package com.example.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Post;
import com.example.model.Writer;
import com.example.repository.WriterRepository;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private final String JDBC_URL;
    private final String JDBC_USER;
    private final String JDBC_PASSWORD;

    public JdbcWriterRepositoryImpl(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.JDBC_URL = jdbcUrl;
        this.JDBC_USER = jdbcUser;
        this.JDBC_PASSWORD = jdbcPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public Writer getById(Long id) throws SQLException {
        String query = "SELECT * FROM writers WHERE id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Writer writer = new Writer();
                writer.setFirstName(rs.getString("first_name"));
                writer.setLastName(rs.getString("last_name"));
                return writer;
            }
        }
        return null;
    }

    @Override
    public List<Writer> getAll() throws SQLException {
        String query = "SELECT * FROM writers";
        List<Writer> writers = new ArrayList<>();
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Writer writer = new Writer();
                writer.setFirstName(rs.getString("first_name"));
                writer.setLastName(rs.getString("last_name"));
                writers.add(writer);
            }
        }
        return writers;
    }

    @Override
    public Writer save(Writer entity) throws SQLException {
        String query = "INSERT INTO writers (first_name, last_name) VALUES (?)";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.executeQuery();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
        }
        return entity;
    }

    @Override
    public Writer update(Writer entity) throws SQLException {
        String query = "UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setLong(3, entity.getId());
            stmt.executeUpdate();
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM writers WHERE id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Post> getAllPostsByWriterId(Long id) throws SQLException {
        String query = "SELECT p.* FROM posts p WHERE p.writer_id = ?";
        List<Post> posts = new ArrayList<>();
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getDate("created"));
                post.setUpdated(rs.getDate("updated"));
                posts.add(post);
            }
        }
        return posts;
    }
}
