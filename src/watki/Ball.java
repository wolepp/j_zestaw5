package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.Random;

public class Ball extends java.lang.Thread {

    // promień kulki
    private static double r;
    // prędkość kulki
    private static double v;
    // współrzędne lewego górnego rogu kulki
    private double x, y;
    // składowe wektora prędkości
    private double vx, vy;
    //TODO: randomowy ma być
    private Color color;

    // granice płótna
    private double top, bottom, left, right;


    private GraphicsContext gc;

    static {
        r = 10;
        v = 1.0;
    }

    //TODO: kulka ma być kolorowa
    public Ball(GraphicsContext GC) {
        this.gc = GC;

        top = 0;
        left = 0;
        bottom = gc.getCanvas().getHeight();
        right = gc.getCanvas().getWidth();

        x = Math.random() * (right - 2*r);
        y = Math.random() * (bottom - 2*r);

        // maksymalna prędkość - v pikseli na cykl
        vx = Math.random() * v;
        vy = Math.sqrt(v - Math.pow(vx, 2));
    }

    //TODO: każda kulka ma się sama odrysować
    private void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, right, bottom);
    }

    public void run() {
        clearCanvas();
        for (int i = 0; i < 2000; i++) {
            paint(Color.BLACK);
            move();
            paint(Color.PINK);
            move();
        }
    }

    private void clear() {
        gc.clearRect(x, y, r, r);
    }

    private void move() {
        if (x + vx < left || x + 2*r + vx > right)
            vx = -vx;
        if (y + vy < top || y + 2*r + vy > bottom)
            vy = -vy;

        x += vx;
        y += vy;
    }

    private void paint(Color c) {
        gc.setFill(c);
        gc.fillOval(x, y, 2*r, 2*r);
        gc.setFill(Color.BLACK);
    }

}
