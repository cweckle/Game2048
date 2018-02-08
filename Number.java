/**
 * Changes made to code:
 *      - Resolved the nullPointerException for the getGrid() issue.
 *           - Created a thisGrid() method in BWorld that returns the current Grid. 
 *           - Set a Grid object grid to this, and used grid instead of getGrid();
 *      - @elysia simplified your code into one shorter method.
 *      - attempted to write the code to paste the processed row/column back onto the Grid.
 * 
 * Issues3
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

public class Number extends Actor
{
    
}