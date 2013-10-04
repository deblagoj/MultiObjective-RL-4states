package Distribution;

import Matrix.ArrayDouble;

public class Test_distribution {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		double volume= 4.89;
	    int discretization = 50;
	    double [] user_demands = {1.92,  3.44,  0.13 };
	    double [] user_weights = {0.95,  0.03,  0.02};

	  Distribute a = new Distribute();
	    a.setInput(user_demands, user_weights, volume, discretization);
	    double[] calcDPQuadraticDistribution = a.calculateDPQuadraticDistribution();
	    double errorDPQuadraticDistribution=a.getQuadraticDistributionError();
	    
	    ArrayDouble t=new ArrayDouble();
	    t.PrintMatrix(calcDPQuadraticDistribution);
	    System.out.println("total error quadratic is "+errorDPQuadraticDistribution);
	    
	    double[] calcSimplex = a.calculateSimplex();
	    double errorSimplex=a.getSimplexResult();
	    
	    System.out.println(" distribution in simplex is ");
	    t.PrintMatrix(calcSimplex);
	    
	    System.out.println("total error Simplex is "+errorSimplex);
	    
	    
	    
	    
	}

}
