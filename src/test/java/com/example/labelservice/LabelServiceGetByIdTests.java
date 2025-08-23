package com.example.labelservice;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.repository.RepositoryException;
import com.example.service.LabelService;
import com.example.service.ServiceException;
import com.example.service.impl.LabelServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for LabelService getById method.
 */

public class LabelServiceGetByIdTests {
    @Test
    public void testPositiveLabelServiceGetById() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        Label l = new Label();

        doReturn(l).when(mockLR).getById(anyLong());

        assertEquals(l, labelService.getById(1L));
    }

    @Test
    public void testNegativeLabelServiceGetById() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        doThrow(new RepositoryException()).when(mockLR).getById(anyLong());

        assertThrows(ServiceException.class, () -> labelService.getById(1L));
    }
}
