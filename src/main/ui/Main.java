package ui;

import model.Cube;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CubeHandler cubeHandler = new CubeHandler();
        Scanner scan = new Scanner(System.in);

        boolean run = true;
        cubeHandler.print();
        while (run) {
            System.out.println("Type \"h\" for help, \"exit\" to exit");
            run = getUserInput(scan, cubeHandler);
        }
    }

    public static boolean getUserInput(Scanner scan, CubeHandler cubeHandler) {
        String input = scan.nextLine();
        if (input.equalsIgnoreCase("h")) {
            printHelpMessage();
        } else if (input.equalsIgnoreCase("exit")) {
            return false;
        } else if (input.equalsIgnoreCase("scramble")) {
            cubeHandler.scramble();
            cubeHandler.print();
        } else if (input.equalsIgnoreCase("scramble string")) {
            getScramble(scan, cubeHandler);
        } else if (input.equalsIgnoreCase("reset")) {
            cubeHandler.reset();
            cubeHandler.print();
        } else {
            cubeHandler.parseTurn(input);
            cubeHandler.print();
        }
        return true;
    }

    public static void getScramble(Scanner scan, CubeHandler cubeHandler) {
        System.out.println("Input your scramble string: ");
        String scrambleString = scan.nextLine();
        cubeHandler.parseScramble(scrambleString);
        cubeHandler.print();
    }

    public static void printHelpMessage() {
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
