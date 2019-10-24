package com.nt.jdbc;
/*  JDBC App to delete students based on the given city names
 *  
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteTest1 {

	public static void main(String[] args) {
	    Scanner sc=null;
	    String city1=null,city2=null;
	    Connection con=null;
	    Statement st=null;
	    String query=null;
	    int count=0;
	    String cond=null;
		//read inputs
		try{
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter city1::");
				city1=sc.next();  //gives hyd
				System.out.println("Enter city2::");
				city2=sc.next();  //gives vizag
			}//if
			//convert input values as required for the SQL Query  ('hyd','vizag')
			cond="('"+city1+"','"+city2+"')";
			System.out.println(cond);
			
			
			//register jdbc driver
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 //establish the connection
			 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			 //create Statement object
			 if(con!=null)
				 st=con.createStatement();
			 //prepare query
			 query="DELETE FROM STUDENT WHERE SADD IN"+cond;
			 System.out.println(query);
			 //send and execute SQL Query
			 if(st!=null)
				 count=st.executeUpdate(query);
			 //process the result
			 if(count==0)
				 System.out.println("Record not found for deletion");
			 else
				 System.out.println(count+" no.of Records are found  and deleted");
		}//try
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//close jdbc objs
			try{
				if(st!=null)
					st.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			try{
				if(con!=null)
					con.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			try{
				if(sc!=null)
					sc.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}//finally
		
	}//main
}//class
