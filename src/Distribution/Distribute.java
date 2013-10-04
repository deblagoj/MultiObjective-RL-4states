package Distribution;


import Matrix.ArrayDouble;
import Matrix.Matrix3DDouble;
import Matrix.MatrixDouble;

public class Distribute implements IDistribute {

	
	private static	double demands[];
	private static	double weights [];
	private static int disk;
	private static double volume;
	
	private static double[] volume_discretized;
	
	
	private double result;
	private double [] distribution;
	
	@Override
	public void setInput(double[] demands, double[] weights, double allocated, int discretization) {
		this.demands=demands;
		this.weights=weights;
		this.volume=allocated;
		this.disk= discretization;
	}

	

	@Override
	public double[] calculateDPQuadraticDistribution() {
		DpDistribution Dp= new DpDistribution(volume, demands, weights, disk);
		this.distribution=Dp.getDistribution();
		this.result=Dp.get_quadraticDiffence();
		return distribution;
	}

	@Override
	public double getQuadraticDistributionError() {
	
		return result;
	}

	@Override
	public double[] calculateSimplex() {
		int objectives=weights.length;
		int i;
		//System.out.println("------- "+objectives);
		double [][] A = new double [objectives+1][objectives+1];
		
		/*double[][] A = {
	            { 1,  1,  1, 1 },
	            { 1,  0,  0, 0 },
	            { 0,  1,  0, 0 },
	            { 0,  0,  1, 0 },
	            
	        };
		*/
		for(i=0;i<objectives+1;i++) A[0][i]=1;
		for (i=1;i<objectives+1; i++) A[i][i-1]=1;
		
		
		double [] b=new double [objectives+1];
		b[0]=volume;
		for (i=0;i<objectives;i++) b[i+1]= demands[i];
		Simplex t1= new Simplex(A, b, weights);
		
		//MatrixDouble t = new MatrixDouble(A);
		//t.PrintMatrix();
		
		//ArrayDouble k = new ArrayDouble();
		//k.PrintMatrix(b);
		//k.PrintMatrix(weights);
		
		
		
		distribution=t1.calculate(A, b, weights);
		
		result=t1.result();
		
		
		
		return distribution;
	}

	@Override
	public double getSimplexResult() {
		// TODO Auto-generated method stub
		return result;
	}
	
	

}
