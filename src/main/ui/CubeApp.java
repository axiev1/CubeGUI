package ui;

import model.Cube;
import model.CubeHandler;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 2D ui application for cube
public class CubeApp {
    private CubeHandler cubeHandler;
    private Scanner scan;
    private static final String JSON_STORE = "./data/CubeHandler.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: starts the application
    public CubeApp() {
        cubeHandler = new CubeHandler(false);
        scan = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // EFFECTS: gets user input and runs the application until user quits
    private void runApp() {
        boolean run = true;
        String command;

        checkLoad();

        print(cubeHandler.getCube());
        while (run) {
            System.out.println("Type \"h\" for help, \"exit\" to exit");
            command = scan.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                run = false;
            } else {
                processCommand(command);
            }
        }

        checkSave();
    }

    // MODIFIES: this
    // EFFECTS: checks if there is a previous save and loads if the user agrees
    private void checkLoad() {
        File save = new File(JSON_STORE);
        if (!save.exists()) {
            return;
        }

        String command;

        System.out.println("Would you like to load the previous save? [\"y\"/\"n\"]");
        command = scan.nextLine();

        if (command.equalsIgnoreCase("y")) {
            loadCubeHandler();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads previous save
    private void checkSave() {
        String command;

        System.out.println("Would you like to save? [\"y\"/\"n\"]");
        command = scan.nextLine();

        if (command.equalsIgnoreCase("y")) {
            saveCubeHandler();
        }
    }

    // EFFECTS: process the input given by the user
    private void processCommand(String command) {
        Pattern p = Pattern.compile("^(load [\\d])");
        Matcher load = p.matcher(command);

        if (command.equalsIgnoreCase("h")) {
            printHelpMessage();
        } else if (command.equalsIgnoreCase("scramble")) {
            cubeHandler.scramble();
            print(cubeHandler.getCube());
        } else if (command.equalsIgnoreCase("scramble string")) {
            getScramble();
        } else if (command.equalsIgnoreCase("reset")) {
            cubeHandler.reset();
            print(cubeHandler.getCube());
        } else if (command.equalsIgnoreCase("save")) {
            cubeHandler.saveCube();
            System.out.println("Cube saved.");
        } else if (command.equalsIgnoreCase("view")) {
            print(cubeHandler.getCube());
        } else if (load.find()) {
            cubeHandler.loadCube(Integer.parseInt(command.substring(5)));
        } else {
            cubeHandler.parseTurn(command);
            print(cubeHandler.getCube());
        }
    }

    // EFFECTS: gets and processes a scramble string from the user
    private void getScramble() {
        System.out.println("Input your scramble string: ");
        String scrambleString = scan.nextLine();
        cubeHandler.parseScramble(scrambleString);
        print(cubeHandler.getCube());
    }

    // EFFECTS: prints out the help message
    private void printHelpMessage() {
        System.out.println("To make a move, type in the name of the face to rotate it 90 degrees clockwise:");
        System.out.println("Faces (Front, Right, Up, Left, Back, Down): F R U L B D");
        System.out.println("Add an apostrophe to the end of the move to turn counterclockwise (e.g. F', R')");
        System.out.println("Add a 2 to the end of the move to make a double turn (e.g. R2, D2')");
        System.out.println("To scramble the cube, type \"scramble\"");
        System.out.println("To input a scramble string, type \"scramble string\"");
        System.out.println("To save the current cube state, type \"save\"");
        System.out.println("To view a list of saved cubes, type \"view\"");
        System.out.println("To load a saved cube, type \"load (index here)\"");
        System.out.println("To reset the cube, type \"reset\"");
        System.out.println("To exit, type \"exit\"");
    }

    // EFFECTS: saves the cube handler to file
    private void saveCubeHandler() {
        try {
            jsonWriter.open();
            jsonWriter.write(cubeHandler);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads cube handler from file
    private void loadCubeHandler() {
        try {
            cubeHandler = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays the list of saved cubes
    public void viewSavedCubes() {
        for (int i = 0; i < cubeHandler.getSavedCubes().size(); i++) {
            System.out.println(i + 1 + ". ");
            print(cubeHandler.getSavedCubes().get(i));
            System.out.println();
        }
    }

    // EFFECTS: prints out a given cube
    public void print(Cube cube) {
        printCube(cube);
    }

    // EFFECTS: prints out the cube to the console
    private void printCube(Cube cube) {
        String[][] faceU = cube.getFace("U").to2DStringArray();
        String[][] faceL = cube.getFace("L").to2DStringArray();
        String[][] faceF = cube.getFace("F").to2DStringArray();
        String[][] faceR = cube.getFace("R").to2DStringArray();
        String[][] faceB = cube.getFace("B").to2DStringArray();
        String[][] faceD = cube.getFace("D").to2DStringArray();

        String[][] middle = new String[3][12];

        for (int y = 0; y < 3; y++) {
            String[] combined = Arrays.copyOf(faceL[y], 12);
            System.arraycopy(faceF[y], 0, combined, 3, 3);
            System.arraycopy(faceR[y], 0, combined, 6, 3);
            System.arraycopy(faceB[y], 0, combined, 9, 3);
            middle[y] = combined;
        }

        printFace(faceU, true);
        printFace(middle, false);
        printFace(faceD, true);
    }

    // EFFECTS: prints formatted 2D string array
    private void printFace(String[][] faceToPrint, boolean padding) {

        String row0 = "| " + String.join(" | ", faceToPrint[0]) + " |";
        String row1 = "| " + String.join(" | ", faceToPrint[1]) + " |";
        String row2 = "| " + String.join(" | ", faceToPrint[2]) + " |";
        String dashSeparator = "-".repeat(row0.length());

        if (padding) {
            String whitespace = " ".repeat(row0.length() - 1);
            row0 = whitespace + row0;
            row1 = whitespace + row1;
            row2 = whitespace + row2;
            dashSeparator = whitespace + dashSeparator;
        }

        System.out.println(dashSeparator);
        System.out.println(row0);
        System.out.println(dashSeparator);
        System.out.println(row1);
        System.out.println(dashSeparator);
        System.out.println(row2);
        System.out.println(dashSeparator);
    }
}
