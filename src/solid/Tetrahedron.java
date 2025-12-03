package solid;

import transforms.Point3D;

public class Tetrahedron extends Solid {

    public Tetrahedron() {

        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(1, 0, 0));
        vertexBuffer.add(new Point3D(0.5, 1 * Math.sqrt(3.0) / 2.0, 0));
        vertexBuffer.add(new Point3D(0.5, 1 / (2.0 * Math.sqrt(3.0)),Math.sqrt(2.0/3.0)));

        addIndicies(0,1);
        addIndicies(0,2);
        addIndicies(0,3);

        addIndicies(1,2);
        addIndicies(1,3);

        addIndicies(2,3);

    }
}
