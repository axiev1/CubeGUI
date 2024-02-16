package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position pos1;
    Position pos2;

    @BeforeEach
    void runBefore() {
        pos1 = new Position(-1, -1, -1);
        pos2 = new Position(1, 1, 1);
    }

    @Test
    void testIsInFace() {
        assertTrue(pos1.isInFace("L"));
        assertTrue(pos1.isInFace("F"));
        assertTrue(pos1.isInFace("D"));
        assertFalse(pos1.isInFace("R"));
        assertFalse(pos1.isInFace("B"));
        assertFalse(pos1.isInFace("U"));

        assertTrue(pos2.isInFace("R"));
        assertTrue(pos2.isInFace("B"));
        assertTrue(pos2.isInFace("U"));
        assertFalse(pos2.isInFace("L"));
        assertFalse(pos2.isInFace("F"));
        assertFalse(pos2.isInFace("D"));
    }

    @Test
    void testEquals() {
        assertTrue(pos1.equals(new Position(-1, -1, -1)));
        assertFalse(pos1.equals(new Position(-1, -1, 0)));
        assertFalse(pos1.equals(new Position(-1, 1, 0)));
        assertFalse(pos1.equals(new Position(-1, 1, -1)));
        assertFalse(pos1.equals(new Position(0, -1, -1)));
        assertFalse(pos1.equals(pos2));

    }
}