package environment;




	abstract public class AbstractEnvironment implements IEnvironment{

	    /**  Gives the list of possible actions from a given state. */
	    abstract public ActionList getActionList(IState s); 
	    
	    /**  Computes the next state, given a start state and an action. */
	    abstract public IState successorState(IState s,IAction a); 

	    abstract public IState defaultInitialState(); 
	    
	    abstract public double getReward(IState s1,IState s2,IAction a); 
	 
	    abstract public int  timeStep();
	
	    abstract public int  discretization();
	 
	    abstract public boolean isFinal(IState s); 
	 
	    
	    abstract public double [] returnRewardTotal();
	    abstract public double [] returnWeights();
	    abstract public int return_lenght_inflow();
	    
	    abstract public void setEpisode(int a);
	  }




	    

	
