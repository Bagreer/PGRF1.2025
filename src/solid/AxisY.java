package solid;

import transforms.Point3D;

public class AxisY extends Solid {

    public AxisY() {
        vertexBuffer.add(new Point3D(0,0,0));
        vertexBuffer.add(new Point3D(0,1,0));
    
        addIndicies(0,1);
    }
}
