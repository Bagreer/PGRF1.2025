package controller;

import clip.Clipper;
import fill.Filler;
import fill.ScanLineFiller;
import fill.SeedFill;
import model.Line;
import model.Point;
import model.Polygon;
import model.Rectangle;
import rasterize.*;
import view.Panel;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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
    private PolygonRasterizer polygonRasterizer;

    private Filler seedFiller;
    private Point seedFillerStartPoint;

    private Filler scanLineFiller;

    private Clipper clipper;
    private Polygon polygonClipper = new Polygon();
    private Polygon polygonToDraw = new Polygon();
    List<Point> clippedPoints = new ArrayList<>();


    private Rectangle rectangle = new Rectangle();
    private ArrayList<Point> rPoints = new ArrayList<>(3);
    private boolean rectangleDrawn = false;

    /**
     * třída na ovládání a zobrazování grafických blbostí.
     * inicializuje listenery, definuje lineRasterizery a polygonRasterizery
     *
     * @param panel panel, na kterém se vykresluje
     */
    public Controller2D(Panel panel) {
        this.panel = panel;
        LineRasterizer = new LineRasterizerTrivial(panel.getRaster(), color);
        polygonRasterizer = new PolygonRasterizer(LineRasterizer);
        scanLineFiller = new ScanLineFiller(polygon, panel.getRaster(), LineRasterizer);
        clipper = new Clipper();
        initListeners();
    }

    /**
     * Metoda pro vykreslování scény, clearne scénu > vykreslí linky > rasterizuje polygon >
     */
    private void drawScene() {
        panel.getRaster().clear();

        // Vykreslení úseček
        for (Line line : lines)
            LineRasterizer.rasterize(line);

        // seedFill vykreslení
        if (seedFillerStartPoint != null) {
            seedFiller.fill();
        }

        // TODO vytvořit polygon pomoci pointu (nejlepe pracovat s Listem, ale pokud to rozbije projekt tak pouzit new ArrayList<clipped points>)

        //vykreslení obou polygonů
        polygonRasterizer.rasterize(polygon);
        LineRasterizer.setColor(0x5500ff);  // aby byl kazdy jinou barvou
        polygonRasterizer.rasterize(polygonClipper);
        LineRasterizer.setColor(0xffffff);

        // TODO provedu ořezání - Používám Clipper.clip()
        clippedPoints = clipper.clip(polygon.getPoints(), polygonClipper.getPoints());

        // TODO vytvořit polygon pomoci pointu

        polygonToDraw = new Polygon();
        for (Point p : clippedPoints) {
            // TODO vznikne novy seznam pointu > vyplním výsledek ořezání pomocí scanLine
            polygonToDraw.addPoint(p);
        }

        if (polygonToDraw.getSize() >= 3) {
            scanLineFiller = new ScanLineFiller(
                    polygonToDraw,
                    panel.getRaster(),
                    LineRasterizer
            );
//            scanLineFiller.fill();
//            polygonRasterizer.rasterize(polygonToDraw);
        }
        // TODO vyplním výsledek ořezání pomocí scanLine

        polygonRasterizer.rasterize(rectangle);
        rectangleDrawn = true;

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

                if (e.getButton() == MouseEvent.BUTTON2) {
                    seedFillerStartPoint = new Point(e.getX(), e.getY());
                    seedFiller = new SeedFill(panel.getRaster(), seedFillerStartPoint.getX(), seedFillerStartPoint.getY(), 0xffffff);
                    seedFiller.fill();
                } else if (e.getButton() == MouseEvent.BUTTON1) {  // Polygon
                    // Fixace bodu na aktuální pozici kurzoru (currentX/currentY)
                    polygon.addPoint(new Point(currentX, currentY));
                    drawScene();
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Clipper Polygon
                    polygonClipper.addPoint(new Point(currentX, currentY));
                    drawScene();
                }
            }

            public void mouseReleased(MouseEvent e) {
//                int tempX2 = e.getX();
//                int tempY2 = e.getY();
//
//                // Používáme atribut 'shiftPressed'
//                if (shiftPressed) {
//                    Point aligned = alignCoordinates(x1, y1, tempX2, tempY2);
//                    x2 = aligned.getX();
//                    y2 = aligned.getY();
//                } else {
//                    x2 = tempX2;
//                    y2 = tempY2;
//                }
//
//                System.out.println(x2 + " " + y2);
//                lines.add(new Line(x1, y1, x2, y2));
//                drawScene();
            }

        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//
//                int tempX2 = e.getX();
//                int tempY2 = e.getY();
//
//                if (shiftPressed) {
//                    Point aligned = alignCoordinates(x1, y1, tempX2, tempY2);
//                    tempX2 = aligned.getX();
//                    tempY2 = aligned.getY();
//                }
//
////                panel.getRaster().clear();
////                LineRasterizer.rasterize(x1, y1, tempX2, tempY2);
//                panel.repaint();
//            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

//                panel.getRaster().clear();
//
//                Point last = new Point(currentX, currentY);
//
//                if (polygon.getSize() == 1) {
//                    LineRasterizer.rasterize(new Line(polygon.getPoint(0), last));
//                } else if (polygon.getSize() == 2) {
//                    LineRasterizer.rasterize(new Line(polygon.getPoint(0), polygon.getPoint(1)));
//                    LineRasterizer.rasterize(new Line(polygon.getPoint(1), last));
//                    LineRasterizer.rasterize(new Line(polygon.getPoint(0), last));
//                } else if (polygon.getSize() >= 3) {
//                    return;
//                }
//
                panel.repaint();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_SHIFT) {
                    shiftPressed = true;
                } else if (keyCode == KeyEvent.VK_C) {
                    lines.clear();
                    rectangleDrawn = false;
                    clippedPoints.clear();

                    rectangle = new Rectangle();
                    polygonClipper = new Polygon();
                    polygonToDraw = new Polygon();
                    polygon = new Polygon();
                    rPoints = new ArrayList<>(3);
                    scanLineFiller = new ScanLineFiller(LineRasterizer);

                    drawScene();

                } else if (keyCode == KeyEvent.VK_V) {
                    LineRasterizer = new LineRasterizerTrivialColor(panel.getRaster(), color);
                } else if (keyCode == KeyEvent.VK_F) {
                    if (polygon.getSize() >= 3) {
                        Filler currentFiller = new ScanLineFiller(
                                polygon,
                                panel.getRaster(),
                                LineRasterizer
                        );
                        currentFiller.fill();
                    }
                    panel.repaint();
                } else if (keyCode == KeyEvent.VK_R) {
                    if (rPoints.size() < 3) {
                        rPoints.add(new Point(currentX, currentY));
                    }

                    if ((rPoints.size() == 3) && rectangleDrawn) {
                        rPoints.clear();
                        rectangle = new Rectangle();
                        drawScene();
                        panel.repaint();
                        rectangleDrawn = false;
                        rPoints.add(new Point(currentX, currentY));
                    } else if (rPoints.size() == 3) {
                        rectangle = new Rectangle(rPoints.getFirst(), rPoints.get(1), rPoints.get(2));
                        drawScene();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = false;
                }
            }
        });
    }

    /**
     * Zarovná souřadnice bodů na úsečce tak aby byly v úhlech (0,45,90,...)
     *
     * @param x1 souřadnice x prvního body
     * @param y1 souřadnice y prvního body
     * @param x2 souřadnice x druhého body
     * @param y2 souřadnice y druhého body
     * @return srovnané souřadnice úsečce
     */
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