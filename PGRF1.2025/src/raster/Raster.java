package raster;

public interface Raster {


    void setPixel(int x, int y, int c);
    int getPixel(int x, int y);

    int getWidth();
    int getHeight();

    void clear();
}
