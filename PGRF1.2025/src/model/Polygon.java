package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    protected ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public Polygon(ArrayList<Point> points) {
        this.points = points;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getSize() {
        return points.size();
    }
}
