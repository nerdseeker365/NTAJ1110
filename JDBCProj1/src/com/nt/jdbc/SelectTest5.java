package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* App to get highest Salary Employee details */

public class SelectTest5 {

	public static void main(String[] args) {
     String query=null;
     boolean flag=false;
	try{
	//Load jdbc driver class to register jdbc driver
	  Class.forName("oracle.jdbc.driver.OracleDriver");
	}
	catch(ClassNotFoundException cnf){
		cnf.printStackTrace();
	}
	  //establish the connection
	try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager")){
   	      //create JDBC STatement object
	   	     try(Statement st=con.createStatement()){
                   	  // prpeare Query
	                query="SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE SAL=(SELECT MAX(SAL) FROM EMP)";
	                System.out.println(query);
                	   //send and execute SQL Query in Db s/w
		              try(ResultSet  rs=st.executeQuery(query)){
                	   //process the ResultSEt
	                   if(rs!=null){
             		   while(rs.next()){
			                flag=true;
			               System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getInt(4));
		                }
         	   }
	                   if(flag==false)
	    	            System.out.println("Records not found");
		     }//try
		 }//try
	}//try
	catch(SQLException se){  //known exception
		se.printStackTrace();
	}
	catch(Exception e){   // unknown exception
		e.printStackTrace();
	}
	
  }//main
}//class
