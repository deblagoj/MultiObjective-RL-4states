package kMeans;
public class Point {
    private float x;
   

    public Point(String m) {
	super();
	this.x = Float.parseFloat(m);
	
    }

    public Point(Float x, Float y) {
	this.x = x;
	
    }

    public float getX() {
	return x;
    }

   

    @Override
    public String toString() {
	return "(" + x + ")";
    }

    public Double distanciaEuclideana(Point destino) {
	return Math.sqrt(Math.pow(destino.x - this.x, 2));
		
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(x);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	

	return result;
    }

    @Override
    public boolean equals(Object obj) {
	Point other = (Point) obj;
	if (this.x == other.x ) {
	    return true;
	} else {
	    return false;
	}
    }
}