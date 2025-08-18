package com.example.repository;

import java.util.List;

import com.example.model.Label;
import com.example.model.Post;

/**
 * Post Repository interface.
 */

public interface PostRepository extends GenericRepository<Post, Long> {
    List<Label> getAllLabelsByPostId(Long id) throws Exception;
    void deleteLabelFromPostById(Long postId, Long labelId) throws Exception;
    void addLabelToPostById(Long postId, Long labelId) throws Exception;
}
