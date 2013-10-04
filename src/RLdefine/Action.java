package RLdefine;


import environment.IAction; 

public class Action implements IAction{
	
	private static final long serialVersionUID = 1L;
	 
    private int action; 
    
 
    public Action(int r){
    	 
    	action=r; 
    	
        }
    
        
    public int getAction(){return action;}
   
    /** Total action */
  //  public double getaction(){return totalaction;}
    
    public String toString(){return " "+action;}

    public int hashCode(){
	return (int) (10*action); 
    }
    
    
    public boolean equals(Object o){
    	if(!(o instanceof Action)) return false; 
    	Action el=(Action)o; 
    	return(el.action==this.action); 
        }
    
    public IAction copy(){return new Action(action); }


    }
    
    

