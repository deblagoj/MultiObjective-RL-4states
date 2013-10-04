package Matrix;

public class Matrix3DDouble {
	
	private int rows;
	private int columms;
	private int deep; 
	private double[][][] mat;
	
	//public Matrix(int c,int r){this.columms=c;this.rows=r;}
	
	public Matrix3DDouble(double [][][]t){
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
		this.deep = t[0][0].length;
	}
	
	public void PrintMatrix3D()
	{
		int j,i,k;
	//	System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
		
			for (i=0;i<rows;i++)
			{
				for (j=0;j<columms;j++)	
				{
				
					for (k=0;k<deep;k++)
					{
						System.out.print("\t "+  mat[i][j][k]);
					}
					System.out.println(" ");
					
			}
			System.out.println("");
			
		}
	}
	
	
	
	public void PrintMatrix3Dtime()
	{
		int j,i,k;
	//	System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
		
			for (i=0;i<deep;i++)
			{
				for (j=0;j<columms;j++)	
				{
				
					for (k=0;k<rows;k++)
					{
						System.out.print("\t "+  mat[k][j][i]);
					}
					System.out.println(" ");
					
			}
			System.out.println("");
			
		}
	}
}
