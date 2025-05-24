import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.List;

// A set of control blocks that bring the program's functionality together
public class ControlUnits {

    private Render render;
    private Logic logic;
    private Move move;
    private Grid grid;
    private Timeline timeline;
    private Color currentColor;
    private final int GameSpeed = 800;
    private final int boost = 50;
    private boolean gameOver = false;
    private boolean[][] gameGrid;
    private List<int[]> figureCoordinates;
    private Color[][] colorGrid;

    public ControlUnits(Grid grid, Render render, Logic logic, Move move) {
        this.render = render;
        this.logic = logic;
        this.move = move;
        this.grid = grid;
        this.gameGrid = grid.getGrid();
        this.colorGrid = grid.getColors();
    }

    public void keyPressed(KeyEvent event){

        render.drawBlock(render.getCurrentCoordinates(), Color.BLACK);

        switch (event.getCode()) {
            case LEFT -> {
                figureCoordinates = move.figureMoveLeft(gameGrid, render.getCurrentCoordinates());
                render.setCurrentCoordinates(figureCoordinates);
            }
            case RIGHT -> {
                figureCoordinates = move.figureMoveRight(gameGrid, render.getCurrentCoordinates());
                render.setCurrentCoordinates(figureCoordinates);
            }
            case SPACE -> {
                figureCoordinates = move.rotate90(grid.getRows(), grid.getCols() ,render.getCurrentCoordinates());
                render.setCurrentCoordinates(figureCoordinates);
            }
            case DOWN -> timeline.setRate(800.0 / boost);
            case ENTER -> restartGame();

        }

        render.drawBlock(render.getCurrentCoordinates(), currentColor);
    } // Control button block

    public void keyReleased(KeyEvent event){
        if (event.getCode() == KeyCode.DOWN && timeline != null) {
            timeline.setRate(800.0 / GameSpeed);
        }
    } // Returns speed to normal after releasing "DOWN"

    public void fallStart(Color color, int speed, Runnable onNextRound){
        this.currentColor = color;

            timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
                render.drawBlock(render.getCurrentCoordinates(), Color.BLACK);

                if (!Logic.isAllowedDown(gameGrid, render.getCurrentCoordinates())){
                    timeline.stop();
                    onNextRound.run();
                    return;
                }
                figureCoordinates = move.figureMoveDown(gameGrid, figureCoordinates);
                render.setCurrentCoordinates(figureCoordinates);
                render.drawBlock(render.getCurrentCoordinates(), color);
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
    } // Responsible for the falling of figures down. If this is not possible, calls the next

    public void fallEnd(){
        if (timeline != null) {
            timeline.stop();
        }
    } // Stops the current thread

    public void startNewRound(){

        boolean[][] setFigure = Render.whichFigure(); // get a certain Figure
        List<int[]> figure = Render.getFigure(setFigure); // get coordinates for certain Figure
        Color color = Render.getColor(setFigure); // get a Color for certain Figure

        for (int[] i : figure){
            i[0] = i[0] + (grid.getRows()/*(render.getWidth()/render.getBlockSize())*//2);
        }

        if (logic.isGameOver(gameGrid,figure)){
            gameOver = true;
            render.gameOver("GAME OVER", Color.WHITE);
            render.restart("Press ENTER to restart", Color.WHITE);
            fallEnd();
            return;
        }

        render.setCurrentCoordinates(figure); // Saving the current coordinates
        render.drawBlock(render.getCurrentCoordinates(), color);

        fallStart(color, GameSpeed, this::nextRound);
    } // launches a new figure and checks for game over

    public void nextRound(){
        grid.fixInGrid(render.getCurrentCoordinates(), currentColor);
        render.drawBlock(render.getCurrentCoordinates(), currentColor);
        checkAndClear();
        startNewRound();
    } // Fixes the current figure, checks for filled rows and calls the next figure

    private void checkAndClear(){
        int full;
        while ((full = isLinefull(gameGrid/*logic.getGrid()*/)) != -1){
            grid.clearLine(full);
            grid.shiftDown(full);
            render.redrawGrid(gameGrid, colorGrid);
        }
    } // Filled Rows Check Block

    public void restartGame(){
        if (!gameOver) return;

        gameOver = false;
        grid.setNewGrid();
        render.getCanvas();
        startNewRound();
    } // Restarts the game

    private int isLinefull(boolean[][] grid){

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
    } // Auxiliary method for checkAndClear
}