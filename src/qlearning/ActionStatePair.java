package qlearning;

/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation; either version 2.1 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    ActionStatePairjava
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import environment.IAction;
import environment.IState;


/** Acts like a key to store Q(s,a) values. The key is the union of state and action.

 @author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/

public class ActionStatePair {

    /**
	 * 
	 */

	/** Action part of the key. */
    protected IAction myAction; 
    /** State part of the key.*/
    protected IState myState; 

    public ActionStatePair(IAction a,IState s){
	this.myAction=a; 
	this.myState=s; 
    }

    public int hashCode(){
	return myAction.hashCode()+myState.hashCode(); 
    }

    public boolean equals(Object o){
	if(!(o instanceof ActionStatePair)) return false; 
	ActionStatePair us=(ActionStatePair)o; 
	return (us.myState.equals(this.myState)&&us.myAction.equals(this.myAction)); 
    }

    public IState getState(){return myState; }
    public IAction getAction(){return myAction; }
    
    public void putState(IState s){myState=s; }
    public void putAction(IAction a){myAction=a; }
    
    
    public void copyActionState(ActionStatePair t)
    {
    	myAction=t.getAction();
    	myState=t.getState();
    }

    public String toString(){
	    return myState+" "+myAction; 
    }
}
