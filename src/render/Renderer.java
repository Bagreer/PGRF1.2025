package render;

import rasterize.LineRasterizer;
import solid.Solid;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.ArrayList;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;

    public Renderer(LineRasterizer lineRasterizer, int width, int height) {
        this.lineRasterizer = lineRasterizer;
        this.width = width;
        this.height = height;
    }

    public void renderSolid(Solid solid) {

        for(int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            int indexA = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            Point3D a = solid.getVertexBuffer().get(indexA);
            Point3D b = solid.getVertexBuffer().get(indexB);

            // transformace do okna
            Vec3D vecA = new Vec3D(a); //udelat z bodu vector, kvuli nasobeni ( nejde nasobit bod vektorem)
            Vec3D vecB = new Vec3D(b);


            // FIXME: vytvořit metodu pro tohle
//            vecA = vecA.mul(new Vec3D(1,-1,1)); //mul vraci nový vektor, potřeba ho uložit !!!
//            vecA = vecA.add(new Vec3D(1,1,0));
//            vecA = vecA.mul(new Vec3D((double) (width-1) / 2, (double)(height-1)/2, 1));
//
//            vecB = vecB.mul(new Vec3D(1,-1,1));
//            vecB = vecB.add(new Vec3D(1,1,0));
//            vecB = vecB.mul(new Vec3D((double) (width-1) / 2, (double)(height-1)/2, 1));

            vecA = transformVec(vecA); // transformVec vrací nový vetor
            vecB = transformVec(vecB);

            lineRasterizer.rasterize(
                    (int) Math.round(vecA.getX()), (int) Math.round(vecA.getY()),
                    (int) Math.round(vecB.getX()), (int) Math.round(vecB.getY())
            );
        }

    }

    /**
     * udělá 3 operace nad maticí aby se pokaždé nemusel psát kód znovu
     * @param vecA vector na kterem se provadi upravy
     * @return nový vektor
     */
    private Vec3D transformVec(Vec3D vecA) {
        vecA = vecA.mul(new Vec3D(1,-1,1)); //mul vraci nový vektor, potřeba ho uložit !!!
        vecA = vecA.add(new Vec3D(1,1,0));
        vecA = vecA.mul(new Vec3D((double) (width-1) / 2, (double)(height-1)/2, 1));

        return vecA;
    }

    public void renderSolids(ArrayList<Solid> solids) {
        //TODO implementovat

        for(Solid solid : solids) {
            renderSolid(solid);
        }

    }

}
