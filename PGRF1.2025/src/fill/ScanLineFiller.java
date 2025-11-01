package fill;

import model.*;
import raster.Raster;
import model.Polygon;
import raster.RasterBufferedImage;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerTrivial;
import rasterize.LineRasterizerTrivialColor;
import rasterize.PolygonRasterizer;

import java.util.ArrayList;

public class ScanLineFiller implements Filler {

    private Polygon polygon;
    private Raster raster;
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;

    public ScanLineFiller(Polygon polygon, Raster raster, LineRasterizer lineRasterizer) {
        this.polygon = polygon;
        this.raster = raster; // PŘIŘAZENÍ OPRAVENO
        this.lineRasterizer = lineRasterizer; // PŘIJAT LineRasterizer z Controleru
        this.polygonRasterizer = new PolygonRasterizer(lineRasterizer);
    }

    @Override
    public void fill() {

        if (polygon.getSize() < 3) {
            return;
        }

        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < polygon.getSize(); i++) {   // hledame hrany polygonu, ktere nejsou horizontalni
            Point pointA = polygon.getPoint(i);

            int indexB;

            if (i == polygon.getSize() - 1) {
                indexB = 0;
            } else {
                indexB = i + 1;
            }
            Point pointB = polygon.getPoint(indexB);

            Edge edge = new Edge(pointA, pointB);

            if(!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }

        //najít yMin yMax

        int yMin = polygon.getPoint(0).getY();
        int yMax = polygon.getPoint(0).getY(); // Správná inicializace prvním bodem

        for (int i = 1; i < polygon.getSize(); i++) {
            int y = polygon.getPoint(i).getY();
            yMin = Math.min(yMin, y);
            yMax = Math.max(yMax, y);
        }

        //TODO projit seznam pointu v polygonu a najit min a max

        for (int y = yMin; y <= yMax; y++) {

            ArrayList<Integer> intersections = new ArrayList<>();

            for (Edge edge : edges) {
                // TODO existuje prusecik ?

                if (!edge.isIntersection(y)) {
                    continue; //jde na dalsi polozku v for each
                }

                // TODO pokud ano => spocitam
                int x = edge.getIntersection(y);

                // TODO ulozim do seznamu pruseciku
                intersections.add(x);

            }

            //TODO seradit pruseciky od min po max
            intersections.sort(Integer::compareTo);

            //TODO projdu pruseciky a vykreslit mezi lich. a sud. (0 -> 1; 2 -> 3)
            for (int i = 0; i < intersections.size(); i+=2) {

                if ((i+1) >= intersections.size()) {
                    continue;
                }

                lineRasterizer.rasterize(intersections.get(i),y, intersections.get(i+1), y);
            }
        }

        //TODO obtahnout hranici polygonu (pres polygon rasterizer)
        polygonRasterizer.rasterize(polygon);

    }
    //TODO
    // podmínka pro vyplnění polygonu o méně jak třech vrcholech
    // seznam hran (Line)
    //      ArrayList<Edge> edges = new ArrayList<>();

}
