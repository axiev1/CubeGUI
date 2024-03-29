package model;

// Represents an edge cubelet in the Rubik's cube
public class EdgeCubelet extends Cubelet {
    public EdgeCubelet(Position pos) {
        super(pos);
    }

    // EFFECTS: creates a new edge cubelet with a position
    public EdgeCubelet(Position pos, boolean modelExists) {
        super(pos, modelExists);
    }

    // EFFECTS: copy constructor for edge cubelet
    public EdgeCubelet(Cubelet c) {
        super(c);
    }

    // MODIFIES: this
    // EFFECTS: scrambles the colors of cubelet by randomly swapping or not swapping the colors
    public void scrambleColors() {
        String color1 = getColor1();
        String color2 = getColor2();

        if (Math.random() < 0.5) {
            String swap = color1;
            color1 = color2;
            color2 = swap;
        }

        if (getPos().getX() == 0) {
            setColorX(null);
            setColorY(color1);
            setColorZ(color2);
        } else if (getPos().getY() == 0) {
            setColorX(color1);
            setColorY(null);
            setColorZ(color2);
        } else {
            setColorX(color1);
            setColorY(color2);
            setColorZ(null);
        }

        generateModel();
    }

    //
    private void generateModel() {
        if (isModelExists()) {
            getCubeletModel().createGroup(getColorX(), getColorY(), getColorZ(), getPos());
        }
    }

    // EFFECTS: returns the first color of the cubelet
    private String getColor1() {
        String color1;
        if (getTargetPos().getX() == 0) {
            color1 = getColorY();
        } else if (getTargetPos().getY() == 0) {
            color1 = getColorX();
        } else {
            color1 = getColorX();
        }
        return color1;
    }

    // EFFECTS: returns the second color of the cubelet
    private String getColor2() {
        String color2;
        if (getTargetPos().getX() == 0) {
            color2 = getColorZ();
        } else if (getTargetPos().getY() == 0) {
            color2 = getColorZ();
        } else {
            color2 = getColorY();
        }
        return color2;
    }

    // EFFECTS: returns 1 if the up or down color is on the up or down face, or if the edge cubelet does not have an
    //          up or down color, returns 1 if the left or right color is on the left or right face. Else returns 0.
    public int keyColorOnKeyFace() {
        String keyColor1 = "B";
        String keyColor2 = "G";
        if (getTargetPos().getZ() != 0) {
            keyColor1 = "W";
            keyColor2 = "Y";
        }
        if (getPos().getZ() != 0) {
            if (getColorZ().equals(keyColor1) || getColorZ().equals(keyColor2)) {
                return 1;
            }
        } else {
            if (getColorY().equals(keyColor1) || getColorY().equals(keyColor2)) {
                return 1;
            }
        }
        return 0;
    }
}
