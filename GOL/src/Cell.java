public class Cell {
    private final int ALIVE = 1;
    private final int DEAD = 0;

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
