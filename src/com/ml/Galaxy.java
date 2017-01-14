package com.ml;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Galaxy extends JPanel implements Runnable {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private int numberOfDays = 20;
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

    @Override
    public void run() {
        int grade = 0;
        int grade1 = 0;
        int grade2 = 0;

        while (numberOfDays-->0) {
            try {
                Thread.sleep(1000);
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
    }
}
