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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for PostService update method.
 */

public class PostServiceUpdateTests {

    @Test
    public void testPositivePostServiceUpdate() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Label l = new Label();
        Post p = new Post();
        p.setId(1L);

        doReturn(p).when(mockPR).update(any(Post.class));
        doReturn(List.of(l)).when(mockPR).getAllLabelsByPostId(anyLong());

        assertEquals(l, postService.update(new Post()).getLabels().getFirst());
    }

    @Test
    public void testNegativePostServiceUpdate() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);

        doThrow(new RepositoryException()).when(mockPR).update(any(Post.class));

        assertThrows(ServiceException.class, () -> postService.update(new Post()));
    }

    @Test
    public void testPositivePostServiceUpdateClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        Post p2 = new Post();
        p2.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(p).when(mockPR).getById(anyLong());
        assertEquals(null, postService.update(p2, ControllerClientStatus.USER));
    }

    @Test
    public void testPositivePostServiceUpdateClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        Post p2 = new Post();
        p2.setId(1L);
        p.setStatus(PostStatus.ACTIVE);

        doReturn(p).when(mockPR).getById(anyLong());
        doReturn(p).when(mockPR).update(any(Post.class));
        assertEquals(p, postService.update(p2, ControllerClientStatus.USER));
    }

    @Test
    public void testPositivePostServiceUpdateClientStatusUserDeleted() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        Post p2 = new Post();
        p2.setId(1L);
        p.setStatus(PostStatus.DELETED);

        doReturn(p).when(mockPR).getById(anyLong());
        doReturn(p).when(mockPR).update(any(Post.class));
        assertEquals(null, postService.update(p2, ControllerClientStatus.USER));
    }

    @Test
    public void testNegativePostServiceUpdateClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);

        doThrow(new RepositoryException()).when(mockPR).getById(anyLong());
        assertThrows(ServiceException.class, () -> postService.update(p, ControllerClientStatus.ADMIN));
    }

    @Test
    public void testPositivePostServiceUpdateClientStatusAdminUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        PostServiceImpl postService = new PostServiceImpl(mockPR);

        Post p = new Post();
        p.setId(1L);
        Post p2 = new Post();
        p2.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        doReturn(p).when(mockPR).getById(anyLong());
        doReturn(p).when(mockPR).update(any(Post.class));
        assertEquals(p, postService.update(p2, ControllerClientStatus.ADMIN));
    }
}
