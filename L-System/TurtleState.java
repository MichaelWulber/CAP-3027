import java.util.LinkedList;

public class TurtleState {
    public double x;
    public double y;
    public double bearing;
    public double bsl;

    public TurtleState(double x, double y, double bearing, double bsl){
        this.x = x;
        this.y = y;
        this.bearing = bearing;
        this.bsl = bsl;
    }

    public TurtleState(){
        this.x = 0;
        this.y = 0;
        this.bearing = 0;
        this.bsl = 1;
    }
}
