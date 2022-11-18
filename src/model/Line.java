package model;

import java.awt.*;

public class Line {
    private final int x1, y1;
    private final int x2, y2;
    private Color color = Color.WHITE;
    private Point centerPoint;

    private String type = "solids";

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        sumLineCenter();
    }

    public Line(Point point1, Point point2) {
        this.x1 = point1.getX();
        this.y1 = point1.getY();
        this.x2 = point2.getX();
        this.y2 = point2.getY();

        sumLineCenter();
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getCenter() {
        return this.centerPoint;
    }

    private void sumLineCenter() {
        this.centerPoint = new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    public double getLineLength() {
        return Math.sqrt(Math.pow(this.x2 - this.x1, 2) + Math.pow(this.y2 - this.y1, 2));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
