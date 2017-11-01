public class Cell {
    public final static int ALIVE = 1;
    public final static int DEAD = 0;

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

    public boolean isAlive(){
        return (state == ALIVE);
    }
}
