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

public class Controller3D {
    private final Panel panel;
    private LineRasterizer LineRasterizer;


    /**
     * třída na ovládání a zobrazování grafických blbostí.
     * inicializuje listenery, definuje lineRasterizery a polygonRasterizery
     *
     * @param panel panel, na kterém se vykresluje
     */
    public Controller3D(Panel panel) {
        this.panel = panel;
        LineRasterizer = new LineRasterizerTrivial(panel.getRaster(),0xffffff);
        initListeners();
    }

    /**
     * Metoda pro vykreslování scény, clearne scénu > vykreslí linky > rasterizuje polygon >
     */
    private void drawScene() {

    }

    /**
     * inicializace listeneru (mouse, key listeners)
     */
    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {

        });
    }
}

//TODO
// model -> vertexBuffer, indexBuffer -> model space
// modelovaci transformace = model space -> world space
// nastaveni kamery
// pohledova transformace = world space -> view space
// projekce = projekční transformace = view space -> clip space
// ořezání
// dehomogenizace - x,y,z,w = x/w, y/w, z/w, w/w       clip space -> NDC
// transformace do okna obrazovky
// rasterizace