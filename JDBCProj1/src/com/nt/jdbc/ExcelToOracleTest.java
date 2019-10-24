package com.nt.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelToOracleTest {
    private static final String ORA_INSERT_QUERY="INSERT INTO STUDENT VALUES(?,?,?)";
    private  static final String EXCEL_ALL_QUERY="SELECT SNO,SNAME,SADD FROM College.Sheet1";
	public static void main(String[] args) {
		Connection  oraCon=null,excelCon=null; 
		Statement st=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int sno=0;
		String sname=null;
		String addrs=null;
		
		try{
			//register jdbc drivers
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			//Establish the connections
			oraCon=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			excelCon=DriverManager.getConnection("jdbc:excel:///E:/Apps");
			//create Statement objs
			if(excelCon!=null)
				st=excelCon.createStatement();
			if(oraCon!=null)
				ps=oraCon.prepareStatement(ORA_INSERT_QUERY);
			if(excelCon!=null)
				rs=st.executeQuery(EXCEL_ALL_QUERY);
			//copy records from mysql to oracle
			if(rs!=null && ps!=null){
				while(rs.next()){
					//get each record from ResultSet (mysql)
					sno=rs.getInt(1);
					sname=rs.getString(2);
					addrs=rs.getString(3);
					//set values to query params (oracle )
					ps.setInt(1,sno);
					ps.setString(2,sname);
					ps.setString(3,addrs);
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
				if(excelCon!=null)
					excelCon.close();
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
