package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AgeCaluculatorMysql {
  private static final String  AGE_CALC_QUERY="SELECT YEAR(CURDATE())-YEAR(DOB) FROM PERSON_DATES WHERE PID=?";
	public static void main(String[] args) {
		Scanner sc=null; 
		int pid=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Person Id::");
				pid=sc.nextInt();
			}
			//register jdbc driver
			Class.forName("com.mysql.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///ntaj1110db1","root","root");
			//create PrepardSTatement objecct
			if(con!=null)
				ps=con.prepareStatement(AGE_CALC_QUERY);
			//set values to query params
			if(ps!=null)
				ps.setInt(1,pid);
			//execute the query
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ResultSet
			if(rs!=null){
				if(rs.next())
				   System.out.println("age :::"+rs.getInt(1));
				else
					System.out.println("No Person found");
			}
		}
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
		}//finally

	}//main
}//class
