package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerCubeletTest {
    CornerCubelet cc1;
    CornerCubelet cc2;

    @BeforeEach
    void runBefore() {
        cc1 = new CornerCubelet(new Position(-1, 1, 1));
        cc2 = new CornerCubelet(new Position(1, 1, 1));
    }

    @Test
    void testScrambleColors() {
        String colorX = cc1.getColorX();
        String colorY = cc1.getColorY();
        String colorZ = cc1.getColorZ();
        cc1.scrambleColors();
        boolean noShift = (cc1.getColorX().equals(colorX) && cc1.getColorY().equals(colorY)
                && cc1.getColorZ().equals(colorZ));
        boolean shift1 = (cc1.getColorX().equals(colorY) && cc1.getColorY().equals(colorZ)
                && cc1.getColorZ().equals(colorX));
        boolean shift2 = (cc1.getColorX().equals(colorZ) && cc1.getColorY().equals(colorX)
                && cc1.getColorZ().equals(colorY));

        assertTrue(noShift || shift1 || shift2);

        cc2.scrambleColors();
        colorX = cc2.getColorX();
        colorY = cc2.getColorY();
        colorZ = cc2.getColorZ();

        noShift = (cc2.getColorX().equals(colorX) && cc2.getColorY().equals(colorY)
                && cc2.getColorZ().equals(colorZ));
        shift1 = (cc2.getColorX().equals(colorZ) && cc2.getColorY().equals(colorX)
                && cc2.getColorZ().equals(colorY));
        shift2 = (cc2.getColorX().equals(colorY) && cc2.getColorY().equals(colorZ)
                && cc2.getColorZ().equals(colorX));

        assertTrue(noShift || shift1 || shift2);
    }

    @Test
    void testGetClockwiseTurns() {
        assertEquals(0, cc1.getClockwiseTurns());
        String[] scramble = new String[] {cc1.getColorX(), cc1.getColorY(), cc1.getColorZ()};
        int shift = 1;
        cc1.setColorX(scramble[shift]);
        cc1.setColorY(scramble[(shift + 1) % 3]);
        cc1.setColorZ(scramble[(shift + 2) % 3]);
        assertEquals(1, cc1.getClockwiseTurns());

        shift = 2;
        cc1.setColorX(scramble[shift]);
        cc1.setColorY(scramble[(shift + 1) % 3]);
        cc1.setColorZ(scramble[(shift + 2) % 3]);
        assertEquals(2, cc1.getClockwiseTurns());


        assertEquals(0, cc2.getClockwiseTurns());
        scramble = new String[] {cc2.getColorX(), cc2.getColorZ(), cc2.getColorY()};
        shift = 1;
        cc2.setColorX(scramble[shift]);
        cc2.setColorZ(scramble[(shift + 1) % 3]);
        cc2.setColorY(scramble[(shift + 2) % 3]);
        assertEquals(1, cc2.getClockwiseTurns());

        shift = 2;
        cc2.setColorX(scramble[shift]);
        cc2.setColorZ(scramble[(shift + 1) % 3]);
        cc2.setColorY(scramble[(shift + 2) % 3]);
        assertEquals(2, cc2.getClockwiseTurns());
    }
}