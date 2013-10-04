package algorithms;

/*
 *   MUST BE IMPROVED !!! THE BEST ACTION VALUE SHOULD BE STORED AND NOT SEARCHED AT EVERY STEP !!! TOTALY WRONG 
 *
 */

import java.util.Iterator;
import java.util.Random; 

import qlearning.IRewardStore;
import qlearning.RewardMemorizer;
import environment.ActionList;
import environment.IAction;
import environment.IState;


/**
 * The base of all Q-Learning-like algorithms :
 * 
 * <ul>
 * <li> Provides a structure to memorize or compute the Q(s,a) </li>
 * <li> Contains all the parameters used in the Q-Learning update rules </li>
 * <li> Contains all the parameters used to control convergence</li>
 * </ul>
 * 
 * 
 * 
 * @author Francesco De Comite (decomite at lifl.fr)
 * @version $Revision: 1.0 $
 * 
 */
/* October 4th 2006 : return to the old version, where boltzmann, epsilon-greedy
 *  and rouletteWheel are declared inside this class : 
 *  too much problems in defining and monitoring the parameters of each
 *  choosing strategy (epsilon, tau, modifying epsilon...) (fd)
 * 
 */
abstract public class AbstractMemorySelector implements ISelector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Memorizing or computing Q(s,a) */
	protected IRewardStore memory;

	

	/** Learning rate */
	protected double alpha;

	/** discount rate */
	protected double gamma;
	
	protected double epsilon; 
	
	private Random generator=new Random();



	/**
	 * Factor by which we multiply alpha at each learning step (geometric decay)<br>
	 * <i> Note : geometric decay does no insure convergence.</i>
	 */
	protected double decayAlpha = 0.999999999;

	/** Number of learning steps */
	protected double count = 1.0;

	/**
	 * Power of decay when alpha=1/(count^alphaDecayPower)
	 * 
	 * @see <a href="http://www.cs.tau.ac.il/~evend/papers/ql-jmlr.ps">Learning
	 *      rates for Q-Learning</a>
	 */
	protected double alphaDecayPower = 0.8;
	
	

	public void setAlpha(double a) {
		this.alpha = a;
	}

	public void setGamma(double g) {
		this.gamma = g;
	}
	public void setDecay(double d) {
		this.decayAlpha = d;
	}

	public void setAlphaDecayPower(double a) {
		this.alphaDecayPower = a;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getGamma() {
		return gamma;
	}

	public double getDecay() {
		return this.decayAlpha;
	}

	public double getAlphaDecayPower() {
		return this.alphaDecayPower;
	}

	/**
	 * Alpha decay methods
	 * <ul>
	 * <li> Geometric : use decayAlpha </li>
	 * <li> Exponential : use alphaDecayPower (default)</li>
	 * <ul>
	 */
	protected boolean geometricDecay = false;

	public void setGeometricAlphaDecay() {
		geometricDecay = true;
	}

	public void setExponentialAlphaDecay() {
		geometricDecay = false;
	}

	/**
	 * How convergence is controlled ?
	 * <ul>
	 * <li> true : alpha decays geometrically </li>
	 * <li> false : alpha decays exponentially</li>
	 * </ul>
	 */
	public boolean getGeometricDecay() {
		return geometricDecay;
	}

	/**
	 * How to implement randomness ?
	 * <ul>
	 * <li>epsilon-greedy</li>
	 * <li>Roulette wheel selection</li>
	 * <li>Boltzmann </li>
	 * <ul>
	 * Roulette wheel or Boltzmann selection makes epsilon useless.
	 */
	protected boolean rouletteWheel = false;

	protected boolean epsilonGreedy = true;

	protected boolean boltzmann = false;
	



	/** Finding Q(s,a) */
	public double getValue(IState s, IAction a) {
		return memory.get(s, a);
	}

	/** Nothing to reset at this level. */
	public void newEpisode() {
	};

	/**
	 * Learning from experience.
	 * 
	 * @param s1
	 *            the start state.
	 * @param s2
	 *            the arrival state.
	 * @param a
	 *            the chosen action.
	 * 
	 * <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node65.html">Sutton &
	 * Barto p 149 Q-Learning</a>
	 * @param reward
	 *            immediate reward.
	 *            
	 */
	boolean print=false;
	public void setPrintScreen()
	{
	print=true;	
	}
	
	
	public double learn(IState s1, IState s2, IAction a, double reward) {
		/*if (geometricDecay)
			alpha *= decayAlpha;
		else {
			alpha = 1 / Math.pow(count + 1.0, this.alphaDecayPower);
		}
		*/
		count++;
		double qsa = memory.get(s1, a);
	
	
		double difference = 0;
		ActionList la = s2.getActionList();
		if (la.size() != 0) {
			Iterator<IAction> iterator = la.iterator();
			double maxqsap = memory.get(s2, iterator.next());
			while (iterator.hasNext()) {
				IAction aprime = iterator.next();
				double qsap = memory.get(s2, aprime);
				if (qsap > maxqsap)						//!!! to maximize or minimize the selection
					maxqsap = qsap;
			}
			if (print) System.out.println("------------------");
			if (print) System.out.print("previous QSA "+qsa);
			
			//qsa = (1-alpha)*qsa+ alpha*((1-gamma)*reward + gamma * maxqsap);
			difference=qsa;
			qsa += alpha * (reward + gamma * maxqsap - qsa);
			//qsa=qsa + alpha * (reward - qsa);
			
			difference=Math.abs((qsa-difference));
			if (print) System.out.println(" procentage difference------------------------------------------------------------------------------------------------- " + difference);
			
			
			memory.put(s1, a, s2, qsa);
			
			
			if (print) System.out.println(" alpha  "+ alpha+"  reward  "+ reward+ " gamma  "+gamma+ " maxqsap  " + maxqsap);
			if (print) System.out.println("state 1" + s1.toString()+" action   "+a.toString()+"state 2 "+ s2.toString()+" qsa--  "+ qsa);
		
			if (print) System.out.println("------------------");
			
		} else {
			memory.put(s1, a, s2, qsa + alpha * (reward - qsa));
			//System.out.println("d222222222222222");
			//System.out.println("state 1" + s1.toString()+" action   "+a.toString()+"state 2 "+ s2.toString()+" qsa--  "+ qsa);
			//System.out.println("d2222222222222222222");
		}
		
		return difference;
	}
	

	

	/** Choose one of the legal moves */
	public IAction getChoice(ActionList l) {
		
		if (epsilonGreedy)
			return epsilonGreedyChoice(l);
		
		return null;
	}

	 
	
	/** Epsilon-greedy choice of next action */
	private IAction epsilonGreedyChoice(ActionList l){
		if(l.size()==0) return null;
		IState s=l.getState(); 	
		IAction firstA=l.get(0); 
		double maxqsap=memory.get(s,firstA);
	
		
		
		// TODO Beginning the method with this test should speed up the program
		
		//System.out.println("epsilon is "+this.epsilon );
		if(generator.nextDouble()>this.epsilon)
			{
			// TODO : might use an iterator
			for(int i=0;i<l.size();i++){
			    IAction a=l.get(i); 
			    double qsap=memory.get(s,a);
			    if(qsap>maxqsap) {						//!! the smallest should be selected
			    	maxqsap=qsap;  
				firstA=a;
			    }
			}
			//System.out.println("The best action is selected ----- " +firstA.toString());
			return firstA; 
			
			}
		
		else 
			{
				int a=generator.nextInt(l.size());
				System.out.println("This is random action----- " +l.get(a).toString()+"   episolo value "+epsilon);
			    return l.get(a);
			
				
			}
		
		
	}

	
	
	/*
	 * HERE IT SHOULD BE THE CODE FOR SMART SELECTION OF ACTIONS 
	 * For begginign it can be set to choose with 50% of random actions an action which is close to the best action ! it would definitely
	 * improve algorithm
	 * Benchmark it !
	 */
	
	
	
	/** Auxiliary/debug method : find the best action from a state. */
	public IAction bestAction(IState s) {
		ActionList l = s.getActionList();
		Iterator<IAction> iterator = l.iterator();
		if(l.size()==0) return null;
		IAction bestA = iterator.next();
		double maxqsap = memory.get(s, bestA);   // !!! the least should be selected
		while (iterator.hasNext()) {
			IAction a = iterator.next();
			double qsap = memory.get(s, a);
			if (qsap > maxqsap) {
				maxqsap = qsap;
				bestA = a;
			}
		}
		return bestA;
	}
	
	
	

	public String toString() {
		return memory.toString();
	}
	
	
	
	public void writeToDatabase() throws Exception {
		((RewardMemorizer) memory).writeToDatabase();
	}
	
	
	public void readfromDatabase() throws Exception {
		((RewardMemorizer) memory).readfromDatabase();
	}
	
	
	public int testDatabaseConnection() {
		return ((RewardMemorizer) memory).testDatabaseConnection();
	}

	

	
	/**

	/**
	 * @return the epsilon
	 */
	public double getEpsilon() {
		return epsilon;
	}

	/**
	 * @param epsilon the epsilon to set
	 */
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

}
