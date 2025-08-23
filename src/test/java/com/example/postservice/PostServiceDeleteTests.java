package com.example.postservice;

import com.example.controller.ControllerClientStatus;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.service.ServiceException;
import com.example.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test suite for PostService delete method.
 */

public class PostServiceDeleteTests {

    @Test
    public void testPositivePostServiceDelete() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);

        doNothing().when(mockPR).deleteById(anyLong());
        postService.delete(1L);
        verify(mockPR, times(1)).deleteById(1L);
    }

    @Test
    public void testNegativePostServiceDelete() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);

        doThrow(new RepositoryException()).when(mockPR).deleteById(anyLong());
        assertThrows(ServiceException.class, () -> postService.delete(1L));
    }

    @Test
    public void testPositivePostServiceDeleteClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(p).when(mockPR).getById(anyLong());
        doNothing().when(mockPR).deleteById(anyLong());
        postService.delete(1L, ControllerClientStatus.USER);
        verify(mockPR, times(0)).deleteById(1L);
    }

    @Test
    public void testPositivePostServiceDeleteClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.ACTIVE);

        doReturn(p).when(mockPR).getById(anyLong());
        doNothing().when(mockPR).deleteById(anyLong());
        postService.delete(1L, ControllerClientStatus.USER);
        verify(mockPR, times(1)).deleteById(1L);
    }

    @Test
    public void testNegativePostServiceDeleteClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        doThrow(new RepositoryException()).when(mockPR).getById(anyLong());
        assertThrows(ServiceException.class, () -> postService.delete(1L, ControllerClientStatus.ADMIN));
    }
}
