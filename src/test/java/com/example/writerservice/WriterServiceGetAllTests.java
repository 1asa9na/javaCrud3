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
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for WriterService getAll method.
 */

public class WriterServiceGetAllTests {
    @Test
    public void testPositiveWriterServiceGetAll() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Label l = new Label(1L, "Test Label");
        List<Writer> writers = new ArrayList<>();
        Writer w = new Writer();
        w.setId(1L);
        Post p = new Post();
        p.setId(1L);
        try {
            doReturn(new ArrayList<>(Arrays.asList(w))).when(mockWR).getAll();
            doReturn(new ArrayList<>(Arrays.asList(l))).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            writers = writerService.getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(l, writers.getFirst().getPosts().getFirst().getLabels().getFirst());
    }

    @Test
    public void testNegativeWriterServiceGetAll() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertThrows(ServiceException.class, () -> writerService.getAll());
    }

    @Test
    public void testPositiveWriterServiceGetAllClientStatusUserUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);
        List<Writer> writers = new ArrayList<>();

        try {
            doReturn(new ArrayList<>(Arrays.asList(w))).when(mockWR).getAll();
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            writers = writerService.getAll(ControllerClientStatus.USER);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(0, writers.getFirst().getPosts().size());
    }

    @Test
    public void testPositiveWriterServiceGetAllClientStatusUserActive() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.ACTIVE);
        List<Writer> writers = new ArrayList<>();
        Label l = new Label();

        try {
            doReturn(new ArrayList<>(Arrays.asList(w))).when(mockWR).getAll();
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            writers = writerService.getAll(ControllerClientStatus.USER);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(l, writers.getFirst().getPosts().getFirst().getLabels().getFirst());
    }

    @Test
    public void testPositiveWriterServiceGetAllClientStatusUserDeleted() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.DELETED);
        List<Writer> writers = new ArrayList<>();

        try {
            doReturn(new ArrayList<>(Arrays.asList(w))).when(mockWR).getAll();
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            writers = writerService.getAll(ControllerClientStatus.USER);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(0, writers.getFirst().getPosts().size());
    }

    @Test
    public void testNegativeWriterServiceGetAllClientStatusAdmin() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertThrows(ServiceException.class, () -> writerService.getAll(ControllerClientStatus.ADMIN));
    }

    @Test
    public void testNegativeWriterServiceGetAllClientStatusAdminUnderReview() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        w.setId(1L);
        Post p = new Post();
        p.setId(1L);
        p.setStatus(PostStatus.UNDER_REVIEW);
        List<Writer> writers = new ArrayList<>();
        Label l = new Label();

        try {
            doReturn(new ArrayList<>(Arrays.asList(w))).when(mockWR).getAll();
            doReturn(Arrays.asList(l)).when(mockPR).getAllLabelsByPostId(anyLong());
            doReturn(new ArrayList<>(Arrays.asList(p))).when(mockWR).getAllPostsByWriterId(anyLong());
            writers = writerService.getAll(ControllerClientStatus.ADMIN);
        } catch (RepositoryException | ServiceException e) {
            e.printStackTrace();
        }
        assertEquals(l, writers.getFirst().getPosts().getFirst().getLabels().getFirst());
    }
}
