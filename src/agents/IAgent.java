package agents;

import algorithms.ISelector;

import environment.IEnvironment;

import environment.IAction;
import environment.IState;

import java.io.Serializable;

public interface IAgent extends Serializable{

	/** Access the algorithm, for exemple to change its settings. */
	public ISelector  getAlgorithm();

	public IEnvironment getEnvironment();

	/** Put the agent in learning phase (default). */
	public void enableLearning();

	/** Stop learning. */
	public void freezeLearning();

	/** Get the state where the agent is.*/
	public IState getCurrentState();

	/** Place the agent.*/
	public void setInitialState(IState s);

	/** Read the last reward. */
	public double getLastReward();
	
	public double getIncrementLearn();

	/** Acts */
	public IAction act();

	/** Try to understand the states and/or actions values found by the algorithm. */
	public void explainValues();

	public void newEpisode();
	public  IAction getLastAction(); 
	
	public void setLastAction(IAction lastAction);
	
	public IAction applyAction(IAction a);

	public void setWeights(double[] weights);

}