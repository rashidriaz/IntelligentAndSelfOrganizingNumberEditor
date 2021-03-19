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
                findAndReplace();
                return false;
            case '6':
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
    ---------------------------------------------------------------------------------------------
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

    /*
    -----------------------------------------------------------------------------------------
    This method will be called when user will choose to perform simple insertion
    -----------------------------------------------------------------------------------------
     */
    private static void insert() {
        System.out.print("\nEnter a number:\t");
        int number = input.nextInt();
        input.nextLine();
        editor.insert(number);

    }

    /*
    --------------------------------------------------------------------------------------------
    The method will be called when the user will choose to insert number at some specific postion
    ---------------------------------------------------------------------------------------------
     */
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

    /*
    ------------------------------------------------------------------------------------------------
    The method will be called when the user will choose to delete one number either completely or
    only one occurrence from the array
    -----------------------------------------------------------------------------------------------
     */
    private static void delete(boolean deleteAllOccurrences) {
        System.out.print("\nEnter a number:\t");
        int number = input.nextInt();
        input.nextLine();
        if (editor.delete(number, deleteAllOccurrences, false)) {
            System.out.println("Number Deleted Successfully, Remaining numbers are: ");
            editor.printNumbers();
        } else {
            System.out.println("Number deletion failed, Something went wrong. Please try again. Maybe " + number + " is not located in the list");
        }// end if else
    }

    /*
    ------------------------------------------------------------------------------------------------
    The method will be called when the user will choose to delete every number within the given range
    -----------------------------------------------------------------------------------------------
     */
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

    /*
    -------------------------------------------------------------------------------------------------
    The functionality of this method is to perform any of the available type of deletion based on the
    user's choice.
    -------------------------------------------------------------------------------------------------
     */
    private static void deleteANumber() {
        if (editor.isEmpty()) {
            System.out.println("There's nothing to delete. The storage is empty!");
            return;
        }
        char option = printDeletionMenuAndGetSelectedOption();
        if (editor.getCounter() <= 2 && option == '3') {
            option = '.';
        }
        switch (option) {
            case '1':
                delete(false);
                break;
            case '2':
                delete(true);
                break;
            case '3':
                deleteInARange();
                break;
            case '4':
                deleteAtPosition();
                break;
            case '5':
                editor.reset();
                break;
        }
    }

    /*
    -------------------------------------------------------------------------------
    The method will get position as input from the user and then will delete the
    number present at that position
    -------------------------------------------------------------------------------
     */
    private static void deleteAtPosition() {
        System.out.print("\nEnter the position of the number:\t");
        int position = input.nextInt();
        input.nextLine();
        if (editor.deleteInARange(position, position)) {
            System.out.println("Number Deleted Successfully, Remaining numbers are: ");
            editor.printNumbers();
        } else {
            System.out.println("Number deletion failed, Something went wrong. Please try again.");
        }// end if else
    }

    /*
    -----------------------------------------------------------------------------------
    The functionality of this method is to take input from the user and then validate
    the user's choice input based on the different parameters.
    -----------------------------------------------------------------------------------
     */
    private static char getChoice() {
        char option = printMainMenuAndGetSelectedOption();
        if ((editor.isEmpty() && option != '1') || (option == '1' && editor.isFull())) {
            option = '.';
        } else if (option == '6' && (editor.getCounter() <= 3 || editor.isSorted())) {
            option = '.';
        }
        return option;
    }

    /*
    -----------------------------------------------------------------------------------
    The functionality of this method is to take the input from the user and then based
    on that call the method to print the numbers available in the memory.
    -----------------------------------------------------------------------------------
     */
    private static void viewNumbers() {
        if (editor.isEmpty()) {
            System.out.println("Storage is empty. There's nothing to delete");
        }
        char option = printViewingMenuAndGetSelectedOption();
        switch (option) {
            case '1':
                editor.printNumbers();
                break;
            case '2':
                System.out.print("\nEnter the index :\t");
                int index = input.nextInt();
                input.nextLine();
                editor.printNumberAt(index);
                break;
        }
    }

    /*
    -----------------------------------------------------------------------------------
    The functionality of this method is to take two inputs from the user one is the old
    number, the second one is the new number. the program will replace the old number with
    the given new number
    ------------------------------------------------------------------------------------
     */
    private static void findAndReplace() {
        System.out.print("\nEnter the old number:\t");
        int oldNumber = input.nextInt();
        input.nextLine();
        System.out.print("\nEnter the new number:\t");
        int newNumber = input.nextInt();
        input.nextLine();
        if (oldNumber == newNumber) {
            System.out.println("Error! Old number and the new number are same.");
            return;
        }
        boolean replaceAll = askForUsersPermission("""
                Would you like to replace all the occurrences of the number or only the first occurrence?
                 Press Y to Replace all
                N to Replace the first Occurrence
                 Enter Your choice:\t""");
        if (editor.findAndReplace(oldNumber, newNumber, replaceAll)) {
            System.out.println("Numbers replaced successfully");
        } else {
            System.out.println("Error!Something went wrong. Please try again!");
        }
    }

    private static char printMainMenuAndGetSelectedOption() {
        System.out.println(" Please select an option");
        if (!editor.isFull()) {
            System.out.println("1. Insert a number");
        }//end if
        if (!editor.isEmpty()) {
            System.out.println("2. Delete a number");
            System.out.println("3. View entered numbers.");
            System.out.println("4. Find a number");
            System.out.println("5. Find and Replace");
        }//end if
        if (editor.getCounter() > 3 && !editor.isSorted()) {
            System.out.println("6. Sort numbers");
        }//end if
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
        if (editor.getCounter() > 2) {
            System.out.println("3. Delete in a selected range");
        }
        System.out.println("4. Delete at Position");
        System.out.println("5. Delete all numbers");
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

    /*
    ----------------------------------------------------------------------------------------
    The function of this method is to prompt user and ask for their confirmation before
    performing the task if necessary
    ----------------------------------------------------------------------------------------
     */
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
