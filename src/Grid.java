import javafx.scene.paint.Color;

import java.util.List;

public class Grid {

    private boolean[][] grid;
    private Color[][] colors;

    public Grid(int rows, int cols) {
        this.grid = new boolean[rows][cols];
        this.colors = new Color[rows][cols];
        setNewGrid();
    }

    public void setNewGrid(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = true;
                colors[i][j] = null;
            }
        }
    }

    public void fixInGrid(List<int[]> coordinates, Color color){

        for (int[] coord : coordinates){
            int x = coord[0];
            int y = coord[1];

            grid[x][y] = false;
            colors[x][y] = color;
        }
        //render.drawBlock(render.getCurrentCoordinates(), color);
    }

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
        //render.redrawGrid(grid, colorGrid);
    }

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
