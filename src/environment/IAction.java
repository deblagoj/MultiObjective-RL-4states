package environment; 



    /** Minimal behavior of a generic Action.*/

    public interface IAction {

	/** Clone an Action. */
    	// TODO change profile to public IAction ? 
	public IAction copy(); 


	
	/** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
	public int hashCode(); 


	/** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
	public boolean equals(Object o);



	public int getAction(); 


   
}
  
