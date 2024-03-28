package ui;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class SmartGroup extends Group {
    private Rotate rotate;
    private Transform transform = new Rotate();

    void rotateByX(int ang) {
        rotate = new Rotate(ang, Rotate.X_AXIS);
        transform = transform.createConcatenation(rotate);
        this.getTransforms().clear();
        this.getTransforms().addAll(transform);
    }
}
