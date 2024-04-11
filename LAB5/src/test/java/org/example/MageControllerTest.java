package org.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class MageControllerTest {
    private MageController mageController;
    private MageRepository mageRepository = Mockito.mock(MageRepository.class);

    @Before
    public void setUp() {
        mageController = new MageController(mageRepository);
    }

    @Test
    public void testFind_exists() {
        when(mageRepository.find("A")).thenReturn(Optional.of(new Mage("A", 1)));
        var result = mageController.findMage("A");
        assertEquals("A 1", result);
    }

    @Test
    public void testFind_doest_exist() {
        when(mageRepository.find("D")).thenReturn(Optional.empty());
        var result = mageController.findMage("D");
        assertEquals("not found", result);
    }
    @Test
    public void testDelete_doest_exist() {
        doThrow(new IllegalArgumentException()).when(mageRepository).delete("D");
        var result = mageController.deleteMage("D");
        assertEquals("not found", result);
    }
    @Test
    public void testDelete_exists() {
        doNothing().when(mageRepository).delete("A");
        var result = mageController.deleteMage("A");
        assertEquals("done", result);
    }
    @Test
    public void testSave_exists() {
        doThrow(new IllegalArgumentException()).when(mageRepository).save(new Mage("A", 1));
        var result = mageController.saveMage("A", "1");
        assertEquals("bad request", result);
    }
    @Test
    public void testSave_doest_exist() {
        doNothing().when(mageRepository).save(new Mage("D", 4));
        var result = mageController.saveMage("D", "4");
        assertEquals("done", result);
    }
}
