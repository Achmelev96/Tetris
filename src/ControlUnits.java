import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.List;

public class ControlUnits {

    private Render render;
    private Logic logic;
    private Move move;
    private Timeline timeline;
    private Color currentColor;
    private final int speed = 800;
    private final int boost = 50;

    public ControlUnits(Render render, Logic logic, Move move) {
        this.render = render;
        this.logic = logic;
        this.move = move;
    }

    public void keyPressed(KeyEvent event){

        render.drawBlock(render.getCurrentCoordinates(), Color.BLACK);

        switch (event.getCode()) {
            case LEFT -> move.figureMoveLeft(render.getCurrentCoordinates());
            case RIGHT -> move.figureMoveRight(render.getCurrentCoordinates());
            case F -> move.rotate90(render.getCurrentCoordinates());
            case DOWN -> timeline.setRate(1000.0 / boost);
        }

        render.drawBlock(render.getCurrentCoordinates(), currentColor);
    }

    public void keyReleased(KeyEvent event){
        if (event.getCode() == KeyCode.DOWN && timeline != null) {
            timeline.setRate(1000.0 / speed);
        }
    }

    public void fallStart(Color color, int speed, Runnable onNextRound){
        this.currentColor = color;

            timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
                render.drawBlock(render.getCurrentCoordinates(), Color.BLACK);

                if (!logic.isAllowedDown(render.getCurrentCoordinates())){
                    timeline.stop();
                    onNextRound.run();
                    return;
                }
                move.figureMoveDown(render.getCurrentCoordinates());
                render.drawBlock(render.getCurrentCoordinates(), color);
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
    }

    public void fallEnd(){
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void startNewRound(){

        boolean[][] setFigure = Render.whichFigure(); // get a certain Figure
        List<int[]> figure = Render.getFigure(setFigure); // get coordinates for certain Figure
        Color color = Render.getColor(setFigure); // get a Color for certain Figure

        for (int[] i : figure){
            i[0] = i[0] + ((render.getWidth()/render.getBlockSize())/2);
        }

        if(logic.isGameOver(figure)){
            System.out.println("Game Over");
            render.textDraw("GAME OVER", Color.WHITE);
            fallEnd();
            return;
        }

        render.setCurrentCoordinates(figure); // Saving the current coordinates
        render.drawBlock(render.getCurrentCoordinates(), color);

        fallStart(color, speed, this::nextRound);
    }

    public void nextRound(){
        logic.fixInGrid(render.getCurrentCoordinates(), currentColor);
        checkAndClear();
        startNewRound();
    }

    public void checkAndClear(){
        int full;

        while ((full = isLinefull(logic.getGrid())) != -1){
            logic.clearLine(full);
            logic.shiftDown(full);
            render.redrawGrid(logic.getGrid(), logic.getColorGrid());

        }
    }

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
    }
}