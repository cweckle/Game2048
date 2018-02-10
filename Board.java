import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.Color;
public class Board extends BWorld{
    private Number[] allNums;

    public Board(){
    }

    public Board(BoundedGrid<Actor> gr) {
        super(gr);
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
        for(int i = 0; i < 11; i ++)
            System.out.println(allNums[i]);
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
        //still need to do this for up and down columns
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
        boolean chosen = false;
        while(!chosen){
            int randX = (int)(Math.random()*4);
            int randY = (int)(Math.random()*4);
            Location random = new Location(randX, randY);
            if(random != null){
                new Two().putSelfInGrid(getGrid(), random);
                chosen = true;
            }
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
        numbers.get(i).removeSelfFromGrid();
        if(numbers.get(i) != null) {
                replacement.putSelfInGrid(getGrid(), loc);
            } 
    }
    // precondition: none.
    // postcondition: returns the reverse of the passed in ArrayList.
    public ArrayList<Number> reverseList(ArrayList<Number> numbers) {
        ArrayList<Number> tempNum = new ArrayList<Number>();
        int count = 0;
        if(numbers.size() != 0 && tempNum.size() != 0){
            for(int j = numbers.size() - 1; j >= 0; j--) {
                tempNum.set(count, numbers.get(j));
                count++;
            }
        }
        return tempNum;
    }

    // precondition: none.
    // postcondition: combines two numbers that are the same and returns a modified ArrayList.
    public ArrayList<Number> combineNumber(ArrayList<Number> other) {
        // this properly creates the new combined array list but it doesn't show still for some reason
        if(other.size() != 1){
            for(int i = 1; i < other.size(); i++) {
                if(getIndex(other.get(i - 1)) == getIndex(other.get(i))) {
                    System.out.println("go");
                    Number check = other.get(i);
                    int index = index(check);
                    Number newNum = getNum(index + 1);
                    System.out.println(newNum);
                    other.set(i - 1, newNum);
                    other.remove(i);
                }
            }
        }
        System.out.println("new other " + other);
        return other;
    }

    public int index(Number check){
        for(int i = 0; i < 11; i ++){
            if(check.getClass() == allNums[i].getClass())
            {
                return i;
            }
        }
        return -1;
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
        if(spot != null && spot instanceof Number) {
            numbers.add((Number)getGrid().get(loc));
        }
        return numbers;
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
        int direction = 0;
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
        act(direction);
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