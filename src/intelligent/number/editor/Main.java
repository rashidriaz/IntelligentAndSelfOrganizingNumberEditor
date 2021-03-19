package intelligent.number.editor;

import java.util.Scanner;

public class Main {
    private static final Editor editor = new Editor();
    private static final Scanner input = new Scanner(System.in);

    /*
    ---------------------------------------------------------------------------------------------
    ---------------------------------------------------------------------------------------------
    --------------------------------------MAIN METHOD--------------------------------------------
    ---------------------------------------------------------------------------------------------
    ---------------------------------------------------------------------------------------------
     */
    public static void main(String[] args) {
        boolean exit;
        System.out.println("\"Welcome to self intelligent and self organizing number editor.\"");
        do {
            char option = getChoice();
            exit = performTask(option);
        } while (!exit);//end do-while loop
        System.out.println("Program ended Successfully");
    }

    /*
    ----------------------------------------------------------------------------------------------
    The function of this method is to perform the task based on the provided choice chosen by the
    user
    ----------------------------------------------------------------------------------------------
     */
    private static boolean performTask(char option) {
        switch (option) {
            case '1':
                insertANumber();
                return false;
            case '2':
                deleteANumber();
                return false;
            case '3':
                viewNumbers();
                return false;
            case '4':
                findNumber();
                return false;
            case '5':
                sort();
                return false;
        }
        return true;
    }

    /*
    ----------------------------------------------------------------------------------------------
    The function of this method is to print a menu and ask user what type of sorting they want, and
    based on the choice of the user, the method calls another method from the editor class which will
    perform the sorting.
    ------------------------------------------------------------------------------------------------
     */
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
        }//end if else
        System.out.println("Invalid option selected. Please try again");
    }

    /*
    --------------------------------------------------------------------------------------------
    This method takes a number as an input from the user passes it to the method in editor class
    which then traverses the array to find the position of that number in the array. This function
    prints the position of the given number on the console as the result.
    ---------------------------------------------------------------------------------------------
     */
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

    /*
    ----------------------------------------------------------------------------------------------
    The functionality of this method is to perform the insertion based on the choice of the user
    whether the user wants to make simple insertion or user wants to insert the number at the
    specific position
     */
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
        if (editor.delete(number, false)) {
            System.out.println("Number Deleted Successfully, Remaining numbers are: ");
            editor.printNumbers();
        } else {
            System.out.println("Number deletion failed, Something went wrong. Please try again. Maybe " + number + " is not located in the list");
        }// end if else
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
        if (editor.deleteInARange(start, end)) {
            System.out.println("Number Deleted Successfully, Remaining numbers are: ");
            editor.printNumbers();
        } else {
            System.out.println("Number deletion failed, Something went wrong. Please try again.");
        }//end if else
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
            case '4' -> editor.reset();
        }
    }

    private static char getChoice() {
        char option = printMainMenuAndGetSelectedOption();
        if ((editor.isEmpty() && option != '1') || (option == '1' && editor.isFull())) {
            option = '.';
        } else if (option == '5' && (editor.getCounter() <= 3 || editor.isSorted())) {
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
        System.out.println("4. Delete all numbers");
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


    public static boolean askForUsersPermission(String message) {
        while (true) {
            System.out.print("\n" + message + ":\t");
            char decision = input.next().toLowerCase().charAt(0);
            if (decision == 'y') {
                return true;
            } else if (decision == 'n') {
                return false;
            } else {
                System.out.println("\nError!! Wrong option chosen. Please try again\n");
            }
        }
    }
}
