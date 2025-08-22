package com.example;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test cases for WriterService.
 */

public class WriterServiceTests {
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

    @Test
    public void testPositiveWriterServiceSave() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Writer w = new Writer();
        Writer w2 = new Writer();

        try {
            doReturn(w).when(mockWR).save(any(Writer.class));
            w2 = writerService.save(new Writer());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        assertEquals(w, w2);
    }

    @Test
    public void testNegativeWriterServiceSave() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        try {
            doThrow(new RepositoryException()).when(mockWR).save(any(Writer.class));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        assertThrows(ServiceException.class, () -> writerService.save(new Writer()));
    }

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

    @Test
    public void testPositiveWriteServiceDelete() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        doNothing().when(mockWR).deleteById(anyLong());
        writerService.delete(1L);
        verify(mockWR, times(1)).deleteById(1L);
    }

    @Test
    public void testNegativeWriteServiceDelete() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        doThrow(new RepositoryException()).when(mockWR).deleteById(anyLong());
        assertThrows(ServiceException.class, () -> writerService.delete(1L));
    }
}
