package RLdefine;


import environment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random; 

import kMeans.Cluster;
import kMeans.KMeans;
import kMeans.KMeansResult;
import kMeans.Point;
import environment.IEnvironment;

import Data.AbstactData;
import Data.IData;
import Distribution.Distribute;
import HydroPower.hydroPower;



public class RLdefine  extends AbstractEnvironment{

	
    protected int timesteps; 		
    protected int discretizationLevels;
    protected double reservoirVolume;
   
    
    protected  State oldState=null; 

   
	 DecimalFormat df = new DecimalFormat("00.000");
    public double [][] frame; 
    
    
    protected double [] storagediscretization;
    
    int episodes;
    double [] inflowRaw;
    public double [] inflow;
	public int [] inflow_work_int;
    public int [] inflow_start_int;
    public double [] demand; // total demand from the reservoir (it time)
    
    protected Random generateur=new Random(); 

    private double [] storageTargetsFloods;
	private double [] storageTargetsRecreation;

	private double [] storageTargetsFloodWeights;
	private double [] storageTargetsRecreationWeights;

	private double [] Towns_PZ;
	private double [] Towns_SS;
	
	private double [] Agriculture_UZ;
	private double [] Agriculture_LZ;
	private double [] Ecology;
	
	private double [] TownsWeights_PZ;
	private double [] TownsWeights_SS;
	private double [] AgricultureWeights_UZ;
	private double [] AgricultureWeights_LZ;
	private double [] EcologyWeights;
	
	
	private double [] middleInflowRaw;
	private int[] middleInflowRaw_int;
	private double [] middleInflow;
	private int [] middle_inflow_work_int;

	private double[] middleInflow_Hight_Knezevo;
	private double[] middleInflow_LowZ_UpZ;
	
	private double [] HydroPower;
	private double [] HydroPowerWeights;
	private double[] rewardTotal;  
	private double[] weightsTotal;  
	private double [] reservoir_height;
	
	//private double weights [];
   
	 public RLdefine () {};
	 
	 
	// public void setWeights(double  [ ]w)
	// {
	//	 this.weights=w;
	// }

	boolean print=false;
	
	 public void print()
	 {
		 this.print=true;
	 }
	 
	 public int [] Infow_mid_int()
	 {
		 return middleInflowRaw_int;
	 }
	 
	 
	 	boolean setDistributionAlgorithmLP=true;
	 
	 	public void setDistributionAlgorithmLP(boolean a)
		{
	 		if (a==true)System.out.println("ODBRAN E SIMPLEX"); else System.out.println("ODBRAN E Knapsack");
	 			
			this.setDistributionAlgorithmLP=a;
	 	}
	 	
	 	
	 	public boolean getDistributionAlgorithmLP()
		{
	 		return setDistributionAlgorithmLP;
	 			

	 	}
	 	
	 	
	 	

	 	public int numberClustersMiddleInflow;
	 	
	 public int returnNumberClustersMiddleInflow()
	 {
		 return numberClustersMiddleInflow;
		 
	 }
	 
	 public int numberClustersInflow;
	 	
	 public int returnNumberClustersInflow()
	 {
		 return numberClustersInflow;
		 
	 }
	 	
	 	
	 	
	 
	 public int [] Infow_int()
	 {
		 return inflowRaw_int;
	 }
	 
	 public int return_number_episodes()
	 {
		 return episodes;
	 }
	 
	 public int return_lenght_inflow()
	 {
		 return inflowRaw.length;
		 
	 }
	 
	 //INICIALIZE THE INPUT TO THE ALGORITHM
	public void initialize() throws Exception
	{
		
		System.out.println("This inicialization will put values in storage and release Targets for each timestep");
		
		
		int i,a,j;	
		AbstactData test = new AbstactData();
		a=test.testDatabaseConnection();	
		IData test1 = new AbstactData();
		a=test.testDatabaseConnection();
		
		
		int k=3;
		double [] inflowRaw=test1.getInflow();
		this.timesteps=12;
		
		int episodes = 40;
		this.episodes=episodes;
		
		
		
		
		
		double[] storageTargetsFloodsN=new double [timesteps];
		double[] storageTargetsFloodsNWeights=new double [timesteps];

		
		double[] storageTargetsRecreationN=new double [timesteps];
		double[]  storageTargetsRecreationNWeights=new double [timesteps];

		
	
		double[]  TargetsTowns_PZ=new double [timesteps];
		double[]  TargetsTownsWeights_PZ=new double [timesteps];
		
		double[]  TargetsTowns_SS=new double [timesteps];
		double[]  TargetsTownsWeights_SS=new double [timesteps];
		
		double[] TargetsAgriculture_UZ=new double [timesteps];
		double[]  TargetsAgricultureWeights_UZ=new double [timesteps];
		
		double[] TargetsAgriculture_LZ=new double [timesteps];
		double[]  TargetsAgricultureWeights_LZ=new double [timesteps];
		
		double[] storageTargetsEcology=new double [timesteps];
		double[]  storageTargetsEcologyWeights=new double [timesteps];
		
		double[] storageTargetsHydroPower=new double [timesteps];
		double[]  storageTargetsHydroPowerWeights=new double [timesteps];
		
		double[] middleInflowRaw=new double [inflowRaw.length];
		
		double[] middleInflow_Hight_Knezevo=new double [inflowRaw.length];;
		
		double[] middleInflow_LowZ_UpZ=new double [inflowRaw.length];;
		
		
		
	
		
		storageTargetsFloodsN=test1.getstorageTargetsFloods();
		storageTargetsRecreationN=test1.getstorageTargetsRecreation();

		storageTargetsFloodsNWeights=test1.getstorageTargetsFloodWeights();
		storageTargetsRecreationNWeights=test1.getstorageTargetsRecreationWeights();
		
		
		TargetsTowns_SS=test1.getTargetsTowns_SS();
		TargetsTownsWeights_SS=test1.getTargetsTownsWeights_SS();
		
		TargetsTowns_PZ=test1.getTargetsTowns_PZ();
		TargetsTownsWeights_PZ=test1.getTargetsTownsWeights_PZ();
		

		TargetsAgriculture_UZ=test1.getTargetAgriculture_UZ();
		TargetsAgricultureWeights_UZ=test1.getTargetAgricultureWeights_UZ();
		
		TargetsAgriculture_LZ=test1.getTargetsAgriculture_LZ();
		TargetsAgricultureWeights_LZ=test1.getTargetsAgricultureWeights_LZ();
		
		
		storageTargetsEcology=test1.getstorageTargetsEcology();
		storageTargetsEcologyWeights=test1.getstorageTargetsEcologyWeights();
	
		
		storageTargetsHydroPower=test1.getstorageTargetsHydroPower();
		storageTargetsHydroPowerWeights=test1.getstorageTargetsHydroPowerWeights();
		
		middleInflowRaw=test1.getMiddleInflow();
		middleInflow_Hight_Knezevo=test1.getMiddleInflow();
		middleInflow_LowZ_UpZ=test1.getMiddleInflow();
		
double [] storagediscretization;
storagediscretization=test1.getStorageDiscretisation();
		

this.timesteps=TargetsTowns_SS.length;


for (i=0;i<storagediscretization.length;i++)
	System.out.println("Rows ------" + storagediscretization[i]);
	System.out.println("");


	
	this.storagediscretization=storagediscretization;	
	this.middleInflowRaw=middleInflowRaw;
	this.discretizationLevels=storagediscretization.length; 
	this.inflowRaw=inflowRaw;

	
	
	
	//!!!!!!! this creates the k-means clastering on middle inflow data !!!
	segmentation(middleInflowRaw); 

	segment_middle_inflow();

	
					segmentation_inflow(inflowRaw);
					segment_inflow();
					
					
			
					

					
					
		int [] inflow_start_int=new int [timesteps];
		for (i=0;i<inflowRaw.length;i++)
			System.out.println("Inflow ------" + inflowRaw[i]+  "     nb  "+ i);
	
		double [] inflow_start=new double [timesteps];
		for(i=0;i<timesteps;i++)
		 {
			inflow_start[i]= inflowRaw[i];
			inflow_start_int[i]=inflowRaw_int[i];
			System.out.print("  --1--  "+inflow_start[i] +"    "+inflow_start_int[i]);
		 }
		
		int [] middle_inflow_start=new int [timesteps];
		for(i=0;i<timesteps;i++)
		 {
			middle_inflow_start[i]= middleInflowRaw_int[i];
			System.out.print("  --start--  "+middle_inflow_start[i]);
		 }
		
		
		
	
	
	
		
	
		
		boolean print=true;
if (print){
			
			System.out.println(" ");
			System.out.println("storageTargetsFloods -- storageTargetsFloodsNWeights []");
			for (i=0;i<timesteps;i++) System.out.println(storageTargetsFloodsN[i] + "                     "+storageTargetsFloodsNWeights[i] );
			System.out.println(" ");
			
			
			
			System.out.println("storageTargetsRecreationN  [] ------storageTargetsRecreationNWeight");
			for (i=0;i<timesteps;i++) System.out.println(storageTargetsRecreationN[i]+ "  "+  storageTargetsRecreationNWeights[i]);
			System.out.println(" ");
			
			System.out.println("Towns PZ [] ------ TownsWeights []");
			for (i=0;i<timesteps;i++)System.out.println(TargetsTowns_PZ[i]+ "  "+ TargetsTownsWeights_PZ[i]);
			System.out.println(" ");
			
			System.out.println("Towns SS [] ------ TownsWeights []");
			for (i=0;i<timesteps;i++)System.out.println(TargetsTowns_SS[i]+ "  "+ TargetsTownsWeights_SS[i]);
			System.out.println(" ");
		
			System.out.println("Agriculture UZ [] ------ AgricultureNWeights");
			for (i=0;i<timesteps;i++) System.out.println(TargetsAgriculture_UZ[i]+ "  "+ TargetsAgricultureWeights_UZ[i]);
			System.out.println(" ");
			
			System.out.println("Agriculture LZ [] ------ AgricultureNWeights");
			for (i=0;i<timesteps;i++) System.out.println(TargetsAgriculture_LZ[i]+ "  "+ TargetsAgricultureWeights_LZ[i]);
			System.out.println(" ");
			
			System.out.println("Ecology [] ------EcologyNWeights []");
			for (i=0;i<timesteps;i++)System.out.println(storageTargetsEcology[i]+ "  "+ storageTargetsEcologyWeights[i]);
			System.out.println(" ");
			
			
			
			System.out.println("Hydro power [] ------hydro power Weights []");
			for (i=0;i<timesteps;i++)System.out.println(storageTargetsHydroPower[i]+ "  "+ storageTargetsHydroPowerWeights[i]);
			System.out.println(" ");
			
			System.out.println(" ");
			System.out.println("Middle Inflow");
			for (i=0;i<inflowRaw.length;i++) System.out.println(middleInflowRaw[i]);
			System.out.println(" ");
			
			
			
			System.out.println(" ");
			System.out.println("MiddleInflow_Hight_Knezevo");
			for (i=0;i<inflowRaw.length;i++) System.out.println(middleInflow_Hight_Knezevo[i]);
			System.out.println(" ");
			
			
			
			System.out.println(" ");
			System.out.println("middleInflow_LowZ_UpZ");
			for (i=0;i<inflowRaw.length;i++) System.out.println(middleInflow_LowZ_UpZ[i]);
			System.out.println(" ");
		
		}
		
	

this.inflow=inflow_start;
this.middle_inflow_work_int=middle_inflow_start;
this.inflow_start_int=inflow_start_int;
this.middleInflow_Hight_Knezevo=middleInflow_Hight_Knezevo;
this.middleInflow_LowZ_UpZ=middleInflow_LowZ_UpZ;

this.storageTargetsFloods=storageTargetsFloodsN;
this.storageTargetsRecreation=storageTargetsRecreationN;

	

this.storageTargetsFloodWeights=storageTargetsFloodsNWeights;
this.storageTargetsRecreationWeights=storageTargetsRecreationNWeights;



this.Towns_SS=TargetsTowns_SS;
this.TownsWeights_SS=TargetsTownsWeights_SS;

this.Towns_PZ=TargetsTowns_PZ;
this.TownsWeights_PZ=TargetsTownsWeights_PZ;

this.Agriculture_UZ=TargetsAgriculture_UZ;
this.AgricultureWeights_UZ=TargetsAgricultureWeights_UZ;

this.Agriculture_LZ=TargetsAgriculture_LZ;
this.AgricultureWeights_LZ=TargetsAgricultureWeights_LZ;

this.Ecology=storageTargetsEcology;
this.EcologyWeights=storageTargetsEcologyWeights;

this.HydroPower=storageTargetsHydroPower;
this.HydroPowerWeights=storageTargetsHydroPowerWeights;





double [] reservoir_height= new double [discretizationLevels];
reservoir_height=reserovirVolumeToHeight(storagediscretization);

this.reservoir_height=reservoir_height;






    	
    	frame=new double[timesteps][discretizationLevels]; 
    	
    
    	 
    	 for (i=0;i<timesteps;i++)
       		 for(j=0;j<discretizationLevels;j++)
       			 
       		 frame[i][j]=storagediscretization[discretizationLevels-j-1]; 
    		 frame.toString();
    		 
    		 
    		 reservoirVolume=frame[0][0];

			
    		 double [] reservoir_area = new double [timesteps];
 			
 			reservoir_area=reservoirVolumeToArea( storagediscretization);
 		 this. reservoir_area=reservoir_area;
 		 
	}
 
	
	
	
	
	double [] weights_new;
	public void change_weights( double [] weights)
	{
		
		
	
	
			for (int j=0;j<timesteps;j++)
			{
				
				
				storageTargetsRecreationWeights[j]=weights[0];
				storageTargetsFloodWeights[j]=weights[1];
				TownsWeights_PZ[j]=weights[2];
				AgricultureWeights_UZ[j]=weights[3];
				TownsWeights_SS[j]=weights[4];
				AgricultureWeights_LZ [j]=weights[5];
				EcologyWeights[j]=weights[6];
				HydroPowerWeights[j]=weights[7];
			}
			System.out.println("the new weigts are:");
			System.out.println("storage recreation" +storageTargetsRecreationWeights[0]+" town PZ" +TownsWeights_PZ[3]);
		
			
			
			
			int i;
			System.out.println(" NEWWWWWW");
			System.out.println(" storageTargetsFloodsNWeights []");
			for (i=0;i<timesteps;i++) System.out.println(storageTargetsRecreationWeights[i] );
			System.out.println(" ");
			
			
			
			System.out.println("storageTargetsRecreationNWeight");
			for (i=0;i<timesteps;i++) System.out.println(storageTargetsFloodWeights[i]);
			System.out.println(" ");
			
			System.out.println("TownsWeights PZ []");
			for (i=0;i<timesteps;i++)System.out.println(TownsWeights_PZ[i]);
			System.out.println(" ");
			
			
			System.out.println(" AgricultureNWeights");
			for (i=0;i<timesteps;i++) System.out.println(AgricultureWeights_UZ[i]);
			System.out.println(" ");
			
			
			System.out.println("TownsWeights []");
			for (i=0;i<timesteps;i++)System.out.println(TownsWeights_SS[i]);
			System.out.println(" ");
		
			
			
			System.out.println(" AgricultureNWeights");
			for (i=0;i<timesteps;i++) System.out.println(AgricultureWeights_LZ[i]);
			System.out.println(" ");
			
			System.out.println("EcologyNWeights []");
			for (i=0;i<timesteps;i++)System.out.println(EcologyWeights[i]);
			System.out.println(" ");
			
			
			
			System.out.println("hydro power Weights []");
			for (i=0;i<timesteps;i++)System.out.println(HydroPowerWeights[i]);
			System.out.println(" ");
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	}
	
	
	
	
	
	
	
			private double[] reservoir_area;
	
	double [] reserovirVolumeToHeight(double [] storage)
	{
		
		
		
		double [] reservoir_height1= new double [discretizationLevels];
		
		for (int i=0;i<discretizationLevels;i++)
		{
			if ((storage[i]>=0)&& (storage[i]<0.2581015)){ reservoir_height1[i]=38.744*storage[i];}
			else if ((storage[i]>=0.2581015)&& (storage[i]<1.00194226)) {reservoir_height1[i]=10.755*storage[i]+7.2241;}
				else if ((storage[i]>=1.00194226)&& (storage[i]<3.20902792)) {reservoir_height1[i]=5.437*storage[i]+12.552;}
					else if ((storage[i]>=3.20902792)&& (storage[i]<6.10104382)) {reservoir_height1[i]=3.457*storage[i]+18.904;}
						else if ((storage[i]>=6.10104382)&& (storage[i]<10.11895657)) {reservoir_height1[i]=2.4889*storage[i]+24.815;}	
					else if ((storage[i]>=10.11895657)&& (storage[i]<15.36823722)) {reservoir_height1[i]=1.905*storage[i]+30.723;}	
				else if ((storage[i]>=15.36823722)&& (storage[i]<22.01049192)) {reservoir_height1[i]=1.505*storage[i]+36.863;}	
			else if ((storage[i]>=22.01049192)&& (storage[i]<30.16427517)) {reservoir_height1[i]=1.2264*storage[i]+43.006;}
			
			else {reservoir_height1[i]=1.2264*storage[i]+43.006;};



			
		}
		for(int i=0;i<discretizationLevels;i++)
		{
			System.out.println("the reservoir height by function " + reservoir_height1[i] );
		}
		
		return reservoir_height1;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void segment_middle_inflow()
	{
		int [] middleInflowRaw_int = new int [middleInflowRaw.length];
		int i;
		for(i=0;i<middleInflowRaw.length;i++)
		{
			middleInflowRaw_int[i]=belongToInterval(middleInflowRaw[i]);
			System.out.println("middle inflwo ------" + middleInflowRaw[i]+ "discretization level "+ middleInflowRaw_int[i]);
		}
		this.middleInflowRaw_int=middleInflowRaw_int;
	}
	
	

	public int sections_middle_inflow;
	public double [] intervals_middle_inflow;
	public double [] clasterValues_middle_inflow;
	
	
	public void segmentation (double [] middleInflowRaw) throws IOException
	{
		List<double[]> a;
		int golemina=middleInflowRaw.length;
			
		int k_steps=(int) Math.sqrt(golemina/2);
		
		double [] niza = middleInflowRaw;
		a=Arrays.asList(middleInflowRaw);
		
		List<Point> points = new ArrayList<Point>();

		for(int i=0; i<niza.length; i++)
		{
			String m=String.valueOf(niza[i]);
		    Point p = new Point(m);
		    points.add(p);
		}
		

		KMeans kmeans = new KMeans();
		for (int k = 1; k <= k_steps; k++) 
		{
		    KMeansResult result = kmeans.calculate(points, k);
		    	System.out.println("------- Con k=" + k + " ofv=" + result.getOfv() + "-------\n");
		    	int i = 0;
		    	for (Cluster cluster : result.getClusters()) 
		    	{
		    		i++;
		    		System.out.println("-- Cluster " + i + " --\n");
		    			for (Point punto : cluster.getPoints()) 
		    				{
		    				System.out.println(punto.getX() + ", " + "\n");
		    				}
		    	System.out.println("\n");
		    	if(print) System.out.println(cluster.getCentroide().getX() + ", ");
		    	if(print) System.out.println("\n\n");
		    	}
	
		}
		
		int k1=4;
		
	/*	InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Insert number Middle inflos of clasters");
        System.out.println("----------------------------------------------------------------");
	String id;
   id= in.readLine();
*/   

	
//try {k1=Integer.parseInt(id);} catch (NumberFormatException e) {      System.exit(0);   }
		
System.out.println("THE NUMBER OF MIDDLE INFLOW CLUSTERS IS " + k1);
this.numberClustersMiddleInflow=k1;

KMeansResult result = kmeans.calculate(points, k1);
double [] clastersInterval=new double [k1];

if(print) System.out.println("------- Con k=" + k1 + " ofv=" + result.getOfv() + "-------\n");
	int i = 0;
	for (Cluster cluster : result.getClusters()) 
	{
	i++;
	if(print) System.out.println("-- Cluster " + i + " --\n");
		for (Point punto : cluster.getPoints()) 
			{
			if(print) System.out.println(punto.getX() + ", " + "\n");
			}
	if(print) System.out.println("\n");
	if(print) System.out.println(cluster.getCentroide().getX() + ", ");
	clastersInterval[i-1]=cluster.getCentroide().getX();
	
	if(print) System.out.println("\n\n");
	}

	
	Arrays.sort(clastersInterval);
	
	
	for (i=0;i<k1;i++)
	{ System.out.println("\n this are clusters");
		System.out.print(clastersInterval[i] + ", ");
	}
	
	
	
	double [] intervals1 = new double [k1+1];
	
	intervals1[0]=0;
	
	System.out.print(" intervals  " +intervals1[0]);
	
	for (i=0;i<clastersInterval.length-1;i++)
	{
		intervals1[i+1]=clastersInterval[i]+(clastersInterval[i+1]-clastersInterval[i])/2 ;           // regular intervals -- can be with different interval not only regular
	
	}
	
	
	intervals1[k1]=clastersInterval[k1-1]*10;  // the begining in 0 the end is twice the last one !!!
	
	for (i=0;i<intervals1.length;i++)
	{
		
		System.out.print(" intervalsssssss  " +intervals1[i]);
	}
	
	
	
	this.intervals_middle_inflow=intervals1;
	this.sections_middle_inflow=k1;
	this.clasterValues_middle_inflow=clastersInterval;
	
	
	
	}
	
	
	
	public int  belongToInterval(double value)
	{
	
	int i=0;
	
	for (i=0;i<intervals_middle_inflow.length-1;i++)
	{
		if ((value>=intervals_middle_inflow[i])&& (value<intervals_middle_inflow[i+1]))
		{
			return i;
		
		}
	}
	System.out.println("this line should not print !!!!! " +value );
	return i;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int[] inflowRaw_int;
	
	
	public void segment_inflow()
	{
		int [] inflowRaw_int = new int [inflowRaw.length];
		int i;
		for(i=0;i<inflowRaw.length;i++)
		{
			inflowRaw_int[i]=belongToInterval(inflowRaw[i]);
			System.out.println("inflwo ------" + inflowRaw[i]+ "discretization level "+ inflowRaw_int[i]);
		}
		this.inflowRaw_int=inflowRaw_int;
	}
	
	

	public int sections_inflow;
	public double [] intervals_inflow;
	public double [] clasterValues_inflow;
	
	
	public void segmentation_inflow (double [] middleInflowRaw) throws IOException
	{
		List<double[]> a;
		int golemina=middleInflowRaw.length;
			
		int k_steps=(int) Math.sqrt(golemina/2);
		
		double [] niza = middleInflowRaw;
		a=Arrays.asList(middleInflowRaw);
		
		List<Point> points = new ArrayList<Point>();

		for(int i=0; i<niza.length; i++)
		{
			String m=String.valueOf(niza[i]);
		    Point p = new Point(m);
		    points.add(p);
		}
		

		KMeans kmeans = new KMeans();
		for (int k = 1; k <= k_steps; k++) 
		{
		    KMeansResult result = kmeans.calculate(points, k);
		    	System.out.println("------- Con k=" + k + " ofv=" + result.getOfv() + "-------\n");
		    	int i = 0;
		    	for (Cluster cluster : result.getClusters()) 
		    	{
		    		i++;
		    		System.out.println("-- Cluster " + i + " --\n");
		    			for (Point punto : cluster.getPoints()) 
		    				{
		    				System.out.println(punto.getX() + ", " + "\n");
		    				}
		    	System.out.println("\n");
		    	if(print) System.out.println(cluster.getCentroide().getX() + ", ");
		    	if(print) System.out.println("\n\n");
		    	}
	
		}
		
		int k1=4;
		
	/*	InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Insert number of clasters for INFLOW");
        System.out.println("----------------------------------------------------------------");
	String id;
   id= in.readLine();
   
   
try {k1=Integer.parseInt(id);} catch (NumberFormatException e) {      System.exit(0);   }

*/
this.numberClustersInflow=k1;	
System.out.println("THE NUMBER OF INFLOW CLUSTERS IS " + k1);
KMeansResult result = kmeans.calculate(points, k1);
double [] clastersInterval=new double [k1];

if(print) System.out.println("------- Con k=" + k1 + " ofv=" + result.getOfv() + "-------\n");
	int i = 0;
	for (Cluster cluster : result.getClusters()) 
	{
	i++;
	if(print) System.out.println("-- Cluster " + i + " --\n");
		for (Point punto : cluster.getPoints()) 
			{
			if(print) System.out.println(punto.getX() + ", " + "\n");
			}
	if(print) System.out.println("\n");
	if(print) System.out.println(cluster.getCentroide().getX() + ", ");
	clastersInterval[i-1]=cluster.getCentroide().getX();
	
	if(print) System.out.println("\n\n");
	}

	
	Arrays.sort(clastersInterval);
	
	
	for (i=0;i<k1;i++)
	{ System.out.println("\n this are clusters");
		System.out.print(clastersInterval[i] + ", ");
	}
	
	
	
	double [] intervals1 = new double [k1+1];
	
	intervals1[0]=0;
	
	System.out.print(" intervals  " +intervals1[0]);
	
	for (i=0;i<clastersInterval.length-1;i++)
	{
		intervals1[i+1]=clastersInterval[i]+(clastersInterval[i+1]-clastersInterval[i])/2 ;           // regular intervals -- can be with different interval not only regular
	
	}
	
	
	intervals1[k1]=clastersInterval[k1-1]*10;  // the begining in 0 the end is twice the last one !!!
	
	for (i=0;i<intervals1.length;i++)
	{
		
		System.out.print(" intervalsssssss  " +intervals1[i]);
	}
	
	
	
	this.intervals_inflow=intervals1;
	this.sections_inflow=k1;
	this.clasterValues_inflow=clastersInterval;
	
	
	
	}
	
	
	
	public int  belongToInterval_inflow(double value)
	{
	
	int i=0;
	
	for (i=0;i<intervals_inflow.length-1;i++)
	{
		if ((value>=intervals_inflow[i])&& (value<intervals_inflow[i+1]))
		{
			return i;
		
		}
	}
	System.out.println("this line should not print !!!!! " +value );
	return i;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private double [] middleInflow_HK ;
	private  double [] middleInflow_LK;
	
	
	
	

	private double [] Towns_PZ_Reservoir;
	private double [] Towns_SS_Reservoir;
	private double [] Agriculture_UZ_Reservoir;
	private double [] Agriculture_LZ_Reservoir;
	private double [] Ecology_Reservoir;

	
	// in which episode we are in !!
	
	

	
	public int episode;
	
	 public void setEpisode(int a)
	 {
		 this.episode=a;
		 
		 if (a>=episodes) {System.out.println("THAT INFLOW EPISODE DONT EXIST !!!!!");return;}
		// double [] new_inflow_episode=new double[timesteps];
		
		 System.out.print(" episode "+a + "  ");
		 int [] inflow_work_int= new int [timesteps];
		 double [] middleInflow= new double[timesteps]; 
		 double [] middleInflow_HK =new double [timesteps];
		 double [] middleInflow_LK=new double [timesteps];
		 for(int i=0;i<timesteps;i++)
		 {
			this.inflow[i]= inflowRaw[(a*timesteps)+i];
			
			middle_inflow_work_int[i]=middleInflowRaw_int[(a*timesteps)+i];
			
			inflow_work_int[i]=inflowRaw_int[(a*timesteps)+i];
			middleInflow[i]=middleInflowRaw[(a*timesteps)+i];
			if(print) System.out.print("  _inflow  "+inflow[i]);
		
			if(print) System.out.println("  _middle inflow  "+middle_inflow_work_int[i]);
			middleInflow_HK[i]=middleInflow_Hight_Knezevo[(a*timesteps)+i];
			middleInflow_LK[i]=middleInflow_LowZ_UpZ[(a*timesteps)+i];
			
			if(print) System.out.println("  _middle inflow HK  "+middleInflow_HK[i]);
			if(print) System.out.println("  _middle inflow  LK "+middleInflow_LK[i]);
		 }
		 this.inflow_work_int=inflow_work_int;
		 this.middleInflow_HK=middleInflow_HK;
		 this.middleInflow_LK=middleInflow_LK;
		 // !!!!!!!!here it is calculated how much water is supplyed from the middle part !
		 
		 
		 
		 
		 
		 
		 	double[] TargetsTowns_PZ_Reservoir=new double [timesteps];
			double[] TargetsTowns_SS_Reservoir=new double [timesteps];
			double[] TargetsAgriculture_UZ_Reservoir=new double [timesteps];
			double[] TargetsAgriculture_LZ_Reservoir=new double [timesteps];
			double[] TargetsEcology_Reservoir=new double [timesteps];
			
			int i,j;
			double  [] distributed = new double [5];//the deficit of water needed from the reservoir
			double  [] deficit = new double [5];
			
			
			if(print) System.out.println("Middle Hight_LowZ_UpZ");
			
			
			for(i=0;i<timesteps;i++)
			{
			
			double []  weights= { TownsWeights_PZ[i],TownsWeights_SS[i], AgricultureWeights_UZ[i],AgricultureWeights_LZ[i],EcologyWeights[i] };
			double[] demands = { Towns_PZ[i],Towns_SS[i],Agriculture_UZ[i],Agriculture_LZ[i],Ecology[i]};
			
				if (middleInflow[i]>=(Towns_PZ[i]+Towns_SS[i]+Agriculture_UZ[i]+Agriculture_LZ[i]+Ecology[i]))
				{
				distributed[0]=Towns_PZ[i];distributed[1]=Towns_SS[i];distributed[2]=Agriculture_UZ[i];
				distributed[3]=Agriculture_LZ[i];distributed[4]=Ecology[i];
					
				}
			
					else
					{
				
			
			
					/*
					 * The two comment bellow are changing between simplex and quadratic Knapsack optimization		
					 */
			
			


			
					Distribute distribution = new Distribute() ;
			
			if (setDistributionAlgorithmLP){
				//System.out.println("LP");
				distribution.setInput(demands, weights,middleInflow[i] ,0);
				distributed=distribution.calculateSimplex();
			}else
			{
				//System.out.println("DP2");
				distribution.setInput(demands, weights,middleInflow[i] ,50);
				distributed=distribution.calculateDPQuadraticDistribution();
			}
					
			
			//for (i=0;i<3;i++)System.out.println(" rezultat solution"+i+ "   "+ solution[i]);
				
				
			}
				
				
				for (j=0;j<5;j++)
				{
					if (distributed[j]<demands[j]) deficit[j]=demands[j]-distributed[j]; else deficit[j]=0; 
				}
				TargetsTowns_PZ_Reservoir[i]=deficit[0];
				TargetsTowns_SS_Reservoir[i]=deficit[1];
				TargetsAgriculture_UZ_Reservoir[i]=deficit[2];
				TargetsAgriculture_LZ_Reservoir[i]=deficit[3];
				TargetsEcology_Reservoir[i]=deficit[4];
				
			}
			
			

			this.Towns_PZ_Reservoir=TargetsTowns_PZ_Reservoir;
			this.Towns_SS_Reservoir=TargetsTowns_SS_Reservoir;
			this.Agriculture_UZ_Reservoir=TargetsAgriculture_UZ_Reservoir;
			this.Agriculture_LZ_Reservoir=TargetsAgriculture_LZ_Reservoir;
			this.Ecology_Reservoir=TargetsEcology_Reservoir;
			

			
	if(print) System.out.println("time    MiddleInflow  DemandPZ  DemandSS   DemandUZ   DemandLZ  DemandEc  DeficitPZ    DeficitSS   DeficitUZ   DeficitLZ  DeficitEc  WeightPZ  WeightSS   WeightUZ" +
			"WeightLZ    WeightEc ");

			for(i=0;i<timesteps;i++)
			{ 
	if(print) System.out.println( i+"      "+ df.format(middleInflow[i])+ "        "+ df.format(Towns_PZ[i])+"      "+df.format(Towns_SS[i])+"      "+df.format(Agriculture_UZ[i])
			+"    "+df.format(Agriculture_LZ[i])+"       "+df.format(Ecology[i])+"    "+df.format(TargetsTowns_PZ_Reservoir[i])+
			"    "+df.format(TargetsTowns_SS_Reservoir[i])+"     "+df.format(TargetsAgriculture_UZ_Reservoir[i])+"     "+df.format(TargetsAgriculture_LZ_Reservoir[i])+
			"     "+df.format(TargetsEcology_Reservoir[i])+
			"     "+df.format(TownsWeights_PZ[i])+"     "+df.format(TownsWeights_SS[i])+"     "+df.format(AgricultureWeights_UZ[i])
			+"     "+df.format(AgricultureWeights_LZ[i])+"     "+df.format(EcologyWeights[i]));
			
			}

		 
		 
		 
		 
		 
		 
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

	 
	 
	 public void setEpisode_inflow_middle_inflow_simulation( int episodes) throws Exception
	 {
		 int i,a;	
			AbstactData test1 = new AbstactData();
			a=test1.testDatabaseConnection();	
		 
		 
			double [] inflow_sim=test1.getInflow_sim();
			double [] inflow_mid_sim=test1.getMiddleInflow_sim();
			double [] inflow_mid_high=test1.getMiddleInflow_Hight_Knezevo_sim();
			double [] inflow_mid_low=test1.getMiddleInflow_LowZ_UpZ_sim();
		 
			System.out.print(" episode inflow simulation ");
			 for(i=0;i<inflow_sim.length;i++)
			 {
				this.inflowRaw[i]= inflow_sim[i];
				System.out.println("    "+this.inflowRaw[i]);
			 }
			 System.out.println();
		 
			 System.out.print(" episode middle simulation ");
			 for(i=0;i<inflow_sim.length;i++)
			 {
				this.middleInflowRaw[i]= inflow_mid_sim[i];
				System.out.println("    "+this.middleInflowRaw[i]);
			 }
			 System.out.println();
			 
			 
			 for(i=0;i<inflow_sim.length;i++)
			 {
				this.middleInflow_Hight_Knezevo[i]=inflow_mid_high[i];
				this.middleInflow_LowZ_UpZ[i]=inflow_mid_low[i];
				
				System.out.println("   hight  "+this.middleInflow_Hight_Knezevo[i]+"      low  "+middleInflow_LowZ_UpZ[i]);
			 }
			 System.out.println();
		 this.episodes=episodes;
		 
		 segment_middle_inflow();
		 segment_inflow();
	 }
	 

    
	 
	 
	 
	 
	 
	 public void setEpisode_inflow_middle_inflow_normal( int episodes) throws Exception
	 {
		 int i,a;	
			AbstactData test1 = new AbstactData();
			a=test1.testDatabaseConnection();	
		 
		 
			double [] inflow=test1.getInflow();
			double [] inflow_mid=test1.getMiddleInflow();
			double [] inflow_mid_high=test1.getMiddleInflow_Hight_Knezevo();
			double [] inflow_mid_low=test1.getMiddleInflow_LowZ_UpZ();
		 
			System.out.print(" episode inflow simulation ");
			 for(i=0;i<inflow.length;i++)
			 {
				this.inflowRaw[i]= inflow[i];
				System.out.println("    "+this.inflowRaw[i]);
			 }
			 System.out.println();
		 
			 System.out.print(" episode middle simulation ");
			 for(i=0;i<inflow.length;i++)
			 {
				this.middleInflowRaw[i]= inflow_mid[i];
				System.out.println("    "+this.middleInflowRaw[i]);
			 }
			 System.out.println();
			 
			 
			 for(i=0;i<inflow.length;i++)
			 {
				this.middleInflow_Hight_Knezevo[i]=inflow_mid_high[i];
				this.middleInflow_LowZ_UpZ[i]=inflow_mid_low[i];
				
				System.out.println("   hight  "+this.middleInflow_Hight_Knezevo[i]+"      low  "+middleInflow_LowZ_UpZ[i]);
			 }
			 System.out.println();
		 this.episodes=episodes;
		 
		 segment_middle_inflow();
		 segment_inflow();
	 }
	 
	 
	 
	 
	 
	 
    
    // this should be inicialized over the starting  programm
    
    protected State defaultCurrentState=new State(0,0,0,0,this);

    
    
    
    
    
    
    public IState defaultInitialState(){return defaultCurrentState;}
   
    public IState defineState(int i,int j,int k,int l){ return new State(i,j,k,l,this);} //we can check if i and i are in the limits of widey and longx
    

    public void InitialState(int timestep, int storage, int middle_inflow, int inflow){
    	 				//initial state which should be
    	oldState=defaultCurrentState; 
    	defaultCurrentState=new State(timestep,storage,middle_inflow,inflow, this); 
        }
  
    
    
    
     
   
    public IState successorState(IState s,IAction a){
    	State el=(State) s;
    	Action al=(Action)a;
    	oldState=el; 
    	if (el.timestep==timesteps-1) return new State(0, el.getState()+al.getAction(),middle_inflow_work_int[el.timestep],inflow_work_int[el.timestep],this); // this should not happen to have successor state afte end of time timestep
    	
    	//if (el.timestep==timesteps) return new State(0, 0,this);
    	return new State((el.getTimestep()+1), el.getState()+al.getAction(),middle_inflow_work_int[el.timestep+1],inflow_work_int[el.timestep+1],this);			//check this out .this???
        }
 
    
    public ActionList getActionList(IState s){
    	State el=(State) s;
   	
    	ActionList l=new ActionList(el);  
;  
	   
     	 
	    
    	int newY;
    	int newX = el.getTimestep();// timestep
    	/* while checks if the next state is possible it calculates the difference between the
    	 * current state - inflows - next state and it stops when the state can't be reached 
    */
    	
    	if (newX==timesteps-1) { newX=0;}  // there is not a action list on the last final state
   	 else newX=el.getTimestep()+1;   
    	
	     	//&& (el.getState()+1<timesteps))
	   for (int i=0;i<discretizationLevels;i++)   //HERE ALSO WHILE CYCLUS COULD BE GOOD
	    {
		   newY=-el.state+i;	   
		   	Action a=new Action(newY);
	    	
		   	
		  
	    	// System.out.println("OVA E BITNOO    momentalniot timestep "+ el.getTimestep()+ "noviot xx"+newX);
		   	if((frame[el.getTimestep()][el.getState()]+inflow[el.getTimestep()]-frame[newX][el.getState()+newY]
	-alpha*evapotranspiration[el.getTimestep()%12]* (reservoir_area[el.getState()]+reservoir_area[el.getState()+newY])/2)>=0)
	 l.add(a);   
	 
	 
	 }
// System.out.print("OVA E ACTION LIST "+l.toString());
	   
	   if (l.size()==0) {Action a=new Action(0);l.add(a);};
	    return l;
    	
    	
    	
        }


  
    
    
    
    
    
    
    
    
    double [] evapotranspiration = {0.0015,	0.006,	0.0228,	0.049,	0.088,	0.117,	0.134,	0.124,	0.083,	0.048,	0.017,	0.0047};
	double alpha=0.1;
	
    
    
    public double [] reservoirVolumeToArea(double [] storage)
	{
		
		
		
		
		
double [] reservoir_area1= new double [storagediscretization.length];
		
		for (int i=0;i<storagediscretization.length;i++)
		{
			if ((storage[i]>=0)&& (storage[i]<0.2581015)){ reservoir_area1[i]=0.2*storage[i];} 
			else if ((storage[i]>=0.2581015)&& (storage[i]<1.00194226)) {reservoir_area1[i]=0.1112*storage[i]+0.0229;}
				else if ((storage[i]>=1.00194226)&& (storage[i]<3.20902792)) {reservoir_area1[i]=0.449*storage[i]+0.0893;}
					else if ((storage[i]>=3.20902792)&& (storage[i]<6.10104382)) {reservoir_area1[i]=0.0385*storage[i]+0.1099;}
						else if ((storage[i]>=6.10104382)&& (storage[i]<10.11895657)) {reservoir_area1[i]=0.0283*storage[i]+0.1721;}	
					else if ((storage[i]>=10.11895657)&& (storage[i]<15.36823722)) {reservoir_area1[i]=0.0252*storage[i]+0.2033;}	
				else if ((storage[i]>=15.36823722)&& (storage[i]<22.01049192)) {reservoir_area1[i]=0.022*storage[i]+0.2531;}	
			else if ((storage[i]>=22.01049192)&& (storage[i]<30.16427517)) {reservoir_area1[i]=0.0192*storage[i]+0.3157;}

			
			else {reservoir_area1[i]=0.0192*storage[i]+0.3157;}

		}
		
		
			for(int i=0;i<storagediscretization.length;i++)
			{
				System.out.println("the reservoir area by function " + reservoir_area1[i] );
			}
		
		
		return reservoir_area1;
	}
	
    
    
    
    
    
    
    
    
    
    
    
    
    
 public double getReward(IState s1,IState s2,IAction a){
    	State x1=(State)s1; 
    	State x2=(State)s2; 
 
    	int i=x1.getTimestep();
    	int j=x2.getTimestep();
    	int disk1=x1.getState();
    	int disk2=x2.getState();
    	
    	//if (j!=(i+1)) System.out.println("GRESKAAAA111");
    	

    	double	recreationQ=0;
		double floodQ=0;
		double townsQ_SS=0;
		double townsQ_PZ=0;
		double agricultureQ_UZ=0;
		double agricultureQ_LZ=0;
		double ecologyQ=0;
		double hydroPowerQ=0;
		
		
	
	
		double allocated=frame[i][disk1]+inflow[i]-frame[j][disk2];
				
				
				if 	(storageTargetsRecreationWeights[i]>0)
				{
					
			//if((storageTargetsRecreation[i]-stDisk[j])>0)
		//	recreationQ=	(-1)*( Math.pow((storageTargetsRecreation[i]-frame[i][disk1]), 2));
								
			if((storageTargetsRecreation[i]-frame[j][disk2])>0)		
			recreationQ=(-1)*(Math.pow((storageTargetsRecreation[i]-frame[j][disk2]), 2));
				}
				
				
				
				
				if 	(storageTargetsFloodWeights[i]>0)
					
				{
				
		//	if((storageTargetsFloods[i]-frame[i][disk1])<0)
		//	floodQ=(-1)*( Math.pow((storageTargetsFloods[i]-frame[i][disk1]), 2));
								
			if((storageTargetsFloods[i]-frame[j][disk2])<0)		
			floodQ=(-1)*(Math.pow((storageTargetsFloods[i]-frame[j][disk2]), 2));
				}
				
			
				
			
				                  
				                  
				double [] solution=new double [6]; 
				
				
				double []  weights= {  TownsWeights_PZ[i],TownsWeights_SS[i], AgricultureWeights_UZ[i],AgricultureWeights_LZ[i],EcologyWeights[i] };
				double[] demands = { Towns_PZ_Reservoir[i],Towns_SS_Reservoir[i],Agriculture_UZ_Reservoir[i],Agriculture_LZ_Reservoir[i],Ecology_Reservoir[i]};
				
				if (allocated>=(Towns_PZ_Reservoir[i]+Towns_SS_Reservoir[i]+Agriculture_UZ_Reservoir[i]+Agriculture_LZ_Reservoir[i]+Ecology_Reservoir[i]))
				{
					solution[0]=Towns_PZ_Reservoir[i];solution[1]=Towns_SS_Reservoir[i];solution[2]=Agriculture_UZ_Reservoir[i];solution[3]=Agriculture_LZ_Reservoir[i];
					solution[4]=allocated-(Towns_PZ_Reservoir[i]+Towns_SS_Reservoir[i]+Agriculture_UZ_Reservoir[i]+Agriculture_LZ_Reservoir[i]);

				}
					else
					{
				
						/*
						 * The two comment bellow are changing between simplex and quadratic Knapsack optimization		
						 */
				
				
		
		
				
						Distribute distribution = new Distribute() ;
				
				
						//distribution.setInput(demands, weights, allocated,20);
					//solution=distribution.calculateDPQuadraticDistribution();
				
					distribution.setInput(demands, weights, allocated,0);
					solution=distribution.calculateSimplex();
				
				
				
						
					}
				
				if (solution[0]<=demands[0]) townsQ_PZ=(-1)*Math.pow((Towns_PZ_Reservoir[i]-solution[0]),2); else townsQ_PZ=0;
				if (solution[1]<=demands[1]) townsQ_SS=(-1)*Math.pow((Towns_SS_Reservoir[i]-solution[1]),2); else townsQ_SS=0;
				 
				 
			     if (solution[2]<=demands[2]) agricultureQ_UZ=(-1)*Math.pow((Agriculture_UZ_Reservoir[i]-solution[2]),2);else agricultureQ_UZ=0;
			     if (solution[3]<=demands[3]) agricultureQ_LZ=(-1)*Math.pow((Agriculture_LZ_Reservoir[i]-solution[3]),2);else agricultureQ_LZ=0;
			    
			     
				 if (solution[4]<=demands[4]) ecologyQ=(-1)*Math.pow((Ecology_Reservoir[i]-solution[4]),2); else ecologyQ=0; 
				
				
				
				// double weight_on_water_distribution=1;
				
				
			 
				 double HP_production;
					hydroPower HP = new hydroPower();
					//the input for the hydropower are : explain !!!!
			//		HP.setInput(reservoir_height[disk1], allocated, middleInflow_HK[i],middleInflow_LK[i],(solution[0]+Towns_PZ[i]-Towns_PZ_Reservoir[i]),
			HP.setInput(reservoir_height[disk1], allocated, middleInflow_HK[i],middleInflow_LK[i],(solution[0]+Towns_PZ[i]-Towns_PZ_Reservoir[i]),
							(solution[2]+ Agriculture_UZ[i]-Agriculture_LZ_Reservoir[i]),solution[4], i);
					HP.calculate();
					
					
					
					HP_production=HP.return_total_power_Generated();
					
					//System.out.println(" hydro power demanded  "+ HydroPower[i]+ "   hydro power production " +HP_production);
					 
					 if (HydroPower[i]<HP_production) hydroPowerQ=0;
						else hydroPowerQ=(-1)*Math.pow((HydroPower[i]-HP_production),2);
					 
					 if(print) System.out.println(" hydro power demanded  "+ HydroPower[i]+ "   hydro power production " +HP_production);
				 
				 
				 
		
			
			
				
		double sumTotalReward;
		sumTotalReward=townsQ_PZ*TownsWeights_PZ[i]+
						townsQ_SS*TownsWeights_SS[i]+
						agricultureQ_UZ*AgricultureWeights_UZ[i]+
						agricultureQ_LZ*AgricultureWeights_LZ[i]+
						ecologyQ*EcologyWeights[i]+
						hydroPowerQ*HydroPowerWeights[i]+
						floodQ*storageTargetsFloodWeights[i]+
						recreationQ*storageTargetsRecreationWeights[i];
		
		
		if(print) System.out.println("starting state    final state   inflow    allocated    demand towns PZ  demand towns SS is    demand agri UZ is   demand agri LZ is  demand ecol is" );
		if(print) System.out.println(df.format(frame[i][disk1]) +"              "+df.format(frame[j][disk2])+"     "+df.format(inflow[i])
				+"          "+df.format(allocated)+ "        "+df.format(Towns_PZ_Reservoir[i])+
				"           "+ df.format(Towns_SS_Reservoir[i])+ "              " +df.format(Agriculture_UZ_Reservoir[i])+
				"             "+df.format(Agriculture_LZ_Reservoir[i])+"           "+df.format(Ecology_Reservoir[i]));
		if(print) System.out.println("                                RELEASE                       " +df.format(solution[0])+"           "+df.format(solution[1])+"          "
				+df.format(solution[2])+"              "	+ df.format(solution[3])+"              "+df.format(solution[4])+"    ");
				
		if(print) System.out.println("eerr towns ss    towns PZ         agri UZ         agriLZ           ecol           Hpow        flood    recreation      SUMTOTAL REWARD");
	 
		if(print) System.out.println( df.format(townsQ_SS)+"        " + df.format(townsQ_PZ)+ "          " + df.format(agricultureQ_UZ)+
				"         " + df.format(agricultureQ_LZ)+"         " + df.format(ecologyQ)+"         "+df.format(hydroPowerQ)+"         "
				+df.format(floodQ)+"         "+ df.format(recreationQ)+"         "+sumTotalReward);
		
		
// THE BELOW CODE IS FOR SIMULATION
		
		double [] sum_withoutWeights = new double [8];
		sum_withoutWeights[0]=     recreationQ;                   		
		sum_withoutWeights[1]=     floodQ;
		sum_withoutWeights[2]=    townsQ_PZ ;
		sum_withoutWeights[3]=    agricultureQ_UZ; 
		sum_withoutWeights[4]=     townsQ_SS; 
		sum_withoutWeights[5]=    agricultureQ_LZ; 
		sum_withoutWeights[6]=    ecologyQ; 
		sum_withoutWeights[7]=     hydroPowerQ;
		                                          		
		                           				
		this.rewardTotal=sum_withoutWeights;
		                                   		
		double [] weightstotal = new double [8];
		weightstotal[0]= storageTargetsRecreationWeights[i];
		weightstotal[1]= storageTargetsFloodWeights[i];
		weightstotal[2]= TownsWeights_PZ[i];
		weightstotal[3]= AgricultureWeights_UZ[i];
		weightstotal[4]= TownsWeights_SS[i];
		weightstotal[5]= AgricultureWeights_LZ[i];
		weightstotal[6]= EcologyWeights[i];
		weightstotal[7]= HydroPowerWeights[i];
		
		this.weightsTotal=weightstotal;
		
		
		return sumTotalReward;	
    }
	
    
    
 
 
 

 
 
 
 
 
 
 
 
 
    public String toString(){
    	String s=""; 
    	for(int j=0;j<discretizationLevels;j++){
    	    s+="|"; 
    	    for(int i=0;i<timesteps ;i++)
    		s+=frame[i][j]+"|"; 
    	    s+="\n"; 
    	    for(int k=0;k<discretizationLevels;k++) s+="--"; 
    	    s+="\n"; 
    	}
    	return s; 
        }

    
    
    

	@Override
	public boolean isFinal(IState e){
    	State s=(State)e; 
    	return (s.getTimestep()==(timesteps-1)) ;					//this should be improved also
        }



	public int timeStep() {
		// TODO Auto-generated method stub
		return timesteps;
	}
	
	public int discretization() {
		// TODO Auto-generated method stub
		return discretizationLevels;
	}

	public double ReservoirVolume() {
		// TODO Auto-generated method stub
		return reservoirVolume;
	}


	public double InflowinTimestep(int i) {
		// TODO Auto-generated method stub
		return inflow[i];
	}



	public double DemandinTimestep(int i) {
		// TODO Auto-generated method stub
		return demand[i];
	}
	
	public double [] getStoragediscretization() {
		// TODO Auto-generated method stub
		return storagediscretization;
	}

	 
	public double [][] returnframe()
	{
		return frame;
	}

	public double [] returnRewardTotal()
	{
		return rewardTotal;
	}
	
	
	public double [] returnWeights()
	{
		return weightsTotal;
	}

	
	

	


	



    
}
