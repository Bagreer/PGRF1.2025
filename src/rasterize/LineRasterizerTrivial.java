package rasterize;

import raster.RasterBufferedImage;

import javax.sound.sampled.Line;

public class LineRasterizerTrivial extends LineRasterizer {

    public LineRasterizerTrivial(RasterBufferedImage raster, int color) {
        super(raster, color);
    }


    /**f-ce vykreslující úsečku pomocí triviálního algoritmu
     *
     * @param x1 souřadnice x prvního bodu
     * @param y1 souřadnice y prvního bodu
     * @param x2 souřadnice x druhého bodu
     * @param y2 souřadnice y druhého bodu
     */
    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {     //triviální algoritmus
        float k = 0;
        float q = 0;

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
