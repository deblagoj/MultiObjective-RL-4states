package environment;

import RLdefine.RLdefine;



    /**The game's rules : 
       <ul>
       <li> Gives the list of possible actions from a given state.</li>
       <li> Computes the next state, given a start state and an action.</li>
       <li> Knows when a state is final.</li>
       <li> Knows whether a state is a winning one.</li>
       </ul>
       
    */

    public interface IEnvironment {

	public double InflowinTimestep(int i);
	public double DemandinTimestep(int i) ;




	/**  Gives the list of possible actions from a given state. */
	public ActionList getActionList(IState s); 

	/**  Computes the next state, given a start state and an action. */
	public IState successorState(IState s,IAction a); 
	
	public IState defaultInitialState(); 

	
	public double getReward(IState s1,IState s2,IAction a); 


	public boolean isFinal(IState s); 
	
	public int timeStep();
	
	public int discretization();
	
	public int return_lenght_inflow();
	public void setEpisode(int a);

	
	public double ReservoirVolume();
	
	
	
	
	
	public double [] returnRewardTotal();
	public double [] returnWeights();
	
    }
