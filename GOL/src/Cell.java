import java.awt.*;
import java.util.Dictionary;
import java.util.HashMap;

public class Cell {
    public final static int JUST_BORN = 0;
    public final static int ALIVE = 1;
    public final static int JUST_DIED = 2;
    public final static int DEAD = 3;
    public int state;

    public Cell(){
        state = DEAD;
    }

    public Cell(int state){
        this.state = state;
    }

    public void invert(){
        state = (state == ALIVE) ? DEAD : ALIVE;
    }

    public void advanceState(){
        state = (state + 1) % 4;
    }

    public boolean isAlive(){
        return (state == ALIVE || state == JUST_BORN);
    }
}
