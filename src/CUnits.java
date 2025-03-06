import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.List;

public class CUnits {

    private Render render;
    private Logic logic;
    private Move move;
    private Timeline timeline;
    private Color currentColor;
    private final int normalSpeed = 1000;
    private final int fastSpeed = 100;

    public CUnits(Render render, Logic logic, Move move) {
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
            case DOWN -> fallStart(currentColor, fastSpeed, this::nextRound);
        }

        render.drawBlock(render.getCurrentCoordinates(), currentColor);
    }

    public void fallStart(Color color, int speed,Runnable onNextRound){
        this.currentColor = color;

        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
            render.drawBlock(render.getCurrentCoordinates(), Color.BLACK);
            if (!logic.isAllowedDown(render.getCurrentCoordinates())) {
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

    public void keyReleased(KeyEvent event){
        if (event.getCode() == KeyCode.DOWN) {
            fallStart(currentColor, normalSpeed, this::nextRound);
        }
    }

    public void startNewRound(){

        boolean[][] setFigure = Render.whichFigure(); // get a certain Figure
        List<int[]> figure = Render.getFigure(setFigure); // get coordinates for certain Figure
        Color color = Render.getColor(setFigure); // get a Color for certain Figure

        for (int[] i : figure){
            i[0] = i[0] + ((render.getWidth()/render.getBlockSize())/2);
        }

        render.setCurrentCoordinates(figure); // Saving the current coordinates
        render.drawBlock(render.getCurrentCoordinates(), color);

        fallStart(color, normalSpeed,this::nextRound);
    }

    public void nextRound(){
        logic.fixinGrid(render.getCurrentCoordinates(), currentColor);
        checkAndClear();
        startNewRound();
    }

    public void checkAndClear(){
        int full;
        while ((full = logic.isLinefull()) != -1){
            logic.clearLine(full);
            logic.shiftDown(full);
            render.redrawGrid(logic.getGrid(), logic.getColorGrid());
        }
    }
}
