package persistence;

import org.json.JSONString;
import ui.CubeHandler;
import model.Cube;
import model.Cubelet;
import model.*;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads cube handler from JSON data stored in file (adapted from edx)
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads CubeHandler from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CubeHandler read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCubeHandler(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses cube handler from JSON object and returns it
    private CubeHandler parseCubeHandler(JSONObject jsonObject) {
        CubeHandler ch = new CubeHandler();

        Cube cube = parseCube((JSONObject) jsonObject.get("cube"));
        ch.setCube(cube);
        addSavedCubes(ch, jsonObject);
        return ch;
    }

    // MODIFIES: ch
    // EFFECTS: parses saved cubes from JSON object and adds them to cube handler
    private void addSavedCubes(CubeHandler ch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("savedCubes");

        ArrayList<Cube> savedCubes = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject nextCube = (JSONObject) json;
            savedCubes.add(parseCube(nextCube));
        }

        ch.setSavedCubes(savedCubes);
    }

    // EFFECTS: parses cube from JSON object and returns it
    private Cube parseCube(JSONObject jsonObject) {
        Cube cube = new Cube();
        addCenterCubelets(cube, jsonObject);
        addEdgeCubelets(cube, jsonObject);
        addCornerCubelets(cube, jsonObject);
        return cube;
    }

    // MODIFIES: cube
    // EFFECTS: adds center cubelets from JSON object to cube handler
    private void addCenterCubelets(Cube cube, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("centerCubelets");

        for (Object json : jsonArray) {
            JSONObject nextCenterCubelet = (JSONObject) json;
            addCenterCubelet(cube, nextCenterCubelet);
        }
    }

    // MODIFIES: cube
    // EFFECTS: adds a center cubelet from JSONObject to cube
    private void addCenterCubelet(Cube cube, JSONObject jsonObject) {
        Position targetPos = parsePos((JSONObject) jsonObject.get("targetPos"));

        Cubelet centerCubelet = new Cubelet(targetPos);
        centerCubelet.setPos(parsePos((JSONObject) jsonObject.get("currentPos")));
        centerCubelet.setColorX(parseColor("colorX", jsonObject));
        centerCubelet.setColorY(parseColor("colorY", jsonObject));
        centerCubelet.setColorZ(parseColor("colorZ", jsonObject));

        cube.getCenterCubelets().remove();
        cube.addCenterCubelet(centerCubelet);
    }

    // MODIFIES: cube
    // EFFECTS: adds edge cubelets from JSON object to cube handler
    private void addEdgeCubelets(Cube cube, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("edgeCubelets");

        for (Object json : jsonArray) {
            JSONObject nextEdgeCubelet = (JSONObject) json;
            addEdgeCubelet(cube, nextEdgeCubelet);
        }
    }

    // MODIFIES: cube
    // EFFECTS: adds an edge cubelet from JSONObject to cube
    private void addEdgeCubelet(Cube cube, JSONObject jsonObject) {
        Position targetPos = parsePos((JSONObject) jsonObject.get("targetPos"));

        EdgeCubelet edgeCubelet = new EdgeCubelet(targetPos);
        edgeCubelet.setPos(parsePos((JSONObject) jsonObject.get("currentPos")));
        edgeCubelet.setColorX(parseColor("colorX", jsonObject));
        edgeCubelet.setColorY(parseColor("colorY", jsonObject));
        edgeCubelet.setColorZ(parseColor("colorZ", jsonObject));

        cube.getEdgeCubelets().remove();
        cube.addEdgeCubelet(edgeCubelet);
    }

    // MODIFIES: cube
    // EFFECTS: adds corner cubelets from JSON object to cube handler
    private void addCornerCubelets(Cube cube, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cornerCubelets");

        for (Object json : jsonArray) {
            JSONObject nextCornerCubelet = (JSONObject) json;
            addCornerCubelet(cube, nextCornerCubelet);
        }
    }

    // MODIFIES: cube
    // EFFECTS: adds a corner cubelet from JSONObject to cube
    private void addCornerCubelet(Cube cube, JSONObject jsonObject) {
        Position targetPos = parsePos((JSONObject) jsonObject.get("targetPos"));

        CornerCubelet cornerCubelet = new CornerCubelet(targetPos);
        cornerCubelet.setPos(parsePos((JSONObject) jsonObject.get("currentPos")));
        cornerCubelet.setColorX(parseColor("colorX", jsonObject));
        cornerCubelet.setColorY(parseColor("colorY", jsonObject));
        cornerCubelet.setColorZ(parseColor("colorZ", jsonObject));

        cube.getCornerCubelets().remove();
        cube.addCornerCubelet(cornerCubelet);
    }

    // EFFECTS: parses a position object from jsonObject and returns it
    private Position parsePos(JSONObject jsonObject) {
        int x = jsonObject.getInt("posX");
        int y = jsonObject.getInt("posY");
        int z = jsonObject.getInt("posZ");

        Position pos = new Position(x, y, z);
        return pos;
    }

    // EFFECTS: parses a color from jsonObject and returns it
    private String parseColor(String key, JSONObject jsonObject) {
        if (!jsonObject.has(key)) {
            return null;
        }
        return jsonObject.getString(key);
    }
}
