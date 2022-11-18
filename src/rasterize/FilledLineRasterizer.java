package rasterize;

import java.awt.*;

public class FilledLineRasterizer extends LineRasterizer {
    private Color color;
    private String type;
    private int pixelCounter;

    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color, String type) {
        this.color = color;
        this.type = type;

        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;

        // pokud je x2 <= x1 -> x1 se otočí za x2
        if (x2 <= x1) {
            int _x1 = x1;
            x1 = x2;
            x2 = _x1;
        }

        // pokud je y2 <= y1 -> y1 se otočí za y2
        if (y2 <= y1) {
            int _y1 = y1;
            y1 = y2;
            y2 = _y1;
        }

        // pokud je vzdálenost bodů X1 a X2 větší než vzdálenost bodů Y1 a Y2 vykresluj podle osy X jinak vykresluj podle osy Y
        if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
            // pro všechny X na vzdálenosti X2 - X1 vypočítej všechna Y
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                setPixel(x, Math.round(y));
            }
        } else {
            // pro všechny Y na vzdálenosti X2 - X1 vypočítej všechna Y
            for (int y = y1; y <= y2; y++) {
                float x;

                // pokud je X1 stejné jako X2 nastav bod X jako jeden ze souřadnic X, jinak by se pro tyto hodnoty X rovnalo 0
                if (x1 == x2) {
                    x = x1;
                } else {
                    x = (y - q) / k;
                }

                setPixel(Math.round(x), y);
            }
        }
    }

    private void setPixel(int x, int y) {
        switch (type) {
            case "solids" -> raster.setPixel(x, y, this.color);
            case "dotted" -> {
                if (pixelCounter % 10 == 0) {
                    raster.setPixel(x, y, this.color);
                }
            }
            case "dashed" -> {
                if (pixelCounter % 20 > 10) {
                    raster.setPixel(x, y, this.color);
                }
            }
        }

        pixelCounter++;
    }
}
