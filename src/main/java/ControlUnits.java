import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.List;

/**
 * The ControlUnits class serves as the central controller of the Tetris game.
 *
 * It coordinates the interaction between the rendering system, the game grid,
 * and movement logic. This class is responsible for handling player input,
 * controlling the figure's fall, checking and clearing filled lines,
 * and managing game flow such as starting a new round or restarting after game over.
 *
 * Responsibilities include:
 * <ul>
 *     <li>Handling keyboard events (movement, rotation, acceleration, restart)</li>
 *     <li>Initiating and controlling the automatic fall of the current figure</li>
 *     <li>Locking the figure into the grid and starting a new round</li>
 *     <li>Checking for and clearing fully filled lines</li>
 *     <li>Resetting the game when it's over</li>
 * </ul>
 *
 * This class acting as the glue that drives the overall gameplay logic.
 */

public class ControlUnits {

    private final Render render;
    private final Move move;
    private final Grid grid;
    private Timeline timeline;
    private Color currentColor;
    private final int GameSpeed = 800;
    private final int boost = 50;
    private boolean gameOver = false;
    private final boolean[][] gameGrid;
    private List<int[]> figureCoordinates;
    private final Color[][] colorGrid;

    public ControlUnits(Grid grid, Render render, Move move) {
        this.render = render;
        this.move = move;
        this.grid = grid;
        this.gameGrid = grid.getGrid();
        this.colorGrid = grid.getColors();
    }

    /**
     * Handles key presses during gameplay.
     * LEFT / RIGHT / SPACE: move and rotate the figure.
     * DOWN: temporarily accelerates the fall.
     * ENTER: restarts the game after it ends.
     */
    public void keyPressed(KeyEvent event){
        render.drawBlock(figureCoordinates, Color.BLACK);

        switch (event.getCode()) {
            case LEFT -> figureCoordinates = move.figureMoveLeft(gameGrid, figureCoordinates);
            case RIGHT -> figureCoordinates = move.figureMoveRight(gameGrid, figureCoordinates);
            case SPACE -> figureCoordinates = move.rotate90(grid.getRows(), grid.getCols(), figureCoordinates);
            case DOWN -> timeline.setRate(800.0 / boost);
            case ENTER -> restartGame();
        }
        render.drawBlock(figureCoordinates, currentColor);
    }

    // Returns speed to normal after releasing "DOWN"
    public void keyReleased(KeyEvent event){
        if (event.getCode() == KeyCode.DOWN && timeline != null) {
            timeline.setRate(800.0 / GameSpeed);
        }
    }

    /**
     * Starts the automatic falling animation for the current figure.
     * The figure is moved downward at a regular interval. If it can no longer move down,
     * the animation stops and the provided callback (onNextRound) is executed.
     * @param onNextRound the action to run when the figure can no longer fall
     */
    public void fallStart(Color color, int speed, Runnable onNextRound){
        this.currentColor = color;

            timeline = new Timeline(new KeyFrame(Duration.millis(speed), _ -> {
                render.drawBlock(figureCoordinates, Color.BLACK);

                if (!Logic.isAllowedDown(gameGrid, figureCoordinates)){
                    timeline.stop();
                    onNextRound.run();
                    return;
                }
                figureCoordinates = move.figureMoveDown(gameGrid, figureCoordinates);
                render.drawBlock(figureCoordinates, color);
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
    }

    // Stops the current Timeline-thread
    public void fallEnd(){
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Spawns a new random figure at the top of the grid and begins its descent.
     *
     * The method randomly selects a figure shape and color, computes its initial position,
     * and places it on the game grid. If the newly spawned figure immediately collides with
     * existing blocks, the game is considered over. In that case, the game is stopped and
     * a "Game Over" message is shown. Otherwise, the figure starts falling via {@code fallStart}.
     */
    public void startNewRound(){
        boolean[][] setFigure = Render.whichFigure(); // get a certain Figure
        List<int[]> figure = Render.getFigure(setFigure); // get coordinates for certain Figure
        Color color = Render.getColor(setFigure); // get a Color for certain Figure

        for (int[] i : figure){
            i[0] = i[0] + (grid.getRows()/2);
        }

        if (Logic.isGameOver(gameGrid,figure)){
            gameOver = true;
            render.gameOver("GAME OVER", Color.WHITE);
            render.restart("Press ENTER to restart", Color.WHITE);
            fallEnd();
            return;
        }

        figureCoordinates = figure;
        render.drawBlock(figureCoordinates , color);

        fallStart(color, GameSpeed, this::nextRound);
    }

    // Fixes the current figure, checks for filled rows and calls the next figure
    public void nextRound(){
        grid.fixInGrid(figureCoordinates, currentColor);
        render.drawBlock(figureCoordinates, currentColor);
        checkAndClear();
        startNewRound();
    }

    /**
     * Checks for full lines in the grid and clears them.
     * After each cleared line, shifts the rows above down and redraws the grid.
     */
    private void checkAndClear(){
        int full;
        while ((full = isLineFull(gameGrid)) != -1){
            grid.clearLine(full);
            grid.shiftDown(full);
            render.redrawGrid(gameGrid, colorGrid);
        }
    }

    // Restarts the game
    public void restartGame(){
        if (!gameOver) return;

        gameOver = false;
        grid.setNewGrid();
        render.getCanvas();
        startNewRound();
    }

    /** Auxiliary method for checkAndClear
     * Checks for filled rows in the grid (all is false)
     * @return index of a filled row or -1 if no rows are full.
     */
    private int isLineFull(boolean[][] grid){
        for (int i = grid[0].length - 1; i >= 0; i--) {
            boolean flag = true;
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i]){
                    flag = false;
                    break;
                }
            }
            if (flag) return i;
        }
        return -1;
    }
}