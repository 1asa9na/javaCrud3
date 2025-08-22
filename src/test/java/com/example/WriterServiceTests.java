package com.example;

import com.example.model.Label;
import com.example.model.Post;
import com.example.model.Writer;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import com.example.service.ServiceException;
import com.example.service.WriterService;
import com.example.service.impl.WriterServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Test cases for WriterService.
 */

public class WriterServiceTests {
    @Test
    public void testPositiveWriterServiceGetById() {
        PostRepository mockPR = mock(PostRepository.class);
        WriterRepository mockWR = mock(WriterRepository.class);
        WriterService writerService = new WriterServiceImpl(mockWR, mockPR);

        Label l = new Label(1L, "Test Label");
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
        assertEquals(w.getPosts().get(0).getLabels().get(0).getName(), "Test Label");
    }
}
