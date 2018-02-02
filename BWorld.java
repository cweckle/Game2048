import info.gridworld.grid.*;
import info.gridworld.actor.*;
import info.gridworld.world.*;

import java.util.ArrayList;

// BWorld overrides step so it doesn't do anything,  
// processes W and D keys to move its mover up and right,
// and processes mouse click to move mover to spot clicked
public class BWorld extends World<Actor>
{       
    private Mover mover;                            // a copy of mover is here so I can manipulate it           
 
    public BWorld(Mover mover)
    {
        this.mover = mover;
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
     * This method is called when the user clicks on a location in the
     * WorldFrame.
     * 
     * @param loc the grid location that the user selected
     * @return true if the world consumes the click, or false if the GUI should
     * invoke the Location->Edit menu action
     */
    // implemented to move mover to location
    public boolean locationClicked(Location loc)
    {
        mover.moveTo(loc);
        return true;
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
    // implemented to move up and right with the W and D keys
    public boolean keyPressed(String description, Location loc)
    {
        System.out.println(description); 
  
        if (description.equals("W"))
            mover.up();
        else if (description.equals("D"))
            mover.right();
   
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