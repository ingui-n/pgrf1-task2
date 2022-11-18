package clip;

import model.Edge;
import model.Point;
import model.Polygon;

public class Clipper {
    private Polygon polygon;
    private final Polygon polygonClipper;

    public Clipper(Polygon polygonClipper, Polygon polygon) {
        this.polygonClipper = polygonClipper;
        this.polygon = polygon;
    }

    public Polygon getClip() {
        Polygon newPolygon = new Polygon();

        for (int i = 0; i < polygonClipper.getCount(); i++) {
            if (polygon.getCount() > 0) {
                newPolygon = new Polygon();

                Point point1 = polygonClipper.getPoint(i);
                Point point2 = polygonClipper.getPoint((i + 1) % polygonClipper.getCount());

                Edge edge = new Edge(point1.getX(), point1.getY(), point2.getX(), point2.getY());

                Point v1 = polygon.getPoint(-1);

                for (Point v2 : polygon.getPoints()) {
                    if (isInLine(v2, edge)) {
                        if (!isInLine(v1, edge))
                            newPolygon.addPoint(getIntersection(v1, v2, edge));

                        newPolygon.addPoint(v2);
                    } else {
                        if (isInLine(v1, edge))
                            newPolygon.addPoint(getIntersection(v1, v2, edge));
                    }
                    v1 = v2;
                }
                polygon = newPolygon;
            }
        }

        return newPolygon;
    }

    private Point getIntersection(Point v1, Point v2, Edge edge) {
        double division = (v1.getX() - v2.getX()) * (edge.getY1() - edge.getY2()) - (v1.getY() - v2.getY()) * (edge.getX1() - edge.getX2());
        double x1 = (((v1.getX() * v2.getY() - v2.getX() * v1.getY()) * (edge.getX1() - edge.getX2()) - (edge.getX1() * edge.getY2() - edge.getX2() * edge.getY1()) * (v1.getX() - v2.getX())) / division);
        double y1 = (((v1.getX() * v2.getY() - v2.getX() * v1.getY()) * (edge.getY1() - edge.getY2()) - (edge.getX1() * edge.getY2() - edge.getX2() * edge.getY1()) * (v1.getY() - v2.getY())) / division);

        return new Point(x1, y1);
    }

    private boolean isInLine(Point v2, Edge edge) {
        return v2.getX() * (edge.getY2() - edge.getY1()) - v2.getY() * (edge.getX2() - edge.getX1()) + edge.getX2() * edge.getY1() - edge.getY2() * edge.getX1() > 0;
    }
}
