package intelligent.number.editor;

import java.util.Scanner;

public class Editor {
    private final int ARRAY_SIZE = 25;
    private final int[] array;
    private int counter;
    private boolean isInAscendingOrder;
    private boolean isInDescendingOrder;

    public Editor() {
        this.array = new int[ARRAY_SIZE];
        this.counter = 0;
        this.isInAscendingOrder = true;
        this.isInDescendingOrder = true;
    }

    /*
    This function inserts a new number into the array if the counter is less than 3.
    In case if the counter is greater than 3 this method calls a private method which
     first checks the order of array and based on that, makes decision about the position
     of the newly entered number.
     */
    public void insert(int number) {
        if (counter == 0) {
            insertAtTheEnd(number);
            return;
        }//end if
        if (counter == 1) {
            isInAscendingOrder = isInAscendingOrder(number);
            isInDescendingOrder = isInDescendingOrder(number);
            insertAtTheEnd(number);
            return;
        }//end if
        checkOrderAndInsert(number);
    }// end Insert

    /*
    This function takes a number as an argument, and checks whether the array will remain sorted with the
    insertion of the new number. and based on that it asks user to choose whether he/she wants to maintain
    the order or not. Based on the user's decision, the program will proceed.
     */
    private void checkOrderAndInsert(int number) {
        boolean disturbTheOrder = false;
        if ((isInAscendingOrder && isInAscendingOrder(number)) ||
                (isInDescendingOrder && isInDescendingOrder(number))) {
            insertAtTheEnd(number);
            return;
        }//end if
        if (isSorted()) {
            disturbTheOrder = IOStream.askForUsersPermission("Inserting this number might affect the order.\n" +
                    " Would you still like to proceed? (y-yes, N- No)");
        }//endIf
        if (!disturbTheOrder && isSorted()) {
            insertAndSort(number);
            return;
        }//end if
        array[counter++] = number;
        isInAscendingOrder = false;
        isInDescendingOrder = false;
    }//end check order And insert method

    /*
    The functionality of this method is to take two numbers as arguments first a number to be inserted,
    and second is the position where the number is to be positioned. First this method validated the position.
    if the position is valid then based on the order of the array, this methods places the number
    at the suitable place
     */
    public boolean insertAt(int number, int position) {
        if (position <= 0) {
            return false;
        }//end if
        if (position >= counter) {
            insert(number);
            return true;
        }//end if
        if (isSorted()) {
            if ((array[position - 1] <= number && array[position] >= number) ||
                    (array[position - 1] >= number && array[position] <= number)) {
                placeNumberAndMoveElementsToRight(number, position);
            }
            System.out.println("Inserting this number might affect " +
                    "the order.\n" +
                    " Would you still like to proceed? (y-yes, N- No)");
            char result = new Scanner(System.in).next().toLowerCase().charAt(0);
            boolean disturbTheOrder = false;
            if (result == 'y') {
                disturbTheOrder = true;
            }
            if (!disturbTheOrder) {
                insertAndSort(number);
                return true;
            }//end If
        }// end if
        int temp = array[position - 1];
        array[position - 1] = number;
        array[counter++] = temp;
        return true;
    }

    /*
    This function puts the given number on the given index and moves the rest of the array to the right
     */
    private void placeNumberAndMoveElementsToRight(int number, int position) {
        for (int i = counter; i >= position; i--) {
            array[i] = array[i - 1];
        }
        array[position - 1] = number;
    }

    /*
    This function inserts the number at the end of the array
     */
    private void insertAtTheEnd(int number) {
        array[counter++] = number;
    }//end insert at the end

    /*
    This function first inserts the number into the array and then sorts it into the desired order
     */
    private void insertAndSort(int number) {
        array[counter++] = number;
        Sort.sort(array, 0, counter - 1, isInAscendingOrder);
    }//end insert and sort

    /*
    the functionality of this method is to delete a number from the array. this method first checks whether the
    array is sorted or not and based on that, decides that from where it should start traversing the array and
    at which point it should stop.
    this method returns true if it finds and deletes the given number else it returns false
     */
    public boolean delete(int number, boolean deleteAll) {
        int start = getStartingIndex(number), end = getEndingIndex(number);
        return deleteNumber(number, start, end, deleteAll);
    }

    public boolean deleteInARange(int start, int end) {
        if ((start <= 0 || start >= counter) || end <= 0 || end < start) {
            return false;
        }//end if
        if (end >= counter) {
            counter = start - 1;
            return true;
        }//end if
        start--;
        for (int i = end; i < counter; i++) {
            array[start++] = array[i];
        }
        counter = start;
        return true;
    }

    /*
    This private method takes a number, start and end as parameters. the main function of this method is to
    traverse the array from the start to end and find the desired number and delete it from the array.
     */
    private boolean deleteNumber(int number, int start, int end, boolean deleteAll) {
        boolean numberDeleted = false;
        for (int i = start; i <= end; i++) {
            if (array[i] == number) {
                if (isSorted()) {
                    moveElementsToLeft(i, counter);
                    counter--;
                } else {
                    array[i] = array[counter--];
                }//end if/else
                numberDeleted = true;
                if (!deleteAll) {
                    break;
                }//end if
            }//end if
        }//end for loop
        return numberDeleted;
    }

    /*
    The function of this method is to move the elements of the array one position to the left
     */
    private void moveElementsToLeft(int start, int end) {
        for (int i = start; i < end; i++) {
            array[i] = array[i + 1];
        }
    }

    /*
    This methods check and tells whether the array is empty or not
     */
    public boolean isEmpty() {
        return counter == 0;
    }

    /*
       This methods check and tells whether the array is full or not
    */
    public boolean isFull() {
        return counter == ARRAY_SIZE;
    }

    /*
   This methods check and tells whether the array will remain in ascending order with the given number or not
    */
    private boolean isInAscendingOrder(int number) {
        return array[counter - 1] <= number;
    }

    /*
   This methods check and tells whether the array will remain in Descending order with the given number or not
    */
    private boolean isInDescendingOrder(int number) {
        return array[counter - 1] >= number;
    }

    /*
           This methods check and tells whether the array is in ascending order or not
    */
    public boolean isInAscendingOrder() {
        return isInAscendingOrder;
    }

    /*
    This method allows user to sort the array in either ascending or descending order
     */
    public void sort(boolean inAscendingOrder) {
        if ((isInAscendingOrder && inAscendingOrder) || (isInDescendingOrder && !inAscendingOrder)) {
            return;
        }
        Sort.sort(array, 0, counter - 1, inAscendingOrder);
    }

    /*
       This methods check and tells whether the array is in descending or not
    */
    public boolean isInDescendingOrder() {
        return isInDescendingOrder;
    }

    /*
This method return the index of array from which the array should be traversed to find the desired number
 */
    private int getStartingIndex(int number) {
        int start = 0;
        if (isSorted()) {
            if (array[counter / 2] < number && isInAscendingOrder) {
                start = counter / 2;
            } else if (array[counter / 2] > number && isInDescendingOrder) {
                start = counter / 2;
            }// end if/else
        }//end if
        return start;
    }

    /*
    This method return the index of array upto which the array should be traversed to find the desired number
     */
    private int getEndingIndex(int number) {
        int end = counter;
        if (isSorted()) {
            if (array[counter / 2] < number && isInDescendingOrder) {
                end = counter / 2;
            } else if (array[counter / 2] < number && isInAscendingOrder) {
                end = counter / 2;
            }//end if / else
        }//end if
        return end;
    }

    /*
    This method finds a given number from the array and if it finds the number in the array,
    it returns the index of where tha number is placed.
     */
    public int findNumber(int number) {
        int start = getStartingIndex(number), end = getEndingIndex(number);
        for (int i = start; i < end; i++) {
            if (array[i] == number) {
                return (i + 1);
            }//end if
        }//end loop
        return -1;
    }

    /*
    This method prints all the numbers from the array on the console
     */
    public void printNumbers() {
        System.out.println("\nEntered numbers are: \n");
        for (int i = 0; i < counter; i++) {
            System.out.print(array[i] + "\t");
        }//end loop
        System.out.println();
    }

    /*
    this method prints a number from the specific position on the index
     */
    public void printNumberAt(int index) {
        if (index <= 0 || index >= counter) {
            System.out.println("invalid position entered");
            return;
        }//end if
        System.out.println("Number at index \"" + index + "\" is :\t" + array[index - 1]);
    }

    /*
    this method return the counter value
     */
    public int getCounter() {
        return counter;
    }

    /*
       This methods check and tells whether the array is sorted or not
    */
    public boolean isSorted() {
        return isInAscendingOrder || isInDescendingOrder;
    }
}
