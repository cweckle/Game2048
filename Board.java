import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.Color;
public class Board extends BWorld{
    private Number[] allNums;
    
    public Board() {
        allNums = new Number[11];
        allNums[0] = new Two();
        allNums[1] = new Four();
        allNums[2] = new Eight();
        allNums[3] = new Sixteen();
        allNums[4] = new ThirtyTwo();
        allNums[5] = new SixtyFour();
        allNums[6] = new OneTwentyEight();
        allNums[7] = new TwoFiftySix();
        allNums[8] = new FiveTwelve();
        allNums[9] = new TenTwentyFour();
        allNums[10] = new TwentyFourtyEight();
    }
    
    // precondition: none.
    // postcondition: retrieves a number from the master Array (allNums) and returns it.
    public Number getNum(int i) {
        return allNums[i];
    }
    
    // precondition: none.
    // postcondition: returns the index of a Number in the master Array of numbers (allNums). */
    public int getIndex(Number other) {
        for(int i = 0; i < allNums.length; i++) {
            if(other.equals(allNums[i]))
                return i;
        }
        return -1;
    }
    
    public void act(int direction) {
        /* This ArrayList will hold all the Numbers in a specific row or column. */
        ArrayList<Number> numbers = new ArrayList<Number>();
        
        /* Go through each row/column in the Grid and process that individual one. */
        for(int i = 0; i < 4; i++){
            numbers = findActorsInRow(direction, i);
            
            /* If the direction is up or left, processing starts naturally at the first block. */
            if(direction == 0 || direction == 270) {
                numbers = combineNumber(numbers);
            }
            /* However, if direction is right or down, processing must start at the last block. 
             * We solve this issue by reversing the array and then combining/processing.
             */
            else {
                numbers = reverseList(numbers);
                numbers = combineNumber(numbers);
            }
            
            /* After all processing is done, put the row/column back onto the grid. */
            putBackInGrid(numbers, i, direction);
        }
    }
    
    // precondition: none.
    // postcondition: puts back a row or column onto the Grid.
    public void putBackInGrid(ArrayList<Number> numbers, int lineNum, int direction) {
        if(numbers.size() == 0)
            return;
        
        for(int i = 0; i < numbers.size(); i++)
            putBackBlock(lineNum, i, numbers, direction);
        
        /* Right/down lines get put back in reverse order. */
        for(int i = numbers.size() - 1; i >= 0; i--)
            putBackBlock(lineNum, i, numbers, direction);
    }
    
    // precondition: none.
    // postcondition: puts back an individual block onto the Grid.
    public void putBackBlock(int lineNum, int i, ArrayList<Number> numbers, int direction) {
        /* If the ArrayList is a row, then the changing element (i) is the x coordinate.
         * If the ArrayList is a column, the changing element(i) is set to the y coordinate. */
        int x = i; 
        System.out.println("I: " + i);
        int y = lineNum;
        if(direction == 0 || direction == 180) {
            x = lineNum;
            y = i;
        }
        
        /* Identify the spot on the grid. If that spot is occupied, remove it and replace it with the new Number from the Arraylist. */
        Location loc = new Location(x, y);
        Actor spot = getGrid().get(loc);
        if(spot != null)
            spot.removeSelfFromGrid();
        Actor replacement = numbers.get(i);
        if(numbers.get(i) != null) {
            replacement.putSelfInGrid(getGrid(), loc);
        }    
    }
    // precondition: none.
    // postcondition: returns the reverse of the passed in ArrayList.
    public ArrayList<Number> reverseList(ArrayList<Number> numbers) {
        ArrayList<Number> tempNum = new ArrayList<Number>();
        int count = 0;
        for(int j = numbers.size() - 1; j >= 0; j--) {
            tempNum.set(count, numbers.get(j));
            count++;
        }
        return tempNum;
    }

    // precondition: none.
    // postcondition: combines two numbers that are the same and returns a modified ArrayList.
    public ArrayList<Number> combineNumber(ArrayList<Number> other) {
        for(int i = 0; i < other.size(); i++) {
            if(other.get(i).equals(other.get(i + 1))) {
                int index = getIndex(other.get(i));
                Number newNum = getNum(index + 1);
                other.set(i, newNum);
                other.remove(i + 1);
            }
        }
        return other;
    }
    
    // precondition: the Grid must exist.
    // postcondition: checks each block in one row/column and adds found Numbers into an ArrayList that is returned.
    public ArrayList<Number> findActorsInRow(int direction, int line) {
        ArrayList<Number> numbers = new ArrayList<Number>();
        for(int i = 0; i < 4; i++)
            numbers = addNumbers(numbers, line, direction, i);
        return numbers;
    }
    
    // precondition: none.
    // postcondition: checks the block for a Number and, if it is, adds it to the ArrayList and returns it.
    public ArrayList<Number> addNumbers(ArrayList<Number> numbers, int line, int direction, int i) {
        int x = line;
        int y = i;
        if(direction == 0 || direction == 180) {
            x = i;
            y = line;
        }
        Location loc = new Location(line, i);
        Actor spot = getGrid().get(loc);
        System.out.println(getGrid());
        if(spot != null && spot instanceof Number) {
            numbers.add((Number)getGrid().get(loc));
            System.out.println("hello");
        }
        return numbers;
    }
}