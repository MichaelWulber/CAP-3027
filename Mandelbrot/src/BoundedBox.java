public class BoundedBox {
    public int lowerX;
    public int upperX;
    public int lowerY;
    public int upperY;

    public BoundedBox(){
        this.lowerX = 0;
        this.upperX = 0;
        this.lowerY = 0;
        this.upperY = 0;
    }

    public BoundedBox(int lowerX, int upperX, int lowerY, int upperY){
        this.lowerX = lowerX;
        this.upperX = upperX;
        this.lowerY = lowerY;
        this.upperY = upperY;
    }
}
