package LSystem;

public class GrowingState {
    public double posX;
    public double posY;
    public double posZ;

    public double pitch;
    public double yaw;
    public double roll;

    public double stepSize;
    public double radius;

    public GrowingState(double stepSize){
        this.posX = 0;
        this.posY = 0;
        this.posZ = 0;
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
        this.stepSize = stepSize;
        this.radius = 5;
    }

    public GrowingState(GrowingState gs){
        this.posX = gs.posX;
        this.posY = gs.posY;
        this.posZ = gs.posZ;
        this.pitch = gs.pitch;
        this.yaw = gs.yaw;
        this.roll = gs.roll;
        this.stepSize = gs.stepSize;
    }
}
