package fill;

import rasterize.RasterBufferedImage;

import java.awt.*;

public class SeedFillerBorder implements Filler {
    private final int x;
    private final int y;
    private final Color fillColor, stopColor1, stopColor2;

    private final RasterBufferedImage raster;

    public SeedFillerBorder(int x, int y, Color fillColor, Color stopColor1, Color stopColor2, RasterBufferedImage raster) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.stopColor1 = stopColor1;
        this.stopColor2 = stopColor2;
        this.raster = raster;
    }

    @Override
    public void fill() {
        seedFill(x, y);
    }

    private void seedFill(int x, int y) {
        if (!raster.isOnRaster(x, y))
            return;

        Color color = new Color(raster.getPixel(x, y));

        if (!color.equals(stopColor1) && !color.equals(stopColor2) && !color.equals(fillColor)) {
            raster.setPixel(x, y, fillColor);

            seedFill(x + 1, y);
            seedFill(x - 1, y);
            seedFill(x, y + 1);
            seedFill(x, y - 1);
        }
    }
}
