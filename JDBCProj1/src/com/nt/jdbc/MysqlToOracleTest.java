package com.nt.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlToOracleTest {
    private static final String ORA_INSERT_QUERY="INSERT INTO PERSON_DATES VALUES(?,?,?,?,?)";
    private  static final String MYQL_ALL_QUERY="SELECT PID,PNAME,DOB,DOJ,DOM FROM PERSON_DATES";
	public static void main(String[] args) {
		Connection  oraCon=null,mysqlCon=null; 
		Statement st=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int pid=0;
		String pname=null;
		Date dob=null,doj=null,dom=null;
		
		
		try{
			//register jdbc drivers
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver");
			//Establish the connections
			oraCon=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			mysqlCon=DriverManager.getConnection("jdbc:mysql:///ntaj1110db1","root","root");
			//create Statement objs
			if(mysqlCon!=null)
				st=mysqlCon.createStatement();
			if(oraCon!=null)
				ps=oraCon.prepareStatement(ORA_INSERT_QUERY);
			if(mysqlCon!=null)
				rs=st.executeQuery(MYQL_ALL_QUERY);
			//copy records from mysql to oracle
			if(rs!=null && ps!=null){
				while(rs.next()){
					//get each record from ResultSet (mysql)
					pid=rs.getInt(1);
					pname=rs.getString(2);
					dob=rs.getDate(3);
					doj=rs.getDate(4);
					dom=rs.getDate(5);
					//set values to query params (oracle )
					ps.setInt(1, pid);
					ps.setString(2,pname);
					ps.setDate(3,dob);
					ps.setDate(4,doj);
					ps.setDate(5,dom);
					
					//execute the query(oracle)
					ps.executeUpdate();  
				}//while
				System.out.println("Records are copiled");
			}//if
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
				if(st!=null)
					st.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			
			try{
				if(mysqlCon!=null)
					mysqlCon.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			try{
				if(oraCon!=null)
					oraCon.close();
			}
			catch(SQLException ioe){
				ioe.printStackTrace();
			}
		}//finally
	}//main
}//class
