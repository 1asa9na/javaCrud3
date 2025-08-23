package com.example.postservice;

import com.example.controller.ControllerClientStatus;
import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.service.ServiceException;
import com.example.service.impl.PostServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for PostService getAll method.
 */

public class PostServiceGetAllTests {

    @Test
    public void testPositivePostServiceGetAll() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Label l = new Label();
        Post p = new Post();
        p.setId(1L);

        doReturn(new ArrayList<>(List.of(p))).when(mockPR).getAll();
        doReturn(new ArrayList<>(List.of(l))).when(mockPR).getAllLabelsByPostId(anyLong());

        assertEquals(l, postService.getAll().getFirst().getLabels().getFirst());
    }

    @Test
    public void testNegativePostServiceGetAll() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);

        doThrow(new RepositoryException()).when(mockPR).getAll();

        assertThrows(ServiceException.class, () -> postService.getAll());
    }

    @Test
    public void testPositivePostServiceGetAllClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(new ArrayList<>(List.of(p))).when(mockPR).getAll();
        assertEquals(0, postService.getAll(ControllerClientStatus.USER).size());
    }

    @Test
    public void testPositivePostServiceGetAllClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.ACTIVE);

        doReturn(new ArrayList<>(List.of(p))).when(mockPR).getAll();
        assertEquals(p, postService.getAll(ControllerClientStatus.USER).getFirst());
    }

    @Test
    public void testPositivePostServiceGetAllClientStatusUserDeleted() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.DELETED);

        doReturn(new ArrayList<>(List.of(p))).when(mockPR).getAll();
        assertEquals(0, postService.getAll(ControllerClientStatus.USER).size());
    }

    @Test
    public void testNegativePostServiceGetAllClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        doThrow(new RepositoryException()).when(mockPR).getAll();
        assertThrows(ServiceException.class, () -> postService.getAll(ControllerClientStatus.ADMIN));
    }

    @Test
    public void testPositivePostServiceGetAllClientStatusAdminUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(new ArrayList<>(List.of(p))).when(mockPR).getAll();
        assertEquals(p, postService.getAll(ControllerClientStatus.ADMIN).getFirst());
    }
}
