package solid;

import transforms.Point3D;

public class Cube extends Solid {

    public Cube() {
        vertexBuffer.add(new Point3D(0, 0, 0));     //v0
        vertexBuffer.add(new Point3D(1, 0, 0));     //v1
        vertexBuffer.add(new Point3D(0, 1, 0));     //v2
        vertexBuffer.add(new Point3D(1, 1, 0));     //v3

        vertexBuffer.add(new Point3D(0, 0, 1));     //v4
        vertexBuffer.add(new Point3D(1, 0, 1));     //v5
        vertexBuffer.add(new Point3D(0, 1, 1));     //v6
        vertexBuffer.add(new Point3D(1, 1, 1));     //v7

        addIndicies(0,1);
        addIndicies(0,2);
        addIndicies(2,3);
        addIndicies(1,3);

        addIndicies(4,5);
        addIndicies(4,6);
        addIndicies(5,7);
        addIndicies(6,7);

        addIndicies(0,4);
        addIndicies(1,5);
        addIndicies(2,6);
        addIndicies(3,7);
    }

}
