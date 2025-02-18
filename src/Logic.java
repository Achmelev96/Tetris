import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Logic {

    private boolean[][] grid;
    private Render render;

    public Logic(Render render, int rows, int cols) {
        this.render = render;
        this.grid = getLogikMatrix();

    }

    public boolean[][] getGrid() {
        return grid;
    }

    public boolean isAllowedDown(List<int[]> coordinates) {
        getGrid();

        Map<Integer, int[]> bottom = new HashMap<>();

        for (int[] p : coordinates) {
            int row = p[0];
            int col = p[1];

            if (!bottom.containsKey(col) || row > bottom.get(col)[0]) {
                bottom.put(col, p);
            }
        }

        for (Map.Entry<Integer, int[]> entry : bottom.entrySet()) {
            int[] bottomPoint = entry.getValue();
            int row = bottomPoint[0];
            int col = bottomPoint[1] + 1;

            if (col >= 40 || !grid[row][col])
                return false;
        }
        return true;
    }

    public boolean isAllowedRight(List<int[]> coordinates) {
        getGrid();

        Map<Integer, int[]> right = new HashMap<>();

        for (int[] p : coordinates) {
            int row = p[0];
            int col = p[1];

            if (!right.containsKey(row) || col > right.get(row)[1]) {
                right.put(row, p);
            }
        }

        for (Map.Entry<Integer, int[]> entry : right.entrySet()) {
            int[] rightmostPoint = entry.getValue();
            int row = rightmostPoint[0] + 1;
            int col = rightmostPoint[1];

            if (row >= 20 || !grid[row][col])
                return false;
        }
        return true;
    }

    public boolean isAllowedLeft(List <int[]> coordinates){
        getGrid();

        Map<Integer, int[]> left = new HashMap<>();

        for (int[] p : coordinates){
            int row = p[0];
            int col = p[1];

            if (!left.containsKey(row) || col < left.get(row)[1]) {
                left.put(row, p);
            }
        }

        for (Map.Entry<Integer, int[]> entry : left.entrySet()) {
            int[] leftmostPoint = entry.getValue();
            int row = leftmostPoint[0] - 1;
            int col = leftmostPoint[1];

            if (row < 0 || !grid[row][col])
                return false;
        }
        return true;
    }

    public  List<int[]> getLowerCoordinates(List <int[]> coordinates) {

        List<int[]> lowerCoordinates = new ArrayList<>();
        int maxRow = Integer.MIN_VALUE;

        for (int[] i : coordinates){
            if (i[0] > maxRow){
                maxRow = i[0];
            }
        }

        for (int[] i : coordinates){
            if (i[0] == maxRow){
                lowerCoordinates.add(i);
            }
        }

        return lowerCoordinates;
    }

    public List<int[]> getRightCoordinates(List <int[]> coordinates){

        List<int[]> rightCoordinates = new ArrayList<>();
        int rightRow = Integer.MIN_VALUE;

        for (int[] i : coordinates){
            if (i[1] > rightRow){
                rightRow = i[1];
            }
        }

        for (int[] i : coordinates){
            if (i[1] == rightRow){
                rightCoordinates.add(i);
            }
        }
        return rightCoordinates;
    }

    public List<int[]> getLeftCoordinates(List <int[]> coordinates){

        List<int[]> leftCoordinates = new ArrayList<>();
        //List<int[]> figure = Render.getCoordinats(Render.getFigure());
        int leftRow = Integer.MIN_VALUE;

        for (int[] i : coordinates) {
            if (i[1] < leftRow){
                leftRow = i[1];
            }
        }

        for (int[] i : coordinates){
            if (i[1] == leftRow){
                leftCoordinates.add(i);
            }
        }
        return leftCoordinates;
    }

    public boolean[][] getLogikMatrix() {

        int rows = render.getWidth() / 20;
        int cols = render.getHeight() / 20;

        boolean[][] matrix = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = true;
            }
        }

        return matrix;
    }
}