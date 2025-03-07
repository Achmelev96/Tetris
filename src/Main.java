// Tetris

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 600;
    public static final int BLOCK_SIZE = 30;

    @Override
    public void start(Stage primaryStage) {

        Render render = new Render(WIDTH, HEIGHT, BLOCK_SIZE);
        render.getCanvas();

        Logic logic = new Logic(render, WIDTH/BLOCK_SIZE,HEIGHT/BLOCK_SIZE);
        Move move = new Move(render, logic);
        Scene scene = new Scene(render.getRoot(), WIDTH,HEIGHT);
        ControlUnits controlUnits = new ControlUnits(render, logic, move);

        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();

        controlUnits.startNewRound();

        scene.setOnKeyPressed((KeyEvent event) -> controlUnits.keyPressed(event));
        scene.setOnKeyReleased((KeyEvent event) -> controlUnits.keyReleased(event));

        primaryStage.setOnCloseRequest(event -> controlUnits.fallEnd());
    }

    public static void main(String[] args) {
        launch(args);
    }
}