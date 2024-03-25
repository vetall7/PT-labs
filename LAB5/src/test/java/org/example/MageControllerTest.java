package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class MageControllerTest {
    private MageController mageController;
    private MageRepository mageRepository;

    @Before
    public void setUp() {
        Collection<Mage> mages = new ArrayList<>();
        mages.add(new Mage("A", 1));
        mages.add(new Mage("B", 2));
        mages.add(new Mage("C", 3));
        mageRepository = new MageRepository(mages);
        mageController = new MageController(mageRepository);
    }

    @Test
    public void testFind_exists() {
        var result = mageController.findMage("A");
        assertEquals("A 1", result);
    }

    @Test
    public void testFind_doest_exist() {
        var result = mageController.findMage("D");
        assertEquals("not found", result);
    }
    @Test
    public void testDelete_doest_exist() {
        var result = mageController.deleteMage("D");
        assertEquals("not found", result);
    }
    @Test
    public void testDelete_exists() {
        var result = mageController.deleteMage("A");
        assertEquals("done", result);
    }
    @Test
    public void testSave_exists() {
        var result = mageController.saveMage("A", "1");
        assertEquals("bad request", result);
    }
    @Test
    public void testSave_doest_exist() {
        var result = mageController.saveMage("D", "4");
        assertEquals("done", result);
    }
}
