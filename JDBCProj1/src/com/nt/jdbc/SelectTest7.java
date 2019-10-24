package com.nt.jdbc;
/* App to get Records count from Db table*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest7 {

	public static void main(String[] args) {
	     Connection con=null;
	     Statement st=null;
	     String query=null;
	     ResultSet rs=null;
		try{
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create STatement object
			if(con!=null)
				st=con.createStatement();
			//prpeare SQL Query
			query="SELECT COUNT(*) FROM STUDENT";
			//send and execute SQL Query in Db s/w
			if(st!=null)
				rs=st.executeQuery(query);
			//process the ResultSet
			if(rs!=null){
			   rs.next();
			   //System.out.println("Count:::"+rs.getInt(1));
			   System.out.println("Count:::"+rs.getInt("count(*)"));
			}
		}//try
		catch(SQLException se){  //known exception
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();  //known exception
		}
		catch(Exception e){   // unknown exception
			e.printStackTrace();
		}
		finally{
			//close jdbc objs
			try{
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
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
		}//finally

	}//main
}//class
