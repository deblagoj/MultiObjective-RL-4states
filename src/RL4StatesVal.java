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
public class RL4StatesVal {

	/**
	 * @param args
	 * @throws Exception 
	 */

	
	static void inicializeRLDefine() throws Exception
	{
		RLdefine first=new RLdefine();
		first.initialize();
		first.setDistributionAlgorithmLP(false);
	}
	

	
	public static void main(String[] args) throws Exception {
			
		
		
		Random generator = new Random();
		//inicializeRLDefine();
		
		/* 
		 *  Environment
		 */
	RLdefine first=new RLdefine();
		first.initialize();
		
		first.setDistributionAlgorithmLP(true);// selecting distribution algorithm 
		// true is for simplex, false is for Knapsack
		
		
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
		int warm_up=600000;
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
		
		
		if (itteration%10000==0){ 
			
			
			
			
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
		
			
			
			
			
			
			
			
			
			zero07.enableLearning();
			
			first.setEpisode_inflow_middle_inflow_normal(40);
			

			// true is for simplex, false is for Knapsack
			
			
			/*
			 * getting middle_inflow and inflow to create the starting states of the episodes
			 */
			lenght=first.return_lenght_inflow();
			episodes=first.return_number_episodes();
			timesteps=first.timeStep();
		
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
			
			
			
			
			
			
			
			
		}
		
		}
		
		
		

		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println(" 2ra-tura ");
		sql.setAlpha(0.00000001); System.out.println("1 leg---2" );
		sql.setEpsilon(0.0000001); System.out.println("1 leg--2" );
	
		warm_up=20000;
		itteration=0;
		while(itteration<warm_up)
		{
			
			
			itteration++;
			first.setEpisode(itteration%episodes);

			a=itteration%episodes *(lenght/episodes);
	
			reward2=arbiter.episode(first.defineState(0, generator.nextInt(discretization),inflow_mid_interger[a],inflow_interger[a]));	
			

		
		//arbiter.setVerbosity();
		//sql.setPrintScreen();
	 arbiter.unsetVerbosity();
		
	  
	  
		
			
		
		System.out.println("^^^^^^ increment__ "+ reward2+" itteracija "+ itteration);
		
		
		if (itteration%1000==0){ 
			
			
			
			
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
				
			System.out.println("ALGORIHTM SETTINGS1 ARE    Epsilon     alpha     gamma     NumberEpisodes   NumberClustersMiddle Flow    NumberClusters inflow    Simplex/true or Knapsack/false ");
			System.out.println("                       "+epsilon+"       "+alpha+"   "+gamma +"       "+warm_up+"         "
					+ first.returnNumberClustersMiddleInflow()   +"      "+ first.returnNumberClustersInflow()+"       "+ first.getDistributionAlgorithmLP());	
		
			
			
			
			
			
			
			
			
			zero07.enableLearning();
			
			first.setEpisode_inflow_middle_inflow_normal(40);
			

			// true is for simplex, false is for Knapsack
			
			
			/*
			 * getting middle_inflow and inflow to create the starting states of the episodes
			 */
			lenght=first.return_lenght_inflow();
			episodes=first.return_number_episodes();
			timesteps=first.timeStep();
		
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
			
			
			
			
			
			
			
			
		}
		
		}
		
		
		
		
		
		
		
		
		
		
		
	}	
	    	//*/		
	}









