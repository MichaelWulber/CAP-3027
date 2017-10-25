public class JuliaSet {
    public BoundedBox bounds;
    public int memberColor;
    public int nonMemberColor1;
    public int nonMemberColor2;

    public JuliaSet(){
        this.bounds = new BoundedBox(-2, 2, -1.5, 1.5);
        this.memberColor = 0xFF000000;
        this.nonMemberColor1 = 0xFFFF3300;
        this.nonMemberColor2 = 0xFF005000;
    }

    public void resetBounds(){
        this.bounds = new BoundedBox(-2, 2, -1.5, 1.5);
    }
}
