package com.example.labelservice;

import com.example.repository.LabelRepository;
import com.example.repository.RepositoryException;
import com.example.service.LabelService;
import com.example.service.ServiceException;
import com.example.service.impl.LabelServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test suite for LabelService delete method.
 */

public class LabelServiceDeleteTests {
    @Test
    public void testPositiveLabelServiceDelete() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        doNothing().when(mockLR).deleteById(anyLong());
        labelService.delete(1L);

        verify(mockLR, times(1)).deleteById(1L);
    }

    @Test
    public void testNegativeLabelServiceDelete() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        doThrow(new RepositoryException()).when(mockLR).deleteById(anyLong());

        assertThrows(ServiceException.class, () -> labelService.delete(anyLong()));
    }
}
