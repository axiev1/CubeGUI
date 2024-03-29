package ui;

import model.Cube;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;

// Cube handler class to control the cube
public class CubeHandler implements Writable {
    private Cube cube;
    private ArrayList<Cube> savedCubes;
    private boolean modelExists;

    // EFFECTS: creates new cube handler object with a cube and an empty list of saved cube states
    public CubeHandler(boolean modelExists) {
        cube = new Cube(modelExists);
        savedCubes = new ArrayList<>();
        this.modelExists = modelExists;
    }

    // REQUIRES: scramble string is in proper notation
    // MODIFIES: this
    // EFFECTS: parses a scramble string
    public void parseScramble(String scramble) {
        String[] turns = scramble.split(" ");
        for (String turn : turns) {
            parseTurn(turn);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the current cube
    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public Cube getCube() {
        return cube;
    }

    public ArrayList<Cube> getSavedCubes() {
        return savedCubes;
    }

    // MODIFIES: this
    // EFFECTS: sets the saved cubes
    public void setSavedCubes(ArrayList<Cube> savedCubes) {
        this.savedCubes = savedCubes;
    }

    // MODIFIES: this
    // EFFECTS: scrambles the cube
    public void scramble() {
        cube.scramble();
    }

    // REQUIRES: turn is a valid instruction
    // MODIFIES: this
    // EFFECTS: parses a turn by the user
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

    // MODIFIES: this
    // EFFECTS: resets the cube to the solved state
    public void reset() {
        setCube(new Cube(modelExists));
    }

    // MODIFIES: this
    // EFFECTS: saves the current cube state to a list of saved cubes
    public void saveCube() {
        savedCubes.add(new Cube(cube));
        System.out.println("Cube was saved");
    }

    // REQUIRES: 1 <= index <= savedCubes.size()
    // MODIFIES: this
    // EFFECTS: loads a cube state from the saved states at index
    public void loadCube(int index) {
        setCube(savedCubes.get(index - 1));
        print();
    }

    // EFFECTS: displays the list of saved cubes
    public void viewSavedCubes() {
        for (int i = 0; i < savedCubes.size(); i++) {
            System.out.println(i + 1 + ". ");
            print(savedCubes.get(i));
            System.out.println();
        }
    }

    // EFFECTS: prints out the cube
    public void print() {
        printCube(this.cube);
    }

    // EFFECTS: prints out a given cube
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

    // EFFECTS: prints formatted 2D string array
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

    // EFFECTS: Converts cube handler to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cube", cube.toJson());
        json.put("savedCubes", savedCubesToJson());
        json.put("modelExists", modelExists);
        return json;
    }

    // EFFECTS: converts saved cubes to json
    private JSONArray savedCubesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cube c : savedCubes) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
