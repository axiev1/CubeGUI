package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import model.Position;

import java.util.HashMap;
import java.util.Map;

// Class representing a cubelet 3D model, consisting of a central box and stickers
public class CubeletModel {
    private static final int CUBELET_SIZE = 50;
    private static final int STICKER_SIZE = CUBELET_SIZE - 6;
    private Group group;
    private double cubeletCenterX;
    private double cubeletCenterY;
    private double cubeletCenterZ;
    private double rotationRate = 50;

    // EFFECTS: constructor for cubelet model with colors and a position
    public CubeletModel(String colorX, String colorY, String colorZ, Position pos) {
        createGroup(colorX, colorY, colorZ, pos);
    }

    // MODIFIES: this
    // EFFECTS: creates the 3D model of the cubelet
    public void createGroup(String colorX, String colorY, String colorZ, Position pos) {
        cubeletCenterX = CUBELET_SIZE * pos.getX();
        cubeletCenterY = -1 * CUBELET_SIZE * pos.getZ();
        cubeletCenterZ = CUBELET_SIZE * pos.getY();

        group = new Group();
        addBox();
        if (colorX != null) {
            addStickerX(colorX, pos.getX());
        }

        if (colorZ != null) {
            addStickerY(colorZ, pos.getZ());
        }

        if (colorY != null) {
            addStickerZ(colorY, pos.getY());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the center box
    private void addBox() {
        Box box = new Box(CUBELET_SIZE, CUBELET_SIZE, CUBELET_SIZE);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLACK);
        box.setMaterial(material);
        box.translateXProperty().set(cubeletCenterX);
        box.translateYProperty().set(cubeletCenterY);
        box.translateZProperty().set(cubeletCenterZ);
        group.getChildren().add(box);
    }

    // MODIFIES: this
    // EFFECTS: creates the sticker on the x-axis
    private void addStickerX(String color, int xpos) {
        Box stickerX = new Box(1, STICKER_SIZE, STICKER_SIZE);
        PhongMaterial materialX = new PhongMaterial();
        materialX.setDiffuseColor(getColor(color));
        stickerX.setMaterial(materialX);
        stickerX.translateXProperty().set(cubeletCenterX + 0.5 * CUBELET_SIZE * xpos);
        stickerX.translateYProperty().set(cubeletCenterY);
        stickerX.translateZProperty().set(cubeletCenterZ);
        group.getChildren().add(stickerX);
    }

    // MODIFIES: this
    // EFFECTS: creates the sticker on the y-axis
    private void addStickerY(String color, int ypos) {
        Box stickerY = new Box(STICKER_SIZE, 1, STICKER_SIZE);
        PhongMaterial materialY = new PhongMaterial();
        materialY.setDiffuseColor(getColor(color));
        stickerY.setMaterial(materialY);
        stickerY.translateXProperty().set(cubeletCenterX);
        stickerY.translateYProperty().set(cubeletCenterY - 0.5 * CUBELET_SIZE * ypos);
        stickerY.translateZProperty().set(cubeletCenterZ);
        group.getChildren().add(stickerY);
    }

    // MODIFIES: this
    // EFFECTS: creates the sticker on the z-axis
    private void addStickerZ(String color, int zpos) {
        Box stickerZ = new Box(STICKER_SIZE, STICKER_SIZE, 1);
        PhongMaterial materialZ = new PhongMaterial();
        materialZ.setDiffuseColor(getColor(color));
        stickerZ.setMaterial(materialZ);
        stickerZ.translateXProperty().set(cubeletCenterX);
        stickerZ.translateYProperty().set(cubeletCenterY);
        stickerZ.translateZProperty().set(cubeletCenterZ + 0.5 * CUBELET_SIZE * zpos);
        group.getChildren().add(stickerZ);
    }

    public Group getModel() {
        return group;
    }

    // REQUIRES: color is one of W, O, G, R, B, Y
    // EFFECTS: return the javafx color given a string color
    public Color getColor(String color) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("W", Color.WHITE);
        colorMap.put("O", Color.ORANGE);
        colorMap.put("G", Color.GREEN);
        colorMap.put("R", Color.RED);
        colorMap.put("B", Color.BLUE);
        colorMap.put("Y", Color.YELLOW);

        return colorMap.get(color);
    }

    // REQUIRES: orientation is valid
    // MODIFIES: this
    // EFFECTS: rotates the cubelet model and animates it
    public void rotate(String orientation, boolean clockwise) {
        Gui3D.setAnimating(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(6),
                e -> performRotation(orientation, clockwise)));
        timeline.setCycleCount((int) rotationRate);
        timeline.playFromStart();

        timeline.setOnFinished(e -> Gui3D.setAnimating(false));
    }

    // REQUIRES: orientation is valid
    // MODIFIES: this
    // EFFECTS: performs the rotation of the cubelet model
    private void performRotation(String orientation, boolean clockwise) {
        Rotate r;
        double angle = 90 / rotationRate;
        if (orientation.equals("D") || orientation.equals("R") || orientation.equals("B")) {
            angle = -90 / rotationRate;
        }

        if (!clockwise) {
            angle *= -1;
        }

        if (orientation.equals("U") || orientation.equals("D")) {
            r = new Rotate(angle, Rotate.Y_AXIS);
        } else if (orientation.equals("L") || orientation.equals("R")) {
            r = new Rotate(angle, Rotate.X_AXIS);
        } else {
            r = new Rotate(angle, Rotate.Z_AXIS);
        }

        if (group.getTransforms().size() > 0) {
            group.getTransforms().setAll(r.createConcatenation(group.getTransforms().get(0)));
        } else {
            group.getTransforms().add(r);
        }
    }
}
