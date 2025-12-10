package solid.curves;

import solid.Solid;
import transforms.Cubic;
import transforms.Point3D;

public class CurveBezier extends Solid {

    public CurveBezier() {
        Point3D p1 = new Point3D(0, 0, 0);
        Point3D p2 = new Point3D(0.5, 0, 1);
        Point3D p3 = new Point3D(0.75, 0, 0.75);
        Point3D p4 = new Point3D(1.5, 0, 0);

        // 2. Inicializace pomocné třídy Cubic
        // Předáme jí TYP křivky (BEZIER) a body
        Cubic cubic = new Cubic(Cubic.BEZIER, p1, p2, p3, p4);

        // 3. Generování bodů (diskretizace)
        int steps = 10; // Počet dílků (čím víc, tím hladší)

        for (int i = 0; i <= steps; i++) {
            // Parametr t musí jít od 0.0 do 1.0
            double t = (double) i / steps;

            // Necháme třídu Cubic vypočítat bod pro dané t
            Point3D computedPoint = cubic.compute(t);

            // Uložíme bod do vertex bufferu (tohle je vlastnost třídy Solid)
            vertexBuffer.add(computedPoint);

            // Pokud nejsme u prvního bodu, spojíme tento bod s předchozím čarou
            if (i > 0) {
                addIndicies(i - 1, i);
            }
        }
    }

}
