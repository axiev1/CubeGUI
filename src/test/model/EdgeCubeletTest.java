package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeCubeletTest {
    private EdgeCubelet ec;
    @BeforeEach
    void runBefore() {
        ec = new EdgeCubelet(new Position(-1, 0, 1));
    }

    @Test
    void testScrambleColors() {
        String colorX = ec.getColorX();
        String colorZ = ec.getColorZ();

        ec.scrambleColors();
        assertNull(ec.getColorY());
        assertTrue((ec.getColorX().equals(colorX) && ec.getColorZ().equals(colorZ))
                || ec.getColorX().equals(colorZ) && ec.getColorZ().equals(colorX));

        ec = new EdgeCubelet(new Position(-1, 0, 1));
        ec.setPos(new Position(0, 1, 1));
        ec.scrambleColors();
        assertNull(ec.getColorX());
        assertTrue((ec.getColorY().equals(colorX) && ec.getColorZ().equals(colorZ))
                || ec.getColorY().equals(colorZ) && ec.getColorZ().equals(colorX));

        ec = new EdgeCubelet(new Position(-1, 0, 1));
        ec.setPos(new Position(-1, -1, 0));
        ec.scrambleColors();
        assertNull(ec.getColorZ());
        assertTrue((ec.getColorX().equals(colorX) && ec.getColorY().equals(colorZ))
                || ec.getColorX().equals(colorZ) && ec.getColorY().equals(colorX));

    }

    @Test
    void testKeyColorOnFace() {
        assertEquals(1, ec.keyColorOnKeyFace());
        ec.setColorZ("Y");
        assertEquals(1, ec.keyColorOnKeyFace());
        ec.setColorZ("O");
        assertEquals(0, ec.keyColorOnKeyFace());

        ec = new EdgeCubelet(new Position(-1, -1, 0));
        assertEquals(1, ec.keyColorOnKeyFace());
        ec.setColorY("B");
        assertEquals(1, ec.keyColorOnKeyFace());
        ec.setColorY("R");
        assertEquals(0, ec.keyColorOnKeyFace());
    }
}