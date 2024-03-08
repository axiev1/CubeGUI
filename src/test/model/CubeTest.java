package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CubeTest {
    private Cube cube;
    String[][] faceU;
    String[][] faceL;
    String[][] faceF;
    String[][] faceR;
    String[][] faceB;
    String[][] faceD;

    @BeforeEach
    void runBefore() {
        cube = new Cube();
    }

    @Test
    void testCube() {
        faceU = cube.getFace("U").to2DStringArray();
        faceL = cube.getFace("L").to2DStringArray();
        faceF = cube.getFace("F").to2DStringArray();
        faceR = cube.getFace("R").to2DStringArray();
        faceB = cube.getFace("B").to2DStringArray();
        faceD = cube.getFace("D").to2DStringArray();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals("W", faceU[y][x]);
                assertEquals("O", faceL[y][x]);
                assertEquals("G", faceF[y][x]);
                assertEquals("R", faceR[y][x]);
                assertEquals("B", faceB[y][x]);
                assertEquals("Y", faceD[y][x]);
            }
        }
    }

    @Test
    void testCopyCube() {
        cube.scramble();
        faceU = cube.getFace("U").to2DStringArray();
        faceL = cube.getFace("L").to2DStringArray();
        faceF = cube.getFace("F").to2DStringArray();
        faceR = cube.getFace("R").to2DStringArray();
        faceB = cube.getFace("B").to2DStringArray();
        faceD = cube.getFace("D").to2DStringArray();

        Cube copy = new Cube(cube);

        String[][] faceU2 = copy.getFace("U").to2DStringArray();
        String[][] faceL2 = copy.getFace("L").to2DStringArray();
        String[][] faceF2 = copy.getFace("F").to2DStringArray();
        String[][] faceR2 = copy.getFace("R").to2DStringArray();
        String[][] faceB2 = copy.getFace("B").to2DStringArray();
        String[][] faceD2 = copy.getFace("D").to2DStringArray();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(faceU[y][x], faceU2[y][x]);
                assertEquals(faceL[y][x], faceL2[y][x]);
                assertEquals(faceF[y][x], faceF2[y][x]);
                assertEquals(faceR[y][x], faceR2[y][x]);
                assertEquals(faceB[y][x], faceB2[y][x]);
                assertEquals(faceD[y][x], faceD2[y][x]);
            }
        }
    }

    @Test
    void testRotateFace() {
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

        assertTrue(cube.isValid());
        faceU = cube.getFace("U").to2DStringArray();
        faceL = cube.getFace("L").to2DStringArray();
        faceF = cube.getFace("F").to2DStringArray();
        faceR = cube.getFace("R").to2DStringArray();
        faceB = cube.getFace("B").to2DStringArray();
        faceD = cube.getFace("D").to2DStringArray();

        String[][] expectedU = {{"W", "B", "B"}, {"R", "W", "Y"}, {"O", "W", "Y"}};
        String[][] expectedL = {{"G", "W", "Y"}, {"B", "O", "O"}, {"R", "W", "G"}};
        String[][] expectedF = {{"G", "G", "O"}, {"G", "G", "R"}, {"R", "B", "G"}};
        String[][] expectedR = {{"B", "G", "R"}, {"G", "R", "R"}, {"W", "Y", "W"}};
        String[][] expectedB = {{"Y", "W", "O"}, {"Y", "B", "R"}, {"O", "Y", "B"}};
        String[][] expectedD = {{"Y", "O", "R"}, {"O", "Y", "B"}, {"W", "O", "B"}};

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(expectedU[y][x], faceU[y][x]);
                assertEquals(expectedL[y][x], faceL[y][x]);
                assertEquals(expectedF[y][x], faceF[y][x]);
                assertEquals(expectedR[y][x], faceR[y][x]);
                assertEquals(expectedB[y][x], faceB[y][x]);
                assertEquals(expectedD[y][x], faceD[y][x]);
            }
        }
    }

    @Test
    void testScramble() {
        for (int i = 0; i < 3; i++) {
            Cube copy = new Cube(cube);
            cube.scramble();

            String[][] faceU2 = copy.getFace("U").to2DStringArray();
            String[][] faceL2 = copy.getFace("L").to2DStringArray();
            String[][] faceF2 = copy.getFace("F").to2DStringArray();
            String[][] faceR2 = copy.getFace("R").to2DStringArray();
            String[][] faceB2 = copy.getFace("B").to2DStringArray();
            String[][] faceD2 = copy.getFace("D").to2DStringArray();

            faceU = cube.getFace("U").to2DStringArray();
            faceL = cube.getFace("L").to2DStringArray();
            faceF = cube.getFace("F").to2DStringArray();
            faceR = cube.getFace("R").to2DStringArray();
            faceB = cube.getFace("B").to2DStringArray();
            faceD = cube.getFace("D").to2DStringArray();

            boolean dif = false;
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (!faceU2[y][x].equals(faceU[y][x]) || !faceL2[y][x].equals(faceL[y][x])
                            || !faceF2[y][x].equals(faceF[y][x]) || !faceR2[y][x].equals(faceR[y][x])
                            || !faceB2[y][x].equals(faceB[y][x]) || !faceD2[y][x].equals(faceD[y][x])) {
                        dif = true;
                        break;
                    }
                }
            }

            assertTrue(dif);
        }
    }

    @Test
    void testIsValid() {
        assertTrue(cube.isValid());

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
        assertTrue(cube.isValid());

        cube = new Cube();
        CornerCubelet cc = cube.getCornerCubelets().get(0);
        String[] scramble = {cc.getColorX(), cc.getColorY(), cc.getColorZ()};
        cc.setColorX(scramble[1]);
        cc.setColorY(scramble[2]);
        cc.setColorZ(scramble[0]);
        assertFalse(cube.isValid());

        cube = new Cube();
        EdgeCubelet ec = cube.getEdgeCubelets().get(0);
        String swap = ec.getColorX();
        ec.setColorX(ec.getColorY());
        ec.setColorY(swap);
        assertFalse(cube.isValid());

        cube = new Cube();
        CornerCubelet c1 = cube.getCornerCubelets().get(1); // At pos -1, -1, 1
        CornerCubelet c2 = cube.getCornerCubelets().get(7); // At pos 1, 1, 1
        c1.setPos(new Position(1, 1, 1));
        c2.setPos(new Position(-1, -1, 1));
        assertFalse(cube.isValid());
    }

    @Test
    void testGetFace() {
        cube.rotateFace("U", true, 2);
        cube.rotateFace("D", true, 2);
        cube.rotateFace("R", true, 2);
        cube.rotateFace("L", true, 2);
        cube.rotateFace("F", true, 2);
        cube.rotateFace("B", true, 2);

        assertTrue(cube.isValid());
        faceU = cube.getFace("U").to2DStringArray();
        faceL = cube.getFace("L").to2DStringArray();
        faceF = cube.getFace("F").to2DStringArray();
        faceR = cube.getFace("R").to2DStringArray();
        faceB = cube.getFace("B").to2DStringArray();
        faceD = cube.getFace("D").to2DStringArray();

        String[][] expectedU = {{"W", "Y", "W"}, {"Y", "W", "Y"}, {"W", "Y", "W"}};
        String[][] expectedL = {{"O", "R", "O"}, {"R", "O", "R"}, {"O", "R", "O"}};
        String[][] expectedF = {{"G", "B", "G"}, {"B", "G", "B"}, {"G", "B", "G"}};
        String[][] expectedR = {{"R", "O", "R"}, {"O", "R", "O"}, {"R", "O", "R"}};
        String[][] expectedB = {{"B", "G", "B"}, {"G", "B", "G"}, {"B", "G", "B"}};
        String[][] expectedD = {{"Y", "W", "Y"}, {"W", "Y", "W"}, {"Y", "W", "Y"}};

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(expectedU[y][x], faceU[y][x]);
                assertEquals(expectedL[y][x], faceL[y][x]);
                assertEquals(expectedF[y][x], faceF[y][x]);
                assertEquals(expectedR[y][x], faceR[y][x]);
                assertEquals(expectedB[y][x], faceB[y][x]);
                assertEquals(expectedD[y][x], faceD[y][x]);
            }
        }
    }

    @Test
    void testEquals() {
        Cube cube2 = new Cube();
        assertTrue(cube.equals(cube2));

        cube2.getCenterCubelets().set(0, new Cubelet(new Position(0, 0, -1)));
        assertFalse(cube.equals(cube2));

        cube2 = new Cube(cube);
        cube2.getEdgeCubelets().set(0, new EdgeCubelet(new Position(-1, 0, -1)));
        assertFalse(cube.equals(cube2));

        cube2 = new Cube(cube);
        cube2.getCornerCubelets().set(0, new CornerCubelet(new Position(-1, 1, 1)));
        assertFalse(cube.equals(cube2));

        cube2 = new Cube(cube);
        cube2.getCornerCubelets().get(0).setColorZ("incorrect");
        assertFalse(cube.equals(cube2));

        cube.scramble();
        cube2 = new Cube(cube);
        assertTrue(cube.equals(cube2));
    }
}