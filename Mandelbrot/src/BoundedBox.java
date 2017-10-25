public class BoundedBox {
    public double lowerX;
    public double upperX;
    public double lowerY;
    public double upperY;

    public BoundedBox(){
        this.lowerX = 0;
        this.upperX = 0;
        this.lowerY = 0;
        this.upperY = 0;
    }

    public BoundedBox(double lowerX, double upperX, double lowerY, double upperY){
        this.lowerX = lowerX;
        this.upperX = upperX;
        this.lowerY = lowerY;
        this.upperY = upperY;
    }

    public void map(int width, int height, int x, int y, int w, int h){
        double tempX = lowerX;
        double tempY = lowerY;
        this.lowerX = tempX + ((double)x/(double)width) * (upperX - tempX);
        this.upperX = tempX + ((double)(x + w)/(double)width) * (upperX - tempX);
        this.lowerY = tempY + ((double)y/(double)height) * (upperY - tempY);
        this.upperY = tempY +((double)(y + h)/(double)height) * (upperY - tempY);
    }

}
