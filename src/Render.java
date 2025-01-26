import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Render {

    // Figure L
    public static final boolean[][] Block_1 ={
            {true,false},
            {true,false},
            {true,true}
    };

    // Figure T
    public static final boolean[][] Block_2 ={
            {true, true,true},
            {false,true,false},
            {false,true,false}
    };

    // Figure _|_
    public static final boolean[][] Block_3 ={
            {false,true,false},
            {true, true,true}
    };

    // Figure Cube
    public static final boolean[][] Block_4 ={
            {true,true},
            {true,true}
    };

    // Figure Stick
    public static final boolean[][] Block_5 ={
            {true,true,true,true}
    };

    private final StackPane root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private List<int[]> currentCoordinates;

    public Render(double width, double height){

        root = new StackPane();
        canvas = new Canvas(width, height);

        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        currentCoordinates = new ArrayList<>();
    }

    public GraphicsContext getGC() {
        return gc;
    }

    public void getCanvas(){

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

    }

    public List<int[]> getCurrentCoordinates(){
        return currentCoordinates;
    }

    public void setCurrentCoordinates(List<int[]> coordinates){
        this.currentCoordinates = coordinates;
    }

    public void drawBlock(List<int[]> coordinates, int size, Color color) {

        gc.setFill(color);
        for (int draw[] : coordinates) {
            int x = draw[0];
            int y = draw[1];
            gc.fillRect(x*size, y*size, size, size);

        }
    }

    public static List<int[]> getFigure(boolean[][] figure){

        List<int[]> coordinats = new ArrayList<>();

        for(int i = 0; i<figure.length; i++){
            for(int j = 0; j<figure[i].length; j++){
                if(figure[i][j]){
                    coordinats.add(new int[]{i,j});
                }
            }
        }
        return coordinats;
    } // set Coordinates for certain Figure

    public static boolean[][] whichFigure(){

        return switch (new Random().nextInt(5)){
            case 0 -> Block_1;
            case 1 -> Block_2;
            case 2 -> Block_3;
            case 3 -> Block_4;
            case 4 -> Block_5;
            default -> Block_1;
        };
    } // set a concrete Figure from Block 1-5

    /*public void clearPlace(List<int[]> coordinates) {

        gc.setFill(Color.BLACK);
        for (int draw[] : coordinates) {
            int x = draw[0];
            int y = draw[1];
            gc.fillRect(x*20, y*20, 20, 20);

        }
    }*/

    public static Color getColor(boolean[][] block){

        if (Arrays.deepEquals(block, Block_1)) return Color.RED;
        if (Arrays.deepEquals(block, Block_2)) return Color.BLUE;
        if (Arrays.deepEquals(block, Block_3)) return Color.GREEN;
        if (Arrays.deepEquals(block, Block_4)) return Color.YELLOW;
        return Color.PURPLE;
    }

    public StackPane getRoot(){
        return root;
    }
}
