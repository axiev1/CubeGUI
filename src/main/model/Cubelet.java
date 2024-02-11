package model;

public abstract class Cubelet {
    private String colorX;
    private String colorY;
    private String colorZ;
    private Position currentPos;
    private Position targetPos;

    public Cubelet(Position pos) {
        this.currentPos = pos;
        this.targetPos = (new Position(pos.getX(), pos.getY(), pos.getZ()));
        setColors();
    }

    public Cubelet(Cubelet c) {
        this.colorX = c.getColorX();
        this.colorY = c.getColorY();
        this.colorZ = c.getColorZ();
        this.currentPos = new Position(c.getPos());
        this.targetPos = new Position(c.getTargetPos());
    }

    private void setColors() {
        String[] colorsX = new String[] {"O", null, "R"};
        String[] colorsY = new String[] {"G", null, "B"};
        String[] colorsZ = new String[] {"Y", null, "W"};

        this.colorX = colorsX[targetPos.getX() + 1];
        this.colorY = colorsY[targetPos.getY() + 1];
        this.colorZ = colorsZ[targetPos.getZ() + 1];
    }

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

    public abstract void scrambleColors();
}
