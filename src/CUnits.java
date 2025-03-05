import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    public CUnits(Render render, Logic logic, Move move) {
        this.render = render;
        this.logic = logic;
        this.move = move;
    }

    public void keyPressed(KeyEvent event, int size){

        render.drawBlock(render.getCurrentCoordinates(), size, Color.BLACK);

        switch (event.getCode()) {
            case LEFT -> move.figureMoveLeft(render.getCurrentCoordinates());
            case RIGHT -> move.figureMoveRight(render.getCurrentCoordinates());
            case F -> move.rotate90(render.getCurrentCoordinates());
        }

        render.drawBlock(render.getCurrentCoordinates(), size, currentColor);
    }

    public void fallStart(int Size, Color color, Runnable onNextRound){
        this.currentColor = color;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            render.drawBlock(render.getCurrentCoordinates(), Size, Color.BLACK);
            if (!logic.isAllowedDown(render.getCurrentCoordinates())) {
                timeline.stop();
                onNextRound.run();
                return;
            }
            move.figureMoveDown(render.getCurrentCoordinates());
            render.drawBlock(render.getCurrentCoordinates(), Size, color);
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
            i[0] = i[0] + ((Main.WIDTH/Main.BLOCK_SIZE)/2);
        }

        render.setCurrentCoordinates(figure); // Saving the current coordinates
        render.drawBlock(render.getCurrentCoordinates(), Main.BLOCK_SIZE, color);

        fallStart(Main.BLOCK_SIZE, color, this::nextRound);
    }

    public void nextRound(){
        logic.setNewGrid(render.getCurrentCoordinates(), currentColor);
        startNewRound();
    }
}
