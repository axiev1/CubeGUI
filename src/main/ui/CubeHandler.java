package ui;

import model.Cube;

public class CubeHandler {
    private Cube cube;

    public CubeHandler() {
        cube = new Cube();
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
}
