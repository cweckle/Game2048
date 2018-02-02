import info.gridworld.actor.*;
import info.gridworld.grid.*;

import java.awt.Color;

public class NumberRunner{
    public static void main(String[] args){
        ActorWorld world = new ActorWorld(new BoundedGrid<Actor>(4,4));
        for(int i = 0; i < 2; i ++){
            int val = (int)(Math.random()*2);
            if(val == 0)
                world.add(new Two());
            else
                world.add(new Four());
        }
        world.show();
    }
}