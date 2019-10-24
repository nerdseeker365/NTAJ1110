package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ResultSetMetaDataSelectTest {

	public static void main(String[] args) {
		Scanner sc=null;
	     int dno=0;
	     Connection con=null;
	     Statement st=null;
	     String query=null;
	     ResultSet rs=null;
	     ResultSetMetaData rsmd=null;
	     int colCount=0;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Dept no::");
				dno=sc.nextInt();
			}
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create STatement object
			if(con!=null)
				st=con.createStatement();
			//prpeare SQL Query
			query="SELECT DEPTNO,DNAME,LOC FROM DEPT WHERE DEPTNO="+dno;
			//send and execute SQL Query in Db s/w
			if(st!=null)
				rs=st.executeQuery(query);
			//create ResultSetMetaData object
			if(rs!=null)
				rsmd=rs.getMetaData();
			//print col names
			colCount=rsmd.getColumnCount();
			if(rsmd!=null){
				for(int i=1;i<=colCount;++i){
					  System.out.print(rsmd.getColumnLabel(i)+"             ");
				  }
				  System.out.println();
			  for(int i=1;i<=colCount;++i){
				  System.out.print(rsmd.getColumnTypeName(i)+"  ");
			  }
			  System.out.println();
			  
			}
				  
			
			
			//process the ResultSet
			if(rs!=null){
				if(rs.next()){
					System.out.println(rs.getInt(1)+"       "+rs.getString(2)+"        "+rs.getString(3));
				}
				else{
					System.out.println("records not found");
				}
			}
		}//try
		catch(SQLException se){  //known exception
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();  //known exception
		}
		catch(Exception e){   // unknown exception
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
				if(st!=null)
					st.close();
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
