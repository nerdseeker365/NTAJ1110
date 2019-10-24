package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ExcelInsertTest {
  private static final String EXCEL_INSERT_QUERY="INSERT INTO COLLEGE.SHEET1 VALUES(?,?,?)";
  	
	public static void main(String[] args) {
		Scanner sc=null;
		String name=null,addrs=null;
		int no=0;
		Connection con=null;
		PreparedStatement ps=null;
		String query=null;
		int count=0;
		//read inputs
		try{
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter student number::");
				no=sc.nextInt(); //gives 101
				System.out.println("Enter student name::");
				name=sc.next(); //gives raja
				System.out.println("Enter student addrs::");
				addrs=sc.next(); //gives hyd
			}//if
			
			//register jdbc driver
			 Class.forName("com.hxtt.sql.excel.ExcelDriver");
			 //establish the connection
			 con=DriverManager.getConnection("jdbc:Excel:///e:/Apps");
			 //create Statement object
			 if(con!=null)
				 ps=con.prepareStatement(EXCEL_INSERT_QUERY);
			 //set values to query params
			 if(ps!=null){
				 ps.setInt(1,no);
				 ps.setString(2,name);
				 ps.setString(3,addrs);
			 }
			   //send and execute SQL Query in Db s/w
			   if(ps!=null)
				   count=ps.executeUpdate();
			 
			   //process the result
			   if(count==0)
				   System.out.println("Record not inserted");
			   else
				   System.out.println("Record inserted");
		}//try
		catch(SQLException se){
			se.printStackTrace();
			 System.out.println("Record not inserted");
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
