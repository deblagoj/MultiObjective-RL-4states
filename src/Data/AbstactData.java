package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public  class AbstactData implements IData {

	
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	
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

			 connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");
			  
			  
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
	   
	
	
	
	
		

	
	
	
	public double[] getStorageDiscretisation() throws Exception {
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.storage_rl";
			
			/*
			 * the three line bellow are for row count to know how many discretisation states are there
			 * this is done to initialize the storagediscretization vector
			 */
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.storage_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}
	
	
	
	
	
	
	
	
	public double[] getArray(int k) throws Exception
	{    
		
		String rowcountquery;
		
		connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","link", "link231");

		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
		// Result set get the result of the SQL query
		
	 
		
	
		
		/*
		 * the three line bellow are for row count to know how many discretisation states are there
		 * this is done to initialize the storagediscretization vector
		 */
		rowcountquery="SELECT data_array FROM public.inflow_rl WHERE id ="+"'" +  k  + "'";
					
	    
		
		resultSet=statement.executeQuery(rowcountquery);
		
		resultSet.next();
		
		String z = resultSet.getString("data_array");
		     
		
		String wtbracket=z.substring(1, z.length()-1);
		     
		List<String> items = Arrays.asList(wtbracket.split("\\s*,\\s*"));
		     
		     
		        
		ArrayList<Double> ds = new ArrayList<Double>();
		// fill ds with Doubles
		
		for (String d : items) {
		    // Apply formatting to the string if necessary
		    ds.add(Double.parseDouble(d));
		}
		
		
				double y[] = new double[ds.size()];
				for (int i=0; i<ds.size(); i++)
				{
				    y[i] = (double)ds.get(i);
				}
		
		
		
		return y;
		
	}
	
	
	
	public int getEpisodes(int m) throws SQLException
	{
		
	    int s;
		String rowcountquery;
		connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","link", "link231");

		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
		// Result set get the result of the SQL query
		

	
		
		/*
		 * the three line bellow are for row count to know how many discretisation states are there
		 * this is done to initialize the storagediscretization vector
		 */
		rowcountquery="SELECT episodes FROM public.inflow_rl WHERE id ="+"'" +  m  + "'";
					
	
		
		resultSet=statement.executeQuery(rowcountquery);
		resultSet.next();
		
		s=resultSet.getInt("episodes");
	
		return s;
		
		
		
	}
	
	
	
	public int getTimeSteps(int m) throws SQLException
	{
		
	    int s;
		String rowcountquery;
		connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","link", "link231");

		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
		// Result set get the result of the SQL query
		
	 
		
	
		
		/*
		 * the three line bellow are for row count to know how many discretisation states are there
		 * this is done to initialize the storagediscretization vector
		 */
		rowcountquery="SELECT timesteps FROM public.inflow_rl WHERE id ="+"'" +  m  + "'";
					
	
		
		resultSet=statement.executeQuery(rowcountquery);
		resultSet.next();
		
		s=resultSet.getInt("timesteps");
	
		return s;
		
		
		
	}
	
	
	
	
	
	
	


	public double[] getstorageTargetsFloods() throws Exception {
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.flood_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.flood_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			resultSet = statement.executeQuery(query);
			
	
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}

	
	public double[] getstorageTargetsFloodWeights() throws Exception {
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.flood_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.flood_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

		
			resultSet = statement.executeQuery(query);
			
	
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}
	
	
	
	
	
	public double[] getstorageTargetsRecreation() throws Exception {
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.recreation_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.recreation_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
			
	
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}
	
	
	public double[] getstorageTargetsRecreationWeights() throws Exception {
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.recreation_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.recreation_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
			
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}
	
	
	
	
	public void  putStorageOptimal( double [] Optimal, int fD ) throws Exception{
		try {
		
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			
			
			 String sql = "DELETE FROM public.reservoir_level_rl";
			  
			 int delete = statement.executeUpdate(sql);
			  if(delete == 0){
			  System.out.println("All rows are completelly deleted!");
			  }
			
			  int rows; 
			  
			for(int i=0;i<fD;i++)
			{
				String sql1 = "INSERT INTO public.reservoir_level_rl VALUES "
				 + "('" +   i + "','" + Optimal[i] + "')";
				
				
				
				rows = statement.executeUpdate(sql1); 
			}
			
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


	
	
	
	@Override
	public double[] getTargetsTowns_SS() throws Exception {
		// TODO Auto-generated method stub
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandtown_rl_ss";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandtown_rl_ss";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}










	@Override
	public double[] getTargetsTownsWeights_SS() throws Exception {
		// TODO Auto-generated method stub
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandtown_rl_ss";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandtown_rl_ss";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}






	
	
	
	

	@Override
	public double[] getTargetsTowns_PZ() throws Exception {
		// TODO Auto-generated method stub
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandtown_rl_pz";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandtown_rl_pz";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}










	@Override
	public double[] getTargetsTownsWeights_PZ() throws Exception {
		// TODO Auto-generated method stub
		try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandtown_rl_pz";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandtown_rl_pz";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}



	
	




	@Override
	public double[] getTargetAgriculture_UZ() throws Exception {
		// TODO Auto-generated method stub
try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandagriculture_rl_uz";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandagriculture_rl_uz";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}






	



	@Override
	public double[] getTargetAgricultureWeights_UZ() throws Exception {
try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandagriculture_rl_uz";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandagriculture_rl_uz";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}




	
	
	
	
	


	@Override
	public double[] getTargetsAgriculture_LZ() throws Exception {
		// TODO Auto-generated method stub
try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandagriculture_rl_lz";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandagriculture_rl_lz";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}










	@Override
	public double[] getTargetsAgricultureWeights_LZ() throws Exception {
try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandagriculture_rl_lz";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandagriculture_rl_lz";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}







	@Override
	public double[] getstorageTargetsEcology() throws Exception {
try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandecology_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandecology_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}










	@Override
	public double[] getstorageTargetsEcologyWeights() throws Exception {
try {
			
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demandecology_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandecology_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}






	
	public double[] getstorageTargetsHydroPower() throws Exception {
		try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.demandhydropower_rl";
					
				
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandhydropower_rl";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
				
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}










			@Override
			public double[] getstorageTargetsHydroPowerWeights() throws Exception {
		try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.demandhydropower_rl";
					
				
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demandhydropower_rl";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(3);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			
			
	
	
	
	
	
	
			
			
			public double[] getMiddleInflow() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_mid_rl";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_mid_rl";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			
			
			
			
			
			public double[] getMiddleInflow_sim() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_mid_rl_sim";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_mid_rl_sim";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			
			
			
			
			
			public double[] getMiddleInflow_Hight_Knezevo() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_mid_hk_rl";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_mid_hk_rl";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			
			
			
			public double[] getMiddleInflow_Hight_Knezevo_sim() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_mid_hk_rl_sim";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_mid_hk_rl_sim";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			
			
			
			
			public double[] getMiddleInflow_LowZ_UpZ() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_mid_lu_rl";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_mid_lu_rl";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}



			public double[] getMiddleInflow_LowZ_UpZ_sim() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_mid_lu_rl_sim";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_mid_lu_rl_sim";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			
			
			

			public double[] getInflow() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_rl";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_rl";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}
			




		
			public double[] getInflow_sim() throws Exception {
				try {
					
					connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

					// Statements allow to issue SQL queries to the database
					statement = connect.createStatement();
					// Result set get the result of the SQL query
					String query,rowcountquery;
				
					int row_count=1;
					query="select * from public.inflow_rl_sim";
					
					
					rowcountquery="SELECT COUNT( * ) AS row_count FROM public.inflow_rl_sim";
					resultSet=statement.executeQuery(rowcountquery);
					resultSet.next();
					row_count=resultSet.getInt(1);

					// this line executes the querey 
					resultSet = statement.executeQuery(query);
					
				
					
				 double[] StorageDiskretization=new double [row_count];
				 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
				
			
					 int i=0;
					while (resultSet.next())
				{
						StorageDiskretization[i]=resultSet.getDouble(2);
				          i++;
					}
			
					return StorageDiskretization;
					
				} catch (Exception e) {
					throw e;
				} finally {
					
			close();
				}
			}







		



		
			
			
			
			
		
			
			
			
			
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 public double[] getreleaseTargets() throws Exception {
		try {
		
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demand_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demand_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
		
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(2);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}
	
	public double[] getreleaseTargetsWeights() throws Exception {
		try {
		
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			String query,rowcountquery;
		
			int row_count=1;
			query="select * from public.demand_rl";
			
		
			rowcountquery="SELECT COUNT( * ) AS row_count FROM public.demand_rl";
			resultSet=statement.executeQuery(rowcountquery);
			resultSet.next();
			row_count=resultSet.getInt(1);

			// this line executes the querey 
			resultSet = statement.executeQuery(query);
		
			
		 double[] StorageDiskretization=new double [row_count];
		 System.out.print("	eeeeeeeeeeeeeeee" +row_count);
	
			 int i=0;
			while (resultSet.next())
		{
				StorageDiskretization[i]=resultSet.getDouble(3);
		          i++;
			}
	
			return StorageDiskretization;
			
		} catch (Exception e) {
			throw e;
		} finally {
			
	close();
		}
	}
	 * 
	 * 
    // this is made by me!!!
    @SuppressWarnings({ "unchecked", "null" })
    public void writeToDatabase()throws Exception {
    	
    	
    	try {
    		// This will load the MySQL driver, each DB has its own driver
    		//Class.forName("com.mysql.jdbc.Driver");
    		// Setup the connection with the DB
    		connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/HMak","postgres", "marjan007");

    		// Statements allow to issue SQL queries to the database
    		// PreparedStatements can use variables and are more efficient
		
    		//this code bellow is working
    	//preparedStatement = connect.prepareStatement("insert into  public.rlqsa values (6,33,'eerrrr')");
			
    			
	    	int i=337;
	    	while(enu.hasNext()){
	    		
	    			preparedStatement = connect.prepareStatement("insert into  public.rlqsa values (?,?,?)");
	    		preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 556+i);
				preparedStatement.setString(3, cor.toString());
				preparedStatement.executeUpdate();
				i=i+1;
	    		courante=(ActionStatePair)enu.next(); 
	    	    s+=courante.getState()+" "+courante.getAction()+" "+this.get(courante.getState(),courante.getAction())+"\n";
	    	    IAction bestAct=(IAction)(prov.get(courante.getState())); 
	    	    if(bestAct==null) prov.put(courante.getState(),courante.getAction()); 
	    	    else{
	    		if(this.get(courante.getState(),courante.getAction())>
	    		   this.get(courante.getState(),bestAct))
	    		    prov.put(courante.getState(),courante.getAction());
	    	    }
	    	
	    	}
    		
    		
    		
    		preparedStatement = connect.prepareStatement("insert into  public.rlqsa values (?,?,?)");
    		// "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setInt(1, 9);
			preparedStatement.setInt(2, 556);
			preparedStatement.setString(3, "00556");
			preparedStatement.executeUpdate();

    	
				// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
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
		          
		  
    	
    	
    	} catch (Exception e) {
    		throw e;
    	} finally {
    		close();
    	}
  
    }


*/



	
	
	


	
	
	

	

}
