package solid;

import transforms.Point3D;

public class Pyramid extends Solid {

    public Pyramid() {

        vertexBuffer.add(new Point3D(0,0,0));
        vertexBuffer.add(new Point3D(0,1,0));
        vertexBuffer.add(new Point3D(1,1,0));
        vertexBuffer.add(new Point3D(1,0,0));

        vertexBuffer.add(new Point3D(0.5,0.5,1));

        addIndicies(0,1);
        addIndicies(1,2);
        addIndicies(2,3);
        addIndicies(0,3);

        addIndicies(0,4);
        addIndicies(1,4);
        addIndicies(2,4);
        addIndicies(3,4);

    }
}
