package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/*
   oracleSQL> create table person_dates(pid number(5) primary key,pname varchar2(20),DOB date,DOJ date,DOM date);
   mysql> create table person_dates(pid int(5) primary key,pname varchar(20),DOB date,DOJ date,DOM date);
 */

public class DateRetrieveMysqlTest {
 private static final String  READ_DATE_QUERY="SELECT PID,PNAME,DOB,DOJ,DOM FROM PERSON_DATES";
	public static void main(String[] args) {
		int id=0;
		String name=null,dob=null,doj=null,dom=null;
		java.util.Date udob=null,udom=null,udoj=null;
		SimpleDateFormat sdf1=null;
		java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean flag=false;
	 //read inputs
		try{
			//register jdbc driver
			Class.forName("com.mysql.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///NTAJ1110DB", "root","root");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(READ_DATE_QUERY);
			
			//execute SQL query
			if(ps!=null)
				rs=ps.executeQuery();
			//process the result
			if(rs!=null){
				while(rs.next()){
					flag=true;
					id=rs.getInt(1);
					name=rs.getString(2);
					sqdob=rs.getDate(3);
					sqdoj=rs.getDate(4);
					sqdom=rs.getDate(5);
					//convert java.sql.Date class objs to java.util.Date class objs
					udob=(java.util.Date)sqdob;
					udoj=(java.util.Date)sqdoj;
					udom=(java.util.Date)sqdom;
					//convert java.util.Date class objs to String date values
					sdf1=new SimpleDateFormat("dd-yyyy-MMM");
					dob=sdf1.format(udob);
					doj=sdf1.format(udoj);
					dom=sdf1.format(udom);
					System.out.println(id+" "+name+"  "+dob+"  "+doj+"  "+dom);
				}//while
				if(flag==false)
					System.out.println("Records not found");
			}
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
			
		
		}//finally
	}//main
}//class
