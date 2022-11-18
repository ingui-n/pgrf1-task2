package view;

import rasterize.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Panel extends JPanel {
    private RasterBufferedImage raster;

    private String mode = "polygon";

    private static final int FPS = 30;

    Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        raster = new RasterBufferedImage(width, height);

        raster.setClearColor(Color.BLACK);
        setLoop();
    }

    public RasterBufferedImage getRaster() {
        return raster;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public boolean isNotMode(String mode) {
        return !this.mode.equals(mode);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.repaint(g);
    }

    public void resize() {
        if (this.getWidth() < 1 || this.getHeight() < 1)
            return;
        if (this.getWidth() <= raster.getWidth() && this.getHeight() <= raster.getHeight())
            return;
        RasterBufferedImage newRaster = new RasterBufferedImage(this.getWidth(), this.getHeight());

        newRaster.draw(raster);
        raster = newRaster;
    }

    private void setLoop() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, FPS);
    }

    public void clear() {
        raster.clear();
        printLegend();
    }

    public void printLegend() {
        Graphics gr = raster.getGraphics();

        gr.drawString("Clear [C]", 5, 15);
        gr.drawString("Scan line fill polygon [S]", 5, 30);
        gr.drawString("Clip polygon with polygon [W]", 5, 45);
        gr.drawString("Draw clipping polygon [CTRL + mouse]", 5, 60);
        gr.drawString("Seed fill [RBM]", 5, 75);
        gr.drawString("Seed fill border [CTRL + RMB]", 5, 90);
    }
}
