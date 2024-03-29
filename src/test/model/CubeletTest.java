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
        center = new Cubelet(new Position(0, 0, 1), false);
        edge = new EdgeCubelet(new Position(-1, 0, 1), true);
        corner = new CornerCubelet(new Position(-1, -1, 1), true);
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
    void testCopyCubelet() {
        Cubelet center2 = new Cubelet(center);
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
        center = new Cubelet(new Position(0, 0, 1), false);
        edge = new EdgeCubelet(new Position(-1, 0, 1), false);
        corner = new CornerCubelet(new Position(-1, -1, 1), false);

        Cubelet edgeCopy = new EdgeCubelet(edge);
        edgeCopy.rotate("U", true);
        assertEquals(edge.getColorX(), edgeCopy.getColorY());
        assertEquals(edge.getColorY(), edgeCopy.getColorX());

        edgeCopy = new EdgeCubelet(edge);
        edgeCopy.rotate("L", true);
        assertEquals(edge.getColorY(), edgeCopy.getColorZ());
        assertEquals(edge.getColorZ(), edgeCopy.getColorY());

        edgeCopy = new EdgeCubelet(edge);
        edgeCopy.rotate("F", true);
        assertEquals(edge.getColorX(), edgeCopy.getColorZ());
        assertEquals(edge.getColorZ(), edgeCopy.getColorX());

        Cubelet cornerCopy = new CornerCubelet(corner);
        cornerCopy.rotate("D", false);
        assertEquals(corner.getColorX(), cornerCopy.getColorY());
        assertEquals(corner.getColorY(), cornerCopy.getColorX());

        cornerCopy = new CornerCubelet(corner);
        cornerCopy.rotate("R", false);
        assertEquals(corner.getColorY(), cornerCopy.getColorZ());
        assertEquals(corner.getColorZ(), cornerCopy.getColorY());

        cornerCopy = new CornerCubelet(corner);
        cornerCopy.rotate("B", false);
        assertEquals(corner.getColorX(), cornerCopy.getColorZ());
        assertEquals(corner.getColorZ(), cornerCopy.getColorX());
    }

    @Test
    void testEquals() {
        Cubelet center2 = new Cubelet(center);
        assertTrue(center.equals(center2));

        center2.setColorX("random");
        assertFalse(center.equals(center2));

        center2 = new Cubelet(center);
        center2.setColorY("random");
        assertFalse(center.equals(center2));

        center2 = new Cubelet(center);
        center2.setColorZ("random");
        assertFalse(center.equals(center2));

        center2 = new Cubelet(center);
        center2.setPos(new Position(1, 1, -1));
        assertFalse(center.equals(center2));

        center2 = new Cubelet(new Position(0, 0, -1), false);
        center2.setColorX(center.getColorX());
        center2.setColorY(center.getColorY());
        center2.setColorZ(center.getColorZ());
        center2.setPos(center.getPos());
        assertFalse(center.equals(center2));
    }
}