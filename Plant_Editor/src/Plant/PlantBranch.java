package Plant;

import javafx.scene.shape.Shape3D;

import java.util.LinkedList;

public class PlantBranch extends PlantComponent {
    private LinkedList<Shape3D> shapes = null;
    private LinkedList<PlantComponent> children = null;

    protected double rootX;
    protected double rootY;
    protected double length;
    protected double rotateX;
    protected double rotateY;

    public PlantBranch(){
        this.shapes = new LinkedList<Shape3D>();
        this.children = new LinkedList<PlantComponent>();
    }

    public PlantBranch(PlantComponent... components){
        children = new LinkedList<PlantComponent>();
        for (PlantComponent component : components){
            this.addChild(component);
        }
    }

    @Override
    public void addChildHelp(PlantComponent pc) throws Exception {
        children.add(pc);
    }

    @Override
    public double getTipX() {
        return rootX + Math.cos(rotateX) * length;
    }

    @Override
    public double getTipY() {
        return rootY + Math.sin(rotateY) * length;
    }

    @Override
    public void addShape(Shape3D shape) {
        this.shapes.add(shape);
    }
}
