package RLdefine;
import RLdefine.RLdefine;
import environment.IEnvironment;
import environment.AbstractState; 
import environment.IState; 

public class State extends AbstractState {
	

	protected int timestep;
	protected int state;
	protected int middle_inflow;
	protected int inflow;
	
	
	
	public State (int z,int t,int m,int i,IEnvironment univers){
		super(univers); 
		this.timestep=z; 
		this.state=t;
		this.middle_inflow=m;
		this.inflow=i;
	
	    }
	
	
	 public int getTimestep(){return timestep;}
	    public int getState(){return state;}
	
	
	public IState copy(){
		return new State(timestep,state,middle_inflow,inflow, myEnvironment); 
	    }
	
	 public String toString(){return "timestep "+timestep+" storage"+state+"middle inflow  "+middle_inflow+" inflow "+inflow;}

	   public int hashCode(){
	 return (this.timestep+this.state*((RLdefine)myEnvironment).timesteps+
				this.middle_inflow*((((RLdefine)myEnvironment).timesteps)* (((RLdefine)myEnvironment).discretizationLevels))+
				this.inflow*(((((RLdefine)myEnvironment).timesteps)* ((RLdefine)myEnvironment).discretizationLevels)
				*((RLdefine)myEnvironment).sections_middle_inflow)); ///the bigger from X or Y to have different hash code
	   }
	    
	    public boolean equals(Object o){
	    	if(!(o instanceof State)) return false; 
	    	State el=(State)o; 
	    	return((el.timestep==this.timestep)&&(el.state==this.state)&&(el.middle_inflow==this.middle_inflow)&&(el.inflow==this.inflow)); 
	        }
	    public boolean getTurn(){return false;}

	 
	    public boolean isFinal(IState e){
	    	State s=(State)e; 
	    	
	    	if (s.getTimestep()==timestep) return true;
	    	else 
	    		return false;
	    	
	        }
	 
	

}
