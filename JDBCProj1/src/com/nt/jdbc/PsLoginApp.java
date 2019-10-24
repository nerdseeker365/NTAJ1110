package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PsLoginApp {
  private static final  String LOGIN_QUERY="SELECT COUNT(*) FROM USERLIST WHERE UNAME=? AND PWD=?";
	public static void main(String[] args) {
		Scanner sc=null;
		String user=null,pass=null;
		Connection con=null;
		PreparedStatement ps=null;
		String query=null;
		ResultSet rs=null;
		int count=0;
		
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter username::");
				user=sc.nextLine(); //gives raja
				System.out.println("Enter password::");
				pass=sc.nextLine(); //gives rani
			}
		
			
			//register jdbc driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:oci8:@xe", "system","manager");
			//create Statement obj
			if(con!=null)
				ps=con.prepareStatement(LOGIN_QUERY);
			//set input values query params
			if(ps!=null){
				ps.setString(1, user);
				ps.setString(2,pass);
			}
		
			 //send and execute SQL query
			 if(ps!=null)
				 rs=ps.executeQuery();
			 //process the ResultSet
			 if(rs!=null){
				 rs.next();
				 count=rs.getInt(1);
			 }
			  if(count==0)
				  System.out.println("InValid Credentials");
			  else
				  System.out.println("Valid Credentials");
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			try{
				if(ps!=null)
					ps.close();
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
