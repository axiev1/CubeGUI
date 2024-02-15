package ui;

import model.Cube;

import java.util.ArrayList;
import java.util.Arrays;

public class CubeHandler {
    private Cube cube;
    private ArrayList<Cube> savedCubes;

    public CubeHandler() {
        cube = new Cube();
        savedCubes = new ArrayList<>();
    }

    public void parseScramble(String scramble) {
        String[] turns = scramble.split(" ");
        for (String turn : turns) {
            parseTurn(turn);
        }
    }

    public void scramble() {
        cube.scramble();
    }

    public void parseTurn(String turn) {
        String orientation = turn.substring(0, 1).toUpperCase();
        boolean clockwise = true;
        int numTurns = 1;
        if (turn.length() == 2) {
            if (turn.substring(1, 2).equals("'")) {
                clockwise = false;
            } else {
                numTurns = Integer.parseInt(turn.substring(1, 2));
            }
        }
        cube.rotateFace(orientation, clockwise, numTurns);
    }

    public void reset() {
        cube = new Cube();
    }

    public void saveCube() {
        savedCubes.add(new Cube(cube));
        System.out.println("Cube was saved");
    }

    public void loadCube(int index) {
        cube = savedCubes.get(index - 1);
        print();
    }

    public void viewSavedCubes() {
        for (int i = 0; i < savedCubes.size(); i++) {
            System.out.println(i + 1 + ". ");
            print(savedCubes.get(i));
            System.out.println();
        }
    }

    public void print() {
        printCube(this.cube);
    }

    public void print(Cube cube) {
        printCube(cube);
    }

    // EFFECTS: prints out the cube to the console
    private void printCube(Cube cube) {
        String[][] faceU = cube.getFace("U").to2DStringArray();
        String[][] faceL = cube.getFace("L").to2DStringArray();
        String[][] faceF = cube.getFace("F").to2DStringArray();
        String[][] faceR = cube.getFace("R").to2DStringArray();
        String[][] faceB = cube.getFace("B").to2DStringArray();
        String[][] faceD = cube.getFace("D").to2DStringArray();

        String[][] middle = new String[3][12];

        for (int y = 0; y < 3; y++) {
            String[] combined = Arrays.copyOf(faceL[y], 12);
            System.arraycopy(faceF[y], 0, combined, 3, 3);
            System.arraycopy(faceR[y], 0, combined, 6, 3);
            System.arraycopy(faceB[y], 0, combined, 9, 3);
            middle[y] = combined;
        }

        printFace(faceU, true);
        printFace(middle, false);
        printFace(faceD, true);
    }

    // EFFECTS: prints out a face to the console
    private void printFace(String[][] faceToPrint, boolean padding) {

        String row0 = "| " + String.join(" | ", faceToPrint[0]) + " |";
        String row1 = "| " + String.join(" | ", faceToPrint[1]) + " |";
        String row2 = "| " + String.join(" | ", faceToPrint[2]) + " |";
        String dashSeparator = "-".repeat(row0.length());

        if (padding) {
            String whitespace = " ".repeat(row0.length() - 1);
            row0 = whitespace + row0;
            row1 = whitespace + row1;
            row2 = whitespace + row2;
            dashSeparator = whitespace + dashSeparator;
        }

        System.out.println(dashSeparator);
        System.out.println(row0);
        System.out.println(dashSeparator);
        System.out.println(row1);
        System.out.println(dashSeparator);
        System.out.println(row2);
        System.out.println(dashSeparator);
    }
}
