package com.example.repository;

import com.example.model.Post;
import com.example.model.Writer;
import java.util.List;

/**
 * Writer Repository interface.
 */

public interface WriterRepository extends GenericRepository<Writer, Long> {
    List<Post> getAllPostsByWriterId(Long id) throws RepositoryException;
}
