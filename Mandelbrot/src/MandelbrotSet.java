import java.awt.*;

public class MandelbrotSet {

    public BoundedBox bounds;
    public int memberColor;
    public int nonMemberColor;

    public MandelbrotSet(){
        this.bounds = new BoundedBox(0, 0, 1, 1);
        this.memberColor = 0x00000000;
        this.nonMemberColor = 0xFFFFFFFF;
    }

    public MandelbrotSet(BoundedBox bounds, int memberColor, int nonMemberColor){
        this.bounds = bounds;
        this.memberColor = memberColor;
        this.nonMemberColor = nonMemberColor;
    }
}
