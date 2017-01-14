package com.ml;

import java.awt.*;

/**
 * Created by JesusLeon on 14/01/2017.
 */
public class Planet {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    public static final int X_CENTER = 1920/2;
    public static final int Y_CENTER = 1080/2;

    private int x;
    private int y;
    private String name;

    /**
     * Distance to the sun
     */
    private int radio;

    public Planet(int x, int y, int radio, String name) {
        this.x = x + radio;
        this.y = y;
        this.radio = radio;
        this.name = name;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.black);

        g2d.drawLine(x, y, x, y);
        g2d.drawOval(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void simulateDay(int grade) {
        double a = grade * 2 * Math.PI / 360;

        this.x = (int) (X_CENTER + radio * Math.cos(a));
        this.y = (int) (Y_CENTER + radio * Math.sin(a));
    }
}
