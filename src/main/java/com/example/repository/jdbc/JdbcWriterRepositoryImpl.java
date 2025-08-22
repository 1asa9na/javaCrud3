package com.example.repository.jdbc;

import com.example.model.Post;
import com.example.model.Writer;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of the WriterRepository interface.
 */

public class JdbcWriterRepositoryImpl extends JdbcRepository implements WriterRepository {

    /**
     * Constructor for JdbcWriterRepositoryImpl.
     * @param jdbcUrl
     * @param jdbcUser
     * @param jdbcPassword
     */

    public JdbcWriterRepositoryImpl(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        super(jdbcUrl, jdbcUser, jdbcPassword);
    }

    @Override
    public Writer getById(Long id) throws RepositoryException {
        String query = "SELECT * FROM writers WHERE id = ?";
        try {
            ResultSet rs = executeQuery(query, List.of(id), List.of(JdbcParamType.LONG));
            if (rs.next()) {
                Writer writer = new Writer();
                writer.setFirstName(rs.getString("first_name"));
                writer.setLastName(rs.getString("last_name"));
                return writer;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getById", e);
        }
        return null;
    }

    @Override
    public List<Writer> getAll() throws RepositoryException {
        String query = "SELECT * FROM writers";
        List<Writer> writers = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(query, List.of(), List.of());
            while (rs.next()) {
                Writer writer = new Writer();
                writer.setFirstName(rs.getString("first_name"));
                writer.setLastName(rs.getString("last_name"));
                writers.add(writer);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getAll", e);
        }
        return writers;
    }

    @Override
    public Writer save(Writer entity) throws RepositoryException {
        String query = "INSERT INTO writers (first_name, last_name) VALUES (?)";
        try {
            ResultSet rs = getGeneratedKeys(
                query,
                List.of(entity.getFirstName(), entity.getLastName()),
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
    public Writer update(Writer entity) throws RepositoryException {
        String query = "UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?";
        try {
            executeQuery(
                query,
                List.of(entity.getFirstName(), entity.getLastName(), entity.getId()),
                List.of(JdbcParamType.STRING, JdbcParamType.STRING, JdbcParamType.LONG)
            );
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.update", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        String query = "DELETE FROM writers WHERE id = ?";
        try {
            executeQuery(query, List.of(id), List.of(JdbcParamType.LONG));
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.deleteById", e);
        }
    }

    @Override
    public List<Post> getAllPostsByWriterId(Long id) throws RepositoryException {
        String query = "SELECT p.* FROM posts p WHERE p.writer_id = ?";
        List<Post> posts = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(query, List.of(id), List.of(JdbcParamType.LONG));
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getDate("created"));
                post.setUpdated(rs.getDate("updated"));
                posts.add(post);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getAllPostsByWriterId", e);
        }
        return posts;
    }
}
