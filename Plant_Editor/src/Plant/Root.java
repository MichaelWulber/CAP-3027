package Plant;

import javafx.scene.shape.Shape3D;

import java.util.LinkedList;

public class Root extends PlantComponent {
    private LinkedList<Shape3D> shapes = null;
    private LinkedList<PlantComponent> children = null;

    public Root(){
        this.shapes = new LinkedList<Shape3D>();
        this.children = new LinkedList<PlantComponent>();
    }

    public Root(PlantComponent... components){
        children = new LinkedList<PlantComponent>();
        for (PlantComponent component : components){
            this.addChild(component);
        }
    }
}
