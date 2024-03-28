package ui;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Cube;
import model.Cubelet;
import model.Face;
import javafx.scene.Group;

import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class Test extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private static final double CENTER_X = WIDTH / 2.0;
    private static final double CENTER_Y = HEIGHT / 2.0;
    private static final int CUBELET_SIZE = 50;

    private CubeHandler cubeHandler = new CubeHandler();

    private double anchorX;
    private double anchorY;a
    private double anchorAngleX = CENTER_X;
    private double anchorAngleY = CENTER_Y;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<Box> cubelets = initalizeCubelets();

        SmartGroup group = new SmartGroup();
        group.getChildren().addAll(cubelets);
//        group.translateXProperty().set(CENTER_X);
//        group.translateYProperty().set(CENTER_Y);

        Camera camera = new PerspectiveCamera();
//        camera.setTranslateX(-110);
//        camera.setTranslateY(-200);
//        camera.setTranslateZ(-350);


        Scene scene = new Scene(group, WIDTH, HEIGHT, true);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        initMouseControl(group, scene);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    //box.translateZProperty().set(box.getTranslateZ() + 10);
                    break;
                case S:
                    //box.translateZProperty().set(box.getTranslateZ() - 10);
                    break;
            }
        });

        primaryStage.setTitle("test");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void initMouseControl(SmartGroup group, Scene scene) {
        Rotate xrotate;
        Rotate yrotate;
        group.getTransforms().addAll(
                xrotate = new Rotate(0, Rotate.X_AXIS),
                yrotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xrotate.angleProperty().bind(angleX);
        yrotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<Box> initalizeCubelets() {
        ArrayList<Box> boxes = new ArrayList<>();
        Cube cube = cubeHandler.getCube();
        boxes = getBoxesFromFace(cube.getFace("U"));
        boxes.addAll(getBoxesFromFace(cube.getFace("L")));
        boxes.addAll(getBoxesFromFace(cube.getFace("F")));
        boxes.addAll(getBoxesFromFace(cube.getFace("R")));
        boxes.addAll(getBoxesFromFace(cube.getFace("B")));
        boxes.addAll(getBoxesFromFace(cube.getFace("D")));
        return boxes;
    }

    private ArrayList<Box> getBoxesFromFace(Face f) {
        ArrayList<Box> boxes = new ArrayList<>();

        PhongMaterial material = new PhongMaterial();

        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("W", Color.WHITE);
        colorMap.put("O", Color.ORANGE);
        colorMap.put("G", Color.GREEN);
        colorMap.put("R", Color.RED);
        colorMap.put("B", Color.BLUE);
        colorMap.put("Y", Color.YELLOW);

        for (Cubelet[] row : f.getOrderedCubelets()) {
            for (Cubelet c : row) {
                Box box;
                switch (f.getOrientation()) {
                    case "U":
                    case "D":
                        box = new Box(CUBELET_SIZE, 1, CUBELET_SIZE);
                        box.translateXProperty().set(CENTER_X + CUBELET_SIZE * c.getPos().getX());
                        box.translateZProperty().set(CENTER_Y + CUBELET_SIZE * c.getPos().getY());
                        box.translateYProperty().set(1.5 * CUBELET_SIZE * c.getPos().getZ());

                        material.setDiffuseColor(colorMap.get(c.getColorZ()));
                        System.out.println(colorMap.get(c.getColorZ()));
                        break;
                    case "L":
                    case "R":
                        box = new Box(1, CUBELET_SIZE, CUBELET_SIZE);
                        box.translateXProperty().set(CENTER_X + 1.5 * CUBELET_SIZE * c.getPos().getX());
                        box.translateZProperty().set(CENTER_Y + CUBELET_SIZE * c.getPos().getY());
                        box.translateYProperty().set(CUBELET_SIZE * c.getPos().getZ());

                        material.setDiffuseColor(colorMap.get(c.getColorX()));
                        System.out.println(colorMap.get(c.getColorX()));
                        break;
                    default:
                        box = new Box(CUBELET_SIZE, CUBELET_SIZE, 1);
                        box.translateXProperty().set(CENTER_X + CUBELET_SIZE * c.getPos().getX());
                        box.translateZProperty().set(CENTER_Y + 1.5 * CUBELET_SIZE * c.getPos().getY());
                        box.translateYProperty().set(CUBELET_SIZE * c.getPos().getZ());

                        material.setDiffuseColor(colorMap.get(c.getColorY()));
                        System.out.println(colorMap.get(c.getColorY()));
                }
                box.setMaterial(material);
                boxes.add(box);
            }
        }
        return boxes;
    }
}
