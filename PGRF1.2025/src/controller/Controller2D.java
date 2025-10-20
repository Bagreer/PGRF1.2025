package controller;

import model.Line;
import model.Point;
import model.Polygon;
import rasterize.*;
import view.Panel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Controller2D {
    private final Panel panel;
    private int color = 0xffffff;

    private int x1, y1, x2, y2;
    private int currentX, currentY;

    // NOVÝ ATRIBUT: Sledování stavu klávesy Shift
    private boolean shiftPressed = false;

    private Polygon polygon = new Polygon();
    private ArrayList<Line> lines = new ArrayList<>();

    private LineRasterizer LineRasterizer;
    private PolygonRasterizer PolygonRasterizer;

    // ... (konstruktor a drawScene zůstávají stejné) ...
    public Controller2D(Panel panel) {
        this.panel = panel;
        LineRasterizer = new LineRasterizerTrivial(panel.getRaster(), color);
        PolygonRasterizer = new PolygonRasterizer(LineRasterizer);
        initListeners();
    }

    private void drawScene() {
        panel.getRaster().clear();
        for (Line line : lines) {
            LineRasterizer.rasterize(line);
        }
        PolygonRasterizer.rasterize(polygon);
        panel.repaint();
    }


    /**
     * inicializace listeneru (mouse, key listeners)
     */
    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                System.out.println(x1 + " " + y1);
            }

            public void mouseReleased(MouseEvent e) {
                int tempX2 = e.getX();
                int tempY2 = e.getY();

                // Používáme atribut 'shiftPressed'
                if (shiftPressed) {
                    Point aligned = alignCoordinates(x1, y1, tempX2, tempY2);
                    x2 = aligned.getX();
                    y2 = aligned.getY();
                } else {
                    x2 = tempX2;
                    y2 = tempY2;
                }

                System.out.println(x2 + " " + y2);
                lines.add(new Line(x1, y1, x2, y2));
                drawScene();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                int tempX2 = e.getX();
                int tempY2 = e.getY();

                // Používáme atribut 'shiftPressed'
                if (shiftPressed) {
                    Point aligned = alignCoordinates(x1, y1, tempX2, tempY2);
                    tempX2 = aligned.getX();
                    tempY2 = aligned.getY();
                }

                panel.getRaster().clear();
                LineRasterizer.rasterize(x1, y1, tempX2, tempY2);
                panel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                //System.out.println("current x:" + currentX + "\n current y:" + currentY);
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_SHIFT) {
                    shiftPressed = true;
                } else if (keyCode == KeyEvent.VK_E) {
                    polygon.addPoint(new Point(currentX, currentY));
                    drawScene();
                } else if (keyCode == KeyEvent.VK_C) {
                    panel.getRaster().clear();
                    panel.repaint();
                    lines.clear();
                    polygon = new Polygon();
                } else if (keyCode == KeyEvent.VK_V) {
                    LineRasterizer = new LineRasterizerTrivialColor(panel.getRaster(), color);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // *** NOVÁ LOGIKA PRO SHIFT ***
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = false;
                }
            }
        });
    }

    private Point alignCoordinates(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double angleRad = Math.atan2(dy, dx);
        double angleDeg = Math.toDegrees(angleRad);

        if (angleDeg < 0) {
            angleDeg += 360;
        }

        int snappedAngleDeg = (int) (Math.round(angleDeg / 45.0) * 45);

        if (snappedAngleDeg == 360) {
            snappedAngleDeg = 0;
        }

        double snappedAngleRad = Math.toRadians(snappedAngleDeg);
        double length = Math.sqrt(dx * dx + dy * dy);

        int newX2 = x1 + (int) Math.round(length * Math.cos(snappedAngleRad));
        int newY2 = y1 + (int) Math.round(length * Math.sin(snappedAngleRad));

        return new Point(newX2, newY2);
    }
}