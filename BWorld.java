import info.gridworld.grid.*;
import info.gridworld.actor.*;
import info.gridworld.world.*;

import java.util.ArrayList;

// BWorld overrides step so it doesn't do anything,  
// processes W, D, A, and S keys to move up, right, down, and left, respectively.
public class BWorld extends World<Actor>
{       
    private Mover mover;
    private int direction;
    private Number[] allNums;
    private Grid grid;
 
    public BWorld()
    {
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
    // postcondition: returns the current Grid.
    public Grid thisGrid() {
        return getGrid();
    }
    
    // precondition: none.
    // postcondition: gets the direction of the player's keyPress.
    public int getDirection() {
        return direction;
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
    
    // precondition: none.
    // postcondition: puts back a row or column onto the Grid.
    public void putBackInGrid(ArrayList<Number> numbers, int lineNum, int direction) {
        for(int i = 0; i < 4; i++)
            putBackBlock(lineNum, i, numbers, direction);
        
        /* Right/down lines get put back in reverse order. */
        for(int i = 3; i >= 0; i--)
            putBackBlock(lineNum, i, numbers, direction);
    }
    
    // precondition: none.
    // postcondition: puts back an individual block onto the Grid.
    public void putBackBlock(int lineNum, int i, ArrayList<Number> numbers, int direction) {
        /* If the ArrayList is a row, then the changing element (i) is the x coordinate.
         * If the ArrayList is a column, the changing element(i) is set to the y coordinate. */
        int x = i; 
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

    /**
     * Constructs an actor world with a given grid.
     * @param grid the grid for this world.
     */
    public BWorld(Grid<Actor> grid)
    {
        super(grid);
    }

    public void step()                              // deactivates step
    {
        
    }
    
    /**
     * This method is called when a key was pressed. Override it if your world wants
     * to consume some keys (e.g. "1"-"9" for Sudoku). Don't consume plain arrow keys,
     * or the user loses the ability to move the selection square with the keyboard.   
     * @param description the string describing the key, in 
     * <a href="http://java.sun.com/javase/6/docs/api/javax/swing/KeyStroke.html#getKeyStroke(java.lang.String)">this format</a>. 
     * @param loc the selected location in the grid at the time the key was pressed
     * @return true if the world consumes the key press, false if the GUI should
     * consume it.
     */
    // sets the direction and catalyzes act() and the analysis of the current Grid.
    public boolean keyPressed(String description, Location loc)
    {
        if (description.equals("W")) {
            direction = Location.NORTH;
            System.out.println("DEBUG");
        }
        else if (description.equals("D"))
            direction = Location.EAST;
        else if(description.equals("A"))
            direction = Location.WEST;
        else if(description.equals("S"))
            direction = Location.SOUTH;
        Number num = new Number();
        num.act();
        return true;
    }
    
    // methods below unchanged from ActorWorld
    /**
     * Adds an actor to this world at a given location.
     * @param loc the location at which to add the actor
     * @param occupant the actor to add
     */
    public void add(Location loc, Actor occupant)
    {
        occupant.putSelfInGrid(getGrid(), loc);
    }

    /**
     * Adds an occupant at a random empty location.
     * @param occupant the occupant to add
     */
    public void add(Actor occupant)
    {
        Location loc = getRandomEmptyLocation();
        if (loc != null)
            add(loc, occupant);
    }

    /**
     * Removes an actor from this world.
     * @param loc the location from which to remove an actor
     * @return the removed actor, or null if there was no actor at the given
     * location.
     */
    public Actor remove(Location loc)
    {
        Actor occupant = getGrid().get(loc);
        if (occupant == null)
            return null;
        occupant.removeSelfFromGrid();
        return occupant;
    }
    
    
}