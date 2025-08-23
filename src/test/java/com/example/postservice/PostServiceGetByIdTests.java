package com.example.postservice;

import com.example.controller.ControllerClientStatus;
import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.service.ServiceException;
import com.example.service.impl.PostServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for PostService getById method.
 */

public class PostServiceGetByIdTests {

    @Test
    public void testPositivePostServiceGetById() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Label l = new Label();
        l.setId(1L);
        Post p = new Post();
        p.setId(1L);

        doReturn(p).when(mockPR).getById(anyLong());
        doReturn(List.of(l)).when(mockPR).getAllLabelsByPostId(anyLong());

        assertEquals(l, postService.getById(1L).getLabels().getFirst());
    }

    @Test
    public void testNegativePostServiceGetById() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);

        doThrow(new RepositoryException()).when(mockPR).getById(anyLong());

        assertThrows(ServiceException.class, () -> postService.getById(1L));
    }

    @Test
    public void testPositivePostServiceGetByIdClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(p).when(mockPR).getById(anyLong());
        assertEquals(null, postService.getById(1L, ControllerClientStatus.USER));
    }

    @Test
    public void testPositivePostServiceGetByIdClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.ACTIVE);

        doReturn(p).when(mockPR).getById(anyLong());
        assertEquals(p, postService.getById(1L, ControllerClientStatus.USER));
    }

    @Test
    public void testPositivePostServiceGetByIdClientStatusUserDeleted() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.DELETED);

        doReturn(p).when(mockPR).getById(anyLong());
        assertEquals(null, postService.getById(1L, ControllerClientStatus.USER));
    }

    @Test
    public void testNegativePostServiceGetByIdClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        doThrow(new RepositoryException()).when(mockPR).getById(anyLong());
        assertThrows(ServiceException.class, () -> postService.getById(1L, ControllerClientStatus.ADMIN));
    }

    @Test
    public void testPositivePostServiceGetByIdClientStatusAdminUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(p).when(mockPR).getById(anyLong());
        assertEquals(p, postService.getById(1L, ControllerClientStatus.ADMIN));
    }
}
