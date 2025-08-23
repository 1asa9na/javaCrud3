package com.example.writerservice;

import com.example.controller.ControllerClientStatus;
import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.model.Writer;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import com.example.service.ServiceException;
import com.example.service.WriterService;
import com.example.service.impl.WriterServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for WriterService getById method.
 */

public class WriterServiceGetByIdTests {
    @Test
    public void testPositiveWriterServiceGetById() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Label l = new Label();
        Writer w = new Writer();

        try {
            Post p = new Post();
            p.setId(1L);
            doReturn(new Writer()).when(mockWR).getById(anyLong());
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(Arrays.asList(p)).when(mockWR).getAllPostsByWriterId(anyLong());
            w = writerService.getById(1L);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(l, w.getPosts().getFirst().getLabels().getFirst());
    }

    @Test
    public void testNegativeWriterServiceGetById() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).getById(anyLong());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertThrows(ServiceException.class, () -> writerService.getById(1L));
    }

    @Test
    public void testPositiveWriterServiceGetByIdClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();

        try {
            Post p = new Post();
            p.setId(1L);
            p.setStatus(PostStatus.UNDER_REVIEW);
            doReturn(new Writer()).when(mockWR).getById(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w = writerService.getById(1L, ControllerClientStatus.USER);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(0, w.getPosts().size());
    }

    @Test
    public void testPositiveWriterServiceGetByIdClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Label l = new Label(1L, "Test Label");
        Writer w = new Writer();

        try {
            Post p = new Post();
            p.setId(1L);
            p.setStatus(PostStatus.ACTIVE);
            doReturn(new Writer()).when(mockWR).getById(anyLong());
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w = writerService.getById(1L, ControllerClientStatus.USER);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(l, w.getPosts().getFirst().getLabels().getFirst());
    }

    @Test
    public void testPositiveWriterServiceGetByIdClientStatusUserDeleted() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();

        try {
            Post p = new Post();
            p.setId(1L);
            p.setStatus(PostStatus.DELETED);
            doReturn(new Writer()).when(mockWR).getById(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w = writerService.getById(1L, ControllerClientStatus.USER);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(0, w.getPosts().size());
    }

    @Test
    public void testNegativeWriterServiceGetByIdClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).getById(anyLong());
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertThrows(ServiceException.class, () -> writerService.getById(1L, any(ControllerClientStatus.class)));
    }

    @Test
    public void testPositiveWriterServiceGetByIdClientStatusAdminUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Label l = new Label(1L, "Test Label");
        Writer w = new Writer();

        try {
            Post p = new Post();
            p.setId(1L);
            p.setStatus(PostStatus.UNDER_REVIEW);
            doReturn(new Writer()).when(mockWR).getById(anyLong());
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w = writerService.getById(1L, ControllerClientStatus.ADMIN);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(l, w.getPosts().getFirst().getLabels().getFirst());
    }
}
