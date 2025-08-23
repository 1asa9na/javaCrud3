package com.example.writerservice;

import com.example.model.Writer;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import com.example.service.ServiceException;
import com.example.service.WriterService;
import com.example.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for WriterService save method.
 */

public class WriterServiceSaveTests {
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
}
