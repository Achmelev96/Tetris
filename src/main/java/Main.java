// Tetris
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 600;
    public static final int BLOCK_SIZE = 30;

    @Override
    public void start(Stage primaryStage) {

        Grid grid = new Grid(HEIGHT / BLOCK_SIZE, WIDTH / BLOCK_SIZE);

        Render render = new Render(WIDTH, HEIGHT, BLOCK_SIZE);
        render.getCanvas();

        Move move = new Move();
        Scene scene = new Scene(render.getRoot(), WIDTH, HEIGHT);
        ControlUnits controlUnits = new ControlUnits(grid, render, move);

        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();

        controlUnits.startNewRound();

        scene.setOnKeyPressed((KeyEvent event) -> controlUnits.keyPressed(event));
        scene.setOnKeyReleased((KeyEvent event) -> controlUnits.keyReleased(event));

        primaryStage.setOnCloseRequest(_ -> controlUnits.fallEnd());
    }

    public static void main(String[] args) {
        launch(args);
    }
}