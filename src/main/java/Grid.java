import javafx.scene.paint.Color;
import java.util.List;

public class Grid {

    /**
     * The Grid class represents the game board as a Matrix of booleans and Matrix of Colors
     * The grid is created once during initialization and is used further.
     * Empty cells = True, filled cells = False
     */

    private final boolean[][] grid;
    private final Color[][] colors;

    public Grid(int rows, int cols) {
        this.grid = new boolean[cols][rows];
        this.colors = new Color[cols][rows];
        setNewGrid();
    }

    // Clears the playing field: all cells become empty and colorless.
    public void setNewGrid(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = true;
                colors[i][j] = null;
            }
        }
    }

    // Fixes the figure into a grid, marking the cells as filled and colored
    public void fixInGrid(List<int[]> coordinates, Color color){

        for (int[] coord : coordinates){
            int x = coord[0];
            int y = coord[1];

            grid[x][y] = false;
            colors[x][y] = color;
        }
    }

    /**
     * Shifts the entire grid from x down 1 cell
     * used in conjunction with the method clearLine
     * @param x line
     */
    public void shiftDown(int x){
        if (x == -1) return;

        for (int i = x; i > 0; i--){
            for (int j = 0; j < grid.length; j++){
                grid[j][i] = grid[j][i-1];
                colors[j][i] = colors[j][i-1];
            }
        }

        for (int j = 0; j < grid.length; j++){
            grid [0][j] = true;
            colors[j][0] = null;
        }
    }

    //Clears the entire line x
    public void clearLine(int x){

        if (x == -1) return;

        for (int i = 0; i < grid.length; i++){
            grid[i][x] = true;
            colors[i][x] = null;
        }
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public Color[][] getColors() {
        return colors;
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }
}
