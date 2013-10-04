package Data;

import java.sql.SQLException;


public interface IData {
	
	/**  Storage 
	 * @throws Exception */
	public double[] getInflow_sim() throws Exception;
 
 	

 
 


	public double[] getMiddleInflow_sim() throws Exception;
 	

	public double[] getMiddleInflow_Hight_Knezevo_sim() throws Exception;

public double[] getMiddleInflow_LowZ_UpZ_sim() throws Exception;

	
	public double[] getStorageDiscretisation() throws Exception;
	

	
	
	public double[] getstorageTargetsFloods() throws Exception;
	public double[] getstorageTargetsFloodWeights() throws Exception;
	
	
	
	public double[] getstorageTargetsRecreation() throws Exception;
	public double[] getstorageTargetsRecreationWeights() throws Exception;

	public int getEpisodes(int i) throws SQLException;

	
	
	double[] getTargetsTowns_SS() throws Exception;
	double[] getTargetsTownsWeights_SS() throws Exception;
	
	double[] getTargetsTowns_PZ() throws Exception;
	double[] getTargetsTownsWeights_PZ() throws Exception;
	
	double[] getstorageTargetsEcology() throws Exception;
	double[] getstorageTargetsEcologyWeights() throws Exception;


	double[] getTargetAgriculture_UZ() throws Exception;
	double[] getTargetAgricultureWeights_UZ() throws Exception;
	
	double[] getTargetsAgriculture_LZ() throws Exception;
	double[] getTargetsAgricultureWeights_LZ() throws Exception;

	
	public double[] getstorageTargetsHydroPower()throws Exception;
	public double[] getstorageTargetsHydroPowerWeights()throws Exception;

	public double[] getInflow() throws Exception;

	
	public void  putStorageOptimal( double [] Optimal, int fD ) throws Exception;


	public double[] getMiddleInflow() throws Exception;
	
	
	public double[] getMiddleInflow_Hight_Knezevo() throws Exception;
	public double[] getMiddleInflow_LowZ_UpZ() throws Exception;





	
}
