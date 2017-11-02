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
                    neighbors += (cells[i + 1][j + 1].isAlive()) ? 1:0;
                    neighbors += (cells[i + 1][j].isAlive()) ? 1:0;;
                    neighbors += (cells[i + 1][j - 1].isAlive()) ? 1:0;;

                    neighbors += (cells[i][j + 1].isAlive()) ? 1:0;;
                    neighbors += (cells[i][j - 1].isAlive()) ? 1:0;;

                    neighbors += (cells[i - 1][j + 1].isAlive()) ? 1:0;;
                    neighbors += (cells[i - 1][j].isAlive()) ? 1:0;;
                    neighbors += (cells[i - 1][j - 1].isAlive()) ? 1:0;;
                }
                // cell is on border
                else {
                    if (i == 0 && j == 0){
                        neighbors += (cells[i + 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][grid.numCols - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i][grid.numCols - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[grid.numRows - 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[grid.numRows - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[grid.numRows - 1][grid.numCols - 1].isAlive()) ? 1:0;;
                    } else if (i == 0 && j == grid.numCols - 1){
                        neighbors += (cells[i + 1][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[grid.numRows - 1][0].isAlive()) ? 1:0;;
                        neighbors += (cells[grid.numRows - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[grid.numRows - 1][j - 1].isAlive()) ? 1:0;;
                    } else if (i == grid.numRows - 1 && j == 0){
                        neighbors += (cells[0][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[0][j].isAlive()) ? 1:0;;
                        neighbors += (cells[0][grid.numCols - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i][grid.numCols - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i - 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][grid.numCols - 1].isAlive()) ? 1:0;;
                    } else if (i == grid.numRows - 1 && j == grid.numCols - 1){
                        neighbors += (cells[0][0].isAlive()) ? 1:0;;
                        neighbors += (cells[0][j].isAlive()) ? 1:0;;
                        neighbors += (cells[0][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i - 1][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j - 1].isAlive()) ? 1:0;;
                    } else if (i == 0){
                        neighbors += (cells[i + 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[grid.numRows - 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[grid.numRows - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[grid.numRows - 1][j - 1].isAlive()) ? 1:0;;
                    } else if (i == grid.numRows - 1){
                        neighbors += (cells[0][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[0][j].isAlive()) ? 1:0;;
                        neighbors += (cells[0][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i - 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j - 1].isAlive()) ? 1:0;;
                    } else if (j == 0){
                        neighbors += (cells[i + 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][grid.numCols - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i][grid.numCols - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i - 1][j + 1].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][grid.numCols - 1].isAlive()) ? 1:0;;
                    } else if (j == grid.numCols - 1){
                        neighbors += (cells[i + 1][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i + 1][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i][j - 1].isAlive()) ? 1:0;;

                        neighbors += (cells[i - 1][0].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j].isAlive()) ? 1:0;;
                        neighbors += (cells[i - 1][j - 1].isAlive()) ? 1:0;;
                    }
                }

                if (neighbors < 2 && (cells[i][j].state == Cell.JUST_BORN || cells[i][j].state == Cell.ALIVE) ){
                    nextGen[i][j] = new Cell(Cell.JUST_DIED);
                } else if ( (neighbors > 3) && (cells[i][j].state == Cell.JUST_BORN || cells[i][j].state == Cell.ALIVE) ){
                    nextGen[i][j] = new Cell(Cell.JUST_DIED);
                } else if ( (neighbors == 3) && (cells[i][j].state == Cell.JUST_DIED || cells[i][j].state == Cell.DEAD) ) {
                    nextGen[i][j] = new Cell(Cell.JUST_BORN);
                } else {
                    if (cells[i][j].state == Cell.JUST_DIED){
                        nextGen[i][j] = new Cell(Cell.DEAD);
                    } else if (cells[i][j].state == Cell.JUST_BORN) {
                        nextGen[i][j] = new Cell(Cell.ALIVE);
                    } else {
                        nextGen[i][j] = cell;
                    }
                }
                neighbors = 0;
            }
        }
        grid.setCells(nextGen);
    }

}
