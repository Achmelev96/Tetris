// Tetris

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
        GraphicsContext gc = render.getGC();
        Scene scene = new Scene(render.getRoot(), WIDTH,HEIGHT);
        CUnits cUnits = new CUnits(render,logic, move);

        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();

        cUnits.startNewRound();

        scene.setOnKeyPressed((KeyEvent event) -> cUnits.keyPressed(event, BLOCK_SIZE));

        primaryStage.setOnCloseRequest(event -> cUnits.fallEnd());

    }

    public static void main(String[] args) {
        launch(args);
    }

}