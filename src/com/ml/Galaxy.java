package com.ml;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;

public class Galaxy extends JPanel implements Runnable {
    public static final int WIDTH = 500;//1920;
    public static final int HEIGHT = 500;//1080;
    public static final double DELTA = 0.1;

    private int numberOfDays = 365;
    private Sun sun;
    private java.util.List<Planet> planets = new ArrayList<>();

    Galaxy(Sun sun) {
        super();
        this.sun = sun;
    }

    private void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public Sun getSun() {
        return sun;
    }

    public static void main(String[] args) throws InterruptedException {
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        Galaxy galaxy = new Galaxy(new Sun(centerX, centerY));
        galaxy.addPlanet(new Planet(galaxy.getSun().getPoint().x, galaxy.getSun().getPoint().y, 50, "Ferengi"));
        galaxy.addPlanet(new Planet(galaxy.getSun().getPoint().x, galaxy.getSun().getPoint().y, 200, "Betasoide"));
        galaxy.addPlanet(new Planet(galaxy.getSun().getPoint().x, galaxy.getSun().getPoint().y, 100, "Vulcano"));

        galaxy.setVisible(true);

        JFrame frame = new JFrame("Galaxy Far Away");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(galaxy);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        galaxy.run();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        sun.draw(g);
        drawPlanets(g);
    }

    private void drawPlanets(Graphics g) {
        for (Planet planet: planets) {
            planet.draw(g);
        }
    }

    public void simulateDayF(int angle) {
        this.planets.get(0).simulateDay(angle);
    }

    public void simulateDayB(int angle) {
        this.planets.get(1).simulateDay(angle);
    }

    public void simulateDayV(int angle) {
        this.planets.get(2).simulateDay(angle);
    }

    // Math.abs(a-b) < delta
    public boolean isDroughtPeriod(Point p1, Point p2, Point p3, Point p4) {
        double m12 = slope(p1, p2);
        double m23 = slope(p2, p3);
        double m34 = slope(p3, p4);

        if ( Math.abs(m12-m23) < DELTA && Math.abs(m23-m34) < DELTA) {
            return true;
        } else {
            return false;
        }
    }

    public boolean optimalConditions(Point p1, Point p2, Point p3, Point p4) {
        double m12 = slope(p1, p2);
        double m23 = slope(p2, p3);
        double m34 = slope(p3, p4);

        if ( Math.abs(m12-m23) < DELTA && Math.abs(m23-m34) >= DELTA) {
//        if (slope(p1, p2) == slope(p2, p3)
//                && slope(p2, p3) != slope(p3, p4)) {
            return true;
        } else {
            return false;
        }
    }

    public double slope(Point p1, Point p2) {
        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }

    public double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2.0) + Math.pow(p2.y - p1.y, 2.0));
    }

    public boolean positiveOrientation(Point p1, Point p2, Point p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY())
                - (p1.getY() - p3.getY()) * (p2.getX() - p3.getX()) >= 0;
    }

    @Override
    public void run() {
        int grade = 0;
        int grade1 = 0;
        int grade2 = 0;
        int countDroughtPeriod = 0;
        int countOptimalConditions = 0;
        double maxPerimeter = 0;

        while (numberOfDays-->0) {

             if (isDroughtPeriod(planets.get(0).getPoint(),
                     planets.get(1).getPoint(),
                     planets.get(2).getPoint(),
                     new Point(sun.getPoint()))) {
                 countDroughtPeriod++;
             } else

            if (optimalConditions(planets.get(0).getPoint(),
                    planets.get(1).getPoint(),
                    planets.get(2).getPoint(),
                    new Point(sun.getPoint()))) {
                countOptimalConditions++;
            } else {
               if  (
                       (( positiveOrientation(planets.get(0).getPoint(),
                        planets.get(1).getPoint(),
                        planets.get(2).getPoint())
&&
                positiveOrientation(planets.get(0).getPoint(),
                        planets.get(1).getPoint(),
                        sun.getPoint())
&&
                positiveOrientation(planets.get(0).getPoint(),
                        sun.getPoint(),
                        planets.get(2).getPoint())
&&
                positiveOrientation(sun.getPoint(),
                        planets.get(1).getPoint(),
                        planets.get(2).getPoint())

               )) ||

                (( !positiveOrientation(planets.get(0).getPoint(),
                        planets.get(1).getPoint(),
                        planets.get(2).getPoint())
                        &&
                        !positiveOrientation(planets.get(0).getPoint(),
                                planets.get(1).getPoint(),
                                sun.getPoint())
                        &&
                        !positiveOrientation(planets.get(0).getPoint(),
                                sun.getPoint(),
                                planets.get(2).getPoint())
                        &&
                        !positiveOrientation(sun.getPoint(),
                                planets.get(1).getPoint(),
                                planets.get(2).getPoint())

                ))

                )
                {
                    System.out.println("dentro");
                   double aux = distance(planets.get(0).getPoint(), planets.get(1).getPoint())
                           + distance(planets.get(1).getPoint(), planets.get(2).getPoint())
                           + distance(planets.get(0).getPoint(), planets.get(2).getPoint());

                   if (aux > maxPerimeter) {
                       System.out.println("dist1 = "
                               + distance(planets.get(0).getPoint(), planets.get(1).getPoint()));
                       System.out.println("dist2 = "
                               + distance(planets.get(1).getPoint(), planets.get(2).getPoint()));
                       System.out.println("dist3 = "
                               + distance(planets.get(0).getPoint(), planets.get(2).getPoint()));
                       maxPerimeter = aux;
                   }
               } else {
                   System.out.println("fuera!!!!!!!!!!!!!!!!!!!!!!");
               }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            grade = grade + 1;
            grade1 = grade1 + 3;
            grade2 = grade2 - 5;

            simulateDayF(grade);
            simulateDayB(grade1);
            simulateDayV(grade2);

            this.repaint();
        }

        System.out.println("countOptimalConditions = " + countOptimalConditions);
        System.out.println("countDroughtPeriod = " + countDroughtPeriod);
        System.out.println("maxPerimeter = " + maxPerimeter);
    }
}
