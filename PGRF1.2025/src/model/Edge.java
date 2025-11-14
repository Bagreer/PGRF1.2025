package model;

public class Edge {
    private int x1, y1, x2, y2;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Edge(Point p1, Point p2) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    /**
     * spocita jestli se y rovnaji, pokud ano tak vraci True
     *
     * @return je horizontalni
     */
    public boolean isHorizontal() {
        return y1 == y2; // vrati true, kdyz je hodnota rovna, jinak vrati false
    }

    public void orientate() {
        if(y1 > y2) {
            //TODO prohodit x a y

            int temp = y1;
            y1 = y2;
            y2 = temp;

            temp = x1;
            x1 = x2;
            x2 = temp;
        }
    }

    public boolean isIntersection(int y) {
        return y1 <= y && y < y2;
    }

    /**
     * spočítá průsečík s přímkou v urřitém bodě
     * @param y řádek, ve kterém se nacházíme
     * @return vrátí průsečík s přímkou na dané výšce y
     */
    public int getIntersection(int y) {
        //TODO spočítat průsečíky

        if(x1 == x2) return x1;

        double k = 0;
        double q = 0;

        k = (double) (y2 - y1) / (x2 - x1);
        q = y1 - k * x1;

        return (int) Math.round((y - q) / k);
    }
}
