package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {
    private Face face;

    @BeforeEach
    void runBefore() {
        face = new Face("U", new CenterCubelet(new Position(0, 0, 1)));

        face.addCorner(new CornerCubelet(new Position(-1, -1, 1)));
        face.addCorner(new CornerCubelet(new Position(1, -1, 1)));
        face.addCorner(new CornerCubelet(new Position(-1, 1, 1)));
        face.addCorner(new CornerCubelet(new Position(1, 1, 1)));


        face.addEdge(new EdgeCubelet(new Position(-1, 0, 1)));
        face.addEdge(new EdgeCubelet(new Position(1, 0, 1)));
        face.addEdge(new EdgeCubelet(new Position(0, 1, 1)));
        face.addEdge(new EdgeCubelet(new Position(0, -1, 1)));
    }
    @Test
    void addEdge() {
    }

    @Test
    void addCorner() {
    }

    @Test
    void testTo2DStringArray() {
        System.out.println(Arrays.deepToString(face.to2DStringArray()));
    }

    @Test
    void testGetOrderedCubelets() {
        Cubelet[][] orderedFace = face.getOrderedCubelets();
        for (Cubelet[] y : orderedFace) {
            for (Cubelet x : y) {
                System.out.print(x.getPos());
            }
        }
        System.out.println(Arrays.deepToString(face.getOrderedCubelets()));
    }
}