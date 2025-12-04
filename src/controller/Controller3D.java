package controller;

import rasterize.*;
import render.Renderer;
import solid.*;
import transforms.*;
import view.Panel;

import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Controller3D {
    private final Panel panel;
    private final LineRasterizer lineRasterizer;

    private final Renderer renderer;

    //objekty
    private Solid arrow;
    private Solid cube;
    private Solid tetrahedron;
    private Solid pyramid;
    private Solid cylinder;

    //camera
    private Camera camera;
    private Mat4 proj;

    //osy
    private AxisX axisX;
    private AxisY axisY;
    private AxisZ axisZ;

    //souradnice pro rozhlizeni
    int cordX, cordY;

    //time for aniations
    private Timer timer;
    private double time = 0.0;
    private final double step = 0.05;
    boolean isAnimating = false;




    /**
     * třída na ovládání a zobrazování grafických blbostí.
     * inicializuje listenery, definuje lineRasterizery a polygonRasterizery
     *
     * @param panel panel, na kterém se vykresluje
     */
    public Controller3D(Panel panel) {

        panel.setRedrawAction(this::drawScene);

        this.panel = panel;
        this.lineRasterizer = new LineRasterizerTrivial(panel.getRaster(),0xffffff);

        this.proj = new Mat4PerspRH(Math.toRadians(90),
                panel.getRaster().getHeight() / (double)panel.getRaster().getWidth(),
                0.1,
                100
        );

        this.camera = new Camera()
                .withPosition(new Vec3D(0.5,-1.5,1))
                .withAzimuth(Math.toRadians(90))    //POZOR - zadává se v radiánech   otoceni hlavy doleva a doprava
                .withZenith(Math.toRadians(-25))    //otoceni hlavy dolu a nahoru
                .withFirstPerson(true);     // prvni osoba

        this.renderer = new Renderer(
                lineRasterizer,
                panel.getWidth(),
                panel.getHeight(),
                proj,
                camera.getViewMatrix()
                );

        pyramid = new Pyramid();
        tetrahedron = new Tetrahedron();
        cube = new Cube();
        arrow = new Arrow();
        axisX = new AxisX();
        axisY = new AxisY();
        axisZ = new AxisZ();
        cylinder = new Cylinder();

        initListeners();

        drawScene();
    }

    /**
     * Metoda pro vykreslování scény, clearne scénu > vykreslí objekty > rasterizuje polygon >
     */
    private void drawScene() {
        panel.getRaster().clear();
        renderer.setView(camera.getViewMatrix());

        if (panel.getArrowSelected())
            renderer.renderSolid(arrow);
        else if (panel.getCubeSelected())
            renderer.renderSolid(cube);
        else if (panel.getPyramidSelected())
            renderer.renderSolid(pyramid);
        else if (panel.getTetraSelected())
            renderer.renderSolid(tetrahedron);
        else if (panel.getCylinderSelected())
            renderer.renderSolid(cylinder);

        if (panel.getAxisSelected()) {
            lineRasterizer.setColor(0xff0000); //x
            renderer.renderSolid(axisX);

            lineRasterizer.setColor(0x00ff00); //y
            renderer.renderSolid(axisY);

            lineRasterizer.setColor(0x0000ff); //z
            renderer.renderSolid(axisZ);
        }


        lineRasterizer.setColor(0xffffff);
        panel.repaint();    // nutne pro překreslení scény
    }

    /**
     * inicializace listeneru (mouse, key listeners)
     */
    private void initListeners() {

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((cordX - e.getX()) * 0.0005);
                camera = camera.addZenith((cordY - e.getY()) * 0.0005);

                drawScene();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               panel.requestFocusInWindow();
           }

            @Override
            public void mousePressed(MouseEvent e) {
                cordX = e.getX();
                cordY = e.getY();
            }
        });

        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    transformActiveSolid(new Mat4Scale(1.1));
                    drawScene();
                } else if (e.getWheelRotation() > 0) {
                    transformActiveSolid(new Mat4Scale(0.9));
                    drawScene();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                // posouvání objektu
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    transformActiveSolid(new Mat4Transl(0.1,0,0));
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    transformActiveSolid(new Mat4Transl(-0.1, 0, 0));
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    transformActiveSolid(new Mat4Transl(0,0.1,0));
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    transformActiveSolid(new Mat4Transl(0,-0.1,0));
                    drawScene();
                }

                //rotace šipky v jejím středu
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    arrow.mulModel(new Mat4Transl(-0.3, 0, 0));
                    arrow.mulModel(new Mat4RotZ(Math.toRadians(30)));
                    arrow.mulModel(new Mat4Transl(0.3, 0, 0));
                    drawScene();
                }

                //pohyb kamery
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    camera = camera.left(0.1);
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    camera = camera.right(0.1);
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    camera = camera.backward(0.1);
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    camera = camera.forward(0.1); // Předpokládám kladný posun Vpřed
                    drawScene();
                }


                //rotace kolem os
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    transformActiveSolid(new Mat4RotX(Math.toRadians(15)));
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    transformActiveSolid(new Mat4RotY(Math.toRadians(15)));
                    drawScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    transformActiveSolid(new Mat4RotZ(Math.toRadians(15)));
                    drawScene();
                }

                // animace
                if (e.getKeyCode() == KeyEvent.VK_M) {

                    if (isAnimating) {
                        timer.cancel();
                        timer = null;
                        isAnimating = false;
                    } else {
                        timer = new java.util.Timer();
                        timer.scheduleAtFixedRate(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                time += step;

                                //scaling animation
//                                transformActiveSolid(new Mat4Scale(1.0 + (Math.cos(time * 3.0) * 0.1)));

                                // some random rotation
//                                transformActiveSolid(new Mat4RotZ(Math.sin(time)*0.25));
//                                transformActiveSolid(new Mat4RotX(Math.cos(time)*0.25));


                                //gemini generated, but looks fire
//                                transformActiveSolid(new Mat4RotY(time * 0.5));
//                                transformActiveSolid(new Mat4Transl(Math.sin(time) * 0.75, 0.0, 0.0));

                                SwingUtilities.invokeLater(Controller3D.this::drawScene);
                            }
                        }, 0, 33);
                        isAnimating = true;
                    }
                }

                //kdyz se rotuje, tak je mozny ze se telesa otoci a neni sranda to dat do puvodni polohy takze tlacitko resetuje plochu
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    cube = new Cube();
                    arrow = new Arrow();
                    tetrahedron = new Tetrahedron();
                    pyramid = new Pyramid();
                    axisX = new AxisX();
                    axisY = new AxisY();
                    axisZ = new AxisZ();
                    cylinder = new Cylinder();
                    drawScene();
                }
            }
        });
    }

    public void transformActiveSolid(Mat4 transform) {
        if (panel.getArrowSelected())
            arrow.mulModel(transform);
        else if (panel.getCubeSelected())
            cube.mulModel(transform);
        else if (panel.getTetraSelected())
            tetrahedron.mulModel(transform);
        else if (panel.getPyramidSelected())
            pyramid.mulModel(transform);
        else if (panel.getCylinderSelected())
            cylinder.mulModel(transform);
    }
}

//TODO
// done - model -> vertexBuffer, indexBuffer -> model space
// done - modelovaci transformace = model space -> world space
// done - nastaveni kamery
// done - pohledova transformace = world space -> view space
// done - projekce = projekční transformace = view space -> clip space
// ořezání (není potřeba aby to fungovalo)
// done - dehomogenizace - x,y,z,w = x/w, y/w, z/w, w/w       clip space -> NDC
// done - transformace do okna obrazovky NDC -> okno obrazovky
// done - rasterizace



//TODO nefunguje listener, nejak doresit