package rasterize;

import raster.RasterBufferedImage;

import java.awt.*;

public class LineRasterizerTrivialColor extends LineRasterizer {

    public LineRasterizerTrivialColor(RasterBufferedImage raster, int color) {
        super(raster, color);
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {     //triviální algoritmus
        float k = 0;
        float q = 0;


        //prednaska


        Color c1 = Color.red;
        Color c2 = Color.green;

        float[] colorComponentsC1 = c1.getRGBComponents(null);
        float[] colorComponentsC2 = c2.getRGBComponents(null);

        for(int x = x1; x <= x2; x++) {
            //odečtu minimum a dělím rozsahem

            for(int i = 0; i <= 3; i++) {
                //newColors[k] = (1-t) * c1 + t * c2;
            }

            int y = Math.round(k*x+q);
            raster.setPixel(x,y,color);
        }


        if (x1 == x2) {     //reseni kdyz se rovnaji x (vzniklo by deleni nulou)
            int x = x1;

            if (y1 > y2) {      //kdyz je jedno vetsi tak se prohodi kvuli zaporu
                int s = y1;
                y1 = y2;
                y2 = s;
            }

            for (int y = y1; y < y2 ; y++) {    //vykresleni vertikalni cary
                raster.setPixel(x, y, color);
            }
        } else {
            k = (y2 - y1) / (float) (x2 - x1);  //vyřešit když x1 a x2 jsou stejné
            q = y1 - k * x1;
        }

        if (y1 > y2) {      //kdyz je jedno vetsi tak se prohodi kvuli zaporu
            int s = y1;
            y1 = y2;
            y2 = s;
        }

        if (x1 > x2) {      //kdyz je jedno vetsi tak se prohodi kvuli zaporu
            int s = x1;
            x1 = x2;
            x2 = s;
        }

        if ((x2 - x1) < (y2 - y1)) {
            for (int y = y1; y < y2; y++) {         //vykresleni podle y
                int x = Math.round((y - q) / k);

                raster.setPixel(x,y, color);
            }
        } else {
            for (int x = x1; x <= x2; x++) {        //vykresleni podle x
                int y = Math.round(k * x + q);

                raster.setPixel(x, y, color);
            }
        }
    }
}
