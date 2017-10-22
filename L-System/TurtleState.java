import java.awt.geom.Point2D;

public class TurtleState {
    public Point2D.Double coord;
    private double bearing;
    public double bsl;

    public TurtleState(double x, double y, double bearing, double bsl){
        this.coord = new Point2D.Double(x, y);
        this.bearing = bearing;
        this.bsl = bsl;
    }

    public TurtleState() {
        this.coord = new Point2D.Double(0.0, 0.0);
        this.bearing = 0;
        this.bsl = bsl;
    }

    public void setBearing(double bearing){
        this.bearing = bearing;
    }

    public double getBearing() {
        return bearing;
    }
}
