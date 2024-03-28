package ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import model.Position;


import java.util.HashMap;
import java.util.Map;

public class CubeletModel {
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private static final double CENTER_X = WIDTH / 2.0;
    private static final double CENTER_Y = HEIGHT / 2.0;
    private static final int CUBELET_SIZE = 50;
    private static final int STICKER_SIZE = CUBELET_SIZE - 6;
    private Group group;

    public CubeletModel(String colorX, String colorY, String colorZ, Position pos) {
        double cubeletCenterX = CUBELET_SIZE * pos.getX();
        double cubeletCenterY = -1 * CUBELET_SIZE * pos.getZ();
        double cubeletCenterZ = CUBELET_SIZE * pos.getY();

        Box box = new Box(CUBELET_SIZE, CUBELET_SIZE, CUBELET_SIZE);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLACK);
        box.setMaterial(material);

        group = new Group();
        group.getChildren().add(box);

        if (colorX != null) {
            Box stickerX = new Box(1, STICKER_SIZE, STICKER_SIZE);
            PhongMaterial materialX = new PhongMaterial();
            materialX.setDiffuseColor(getColor(colorX));
            stickerX.setMaterial(materialX);
            stickerX.translateXProperty().set(cubeletCenterX + 0.5 * CUBELET_SIZE * pos.getX());
            stickerX.translateYProperty().set(cubeletCenterY);
            stickerX.translateZProperty().set(cubeletCenterZ);
            group.getChildren().add(stickerX);
        }

        if (colorZ != null) {
            Box stickerY = new Box(STICKER_SIZE, 1, STICKER_SIZE);
            PhongMaterial materialY = new PhongMaterial();
            materialY.setDiffuseColor(getColor(colorZ));
            stickerY.setMaterial(materialY);
            stickerY.translateXProperty().set(cubeletCenterX);
            stickerY.translateYProperty().set(cubeletCenterY - 0.5 * CUBELET_SIZE * pos.getZ());
            stickerY.translateZProperty().set(cubeletCenterZ);
            group.getChildren().add(stickerY);
        }

        if (colorY != null) {
            Box stickerZ = new Box(STICKER_SIZE, STICKER_SIZE, 1);
            PhongMaterial materialZ = new PhongMaterial();
            materialZ.setDiffuseColor(getColor(colorY));
            stickerZ.setMaterial(materialZ);
            stickerZ.translateXProperty().set(cubeletCenterX);
            stickerZ.translateYProperty().set(cubeletCenterY);
            stickerZ.translateZProperty().set(cubeletCenterZ + 0.5 * CUBELET_SIZE * pos.getY());
            group.getChildren().add(stickerZ);
        }

        box.translateXProperty().set(cubeletCenterX);
        box.translateYProperty().set(cubeletCenterY);
        box.translateZProperty().set(cubeletCenterZ);
    }

    public Group getModel() {
        return group;
    }

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

    public void rotate(String orientation) {
        Rotate r;
        Transform t = new Rotate();
        int angle = 90;
        if (orientation.equals("D") || orientation.equals("R") || orientation.equals("B")) {
            angle = -90;
        }

        if (orientation.equals("U") || orientation.equals("D")) {
            r = new Rotate(angle, Rotate.Y_AXIS);
        } else if (orientation.equals("L") || orientation.equals("R")) {
            r = new Rotate(angle, Rotate.X_AXIS);
        } else {
            r = new Rotate(angle, Rotate.Z_AXIS);
        }

        System.out.println(group.getTransforms().size());
        if (group.getTransforms().size() > 0) {
            group.getTransforms().setAll(r.createConcatenation(group.getTransforms().get(0)));
        } else {
            group.getTransforms().add(r);
        }
    }
}
