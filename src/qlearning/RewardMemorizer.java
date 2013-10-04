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
 *    Stockage.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.sql.DataSource;

import environment.IAction;
import environment.IState;
import qlearning.NullValueChooser;
import qlearning.IDefaultValueChooser;




/** Memorizing  Q(s,a) in HashMap. The key is the pair (state, value). */

public class RewardMemorizer implements IRewardStore{
	/**
	 * 
	 */
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	
	
	
	
	private HashMap<ActionStatePair, Double> map = new HashMap<ActionStatePair, Double>();
    @SuppressWarnings("unused")
	private Random generateur=new Random(); 
    /** Number of items stored */
    private int numberOfItems=0; 
    private int histogram[]=new int[100]; 
    private static int numer=0;  
    @SuppressWarnings("unused")
	private int order; 
    private IDefaultValueChooser valueChooser; 

    public RewardMemorizer(){
    	this.valueChooser=new NullValueChooser();
	// Debug : to verify that several RewardMemorizers are created
	this.order=numer; 
	numer++;
    }
    public RewardMemorizer(IDefaultValueChooser vc){
    		this.valueChooser=vc; 
    		this.order=numer; 
    		numer++;	
    }
  
    /** Read Q(s,a) */
    public double get(IState s,IAction a){
	if((a==null)||(s==null)) return 0; 
	//DEBUG
	//System.out.println(s.getClass()+" "+a.getClass());
	ActionStatePair us=new ActionStatePair(a,s); 
	Double db=(Double)(map.get(us));
	if (db==null){
	    numberOfItems++; 
	    /*
	    if(numberOfItems%1000==0){
	    		System.out.println("\t\t\t"+order+"--->"+numberOfItems);
	    }
	    */
	    double u=this.valueChooser.getValue(); 
	     map.put(new ActionStatePair(a,s),new Double(u)); 
	    return u;
	}
	return db.doubleValue(); 
    }

     
    /** Store Q(s,a) : change its value if already there.
     */
    public void put(IState s,IAction a,IState sp,double qsa){
	if(sp!=null){
	    if(map.put(new ActionStatePair(a,s),new Double(qsa))==null)
		{ 
		    numberOfItems++;
		}
	    
	    }
	    else {
	    this.map.put(new ActionStatePair(a,s), new Double(qsa));	    
	    }
    }

   

    /** To monitor the evolution of Q(s,a) values.
     */
    public void makeHistogram(){ 
	Set keys=map.keySet(); 
	Iterator enu=keys.iterator(); 
	double min=10000.0; 
	double max=-10000.0; 
	double sum=0.0;
	int number=0; 
	while(enu.hasNext()){
	    ActionStatePair courante=(ActionStatePair)enu.next(); 
	   //Double db=(Double)this.get(courante.getState(),courante.getAction());
	    Double db=new Double (this.get(courante.getState(),courante.getAction()));
	    double d=db.doubleValue(); 
	    if(d>max) max=d; 
	    if(d<min) min=d; 
	    //sum+=db; 
	    sum += d;
	    number++; 
	}
	double mean=sum/number; 
	double ecartType=0.0; 
       enu=keys.iterator(); 
	while(enu.hasNext()){ 
	    ActionStatePair courante=(ActionStatePair)enu.next(); 
	    //Double db=(Double)this.get(courante.getState(),courante.getAction());
	    Double db=new Double(this.get(courante.getState(),courante.getAction()));
	    double d=db.doubleValue(); 
	    ecartType+=(d-mean)*(d-mean); 
 	    histogram[(int)Math.floor(99*(d-min)/(max-min))]++; 
	}
	
    }// makeHistogram

   public void displayHistogram(){
	for(int i=0;i<100;i++)
	    System.out.println(i+" "+histogram[i]); 
    }
	  

    
	@SuppressWarnings("unchecked")
	public String toString(){
	HashMap<IState,IAction> prov=new HashMap<IState,IAction>(); 
	HashMap local=new HashMap(map); 
	Set keys=local.keySet(); 
	String s=keys.size()+" state/action pairs \nListing of ALL  Q(s,a)\n";  
	Iterator enu=keys.iterator(); 
	ActionStatePair courante=null; 
	while(enu.hasNext()){
	    courante=(ActionStatePair)enu.next(); 
	    s+=courante.getState()+" "+courante.getAction()+" "+this.get(courante.getState(),courante.getAction())+"\n";
	    IAction bestAct=(IAction)(prov.get(courante.getState())); 
	    if(bestAct==null) prov.put(courante.getState(),courante.getAction()); 
	    else{
		if(this.get(courante.getState(),courante.getAction())>this.get(courante.getState(),bestAct))
		    prov.put(courante.getState(),courante.getAction());
	    }
	
	    
	}
	Set bk=prov.keySet(); 
	Iterator ebis=bk.iterator();
	s+="Best values Q(s,a) for given s and a\n"; 
	while(ebis.hasNext()){
	    IState s1=(IState)ebis.next(); 
	    IAction best=(IAction)(prov.get(s1)); 
	    s+=s1+"---->"+best+" : "+this.get(s1,best)+"\n"; 
	}
	
	
	
	    
	
	
	 return s;
    } // toString


  

    protected void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			int  number = resultSet.getRow();
			
			System.out.println("Rows " + number);
			
		}
	}
    
    
public int testDatabaseConnection()
{
	System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
  	 
	  try {
	    Class.forName("org.postgresql.Driver");

	  } catch (ClassNotFoundException e) {
	    System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
	    e.printStackTrace();
	    return 1;
	  }

	  System.out.println("PostgreSQL JDBC Driver Registered!");

	  Connection connection = null;

	  try {

		 connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/RLdata","dbOwner", "11223");
		  
		  
	  } catch (SQLException e) {
	    System.out.println("Connection Failed! Check output console");
	    e.printStackTrace();
	    return 0;
	  }
	 
	  if (connection != null)
	  {
		  System.out.println("You made it, take control your database now!");
		return 1;
	  }
	return 1;
	
	
}
   
public void readfromDatabase() throws Exception {
	try {
		// This will load the MySQL driver, each DB has its own driver
		//Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/RLdata","dbOwner", "11223");

		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
		// Result set get the result of the SQL query
		resultSet = statement.executeQuery("select * from public.first");
		writeResultSet(resultSet);
	
	} catch (Exception e) {
		throw e;
	} finally {
		close();
	}

}
private void close() {
	try {
		if (resultSet != null) {
			resultSet.close();
		}

		if (statement != null) {
			statement.close();
		}

		if (connect != null) {
			connect.close();
		}
	} catch (Exception e) {

	}}


    // this is made by me!!!
    @SuppressWarnings({ "unchecked", "null" })
    public void writeToDatabase()throws Exception {
    	
    	try {
    		// This will load the MySQL driver, each DB has its own driver
    		//Class.forName("com.mysql.jdbc.Driver");
    		// Setup the connection with the DB
    		connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/RLdata","dbOwner", "11223");

    		// Statements allow to issue SQL queries to the database
    		// PreparedStatements can use variables and are more efficient
		
    		//this code bellow is working
    	//preparedStatement = connect.prepareStatement("insert into  public.rlqsa values (6,33,'eerrrr')");
			
    		HashMap<IState,IAction> prov=new HashMap<IState,IAction>(); 
	    	HashMap local=new HashMap(map); 
	    	Set keys=local.keySet(); 
	    	String s=keys.size()+" state/action pairs \nListing of ALL  Q(s,a)\n";  
	    	Iterator enu=keys.iterator(); 
	    	ActionStatePair cor=null; 
	    	int i=337;
	    	while(enu.hasNext()){
	    		
	    		preparedStatement = connect.prepareStatement("insert into  public.rlqsa values (?,?,?)");
	    		preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 556+i);
				preparedStatement.setString(3, cor.toString());
				preparedStatement.executeUpdate();
				i=i+1;
	    	/*	courante=(ActionStatePair)enu.next(); 
	    	    s+=courante.getState()+" "+courante.getAction()+" "+this.get(courante.getState(),courante.getAction())+"\n";
	    	    IAction bestAct=(IAction)(prov.get(courante.getState())); 
	    	    if(bestAct==null) prov.put(courante.getState(),courante.getAction()); 
	    	    else{
	    		if(this.get(courante.getState(),courante.getAction())>
	    		   this.get(courante.getState(),bestAct))
	    		    prov.put(courante.getState(),courante.getAction());
	    	    }
	    	
	    	    */
	    	}
    		
    		
    		
    		preparedStatement = connect.prepareStatement("insert into  public.rlqsa values (?,?,?)");
    		// "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setInt(1, 9);
			preparedStatement.setInt(2, 556);
			preparedStatement.setString(3, "00556");
			preparedStatement.executeUpdate();

    	
				// PreparedStatements can use variables and are more efficient
		/*		preparedStatement = connect
						.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
			

			
				
			

			  
			  
			  
			  
			  
				
		    	System.out.println("what about this code");
			Set bk=prov.keySet(); 
			Iterator ebis=bk.iterator();
			s+="Best values Q(s,a) for given s and a\n"; 
			while(ebis.hasNext()){
			    IState s1=(IState)ebis.next(); 
			    IAction best=(IAction)(prov.get(s1)); 
			    s+=s1+"---->"+best+" : "+this.get(s1,best)+"\n"; 
			}
			 return s.length();
		  }
			  
		  else{
			  System.out.println("Failed to make connection!");
			  return 0;
		  }
		          
		  
    	*/
    	
    	} catch (Exception e) {
    		throw e;
    	} finally {
    		close();
    	}
  
    }
}  

    
    
  

