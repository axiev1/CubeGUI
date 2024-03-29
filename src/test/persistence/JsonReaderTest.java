package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import ui.CubeHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

// adapted from edx
class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CubeHandler ch = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderUnchangedCubeHandler() {
        JsonReader reader = new JsonReader("./data/testReaderDefault.json");
        try {
            CubeHandler ch = reader.read();
            assertTrue(new Cube().equals(ch.getCube()));
            assertEquals(0, ch.getSavedCubes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCubeHandler.json");
        try {
            CubeHandler ch = reader.read();

            CubeHandler ch2 = new CubeHandler(false);
            ch2.parseScramble("D2 U B2 U2 R2 D2 U2 R' L F B R' B2 U' D' F U B' F2 D' U2 R2 F2 B2 D");
            ch2.saveCube();
            ch2.reset();
            ch2.parseScramble("B R2 F2 R L' B D' U B2 D' L' R' F' B2 R2 F' D2 F B U D F D2 U2 R");
            ch2.saveCube();
            ch2.reset();
            ch2.parseScramble("L R' D B' R B R2 B L2 R2 B L2 B U2 D' B' D2 B' F L' B2 R' D R F");

            assertTrue(ch2.getCube().equals(ch.getCube()));
            assertTrue(ch2.getSavedCubes().get(0).equals(ch.getSavedCubes().get(0)));
            assertTrue(ch2.getSavedCubes().get(1).equals(ch.getSavedCubes().get(1)));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}