import info.gridworld.grid.*;
import info.gridworld.actor.*;
import info.gridworld.world.*;

import java.util.ArrayList;

// BWorld overrides step so it doesn't do anything,  
// processes W, D, A, and S keys to move up, right, down, and left, respectively.
public class BWorld extends World<Actor>
{       
    private int direction;
 
    public BWorld()
    {
        
    }
    
    // precondition: none.
    // postcondition: gets the direction of the player's keyPress.
    public int getDirection() {
        return direction;
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
        Board board = new Board();
        if (description.equals("W")) {
            direction = Location.NORTH;
            System.out.println("W");
        }
        else if (description.equals("D")) {
            direction = Location.EAST;
            System.out.println("D");
        }
        else if(description.equals("A")) {
            direction = Location.WEST;
            System.out.println("A");
        }
        else if(description.equals("S")) {
            direction = Location.SOUTH;
            System.out.println("S");
        }
        board.act(direction);
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