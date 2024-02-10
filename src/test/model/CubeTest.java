package model;

import org.junit.jupiter.api.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CubeTest {
    private Cube cube;
    @BeforeEach
    void runBefore() {
        cube = new Cube();

    }

    @Test
    void testCube() {

    }

    @Test
    void printCube() {
        cube.printCube();
    }

    @Test
    void testRotateFace() {
        cube.rotateFace("U", true, 2);
        cube.rotateFace("D", true,2);
        cube.rotateFace("R", true, 2);
        cube.rotateFace("L", true, 2);
        cube.rotateFace("F", true, 2);
        cube.rotateFace("B", true, 2);

        cube.printCube();
    }

    @Test
    void testScramble() {
        cube.scramble();

        cube.printCube();
    }
}