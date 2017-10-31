public class CellGrid {
    private Topology topology;
    private Cell[][] cells;

    public int numRows;
    public int numCols;

    public CellGrid(){
        this.topology = new InfiniteTopology();
        this.numCols = 25;
        this.numRows = 25;
        this.cells = new Cell[numRows][numCols];
    }

    public void step(){
        topology.step(cells);
    }

    public void invert(int x, int y){
        cells[x][y].invert();
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
}
