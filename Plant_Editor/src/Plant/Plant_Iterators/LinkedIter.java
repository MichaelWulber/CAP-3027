package Plant.Plant_Iterators;

import Plant.PlantBranch;
import Plant.PlantComponent;

import java.util.LinkedList;

public class LinkedIter implements Iter {
    private int index;
    private LinkedList<PlantComponent> children;

    public LinkedIter(LinkedList<PlantComponent> children){
        this.children = children;
        this.index = 0;
    }

    @Override
    public PlantComponent currentItem() {
        return children.get(index);
    }

    @Override
    public boolean isValid() {
        return  (index < children.size());
    }

    @Override
    public void next() {
        index++;
    }

    @Override
    public void reset() {
        index = 0;
    }
}
