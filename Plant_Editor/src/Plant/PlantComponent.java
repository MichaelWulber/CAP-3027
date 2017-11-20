package Plant;

import Plant.Plant_Iterators.Iter;
import Plant.Plant_Iterators.SelfIter;
import javafx.scene.shape.Shape3D;

import java.util.LinkedList;
import java.util.Stack;

public abstract class PlantComponent {
    private PlantComponent parent = null;
    private SelfIter selfIter = new SelfIter(this);

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

    public LinkedList<PlantComponent> getChildren(){
        return new LinkedList<PlantComponent>();
    }

    abstract public Iter makeIter();

    final public Iter makePreOrderIter(){
        return new Iter(){
            private Stack<Iter> iterators = new Stack<Iter>();

            @Override
            public PlantComponent currentItem() {
                return iterators.peek().currentItem();
            }

            @Override
            public boolean isValid() {
                return !iterators.isEmpty();
            }

            @Override
            public void next() {
                Iter childIter = iterators.peek().currentItem().makeIter();
                childIter.reset();
                iterators.peek().next();
                iterators.push(childIter);

                while (this.isValid() && !iterators.peek().isValid()){
                    iterators.pop();
                }
            }

            @Override
            public void reset() {
                selfIter.reset();
                iterators.clear();
                iterators.push(selfIter);
            }
        };
    }
}
