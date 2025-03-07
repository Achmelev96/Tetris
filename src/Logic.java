
import java.util.List;
import javafx.scene.paint.Color;

public class Logic {

    private boolean[][] grid;
    private Render render;
    private Color[][] colorGrid;

    // Responsible for checking, storing grids and block fixation
    public Logic(Render render, int rows, int cols) {

        this.render = render;
        this.grid = getLogikMatrix();
        this.colorGrid = new Color[rows][cols];

    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void resetGrid() { // Resetting the state of the grids
        this.grid = getLogikMatrix();
        this.colorGrid = new Color[grid.length][grid[0].length];
    }

    public void fixInGrid(List <int[]> coordinates, Color color){

        for (int[] coord : coordinates){
            int x = coord[0];
            int y = coord[1];

            grid[x][y] = false;
            colorGrid[x][y] = color;
        }

        render.drawBlock(render.getCurrentCoordinates(), color);
    } // fixes the figure in the grid

    public Color[][] getColorGrid(){
        return colorGrid;
    }

    public void clearLine(int x){

        if (x == -1) return;

        for (int i = 0; i < grid.length; i++){
            grid[i][x] = true;
            colorGrid[i][x] = null;
        }
    } // Clears the line by the received row number

    public void shiftDown(int x){
        if (x == -1) return;

        for (int i = x; i > 0; i--){
            for (int j = 0; j < grid.length; j++){
                grid[j][i] = grid[j][i-1];
                colorGrid[j][i] = colorGrid[j][i-1];
            }
        }

        for (int j = 0; j < grid.length; j++){
            grid [0][j] = true;
            colorGrid[j][0] = null;
        }

        render.redrawGrid(grid, colorGrid);
    } // Gets the row number. Shifts everything above it one row down

    public boolean isAllowedDown(List<int[]> coordinates) {
        getGrid();

        for (int[] p : coordinates) {
            int row = p[0];
            int col = p[1] + 1;

            if (col >= grid[0].length || !grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllowedRight(List<int[]> coordinates) {
        getGrid();

        for (int[] p : coordinates) {
            int row = p[0] + 1;
            int col = p[1];

            if (row >= grid.length || !grid[row][col]){
                return false;
            }
        }
        return true;
    }

    public boolean isAllowedLeft(List <int[]> coordinates){
        getGrid();

        for (int[] p : coordinates) {
            int row = p[0] - 1;
            int col = p[1];

            if (row < 0 || !grid[row][col]){
                return false;
            }
        }
        return true;
    }

    public boolean[][] getLogikMatrix(){

        int rows = render.getWidth() / render.getBlockSize();
        int cols = render.getHeight() / render.getBlockSize();

        boolean[][] matrix = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = true;
            }
        }

        return matrix;
    } // Returns an empty logical matrix where "True" is an empty cell

    public boolean isGameOver(List<int[]> newFigure) {
        for (int[] coord : newFigure) {
            int x = coord[0];
            int y = coord[1];

            if (!grid[x][y]){
                return true;
            }
        }
        return false;
    }
}