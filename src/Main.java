// Tetris

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;

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
        GraphicsContext gc = render.getGC();
        Scene scene = new Scene(render.getRoot(), WIDTH,HEIGHT);
        CUnits controlUnits = new CUnits(render,logic, move);

        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();

        controlUnits.startNewRound();

        scene.setOnKeyPressed((KeyEvent event) -> controlUnits.keyPressed(event, BLOCK_SIZE));

        primaryStage.setOnCloseRequest(event -> controlUnits.fallEnd());

    }

    public static void main(String[] args) {
        launch(args);
    }

}