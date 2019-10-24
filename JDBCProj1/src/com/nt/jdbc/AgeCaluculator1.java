package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class AgeCaluculator1 {
  private static final String  AGE_CALC_QUERY="SELECT  DOB FROM PERSON_DATES WHERE PID=?";
	public static void main(String[] args) {
		Scanner sc=null; 
		int pid=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		java.sql.Date sqdob=null;
		java.util.Date udob=null;
		long ms=0;
		long ageS=0,ageM=0,ageH=0,ageD=0,age=0;
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
				if(rs.next()){
					sqdob=rs.getDate(1);
					udob=(java.util.Date)sqdob;
					 ms=(new Date().getTime())-(udob.getTime());
					ageS=ms/1000;
					System.out.println("age in seconds::"+ageS);
					ageM=ageS/60;
					System.out.println("age in minitues::"+ageM);
					ageH=ageM/60;
					System.out.println("age in hours::"+ageH);
					ageD=ageH/24;
					System.out.println("age in days::"+ageD);
					age=ageD/365;
					System.out.println("age ::"+age);
				}
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
