package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/*
   oracleSQL> create table person_dates(pid number(5) primary key,pname varchar2(20),DOB date,DOJ date,DOM date);
   mysql> create table person_dates(pid int(5) primary key,pname varchar(20),DOB date,DOJ date,DOM date);
 */

public class DateInsertMysqlTest {
 private static final String  INSERT_DATE_TEST="INSERT INTO PERSON_DATES VALUES(?,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int id=0;
		String name=null,dob=null,doj=null,dom=null;
		java.util.Date udob=null,udom=null;
		SimpleDateFormat sdf1=null,sdf2=null;
		java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		Connection con=null;
		PreparedStatement ps=null;
		int count=0;
	 //read inputs
		try{
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("enter person id::");
				id=sc.nextInt();
				System.out.println("enter person name::");
				name=sc.next();
				System.out.println("enter DOB(dd-MM-yyyy)");
				dob=sc.next();
				System.out.println("enter DOJ(yyyy-MM-dd)");
				doj=sc.next();
				System.out.println("enter DOM(MM-dd-yyyy)");
				dom=sc.next(); 
			}//if
			
			//convert String date values to java.util.Date class objs
			 //DOB
			sdf1=new SimpleDateFormat("dd-MM-yyyy");
			udob=sdf1.parse(dob);
			//DOM
			sdf2=new SimpleDateFormat("MM-dd-yyyy");
			udom=sdf2.parse(dom);
			 //convert java.util.Date class objs to java.sql.Date class obj
			sqdob=new java.sql.Date(udob.getTime());
			sqdom=new java.sql.Date(udom.getTime());
			//convert String date values (doj) directly to java.sql.Date class obj (yyyy-MM-dd)
			sqdoj=java.sql.Date.valueOf(doj);
			//register jdbc driver
			Class.forName("com.mysql.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///NTAJ1110DB1", "root","root");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(INSERT_DATE_TEST);
			//set values to query params
			if(ps!=null){
				ps.setInt(1,id);
				ps.setString(2,name);
				ps.setDate(3,sqdob);
				ps.setDate(4,sqdoj);
				ps.setDate(5,sqdom);
			}
			//execute SQL query
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
