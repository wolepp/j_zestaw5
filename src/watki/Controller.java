package watki;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;

public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    private Button kulkaButton;

    private GraphicsContext gc;
    private Box box;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        clear(gc);
        box = new Box(gc);
    }

    private void clear(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void addKulka(ActionEvent actionEvent) {
        Ball ball = new Ball(gc, box);
        ball.start();
    }

    public void switchKulkaState(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
    }

    public void clearGC(ActionEvent actionEvent) {
        clear(gc);
    }
}
