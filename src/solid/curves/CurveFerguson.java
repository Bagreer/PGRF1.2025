package solid.curves;

import solid.Solid;
import transforms.Cubic;
import transforms.Point3D;

public class CurveFerguson extends Solid {

    public CurveFerguson() {
        Point3D p1 = new Point3D(0, 0, 0); // Start
        Point3D p2 = new Point3D(0.5, 0, 0.5);   // Konec
        Point3D p3 = new Point3D(0, 0, 2);     // Tečna 1 (Vystřelí nahoru)
        Point3D p4 = new Point3D(1, 0, 0);     // Tečna 2 (Přiletí zprava)

        // Tady je ta změna: Cubic.FERGUSON
        Cubic cubic = new Cubic(Cubic.FERGUSON, p1, p2, p3, p4);

        int steps = 10;
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            vertexBuffer.add(cubic.compute(t));
            if (i > 0) addIndicies(i - 1, i);
        }
    }

}
