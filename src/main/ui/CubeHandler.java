package ui;

import model.Cube;

import java.util.ArrayList;

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

    public void print() {
        cube.printCube();
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
                numTurns = Integer.valueOf(turn.substring(1, 2));
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
        cube.printCube();
    }

    public void viewSavedCubes() {
        for (int i = 0; i < savedCubes.size(); i++) {
            System.out.println(Integer.toString(i + 1) + ". ");
            savedCubes.get(i).printCube();
            System.out.println();
        }
    }
}
