package model;

import java.util.ArrayList;

public class Rectangle extends Polygon{

    public Rectangle() {
        points = new ArrayList<>();
    }

    public Rectangle(Point a, Point b, Point h) {
        int ax = a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();
        int hx = h.getX();
        int hy = h.getY();

        ArrayList<Integer> normalBaseVector = new ArrayList<>();

        normalBaseVector.add(-(by-ay));
        normalBaseVector.add(bx-ax);

        double width = Math.sqrt(           // delka základny AB
                        normalBaseVector.getFirst()* normalBaseVector.getFirst() +
                        normalBaseVector.getLast()* normalBaseVector.getLast());


        ArrayList<Integer> vectorToPoint = new ArrayList<>();   // vektor k bodu H
        vectorToPoint.add(hx - ax);
        vectorToPoint.add(hy - ay);

        double dotProduct = dotProduct(vectorToPoint, normalBaseVector);

        double orientedHeight = dotProduct / width;

        ArrayList<Double> normalVector1 = new ArrayList<>();
        normalVector1.add(normalBaseVector.getFirst()/width);
        normalVector1.add(normalBaseVector.getLast()/width);

        ArrayList<Double> hVector  = new ArrayList<>();

        hVector.add(orientedHeight*normalVector1.getFirst());
        hVector.add(orientedHeight*normalVector1.getLast());

        addPoint(new Point(ax, ay));
        addPoint(new Point(bx, by));
        addPoint(new Point((int) Math.round(bx+hVector.getFirst()),(int) Math.round(by+hVector.getLast())));
        addPoint(new Point((int) Math.round(ax+hVector.getFirst()),(int) Math.round(ay+hVector.getLast())));
    }

    private double dotProduct(ArrayList<Integer> a, ArrayList<Integer> b) {

        return a.getFirst()*b.getFirst() + a.getLast()*b.getLast();
    }
}
