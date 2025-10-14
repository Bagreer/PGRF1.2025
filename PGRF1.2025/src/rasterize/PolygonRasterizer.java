package rasterize;

import model.Line;
import model.Point;
import model.Polygon;

public class PolygonRasterizer {

    private final LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon) {
        if (polygon.getSize()<3) {
            return;
        }

        for (int i = 0; i < polygon.getSize() - 1; i++) {
            int indexA = i;
            int indexB = i+1;

            Point pointA = polygon.getPoint(indexA);
            Point pointB = polygon.getPoint(indexB);

            if (indexB == polygon.getSize()) {
                indexB = 0;
            }

            lineRasterizer.rasterize(new Line(pointA, pointB));
        }
        lineRasterizer.rasterize(new Line(polygon.getPoint(polygon.getSize() - 1), polygon.getPoint(0)));
    }
}
