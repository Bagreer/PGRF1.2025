package solid;

import transforms.Point3D;

public class Arrow extends Solid {

    public Arrow() {

        //naplnit vertex buffer
        vertexBuffer.add(new Point3D(0,0,0)); //v0
        vertexBuffer.add(new Point3D(0.5,0,0)); //v1
        vertexBuffer.add(new Point3D(0.5,-0.1,0)); //v2
        vertexBuffer.add(new Point3D(0.6,0,0)); //v3
        vertexBuffer.add(new Point3D(0.5,0.1,0)); //v4


        //naplnit index buffer
        addIndicies(0,1);
        addIndicies(2,3);
        addIndicies(3,4);
        addIndicies(4,2);

    }

}
