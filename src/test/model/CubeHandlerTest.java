package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CubeHandlerTest {
    private CubeHandler ch1;
    private CubeHandler ch2;

    @BeforeEach
    void runBefore() {
        ch1 = new CubeHandler(false);
        ch2 = new CubeHandler(true);
    }

    @Test
    void testConstructor() {
        ch1.getCube().equals(new Cube());
        assertEquals(0, ch1.getSavedCubes().size());
        assertFalse(ch1.isModelExists());
        assertTrue(ch2.isModelExists());
    }

    @Test
    void testParseScramble() {
        Cube cube = new Cube();
        cube.rotateFace("D", true, 1);
        cube.rotateFace("F", true, 2);
        cube.rotateFace("U", true, 1);
        cube.rotateFace("B", true, 1);
        cube.rotateFace("R", true, 2);
        cube.rotateFace("F", false, 1);
        cube.rotateFace("B", false, 1);
        cube.rotateFace("D", true, 2);
        cube.rotateFace("F", true, 2);
        cube.rotateFace("D", true, 2);
        cube.rotateFace("R", true, 2);
        cube.rotateFace("B", true, 1);
        cube.rotateFace("R", true, 1);
        cube.rotateFace("D", false, 1);
        cube.rotateFace("L", false, 1);
        cube.rotateFace("B", true, 1);
        cube.rotateFace("L", true, 1);
        cube.rotateFace("R", false, 1);
        cube.rotateFace("F", false, 1);
        cube.rotateFace("L", false, 1);
        cube.rotateFace("R", true, 2);
        cube.rotateFace("F", true, 2);
        cube.rotateFace("D", false, 1);
        cube.rotateFace("L", true, 1);
        cube.rotateFace("D", true, 1);

        ch1.parseScramble("D F2 U B R2 F' B' D2 F2 D2 R2 B R D' L' B L R' F' L' R2 F2 D' L D");

        assertTrue(cube.equals(ch1.getCube()));
    }

    @Test
    void testSaveAndLoadCube() {
        Cube c1 = new Cube(ch1.getCube());
        ch1.saveCube();
        ch1.scramble();

        Cube c2 = new Cube(ch1.getCube());
        ch1.saveCube();
        ch1.scramble();

        Cube c3 = new Cube(ch1.getCube());
        ch1.saveCube();
        ch1.scramble();

        ch1.loadCube(1);
        assertTrue(c1.equals(ch1.getCube()));

        ch1.loadCube(2);
        assertTrue(c2.equals(ch1.getCube()));

        ch1.loadCube(3);
        assertTrue(c3.equals(ch1.getCube()));
    }

    @Test
    void testScramble() {
        ch1.scramble();
        assertFalse(new Cube().equals(ch1.getCube()));
    }

    @Test
    void testReset() {
        ch1.scramble();
        ch1.reset();
        assertTrue(new Cube().equals(ch1.getCube()));
    }
}
