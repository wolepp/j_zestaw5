package watki;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box {

    private double x, y;
    private double w, h;
    private boolean hasBallInside = false;

    private Canvas canvas;

    public Box(GraphicsContext gc) {
        this.canvas = gc.getCanvas();

        this.w = 200;
        this.h = this.w;

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

    public synchronized void enter() throws InterruptedException {
        if (hasBallInside)
            this.wait();

        hasBallInside = true;
    }

    public synchronized void exit() {
        hasBallInside = false;
        this.notify();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }
}
