package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a position of a cubelet, with an x, y, and z coordinate relative to the middle of the cube
public class Position implements Writable {
    private int posX;
    private int posY;
    private int posZ;

    // EFFECTS: constructs a new position with an x, y, and z coordinate
    public Position(int x, int y, int z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    // EFFECTS: copy constructor for position
    public Position(Position pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getZ() {
        return posZ;
    }

    // REQUIRES: face is one of L, R, F, B, D, U
    // EFFECTS: returns true if the position puts it in the given face, else false
    public boolean isInFace(String face) {
        switch (face) {
            case "L":
                return (posX == -1);
            case "R":
                return (posX == 1);
            case "F":
                return (posY == -1);
            case "B":
                return (posY == 1);
            case "D":
                return (posZ == -1);
            default:
                return (posZ == 1);
        }
    }

    // EFFECTS: returns true if current position is equivalent to the given position
    public boolean equals(Position pos2) {
        return (posX == pos2.getX() && posY == pos2.getY() && posZ == pos2.getZ());
    }

    // EFFECTS: returns position as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("posX", posX);
        jsonObject.put("posY", posY);
        jsonObject.put("posZ", posZ);

        return jsonObject;
    }
}
