package fill;

import rasterize.RasterBufferedImage;

import java.awt.*;

public class SeedFiller implements Filler {
    private final int x;
    private final int y;
    private final Color fillColor, backgroundColor;

    private final RasterBufferedImage raster;

    public SeedFiller(int x, int y, Color fillColor, RasterBufferedImage raster) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = raster.getBackgroundColor();
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

        if (color.equals(backgroundColor)) {
            raster.setPixel(x, y, fillColor);

            seedFill(x + 1, y);
            seedFill(x - 1, y);
            seedFill(x, y + 1);
            seedFill(x, y - 1);
        }
    }
}
