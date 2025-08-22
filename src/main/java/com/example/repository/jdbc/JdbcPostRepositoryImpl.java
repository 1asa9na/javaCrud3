package com.example.repository.jdbc;

import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of the PostRepository interface.
 */

public class JdbcPostRepositoryImpl extends JdbcRepository implements PostRepository {

    public JdbcPostRepositoryImpl(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        super(jdbcUrl, jdbcUser, jdbcPassword);
    }

    @Override
    public Post getById(Long id) throws RepositoryException {
        String query = "SELECT * FROM posts WHERE id = ?";
        try {
            ResultSet rs = executeQuery(query, List.of(id), List.of(JdbcParamType.LONG));
            if (rs.next()) {
                Post post = new Post();
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getDate("created"));
                post.setUpdated(rs.getDate("updated"));
                post.setStatus(PostStatus.valueOf(rs.getString("status")));
                return post;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getById", e);
        }
        return null;
    }

    @Override
    public List<Post> getAll() throws RepositoryException {
        String query = "SELECT * FROM posts";
        List<Post> posts = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(query, List.of(), List.of());
            while (rs.next()) {
                Post post = new Post();
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getDate("created"));
                post.setUpdated(rs.getDate("updated"));
                post.setStatus(PostStatus.valueOf(rs.getString("status")));
                posts.add(post);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getAll", e);
        }
        return posts;
    }

    @Override
    public Post save(Post entity) throws RepositoryException {
        String query = "INSERT INTO posts (content, status) VALUES (?, ?)";
        try {
            ResultSet rs = getGeneratedKeys(
                query,
                List.of(entity.getContent(), entity.getStatus().name()),
                List.of(JdbcParamType.STRING, JdbcParamType.STRING)
            );
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.save", e);
        }
        return entity;
    }

    @Override
    public Post update(Post entity) throws RepositoryException {
        String query = "UPDATE posts SET content = ?, status = ? WHERE id = ?";
        try {
            executeQuery(
                query,
                List.of(entity.getContent(), entity.getStatus().name(), entity.getId()),
                List.of(JdbcParamType.STRING, JdbcParamType.STRING, JdbcParamType.LONG)
            );
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.update", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        String query = "UPDATE posts SET status = 'DELETED' WHERE id = ?";
        try {
            executeQuery(
                query,
                List.of(id),
                List.of(JdbcParamType.LONG)
            );
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.deleteById", e);
        }
    }

    @Override
    public List<Label> getAllLabelsByPostId(Long id) throws RepositoryException {
        String query = "SELECT l.* FROM labels l "
                       + "JOIN post_labels pl ON l.id = pl.label_id "
                       + "WHERE pl.post_id = ?";
        List<Label> labels = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(
                query,
                List.of(id),
                List.of(JdbcParamType.LONG)
            );
            while (rs.next()) {
                Label label = new Label();
                label.setId(rs.getLong("id"));
                label.setName(rs.getString("name"));
                labels.add(label);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getAllLabelsByPostId", e);
        }
        return labels;
    }

    @Override
    public void deleteLabelFromPostById(Long postId, Long labelId) throws RepositoryException {
        String query = "DELETE FROM post_labels WHERE post_id = ? AND label_id = ?";
        try {
            executeQuery(
                query,
                List.of(postId, labelId),
                List.of(JdbcParamType.LONG, JdbcParamType.LONG)
            );
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.deleteLabelFromPostById", e);
        }
    }

    @Override
    public void addLabelToPostById(Long postId, Long labelId) throws RepositoryException {
        String query = "INSERT INTO post_labels (post_id, label_id) VALUES (?, ?)";
        try {
            executeQuery(
                query,
                List.of(postId, labelId),
                List.of(JdbcParamType.LONG, JdbcParamType.LONG)
            );
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.addLabelToPostById", e);
        }
    }
}
