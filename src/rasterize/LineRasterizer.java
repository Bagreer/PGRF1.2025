package rasterize;

import model.Line;
import raster.RasterBufferedImage;

public abstract class LineRasterizer {

    protected RasterBufferedImage raster;
    protected int color;

    public LineRasterizer(RasterBufferedImage raster, int color) {
        this.raster = raster;
        this.color = color;
    }

    public void rasterize(int x1, int y1, int x2, int y2) {

    }

    public void rasterize(Line line) {
        rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void setColor(int color) {
        this.color = color;
    }
}
