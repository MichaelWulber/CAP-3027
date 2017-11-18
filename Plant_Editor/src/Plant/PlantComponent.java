package Plant;

import javafx.scene.shape.Shape3D;

import java.util.LinkedList;

public abstract class PlantComponent {
    private PlantComponent parent = null;

    public final void addChild(PlantComponent pc){
        try {
            addChildHelp(pc);
            pc.parent = this;
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void addChildHelp(PlantComponent pc) throws Exception{
        throw new RuntimeException("add() not supported");
    }

    public double getTipX(){
        throw new RuntimeException("getTipX() not supported");
    }

    public double getTipY(){
        throw new RuntimeException("getTipY() not supported");
    }

    public void addShape(Shape3D shape){
        throw new RuntimeException("addShape() not supported");
    }

    public LinkedList<Shape3D> getShapes(){
        return new LinkedList<Shape3D>();
    }
}
