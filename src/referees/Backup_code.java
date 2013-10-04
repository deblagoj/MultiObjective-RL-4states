package referees;

import RLdefine.RLdefine;
import RLdefine.State;
import environment.IAction;
import environment.IState;

public class Backup_code {

}



/*
public double compare2arraysQsa(double[]t,double[]t1)
{
	double result=0;
	if(t.length!=t1.length)
	{
	System.out.println("Array can not be compared");
	return 10000;
	}

else
{	
		for(int i=0;i<t.length;i++)
		{
			result+= Math.abs(t[i]-t1[i]);
		}
}

return result;
}
*/
/** Beginning from an initial state, play the game until : 
<ul>
<li> A final state</li>
<li> The maximal length for an episode</li>
</ul>

 	@return Length of episode
 * @throws Exception 
*/







public int [] BestStorageList ;

public IState [] BestPath;

public double best_episode_reward;

public IAction [] BestAction;

public  double episode_with_save(IState initial) throws Exception
{
this.agent007.setInitialState(initial); 
this.agent007.newEpisode(); 

episodeReward=0.0;	

IState [] Path = new IState[timesteps+1];
IAction [] ActionPath = new IAction[timesteps];
int [] StorageList = new int [timesteps+1];


for (int i=0;i<timesteps;i++)
{
 if(verbosity) System.out.println("************\nCurrent State  : "+this.agent007.getCurrentState()); 
   
 Path[i]=this.agent007.getCurrentState();
 
 StorageList[i]=((State) Path[i]).getState();
 

    IAction c=this.agent007.act();	 
 ActionPath[i]=c.copy();
 
    episodeReward+=this.agent007.getLastReward();
   
    if (verbosity) System.out.println("Action : "+c+" Step reward^^^"+this.agent007.getLastReward()+ 
    		//" QSA " + this.agent007.getAlgorithm().getValue(this.agent007.getCurrentState(), c)+
    		
    		" itteration i--"+i); 

    if(verbosity) System.out.println("State Reached  : "+this.agent007.getCurrentState()+"\n************");
    
   
}
// the two lines below are for last state e.g the 0 or starting state    
Path[timesteps]=this.agent007.getCurrentState();
StorageList[timesteps]=((State) Path[timesteps]).getState();
if(verbosity) System.out.println("Episode Reward_______________________________________ : "+ episodeReward);


if (episodeReward>best_episode_reward)
{
	

	
	System.out.println("improved policy: "+ best_episode_reward +" episode reward "+episodeReward);
	for (int j=0;j<timesteps;j++)
	{
		BestStorageList[j]=StorageList[j];
		BestPath[j]=Path[j].copy();
		BestAction[j]=ActionPath[j].copy();

    }	
	BestStorageList[timesteps]=StorageList[timesteps];
	BestPath[timesteps]=Path[timesteps].copy();
	
	best_episode_reward=episodeReward;
	
	return best_episode_reward;
}
return episodeReward;
	

}


this.best_episode_reward=-100000;// this must be improved
this.BestStorageList=new int [timesteps+1] ;
this.BestPath=new IState [timesteps+1];
this.BestAction=new IAction[timesteps];

}

double QSA;




public int [] BestStorageList ;

public IState [] BestPath;

public double best_episode_reward;

public IAction [] BestAction;

public  double episode_with_save(IState initial) throws Exception
{
this.agent007.setInitialState(initial); 
this.agent007.newEpisode(); 

episodeReward=0.0;	

IState [] Path = new IState[timesteps+1];
IAction [] ActionPath = new IAction[timesteps];
int [] StorageList = new int [timesteps+1];


for (int i=0;i<timesteps;i++)
{
 if(verbosity) System.out.println("************\nCurrent State  : "+this.agent007.getCurrentState()); 
   
 Path[i]=this.agent007.getCurrentState();
 
 StorageList[i]=((State) Path[i]).getState();
 

    IAction c=this.agent007.act();	 
 ActionPath[i]=c.copy();
 
    episodeReward+=this.agent007.getLastReward();
   
    if (verbosity) System.out.println("Action : "+c+" Step reward^^^"+this.agent007.getLastReward()+ 
    		//" QSA " + this.agent007.getAlgorithm().getValue(this.agent007.getCurrentState(), c)+
    		
    		" itteration i--"+i); 

    if(verbosity) System.out.println("State Reached  : "+this.agent007.getCurrentState()+"\n************");
    
   
}
// the two lines below are for last state e.g the 0 or starting state    
Path[timesteps]=this.agent007.getCurrentState();
StorageList[timesteps]=((State) Path[timesteps]).getState();
if(verbosity) System.out.println("Episode Reward_______________________________________ : "+ episodeReward);


if (episodeReward>best_episode_reward)
{
	

	
	System.out.println("improved policy: "+ best_episode_reward +" episode reward "+episodeReward);
	for (int j=0;j<timesteps;j++)
	{
		BestStorageList[j]=StorageList[j];
		BestPath[j]=Path[j].copy();
		BestAction[j]=ActionPath[j].copy();

    }	
	BestStorageList[timesteps]=StorageList[timesteps];
	BestPath[timesteps]=Path[timesteps].copy();
	
	best_episode_reward=episodeReward;
	
	return best_episode_reward;
}
return episodeReward;
	

}















//State[] policy = new State[numberStates_inPolicy];

//State[] best_policy_Total_reward = new State[numberStates_inPolicy];
//ActionStatePair [] best_QSA= new ActionStatePair[numberStates_inPolicy];
//ActionStatePair [] sample_QSA= new ActionStatePair[numberStates_inPolicy];

double qSA;
double maxQSA=0;

// Initializing the graphical display if needed






//  policy[i]=(State) this.agent007.getCurrentState();




// sample_QSA[i]=new  ActionStatePair(c, policy[i]);





qSA+=this.agent007.getAlgorithm().getValue(policy[i], c);
System.out.print("THIS IS STEP VALUE FOR QSA "+qSA);








System.out.println("------TOTAL REWARD FOR THIS EPISODE IS "+totalReward); 
//System.out.println("------AVERAGE REWARD FOR THIS EPISODE IS "+totalReward/numberStates_inPolicy); 
//System.out.println("Q(s,a)max table");
 
System.out.print("HEREEE sum QSA "+qSA);
System.out.println("HEREEE maxQSA "+maxQSA);


if (maxQSA< qSA)
{
	
	//double pom;
	//pom=maxQSA;
	System.out.println("maxQSA HEREE "+qSA); 
		
	for (int it=0;it<numberStates_inPolicy;it++)
	{
		best_QSA[it]=sample_QSA[it];	
	}
	maxQSA=qSA;

	


}
/*	
if (max_Reward< totalReward)
{

	max_Reward=totalReward;
	
	System.out.println("max_Reward "+max_Reward); 
 	
	for (int i=0;i<numberStates_inPolicy;i++)
	{
		best_policy_Total_reward[i]=policy[i];	
	}
Stop_calculation=0;

}
*/

}
/*

//this.optimalPath_Total_Reward(best_policy_Total_reward);
System.out.println("   ");

System.out.println("   ");
System.out.println("   ");

this.optimalPath_QSA(best_QSA);

System.out.println("   ");
System.out.println("   ");

this.optimalPath_from_starting_state(best_QSA);
return 5;

}
public void optimalPath_from_starting_state(ActionStatePair [] Solution) throws Exception
{
	for (int i=0;i<this.agent007.getEnvironment().timeStep();i++)
	{
		System.out.println("   "+Solution[i].toString());	
	}
	int numberStates_inPolicy=this.agent007.getEnvironment().timeStep();
	int i;
	double [] OptimalStorage = new double [numberStates_inPolicy];

	IState state_first=(IState) Solution[0].getState();
	IAction c=this.agent007.getAlgorithm().bestAction(state_first);
	RLdefine t1=(RLdefine)this.agent007.getEnvironment();
	double [][] desing=t1.returnDesing();
	double totalE=0;
	for (int k=0;k<numberStates_inPolicy*8;k++)
	{
		i=k%numberStates_inPolicy;
		State x1=(State)state_first;
		   IState state1=this.agent007.getEnvironment().successorState(state_first, c);
	   	 State x2=(State)state1; 
	   	 	
	   System.out.print("Period"+i);
	   System.out.print("   Curent "+desing[x1.getX()][x1.getY()]);
	   OptimalStorage[i]=desing[x1.getX()][x1.getY()];
	   System.out.print("   Inflow" + t1.inflow[x1.getX()]);
	   System.out.print("   Demand" + t1.releaseTargets[x1.getX()]);
	   System.out.print(" Final State"+ desing[x2.getX()][x2.getY()] );
	   if ((desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-t1.releaseTargets[x1.getX()]-desing[x2.getX()][x2.getY()])>0)
	    	System.out.print("  Squre difference  0.0 ");
	   else
	   { System.out.print("  Squre difference "+ Math.pow((desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-t1.releaseTargets[x1.getX()]-desing[x2.getX()][x2.getY()]),2));
	    	
	   totalE+=Math.pow((desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-t1.releaseTargets[x1.getX()]-desing[x2.getX()][x2.getY()]),2);
	   }
	    	System.out.println("");
	   	   state_first=(State) state1.copy();
	   	   IAction c1=this.agent007.getAlgorithm().bestAction(state_first);
	    	   c=(IAction) c1.copy();	
	}
	System.out.println("TOTALLL SQUARE DIFFERENCE IS   " +totalE/8);



	System.out.println("  ");
	System.out.println("  ");
	

	
	
		
}


public void optimalPath_QSA(ActionStatePair [] Solution) throws Exception
{
	for (int i=0;i<this.agent007.getEnvironment().timeStep();i++)
	{
		System.out.println("   "+Solution[i].toString());	
	}
	int numberStates_inPolicy=this.agent007.getEnvironment().timeStep();
	
	double [] OptimalStorage = new double [numberStates_inPolicy];
	RLdefine t1=(RLdefine)this.agent007.getEnvironment();
	double [][] desing=t1.returnDesing();
	double totalE=0;
	System.out.println("Optimal path by QSA");
	ActionStatePair as1=Solution[0];
	State s1=(State)as1.getState();
	Action a1=(Action)as1.getAction();
	
		for (int i=1;i<this.agent007.getEnvironment().timeStep();i++)
		{
			
			
			ActionStatePair as2=Solution[i];
			State s2=(State)as2.getState();
	    
			OptimalStorage[i]=desing[s1.getX()][s1.getY()];
			System.out.print("Period  " + (i-1)+ " momentalna sostojba "+desing[s1.getX()][s1.getY()]);
			 System.out.print(" Inflow e  " + t1.inflow[s1.getX()]);
			 System.out.print(" Demand e  " + t1.releaseTargets[s1.getX()]);
			 double rez=desing[s1.getX()][s1.getY()]+t1.inflow[s1.getX()]-desing[s2.getX()][s2.getY()];
			 System.out.print("  akcijata e "+ rez ) 	;
			 System.out.print(" check succesor state "+ this.universe.successorState(s1, a1) ); 	
			 System.out.print(" rewardot  " +this.universe.getReward(s1, s2, a1));
			 if ((desing[s1.getX()][s1.getY()]+t1.inflow[s1.getX()]-t1.releaseTargets[s1.getX()]-desing[s2.getX()][s2.getY()])>0)
			    	System.out.println("  Squre difference  0.0 ");
			   else
			   { System.out.println("  Squre difference "+ Math.pow((desing[s1.getX()][s1.getY()]+t1.inflow[s1.getX()]-t1.releaseTargets[s1.getX()]-desing[s2.getX()][s2.getY()]),2));
			    	
			   totalE+=Math.pow((desing[s1.getX()][s1.getY()]+t1.inflow[s1.getX()]-t1.releaseTargets[s1.getX()]-desing[s2.getX()][s2.getY()]),2);
			   }
			 as1=as2;
			 s1=(State)as1.getState();
		     a1=(Action)as1.getAction();
			// System.out.println("The sum error for this step is "+ this.agent007.getEnvironment().getReward(x1, x2, c));
		}
		System.out.println("TOTALLL SQUARE DIFFERENCE IS by QSA  " +totalE);
		
		
		
		AbstactData test = new AbstactData();
		int a;
		a=test.testDatabaseConnection();

		IData test1 = new AbstactData();


		test1.putStorageOptimal(OptimalStorage,numberStates_inPolicy);
}

public void optimalPath_Total_Reward(IState [] Solution) throws Exception
{
	
	
	for (int i=0;i<this.agent007.getEnvironment().timeStep();i++)
	{
		System.out.println("   "+Solution[i].toString());	
	}

	RLdefine t1=(RLdefine)this.agent007.getEnvironment();
	double [][] desing=t1.returnDesing();
	double totalE=0;
	System.out.println("Optimal path by mapping states and max rewards");
	State x1=(State)Solution[0];
	
		for (int i=1;i<this.agent007.getEnvironment().timeStep();i++)
		{
			
			
			State x2=(State)Solution[i];
			System.out.print("Period  " + (i-1)+ " momentalna sostojba "+desing[x1.getX()][x1.getY()]);
			 System.out.print(" Inflow e  " + t1.inflow[x1.getX()]);
			 System.out.print(" Demand e  " + t1.releaseTargets[x1.getX()]);
			 double rez=desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-desing[x2.getX()][x2.getY()];
			 System.out.print("        akcijata e (ispustanje na voda ) "+ rez ) 	;
			 if ((desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-t1.releaseTargets[x1.getX()]-desing[x2.getX()][x2.getY()])>0)
			    	System.out.print("  Squre difference  0.0 ");
			   else
			   { System.out.print("  Squre difference "+ Math.pow((desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-t1.releaseTargets[x1.getX()]-desing[x2.getX()][x2.getY()]),2));
			    	
			   totalE+=Math.pow((desing[x1.getX()][x1.getY()]+t1.inflow[x1.getX()]-t1.releaseTargets[x1.getX()]-desing[x2.getX()][x2.getY()]),2);
			   }
			 System.out.println("");
			 x1=x2;
			 
			
		}

		System.out.println("TOTALLL SQUARE DIFFERENCE IS by Path  " +totalE);

}
*/
 public String qSAtable(RLdefine first,int longx, int widey)
{

	 System.out.println("");
    	System.out.println("");
    	System.out.println("");
    	System.out.println("");
	if (verbosity) System.out.println("this is the tables of QSA");
	double QSA;
	int i,j;
	String s="";
	for(j=0;j<widey;j++)
	{
		 s+="|"; 
		 for(i=0;i<longx ;i++)
		{
		
			// System.out.println("i i J : "+i+"   "+j); 
			 IState bd= first.defineState(i, j);
			// System.out.println("i i hashcode: "+i+"   "+ bd.hashCode());
			 IAction c=this.agent007.getAlgorithm().bestAction(bd);
			 QSA =this.agent007.getAlgorithm().getValue(bd, c);
			 s+=QSA+" |";
		//	 if (verbosity) System.out.println("State : "+bd.toString()); 
			//  if (verbosity) System.out.println("Action : "+c+" QSA^^^" + QSA); 
		}
		 s+="\n"; 
		 for(int k=0;k<widey;k++) s+="--"; 
 	    s+="\n"; 
	}
	
return s; 
 }

	
