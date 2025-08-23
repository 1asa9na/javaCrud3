package com.example.writerservice;

import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import com.example.service.ServiceException;
import com.example.service.WriterService;
import com.example.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test suite for WriterService delete method.
 */

public class WriterServiceDeleteTests {
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
