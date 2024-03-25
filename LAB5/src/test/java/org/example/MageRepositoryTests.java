package org.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class MageRepositoryTests {
    private MageRepository mageRepository;

    @Before
    public void setUp() {
        Collection<Mage> mages = new ArrayList<>();
        mages.add(new Mage("A", 1));
        mages.add(new Mage("B", 2));
        mages.add(new Mage("C", 3));
        mageRepository = new MageRepository(mages);
    }

    @Test
    public void testFind_exists() {
        var mage = mageRepository.find("A");
        assertTrue(mage.isPresent());
        assertEquals("A", mage.get().getName());
        assertEquals(1, mage.get().getLevel());
    }
    @Test
    public void testFind_doest_exist() {
        var mage = mageRepository.find("D");
        assertFalse(mage.isPresent());
    }
    @Test
    public void testDelete_doest_exist() {
        try {
            mageRepository.delete("D");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid mage name", e.getMessage());
        }
    }
    @Test
    public void testDelete_exists() {
        mageRepository.delete("A");
        var mage = mageRepository.find("A");
        assertFalse(mage.isPresent());
    }
    @Test
    public void testSave_exists() {
        try {
            mageRepository.save(new Mage("A", 1));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Mage already exists", e.getMessage());
        }
    }
    @Test
    public void testSave_doest_exist() {
        mageRepository.save(new Mage("D", 4));
        var mage = mageRepository.find("D");
        assertTrue(mage.isPresent());
        assertEquals("D", mage.get().getName());
        assertEquals(4, mage.get().getLevel());
    }
}
