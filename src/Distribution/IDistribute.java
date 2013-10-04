package Distribution;

public interface IDistribute {
	
	public void setInput ( double [] demands, double [] weights, double allocated, int discretization);

	
	
	public double [] calculateDPQuadraticDistribution ();
	public double getQuadraticDistributionError();
	
	
	public double [] calculateSimplex();
	public double getSimplexResult();
	
	
}
