package intelligent.number.editor;

import java.util.Scanner;

public class IOStream {
    private static final Scanner input = new Scanner(System.in);

    public static double getDouble(String message) {
        System.out.print("\n Please Enter " + message + " number:\t ");
        while (!input.hasNextDouble()) {
            System.out.println("Error!! You have entered an invalid number. Please Try Again");
            System.out.print("\n Please Enter a number:\t ");
            input.next();
        }
        double number = input.nextDouble();
        input.nextLine();
        return number;
    }

    public static int getInteger(String message) {
        System.out.print("\n Please Enter " + message + " number:\t ");
        while (!input.hasNextInt()) {
            System.out.println("\n\t\tError! Invalid number entered. Please Try Again!\n");
            System.out.print("\n Please Enter a number:\t ");
            input.next();
        }
        int number = input.nextInt();
        input.nextLine();
        return number;
    }

    public static char getCharacter() {
        return input.next().charAt(0);
    }

    public static void displayMenu(boolean memoryIsFull) {
        System.out.println("Please choose one of the following options: ");
        if (!memoryIsFull) {
            System.out.println("\t1. Evaluate an expression");
        }
        System.out.println("\t2. Go to previous record.");
        System.out.println("\t\tPress Any Other key to exit");
        System.out.print("\n\tPlease Enter your choice:\t");
    }

//    public static void displayResults(MathematicalExpression expression, double result) {
//        System.out.print("\n\n " + expression.firstNumber + "  " + expression.operator +
//                "  " + expression.secondNumber + "\t=\t" + result + "\n\n");
//    }

//    public static boolean askForUsersPermission(String message) {
//        while (true) {
//            System.out.print("\n" + message + ":\t");
//            char decision = input.next().toLowerCase().charAt(0);
//            if (decision == 'y') {
//                return true;
//            } else if (decision == 'n') {
//                return false;
//            } else {
//                System.out.println("\nError!! Wrong option chosen. Please try again\n");
//            }
//        }
//    }
}

