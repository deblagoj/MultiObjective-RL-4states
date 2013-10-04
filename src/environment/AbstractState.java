package environment; 




abstract public class AbstractState implements IState{

 

	/** The game's rules */
    protected IEnvironment myEnvironment; 
    
    public AbstractState(IEnvironment ct){
	this.myEnvironment=ct; 
    }

    public boolean isFinal(){
	return this.myEnvironment.isFinal(this); 
    }

    public void setEnvironment(IEnvironment ct){this.myEnvironment=ct;}
    
    
    public ActionList getActionList(){
	return myEnvironment.getActionList(this); 
    }
	
    
    public IState modify(IAction a){
    	return this.myEnvironment.successorState(this,a); 
    }

    public IEnvironment getEnvironment(){return myEnvironment;}

    public double getReward(IState old,IAction a){
	return this.myEnvironment.getReward(old,this,a); 
}

    abstract public IState copy(); 
    
   
  
    
  //  abstract public int getY();
  

}




    
