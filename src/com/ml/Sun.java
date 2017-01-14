package com.ml;

import java.awt.*;

/**
 * Created by JesusLeon on 14/01/2017.
 */
public class Sun {

    private Point point;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    public Sun(int x, int y) {
        point = new Point(x, y);
    }

    public Point getPoint() {
        return point;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.black);

        g2d.drawLine(point.x, point.y, point.x, point.y);
        g2d.drawOval(point.x - WIDTH / 2, point.y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
