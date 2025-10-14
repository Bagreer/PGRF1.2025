package controller;


import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.LineRasterizerTrivial;
import rasterize.PolygonRasterizer;
import view.Panel;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller2D {
    private final Panel panel;
    private int color = 0xffffff;

    private int x1, y1, x2, y2;
    private int currentX, currentY;

    private Polygon polygon = new Polygon();

    private ArrayList<Line> lines = new ArrayList<>();
//    private Point pLineStart, point2;

    private LineRasterizer LineRasterizer;
    private PolygonRasterizer PolygonRasterizer;


    /**konstruktor pro panel 2D
     *
     * @param panel konkretni panel ktery bude pouzivat tohle
     */
    public Controller2D(Panel panel) {
        this.panel = panel;

//        LineRasterizer = new LineRasterizerGraphics(panel.getRaster(), color);
        LineRasterizer = new LineRasterizerTrivial(panel.getRaster(), color);

        PolygonRasterizer = new PolygonRasterizer(LineRasterizer);

        initListeners();
    }

    /**
     * veškeré vykreslení probíhá zde
     */
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
//                    polygon.addPoint(new Point(e.getX(), e.getY()));

//                if (pLineStart == null) {
//                    pLineStart = new Point(e.getX(), e.getY());
//                } else {
//                    Line line = new Line(pLineStart, new Point(e.getX(), e.getY()));
//                    lines.add(line);
//                    pLineStart = null;
//                }
            }

            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                System.out.println(x2 + " " + y2);

                lines.add(new Line(x1, y1, x2, y2));

                drawScene();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {  // vykresleni usecky pri tahani mysi

                panel.getRaster().clear();
                LineRasterizer.rasterize(x1,y1,e.getX(),e.getY());
                panel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                System.out.println("current x:" + currentX + "\n current y:" + currentY);
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    polygon.addPoint(new Point(currentX, currentY));
                    drawScene();
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    panel.getRaster().clear();
                    panel.repaint();
                    lines.clear();
                    polygon = new Polygon();
                }
            }
        });
    }
}