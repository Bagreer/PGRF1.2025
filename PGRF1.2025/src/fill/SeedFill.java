package fill;

import raster.Raster;
import java.util.OptionalInt;

public class SeedFill implements Filler {

    private Raster raster;
    private int startX, startY;
    private int backgroundColor;
    private int fillColor;


    public SeedFill(Raster raster, int startX, int startY, int fillColor) {
        this.raster = raster;
        this.startX = startX;
        this.startY = startY;
        this.fillColor = fillColor;

        OptionalInt pixelColor = raster.getPixel(startX, startY);
        if (pixelColor.isPresent())
            this.backgroundColor = pixelColor.getAsInt();
    }


    @Override
    public void fill() {
        seedFill(startX,startY);
    }

    private void seedFill(int x, int y) {

        OptionalInt pixelColor = raster.getPixel(x, y);
        if (pixelColor.isEmpty()) return;


        if (pixelColor.getAsInt() != backgroundColor)
            return;


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
