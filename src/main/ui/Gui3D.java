package ui;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import model.CubeHandler;
import model.Cubelet;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// 3D GUI with JavaFX and Swing.
// Learned JavaFX syntax from https://www.youtube.com/playlist?list=PLhs1urmduZ295Ryetga7CNOqDymN_rhB_
// Learned Swing syntax from https://docs.oracle.com/javase/tutorial/uiswing/
public class Gui3D extends JFrame {
    private static final int WIDTH = 1480;
    private static final int HEIGHT = 850;
    private static double anchorX;
    private static double anchorY;
    private static double anchorAngleX = 0;
    private static double anchorAngleY = 0;
    private static final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private static final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private static CubeHandler cubeHandler;
    private static Group group;
    private static final String JSON_STORE = "./data/CubeHandler.json";
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;
    private static boolean animating;
    private static boolean shiftPressed;

    // MODIFIES: this
    // EFFECTS: shows GUI interface
    private static void initAndShowGUI() {
        JFrame frame = new JFrame("Rubik's Cube");
        final JFXPanel panel = new JFXPanel();
        frame.add(panel);

        checkLoad();
        createButtons(panel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);

        initWindowResize(frame);
        initWindowClose(frame);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                panel.setScene(initJavaFX());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: moves cube to center on window resize
    private static void initWindowResize(JFrame frame) {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                group.translateXProperty().set(frame.getBounds().width / 2.0);
                group.translateYProperty().set(frame.getBounds().height / 2.0);
                super.componentResized(e);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: prompt to save game on window close
    private static void initWindowClose(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to save?",
                        "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    saveCubeHandler();
                    printEventLog();
                    System.exit(0);
                } else if (choice == JOptionPane.NO_OPTION) {
                    printEventLog();
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: starts javaFX 3D model
    private static Scene initJavaFX() {
        group = new Group();

        group.translateXProperty().set(WIDTH / 2.0);
        group.translateYProperty().set(HEIGHT / 2.0);
        group.translateZProperty().set(-1000);

        if (cubeHandler == null) {
            cubeHandler = new CubeHandler(true);
        }

        createNewCubeModel();

        Scene scene = new Scene(group, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        Camera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        initMouseControl(scene);
        initKeyboardControl(scene);
        return scene;
    }

    // MODIFIES: this
    // EFFECTS: creates the 3D model of the cube
    private static void createNewCubeModel() {
        group.getChildren().clear();
        for (Cubelet c : cubeHandler.getCube().getCenterCubelets()) {
            group.getChildren().add(c.getCubeletModel().getModel());
        }
        for (Cubelet c : cubeHandler.getCube().getEdgeCubelets()) {
            group.getChildren().add(c.getCubeletModel().getModel());
        }
        for (Cubelet c : cubeHandler.getCube().getCornerCubelets()) {
            group.getChildren().add(c.getCubeletModel().getModel());
        }
    }

    // MODIFIES: this
    // EFFECTS: enables the user to rotate the cube with the mouse
    private static void initMouseControl(Scene scene) {
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

    // MODIFIES: this
    // EFFECTS: enables the user to make moves with the keyboard
    private static void initKeyboardControl(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            boolean turn = e.getCode().equals(KeyCode.U) || e.getCode().equals(KeyCode.L)
                    || e.getCode().equals(KeyCode.F) || e.getCode().equals(KeyCode.R)
                    || e.getCode().equals(KeyCode.B) || e.getCode().equals(KeyCode.D);

            if (turn) {
                if (isAnimating()) {
                    return;
                }
                String instruction = e.getText();
                if (shiftPressed) {
                    instruction += "'";
                    System.out.println("lol");
                }
                cubeHandler.parseTurn(instruction);
            } else if (e.getCode().equals(KeyCode.SHIFT)) {
                shiftPressed = true;
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode().equals(KeyCode.SHIFT)) {
                shiftPressed = false;
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons
    private static void createButtons(JFXPanel panel) {
        createScrambleButton(panel);
        createResetButton(panel);
        createSaveAndLoadButton(panel);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to scramble
    private static void createScrambleButton(JFXPanel panel) {
        JButton button = new JButton("Scramble");
        button.setBounds(100, 100, 100, 50);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cubeHandler.scramble();
                panel.requestFocus();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        createNewCubeModel();
                    }
                });
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the button to reset the cube
    private static void createResetButton(JFXPanel panel) {
        JButton button = new JButton("Reset");
        button.setBounds(100, 200, 100, 50);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cubeHandler.reset();
                panel.requestFocus();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        createNewCubeModel();
                    }
                });
            }
        });
    }

    // MODIFIES: this
    // REQUIRES: creates the button to save and load the cube
    private static void createSaveAndLoadButton(JFXPanel panel) {
        JButton button = new JButton("Save");
        button.setBounds(100, 300, 100, 50);
        panel.add(button);

        JComboBox comboBox = new JComboBox();

        if (cubeHandler != null) {
            for (int i = 0; i < cubeHandler.getSavedCubes().size(); i++) {
                comboBox.addItem(Integer.toString(i + 1));
            }
        }

        JScrollPane jscrollPane = new JScrollPane(comboBox);
        jscrollPane.setBounds(100, 400, 100, 50);
        panel.add(jscrollPane);

        createLoadButton(panel, comboBox);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cubeHandler.saveCube();
                comboBox.addItem(Integer.toString(cubeHandler.getSavedCubes().size()));
                panel.requestFocus();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the button to load the cube
    private static void createLoadButton(JFXPanel panel, JComboBox comboBox) {
        JButton button = new JButton("Load");
        button.setBounds(100, 500, 100, 50);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getItemCount() > 0) {
                    cubeHandler.loadCube(comboBox.getSelectedIndex() + 1);
                }
                panel.requestFocus();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        createNewCubeModel();
                    }
                });
            }
        });
    }

    // EFFECTS: saves cubehandler to JSON file
    private static void saveCubeHandler() {
        try {
            jsonWriter.open();
            jsonWriter.write(cubeHandler);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: check if JSON save exists and prompts the user if they want to restore it
    private static void checkLoad() {
        File save = new File(JSON_STORE);
        if (!save.exists()) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(null, "Do you want to load the previous save?",
                "Load?", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            loadCubeHandler();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads cube handler from file
    private static void loadCubeHandler() {
        try {
            cubeHandler = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public static boolean isAnimating() {
        return animating;
    }

    public static void setAnimating(boolean animating) {
        Gui3D.animating = animating;
    }

    private static void printEventLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: starts the GUI
    public static void start(String[] args) {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}
