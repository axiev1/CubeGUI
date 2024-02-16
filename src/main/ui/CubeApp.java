package ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CubeApp {
    private CubeHandler cubeHandler;
    private Scanner scan;

    public CubeApp() {
        cubeHandler = new CubeHandler();
        scan = new Scanner(System.in);
        runApp();
    }

    private void runApp() {
        boolean run = true;
        String command;

        cubeHandler.print();
        while (run) {
            System.out.println("Type \"h\" for help, \"exit\" to exit");
            command = scan.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                run = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void processCommand(String command) {
        Pattern p = Pattern.compile("^(load [\\d])");
        Matcher load = p.matcher(command);

        if (command.equalsIgnoreCase("h")) {
            printHelpMessage();
        } else if (command.equalsIgnoreCase("scramble")) {
            cubeHandler.scramble();
            cubeHandler.print();
        } else if (command.equalsIgnoreCase("scramble string")) {
            getScramble(scan, cubeHandler);
        } else if (command.equalsIgnoreCase("reset")) {
            cubeHandler.reset();
            cubeHandler.print();
        } else if (command.equalsIgnoreCase("save")) {
            cubeHandler.saveCube();
        } else if (command.equalsIgnoreCase("view")) {
            cubeHandler.viewSavedCubes();
        } else if (load.find()) {
            cubeHandler.loadCube(Integer.parseInt(command.substring(5)));
        } else {
            cubeHandler.parseTurn(command);
            cubeHandler.print();
        }
    }

    private void getScramble(Scanner scan, CubeHandler cubeHandler) {
        System.out.println("Input your scramble string: ");
        String scrambleString = scan.nextLine();
        cubeHandler.parseScramble(scrambleString);
        cubeHandler.print();
    }

    private void printHelpMessage() {
        System.out.println("To make a move, type in the name of the face to rotate it 90 degrees clockwise:");
        System.out.println("Faces (Front, Right, Up, Left, Back, Down): F R U L B D");
        System.out.println("Add an apostrophe to the end of the move to turn counterclockwise (e.g. F', R')");
        System.out.println("Add a 2 to the end of the move to make a double turn (e.g. R2, D2')");
        System.out.println("To scramble the cube, type \"scramble\"");
        System.out.println("To input a scramble string, type \"scramble string\"");
        System.out.println("To reset the cube, type \"reset\"");
        System.out.println("To exit, type \"exit\"");
    }
}
