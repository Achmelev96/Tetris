// Tetris

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

public class Main extends Application {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;
    public static final int BLOCK_SIZE = 20;

    @Override
    public void start(Stage primaryStage) {

        Render render = new Render(WIDTH,HEIGHT);
        render.getCanvas();

        Logic logic = new Logic(render, WIDTH/BLOCK_SIZE,HEIGHT/BLOCK_SIZE);

        Move move = new Move(render, logic);

        // RX Java

        GraphicsContext gc = render.getGC();

        Scene scene = new Scene(render.getRoot(), WIDTH,HEIGHT);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();

        boolean[][] setFigure = Render.whichFigure(); // get a certain Figure
        List<int[]> figure = Render.getFigure(setFigure); // get coordinates for certain Figure
        Color color = Render.getColor(setFigure); // get a Color for certain Figure

        for (int[] i : figure){
            i[0] = i[0] + ((WIDTH/BLOCK_SIZE)/2);
        }

        render.setCurrentCoordinates(figure); // Saving the current coordinates

        render.drawBlock(render.getCurrentCoordinates(), BLOCK_SIZE, color);

        scene.setOnKeyPressed(key -> {
            System.out.println("Key pressed: " + key.getCode());
            switch (key.getCode()){
               case LEFT -> {
                       render.drawBlock(render.getCurrentCoordinates(), BLOCK_SIZE, color.BLACK);
                       move.figureMoveLeft(render.getCurrentCoordinates());
               }
               case RIGHT -> {
                       render.drawBlock(render.getCurrentCoordinates(), BLOCK_SIZE, color.BLACK);
                       move.figureMoveRight(render.getCurrentCoordinates());
               }
               case F -> {
                   render.drawBlock(render.getCurrentCoordinates(), BLOCK_SIZE, color.BLACK);
                   move.rotate90(render.getCurrentCoordinates());
               }
               case DOWN ->{
                       render.drawBlock(render.getCurrentCoordinates(), BLOCK_SIZE, color.BLACK);
                       move.figureMoveDown(render.getCurrentCoordinates());
               }
            }
            //render.getCurrentCoordinates();
            render.drawBlock(render.getCurrentCoordinates(), BLOCK_SIZE, color);

        });
    }
    public static void main(String[] args) {
        launch(args);
    }

}