package environment;




public interface IState {

	/** The list of all legal moves starting from the currrent state. */
	public ActionList getActionList();

	public void setEnvironment(IEnvironment c);

	/** Change a state according to the action. */
	public IState modify(IAction a);

	/** Read the universe the state is in. */
	public IEnvironment getEnvironment();

	/** Reward of (old,this,a) */
	public double getReward(IState old, IAction a);

	/** Is this state final ?  */
	public boolean isFinal();

	/** Clone. */
	public IState copy();

	/** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
	public int hashCode();

	/** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
	public boolean equals(Object o);
	
	

}