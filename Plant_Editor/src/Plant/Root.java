package Plant;

import Plant.Plant_Iterators.Iter;
import Plant.Plant_Iterators.LinkedIter;
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

    @Override
    public void addChildHelp(PlantComponent pc) throws Exception {
        children.add(pc);
    }

    @Override
    public Iter makeIter() {
        return new LinkedIter(this.children);
    }

    @Override
    public LinkedList<Shape3D> getShapes() {
        return shapes;
    }

    public LinkedList<PlantComponent> getChildren() {
        return children;
    }
}
