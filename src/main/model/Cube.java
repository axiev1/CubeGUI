package model;

import java.sql.Array;
import java.util.*;

// represents a Rubik's cube
public class Cube {
    private LinkedList<CenterCubelet> centerCubelets;
    private LinkedList<EdgeCubelet> edgeCubelets;
    private LinkedList<CornerCubelet> cornerCubelets;

    // EFFECTS: Initializes a cube in the solved state
    public Cube() {
        this.centerCubelets = new LinkedList<>();
        this.edgeCubelets = new LinkedList<>();
        this.cornerCubelets = new LinkedList<>();

        generateStartingPositions();
    }

    // EFFECTS: Copy constructor for cube
    public Cube(Cube cube) {
        this.centerCubelets = new LinkedList<>();
        this.edgeCubelets = new LinkedList<>();
        this.cornerCubelets = new LinkedList<>();

        for (CenterCubelet c : cube.getCenterCubelets()) {
            this.centerCubelets.add(new CenterCubelet(c));
        }
        for (EdgeCubelet c : cube.getEdgeCubelets()) {
            this.edgeCubelets.add(new EdgeCubelet(c));
        }
        for (CornerCubelet c : cube.getCornerCubelets()) {
            this.cornerCubelets.add(new CornerCubelet(c));
        }
    }

    public LinkedList<CenterCubelet> getCenterCubelets() {
        return centerCubelets;
    }

    public LinkedList<EdgeCubelet> getEdgeCubelets() {
        return edgeCubelets;
    }

    public LinkedList<CornerCubelet> getCornerCubelets() {
        return cornerCubelets;
    }

    // REQUIRES: Orientation is one of U L F R B D
    // MODIFIES: this
    // EFFECTS: Rotates the face of the cube as given by orientation either clockwise or counterclockwise, count
    //          amount of times
    public void rotateFace(String orientation, boolean clockwise, int count) {
        Face face = getFace(orientation);
        for (int i = 0; i < count; i++) {
            if (clockwise) {
                face.rotateFaceClockwise();
            } else {
                face.rotateFaceCounterClockwise();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Scrambles the cube
    public void scramble() {
        while (true) {
            centerCubelets.clear();
            edgeCubelets.clear();
            cornerCubelets.clear();
            generateStartingPositions();
            Collections.shuffle(edgeCubelets);
            Collections.shuffle(cornerCubelets);
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        assignRandom(x, y, z);
                    }
                }
            }
            if (isValid()) {
                return;
            }
        }
    }

    // REQUIRES: x, y, and z are one of -1, 1, or 1
    // MODIFIES: this
    // EFFECTS: Assigns a random cubelet of the correct type in the given position
    private void assignRandom(int x, int y, int z) {
        EdgeCubelet ec;
        CornerCubelet cc;

        int numberOfZeroVals = ((x == 0) ? 1 : 0) + ((y == 0) ? 1 : 0) + ((z == 0) ? 1 : 0);
        switch (numberOfZeroVals) {
            case 0:
                cc = cornerCubelets.poll();
                cc.setPos(new Position(x, y, z));
                cc.scrambleColors();
                cornerCubelets.add(cc);
                break;
            case 1:
                ec = edgeCubelets.poll();
                ec.setPos(new Position(x, y, z));
                ec.scrambleColors();
                edgeCubelets.add(ec);
                break;
        }
    }

    // EFFECTS: Returns true if the cube is in a solvable state
    public boolean isValid() {
        return cornerParity() && edgeParity() && cycleParity();
    }

    // EFFECTS: returns true if the cube has the correct corner parity
    private boolean cornerParity() {
        int turns = 0;
        for (CornerCubelet c : cornerCubelets) {
            turns += c.getClockwiseTurns();
        }
        return (turns % 3 == 0);
    }

    // EFFECTS: returns true if the cube has correct edge parity
    private boolean edgeParity() {
        int numKeyColorsOnKeyFaces = 0;
        for (EdgeCubelet c : edgeCubelets) {
            numKeyColorsOnKeyFaces += c.keyColorOnKeyFace();
        }
        return (numKeyColorsOnKeyFaces % 2 == 0);
    }

    // EFFECTS: returns true if the edge and corner pieces have the correct parity
    private boolean cycleParity() {
        int totalCycleLength = 0;
        ArrayList<ArrayList<Position>> cycles = getCycles();

        for (ArrayList<Position> cycle : cycles) {
            if (cycle.size() % 2 == 0) {
                totalCycleLength++;
            }
        }
        return (totalCycleLength % 2 == 0);
    }

    // EFFECTS: returns the list of all cycles in the Rubik's cube. For example, position A has piece C. Piece C should
    //          be in position B, which has Piece A. The cycle is (A, C, B).
    private ArrayList<ArrayList<Position>> getCycles() {
        ArrayList<ArrayList<Position>> res = new ArrayList<>();
        ArrayList<Position> visited = new ArrayList<>();

        for (Cubelet c : cornerCubelets) {
            if (!visited.contains(c.getPos()) && !c.getPos().equals(c.getTargetPos())) {
                res.add(dfs(c, visited, new ArrayList<Position>()));
            }
        }

        for (Cubelet c : edgeCubelets) {
            if (!visited.contains(c.getPos()) && !c.getPos().equals(c.getTargetPos())) {
                res.add(dfs(c, visited, new ArrayList<Position>()));
            }
        }

        return res;
    }

    // EFFECTS: Returns a cycle starting at a given cubelet
    private ArrayList<Position> dfs(Cubelet c, ArrayList<Position> visited, ArrayList<Position> rsf) {
        if (visited.contains(c.getPos())) {
            return rsf;
        }
        rsf.add(c.getPos());
        visited.add(c.getPos());
        return dfs(getCubeletAtPos(c.getTargetPos()), visited, rsf);
    }

    // REQUIRES: pos.getX(), pos.getY(), and pos.getZ() are all one of -1, 0, and 1.
    // EFFECTS: return Cubelet at a given position
    private Cubelet getCubeletAtPos(Position pos) {
        List<Cubelet> allCubelets = new ArrayList<>();
        allCubelets.addAll(cornerCubelets);
        allCubelets.addAll(edgeCubelets);
        return allCubelets.stream().filter(c -> c.getPos().equals(pos)).findFirst().orElse(null);
    }

    // REQUIRES: orientation is one of U, L, F, R, B, or D
    // EFFECTS: returns face at given orientation
    public Face getFace(String orientation) {
        CenterCubelet center = centerCubelets.stream().filter(c -> c.isInFace(orientation))
                .findFirst().orElse(null);

        Face face = new Face(orientation, center);

        for (EdgeCubelet ec : edgeCubelets) {
            if (ec.isInFace(orientation)) {
                face.addEdge(ec);
            }
        }

        for (CornerCubelet cc : cornerCubelets) {
            if (cc.isInFace(orientation)) {
                face.addCorner(cc);
            }
        }
        return face;
    }

    // EFFECTS: generates cubelets at starting positions
    private void generateStartingPositions() {
        int numberOfZeroVals;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    numberOfZeroVals = ((x == 0) ? 1 : 0) + ((y == 0) ? 1 : 0) + ((z == 0) ? 1 : 0);
                    switch (numberOfZeroVals) {
                        case 0:
                            cornerCubelets.add(new CornerCubelet(new Position(x, y, z)));
                            break;
                        case 1:
                            edgeCubelets.add(new EdgeCubelet(new Position(x, y, z)));
                            break;
                        case 2:
                            centerCubelets.add(new CenterCubelet(new Position(x, y, z)));
                            break;
                    }
                }
            }
        }
    }
}
