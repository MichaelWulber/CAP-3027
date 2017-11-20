package Plant.Plant_Iterators;

import Plant.PlantComponent;
import Plant.Plant_Iterators.Iter;

public class SelfIter implements Iter {
    private PlantComponent object = null;
    private boolean onObject;

    public SelfIter(PlantComponent object) {
        this.object = object;
        this.onObject = true;
    }

    @Override
    public PlantComponent currentItem() {
        return object;
    }

    @Override
    public boolean isValid() {
        return onObject;
    }

    @Override
    public void next() {
        onObject = false;
    }

    @Override
    public void reset() {
        onObject = true;
    }
}