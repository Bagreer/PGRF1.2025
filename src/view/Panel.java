package view;

import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Panel extends JPanel {

    private final RasterBufferedImage raster;

    private Boolean arrowSelected = false;
    private Boolean cubeSelected = false;
    private Boolean tetraSelected  = false;
    private Boolean pyramidSelected = false;
    private Boolean axisSelected = true;
    private Boolean cylinderSelected = false;

    // na volání drawScene
    private Runnable redrawAction;

    /** na volání metody drawScene */
    public void setRedrawAction(Runnable action) {
        this.redrawAction = action; // Uložení instrukce
    }


    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));

        raster = new RasterBufferedImage(width, height);

        JPanel pane = new JPanel();
        pane.setPreferredSize(new Dimension(150, height));

        JLabel label = new JLabel("WASD - pohyb kamery" );
        JLabel label1 = new JLabel("+  - rotace kolem osy x");
        JLabel label2 = new JLabel("ě  - rotace kolem osy y");
        JLabel label3 = new JLabel("č  - rotace kolem osy z");
        JLabel label4 = new JLabel("kolečko myši - scale");



        JLabel label5 = new JLabel("<html>Po vybrání aktivního <br>objektu je potřeba<br>kliknout zpět do plátna</html>");

        JLabel label6 = new JLabel("6 - žádná křivka");
        JLabel label7 = new JLabel("7 - Sinus křivka");
        JLabel label8 = new JLabel("8 - Bezier křivka");
        JLabel label9 = new JLabel("9 - Coons křivka");
        JLabel label10 = new JLabel("0 - Ferguson křivka");

        JLabel label11 = new JLabel("<html>P - přepínání <br> perpektivní/ortogonální</html>");

        ButtonGroup group = new ButtonGroup();
        JRadioButton arrow = new JRadioButton("arrow");
        JRadioButton cube = new JRadioButton("cube");
        JRadioButton tetra = new JRadioButton("tetra");
        JRadioButton pyramid = new JRadioButton("pyramid");
        JRadioButton cylinder = new JRadioButton("cylinder");

        group.add(arrow);
        group.add(cube);
        group.add(tetra);
        group.add(pyramid);
        group.add(cylinder);

        arrow.setSelected(true);

        pane.add(arrow);
        pane.add(cube);
        pane.add(tetra);
        pane.add(pyramid);
        pane.add(cylinder);

        setLayout(new BorderLayout());
        add(pane, BorderLayout.EAST);

        ActionListener radioListener = e -> {
            arrowSelected = false;
            cubeSelected = false;
            tetraSelected = false;
            pyramidSelected = false;
            cylinderSelected = false;

            if (e.getSource() == arrow) {
                arrowSelected = true;
            } else if (e.getSource() == cube) {
                cubeSelected = true;
            } else if (e.getSource() == tetra) {
                tetraSelected = true;
            } else if (e.getSource() == pyramid) {
                pyramidSelected = true;
            } else if (e.getSource() == cylinder) {
                cylinderSelected = true;
            }

            // na volání metody drawScene
            if (redrawAction != null) {
                redrawAction.run();
            }
        };

        arrow.addActionListener(radioListener);
        cube.addActionListener(radioListener);
        tetra.addActionListener(radioListener);
        pyramid.addActionListener(radioListener);
        cylinder.addActionListener(radioListener);

        JCheckBox checkBox = new JCheckBox("Zobrazení os");
        checkBox.setSelected(true);

        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                axisSelected = true;
            } else {
                axisSelected = false;
            }

            // na volání metody drawScene
            if (redrawAction != null) {
                redrawAction.run();
            }
        });

        pane.add(checkBox);

        label5.setPreferredSize(new Dimension(125, 75));

        pane.add(label);
        pane.add(label1);
        pane.add(label2);
        pane.add(label3);
        pane.add(label6);
        pane.add(label7);
        pane.add(label8);
        pane.add(label9);
        pane.add(label10);
        pane.add(label4);
        pane.add(label11);
        pane.add(label5);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(raster.getImage(), 0, 0, null);
    }

    public RasterBufferedImage getRaster() {
        return raster;
    }

    public Boolean getPyramidSelected() {
        return pyramidSelected;
    }
    public Boolean getTetraSelected() {
        return tetraSelected;
    }
    public Boolean getCubeSelected() {
        return cubeSelected;
    }
    public Boolean getArrowSelected() {
        return arrowSelected;
    }
    public Boolean getAxisSelected() {
        return axisSelected;
    }
    public Boolean getCylinderSelected() {
        return cylinderSelected;
    }
}
