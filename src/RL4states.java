import java.util.Random;

import qlearning.ActionStatePair;
import referees.OnePlayerReferee;
import Matrix.MatrixDouble;
import RLdefine.RLdefine;
import agents.AbstractAgent;
import agents.IAgent;
import algorithms.QLearningSelector;

/*
 * THIS IS THE OLD FILE WORKING 
 */
public class RL4states {

	/**
	 * @param args
	 */
	

	
	public static void main(String[] args) throws Exception {
			
		
		
		Random generator = new Random();

		
		/* 
		 *  Environment
		 */
		
		
		
		
		
		// Define different weights
		
		double [] [] weights= {{0.3,0.3,0.1,0.1,0.05,0.05,0.06,0.04},
								
								{0.1,0.1,0.2,0.1,0.2,0.1,0.1,0.1},
								
								{0.05,0.05,0.3,0.1,0.3,0.1,0.05,0.05},
								
								{0.05,0.05,0.3,0.05,0.4,0.05,0.05,0.05},
								
								
								{0.01,0.01,0.4,0.05,0.4,0.05,0.04,0.04},
								
								};
		
		
		
		
		
		for (int PP=0;PP<5;PP++)
		{
			
	
		RLdefine first=new RLdefine();
		first.initialize();
		
		first.change_weights(weights[PP]);
		
		// selecting distribution algorithm 
		first.setDistributionAlgorithmLP(false);// true is for simplex, false is for Knapsack
		
		
		/*
		 * getting middle_inflow and inflow to create the starting states of the episodes
		 */
		int lenght=first.return_lenght_inflow();
		int episodes=first.return_number_episodes();
		int timesteps=first.timeStep();
		int [] inflow_mid_interger = new int [lenght];
		int [] inflow_interger = new int [lenght];
		System.out.println("dolzinata na inflow i inflow mid e "+ lenght +" brojot na epizodi e "+episodes);
		System.out.println("timesteps "+timesteps);
		
		inflow_mid_interger=first.Infow_mid_int();
		
		inflow_interger=first.Infow_int();
		
		System.out.println("Middle inflow   ");
		for(int i=0;i<lenght;i++) System.out.print("   "+inflow_mid_interger[i]);
		System.out.println(" ");
		System.out.println(" ");
		
		System.out.println("Inflow   ");
		for(int i=0;i<lenght;i++) System.out.print("   "+inflow_interger[i]);
		System.out.println(" ");
		System.out.println(" ");
		
		
		
	
		// just testing start
		int discretization=0;
		int startingState=0;
		discretization=first.discretization();	
		first.InitialState(0,startingState,0,0);
		
		System.out.println(first.toString());
		QLearningSelector sql=new QLearningSelector();
		
		
		
		
		/*
		 * DEFINING PARAMETERS
		 */
		double epsilon=0.9;
		double alpha=0.5;
		double gamma=0.98;
		sql.setEpsilon(epsilon); // this start epsilonGreedy selector
		sql.setAlpha(alpha);
		sql.setGamma(gamma);
	

		IAgent zero07=new AbstractAgent(first,sql);
	
		OnePlayerReferee arbiter=new OnePlayerReferee(zero07); 
	
		
		
	
	
	
		
		
	
		
		
		int a=0;
	
		int itteration=0;
		
		
		
		
		/*
		 * WARM UP
		 */
		double tt=0.5;
		int warm_up=70000;
		double reward2=0;
	
		while(itteration<warm_up)
		{
			itteration++;
			first.setEpisode(itteration%episodes);

			a=itteration%episodes *(lenght/episodes);
	
			reward2=arbiter.episode(first.defineState(0, generator.nextInt(discretization),inflow_mid_interger[a],inflow_interger[a]));	
			

		
		//arbiter.setVerbosity();
		//sql.setPrintScreen();
	  arbiter.unsetVerbosity();
		
	  
	  if (itteration==warm_up/4) {sql.setAlpha((sql.getAlpha()*tt)); System.out.println("1 leg" );}  //to decrease epsilon
		if (itteration==warm_up/2) {sql.setAlpha((sql.getAlpha()*tt));System.out.println("2 leg" );}
		if (itteration==warm_up/1.5) {sql.setAlpha((sql.getAlpha()*tt));System.out.println("3 leg" );}
	 
		if (itteration==warm_up/4) {sql.setEpsilon(0.1); System.out.println("1 leg" );}  //to decrease epsilon
		if (itteration==warm_up/2) {sql.setEpsilon(0.01);System.out.println("2 leg" );}
		if (itteration==warm_up/1.5) {sql.setEpsilon(0.001);System.out.println("3 leg" );}
			
		
		System.out.println("^^^^^^ increment__ "+ reward2+" itteracija "+ itteration);
		
		}
		
		
		

		tt=0.5;
		
		
			
	
		
		System.out.println("KRAJJJJJ");
		
		System.out.println("KRAJJJJJ");
		System.out.println("KRAJJJJJ");
		System.out.println("");
		
		for(itteration=0;itteration<episodes;itteration++)
		{
			arbiter.setVerbosity();
			sql.setPrintScreen();
			sql.setAlpha(0.0);
			sql.setEpsilon(0.0);System.out.println("5 leg" );
			first.setEpisode(itteration%episodes);

			a=itteration%episodes *(lenght/episodes);
			System.out.println("itteracii "+itteration  +"   dolzina "+ lenght  + " epizodes   "+episodes +"  pocetniot eleemne e  " + a );
			reward2=arbiter.episode(first.defineState(0, 0,inflow_mid_interger[a],inflow_interger[a]));	
			
			
	

		sql.setPrintScreen();
	  //arbiter.unsetVerbosity();
		//error=arbiter.getRewardForEpisode();
			
		System.out.println("Learning increment is ____________________ _ "+reward2);
		
		
		
		
		}
	
		
		
		
		
		
		
		
		

		
		
		
		
		first.setEpisode_inflow_middle_inflow_simulation(15 );
		int [] inflow_mid_intergerS = new int [180];
		int [] inflow_intergerS = new int [180];
		
	
		inflow_mid_intergerS=first.Infow_mid_int();
		System.out.println("Middle inflow   ");
		for(int i=0;i<180;i++) System.out.print("   "+inflow_mid_intergerS[i]);
		System.out.println(" ");
		System.out.println(" ");
		
		inflow_mid_intergerS=first.Infow_mid_int();
		inflow_intergerS=first.Infow_int();
		System.out.println(" inflow integers  ");
		for(int i=0;i<inflow_intergerS.length;i++) System.out.print("    "+inflow_intergerS[i]);
		System.out.println(" ");
		System.out.println(" ");
		
	    startingState=10; /// here we define the starting state-- in this code starting and ending state are the same e.g if we start in January 
		arbiter.simulation(first.defineState(0, startingState,inflow_mid_intergerS[0],inflow_intergerS[0]), 12,180 );
	
	//	
		
		//arbiter.simulation(first.defineState(0, 4));
			
		System.out.println("ALGORIHTM SETTINGS ARE    Epsilon     alpha     gamma     NumberEpisodes   NumberClustersMiddle Flow    NumberClusters inflow    Simplex/true or Knapsack/false ");
		System.out.println("                       "+epsilon+"       "+alpha+"   "+gamma +"       "+warm_up+"         "
				+ first.returnNumberClustersMiddleInflow()   +"      "+ first.returnNumberClustersInflow()+"       "+ first.getDistributionAlgorithmLP());	
	
		
	}	
	}	
	}


















/*
 * TESTING CONVERGENCE AND STOPPING ALGORITHM
 */


/*	int stop=0;
double boundary=0.005; //to select the value below which learning will stop
int maximum_number_of_itteration=500;

while(itteration<maximum_number_of_itteration)
{
	itteration++;
	first.setEpisode(itteration%episodes);

	a=itteration%episodes *(lenght/episodes);
	//System.out.println("itteracii "+itteration  +"   dolzina "+ lenght  + " epizodes   "+episodes +"  pocetniot eleemne e  " + a );
	reward2=arbiter.episode(first.defineState(0, generator.nextInt(discretization),inflow_mid_interger[a]));	
	
	//reward2=arbiter.episode(first.defineState(0, 0,inflow_mid_interger[a]));	

//arbiter.setVerbosity();
//sql.setPrintScreen();
arbiter.unsetVerbosity();

	
System.out.println("Learning increment is  "+reward2+"   itteration "+ itteration);

if(reward2<boundary) stop++;
else stop=0;

if ((stop>episodes)&& (itteration%episodes==0)) 
{
	double learnining_icrement_per_total=0;
	for(int t=0;t<episodes;t++)
	{
	
		sql.setEpsilon(0.0);System.out.println(" epsilon is 0 leg" );
		first.setEpisode(t%episodes);

		a=t%episodes *(lenght/episodes);
		System.out.println("itteracii "+t  +"   dolzina "+ lenght  + " epizodes   "+episodes +"  pocetniot eleemne e  " + a );
		reward2=arbiter.episode(first.defineState(0, generator.nextInt(discretization),inflow_mid_interger[a]));	
		
		learnining_icrement_per_total+=reward2;

	arbiter.setVerbosity();
	sql.setPrintScreen();
  //arbiter.unsetVerbosity();
	//error=arbiter.getRewardForEpisode();
		
	System.out.println("Learning increment is ____________________ _ "+reward2);
	
	
	
	
	}
	
		if((learnining_icrement_per_total/episodes)<0.005)
		{
				System.out.println("ucenjeto  e  malo broj na itteracii   " +itteration);
				System.out.println("leaninr increment total "+learnining_icrement_per_total+"  per episode "+learnining_icrement_per_total/episodes );
				break;
		}
		else
		{
			System.out.println(" learn again" );
				stop=0;
				sql.setEpsilon(0.1);
		}
	
	
}









if (itteration%20==0){sql.setAlpha((sql.getAlpha()*tt));}
if (itteration==maximum_number_of_itteration/4) {sql.setEpsilon(0.1); System.out.println("1 leg" );}  //to decrease epsilon
if (itteration==maximum_number_of_itteration/2) {sql.setEpsilon(0.01);System.out.println("2 leg" );}
if (itteration==maximum_number_of_itteration/1.5) {sql.setEpsilon(0.001);System.out.println("3 leg" );}
	

System.out.println("^^^^^^ increment__ "+ reward2+" stop   "+ stop+" itteracija "+ itteration);

}
*/		
