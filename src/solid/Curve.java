package solid;

import transforms.Point3D;

public class Curve extends Solid{

    public Curve() {
        int steps = 10;

        double range = Math.PI;

        double scaleY = 0.5;
        double scaleX = 0.5;

        for (int i = 0; i <= steps; i++) {

            double t = (double) i / steps;
            double x = (t * range) * scaleX;
            double z = Math.sin(t * range) * scaleY;
            double y = 0;

            vertexBuffer.add(new Point3D(x, y, z));

            if (i > 0) {
                addIndicies(i - 1, i);
            }
        }
    }
}
