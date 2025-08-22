package com.example.repository;

import com.example.model.Label;
import com.example.model.Post;
import java.util.List;

/**
 * Post Repository interface.
 */

public interface PostRepository extends GenericRepository<Post, Long> {

    List<Label> getAllLabelsByPostId(Long id) throws RepositoryException;

    void deleteLabelFromPostById(Long postId, Long labelId) throws RepositoryException;

    void addLabelToPostById(Long postId, Long labelId) throws RepositoryException;
}
