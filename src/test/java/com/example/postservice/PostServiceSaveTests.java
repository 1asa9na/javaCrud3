package com.example.postservice;

import com.example.model.Post;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.service.ServiceException;
import com.example.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for PostService save method.
 */

public class PostServiceSaveTests {

    @Test
    public void testPositivePostServiceSave() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();

        doReturn(p).when(mockPR).save(any(Post.class));
        assertEquals(p, postService.save(new Post()));
    }

    @Test
    public void testNegativePostServiceSave() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        doThrow(new RepositoryException()).when(mockPR).save(any(Post.class));
        assertThrows(ServiceException.class, () -> postService.save(new Post()));
    }
}
