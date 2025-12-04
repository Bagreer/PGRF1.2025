package solid;

import transforms.Point3D;

public class Cylinder extends Solid {

    public Cylinder() {
        int segments = 12;

        final double radius = 0.5;
        final double deltaAngle = 2 * Math.PI / segments;

        for (int i = 0; i < segments; i++) {
            double angle = i * deltaAngle;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            vertexBuffer.add(new Point3D(x + 0.5, y + 0.5, 0));

        }

        for (int i = 0; i < segments; i++) {
            double angle = i * deltaAngle;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            vertexBuffer.add(new Point3D(x + 0.5, y + 0.5, 1));
        }

        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;

            int currentTop = i + segments;
            int nextTop = next + segments;

            addIndicies(i, next);

            addIndicies(i + segments, next + segments);

            addIndicies(i, i + segments);
        }

    }
}