package solid.curves;

import solid.Curve;
import solid.Solid;
import transforms.Cubic;
import transforms.Point3D;

public class CurveCoons extends Solid {

    public CurveCoons() {
        Point3D p1 = new Point3D(-1.5, 0, -2);

        // P2: Táhne doprava a dozadu (Z > 0)
        Point3D p2 = new Point3D(1.0, 0, 0.5);

        // P3: Táhne doleva a dozadu (Z > 0) - tím vznikne překřížení/smyčka
        Point3D p3 = new Point3D(-1.5, 0, 0.5);

        // P4: Konec - leží na ose X vpravo
        Point3D p4 = new Point3D(0.8, 0, 2);

        // Změna je pouze ZDE: Použijeme matici Cubic.COONS
        Cubic cubic = new Cubic(Cubic.COONS, p1, p2, p3, p4);

        int steps = 10; // Počet segmentů

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;

            // Vypočítat bod
            Point3D p = cubic.compute(t);
            vertexBuffer.add(p);

            // Spojit s předchozím
            if (i > 0) {
                addIndicies(i - 1, i);
            }
        }
    }

}
