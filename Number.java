import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.Color;

public class Number extends Mover
{
    private Number[] allNums;
    private ArrayList<Number> numsOnGrid;
    
    public Number() {
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
        allNums[9] = new OneTwentyFour();
        allNums[10] = new TwentyFourtyEight();
        
        numsOnGrid.add(new Two());
        numsOnGrid.add(new Two());
    }
    
    public void act() {
        for(int i = 0; i < numsOnGrid.size(); i++) {
            /* Get the direction that the user chooses. */
            Number num = numsOnGrid.get(i);
            BWorld temp = new BWorld(num);
            int direction = temp.getDirection();
            
            /* Check what is in the spot in that direction. */
            Location nextSpot = num.getLocation().getAdjacentLocation(direction);
            Actor nextActor = getGrid().get(nextSpot);
            
            if(nextActor instanceof /* specific class -- how to do this? -- */) {
                // merge by having the one in the direction stay and the other one disappear
            }
            else if (nextActor == null) {
                while(nextSpot != null) {
                    moveTo(nextSpot);
                    // need to find a way to make sure that when it hits the side of the Grid, it also stops.
                }
            }
            
        }
    }
    
    
}