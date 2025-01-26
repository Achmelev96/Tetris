import java.util.ArrayList;
import java.util.List;

public class Logic {

    private List<int[]> coordinates;
    private static boolean[][] grid;
    private Render render;

    public Logic(Render render, int rows, int cols) {
        this.render = render;
        this.coordinates = new ArrayList<>(render.getCurrentCoordinates());
        this.grid = Main.getLogikMatrix(rows, cols);

    }

    public static boolean[][] getGrid() {
        return grid;
    }

    public void figureMoveDown(List<int[]> coordinates) {

        if (isAlowedDown()) {
            for (int[] newcord : coordinates) {
                newcord[1] += 1;
            }
        }
        render.setCurrentCoordinates(coordinates);
    }

    public void figureMoveRight(List<int[]> coordinates) {

        if (isAlowedRight()) {
            for (int[] newcord : coordinates) {
                newcord[0] += 1;
            }
        }
        render.setCurrentCoordinates(coordinates);
    }

    public void figureMoveLeft(List<int[]> coordinates) {

        if (isAlowedLeft()) {
            for (int[] newcord : coordinates) {
                newcord[0] -= 1;
            }
        }
        render.setCurrentCoordinates(coordinates);
    }

    public boolean isAlowedDown(){

        List<int[]> lower = getLowerCoordinates();

        for (int point[] : lower) {

            int row = point[0];
            int col = point[1];

            if (row >= grid.length){
                return false;
            }

            if (!grid[row+1][col]){
                return false;
            }
        }
        return true;
    }

    public  List<int[]> getLowerCoordinates(){

        List<int[]> lowerCoordinates = new ArrayList<>();
        //List<int[]> figure = Render.getCoordinats(Render.getFigure());
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

    public List<int[]> getRightCoordinates(){

        List<int[]> rightCoordinates = new ArrayList<>();
        //List<int[]> figure = Render.getCoordinats(Render.getFigure());
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

    public boolean isAlowedRight(){

        List<int[]> right = getRightCoordinates();

        for (int point[] : right) {

            int row = point[0];
            int col = point[1];

            if (col >= grid[0].length){
                return false;
            }

            if (!grid[row][col+1]){
                return false;
            }
        }
        return true;
    }

    public List<int[]> getLeftCoordinates(){

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

    public boolean isAlowedLeft(){

        List<int[]> left = getLeftCoordinates();

        for (int point[] : left) {

            int row = point[0];
            int col = point[1];

            if (col - 1 < 0){
                return false;
            }

            if (!grid[row][col-1]){
                return false;
            }
        }
        return true;
    }

    public void rotate90(List<int[]> coordinates) {
        //if (isAllowedRotation()) {
            int[] center = findCenter(coordinates);
            int cx = center[0];
            int cy = center[1];

            for (int[] coord : coordinates) {
                int x = coord[0] - cx;
                int y = coord[1] - cy;

                int newX = y;
                int newY = -x;

                coord[0] = newX + cx;
                coord[1] = newY + cy;
            //}
        }

        render.setCurrentCoordinates(coordinates);
    }

    public int[] findCenter(List<int[]> coordinates) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        for (int[] coord : coordinates) {
            minX = Math.min(minX, coord[0]);
            maxX = Math.max(maxX, coord[0]);
            minY = Math.min(minY, coord[1]);
            maxY = Math.max(maxY, coord[1]);
        }

        int centerX = (minX + maxX) / 2;
        int centerY = (minY + maxY) / 2;
        return new int[] { centerX, centerY };
    }
}