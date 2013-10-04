package kMeans;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Data.AbstactData;
import Data.IData;
import au.com.bytecode.opencsv.CSVReader;

public class Start {
	public static String[] nova;
	public static List<double[]> a;
    public static void main(String[] args) throws IOException, SQLException {
    	
	FileWriter writer = new FileWriter("out1.csv");

	AbstactData ad= new AbstactData();
	double[] niza=ad.getArray(1);
	
	
	
	
	int golemina=niza.length;
	
	/* Koristenje na tehnikata „Rule of thumb“ za odreduvanje na 
	optimalniot broj na klasteri
	*/
	int k_steps=(int) Math.sqrt(golemina/2);

	a=Arrays.asList(niza);
	
	List<Point> points = new ArrayList<Point>();
	System.out.println("sttt");
	for(int i=0; i<niza.length; i++)
	{
		String m=String.valueOf(niza[i]);
	    Point p = new Point(m);
	    points.add(p);
	}
	

	KMeans kmeans = new KMeans();
	for (int k = 1; k <= k_steps; k++) {
	    KMeansResult result = kmeans.calculate(points, k);
	    System.out.println("------- Con k=" + k + " ofv=" + result.getOfv()
		    + "-------\n");
	    int i = 0;
	    for (Cluster cluster : result.getClusters()) {
		i++;
		System.out.println("-- Cluster " + i + " --\n");
		for (Point punto : cluster.getPoints()) {
			System.out.println(punto.getX() + ", " + "\n");
		}
		System.out.println("\n");
		System.out.println(cluster.getCentroide().getX() + ", ");
		System.out.println("\n\n");
	    }
	}
	writer.close();
    }
}