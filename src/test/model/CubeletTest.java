package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CubeletTest {
    private Cubelet center;
    private Cubelet edge;
    private Cubelet corner;
    @BeforeEach
    void setUp() {
        center = new CenterCubelet(new Position(0, 0, 1));
        edge = new EdgeCubelet(new Position(-1, 0, 1));
        corner = new CornerCubelet(new Position(-1, -1, 1));
    }

    @Test
    void testCubelet() {
        assertNull(center.getColorX());
        assertNull(center.getColorY());
        assertEquals("W", center.getColorZ());

        assertEquals("O", edge.getColorX());
        assertNull(edge.getColorY());
        assertEquals("W", edge.getColorZ());

        assertEquals("O", corner.getColorX());
        assertEquals("G", corner.getColorY());
        assertEquals("W", corner.getColorZ());
    }

    @Test
    void testCopyCube() {
        Cubelet center2 = new CenterCubelet(center);
        assertTrue(center.getPos().equals(center2.getPos()));
        assertTrue(center.getTargetPos().equals(center2.getTargetPos()));
        assertEquals(center.getColorX(), center2.getColorX());
        assertEquals(center.getColorY(), center2.getColorY());
        assertEquals(center.getColorZ(), center2.getColorZ());

    }

    @Test
    void testIsInFace() {
        assertTrue(center.isInFace("U"));
        assertFalse(center.isInFace("L"));
        assertFalse(center.isInFace("F"));
        assertFalse(center.isInFace("R"));
        assertFalse(center.isInFace("B"));
        assertFalse(center.isInFace("D"));

        assertTrue(edge.isInFace("U"));
        assertTrue(edge.isInFace("L"));
        assertFalse(edge.isInFace("F"));
        assertFalse(edge.isInFace("R"));
        assertFalse(edge.isInFace("B"));
        assertFalse(edge.isInFace("D"));

        assertTrue(corner.isInFace("U"));
        assertTrue(corner.isInFace("L"));
        assertTrue(corner.isInFace("F"));
        assertFalse(corner.isInFace("R"));
        assertFalse(corner.isInFace("B"));
        assertFalse(corner.isInFace("D"));
    }

    @Test
    void testRotate() {
        Cubelet edgeCopy = new EdgeCubelet(edge);
        edgeCopy.rotate("U");
        assertEquals(edge.getColorX(), edgeCopy.getColorY());
        assertEquals(edge.getColorY(), edgeCopy.getColorX());

        edgeCopy = new EdgeCubelet(edge);
        edgeCopy.rotate("L");
        assertEquals(edge.getColorY(), edgeCopy.getColorZ());
        assertEquals(edge.getColorZ(), edgeCopy.getColorY());

        edgeCopy = new EdgeCubelet(edge);
        edgeCopy.rotate("F");
        assertEquals(edge.getColorX(), edgeCopy.getColorZ());
        assertEquals(edge.getColorZ(), edgeCopy.getColorX());

        Cubelet cornerCopy = new CornerCubelet(corner);
        cornerCopy.rotate("D");
        assertEquals(corner.getColorX(), cornerCopy.getColorY());
        assertEquals(corner.getColorY(), cornerCopy.getColorX());

        cornerCopy = new CornerCubelet(corner);
        cornerCopy.rotate("R");
        assertEquals(corner.getColorY(), cornerCopy.getColorZ());
        assertEquals(corner.getColorZ(), cornerCopy.getColorY());

        cornerCopy = new CornerCubelet(corner);
        cornerCopy.rotate("B");
        assertEquals(corner.getColorX(), cornerCopy.getColorZ());
        assertEquals(corner.getColorZ(), cornerCopy.getColorX());
    }
}