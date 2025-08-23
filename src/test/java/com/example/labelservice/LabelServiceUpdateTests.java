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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Test suite for LabelService update method.
 */

public class LabelServiceUpdateTests {
    @Test
    public void testPositiveLabelServiceUpdate() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        Label l = new Label();

        doReturn(l).when(mockLR).update(any(Label.class));

        assertEquals(l, labelService.update(new Label()));
    }

    @Test
    public void testNegativeLabelServiceUpdate() {
        LabelRepository mockLR = mock(LabelRepository.class);
        LabelService labelService = new LabelServiceImpl(mockLR);

        doThrow(new RepositoryException()).when(mockLR).update(any(Label.class));

        assertThrows(ServiceException.class, () -> labelService.update(new Label()));
    }
}
