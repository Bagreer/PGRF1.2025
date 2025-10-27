package fill;

import raster.RasterBufferedImage;

import java.awt.image.Raster;
import java.util.OptionalInt;

public class SeedFill implements Filler {

    private int startX,startY;
    private int Backgroundcolor;
    private int fillColor;
    private raster.Raster raster;

    public SeedFill(int startX, int startY, int color, int fillColor, RasterBufferedImage raster) {
        this.startX = startX;
        this.startY = startY;
        this.Backgroundcolor = color;
        this.fillColor = fillColor;
        this.raster = (raster.Raster) raster;

        OptionalInt pixelColor = raster.getPixel(startX,startY);
    }

    @Override
    public void fill() {
        seedFill(startX,startY);
    }

    private void seedFill(int x, int y) {

        OptionalInt pixelColor = raster.getPixel(x,y);

        if (pixelColor.isEmpty()) {return;}

        System.out.println("filled");

        if (pixelColor.getAsInt() != Backgroundcolor) {
            return;
        }

        raster.setPixel(x,y,fillColor);

        seedFill(x + 1,y);
        seedFill(x - 1,y);
        seedFill(x,y + 1);
        seedFill(x,y - 1);

        // z rastru načtu barvu z x a y
        // podmínka: obarvit ?
        // pokud ne: return (konec)
        // pokud ano: obarvim
        //zavolat metodu pro sousedy 4x rekurze
    }
}
