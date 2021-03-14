package intelligent.number.editor;

import java.util.Scanner;

public class Main {
    private static final Editor editor = new Editor();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit;
        System.out.println("\"Welcome to self intelligent and self organizing number editor.\"");
        do {
            char option = getChoice();
            exit = performTask(option);
        } while (!exit);
        System.out.println("Program ended Successfully");
    }

    private static boolean performTask(char option) {
        switch (option) {
            case '1' -> insertANumber();
            case '2' -> deleteANumber();
            case '3' -> viewNumbers();
            case '4' -> findNumber();
            case '5' -> sort();
            default -> {
                return true;
            }
        }
        return false;
    }

    private static void sort() {
        System.out.println("Would you like to sort the numbers in:");
        System.out.println("1. Ascending Order");
        System.out.println("2. Descending Order");
        System.out.print("\nEnter your choice:\t");
        char option = input.next().charAt(0);
        if (option == '1') {
            editor.sort(true);
            return;
        } else if (option == '2') {
            editor.sort(false);
            return;
        }
        System.out.println("Invalid option selected. Please try again");
    }

    private static void findNumber() {
        System.out.print("\nEnter a number you want to search:\t");
        int number = input.nextInt();
        input.nextLine();
        int index = editor.findNumber(number);
        if (index < 0) {
            System.out.println("Number not found");
        } else {
            System.out.println("Number: \"" + number + "\" is at index \"" + index + "\"");
        }
    }

    private static void insertANumber() {
        if (editor.isFull()) {
            System.out.println("Storage is full cannot add more numbers");
            return;
        }
        char option = printInsertionMenuAndGetSelectedOption();
        switch (option) {
            case '1' -> insert();
            case '2' -> insertAtPosition();
        }
    }

    private static void insert() {
        System.out.print("\nEnter a number:\t");
        int number = input.nextInt();
        input.nextLine();
        editor.insert(number);

    }

    private static void insertAtPosition() {
        System.out.print("\nEnter a number:\t");
        int number = input.nextInt();
        input.nextLine();
        System.out.print("\nPlease enter a position where would you like to place the number:\t");
        int position = input.nextInt();
        input.nextLine();
        if (!editor.insertAt(number, position)) {
            System.out.println("Something went wrong. Number failed to get stored.");
        }
    }

    private static void deleteOneOccurrence() {
        System.out.print("\nEnter a number:\t");
        int number = input.nextInt();
        input.nextLine();
        editor.delete(number, false);
    }

    private static void deleteAllOccurrences() {
        System.out.print("\nEnter a number:\t");
        int number = input.nextInt();
        input.nextLine();
        editor.delete(number, true);
    }

    private static void deleteInARange() {
        System.out.print("Enter starting point: \t");
        int start = input.nextInt();
        input.nextLine();
        System.out.print("Enter ending point: \t");
        int end = input.nextInt();
        input.nextLine();
        editor.deleteInARange(start, end);
    }

    private static void deleteANumber() {
        if (editor.isEmpty()) {
            System.out.println("There's nothing to delete. The storage is empty!");
            return;
        }
        char option = printDeletionMenuAndGetSelectedOption();
        switch (option) {
            case '1' -> deleteOneOccurrence();
            case '2' -> deleteAllOccurrences();
            case '3' -> deleteInARange();
        }
    }

    private static char getChoice() {
        char option = printMainMenuAndGetSelectedOption();
        if (((editor.isEmpty() || editor.getCounter() <= 3) && option != '1') ||
                (option == '1' && editor.isFull())) {
            if (!editor.isEmpty() && option == '3') {
                return option;
            }
            option = '.';
        }
        return option;
    }


    private static void viewNumbers() {
        if (editor.isEmpty()) {
            System.out.println("Storage is empty. There's nothing to delete");
        }
        char option = printViewingMenuAndGetSelectedOption();
        switch (option) {
            case '1' -> editor.printNumbers();
            case '2' -> {
                System.out.print("\nEnter the index :\t");
                int index = input.nextInt();
                input.nextLine();
                editor.printNumberAt(index);
            }
        }
    }

    private static char printMainMenuAndGetSelectedOption() {
        System.out.println(" Please select an option");
        if (!editor.isFull()) {
            System.out.println("1. Insert a number");
        }
        if (!editor.isEmpty()) {
            System.out.println("2. Delete a number");
            System.out.println("3. View entered numbers.");
            System.out.println("4. Find a number");
        }
        if (editor.getCounter() > 3 && !editor.isSorted()) {
            System.out.println("5. Sort numbers");
        }
        System.out.println("Press any other key to exit");
        System.out.print("\n Enter your choice:\t");
        return input.next().charAt(0);
    }

    private static char printInsertionMenuAndGetSelectedOption() {
        System.out.println("Choose an option:");
        System.out.println("1. insert a number");
        System.out.println("2. insert at specific position");
        System.out.println("Press any other key to go back");
        System.out.print("\n Enter your choice:\t");
        return input.next().charAt(0);
    }

    private static char printDeletionMenuAndGetSelectedOption() {
        System.out.println("Choose an option:");
        System.out.println("1. Delete a number");
        System.out.println("2. Delete from everywhere");
        System.out.println("3. Delete in a selected range");
        System.out.println("Press any other key to go back");
        System.out.print("\n Enter your choice:\t");
        return input.next().charAt(0);
    }

    private static char printViewingMenuAndGetSelectedOption() {
        System.out.println("Choose an option:");
        System.out.println("1. View All numbers");
        System.out.println("2. View at specific position");
        System.out.println("Press any other key to go back");
        System.out.print("\n Enter your choice:\t");
        return input.next().charAt(0);
    }
}
