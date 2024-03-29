package model;

import org.json.JSONObject;
import persistence.Writable;
import ui.CubeletModel;

import java.util.Objects;

// Represents a cubelet, one of the smaller cubes that make up the Rubik's cube
public class Cubelet implements Writable {
    private String colorX;
    private String colorY;
    private String colorZ;
    private Position currentPos;
    private Position targetPos;
    private CubeletModel cubeletModel;
    private boolean modelExists;

    // EFFECTS: Constructs new cubelet with a position and target position
    public Cubelet(Position pos) {
        this.currentPos = pos;
        this.targetPos = (new Position(pos));
        this.modelExists = false;
        setColors();
    }

    // EFFECTS: Constructs new cubelet with a position and target position
    public Cubelet(Position pos, boolean modelExists) {
        this.currentPos = pos;
        this.targetPos = new Position(pos);
        this.modelExists = modelExists;
        setColors();
    }

    // EFFECTS: Copy constructor for cubelet
    public Cubelet(Cubelet c) {
        this.colorX = c.getColorX();
        this.colorY = c.getColorY();
        this.colorZ = c.getColorZ();
        this.currentPos = new Position(c.getPos());
        this.targetPos = new Position(c.getTargetPos());
        this.modelExists = c.isModelExists();
        if (isModelExists()) {
            cubeletModel = new CubeletModel(colorX, colorY, colorZ, currentPos);
        }
    }

    // MODIFIES: this
    // EFFECTS: assigns the correct colors to the cubelet when initialized
    private void setColors() {
        String[] colorsX = new String[] {"O", null, "R"};
        String[] colorsY = new String[] {"G", null, "B"};
        String[] colorsZ = new String[] {"Y", null, "W"};

        setColorX(colorsX[targetPos.getX() + 1]);
        setColorY(colorsY[targetPos.getY() + 1]);
        setColorZ(colorsZ[targetPos.getZ() + 1]);

        if (isModelExists()) {
            cubeletModel = new CubeletModel(colorX, colorY, colorZ, currentPos);
        }
    }

    // REQUIRES: face is one of U, L, F, R, B, D
    // EFFECTS: returns true if cubelet is in given face
    public boolean isInFace(String face) {
        return currentPos.isInFace(face);
    }

    public Position getPos() {
        return currentPos;
    }

    public void setPos(Position pos) {
        this.currentPos = pos;
    }

    public String getColorX() {
        return colorX;
    }

    public String getColorY() {
        return colorY;
    }

    public String getColorZ() {
        return colorZ;
    }

    public void setColorX(String color) {
        colorX = color;
    }

    public void setColorY(String color) {
        colorY = color;
    }

    public void setColorZ(String color) {
        colorZ = color;
    }

    // REQUIRES: orientation is one of U L F R B D
    // MODIFIES: this
    // EFFECTS: corrects the x, y, and z colors of the cubelet after it is rotated
    public void rotate(String orientation, boolean clockwise) {
        if (orientation.equals("U") || orientation.equals("D")) {
            String colorX = getColorX();
            setColorX(getColorY());
            setColorY(colorX);
        } else if (orientation.equals("L") || orientation.equals("R")) {
            String colorY = getColorY();
            setColorY(getColorZ());
            setColorZ(colorY);
        } else {
            String colorX = getColorX();
            setColorX(getColorZ());
            setColorZ(colorX);
        }
        if (isModelExists()) {
            cubeletModel.rotate(orientation, clockwise);
        }
    }

    public Position getTargetPos() {
        return targetPos;
    }

    public CubeletModel getCubeletModel() {
        return cubeletModel;
    }

    public boolean isModelExists() {
        return modelExists;
    }

    // EFFECTS: returns cubelet as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("targetPos", targetPos.toJson());
        jsonObject.put("currentPos", currentPos.toJson());
        jsonObject.put("colorX", colorX);
        jsonObject.put("colorY", colorY);
        jsonObject.put("colorZ", colorZ);
        jsonObject.put("modelExists", modelExists);

        return jsonObject;
    }

    // EFFECTS: return true if cubelet is equivalent to this
    public boolean equals(Cubelet c) {
        return Objects.equals(colorX, c.getColorX()) && Objects.equals(colorY, c.getColorY())
                && Objects.equals(colorZ, c.getColorZ()) && currentPos.equals(c.getPos())
                && targetPos.equals(c.getTargetPos());
    }
}
