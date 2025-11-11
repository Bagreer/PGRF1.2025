package clip;

import model.Point;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * základ pro ořezávání polygonu
 */

public class Clipper {

    final float EPSILON = 0.000001f;

    /** z dvou polygonů najde společné území resp. společné body
     *
     * @param clippingPoints ořezávané body
     * @param pointsToClip ořezávací body
     * @return seznam bodů, které označují ořezanou oblast
     */
    public List<Point> clip(List<Point> clippingPoints, List<Point> pointsToClip) {

        List<Point> polygonForClipping = new ArrayList<>(clippingPoints);

        if (clippingPoints.size() < 3 || polygonForClipping.size() < 3) return new ArrayList<>();    //kdyz ma mene jak 3 body tak se vrati prazdny


        for (int i = 0; i < pointsToClip.size(); i++) {

            float VectorPart1 = 0, VectorPart2 = 0;

            if (i >= pointsToClip.size() - 1) {     // reseni poseldniho bodu
                VectorPart1 = pointsToClip.getFirst().getX() - pointsToClip.get(i).getX();
                VectorPart2 = pointsToClip.getFirst().getY() - pointsToClip.get(i).getY();
            } else {
                VectorPart1 = pointsToClip.get(i+1).getX() - pointsToClip.get(i).getX();
                VectorPart2 = pointsToClip.get(i+1).getY() - pointsToClip.get(i).getY();
            }

            // vektor
            ArrayList<Float> vector = new ArrayList<>();
            vector.add(VectorPart1); vector.add(VectorPart2);
            System.out.println(vector);

            // normálový vektor
            ArrayList<Float> normalVector = new ArrayList<>();
            normalVector.add(-VectorPart2); normalVector.add(VectorPart1);
            System.out.println(normalVector);

            //dat do cyklu abych hledal vsechny hrany

            Point v1 = polygonForClipping.getLast(); // nebo Point v1 = in.get(in.size() - 1);
            List<Point> pointsToReturn = new ArrayList<>();

            // pocitam vektor v cyklu aby se pořešil každý bod v polygonu
            for (Point v2 : polygonForClipping) {

                boolean isInsideV1;
                boolean isInsideV2;

                ArrayList<Integer> vectorV1 = new ArrayList<>();
                ArrayList<Integer> vectorV2 = new ArrayList<>();

                Point p1 = pointsToClip.get(i);
                Point p2 = pointsToClip.get((i + 1) % pointsToClip.size());

                //pocitani vektoru k pointu
                vectorV1.add(v1.getX() - p1.getX());
                vectorV1.add(v1.getY() - p1.getY());

                vectorV2.add(v2.getX() - p1.getX());
                vectorV2.add(v2.getY() - p1.getY());

                // vypocet skalarniho soucinu (normalVector • vectorToPoint)
                float dotProductV1 =vectorV1.get(0) * normalVector.get(0) + vectorV1.get(1) * normalVector.get(1);
                float dotProductV2 =vectorV2.get(0) * normalVector.get(0) + vectorV2.get(1) * normalVector.get(1);

                isInsideV1 = dotProductV1 <= EPSILON;
                isInsideV2 = dotProductV2 <= EPSILON;

                if (isInsideV1) {
                    if (isInsideV2) {
                        pointsToReturn.add(v2);
                    } else {
                        pointsToReturn.add(getIntersection(v1,v2,p1,p2));
                    }
                } else {
                    if (isInsideV2) {
                        pointsToReturn.add(getIntersection(v1,v2,p1,p2));
                        pointsToReturn.add(v2);
                    } else {
                        continue;
                    }
                }

                v1 = v2;
            }

            polygonForClipping = pointsToReturn;
            if (polygonForClipping.isEmpty()) return new ArrayList<>();

        }

        return polygonForClipping;
    };


    // TODO dopsat alg. - slide 21 - ořezání
    // zjistit jestli je bod vlevo nebo vpravo
    // tecny vektor (slide 28)
    // normála (slide 28)
    // vektor k bodu, o kterem zjistuji kde se nachazi (slide 28)
    // - dot product - skalární součin - slide 28 - ořezání + opakování lineární algebra
    // - podle znaménka určit, jestli je vlevo nebo vpravo - slide 28 - ořezání


    private Point getIntersection(Point v1, Point v2, Point p1, Point p2) {

        double x1,x2,x3,x4,y1,y2,y3,y4;
        x1 = v1.getX();
        x2 = v2.getX();
        y1 = v1.getY();
        y2 = v2.getY();

        x3 = p1.getX();
        x4 = p2.getX();
        y3 = p1.getY();
        y4 = p2.getY();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4); // stejne u obou vypocita se jenom jednou a ne 2x
        if (denominator == 0) return null;

        double x = (((x1*y2)-(x2*y1))*(x3-x4)-((x3*y4)-(x4*y3))*(x1-x2))
                /
                denominator;

        double y = (((x1*y2)-(x2*y1))*(y3-y4)-((x3*y4)-(x4*y3))*(y1-y2))
                /
                denominator;

        return new Point((int) Math.round(x), (int) Math.round(y));
    };

}



