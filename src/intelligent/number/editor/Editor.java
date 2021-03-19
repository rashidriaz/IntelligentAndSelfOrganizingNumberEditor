package intelligent.number.editor;

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
    ----------------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------------
    --------------------------------Public Methods------------------------------------------
    ----------------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------------
     */
    /*
    ----------------------------------------------------------------------------------------
    This function inserts a new number into the array if the counter is less than 3.
    -In case if the counter is greater than 3 this method calls a private method which
    -first checks the order of array and based on that, makes decision about the position
    -of the newly entered number.
     ---------------------------------------------------------------------------------------
     */
    public void insert(int number) {
        if (counter == 0 || !isSorted()) {
            insertAtTheEnd(number);
            return;
        }//end if
        if (counter == 1 || (isInAscendingOrder && isInDescendingOrder)) {
            isInAscendingOrder = isInAscendingOrder(number);
            isInDescendingOrder = isInDescendingOrder(number);
            insertAtTheEnd(number);
            return;
        }//end if
        checkOrderAndInsert(number);
    }// end Insert

    /*
    ---------------------------------------------------------------------------------------
    The functionality of this method is to take two numbers as arguments first a number to
    -be inserted, and second is the position where the number is to be positioned. First
    -this method validated the position. if the position is valid then based on the order of
    -the array, this methods places the number at the suitable place
    ---------------------------------------------------------------------------------------
     */
    //9 Lines of comments in this method and 23 lines of code
    public boolean insertAt(int number, int position) {
        if (position <= 0 || position > ARRAY_SIZE) {
            return false;
        }//end if
        if (position >= counter) {
            insert(number);
            return true;
        }//end if
        if (!isSorted()) {
            insertInUnSortedArrayAtPosition(number, position);
            return true;
        }//end if
        /*
        If our program have crossed the previous if block successfully
        it means that our array is sorted and the value of position is
        valid and less thn the counter value
         */
        if (position == 1) {
            if ((array[position - 1] >= number && isInAscendingOrder) ||
                    (array[position - 1] <= number && isInDescendingOrder)) {
                placeNumberAndMoveElementsToRight(number, position);
                return true;
            }
        } else if ((array[position - 2] <= number && array[position - 1] >= number) ||
                (array[position - 2] >= number && array[position - 1] <= number)) {
            placeNumberAndMoveElementsToRight(number, position);
            return true;
        } //end if/else
        /*
        If our program reaches this line, it means that our array will not
         remain sorted iff we will insert the number at the specified position.
         */
        return insertNumberAtPositionInSortedArrayIfOrderIsGettingDisturbed(number, position);
    }

    /*
    --------------------------------------------------------------------------------------------------------
    The functionality of the method is to find and replace the given number with the new number in the array
    --------------------------------------------------------------------------------------------------------
     */
    public boolean findAndReplace(int oldNumber, int newNumber, boolean replaceAll) {
        int index = findNumber(oldNumber);
        if (index < 0) {//number not found
            return false;
        }
        if (index > 0) {
            if (!Main.askForUsersPermission("Are you sure you want to replace " + oldNumber + " with " + newNumber + "? (Y-yes, N-no):\t")) {
                return false;
            }
        }
        if (isSorted()) {
            if (!Main.askForUsersPermission("Replacing the number might affect the order. Would you still like to proceed? (Y-yes, N-no):\t")) {
                int oldCounter = counter;
                delete(oldNumber, replaceAll, true);
                while (counter != oldCounter) {
                    if (counter < 1) {
                        insert(newNumber);
                    }
                    insertAtSuitablePosition(newNumber);
                }
                return true;
            }
            setArrayAsUnsorted();
        }
        while (index > 0) {
            array[index - 1] = newNumber;
            index = findNumber(oldNumber);
        }
        return true;
    }

    /*
    ---------------------------------------------------------------------------------------
    the functionality of this method is to delete a number from the array. this method
    -first checks whether the array is sorted or not and based on that, decides that from
    -where it should start traversing the array and at which point it should stop. this
    -method returns true if it finds and deletes the given number else it returns false
    ---------------------------------------------------------------------------------------
     */
    public boolean delete(int number, boolean deleteAll, boolean internalCall) {
        if (!isSorted()) {
            return deleteFromUnSortedArray(number, deleteAll, internalCall);
        }
        return deleteFromSortedArray(number, deleteAll, internalCall);
    }

    /*
   ---------------------------------------------------------------------------------------
   The method takes two parameters 1. starting index, 2. ending index. The functionality of
   -this method is to delete each and every element between the indices start and end
   -irrespective of the order of the array
   ---------------------------------------------------------------------------------------
    */
    public boolean deleteInARange(int start, int end) {
        if ((start <= 0 || start > counter) || end <= 0 || end < start) {
            return false;
        }//end if
        if (start == 1 && end == counter) { // If this condition returns true, it means that the user have asked to delete every element of the array
            counter = 0;
            return true;
        }
        if (end == counter) {
            //if this condition returns true, it means the user have asked to delete every number from the startIndex till the end of the array
            counter = start;
            return true;
        }
        if (!Main.askForUsersPermission("Are you sure you want to delete the numbers? (Y- yes, N-no):\t")) {
            return false;
        }
        moveElementsToTheLeft(start, end);
        return true;
    }

    /*
    ---------------------------------------------------------------------------------------
    This method finds a given number from the array and if it finds the number in the array,
    it returns the index of where tha number is placed.
    ---------------------------------------------------------------------------------------
     */
    public int findNumber(int number) {
        int end = counter;
        for (int i = 0; i < end; i++) {
            if (isSorted()) {
                if (isInAscendingOrder) {
                    if (array[counter / 2] > number) {
                        end = counter / 2;
                    } else {
                        i = counter / 2;
                    }
                } else {
                    if (array[counter / 2] < number) {
                        end = counter / 2;
                    } else {
                        i = counter / 2;
                    }
                }
            }
            if (array[i] == number) {
                return (i + 1);
            }//end if
        }//end loop
        return -1;
    }

    /*
    ---------------------------------------------------------------------------------------
    This method prints all the numbers from the array on the console
    ---------------------------------------------------------------------------------------
     */
    public void printNumbers() {
        System.out.println("\nEntered numbers are: \n");
        for (int i = 0; i < counter; i++) {
            System.out.print(array[i] + "\t");
        }//end loop
        System.out.println();
    }

    /*
    ---------------------------------------------------------------------------------------
    This method prints a number from the specific position on the index
    ---------------------------------------------------------------------------------------
     */
    public void printNumberAt(int index) {
        if (index <= 0 || index >= counter) {
            System.out.println("invalid position entered");
            return;
        }//end if
        System.out.println("Number at index \"" + index + "\" is :\t" + array[index - 1]);
    }

    /*
    ---------------------------------------------------------------------------------------
    This method allows user to sort the array in either ascending or descending order
    ---------------------------------------------------------------------------------------
     */
    public void sort(boolean inAscendingOrder) {
        if ((isInAscendingOrder && inAscendingOrder) || (isInDescendingOrder && !inAscendingOrder)) {
            return;
        }
        Sort.sort(array, 0, counter - 1, inAscendingOrder);
    }

    /*
    ---------------------------------------------------------------------------------------
    This method return the counter value
    ---------------------------------------------------------------------------------------
     */
    public int getCounter() {
        return counter;
    }

    /*
    ---------------------------------------------------------------------------------------
    This methods check and tells whether the array is sorted or not
    ---------------------------------------------------------------------------------------
    */
    public boolean isSorted() {
        return isInAscendingOrder || isInDescendingOrder;
    }

    /*
    ---------------------------------------------------------------------------------------
    This methods check and tells whether the array is empty or not
    ---------------------------------------------------------------------------------------
    */
    public boolean isEmpty() {
        return counter == 0;
    }

    /*
    ---------------------------------------------------------------------------------------
       This methods check and tells whether the array is full or not
    ---------------------------------------------------------------------------------------
    */
    public boolean isFull() {
        return counter == ARRAY_SIZE;
    }

    /*
    ---------------------------------------------------------------------------------------
       This method will reset the counter value to 0
    ---------------------------------------------------------------------------------------
    */
    public void reset() {
        counter = 0;
    }

    /*
    ----------------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------------
    --------------------------------Public Methods------------------------------------------
    ----------------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------------
    */
    /*
    ---------------------------------------------------------------------------------------
    This function takes a number as an argument, and checks whether the array will remain
    -sorted with the insertion of the new number. and based on that it asks user to choose
    -whether he/she wants to maintain the order or not. Based on the user's decision, the
     program will proceed.
    ---------------------------------------------------------------------------------------
     */
    private void checkOrderAndInsert(int number) {
        boolean disturbTheOrder = false;
        //The following code block checks whether the insertion of the new number would disturb the order or not,
        // if it does not disturb the order, then it simply add the the new number at the end of the array
        if ((isInAscendingOrder && isInAscendingOrder(number)) ||
                (isInDescendingOrder && isInDescendingOrder(number))) {
            insertAtTheEnd(number);
            return;
        }//end if
        // If our program reaches this statement then it means that the new number would definitely disturb
        // the order of the of the array, so the program will prompt and ask the user whether they would like to continue with that
        // or the program should keep the array sorted
        if (isSorted()) {
            disturbTheOrder = Main.askForUsersPermission("Inserting this number might affect the order.\n" +
                    " Would you still like to proceed and disturb the order? (y-yes, N- No)");
            if (!disturbTheOrder) {
                insertAtSuitablePosition(number);
                return;
            }//end if
            setArrayAsUnsorted();
        }//endIf
        array[counter++] = number;
        isInAscendingOrder = false;
        isInDescendingOrder = false;
    }//end check order And insert method


    /*
    ---------------------------------------------------------------------------------------
    This method gets called from the insertAt Method when there are chances that the order
    -of the array might get disturbed if we try to insert number at specific position. So
    -the functionality of this method is to as user whether he/she would like to disturb
    -the order of the array or the program itself should identify the most suitable place
    -for the number to be inserted
     ---------------------------------------------------------------------------------------
     */
    private boolean insertNumberAtPositionInSortedArrayIfOrderIsGettingDisturbed(int number, int position) {
        boolean disturbTheOrder = Main.askForUsersPermission("Inserting this number might affect " +
                "the order.\n" +
                " Would you still like to proceed and disturb the order? (y-yes, N- No)");
        if (disturbTheOrder) {
            setArrayAsUnsorted();
            insertInUnSortedArrayAtPosition(number, position);
            return true;
        }

        boolean placeNumberAtSuitablePlace = Main.askForUsersPermission(
                " Would you like to place the number at the suitable position?" +
                        "\nPress Y- to place\t N- Discard number." +
                        " \n Please enter your choice:\t");

        if (!placeNumberAtSuitablePlace) {
            System.out.println("Number discarded successfully!");
            return true;
        }
        insertAtSuitablePosition(number);
        return true;
    }

    /*
    ---------------------------------------------------------------------------------------
      The functionality of this method is to find the most suitable place for the given
      -number in the sorted array and place the given number at that position
    ---------------------------------------------------------------------------------------
     */
    private void insertAtSuitablePosition(int number) {
        //Here in this following block, program checks whether the number should be placed at the end of thee array
        // or not based on the last value in the array and by the way in which the array is sorted
        if ((isInAscendingOrder && array[counter - 1] <= number) ||
                (isInDescendingOrder && array[counter - 1] >= number)) {
            insertAtTheEnd(number);
            return;
        }//end if
        array[counter++] = number;
        Sort.sort(array, 0, counter - 1, isInAscendingOrder);
    }

    /*
    ---------------------------------------------------------------------------------------
    The functionality of this method is to insert the given number at the given position in
    -an unsorted array. The method replaces the number at the given position with the new
    -number and moves the previously placed number at the end of the array
    ---------------------------------------------------------------------------------------
     */
    private void insertInUnSortedArrayAtPosition(int number, int position) {
        array[counter++] = array[position - 1];
        array[position - 1] = number;
    }


    /*
    ---------------------------------------------------------------------------------------
    This function puts the given number on the given index position and moves the rest of
    -the array to the right
    ---------------------------------------------------------------------------------------
     */
    private void placeNumberAndMoveElementsToRight(int number, int position) {
        for (int i = counter; i >= position; i--) {
            array[i] = array[i - 1];
        }
        array[position - 1] = number;
        counter++;
    }

    /*
    ---------------------------------------------------------------------------------------
    This function inserts the number at the end of the array
    ---------------------------------------------------------------------------------------
     */
    private void insertAtTheEnd(int number) {
        array[counter++] = number;
    }//end insert at the end

    /*
    ---------------------------------------------------------------------------------------
    The functionality of this method is to delete hte given number from the array considering
    -that the array is in an unsorted order. Based on the value of the parameter:
    - @param: deleteAllOccurrences the function will delete either one or all of the
    -occurrences of the number
    ---------------------------------------------------------------------------------------
     */
    private boolean deleteFromUnSortedArray(int number, boolean deleteAllOccurrences, boolean internalCall) {
        int totalNumbersBeforeDeletion = counter; // The purpose of this variable is to check whether the number is deleted or not at the end of the method
        for (int i = 0; i < totalNumbersBeforeDeletion; i++) {
            if (array[i] == number) {
                if (!internalCall) {
                    if (!Main.askForUsersPermission("Are you sure you want to delete " + number + "? (Y-yes, N-no\t")) {
                        return false;
                    }
                }
                array[i] = array[counter - 1];
                counter--;
                if (!deleteAllOccurrences) {
                    return true;
                }
            }
        }
        return totalNumbersBeforeDeletion != counter; // This condition will return true if any number got deleted from the array
    }


    /*
    ---------------------------------------------------------------------------------------
    This private method takes a number, start and end as parameters. the main function of
    -this method is to traverse the array from the start to end and find the desired number
    -and delete it from the array.
    ---------------------------------------------------------------------------------------
     */
    // total 30 lines of code, => 8 lines of comments, => 22 lines of code
    private boolean deleteFromSortedArray(int number, boolean deleteAll, boolean internalCall) {
        int totalNumbersBeforeDeletion = counter;
        int firstOccurrenceIndex = -1, lastOccurrenceIndex = -1; //initialized with the invalid index numbers
        for (int i = 0; i <= totalNumbersBeforeDeletion; i++) { //Loop for identifying the indexes where the given number occurs in the array
            if (firstOccurrenceIndex < 0 && array[i] == number) {
                firstOccurrenceIndex = i;
            }//end if
            if (array[i] == number) {
                lastOccurrenceIndex = i;
            }//end if
            /* the following code block will check whether the first and last occurrence of the given number has been
            recorded or not if the occurrences have been recorded then, the loop will break another condition of loop
            break is that if the program have recorded the first occurrence and the user have asked to delete only one
             occurrence, hen we don't have to record the last occurrence, so the loop will break in this case too */
            if ((firstOccurrenceIndex >= 0 && lastOccurrenceIndex >= 0 && array[i] != number) ||
                    (firstOccurrenceIndex >= 0 && !deleteAll)) {
                break;
            }//end if
        }//end for loop
        if (firstOccurrenceIndex < 0 && lastOccurrenceIndex < 0) {//if this condition turns true, it means that the number was not found in the array
            return false;
        }
        /*
        The following code block will shift all the remaining elements from the right side of the array to the index,
        from where the number is being deleted
         */
        if (!internalCall) {
            if (!Main.askForUsersPermission("Are you sure you want to delete " + number + "? (Y- yes, N- no):\t")) {
                return false;
            }//end if
        }//end if
        if (lastOccurrenceIndex == counter - 1) {
            counter = firstOccurrenceIndex;
            return true;
        }//end if
        moveElementsToTheLeft(firstOccurrenceIndex, lastOccurrenceIndex);
        return true;
    }


    /*
    ---------------------------------------------------------------------------------------
    The Following method takes starting and ending index as argument and moves the elements
    -from the right of the ending index and place them at the indexes starting from the
    -starting index
    ---------------------------------------------------------------------------------------
     */
    private void moveElementsToTheLeft(int start, int end) {
        while (counter > end) {
            array[start++] = array[++end];
        }
        counter = start;
    }

    /*
    ---------------------------------------------------------------------------------------
    This methods check and tells whether the array will remain in ascending order with the
    -given number or not
    ---------------------------------------------------------------------------------------
    */
    private boolean isInAscendingOrder(int number) {
        return array[counter - 1] <= number;
    }

    /*
   ---------------------------------------------------------------------------------------
   This methods check and tells whether the array will remain in Descending order with the
   -given number or not
   ---------------------------------------------------------------------------------------
    */
    private boolean isInDescendingOrder(int number) {
        return array[counter - 1] >= number;
    }

    /*
    -------------------------------------------------------------------------------------
    This method will set the array as unsorted if the user will choose to do so
    -------------------------------------------------------------------------------------
     */
    private void setArrayAsUnsorted() {
        isInAscendingOrder = false;
        isInDescendingOrder = false;
    }
}
