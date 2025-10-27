package raster;

import java.util.OptionalInt;

public interface Raster {


    void setPixel(int x, int y, int c);
    OptionalInt getPixel(int x, int y);

    int getWidth();
    int getHeight();

    void clear();
}
