package watki;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.swing.*;
import java.util.Random;

public class Ball extends java.lang.Thread {

    private Box box;
    // promień kulki
    private static double r;
    // prędkość kulki
    private static double v;
    // współrzędne lewego górnego rogu kulki
    private double x, y;
    // składowe wektora prędkości
    private double vx, vy;
    private Color color;
    private boolean inside;

    // granice płótna
    private double top, bottom, left, right;

    private GraphicsContext gc;

    static {
        r = 10;
        v = 5.0;
    }

    public Ball(GraphicsContext GC, Box box) {
        this(GC);
        this.box = box;
    }

    public Ball(GraphicsContext GC) {
        this.gc = GC;
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);

        top = 0;
        left = 0;
        bottom = gc.getCanvas().getHeight();
        right = gc.getCanvas().getWidth();

        x = Math.random() * (right - 2*r);
        y = Math.random() * (bottom - 2*r);
        // maksymalna prędkość - v pikseli na cykl
        vx = Math.random() * v;
        vy = Math.sqrt(Math.pow(v, 2) - Math.pow(vx, 2));
        double r, g, b;
        do {
            r = Math.random();
            g = Math.random();
            b = Math.random();
        } while (r + g + b > 2.3);
        this.color = new Color(r, g, b, 1.0);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Platform.runLater(this::draw);
                Thread.sleep(20);

                // styknięcie od zewnątrz z Boxem
                if (!inside && isInsideBox()) {
                    box.enter();
                    inside = true;
                    this.color = color.invert();
                    box.paint();

               // wylecenie z Boxa
                } else if (inside && !isInsideBox()) {
                    box.exit();
                    inside = false;
                    this.color = color.invert();

                //rysowanie boxa
                } else if (inside) {
                    box.paint();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void draw() {
        clear(x, y);
        move();
        paint(color, x, y);
    }

    private void clear(double x, double y) {
        paint(Color.WHITE, x, y);
        gc.setStroke(Color.WHITE);
        gc.strokeOval(x, y, 2*r,  2*r);
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
        gc.setFill(c);
        gc.fillOval(x, y, 2 * r, 2 * r);
    }

    public boolean isInsideBox() {
        return (x + 2*r >= box.getX() && x <= box.getX() + box.getW()
                && y + 2*r >= box.getY() && y <= box.getY() + box.getH());
    }
}
