package algorithms;

import environment.ActionList;
import environment.IAction;
import environment.IState;

public class Bacup_code {

}
public void setRouletteWheel() {
	rouletteWheel = true;
	epsilonGreedy = false;
	boltzmann = false;
}

/** Set the epsilon-greedy policy */
public void setEpsilonGreedy() {
	epsilonGreedy = true;
	rouletteWheel = false;
	boltzmann = false;
}

/** Set Boltzmann selection */
public void setBoltzmann() {
	epsilonGreedy = false;
	rouletteWheel = false;
	boltzmann = true;
	
	

	
}

public boolean getRouletteWheel() {
	return rouletteWheel; 
}

public boolean getEpsilonGreedy() {
	return epsilonGreedy;
}

public boolean getBoltzmann() {
	return boltzmann;
}






/* The following code is important and decreases the value of alpha in further steps or delayed reward
 * because in our alghoritm our goal is comparison with DP and simulation and all period are equaly importatn this code 
 * will be changed e.g take out of the system
 * 
	if (geometricDecay)
	{ System.out.printf("STOJKA");
		alpha *= decayAlpha;
	}
	else {
		
		alpha = 1 / Math.pow(count + 1.0, this.alphaDecayPower);
		System.out.printf("Vrednosta na alpha e "+alpha);
	}
	count++;
*/





/** Roulette Wheel selection of the next action : the probability for an action to be chosen is relative to its Q(s,a) value.

TODO DEBUG : not valid if Q(s,a) can be negative !!!*/

private IAction rouletteWheelChoice(ActionList l){
	if(l.size()==0) return null; 
	IState s=l.getState();
	double sum=0; 
	for(int i=0;i<l.size();i++) sum+=memory.get(s,l.get(i))+1; 
	double choix=generator.nextDouble()*sum; 
	int indice=0;
	double partialSum=memory.get(s,l.get(indice))+1; 
	while(choix>partialSum){
		  indice++; 
		  partialSum+=1+memory.get(s,l.get(indice)); 
	}
	return l.get(indice);	
}



/** Chooses an action according to the Boltzmann protocol */
private IAction boltzmannChoice(ActionList l){
	if(l.size()==0) return null; 
	IState s=l.getState();
	double sum=0; 
	double tab[]=new double[l.size()]; 
	for(int i=0;i<l.size();i++) {
	    sum+=Math.exp(memory.get(s,l.get(i))/this.tau); 
	    tab[i]=sum; 
	}    
	double choix=generator.nextDouble()*sum; 
	for(int i=0;i<l.size();i++){
	    if(choix<=tab[i]) return l.get(i);
	}
	System.err.println(choix+ " "+"Wrong");
	System.exit(-1); 
	return null;
}


public IAction getChoice(ActionList l) {
	if (rouletteWheel)
		return rouletteWheelChoice(l);
	if (epsilonGreedy)
		return epsilonGreedyChoice(l);
	if (boltzmann)
		return boltzmannChoice(l);
	return null;
}




else {
	// improvement to select more the upper states on every second choise 
	if ((generator.nextInt(4)>1)&&(l.size()>1) )	// l.size could be 1 !!! be aware 			

	{
	int a1=generator.nextInt(l.size()/2);
	System.out.println("The random action is selected  prefering the upper states----- " +l.get(a1).toString());
    return l.get(a1);
    
	}