package watki;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
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
    private Color color;

    // granice płótna
    private double top, bottom, left, right;

    private GraphicsContext gc;

    static {
        r = 10;
        v = 2.0;
    }

    public Ball(GraphicsContext GC) {
        this.gc = GC;

        top = 0;
        left = 0;
        bottom = gc.getCanvas().getHeight();
        right = gc.getCanvas().getWidth();

        // ustalenie losowego położenia
        x = Math.random() * (right - 2*r);
        y = Math.random() * (bottom - 2*r);

        // maksymalna prędkość - v pikseli na cykl
        vx = Math.random() * v;
        vy = Math.sqrt(v - Math.pow(vx, 2));

        double r, g, b;
        do {
            r = Math.random();
            g = Math.random();
            b = Math.random();
        } while (r + g + b > 2.5);
        this.color = new Color(r, g, b, 1.0);
    }

    private void clearCanvas() {
        synchronized (gc) {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, right, bottom);
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // ============================================== Lepiej Platform.runLater czy używać synchronized?
            try {
            Platform.runLater(() -> paint(color, x, y));
            Thread.sleep(20);
//            Platform.runLater(() -> clear(x, y));
//            move();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (Math.random() < 0.01)
        this.interrupt();
    }
}

    private void clear(double x, double y) {
        paint(Color.WHITE, x, y);

    }

    private void move() {
        if (x + vx < left || x + 2*r + vx > right)
            vx = -vx;
        if (y + vy < top || y + 2*r + vy > bottom)
            vy = -vy;

        x += vx;
        y += vy;
    }

    private void paint(Color c, double x, double y) {
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, 2 * r, 2 * r);
        move();
        gc.setFill(c);
        gc.fillOval(x, y, 2 * r, 2 * r);
    }

}
