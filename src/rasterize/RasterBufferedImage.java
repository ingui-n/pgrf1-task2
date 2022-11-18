package rasterize;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster {
    private final BufferedImage img;
    private Color color;

    public RasterBufferedImage(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        if (isOnRaster(x, y))
            img.setRGB(x, y, color.getRGB());
    }

    @Override
    public int getPixel(int x, int y) {
        return img.getRGB(x, y);
    }

    public void present(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, getWidth(), getHeight());
    }

    public void clear(Color color) {
        Graphics gr = img.getGraphics();
        gr.setColor(color);
        gr.fillRect(0, 0, getWidth(), getHeight());
    }

    public BufferedImage getImg() {
        return img;
    }

    public Graphics getGraphics() {
        return img.getGraphics();
    }

    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw(RasterBufferedImage raster) {
        Graphics graphics = getGraphics();
        graphics.setColor(color);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(raster.img, 0, 0, null);
    }

    @Override
    public void setClearColor(Color color) {
        this.color = color;
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    public boolean isOnRaster(int x, int y) {
        return x > -1 && x < getWidth() && y > -1 && y < getHeight();
    }

    public Color getBackgroundColor() {
        return this.color;
    }
}
