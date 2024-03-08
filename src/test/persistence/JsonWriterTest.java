package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import ui.CubeHandler;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            CubeHandler ch = new CubeHandler();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            CubeHandler ch = new CubeHandler();
            JsonWriter writer = new JsonWriter("./data/testWriterDefault.json");
            writer.open();
            writer.write(ch);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefault.json");
            ch = reader.read();
            assertTrue((new Cube()).equals(ch.getCube()));
            assertEquals(0, ch.getSavedCubes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            CubeHandler ch = new CubeHandler();
            ch.parseScramble("D2 U B2 U2 R2 D2 U2 R' L F B R' B2 U' D' F U B' F2 D' U2 R2 F2 B2 D");
            ch.saveCube();
            ch.reset();
            ch.parseScramble("B R2 F2 R L' B D' U B2 D' L' R' F' B2 R2 F' D2 F B U D F D2 U2 R");
            ch.saveCube();
            ch.reset();
            ch.parseScramble("L R' D B' R B R2 B L2 R2 B L2 B U2 D' B' D2 B' F L' B2 R' D R F");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCubeHandler.json");
            writer.open();
            writer.write(ch);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCubeHandler.json");
            CubeHandler ch2 = reader.read();

            assertTrue(ch2.getCube().equals(ch.getCube()));
            assertTrue(ch2.getSavedCubes().get(0).equals(ch.getSavedCubes().get(0)));
            assertTrue(ch2.getSavedCubes().get(1).equals(ch.getSavedCubes().get(1)));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}