package app;

import LSystem.LSystemDescription;

import java.io.File;

public class TableEntry {
    public File file;
    public float weight;
    public String name;

    public TableEntry(File file, float weight){
        this.file = file;
        this.weight = weight;
        this.name = file.getName();
    }

    public File getFile() {
        return file;
    }

    public float getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
