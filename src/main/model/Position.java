package model;

public class Position {
    private int posX;
    private int posY;
    private int posZ;

    public Position(int x, int y, int z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
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

    public Boolean isInFace(String face) {
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
}
