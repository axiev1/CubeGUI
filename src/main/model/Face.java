package model;

import java.util.ArrayList;

// represents a face of a Rubik's cube
public class Face {
    private Cubelet center;
    private ArrayList<EdgeCubelet> edges;
    private ArrayList<CornerCubelet> corners;
    private String orientation;

    // REQUIRES: orientation is one of U, L, F, R, B, D
    // EFFECTS: constructs a new face with an orientation and center cubelet
    public Face(String orientation, Cubelet center) {
        this.orientation = orientation;
        this.center = center;
        this.edges = new ArrayList<EdgeCubelet>();
        this.corners = new ArrayList<CornerCubelet>();
    }

    // MODIFIES: this
    // EFFECTS: adds an edge cubelet to the face
    public void addEdge(EdgeCubelet c) {
        edges.add(c);
    }

    // MODIFIES: this
    // EFFECTS: adds a corner cubelet to the face
    public void addCorner(CornerCubelet c) {
        corners.add(c);
    }

    public String getOrientation() {
        return orientation;
    }

    public Cubelet getCenter() {
        return center;
    }

    public ArrayList<EdgeCubelet> getEdges() {
        return edges;
    }

    public ArrayList<CornerCubelet> getCorners() {
        return corners;
    }

    // REQUIRES: edges and corners are both length 4, center is defined
    // EFFECTS: produces a 2D string array representing the colors of the face when seen head on
    public String[][] to2DStringArray() {
        Cubelet[][] orderedCubelets = getOrderedCubelets();
        String[][] colors = new String[3][3];

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (orientation.equals("L") || orientation.equals("R")) {
                    colors[y][x] = orderedCubelets[y][x].getColorX();
                } else if (orientation.equals("U") || orientation.equals("D")) {
                    colors[y][x] = orderedCubelets[y][x].getColorZ();
                } else {
                    colors[y][x] = orderedCubelets[y][x].getColorY();
                }
            }
        }

        return colors;
    }

    // REQUIRES: cubelet is on face
    // EFFECTS: returns the relative x position of a given cubelet on the face
    private int getRelativeX(Cubelet cubelet) {
        switch (orientation) {
            case "L":
                return 2 - (cubelet.getPos().getY() + 1);
            case "R":
                return cubelet.getPos().getY() + 1;
            case "U":
            case "D":
            case "F":
                return cubelet.getPos().getX() + 1;
            default:
                return 2 - (cubelet.getPos().getX() + 1);
        }
    }

    // REQUIRES: cubelet is on face
    // EFFECTS: returns the relative y position of a given cubelet on the face
    private int getRelativeY(Cubelet cubelet) {
        switch (orientation) {
            case "U":
                return 2 - (cubelet.getPos().getY() + 1);
            case "D":
                return cubelet.getPos().getY() + 1;
            default:
                return 2 - (cubelet.getPos().getZ() + 1);
        }
    }

    // REQUIRES: edges and corners are both length 4, center is defined
    // EFFECTS: returns a ordered 2D cubelet array of the cubelets within the face
    public Cubelet[][] getOrderedCubelets() {
        Cubelet[][] orderedCubelets = new Cubelet[3][3];
        ArrayList<Cubelet> allCubelets = new ArrayList<>();
        allCubelets.addAll(edges);
        allCubelets.addAll(corners);

        for (Cubelet c : allCubelets) {
            orderedCubelets[getRelativeY(c)][getRelativeX(c)] = c;
        }

        orderedCubelets[1][1] = center;
        return orderedCubelets;
    }

    // MODIFIES: this, orderedCubelets
    // EFFECTS: corrects the colors of the cubelet after rotation of the face
    private void rotateCubeletColors(Cubelet[][] orderedCubelets, boolean clockwise) {
        for (Cubelet[] row : orderedCubelets) {
            for (Cubelet c : row) {
                c.rotate(orientation, clockwise);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: rotates the current face clockwise
    public void rotateFaceClockwise() {
        Cubelet[][] orderedCubelets = getOrderedCubelets();
        Position topLeftPos = orderedCubelets[0][0].getPos();
        orderedCubelets[0][0].setPos(orderedCubelets[0][2].getPos());
        orderedCubelets[0][2].setPos(orderedCubelets[2][2].getPos());
        orderedCubelets[2][2].setPos(orderedCubelets[2][0].getPos());
        orderedCubelets[2][0].setPos(topLeftPos);

        Position topPos = orderedCubelets[0][1].getPos();
        orderedCubelets[0][1].setPos(orderedCubelets[1][2].getPos());
        orderedCubelets[1][2].setPos(orderedCubelets[2][1].getPos());
        orderedCubelets[2][1].setPos(orderedCubelets[1][0].getPos());
        orderedCubelets[1][0].setPos(topPos);

        rotateCubeletColors(orderedCubelets, true);
    }

    // MODIFIES: this
    // EFFECTS: rotates the current face counterclockwise
    public void rotateFaceCounterClockwise() {
        Cubelet[][] orderedCubelets = getOrderedCubelets();
        Position topLeftPos = orderedCubelets[0][0].getPos();
        orderedCubelets[0][0].setPos(orderedCubelets[2][0].getPos());
        orderedCubelets[2][0].setPos(orderedCubelets[2][2].getPos());
        orderedCubelets[2][2].setPos(orderedCubelets[0][2].getPos());
        orderedCubelets[0][2].setPos(topLeftPos);

        Position topPos = orderedCubelets[0][1].getPos();
        orderedCubelets[0][1].setPos(orderedCubelets[1][0].getPos());
        orderedCubelets[1][0].setPos(orderedCubelets[2][1].getPos());
        orderedCubelets[2][1].setPos(orderedCubelets[1][2].getPos());
        orderedCubelets[1][2].setPos(topPos);

        rotateCubeletColors(orderedCubelets, false);
    }
}
