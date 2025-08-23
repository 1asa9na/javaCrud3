package com.example.labelservice;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.repository.RepositoryException;
import com.example.service.LabelService;
import com.example.service.ServiceException;
import com.example.service.impl.LabelServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for LabelService getAll method.
 */

public class LabelServiceGetAllTests {
    @Test
    public void testPositiveLabelServiceGetAll() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        Label l = new Label();

        doReturn(Arrays.asList(l)).when(mockLR).getAll();

        assertEquals(l, labelService.getAll().getFirst());
    }

    @Test
    public void testNegativeLabelServiceGetAll() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        doThrow(new RepositoryException()).when(mockLR).getAll();

        assertThrows(ServiceException.class, () -> labelService.getAll());
    }
}
