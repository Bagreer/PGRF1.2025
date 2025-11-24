package controller;

import rasterize.*;
import render.Renderer;
import solid.Arrow;
import solid.Solid;
import view.Panel;

import java.awt.event.*;

public class Controller3D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;

    private Renderer renderer;

    private Solid arrow;
    /**
     * třída na ovládání a zobrazování grafických blbostí.
     * inicializuje listenery, definuje lineRasterizery a polygonRasterizery
     *
     * @param panel panel, na kterém se vykresluje
     */
    public Controller3D(Panel panel) {
        this.panel = panel;
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster(),0xffffff);
        this.renderer = new Renderer(lineRasterizer, panel.getWidth(), panel.getHeight());

        arrow = new Arrow();

        initListeners();

        drawScene();
    }

    /**
     * Metoda pro vykreslování scény, clearne scénu > vykreslí linky > rasterizuje polygon >
     */
    private void drawScene() {
        panel.getRaster().clear();

        renderer.renderSolid(arrow);

        panel.repaint();    // nutne pro překreslení scény
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
// done - model -> vertexBuffer, indexBuffer -> model space
// modelovaci transformace = model space -> world space
// nastaveni kamery
// pohledova transformace = world space -> view space
// projekce = projekční transformace = view space -> clip space
// ořezání
// dehomogenizace - x,y,z,w = x/w, y/w, z/w, w/w       clip space -> NDC
// done - transformace do okna obrazovky NDC -> okno obrazovky
// done - rasterizace

