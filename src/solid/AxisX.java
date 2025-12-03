package solid;

import transforms.Point3D;

public class AxisX extends Solid {

    public AxisX() {
        vertexBuffer.add(new Point3D(0,0,0));
        vertexBuffer.add(new Point3D(1,0,0));

        addIndicies(0,1);
    }
}
