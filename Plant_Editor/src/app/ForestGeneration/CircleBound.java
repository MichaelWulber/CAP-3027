package app.ForestGeneration;

public class CircleBound {
    public double x;
    public double z;
    public double radius;

    public CircleBound(double x, double z, double radius){
        this.x = x;
        this.z = z;
        this.radius = radius;
    }

    public boolean intersects(CircleBound other){
        return (this.radius + other.radius) < Math.sqrt( Math.pow(this.x - other.x, 2) + Math.pow(this.z - other.z, 2) );
    }
}
