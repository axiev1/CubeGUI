package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {
    private Face face1;
    private Face face2;

    @BeforeEach
    void runBefore() {
        face1 = new Face("L", new Cubelet(new Position(-1, 0, 0)));
        face2 = new Face("U", new Cubelet(new Position(0, 0, 1)));

        face2.addCorner(new CornerCubelet(new Position(-1, -1, 1)));
        face2.addCorner(new CornerCubelet(new Position(1, -1, 1)));
        face2.addCorner(new CornerCubelet(new Position(-1, 1, 1)));
        face2.addCorner(new CornerCubelet(new Position(1, 1, 1)));


        face2.addEdge(new EdgeCubelet(new Position(-1, 0, 1)));
        face2.addEdge(new EdgeCubelet(new Position(1, 0, 1)));
        face2.addEdge(new EdgeCubelet(new Position(0, 1, 1)));
        face2.addEdge(new EdgeCubelet(new Position(0, -1, 1)));
    }

    @Test
    void testFace() {
        assertEquals("L", face1.getOrientation());
        assertTrue(face1.getCenter().getPos().equals(new Position(-1, 0, 0)));
        assertEquals(0, face1.getEdges().size());
        assertEquals(0, face1.getCorners().size());
    }

    @Test
    void addEdge() {
        face1.addEdge(new EdgeCubelet(new Position(-1, 0, 1)));
        face1.addEdge(new EdgeCubelet(new Position(-1, 1, 0)));
        face1.addEdge(new EdgeCubelet(new Position(-1, -1, 0)));
        face1.addEdge(new EdgeCubelet(new Position(-1, 0, -1)));

        assertTrue((new Position(-1, 0, 1)).equals(face1.getEdges().get(0).getPos()));
        assertTrue((new Position(-1, 1, 0)).equals(face1.getEdges().get(1).getPos()));
        assertTrue((new Position(-1, -1, 0)).equals(face1.getEdges().get(2).getPos()));
        assertTrue((new Position(-1, 0, -1)).equals(face1.getEdges().get(3).getPos()));
    }

    @Test
    void addCorner() {
        face1.addCorner(new CornerCubelet(new Position(-1, -1, 1)));
        face1.addCorner(new CornerCubelet(new Position(-1, -1, -1)));
        face1.addCorner(new CornerCubelet(new Position(-1, 1, -1)));
        face1.addCorner(new CornerCubelet(new Position(-1, 1, 1)));

        assertTrue((new Position(-1, -1, 1)).equals(face1.getCorners().get(0).getPos()));
        assertTrue((new Position(-1, -1, -1)).equals(face1.getCorners().get(1).getPos()));
        assertTrue((new Position(-1, 1, -1)).equals(face1.getCorners().get(2).getPos()));
        assertTrue((new Position(-1, 1, 1)).equals(face1.getCorners().get(3).getPos()));
    }

    @Test
    void testTo2DStringArray() {
        String[][] expected = {{"W", "W", "W"}, {"W", "W", "W"}, {"W", "W", "W"}};
        assertArrayEquals(expected, face2.to2DStringArray());
    }

    @Test
    void testGetOrderedCubelets() {
        Cubelet[][] orderedFace = face2.getOrderedCubelets();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertTrue(orderedFace[y][x].getPos().equals(new Position(x-1, -y+1, 1)));
            }
        }
    }

    @Test
    void testRotateFaceClockwise() {
        Cubelet[][] orderedCubelets = face2.getOrderedCubelets();
        Position[][] originalPos = new Position[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                originalPos[y][x] = new Position(orderedCubelets[y][x].getPos());
            }
        }

        face2.rotateFaceClockwise();
        assertTrue(orderedCubelets[0][0].getPos().equals(originalPos[0][2]));
        assertTrue(orderedCubelets[0][2].getPos().equals(originalPos[2][2]));
        assertTrue(orderedCubelets[2][2].getPos().equals(originalPos[2][0]));
        assertTrue(orderedCubelets[2][0].getPos().equals(originalPos[0][0]));

        assertTrue(orderedCubelets[0][1].getPos().equals(originalPos[1][2]));
        assertTrue(orderedCubelets[1][2].getPos().equals(originalPos[2][1]));
        assertTrue(orderedCubelets[2][1].getPos().equals(originalPos[1][0]));
        assertTrue(orderedCubelets[1][0].getPos().equals(originalPos[0][1]));
    }

    @Test
    void testRotateFaceCounterClockwise() {
        Cubelet[][] orderedCubelets = face2.getOrderedCubelets();
        Position[][] originalPos = new Position[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                originalPos[y][x] = new Position(orderedCubelets[y][x].getPos());
            }
        }

        face2.rotateFaceCounterClockwise();
        assertTrue(orderedCubelets[0][0].getPos().equals(originalPos[2][0]));
        assertTrue(orderedCubelets[2][0].getPos().equals(originalPos[2][2]));
        assertTrue(orderedCubelets[2][2].getPos().equals(originalPos[0][2]));
        assertTrue(orderedCubelets[0][2].getPos().equals(originalPos[0][0]));

        assertTrue(orderedCubelets[0][1].getPos().equals(originalPos[1][0]));
        assertTrue(orderedCubelets[1][0].getPos().equals(originalPos[2][1]));
        assertTrue(orderedCubelets[2][1].getPos().equals(originalPos[1][2]));
        assertTrue(orderedCubelets[1][2].getPos().equals(originalPos[0][1]));
    }
}