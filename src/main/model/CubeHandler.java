package model;

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
        EventLog.getInstance().logEvent(new Event("Cube scrambled."));
    }

    // MODIFIES: this
    // EFFECTS: sets the current cube
    public void setCube(Cube cube) {
        this.cube = cube;
        EventLog.getInstance().logEvent(new Event("Saved cube loaded."));
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
        EventLog.getInstance().logEvent(new Event("Cube scrambled."));
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
        EventLog.getInstance().logEvent(new Event("Cube rotated."));
    }

    // MODIFIES: this
    // EFFECTS: resets the cube to the solved state
    public void reset() {
        setCube(new Cube(modelExists));
        EventLog.getInstance().logEvent(new Event("Cube reset."));
    }

    // MODIFIES: this
    // EFFECTS: saves the current cube state to a list of saved cubes
    public void saveCube() {
        savedCubes.add(new Cube(cube));
        EventLog.getInstance().logEvent(new Event("Cube saved."));
    }

    // REQUIRES: 1 <= index <= savedCubes.size()
    // MODIFIES: this
    // EFFECTS: loads a cube state from the saved states at index
    public void loadCube(int index) {
        setCube(savedCubes.get(index - 1));
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
