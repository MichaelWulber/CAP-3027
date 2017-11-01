public class ToroidalTopology implements Topology {
    @Override
    public void step(CellGrid grid) {
        int neighbors = 0;
        Cell[][] cells = grid.getCells();
        Cell[][] nextGen = new Cell[grid.numRows][grid.numCols];
        Cell cell;

        for (int i = 0; i < grid.numRows; ++i){
            for (int j = 0; j < grid.numCols; j++){
                cell = cells[i][j];

                // cell is not on border
                if ( (i > 0 && i < grid.numRows - 1) && (j > 0 && j < grid.numRows - 1) ) {
                    neighbors += cells[i + 1][j + 1].state;
                    neighbors += cells[i + 1][j].state;
                    neighbors += cells[i + 1][j - 1].state;

                    neighbors += cells[i][j + 1].state;
                    neighbors += cells[i][j - 1].state;

                    neighbors += cells[i - 1][j + 1].state;
                    neighbors += cells[i - 1][j].state;
                    neighbors += cells[i - 1][j - 1].state;
                }
                // cell is on border
                else {
                    if (i == 0 && j == 0){
                        neighbors += cells[i + 1][j + 1].state;
                        neighbors += cells[i + 1][j].state;
                        neighbors += cells[i + 1][grid.numCols - 1].state;

                        neighbors += cells[i][j + 1].state;
                        neighbors += cells[i][grid.numCols - 1].state;

                        neighbors += cells[grid.numRows - 1][j + 1].state;
                        neighbors += cells[grid.numRows - 1][j].state;
                        neighbors += cells[grid.numRows - 1][grid.numCols - 1].state;
                    } else if (i == 0 && j == grid.numCols - 1){
                        neighbors += cells[i + 1][0].state;
                        neighbors += cells[i + 1][j].state;
                        neighbors += cells[i + 1][j - 1].state;

                        neighbors += cells[i][0].state;
                        neighbors += cells[i][j - 1].state;

                        neighbors += cells[grid.numRows - 1][0].state;
                        neighbors += cells[grid.numRows - 1][j].state;
                        neighbors += cells[grid.numRows - 1][j - 1].state;
                    } else if (i == grid.numRows - 1 && j == 0){
                        neighbors += cells[0][j + 1].state;
                        neighbors += cells[0][j].state;
                        neighbors += cells[0][grid.numCols - 1].state;

                        neighbors += cells[i][j + 1].state;
                        neighbors += cells[i][grid.numCols - 1].state;

                        neighbors += cells[i - 1][j + 1].state;
                        neighbors += cells[i - 1][j].state;
                        neighbors += cells[i - 1][grid.numCols - 1].state;
                    } else if (i == grid.numRows - 1 && j == grid.numCols - 1){
                        neighbors += cells[0][0].state;
                        neighbors += cells[0][j].state;
                        neighbors += cells[0][j - 1].state;

                        neighbors += cells[i][0].state;
                        neighbors += cells[i][j - 1].state;

                        neighbors += cells[i - 1][0].state;
                        neighbors += cells[i - 1][j].state;
                        neighbors += cells[i - 1][j - 1].state;
                    } else if (i == 0){
                        neighbors += cells[i + 1][j + 1].state;
                        neighbors += cells[i + 1][j].state;
                        neighbors += cells[i + 1][j - 1].state;

                        neighbors += cells[i][j + 1].state;
                        neighbors += cells[i][j - 1].state;

                        neighbors += cells[grid.numRows - 1][j + 1].state;
                        neighbors += cells[grid.numRows - 1][j].state;
                        neighbors += cells[grid.numRows - 1][j - 1].state;
                    } else if (i == grid.numRows - 1){
                        neighbors += cells[0][j + 1].state;
                        neighbors += cells[0][j].state;
                        neighbors += cells[0][j - 1].state;

                        neighbors += cells[i][j + 1].state;
                        neighbors += cells[i][j - 1].state;

                        neighbors += cells[i - 1][j + 1].state;
                        neighbors += cells[i - 1][j].state;
                        neighbors += cells[i - 1][j - 1].state;
                    } else if (j == 0){
                        neighbors += cells[i + 1][j + 1].state;
                        neighbors += cells[i + 1][j].state;
                        neighbors += cells[i + 1][grid.numCols - 1].state;

                        neighbors += cells[i][j + 1].state;
                        neighbors += cells[i][grid.numCols - 1].state;

                        neighbors += cells[i - 1][j + 1].state;
                        neighbors += cells[i - 1][j].state;
                        neighbors += cells[i - 1][grid.numCols - 1].state;
                    } else if (j == grid.numCols - 1){
                        neighbors += cells[i + 1][0].state;
                        neighbors += cells[i + 1][j].state;
                        neighbors += cells[i + 1][j - 1].state;

                        neighbors += cells[i][0].state;
                        neighbors += cells[i][j - 1].state;

                        neighbors += cells[i - 1][0].state;
                        neighbors += cells[i - 1][j].state;
                        neighbors += cells[i - 1][j - 1].state;
                    }
                }

                if (neighbors < 2){
//                    if (cell.state == Cell.ALIVE){
//                        System.out.println("cell " + i + ", " + j + " died of loneliness " + neighbors);
//                    }
                    nextGen[i][j] = new Cell(Cell.DEAD);
                } else if (neighbors > 3){
//                    if (cell.state == Cell.ALIVE){
//                        System.out.println("cell " + i + ", " + j + " died of loneliness " + neighbors);
//                    }
                    nextGen[i][j] = new Cell(Cell.DEAD);
                } else if (neighbors == 3) {
//                    if (cell.state == Cell.DEAD){
//                        System.out.println("cell " + i + ", " + j + " is born " + neighbors);
//                    }
                    nextGen[i][j] = new Cell(Cell.ALIVE);
                } else {
                    nextGen[i][j] = cell;
                }
                neighbors = 0;
            }
        }
        grid.setCells(nextGen);
    }

}
