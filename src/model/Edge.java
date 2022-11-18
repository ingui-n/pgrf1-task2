package model;

public class Edge {
    private int x1, y1, x2, y2;
    private float k, q;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getK() {
        return k;
    }

    public float getQ() {
        return q;
    }

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public void orientate() {
        if (y1 > y2) {
            int _x1 = x1;
            int _y1 = y1;
            x1 = x2;
            y1 = y2;
            x2 = _x1;
            y2 = _y1;
        }
    }

    public void calculate() {
        k = (y2 - y1) / (float) (x2 - x1);
        q = y1 - k * x1;
    }

    public boolean isIntersection(int y) {
        return y > y1 && y <= y2;
    }

    public int getIntersection(int y) {
        return x1 == x2 ? x1 : (int) ((y - q) / k);
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
}
