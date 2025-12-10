package solid;

import transforms.Point3D;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CurveBezier extends Solid{

    private int segments = 6;

    public  CurveBezier() {
        for (int i = 0; i < segments; i++) {
            vertexBuffer.add(new Point3D(i,0,Math.sin(Math.toRadians(i))));
        }

        addIndicies(0,1);
        addIndicies(1,2);
        addIndicies(2,3);
        addIndicies(3,4);
        addIndicies(4,5);
        addIndicies(5,6);

    }
}
