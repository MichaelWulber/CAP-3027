import java.awt.*;
import java.util.HashMap;

public class CellGrid {
    private Topology topology;
    private Cell[][] cells;

    public int numRows;
    public int numCols;

    private HashMap<Integer, Color> stateColors;

    public CellGrid(){
        this.topology = new ToroidalTopology();
        this.numCols = 50;
        this.numRows = 50;
        this.cells = new Cell[numRows][numCols];

        stateColors = new HashMap<Integer, Color>();
        stateColors.put(Cell.JUST_BORN, Color.BLUE);
        stateColors.put(Cell.ALIVE, Color.GREEN);
        stateColors.put(Cell.JUST_DIED, Color.RED);
        stateColors.put(Cell.DEAD, Color.BLACK);

        initCells();
    }

    private void initCells(){
        for (int i = 0; i < numRows; ++i){
            for (int j = 0; j < numCols; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public void step(){
        topology.step(this);
    }

    public void invert(int x, int y){
        cells[x][y].invert();
    }

    public void increment(int x, int y){
        cells[x][y].advanceState();
    }

    // ACCESS METHODS
    public Topology getTopology() {
        return topology;
    }

    public void setTopology(Topology topology) {
        this.topology = topology;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Color getColor(int row, int col){
        return stateColors.get(cells[row][col].state);
    }
}
