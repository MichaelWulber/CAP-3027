package Plant;

import javafx.scene.shape.Shape3D;

import java.util.LinkedList;

public class Leaf extends PlantComponent {
    private LinkedList<Shape3D> shapes = null;

    public Leaf(){
        this.shapes = new LinkedList<Shape3D>();
    }

    @Override
    public void addShape(Shape3D shape) {
        this.shapes.add(shape);
    }

}
