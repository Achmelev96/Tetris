import java.util.List;
import java.util.ArrayList;

public class Move {

    // Responsible for the movement of figures and rotation

    public List<int[]> figureMoveDown(boolean[][] grid, List<int[]> coordinates) {
        if (Logic.isAllowedDown(grid, coordinates)) {
            for (int[] pointer : coordinates) {
                pointer[1] += 1;
            }
        }
        return coordinates;
    }

    public List<int[]> figureMoveRight(boolean[][] grid, List<int[]> coordinates) {
        if (Logic.isAllowedRight(grid, coordinates)) {
            for (int[] pointer : coordinates) {
                pointer[0] += 1;
            }
        }
        return coordinates;
    }

    public List<int[]> figureMoveLeft(boolean[][] grid, List<int[]> coordinates) {
        if (Logic.isAllowedLeft(grid, coordinates)) {
            for (int[] pointer : coordinates) {
                pointer[0] -= 1;
            }
        }
        return coordinates;
    }

     // Rotates the given figure 90 degrees around its center.
     // Adjusts coordinates to keep the figure within grid bounds.
    public List<int[]> rotate90(int rows, int cols, List<int[]> coordinates) {

        int[] center = findCenter(coordinates);
        int cx = center[0];
        int cy = center[1];

        List<int[]> rotatedCoords = new ArrayList<>();
        for (int[] coord : coordinates) {
            int x = coord[0] - cx;
            int y = coord[1] - cy;

            int newX = y;
            int newY = -x;

            coord[0] = newX + cx;
            coord[1] = newY + cy;

            rotatedCoords.add(new int[]{newX + cx, newY + cy});
        }

        // Protection against out-of-bounds
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        for (int[] coord : rotatedCoords) {
            minX = Math.min(minX, coord[0]);
            maxX = Math.max(maxX, coord[0]);
        }

        int shiftX = 0;
        if (minX < 0) {
            shiftX = -minX;
        } else if (maxX >= rows) {
            shiftX = (cols - 1) - maxX;
        }

        for (int i = 0; i < coordinates.size(); i++) {
            coordinates.get(i)[0] = rotatedCoords.get(i)[0] + shiftX;
            coordinates.get(i)[1] = rotatedCoords.get(i)[1];
        }

        return coordinates;
    }

    /**
     * Auxiliary method for rotate90
     * finds the approximate center of the figure
     * @return 2 central points of the figure
     */
    private int[] findCenter(List<int[]> coordinates) {
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