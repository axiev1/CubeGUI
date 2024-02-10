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
        cube.printCube();
        cube.rotateFace("F", true);
        cube.printCube();
        cube.rotateFace("R", true);
        cube.printCube();
        cube.rotateFace("U", true);
        cube.printCube();
    }
}