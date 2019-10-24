package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class DBCapablities {
   
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		DatabaseMetaData  dbmd=null;
		  try{
			  //register jdbc driver
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			  //Establish the connection
			  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			  //create DataBaseMetaData obj
			  dbmd=con.getMetaData();
			  System.out.println("dbmd class name::"+dbmd.getClass());
			  System.out.println("db name::"+dbmd.getDatabaseProductName());
			  System.out.println("db version::"+dbmd.getDatabaseProductVersion());
			  System.out.println("db major version::"+dbmd.getDatabaseMajorVersion());
			  System.out.println("db minor version::"+dbmd.getDatabaseMinorVersion());
			  System.out.println("jdbc driver name::"+dbmd.getDriverName());
			  System.out.println("jdbc driver Version::"+dbmd.getDriverVersion());
			  System.out.println("All SQL keywords"+dbmd.getSQLKeywords());
			  System.out.println("All number functions"+dbmd.getNumericFunctions());
			  System.out.println("Max chars in Table name::"+dbmd.getMaxTableNameLength());
			  System.out.println("Row size::"+dbmd.getMaxRowSize());
			  System.out.println("supports Procedures::"+dbmd.supportsStoredProcedures());
		  }
		  catch(SQLException se){
			  se.printStackTrace();
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  finally{
			  //close jdbc objs
			  try{
				  if(con!=null)
					  con.close();
			  }
			  catch(SQLException se){
				  se.printStackTrace();
			  }
		  }//finally

	}//method
}//class
