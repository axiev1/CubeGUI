package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CubeletTest {
    private Cubelet cubelet;
    @BeforeEach
    void setUp() {
        cubelet = new Cubelet(new Position(-1, 0, 1));
    }

    @Test
    void testCubelet() {
        assertEquals("O", cubelet.getColorX());
        assertNull(cubelet.getColorY());
        assertEquals("W", cubelet.getColorZ());
    }

    @Test
    void isInFace() {
    }
}