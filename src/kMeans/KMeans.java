package kMeans;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
    public KMeansResult calculate(List<Point> puntos, Integer k) {
	List<Cluster> clusters = getCentroides(puntos, k);

	while (!finalizo(clusters)) {
	    prepareClusters(clusters);
	    assignPoints(puntos, clusters);
	    recalculateCentroides(clusters);
	}

	Double ofv = calcularFuncionObjetivo(clusters);

	return new KMeansResult(clusters, ofv);
    }

    private void recalculateCentroides(List<Cluster> clusters) {
	for (Cluster c : clusters) {
	    if (c.getPoints().isEmpty()) {
		c.setTermino(true);
		continue;
	    }

	    Float newX = 0f;
	    Float newY = 0f;
	    for (Point p : c.getPoints()) {
		newX += p.getX();
	
	    }

	    Point newCentroide = new Point(newX / c.getPoints().size(),
		    newY / c.getPoints().size());

	    if (newCentroide.equals(c.getCentroide())) {
		c.setTermino(true);
	    } else {
		c.setCentroide(newCentroide);
	    }
	}
    }

    private void assignPoints(List<Point> points, List<Cluster> clusters) {
	for (Point punto : points) {
	    Cluster mas = clusters.get(0);
	    Double distanceMin = Double.MAX_VALUE;
	    for (Cluster cluster : clusters) {
		Double distance = punto.distanciaEuclideana(cluster
			.getCentroide());
		if (distanceMin > distance) {
		    distanceMin = distance;
		    mas = cluster;
		}
	    }
	    mas.getPoints().add(punto);
	}
    }

    private void prepareClusters(List<Cluster> clusters) {
	for (Cluster c : clusters) {
	    c.clPoints();
	}
    }

    private Double calcularFuncionObjetivo(List<Cluster> clusters) {
	Double ofv = 0d;

	for (Cluster cluster : clusters) {
	    for (Point punto : cluster.getPoints()) {
		ofv += punto.distanciaEuclideana(cluster.getCentroide());
	    }
	}

	return ofv;
    }

    private boolean finalizo(List<Cluster> clusters) {
	for (Cluster cluster : clusters) {
	    if (!cluster.isTermino()) {
		return false;
	    }
	}
	return true;
    }

    private List<Cluster> getCentroides(List<Point> puntos, Integer k) {
	List<Cluster> centroides = new ArrayList<Cluster>();


	Float minX = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY, minY = Float.POSITIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;

	for (Point punto : puntos) {
	    minX = minX > punto.getX() ? punto.getX() : minX;
	    maxX = maxX < punto.getX() ? punto.getX() : maxX;
	    
	}

	Random random = new Random();

	for (int i = 0; i < k; i++) {
	    Float x = random.nextFloat() * (maxX - minX) + minX;
	    Float y = random.nextFloat() * (maxY - minY) + minY;

	    Cluster c = new Cluster();
	    Point centroide = new Point(x, y);
	    c.setCentroide(centroide);
	    centroides.add(c);
	}

	return centroides;
    }
}
