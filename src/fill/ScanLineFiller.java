package fill;

import model.Edge;
import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;

import java.util.ArrayList;
import java.util.List;

public class ScanLineFiller implements Filler {
    private final LineRasterizer lineRasterizer;
    private final PolygonRasterizer polygonRasterizer;
    private final Polygon polygon;
    private final ArrayList<Point> points;

    public ScanLineFiller(Polygon polygon, LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
        this.points = polygon.getPoints();
    }

    @Override
    public void fill() {
        scanLine();
    }

    private void scanLine() {
        if (points.size() < 1)
            return;

        List<Edge> edges = new ArrayList<>();

        int yMin = points.get(0).getY();
        int yMax = yMin;

        for (int i = 0; i < points.size(); i++) {
            Point point1 = points.get(i);
            Point point2 = points.get((i + 1) % points.size());

            Edge edge = new Edge(point1.getX(), point1.getY(), point2.getX(), point2.getY());

            if (edge.isHorizontal())
                continue;

            edge.orientate();
            edge.calculate();
            edges.add(edge);

            //find yMin and yMax
            if (edge.getY1() < yMin)
                yMin = edge.getY1();

            if (edge.getY2() > yMax)
                yMax = edge.getY2();
        }

        for (int y = yMin; y < yMax; y++) {
            List<Integer> intersections = new ArrayList<>();

            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    intersections.add(edge.getIntersection(y));
                }
            }

            for (int i = 0; i < intersections.size() - 1; i++) {
                for (int j = 0; j < intersections.size() - i - 1; j++) {
                    if (intersections.get(j) > intersections.get(j + 1)) {
                        int biggerX = intersections.get(j);
                        intersections.set(j, intersections.get(j + 1));
                        intersections.set(j + 1, biggerX);
                    }
                }
            }

            for (int i = 0; i < intersections.size(); i += 2) {
                Line line = new Line(intersections.get(i), y, intersections.get(i + 1), y);
                lineRasterizer.rasterize(line);
            }
        }
        polygonRasterizer.rasterize(polygon);
    }
}
