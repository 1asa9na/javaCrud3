package com.example.repository.jdbc;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.repository.RepositoryException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC Label Repository implementation.
 */

public class JdbcLabelRepositoryImpl extends JdbcRepository implements LabelRepository {
    public JdbcLabelRepositoryImpl(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        super(jdbcUrl, jdbcUser, jdbcPassword);
    }

    @Override
    public Label getById(Long id) throws RepositoryException {
        String query = "SELECT * FROM labels WHERE id = ?";
        try {
            ResultSet rs = executeQuery(query, List.of(id), List.of(JdbcParamType.LONG));
            if (rs.next()) {
                return new Label(
                    rs.getLong("id"),
                    rs.getString("name")
                );
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new RepositoryException("Error on repository.getById", e);
        }
        return null;
    }

    @Override
    public List<Label> getAll() throws RepositoryException {
        String query = "SELECT * FROM labels";
        List<Label> labels = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(query, List.of(), List.of());
            while (rs.next()) {
                labels.add(
                    new Label(
                        rs.getLong("id"),
                        rs.getString("name")
                    )
                );
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.getAll", e);
        }
        return labels;
    }

    @Override
    public Label save(Label entity) throws RepositoryException {
        String query = "INSERT INTO labels (name) VALUES (?)";
        try {
            ResultSet rs = getGeneratedKeys(query, List.of(entity.getName()), List.of(JdbcParamType.STRING));
            if (rs.next()) {
                entity.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.save", e);
        }
        return entity;
    }

    @Override
    public Label update(Label entity) throws RepositoryException {
        String query = "UPDATE labels SET name = ? WHERE id = ?";
        try {
            executeQuery(
                query,
                List.of(entity.getName(), entity.getId()),
                List.of(JdbcParamType.STRING, JdbcParamType.LONG)
            );
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.update", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        String query = "DELETE FROM labels WHERE id = ?";
        try {
            executeQuery(query, List.of(id), List.of(JdbcParamType.LONG));
        } catch (SQLException e) {
            throw new RepositoryException("Error on repository.deleteById", e);
        }
    }
}
