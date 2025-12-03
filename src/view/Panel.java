package view;

import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel extends JPanel {

    private final RasterBufferedImage raster;

    private Boolean arrowSelected = false;
    private Boolean cubeSelected = false;
    private Boolean tetraSelected  = false;
    private Boolean pyramidSelected = false;
    private Boolean axisSelected = true;

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

        ButtonGroup group = new ButtonGroup();
        JRadioButton arrow = new JRadioButton("arrow");
        JRadioButton cube = new JRadioButton("cube");
        JRadioButton tetra = new JRadioButton("tetra");
        JRadioButton pyramid = new JRadioButton("pyramid");

        group.add(arrow);
        group.add(cube);
        group.add(tetra);
        group.add(pyramid);

        arrow.setSelected(true);

        pane.add(arrow);
        pane.add(cube);
        pane.add(tetra);
        pane.add(pyramid);

        setLayout(new BorderLayout());
        add(pane, BorderLayout.EAST);

        ActionListener radioListener = e -> {
            arrowSelected = false;
            cubeSelected = false;
            tetraSelected = false;
            pyramidSelected = false;

            if (e.getSource() == arrow) {
                arrowSelected = true;
            } else if (e.getSource() == cube) {
                cubeSelected = true;
            } else if (e.getSource() == tetra) {
                tetraSelected = true;
            } else if (e.getSource() == pyramid) {
                pyramidSelected = true;
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

        JLabel label5 = new JLabel("<html>Po vybrání aktivního <br>objektu je potřeba<br>kliknout zpět do plátna</html>");
        label5.setPreferredSize(new Dimension(125, 75));

        pane.add(label);
        pane.add(label1);
        pane.add(label2);
        pane.add(label3);
        pane.add(label4);
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
}
