package Distribution;

public class DpDistribution {

	private static	double demands[];
	private static	double weights [];
	private static int disk;


	private static double[] volume_discretized;

	private double [] distribution;
	private double quadraticDifference;

double get_quadraticDiffence () { return quadraticDifference;}
double [] getDistribution () {return distribution;}

DpDistribution(double volume,double [] demands,double [] weights,int discretization)
		{
		
		
	if (volume<0) System.out.println("ERROR the volume is below 0 in knapsack algorithm");
			
			int timesteps = demands.length;
			
			int disk=discretization+1;
			

			double cal_matrice[][] = new double [timesteps][disk];
			int pos_matrice[][]=new int [timesteps][disk];
			
			double one_step_matrice [] = new double [disk];
			
			double volume_discretized [] = new double [disk];
			

		/*	System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");

			System.out.println("   FROM HERE IT STARTS the starting volume is "+ volume);
			*/
			this.disk=disk;
			
			
			int i,j,k;
			
			for (i=0;i<disk;i++)volume_discretized[i]=(volume/discretization)*i;
			
			
		//	for(i=0;i<disk;i++) System.out.println("  volume discretized "+volume_discretized[i]);
			
			
			
			
			
			this.volume_discretized = volume_discretized;
			this.demands=demands;
			this.weights=weights;

		/*	for(i=0;i<timesteps;i++) 
				{
				System.out.print("   demand "+demands[i]);
				System.out.println("  weight "+weights[i]);
				}
									
			*/
			
			
			
			
			
			
			
			one_step_matrice=function_One_step_mat(0);
			
		/*	for(i=0;i<disk;i++) System.out.println("  ee "+one_step_matrice[i]);
				
			System.out.println("   ");
			System.out.println("   ");
			System.out.println("   ");
		*/
			for (i=0;i<disk;i++)
				
			{
				cal_matrice[0][i]=one_step_matrice[i];
		//	System.out.println("   "+cal_matrice[0][i]);
				
			}
			
			
			
			double pom=0;
			for (i=1;i<timesteps;i++)
			{
				
				for (j=0;j<disk;j++)
				{
					one_step_matrice=function_One_step_mat(i);
					cal_matrice[i][j]=cal_matrice[i-1][j]+Math.pow((demands[i]), 2)* weights[i];
					pos_matrice[i][j]=j;
					
		//	System.out.println(" pocetna vresnost   "+cal_matrice[i][j]);
					
					for (k=0;k<j;k++)
					{
						pom=cal_matrice[i-1][k]+one_step_matrice[j-k];
						
						if (pom<cal_matrice[i][j]) 
							{
							cal_matrice[i][j]=pom;
							pos_matrice[i][j]=k;
							}
						


						
			//	System.out.println(" pomosno  "+pom);
						
						
					}
					
					
				}
				
			}
			
			
			
			
			
			
			
		/*	
			System.out.println("prin ");
			for (i=0;i<timesteps;i++)
			{
				System.out.println(" ");
				for (j=0;j<disk;j++)
				{
			
					System.out.print(" " +cal_matrice[i][j]);
				}
			}
			
			
		
		System.out.println(" pos");
			for (i=0;i<timesteps;i++)
			{
			System.out.println(" ");
				for (j=0;j<disk;j++)
				{
			
					System.out.print(" " +pos_matrice[i][j]);
				}
			}
			
		*/	
			double [] distribution_users = new double [timesteps];
			
			
		//	System.out.println(" This is the distribution between users ");
			int u;
			 k=disk-1;

			for (i=timesteps;i>0;i--)
			{
		//	System.out.println(" The K is "+k);
				u= pos_matrice[i-1][k];
				
				distribution_users[i-1]=volume_discretized[k]-volume_discretized[u];
				k=u;
				
			}
			
			this.distribution=distribution_users;
			this.quadraticDifference=cal_matrice[timesteps-1][disk-1];
			
		//for (i=0;i<timesteps;i++)			{			System.out.println(" The--- "+i+" gets "+distribution_users[i]);			}
			
			
		
			double sum=0;
			for (i=0;i<timesteps;i++)
			{
				sum+=weights[i]*Math.pow((demands[i]-distribution_users[i]),2);
			}
		//	System.out.println(" The quadratic deficit is  !! Control ! "+sum);
		
			
			
			
			
			
			
		}
		
		
		static double [] function_One_step_mat(int t)
		{
			int i;
			
			double [] error_mat = new double [disk];
			
			for (i=0;i<disk;i++)
				
			{
			if (volume_discretized[i]<demands[t])
			{
				error_mat[i]= Math.pow((volume_discretized[i]-demands[t]), 2)* weights[t];
			}
			else
			{
				error_mat[i]=0;
			}
			}
			return error_mat;
			
		}
	    
	    	
	    	


}

		
			

		