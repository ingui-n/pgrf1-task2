package model;

import java.awt.*;
import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Point> points;
    private int movePointIndex = -1;
    private Color color = Color.WHITE;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public Point getPoint(int index) {
        if (index < 0)
            index = points.size() + index;

        return points.get(index);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getNextPoint(int index) {
        return getCount() > 1 ? getCount() == index + 1 ? points.get(0) : points.get(index + 1) : null;
    }

    public Point getPreviousPoint(int index) {
        return getCount() > 1 ? index == 0 ? points.get(getCount() - 1) : getPoint(index - 1) : getPoint(0);
    }

    public int getCount() {
        return points.size();
    }

    public int getMovePointIndex() {
        return movePointIndex;
    }

    public void setMovePointIndex(int movePointIndex) {
        this.movePointIndex = movePointIndex;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void addPoint(Point point, int index) {
        points.add(index, point);
    }

    public void replacePoint(Point point, int index) {
        points.remove(index);
        points.add(index, point);
    }

    public void removeLastPoint() {
        points.remove(getCount() - 1);
    }

    public void clear() {
        points.clear();
    }

    public void removeClosestPoint(int mouseX, int mouseY) {
        if (getCount() == 0)
            return;

        Point closestPoint = getClosestPoint(mouseX, mouseY);

        Line line = new Line(closestPoint.getX(), closestPoint.getY(), mouseX, mouseY);

        if (line.getLineLength() < 20)
            points.remove(closestPoint);
    }

    public Point getClosestPoint(int x, int y) {
        double distance = points.get(0).countDistance(x, y);
        int pointIndex = 0;

        for (int i = 1; i < getCount(); i++) {
            double testDistance = points.get(i).countDistance(x, y);

            if (distance > testDistance) {
                pointIndex = i;
                distance = testDistance;
            }
        }

        return points.get(pointIndex);
    }

    public int getPointIndex(Point point) {
        for (int i = 0; i <= getCount(); i++) {
            if (getPoint(i).equals(point))
                return i;
        }

        return -1;
    }

    public void moveClosestPointInPolygon(int mouseX, int mouseY) {
        if (getCount() == 0)
            return;

        Point closestPoint = getClosestPoint(mouseX, mouseY);

        Line polygonPointToMouse = new Line(closestPoint.getX(), closestPoint.getY(), mouseX, mouseY);

        if (polygonPointToMouse.getLineLength() > 20)
            return;

        int closestPointIndex = getPointIndex(closestPoint);
        movePointIndex = closestPointIndex;

        Point point = new Point(mouseX, mouseY);
        replacePoint(point, closestPointIndex);
    }

    public Polygon setPoints(ArrayList<Point> points) {
        clear();

        for (Point p : points)
            addPoint(p);

        return this;
    }

    public void spinPoints() {
        ArrayList<Point> points = new ArrayList<>();

        for (int i = getCount() - 1; i >= 0; i--)
            points.add(getPoint(i));

        setPoints(points);
    }
}