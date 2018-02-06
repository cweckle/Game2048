import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.Color;

public class Number extends Mover
{
    public Number() {

    }

    public void act() {
        BWorld temp = new BWorld();
        int direction = temp.getDirection();
        for(int i = 0; i < 4; i++){
            ArrayList<Number> numbers = new ArrayList<Number>();
            numbers = findActorsInRow(direction, i);

            if(direction == 0 || direction == 270) {
                numbers = combineNumber(numbers, temp);
            }
            else {
                ArrayList<Number> tempNum = new ArrayList<Number>();
                int count = 0;
                for(int j = numbers.size() - 1; j >= 0; j--) {
                    tempNum.set(count, numbers.get(j));
                    count++;
                }
                tempNum = combineNumber(tempNum, temp);
            }
        }
    }

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

    public ArrayList<Number> findActorsInRow(int direction, int line) {
        ArrayList<Number> numbers = new ArrayList<Number>();
        if(direction == 0 || direction == 180){
            for(int i = 0; i < 4; i ++){
                Location check = new Location(line, i);
                if(getGrid().get(check) != null)
                    numbers.add((Number)getGrid().get(check));
            }
        }
        else{
            for(int i = 0; i < line; i ++){
                Location check = new Location(i, line);
                if(getGrid().get(check) != null)
                    numbers.add((Number)getGrid().get(check));
            }
        }
        return numbers;
    }
}