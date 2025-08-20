package com.example.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;

public class JdbcPostRepositoryImpl implements PostRepository {

    private final String JDBC_URL;
    private final String JDBC_USER;
    private final String JDBC_PASSWORD;

    public JdbcPostRepositoryImpl(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.JDBC_URL = jdbcUrl;
        this.JDBC_USER = jdbcUser;
        this.JDBC_PASSWORD = jdbcPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public Post getById(Long id) throws SQLException {
        String query = "SELECT * FROM posts WHERE id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getDate("created"));
                post.setUpdated(rs.getDate("updated"));
                post.setStatus(PostStatus.valueOf(rs.getString("status")));
                return post;
            }
        }
        return null;
    }

    @Override
    public List<Post> getAll() throws SQLException {
        String query = "SELECT * FROM posts";
        List<Post> posts = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getDate("created"));
                post.setUpdated(rs.getDate("updated"));
                post.setStatus(PostStatus.valueOf(rs.getString("status")));
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public Post save(Post entity) throws SQLException {
        String query = "INSERT INTO posts (content, created, updated, status) VALUES (?)";
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, entity.getContent());
            stmt.setDate(2, new java.sql.Date(entity.getCreated().getTime()));
            stmt.setDate(3, new java.sql.Date(entity.getUpdated().getTime()));
            stmt.setString(4, entity.getStatus().name());
            stmt.executeQuery();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
        }
        return entity;
    }

    @Override
    public Post update(Post entity) throws SQLException {
        String query = "UPDATE posts SET content = ?, created = ?, updated = ?, status = ? WHERE id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, entity.getContent());
            stmt.setDate(2, new java.sql.Date(entity.getCreated().getTime()));
            stmt.setDate(3, new java.sql.Date(entity.getUpdated().getTime()));
            stmt.setString(4, entity.getStatus().name());
            stmt.setLong(5, entity.getId());
            stmt.executeUpdate();
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String query = "UPDATE posts SET status = 'DELETED' WHERE id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Label> getAllLabelsByPostId(Long id) throws SQLException {
        String query = "SELECT l.* FROM labels l " +
                       "JOIN post_labels pl ON l.id = pl.label_id " +
                       "WHERE pl.post_id = ?";
        List<Label> labels = new ArrayList<>();
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Label label = new Label();
                label.setId(rs.getLong("id"));
                label.setName(rs.getString("name"));
                labels.add(label);
            }
        }
        return labels;
    }

    @Override
    public void deleteLabelFromPostById(Long postId, Long labelId) throws SQLException {
        String query = "DELETE FROM post_labels WHERE post_id = ? AND label_id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, postId);
            stmt.setLong(2, labelId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addLabelToPostById(Long postId, Long labelId) throws SQLException {
        String query = "INSERT INTO post_labels (post_id, label_id) VALUES (?, ?)";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setLong(1, postId);
            stmt.setLong(2, labelId);
            stmt.executeUpdate();
        }
    }
}
