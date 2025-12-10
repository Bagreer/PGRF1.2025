package render;

import rasterize.LineRasterizer;
import solid.Solid;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.ArrayList;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;

    private Mat4 view, proj;

    public Renderer(LineRasterizer lineRasterizer, int width, int height, Mat4 proj,Mat4 view) {
        this.lineRasterizer = lineRasterizer;
        this.width = width;
        this.height = height;
        this.proj = proj;
        this.view = view;
    }



    public void renderSolid(Solid solid) {

        //TODO optimalizace - nasobeni vsech matic spolu a pak bod nebo jednotlice

        for(int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            int indexA = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            Point3D a = solid.getVertexBuffer().get(indexA);
            Point3D b = solid.getVertexBuffer().get(indexB);

            //done - modelovací matice (kazde teleso ma svoje) model -> world space
            a = a.mul(solid.getModel());
            b = b.mul(solid.getModel());

            //pohledová matice (cela scena ma jednu)
            a = a.mul(view);
            b = b.mul(view);

            //projekční matice (cela scena ma jednu)
            a = a.mul(proj);
            b = b.mul(proj);

            //ořezání
            if (a.getW() <= 0.1 || b.getW() <= 0.1) return; // nebo continue

            //dehomogenizace
            a = a.mul(1/a.getW());  //pozor na deleni nulou
            b = b.mul(1/b.getW());

            // transformace do okna
            Vec3D vecA = transformToWindow(a); //udelat z bodu vector, kvuli nasobeni ( nejde nasobit bod vektorem)
            Vec3D vecB = transformToWindow(b);

            lineRasterizer.rasterize(
                    (int) Math.round(vecA.getX()), (int) Math.round(vecA.getY()),
                    (int) Math.round(vecB.getX()), (int) Math.round(vecB.getY())
            );
        }
    }

    /**
     * udělá 3 operace nad maticí aby se pokaždé nemusel psát kód znovu
     * @param point vector na kterem se provadi upravy
     * @return nový vektor
     */
    private Vec3D transformToWindow(Point3D point) {
        return new Vec3D(point).mul(new Vec3D(1,-1,1))
                .add(new Vec3D(1,1,0))
                .mul(new Vec3D((double) (width-1) / 2, (double)(height-1)/2, 1));
    }


    public void renderSolids(ArrayList<Solid> solids) {
        //TODO implementovat

        for(Solid solid : solids) {
            renderSolid(solid);
        }

    }
    public void setView(Mat4 view) {
        this.view = view;
    }
    public void setProj(Mat4 proj) {
        this.proj = proj;
    }
}
