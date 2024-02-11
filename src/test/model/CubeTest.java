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
        cube.getCenterCubelets();
    }

    @Test
    void printCube() {
        cube.printCube();
    }

    @Test
    void testRotateFace() {
        cube.rotateFace("U", true, 2);
        cube.rotateFace("D", false,3);
        cube.rotateFace("R", true, 5);
        cube.rotateFace("L", true, 2);
        cube.rotateFace("F", true, 1);
        cube.rotateFace("B", false, 2);

        System.out.println(cube.isValid());
        cube.printCube();
    }

    @Test
    void testScramble() {
        double valid = 0;
        for (int i = 0; i<10; i++) {
            cube = new Cube();
            cube.scramble();
            if (cube.isValid()) {
                valid++;
                cube.printCube();
            }
        }

        System.out.println(valid / 10);
    }
}