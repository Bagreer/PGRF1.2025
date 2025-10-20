package rasterize;

import raster.RasterBufferedImage;

import java.awt.*;

public class LineRasterizerTrivialColor extends LineRasterizer {

    public LineRasterizerTrivialColor(RasterBufferedImage raster, int color) {
        super(raster, color);
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {

        if (x1 > x2) {

            int tmp = x1;   // Prohazujeme X i Y, aby t=0 vždy odpovídalo startovní barvě
            x1 = x2;
            x2 = tmp;

            int tem = y1;
            y1 = y2;
            y2 = tem;
        }

        if (x1 == x2) { //deleni nulou

            if (y1 > y2) {
                int s = y1;
                y1 = y2;
                y2 = s;
            }

            for (int y = y1; y <= y2; y++) {
                float t = (y - y1) / (float) (y2 - y1);
                int argb = interpolateColor(t, Color.BLUE, Color.GREEN);
                raster.setPixel(x1, y, argb);
            }
            return;
        }

        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;

        if (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) {

            for (int x = x1; x <= x2; x++) {

                float t = (x - x1) / (float) (x2 - x1);

                int argb = interpolateColor(t, Color.BLUE, Color.GREEN);

                int y = Math.round(k * x + q);

                raster.setPixel(x, y, argb);
            }
        } else {

            if (y1 > y2) {
                int tmpY = y1;
                y1 = y2;
                y2 = tmpY;
                int tmpX = x1;
                x1 = x2;
                x2 = tmpX;
            }

            for (int y = y1; y <= y2; y++) {

                int x = Math.round((y - q) / k);

                float t = (x - x1) / (float) (x2 - x1);

                int argb = interpolateColor(t, Color.BLUE, Color.GREEN);

                raster.setPixel(x, y, argb);
            }
        }
    }

    /**
     * pomocna metoda pro interpolaci
     *
     * @param t  keoficient pro interpolaci
     * @param c1 první barva
     * @param c2 druhá barva
     * @return barva jako int
     */
    private int interpolateColor(float t, Color c1, Color c2) {

        float[] components1 = c1.getRGBComponents(null); // jednotlive složky prvni barvy
        float[] components2 = c2.getRGBComponents(null); // jednotlive složky druhe barvy
        float[] newColors = new float[4];

        // Vzorec: vC = (1 - t) * vA + t * vB
        for (int i = 0; i < 3; i++) { // R, G, B
            newColors[i] = (1 - t) * components1[i] + t * components2[i];
        }

        // Převod float na int
        int rInt = Math.min(255, Math.max(0, (int) Math.round(newColors[0] * 255.0f)));
        int gInt = Math.min(255, Math.max(0, (int) Math.round(newColors[1] * 255.0f)));
        int bInt = Math.min(255, Math.max(0, (int) Math.round(newColors[2] * 255.0f)));
        int aInt = Math.min(255, Math.max(0, (int) Math.round(newColors[3] * 255.0f)));

        // Sestavení 32-bit int kódu 0xAARRGGBB pomocí bitového posunu
        return (aInt << 24) | (rInt << 16) | (gInt << 8) | bInt;
    }
}