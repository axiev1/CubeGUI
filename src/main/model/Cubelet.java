package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a cubelet, one of the smaller cubes that make up the Rubik's cube
public class Cubelet implements Writable {
    private String colorX;
    private String colorY;
    private String colorZ;
    private Position currentPos;
    private Position targetPos;

    // EFFECTS: Constructs new cubelet with a position and target position
    public Cubelet(Position pos) {
        this.currentPos = pos;
        this.targetPos = (new Position(pos));
        setColors();
    }

    // EFFECTS: Copy constructor for cubelet
    public Cubelet(Cubelet c) {
        this.colorX = c.getColorX();
        this.colorY = c.getColorY();
        this.colorZ = c.getColorZ();
        this.currentPos = new Position(c.getPos());
        this.targetPos = new Position(c.getTargetPos());
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
    public void rotate(String orientation) {
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
    }

    public Position getTargetPos() {
        return targetPos;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("targetPos", targetPos.toJson());
        jsonObject.put("currentPos", currentPos.toJson());
        jsonObject.put("colorX", colorX);
        jsonObject.put("colorY", colorY);
        jsonObject.put("colorZ", colorZ);

        return jsonObject;
    }

    public boolean equals(Cubelet c) {
        return Objects.equals(colorX, c.getColorX()) && Objects.equals(colorY, c.getColorY())
                && Objects.equals(colorZ, c.getColorZ()) && currentPos.equals(c.getPos())
                && targetPos.equals(c.getTargetPos());
    }
}
