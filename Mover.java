import info.gridworld.grid.*;
import info.gridworld.actor.*;
import java.awt.Color;

// a Mover is an Actor that can move up and to the right
public class Mover extends Number
{

    public Mover()
    {
        setColor(Color.RED);
    }

    /**
     * up and right are copies of the move method from Bug with the direction changed
     */
    public void up()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(Location.NORTH);    // changed this line
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }
    
    public void right()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(Location.EAST);        // and this one
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }
}
