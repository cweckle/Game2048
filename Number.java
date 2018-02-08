/**
 * Changes made to code:
 *      - Resolved the nullPointerException for the getGrid() issue.
 *           - Created a thisGrid() method in BWorld that returns the current Grid. 
 *           - Set a Grid object grid to this, and used grid instead of getGrid();
 *      - @elysia simplified your code into one shorter method.
 *      - attempted to write the code to paste the processed row/column back onto the Grid.
 * 
 * Issues
 *      - Null Pointer Exception came back after coding some more.
 *          - specifically, this issue happens at paste().
 * 
 * Other Stuff
 *      - Remove Mover() if we don't end up using it.
 *      - Do we want it to slide across the board (graphically)? In that case, we would use Mover.
 *      - Add a constructor to Number()?
 *      - SHOULD WE EVEN BE USING ACT() IF WE DISABLED STEP?
 *          - Maybe we should call it playGame() or swipe() or something.
 * 
 */

import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.Color;

public class Number extends Mover
{
    public void act() {
        /* Get the direction that the person "swipes in." */
        BWorld temp = new BWorld();
        int direction = temp.getDirection();
        
        /* This ArrayList will hold all the Numbers in a specific row or column. */
        ArrayList<Number> numbers = new ArrayList<Number>();
        
        /* Go through each row/column in the Grid and process that individual one. */
        for(int i = 0; i < 4; i++){
            numbers = findActorsInRow(direction, i);
            
            /* If the direction is up or left, processing starts naturally at the first block. */
            if(direction == 0 || direction == 270) {
                numbers = combineNumber(numbers, temp);
            }
            /* However, if direction is right or down, processing must start at the last block. 
             * We solve this issue by reversing the array and then combining/processing.
             */
            else {
                numbers = reverseList(numbers);
                numbers = combineNumber(numbers, temp);
            }
            
            /* After all processing is done, put the row/column back onto the grid. */
            temp.putBackInGrid(numbers, i, direction);
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
    public ArrayList<Number> combineNumber(ArrayList<Number> other, BWorld temp) {
        for(int i = 0; i < other.size(); i++) {
            if(other.get(i).equals(other.get(i + 1))) {
                int index = temp.getIndex(other.get(i));
                Number newNum = temp.getNum(index + 1);
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
        BWorld temp = new BWorld();
        Grid current = temp.thisGrid();
        for(int i = 0; i < 4; i++)
            numbers = addNumbers(numbers, current, line, direction, i);
        return numbers;
    }
    
    // precondition: none.
    // postcondition: checks the block for a Number and, if it is, adds it to the ArrayList and returns it.
    public ArrayList<Number> addNumbers(ArrayList<Number> numbers, Grid current, int line, int direction, int i) {
        int x = line;
        int y = i;
        if(direction == 0 || direction == 180) {
            x = i;
            y = line;
        }
        Location loc = new Location(line, i);
        if(current.get(loc) != null && current.get(loc) instanceof Number)
            numbers.add((Number)current.get(loc));
        return numbers;
    }
}