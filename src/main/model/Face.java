package model;

import java.util.ArrayList;

// represents a face of a Rubik's cube
public class Face {
    private Cubelet center;
    private ArrayList<EdgeCubelet> edges;
    private ArrayList<CornerCubelet> corners;
    private String orientation;

    public Face(String orientation, Cubelet center) {
        this.orientation = orientation;
        this.center = center;
        this.edges = new ArrayList<EdgeCubelet>();
        this.corners = new ArrayList<CornerCubelet>();
    }

    public void addEdge(EdgeCubelet c) {
        edges.add(c);
    }

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

    private void rotateCubeletColors(Cubelet[][] orderedCubelets) {
        for (Cubelet[] row : orderedCubelets) {
            for (Cubelet c : row) {
                c.rotate(orientation);
            }
        }
    }

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

        rotateCubeletColors(orderedCubelets);
    }

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

        rotateCubeletColors(orderedCubelets);
    }
}
