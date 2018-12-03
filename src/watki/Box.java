package watki;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box {

    private double x, y;
    private double w, h;

    private Canvas canvas;

    public Box(GraphicsContext gc) {
        this.canvas = gc.getCanvas();

        this.w = this.h = 60;

        this.x = Math.random() * (canvas.getWidth() - w);
        this.y = Math.random() * (canvas.getWidth() - h);
    }

    public void paint() {
        Platform.runLater(this::painting);
    }

    public void painting() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, w, h);
    }

    public synchronized void enter() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void exit() {
        this.notifyAll();
    }

    public boolean isInside(double x, double y, double r) {
        return (x + 2*r >= this.x && x <= this.x + this.w
                && y + 2*r >= this.y && y <= this.y + this.h);
    }
}
