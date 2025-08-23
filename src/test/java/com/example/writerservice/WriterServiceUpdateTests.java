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
 * Test suite for WriterService update method.
 */

public class WriterServiceUpdateTests {
    @Test
    public void testPositiveWriterServiceUpdate() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Writer w2 = new Writer();
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);
        Label l = new Label();

        try {
            doReturn(w).when(mockWR).update(any(Writer.class));
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w2 = writerService.update(w);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(w, w2);
    }

    @Test
    public void testNegativeWriterServiceUpdate() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).update(any(Writer.class));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        assertThrows(ServiceException.class, () -> writerService.update(new Writer()));
    }

    @Test
    public void testPositiveWriterServiceUpdateClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Writer w2 = new Writer();
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);

        try {
            doReturn(w).when(mockWR).update(any(Writer.class));
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w2 = writerService.update(new Writer(), ControllerClientStatus.USER);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(0, w2.getPosts().size());
    }

    @Test
    public void testPositiveWriterServiceUpdateClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Writer w2 = new Writer();
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.ACTIVE);
        Label l = new Label();

        try {
            doReturn(w).when(mockWR).update(any(Writer.class));
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w2 = writerService.update(new Writer(), ControllerClientStatus.USER);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(w, w2);
    }

    @Test
    public void testPositiveWriterServiceUpdateClientStatusUserDeleted() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Writer w2 = new Writer();
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.DELETED);

        try {
            doReturn(w).when(mockWR).update(any(Writer.class));
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w2 = writerService.update(new Writer(), ControllerClientStatus.USER);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(0, w2.getPosts().size());
    }

    @Test
    public void testPositiveWriterServiceUpdateClientStatusAdminUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Writer w2 = new Writer();
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);
        Label l = new Label();

        try {
            doReturn(w).when(mockWR).update(any(Writer.class));
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            w2 = writerService.update(new Writer(), ControllerClientStatus.ADMIN);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(w, w2);
    }

    @Test
    public void testNegativeWriterServiceUpdateClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).update(any(Writer.class));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        assertThrows(ServiceException.class, () -> writerService.update(new Writer(), ControllerClientStatus.ADMIN));
    }
}
