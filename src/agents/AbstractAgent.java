package agents;



import Matrix.ArrayDouble;
import algorithms.ISelector;



import environment.ActionList;
import environment.IAction;
import environment.IEnvironment;
import environment.IState;

public class AbstractAgent implements IAgent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Test Comment : erase
	 */
	/** The current state of the agent. */
	protected IState currentState = null;
	/** The algorithm which chooses the action among the liste of possible ones.xx*/
	protected ISelector algorithm;
	/** The universe in which the agent lives (to allow the referee to communicate with graphical interface) */
	protected IEnvironment universe;
	/** The last action performed by this agent */
	protected IAction lastAction=null;
	
	protected IState oldState=null;

	public ISelector getAlgorithm() {
	return this.algorithm; 
	}

	/** Controlling whether the agent is in a learning phase or not. */
	protected boolean learningEnabled = true;

	public IEnvironment getEnvironment() {return this.universe;}

	/** Last reward. */
	protected double reward = 0;
	
	protected double incrementLearn=0;

	public void enableLearning() {learningEnabled=true;}

	public void freezeLearning() {learningEnabled=false;}

	public IState getOldState(){return this.oldState; }
	

	public IState getCurrentState() {
	return this.currentState; 
	}

	public void setInitialState(IState s) {
		this.oldState=s;
		this.currentState=s; 
	}

	/** Ask the algorithm to choose the next action. */
	/* TODO : It might be that this chosen action will not be the one finally performed (02/2007)*/
	
	public IAction choose() {
	ActionList l=getActionList();
	IAction prov=algorithm.getChoice(l); 
	this.lastAction=(IAction)prov.copy();
	//System.out.println("!!!!!!!!!!!!!!  "+prov.toString());
	return prov;
	}

	protected ActionList getActionList() {
		ActionList prodebug=currentState.getActionList();
		return prodebug;
		
		
	}

	/** <ul>
	<li>Apply the action, get the reward.</li>
	<li>If learning is enabled, learn from states, action, and reward. </li>
	<ul>*/
//	double [] multipleReward = new double [6];

	
	
	private double weights [];
	   

	 
	 
	 public void setWeights(double  [ ]w)
	 {
		 this.weights=w;
	 }
	
	
	public IAction applyAction(IAction a) { 
	oldState = currentState;
	currentState=currentState.modify(a); 
	
	double r;
 r=currentState.getReward(oldState, a);
		
	
//	double r=multipleReward[0]*weights[0]+multipleReward[1]*weights[1]+multipleReward[2]*weights[2]+
	//multipleReward[3]*weights[3]+multipleReward[4]*weights[4]+multipleReward[5]*weights[5];
	this.reward=r; 
	if(this.learningEnabled)
		incrementLearn= algorithm.learn(oldState,currentState,a,r);
	return a; 
	}

	
	public double getIncrementLearn(){return incrementLearn;}
	public double getLastReward() {return reward;}

	public IAction act() {
		return this.applyAction(this.choose());
	}

	public void explainValues() {
	System.out.println(algorithm); 
	}



	public void newEpisode() {
		reward=0.0;
		this.lastAction=null;
		//DEBUG
		this.oldState=null;
		if(this.algorithm!=null)
		    this.algorithm.newEpisode(); 
		}


	


	
	
	public AbstractAgent(IEnvironment s, ISelector al) {
		this.algorithm=al; 
		this.universe=s; 
		
		 if(s!=null)
		    	this.currentState=s.defaultInitialState();
	}


	/**
	 * @return the lastAction
	 */
	public final IAction getLastAction() {
		return lastAction;
	}

	/**
	 * @param lastAction the lastAction to set
	 */
	public final void setLastAction(IAction lastAction) {
		this.lastAction = lastAction;
	}
	
	
		
}