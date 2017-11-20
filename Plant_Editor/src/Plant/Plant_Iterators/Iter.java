package Plant.Plant_Iterators;

import Plant.PlantComponent;

public interface Iter {
    public PlantComponent currentItem();

    public boolean isValid();

    public void next();

    public void reset();
}
