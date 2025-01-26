import java.util.List;

public class Move {

    private Render render;
    private Logic logic;

    public Move(Render render, Logic logic) {
        this.render = render;
        this.logic = logic;
    }

    public void figureMoveDown(List<int[]> coordinates) {

        if (logic.isAlowedDown()) {
            for (int[] newcord : coordinates) {
                newcord[1] += 1;
            }
        }
        render.setCurrentCoordinates(coordinates);
    }

    public void figureMoveRight(List<int[]> coordinates) {

        if (logic.isAlowedRight()) {
            for (int[] newcord : coordinates) {
                newcord[0] += 1;
            }
        }
        render.setCurrentCoordinates(coordinates);
    }

    public void figureMoveLeft(List<int[]> coordinates) {

        if (logic.isAlowedLeft()) {
            for (int[] newcord : coordinates) {
                newcord[0] -= 1;
            }
        }
        render.setCurrentCoordinates(coordinates);
    }

    public void rotate90(List<int[]> coordinates) {
        //if (logic.isAllowedRotation()) {
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
