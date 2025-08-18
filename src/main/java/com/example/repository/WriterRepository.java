package com.example.repository;

import java.util.List;

import com.example.model.Post;
import com.example.model.Writer;

/**
 * Writer Repository interface.
 */

public interface WriterRepository extends GenericRepository<Writer, Long> {
    List<Post> getAllPostsByWriterId(Long id) throws Exception;
}
