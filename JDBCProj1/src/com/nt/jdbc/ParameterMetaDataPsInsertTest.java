package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ParameterMetaDataPsInsertTest {
	private static final  String  STUD_INSERT_QUERY="INSERT INTO STUDENT VALUES(?,?,?)";

	public static void main(String[] args) {
		//read inputs
		Scanner sc=null;
		Connection con=null;
		PreparedStatement ps=null;
		ParameterMetaData pmd=null;
		int count=0;
		try{
			sc=new Scanner(System.in);
		if(sc!=null){
			System.out.println("Enter students Count::");
			count=sc.nextInt();
		  }
		//register JDBc driver
		   //Class.forName("oracle.jdbc.driver.OracleDriver");
		  //establish the connection
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
		//Send SQL Query Db s/ws and make it Pre-compiled SQL Query
		 if(con!=null)
			 ps=con.prepareStatement(STUD_INSERT_QUERY);
		 //create ParameterMetaData 
		 pmd=ps.getParameterMetaData();
		 //get Parameter details
		 count=pmd.getParameterCount();
		 for(int i=1;i<=count;++i){
			 System.out.println("parameter number::"+i);
			 System.out.println("Parameter mode::"+pmd.getParameterMode(i));
			 System.out.println("parameter type ::"+pmd.getParameterType(i));
			 System.out.println("parameter type ::"+pmd.getParameterTypeName(i));
			 System.out.println("parmaeter allows null values?"+pmd.isNullable(i));
		 }
		 
		}//try
		catch(SQLException se){
			se.printStackTrace();
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
