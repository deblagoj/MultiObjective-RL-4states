package referees; 

import java.awt.Frame;
import java.text.DecimalFormat;

import javax.imageio.stream.MemoryCacheImageInputStream;

import qlearning.ActionStatePair;
import qlearning.IRewardStore;
import Data.AbstactData;
import Data.IData;
import RLdefine.RLdefine;
import RLdefine.State;
import RLdefine.Action;
import agents.IAgent;
import algorithms.AbstractMemorySelector;
import algorithms.ISelector;
import environment.AbstractEnvironment;
import qlearning.IRewardStore;
import qlearning.RewardMemorizer;
import environment.IAction;
import environment.IState;

public class OnePlayerReferee{

   

    /** The player */
    private IAgent agent007;
    
   

    /** The universe to whom we have to send graphical informations */
    private AbstractEnvironment universe; 

    /** Reward from the current episode */
    private double episodeReward; 

    public double getRewardForEpisode(){
    	return episodeReward;
        }
    
    public IState [] OptimalPath ;//!!!!!!!!!!!improve number of states
    
    public double pathReward;
    int timesteps;
    int discretization;
  
     /** Verbosity */
    private boolean verbosity=false; 

    /** Verbose*/
    public void setVerbosity(){verbosity=true; }
    /** Mute*/
    public void unsetVerbosity(){verbosity=false;}

    /** */
    public OnePlayerReferee(IAgent agent007){
	this.agent007=agent007; 
	this.universe=(AbstractEnvironment)this.agent007.getEnvironment(); 
	this.timesteps=this.agent007.getEnvironment().timeStep();
	this.discretization=this.universe.discretization();
	
    
   // this.best_episode_reward=-100000;// this must be improved
  //  this.BestStorageList=new int [timesteps+1] ;
  //  this.BestPath=new IState [timesteps+1];
 //   this.BestAction=new IAction[timesteps];

    }

    double QSA;
    
  
    
    
    
    
    public  double episode(IState initial) throws Exception
    {
	this.agent007.setInitialState(initial); 
	this.agent007.newEpisode(); 
	
	
	double learningSum=0;

	double temp=0;
	
	
	 if(verbosity)System.out.println("");System.out.println("");System.out.println("");
	
	for (int i=0;i<timesteps;i++)
	{
	 if(verbosity) System.out.println("******\nCurrent State  : "+this.agent007.getCurrentState()); 
	   
	 IState s=this.agent007.getCurrentState();
	
	
	    IAction c=this.agent007.act();	 

	    temp=this.agent007.getAlgorithm().getValue(s, c);
	    if(verbosity) System.out.println("QSA 222:"+temp);
	
	   
	    learningSum+=this.agent007.getIncrementLearn();

	   
	}

	

	

	return learningSum;


    }
    
   

    
    
    
    
    
public  void simulation(IState initial,int episodes, int lenght ) throws Exception{
	
	int discretization=this.agent007.getEnvironment().discretization();
	
	int simInflowLenght=lenght;
	
	IState [] optimalPath = new IState[simInflowLenght+1];
	this.verbosity=false;

	IAction [] optimalPathAction = new IAction[simInflowLenght];
	
	this.agent007.freezeLearning();
	

	
	
	this.agent007.setInitialState(initial); 
	



int i;
int k=0;
	System.out.println("THIS IS THE Simulation");

	
	
	
	int [] OptimalStorageList = new int [simInflowLenght+1];
	double [] OptimalStorage=new double [simInflowLenght+1];
	
	
	int [] OptimalActionList = new int [simInflowLenght];
	double [] OptimalAction=new double [simInflowLenght];
	
	
	double [][] MinimumSquareError=new double [simInflowLenght][8];
	double [][] Weights = new double [simInflowLenght][8];
	
	
	double []totalError=new double [8];
	double []totalError_with_weights=new double [8];

	
	
	double [][] frame = new double[discretization][timesteps];
	frame=((RLdefine) this.agent007.getEnvironment()).returnframe();
	
	
	
	DecimalFormat df = new DecimalFormat("000.000");
		
	
	System.out.println("The results with weights included");
	System.out.println("time      Storage     Inflow       Release     " +
			"      Next Storage         TotalDev(with weight)       TotalDeviation        SquareTDFlood " +
			"        SquareTDRecreation                     SQ Towns         SQ Agricultyre          SQ Ecology         SQ Hydro Power ") ;
	
	
	for (int t=0;t<simInflowLenght;t++)
	{
	  
	  
	  i=t%timesteps;
	  if (i==0) {this.agent007.getEnvironment().setEpisode(k);k++;} 
	  
	  if(verbosity) System.out.println("************\nCurrent State  : "+this.agent007.getCurrentState()); 
	    IState c=this.agent007.getCurrentState();
	    
	   
		OptimalStorageList[t]=((State) c).getState();
		OptimalStorage[t]=frame[0][OptimalStorageList[t]];
	    
	    optimalPath[t]=c;
	    
	    
	    
	    
	    
	    IAction a=this.agent007.getAlgorithm().bestAction(c);
	    
	    
	    
	   
	    
	    optimalPathAction[t]=a;
	    
	    
	    
		  OptimalActionList[t]= a.getAction(); 

		  if(verbosity) System.out.println("Action : "+a.toString()+" QSA " + 
		    		this.agent007.getAlgorithm().getValue(c, a)+" itteration i--"+t); 
 		
	    this.agent007.applyAction(a);
	 
	    
	    if(verbosity) System.out.println(" Step reward^^^"+this.agent007.getLastReward());
	    

	   
	   

	  if(verbosity) System.out.println("State Reached  : "+this.agent007.getCurrentState()+"\n************"); 
	  
	
	  
	  

		OptimalAction[t]=OptimalStorage[t]-frame[0][((State) this.agent007.getCurrentState()).getState()];
	    
	   
	  
	  
	  

		
		MinimumSquareError[t]=this.agent007.getEnvironment().returnRewardTotal();
		Weights[t]=this.agent007.getEnvironment().returnWeights();
		
		for(int p=0;p<8;p++)
		{
			totalError[p]+=MinimumSquareError[t][p];
			totalError_with_weights[p]+=MinimumSquareError[t][p]*Weights[t][p];
		}

		
	
		 System.out.print( t  +  "           "+ df.format(OptimalStorage[t])+ "       " +df.format(this.agent007.getEnvironment().InflowinTimestep(i))
				 +"        "+ df.format(OptimalAction[t])+"                   " +  
				 df.format(frame[0][((State) this.agent007.getCurrentState()).getState()])+ "                                  ");
		 
		 for(int p=0;p<8;p++)
			{
			 System.out.print(df.format(MinimumSquareError[t][p]*Weights[t][p])+"     ");
			
			}	
		  System.out.println("");
	}

	double TotalErrorWithoutWeights=0;
	double TotalErrorWithWeights=0;
	 for(int p=0;p<8;p++)
		{
		 TotalErrorWithoutWeights+=totalError[p];
		 TotalErrorWithWeights+=totalError_with_weights[p];
		}
	 
	 
	 System.out.println(" Results are ");
	 System.out.println(" Total without weights    recreation    flood     Town PZ    agriHQ    town SS         agricLR     ecology     hydropower   ");
	 
	 System.out.print(""+ df.format(TotalErrorWithoutWeights)+"  ");
	 for(int p=0;p<8;p++)
		{
		 System.out.print(df.format(totalError[p])+"     ");
		
		}	
	
	 System.out.println(""); 
	 System.out.println("   ");
 
	 System.out.print(""+ df.format(TotalErrorWithWeights)+"  ");
	 for(int p=0;p<8;p++)
		{
		 System.out.print(df.format(totalError_with_weights[p])+"     ");
		
		}	
			
	
	
	 
	 System.out.println("     ");
	 System.out.println(" reservoir volume list  ");
	
	 for(int p=0;p<simInflowLenght;p++)
		{
		 if(p%timesteps==0)System.out.println("");
		 System.out.print("   "+ OptimalStorage[p]);
		
		}
	    //list[i].hashCode();
	
	 System.out.println(" reservoir volume list11  ");
		
	 for(int p=0;p<simInflowLenght;p++)
		{
	
		 System.out.println( OptimalStorage[p]);
		
		}
	
	
	
	IData test1 = new AbstactData();
	
	test1.putStorageOptimal(OptimalStorage,simInflowLenght);
	
	
	
	
    }
    
    

    


















    
    

    
}


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //!! the code bellow is change a little bit 
    /*
    public  void final_episode() throws Exception{
    	int j;
  
    	int discretization=this.agent007.getEnvironment().discretization();
    	IState [] path = new IState[timesteps+1];
    	IState [] optimalPath = new IState[timesteps+1];
    	
    	IAction [] pathAction = new IAction[timesteps];
    	IAction [] optimalPathAction = new IAction[timesteps];
    	
    	
    	double bestEpisodeReward=Double.POSITIVE_INFINITY;
    	
    	
    	for (j=0;j<discretization;j++)
    	{
    		IState c1=this.agent007.getEnvironment().defineState(0,j);
    	this.agent007.setInitialState(c1); 
    	this.agent007.newEpisode(); 
    	
    	episodeReward=0.0;
    	
    	System.out.println("THIS IS THE SOLUTION  "+ j);

    	for (int i=0;i<timesteps;i++)
    	{
    	  System.out.println("************\nCurrent State  : "+this.agent007.getCurrentState()); 
    	  
    	  
    	  
    	    IState c=this.agent007.getCurrentState();
    	    
    	    path[i]=c;
    	    
    	    
    	    IAction a=this.agent007.getAlgorithm().bestAction(c);
    	    
    	    pathAction[i]=a;
    	    
    	    this.agent007.applyAction(a);
    	 
    	    episodeReward+=this.agent007.getLastReward();
    	   
    	    System.out.println("Action : "+a.toString()+" Step reward^^^"+this.agent007.getLastReward()+" itteration i--"+i); 

    	  System.out.println("State Reached  : "+this.agent007.getCurrentState()+"\n************"); 
    	  
    	}
    	
    	path[timesteps]=this.agent007.getCurrentState();  //this is for the last state (e.g the first state !! 
    	System.out.println("EPISODE REWARD FINAL   "+episodeReward+"\n************"); 
    	
    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
    	
    	if (Math.abs(episodeReward)<=bestEpisodeReward)
		{
    		
    		System.out.println("promena na pathot podobar " + bestEpisodeReward +"  so  "+ episodeReward);
			
    		for (int i=0;i<timesteps;i++)
    		{
				 optimalPath[i]=path[i];
				 optimalPathAction[i]=pathAction[i];
    		}
    		
    		bestEpisodeReward=Math.abs(episodeReward);
		}
    	
    	
    	
    	}
    
    	int [] OptimalStorageList = new int [timesteps];
    	double [] OptimalStorage=new double [timesteps];
    	
    	
    	int [] OptimalActionList = new int [timesteps];
    	double [] OptimalAction=new double [timesteps];
    	
    	double [] OptimalRelease=new double [timesteps];
    	double [] MinimumSquareError=new double [timesteps];
    	
    	double [][] frame = new double[discretization][timesteps];
    	frame=((RLdefine) this.agent007.getEnvironment()).returnframe();
    	double discretizationVolume= this.agent007.getEnvironment().ReservoirVolume()/(this.agent007.getEnvironment().discretization()-1);
    	System.out.println("discretization volume" +discretizationVolume);                            
    	int k=0;
    	for (int i=0;i<timesteps;i++)
    	{
    	 
    		
    		OptimalStorageList[i]=((State) optimalPath[i]).getState();
    		OptimalActionList[i]=  optimalPathAction[i].getAction();
    		OptimalStorage[i]=frame[i][OptimalStorageList[i]];
    		OptimalAction[i]=OptimalActionList[i]*discretizationVolume;
    		
    		
    	    //list[i].hashCode();
    	}
    	
    	double totalError=0;
    	for (int i=0;i<timesteps;i++)
    	{
    		k=i+1;
    		if (k==timesteps)k=0;
    		
    		OptimalRelease[i]=OptimalStorage[i]+OptimalAction[i]+this.agent007.getEnvironment().InflowinTimestep(i)
    		-this.agent007.getEnvironment().DemandinTimestep(i)-OptimalStorage[k];
    		MinimumSquareError[i]=this.agent007.getEnvironment().getReward(optimalPath[i], optimalPath[k], optimalPathAction[i]);
    		
    		totalError+=MinimumSquareError[i];
    	}
    	
    	System.out.println("Volume starting    Inflow      Demand        Release          Volume ending       Action          SquareError    ");
    	for (int i=0;i<timesteps;i++)
    	{
    	 
    		k=i+1;
    		if (k==timesteps)k=0;
    		
    		System.out.print(OptimalStorage[i]+"              ");
    		System.out.print(this.agent007.getEnvironment().InflowinTimestep(i)+"          ");
    		System.out.print(this.agent007.getEnvironment().DemandinTimestep(i)+"          ");
    		System.out.print(OptimalRelease[i]+"           ");
    		System.out.print(OptimalStorage[k]+"          ");
    		System.out.print(OptimalAction[i]+"           ");
    		System.out.println(MinimumSquareError[i]+"     ");
    	    //list[i].hashCode();
    	}
    	
    	System.out.println("TOTAL ERROR is      "+totalError);
    	
    	IData test1 = new AbstactData();
		
		test1.putStorageOptimal(OptimalStorage,timesteps);
    	
        }
    
    
    
    
    public  void  episode_1(IState initial) throws Exception
    {
	this.agent007.setInitialState(initial); 
	this.agent007.newEpisode(); 
	
	episodeReward=0.0;
	QSA=0;
	
	

	for (int i=0;i<timesteps;i++)
	{
	 if(verbosity) System.out.println("************\nCurrent State  : "+this.agent007.getCurrentState()); 
	   
	
	 
	    IAction c=this.agent007.act();	  
	 
	    episodeReward+=this.agent007.getLastReward();
	    
	   // QSA+=this.agent007.getAlgorithm().getValue(this.agent007.getCurrentState(), c);  ?? this is probably worng
	    
	    if (verbosity) System.out.println("Action : "+c+" Step reward^^^"+this.agent007.getLastReward()+ 
	    		//" QSA " + this.agent007.getAlgorithm().getValue(this.agent007.getCurrentState(), c)+
	    		
	    		" itteration i--"+i); 

	    if(verbosity) System.out.println("State Reached  : "+this.agent007.getCurrentState()+"\n************");
	    
	   
	}
	     
	if(verbosity) System.out.println("Episode Reward_______________________________________ : "+ episodeReward);
	if(verbosity) System.out.println("SUM SQA_______________________________________ : "+ QSA);

    }
    
    
 
    
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
    
    ActionStatePair [][] listBestActionStates;
    
    
    
    public ActionStatePair [][] get_best_actions_states()
    {
    	// System.out.println("This are best action per state");
    	
    	int i,j;
    	IState s;
    	IAction a;
    	
    
    	
    	ActionStatePair [][] listBestActionStates=new ActionStatePair[discretization][timesteps];
    	
  
    	
    	//System.out.println("discretization  " +discretization+"   timesteps  "+timesteps);
    	  	
    	for (i=0;i<discretization;i++)
    	{
    		
    		for (j=0;j<timesteps;j++)
    		{
    			s=this.universe.defineState(j, i);
    			a=this.agent007.getAlgorithm().bestAction(s);
    			ActionStatePair as=new ActionStatePair(a, s);
    
    			listBestActionStates[i][j]=as;
    		
    			// System.out.print(listBestActionStates[i][j].getAction().toString());
    			// System.out.print("|"); 
    			
    			
    		}
    		 System.out.println();
    			
    	}
    	this.listBestActionStates=listBestActionStates;
    	return listBestActionStates;
    }
    
    
    
    
    
    public int compare2Action_States_solutions(ActionStatePair [][] actionStatesSolution1,ActionStatePair [][] actionStatesSolution2)
    {
    	 System.out.println("This is the comparison !!!!");
    	
    	int i,j;
    	    	int difference=0;
    boolean result=true;
    
    	for (i=0;i<discretization;i++)
    	{
    		
    		for (j=0;j<timesteps;j++)
    		{
    			result=actionStatesSolution1[i][j].equals(actionStatesSolution2[i][j]);
    			//System.out.println("ova e edniot "+actionStatesSolution1[i][j].toString()+ "    ova e drugiot "+actionStatesSolution2[i][j].toString()+"   odgovor  "+result);
    			
    			if(result==false) {difference++;
    			System.out.println("ova e edniot "+actionStatesSolution1[i][j].toString()+ "    ova e drugiot "+actionStatesSolution2[i][j].toString()+"   odgovor  "+result);
    			}
    			
    			
    		}
    		
    	}
    	return difference;
    }
    
    
    
    
    
    public ActionStatePair [][] copyAction_states_solution( ActionStatePair [][] actionStatesSolution2)
    {
    	// System.out.println("This are best action per state");
    	
    	int i,j;
    	ActionStatePair [][] copyActionState=new ActionStatePair[discretization][timesteps];
   		
    	for (i=0;i<discretization;i++)
    	{
       		for (j=0;j<timesteps;j++)
    		{
       			copyActionState[i][j]=actionStatesSolution2[i][j];
     			
    		}
    		
    	}
    	return copyActionState;
    }
    
    
    
    	
    public void print_best_actions()
    {
    	 System.out.println("These are the max-QSA per state");
    	
    	int i,j;
    	IState s;
    	IAction a;
    	
    	int discretization=this.universe.discretization();
    	
    	
    	
    	for (i=0;i<discretization;i++)
    	{
    		
    		for (j=0;j<timesteps;j++)
    		{
    			s=this.universe.defineState(j, i);
    			a=this.agent007.getAlgorithm().bestAction(s);
    			
    			 System.out.print(a.toString());
    			 System.out.print("|"); 
    			
    			
    		}
    		 System.out.println();
    			
    	}
    	
    	
    }
    
   
    
    
  



    public  void final_episode_with_starting_state(IState initial) throws Exception{
    	
    	int discretization=this.agent007.getEnvironment().discretization();
    	
    	IState [] optimalPath = new IState[timesteps+1];
    	
 
    	IAction [] optimalPathAction = new IAction[timesteps];
    	
    	agent007.freezeLearning();
    	
    	
    	this.agent007.setInitialState(initial); 
    	this.agent007.newEpisode(); 
    	
    	episodeReward=0.0;
 

    	System.out.println("THIS IS THE SOLUTION");

    	for (int i=0;i<timesteps;i++)
    	{
    	  System.out.println("************\nCurrent State  : "+this.agent007.getCurrentState()); 
    	  
    	  
    	  
    	    IState c=this.agent007.getCurrentState();
    	    
    	    optimalPath[i]=c;
    	    
    	    
    	    IAction a=this.agent007.getAlgorithm().bestAction(c);
    	    optimalPathAction[i]=a;
    	    this.agent007.applyAction(a);
    	 
    	    episodeReward+=this.agent007.getLastReward();
    	   
    	    System.out.println("Action : "+a.toString()+" Step reward^^^"+this.agent007.getLastReward()+" QSA " + 
    	    		this.agent007.getAlgorithm().getValue(optimalPath[i], a)+" itteration i--"+i); 

    	  System.out.println("State Reached  : "+this.agent007.getCurrentState()+"\n************"); 
    	  
    	}
    	optimalPath[timesteps]=this.agent007.getCurrentState();  //this is for the last state (e.g the first state !!
    	System.out.println("EPISODE REWARD FINAL-------------------"+episodeReward+"\n************"); 
    	
    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
    	
    	
    	
    	int [] OptimalStorageList = new int [timesteps+1];
    	double [] OptimalStorage=new double [timesteps+1];
    	
    	
    	int [] OptimalActionList = new int [timesteps];
    	double [] OptimalAction=new double [timesteps];
    	
    	double [] OptimalRelease=new double [timesteps];
    	double [] MinimumSquareError=new double [timesteps];
    	
    	
    	// this should be improved
    	double [][] frame = new double[discretization][timesteps];
    	frame=((RLdefine) this.agent007.getEnvironment()).returnframe();
    	//double discretizationVolume= this.agent007.getEnvironment().ReservoirVolume()/(this.agent007.getEnvironment().discretization()-1);
    	                       
    	int k=0;
    	for (int i=0;i<timesteps;i++)
    	{    		
    		OptimalStorageList[i]=((State) optimalPath[i]).getState();
    		OptimalStorage[i]=frame[i][OptimalStorageList[i]];
    	}
    	
    	// this is for the first state 
    	OptimalStorageList[timesteps]=((State) optimalPath[timesteps]).getState();
    	OptimalStorage[timesteps]=frame[0][OptimalStorageList[timesteps]];
    	
    	for (int i=0;i<timesteps;i++)
    	{
    	 
    		
    		OptimalActionList[i]=  optimalPathAction[i].getAction();
     		OptimalAction[i]=OptimalStorage[i]-OptimalStorage[i+1];

    	}
    	
    	
    	double totalError=0;
    	for (int i=0;i<timesteps;i++)
    	{
    		k=i+1;
    		
    		
    		OptimalRelease[i]=OptimalStorage[i]+OptimalAction[i]+this.agent007.getEnvironment().InflowinTimestep(i)
    		-this.agent007.getEnvironment().DemandinTimestep(i)-OptimalStorage[k];
    		MinimumSquareError[i]=this.agent007.getEnvironment().getReward(optimalPath[i], optimalPath[k], optimalPathAction[i]);
    		
    		totalError+=MinimumSquareError[i];
    	}
    	
    	System.out.println("Volume starting    Inflow      Demand        Release          Volume ending       Action          SquareError    ");
    	for (int i=0;i<timesteps;i++)
    	{
    	 
    		k=i+1;
    		
    		
    		System.out.print(OptimalStorage[i]+"              ");
    		System.out.print(this.agent007.getEnvironment().InflowinTimestep(i)+"          ");
    		System.out.print(this.agent007.getEnvironment().DemandinTimestep(i)+"          ");
    		System.out.print(OptimalRelease[i]+"           ");
    		System.out.print(OptimalStorage[k]+"          ");
    		System.out.print(OptimalAction[i]+"           ");
    		System.out.println(MinimumSquareError[i]+"     ");
    	    //list[i].hashCode();
    	}
    	
    	System.out.println("TOTAL ERROR is      "+totalError);
    	
    //	IData test1 = new AbstactData();
		
		//test1.putStorageOptimal(OptimalStorage,timesteps);
    	
    	
    	
    	
        }
    
    
    
    
public  void final_episode_with_path() throws Exception{
    	
    	int discretization=this.agent007.getEnvironment().discretization();
   
    	
    	System.out.println("THIS IS THE FINAL SOLUTION!!!!! ");
    	
    	
    	int [] OptimalStorageList = new int [timesteps+1];
    	double [] OptimalStorage=new double [timesteps+1];
    	
    	
    	int [] OptimalActionList = new int [timesteps];
    	double [] OptimalAction=new double [timesteps];
    	
    	double [] OptimalRelease=new double [timesteps];
    	double [] MinimumSquareError=new double [timesteps];
    	
    	
    	// this should be improved
    	double [][] frame = new double[discretization][timesteps];
    	frame=((RLdefine) this.agent007.getEnvironment()).returnframe();
    	//double discretizationVolume= this.agent007.getEnvironment().ReservoirVolume()/(this.agent007.getEnvironment().discretization()-1);
    	                       
    	int k=0;
    	for (int i=0;i<timesteps;i++)
    	{    		
    		OptimalStorageList[i]=((State) BestPath[i]).getState();
    		OptimalStorage[i]=frame[i][OptimalStorageList[i]];
    	}
    	
    	// this is for the first state 
    	OptimalStorageList[timesteps]=((State) BestPath[timesteps]).getState();
    	OptimalStorage[timesteps]=frame[0][OptimalStorageList[timesteps]];
    	
    	for (int i=0;i<timesteps;i++)
    	{
    	 
    		
    		OptimalActionList[i]=  BestAction[i].getAction();
     		OptimalAction[i]=OptimalStorage[i]-OptimalStorage[i+1];

    	}
    	
    	
    	double totalError=0;
    	for (int i=0;i<timesteps;i++)
    	{
    		k=i+1;
    		
    		
    		OptimalRelease[i]=OptimalStorage[i]+OptimalAction[i]+this.agent007.getEnvironment().InflowinTimestep(i)
    		-this.agent007.getEnvironment().DemandinTimestep(i)-OptimalStorage[k];
    		MinimumSquareError[i]=this.agent007.getEnvironment().getReward(BestPath[i], BestPath[k], BestAction[i]);
    		
    		totalError+=MinimumSquareError[i];
    	}
    	
    	System.out.println("Volume starting    Inflow      Demand        Release          Volume ending       Action          SquareError    ");
    	for (int i=0;i<timesteps;i++)
    	{
    	 
    		k=i+1;
    		
    		
    		System.out.print(OptimalStorage[i]+"              ");
    		System.out.print(this.agent007.getEnvironment().InflowinTimestep(i)+"          ");
    		System.out.print(this.agent007.getEnvironment().DemandinTimestep(i)+"          ");
    		System.out.print(OptimalRelease[i]+"           ");
    		System.out.print(OptimalStorage[k]+"          ");
    		System.out.print(OptimalAction[i]+"           ");
    		System.out.println(MinimumSquareError[i]+"     ");
    	    //list[i].hashCode();
    	}
    	
    	System.out.println("TOTAL ERROR is      "+totalError);
    	
    	IData test1 = new AbstactData();
		
		test1.putStorageOptimal(OptimalStorage,timesteps);
    	
    	

    	
    	
        }
    
    public double[][] get_max_SQA()
    {
    	if(verbosity) System.out.println("This is get Max QSA");
    	
    	int i,j;
    	IState s;
    	IAction a;
    	
      	double [][] bestQSA=new double[discretization][timesteps];
   
    	for (i=0;i<this.universe.discretization();i++)
    	{
    		
    		for (j=0;j<timesteps;j++)
    		{
    			s=this.universe.defineState(j, i);
    			a=this.agent007.getAlgorithm().bestAction(s);
    			
    			if(verbosity) System.out.printf("%.2f",this.agent007.getAlgorithm().getValue(s, a));
    			if(verbosity) System.out.print("|"); 
    			bestQSA[i][j]=this.agent007.getAlgorithm().getValue(s, a);
    			
    		}
    		if(verbosity) System.out.println();
    			
    	}
    	
    	return bestQSA;
    }
    
    
    
    
    
    
    */
    
    

    	

   
    
    

	
