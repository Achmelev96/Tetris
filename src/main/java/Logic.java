import java.util.List;

public class Logic {

    // Responsible for checking the availability of movement and the game-over conditions

    // For movement the following coordinates from the edge of the figures are checked.
    // If at least one next cell is occupied or goes beyond the grid - return false
    public static boolean isAllowedDown(boolean[][] grid, List<int[]> coordinates) {
        for (int[] p : coordinates) {
            int row = p[0];
            int col = p[1] + 1;

            if (col >= grid[0].length || !grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllowedRight(boolean[][] grid, List<int[]> coordinates) {
        for (int[] p : coordinates) {
            int row = p[0] + 1;
            int col = p[1];

            if (row >= grid.length || !grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllowedLeft(boolean[][] grid, List <int[]> coordinates){
        for (int[] p : coordinates) {
            int row = p[0] - 1;
            int col = p[1];

            if (row < 0 || !grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    // Game over check
    // If the starting position is occupied - game over
    public static boolean isGameOver(boolean[][] grid, List<int[]> newFigure) {
        for (int[] coord : newFigure) {
            int x = coord[0];
            int y = coord[1];

            if (!grid[x][y]) {
                return true;
            }
        }
        return false;
    }
}