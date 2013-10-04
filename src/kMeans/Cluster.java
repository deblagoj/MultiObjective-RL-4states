package kMeans;
import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private List<Point> points = new ArrayList<Point>();
    private Point centroide;
    private boolean termino = false;

    public Point getCentroide() {
	return centroide;
    }

    public void setCentroide(Point centroide) {
	this.centroide = centroide;
    }

    public List<Point> getPoints() {
	return points;
    }

    public boolean isTermino() {
	return termino;
    }

    public void setTermino(boolean termino) {
	this.termino = termino;
    }

    public void clPoints() {
	points.clear();
    }

    @Override
    public String toString() {
	return centroide.toString();
    }
}
