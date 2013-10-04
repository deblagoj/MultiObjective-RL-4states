package algorithms; 

import environment.ActionList;
import environment.IAction;
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
import environment.IState;


/** All we ask an algorithm to be able to do is : 

<ul>
<li> Extract an action chosen among the list of possible moves</li>
<li> Learn from its experience </li>
</ul>


*/

public interface  ISelector {
  
	/** Choose an action in the list of legal moves 
	 * @param l ActionList to deal with (i.e. Isate and associated actions
	 * No need for the start state, it is included into the ActionList
	 * */
	public IAction getChoice(ActionList l);
	
	/** Learn 
	 @param s1 The state the agent is in before the action is performed.
	 * @param s2 The state the agent goes to when the action is performed. 
	 * @param a The action the agent took.
	 * @param reward The reward obtained for this move.
	 * @return 
	 */
	public double learn(IState s1, IState s2, IAction a, double reward);

	/** There might be some things to do at the beginning of each episode... */
	public void newEpisode();
	
	/* the best action */
	public IAction bestAction(IState s);
	
	public double getValue(IState s,IAction a);
	
	public void setPrintScreen();
}
